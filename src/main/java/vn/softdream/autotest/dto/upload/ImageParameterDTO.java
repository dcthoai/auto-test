package vn.softdream.autotest.dto.upload;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class ImageParameterDTO {

    private int originalImageWidth;
    private int originalImageHeight;
    private long originalImageFileSize;
    private String originalImageFilename;
    private String fileExtension;
    private String imageType;
    private MultipartFile image;
    private BufferedImage bufferedImage;
    private float sizeCompressionFactor = 1.0f;
    private float qualityCompressionFactor = 1.0f;

    @SuppressWarnings("unused")
    public static class Builder {
        private final ImageParameterDTO instance;

        public Builder() {
            instance = new ImageParameterDTO();
        }

        public Builder setOriginalImageWidth(int width) {
            instance.originalImageWidth = width;
            return this;
        }

        public Builder setOriginalImageHeight(int height) {
            instance.originalImageHeight = height;
            return this;
        }

        public Builder setOriginalImageFileSize(long fileSize) {
            instance.originalImageFileSize = fileSize;
            return this;
        }

        public Builder setOriginalImageFilename(String filename) {
            instance.originalImageFilename = filename;
            return this;
        }

        public Builder setFileExtension(String extension) {
            instance.fileExtension = extension;
            return this;
        }

        public Builder setBufferedImage(BufferedImage image) {
            instance.bufferedImage = image;
            return this;
        }

        public Builder setSizeCompressionFactor(float factor) {
            instance.sizeCompressionFactor = factor;
            return this;
        }

        public Builder setQualityCompressionFactor(float factor) {
            instance.qualityCompressionFactor = factor;
            return this;
        }

        public Builder setImage(MultipartFile image) {
            instance.image = image;
            return this;
        }

        public Builder setImageType(String imageType) {
            instance.imageType = imageType;
            return this;
        }

        public ImageParameterDTO build() {
            return instance;
        }
    }

    public boolean isCompressed() {
        return sizeCompressionFactor >= 1.0f && qualityCompressionFactor >= 1.0f;
    }

    public int getOriginalImageWidth() {
        return originalImageWidth;
    }

    public void setOriginalImageWidth(int originalImageWidth) {
        this.originalImageWidth = originalImageWidth;
    }

    public int getOriginalImageHeight() {
        return originalImageHeight;
    }

    public void setOriginalImageHeight(int originalImageHeight) {
        this.originalImageHeight = originalImageHeight;
    }

    public long getOriginalImageFileSize() {
        return originalImageFileSize;
    }

    public void setOriginalImageFileSize(long originalImageFileSize) {
        this.originalImageFileSize = originalImageFileSize;
    }

    public String getOriginalImageFilename() {
        return originalImageFilename;
    }

    public void setOriginalImageFilename(String originalImageFilename) {
        this.originalImageFilename = originalImageFilename;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String imageFileExtension) {
        this.fileExtension = imageFileExtension;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public float getSizeCompressionFactor() {
        return sizeCompressionFactor;
    }

    public void setSizeCompressionFactor(float sizeCompressionFactor) {
        this.sizeCompressionFactor = sizeCompressionFactor;
    }

    public float getQualityCompressionFactor() {
        return qualityCompressionFactor;
    }

    public void setQualityCompressionFactor(float qualityCompressionFactor) {
        this.qualityCompressionFactor = qualityCompressionFactor;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
