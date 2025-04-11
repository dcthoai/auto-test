package vn.softdream.autotest.dto.upload;

import java.io.File;

@SuppressWarnings("unused")
public class ImageDTO {

    private File compressedImage;
    private ImageParameterDTO imageParameterDTO;

    public ImageDTO() {}

    public ImageDTO(File compressedImage, ImageParameterDTO imageParameterDTO) {
        this.compressedImage = compressedImage;
        this.imageParameterDTO = imageParameterDTO;
    }

    public File getCompressedImage() {
        return compressedImage;
    }

    public void setCompressedImage(File compressedImage) {
        this.compressedImage = compressedImage;
    }

    public ImageParameterDTO getImageParameterDTO() {
        return imageParameterDTO;
    }

    public void setImageParameterDTO(ImageParameterDTO imageParameterDTO) {
        this.imageParameterDTO = imageParameterDTO;
    }
}
