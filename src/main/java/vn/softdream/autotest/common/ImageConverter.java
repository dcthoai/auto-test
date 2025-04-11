package vn.softdream.autotest.common;

import vn.softdream.autotest.constants.BaseConstants;
import vn.softdream.autotest.dto.upload.ImageDTO;
import vn.softdream.autotest.dto.upload.ImageParameterDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@SuppressWarnings("unused")
public class ImageConverter {

    private static final Logger log = LoggerFactory.getLogger(ImageConverter.class);
    private static final String ENTITY_NAME = "ImageConverter";

    public static boolean isValidImageFormat(MultipartFile file, String[] fileTypesAllowed) {
        if (FileUtils.invalidUploadFile(file))
            return false;

        String lowerCaseFileName = Optional.ofNullable(file.getOriginalFilename()).orElse("").toLowerCase();

        for (String format : fileTypesAllowed) {
            if (lowerCaseFileName.endsWith(format)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidImageFormat(MultipartFile file) {
        return isValidImageFormat(file, BaseConstants.UPLOAD_RESOURCES.VALID_IMAGE_FORMATS);
    }

    public static boolean isCompressibleImage(MultipartFile file) {
        return isValidImageFormat(file, BaseConstants.UPLOAD_RESOURCES.COMPRESSIBLE_IMAGE_FORMATS);
    }

    public static ImageDTO compressImage(MultipartFile image) throws IOException {
        log.debug("Trying compressing `{}`", image.getOriginalFilename());

        if (!isCompressibleImage(image))
            return null;

        ImageParameterDTO imageParameterDTO = getImageParameterWithCompressionFactor(image);

        if (imageParameterDTO.isCompressed())
            return null;

        File compressedImage = switch (imageParameterDTO.getImageType()) {
            case BaseConstants.UPLOAD_RESOURCES.PNG,
                 BaseConstants.UPLOAD_RESOURCES.WEBP -> webpLossyCompression(imageParameterDTO);
            case BaseConstants.UPLOAD_RESOURCES.JPEG,
                 BaseConstants.UPLOAD_RESOURCES.JPG -> jpegLossyCompression(imageParameterDTO);
            default -> {
                log.error("Could not compress image `{}`", image.getOriginalFilename());
                yield null;
            }
        };

        return new ImageDTO(compressedImage, imageParameterDTO);
    }

    private static File webpLossyCompression(ImageParameterDTO imageParameter) throws IOException {
        log.debug("Compressing image `{}` using `webpLossyCompression`", imageParameter.getOriginalImageFilename());
        File temporaryCompressedImage = File.createTempFile("compressed_", imageParameter.getFileExtension());

        try (FileImageOutputStream output = new FileImageOutputStream(temporaryCompressedImage)) {
            ImageWriter writer = getImageWriter(BaseConstants.UPLOAD_RESOURCES.WEBP);
            BufferedImage resizedImage = resizeImage(imageParameter);

            log.debug("Compress with quality factor: {}", writer.getDefaultWriteParam().getCompressionQuality());
            log.debug("Writing compressed image to `{}`", temporaryCompressedImage.getAbsolutePath());

            writer.setOutput(output);
            writer.write(resizedImage);
            writer.dispose();
        }

        return temporaryCompressedImage;
    }

    private static File jpegLossyCompression(ImageParameterDTO imageParameter) throws IOException {
        log.debug("Compressing image `{}` using `jpegLossyCompression`", imageParameter.getOriginalImageFilename());
        File temporaryCompressedImage = File.createTempFile("compressed_", imageParameter.getFileExtension());

        try (FileImageOutputStream output = new FileImageOutputStream(temporaryCompressedImage)) {
            ImageWriter imageWriter = getImageWriter(imageParameter.getImageType());
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            BufferedImage resizedImage = resizeImage(imageParameter);
            IIOImage iioImage = new IIOImage(resizedImage, null, null);

            log.debug("Compress with quality factor: {}", imageParameter.getQualityCompressionFactor());
            log.debug("Writing compressed image to: `{}`", temporaryCompressedImage.getAbsolutePath());

            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(imageParameter.getQualityCompressionFactor());
            imageWriter.setOutput(output);
            imageWriter.write(null, iioImage, imageWriteParam);
            imageWriter.dispose();
        }

        return temporaryCompressedImage;
    }

    private static BufferedImage resizeImage(ImageParameterDTO imageParameter) {
        int originalImageWidth = imageParameter.getOriginalImageWidth();
        int originalImageHeight = imageParameter.getOriginalImageHeight();
        int newImageWidth = (int) (originalImageWidth * imageParameter.getSizeCompressionFactor());
        int newImageHeight = (int) (originalImageHeight * imageParameter.getSizeCompressionFactor());
        log.debug("Resize from {}x{} to {}x{}", originalImageWidth, originalImageHeight, newImageWidth, newImageHeight);

        BufferedImage originalImage = imageParameter.getBufferedImage();
        BufferedImage resizedImage = new BufferedImage(newImageWidth, newImageHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(originalImage, 0, 0, newImageWidth, newImageHeight, null);
        g.dispose();

        return resizedImage;
    }

    // Helper method to get ImageWriter for specific file type
    private static ImageWriter getImageWriter(String fileType) {
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType("image/" + fileType);

        if (!imageWriters.hasNext())
            imageWriters = ImageIO.getImageWritersByFormatName(fileType);

        if (!imageWriters.hasNext())
            throw new IllegalStateException("Not found ImageWriter for: " + fileType);

        return imageWriters.next();
    }

    public static ImageParameterDTO getImageParameterWithCompressionFactor(MultipartFile image) {
        ImageParameterDTO imageParameterDTO = getImageParameter(image);

        long imageFileSize = imageParameterDTO.getOriginalImageFileSize();
        int imageWidth = imageParameterDTO.getOriginalImageWidth();
        int imageHeight = imageParameterDTO.getOriginalImageHeight();

        imageParameterDTO.setSizeCompressionFactor(getImageSizeCompressionFactor(imageWidth, imageHeight));
        imageParameterDTO.setQualityCompressionFactor(getImageQualityCompressionFactor(imageFileSize));

        return imageParameterDTO;
    }

    public static ImageParameterDTO getImageParameter(MultipartFile image) {
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(image.getInputStream());

            if (bufferedImage == null) {
                log.error("Could not read image: {}. Invalid format!", image.getOriginalFilename());
                return new ImageParameterDTO();
            }
        } catch (IOException e) {
            log.error("Could not read image: {}. I/O error!", image.getOriginalFilename());
            return new ImageParameterDTO();
        }

        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();
        long imageFileSize = image.getSize();
        String imageFileName = Optional.ofNullable(image.getOriginalFilename()).orElse("");
        String defaultImageFormat = BaseConstants.UPLOAD_RESOURCES.DEFAULT_IMAGE_FORMAT;
        String defaultImageType = BaseConstants.UPLOAD_RESOURCES.WEBP;
        String imageFileExtension = "", imageType = "";

        if (!imageFileName.isBlank() && imageFileName.contains(".")) {
            int lastDotIndex = imageFileName.lastIndexOf('.');
            imageFileExtension = imageFileName.substring(lastDotIndex).toLowerCase();
            imageType = imageFileExtension.substring(1).toLowerCase();
        }

        return new ImageParameterDTO.Builder()
                .setImage(image)
                .setBufferedImage(bufferedImage)
                .setOriginalImageWidth(imageWidth)
                .setOriginalImageHeight(imageHeight)
                .setOriginalImageFileSize(imageFileSize)
                .setOriginalImageFilename(image.getOriginalFilename())
                .setFileExtension(imageFileExtension.length() > 1 ? imageFileExtension : defaultImageFormat)
                .setImageType(imageType.length() > 1 ? imageType : defaultImageType)
                .build();
    }

    private static float getImageQualityCompressionFactor(long originalImageFileSize) {
        float qualityCompressionFactor = 1.0f; // Default keep image quality

        if (originalImageFileSize > 5 * 1024 * 1024) { // > 5MB
            qualityCompressionFactor = 0.5f;
        } else if (originalImageFileSize > 2 * 1024 * 1024) { // 2MB - 5MB
            qualityCompressionFactor = 0.6f;
        } else if (originalImageFileSize > 500 * 1024) { // 500KB - 2MB
            qualityCompressionFactor = 0.8f;
        } else if (originalImageFileSize > 300 * 1024) { // 300KB - 500KB
            qualityCompressionFactor = 0.9f;
        }

        return qualityCompressionFactor;
    }

    private static float getImageSizeCompressionFactor(int originalImageWidth, int originalImageHeight) {
        float sizeCompressionFactor = 1.0f; // Default keep image size

        if (originalImageWidth > 4000 || originalImageHeight > 4000) {
            sizeCompressionFactor = 0.5f; // Resize 50%
        } else if (originalImageWidth > 2000 || originalImageHeight > 2000) {
            sizeCompressionFactor = 0.7f; // Resize 70%
        } else if (originalImageWidth > 1200 || originalImageHeight > 1080) {
            sizeCompressionFactor = 0.85f;
        }

        return sizeCompressionFactor;
    }
}
