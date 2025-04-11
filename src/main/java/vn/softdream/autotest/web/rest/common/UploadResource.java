package vn.softdream.autotest.web.rest.common;

import vn.softdream.autotest.common.FileUtils;
import vn.softdream.autotest.dto.response.BaseResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/common/upload")
public class UploadResource {

    private static final Logger log = LoggerFactory.getLogger(UploadResource.class);
    private final FileUtils fileUtils;

    public UploadResource(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    @PostMapping("/images")
    public BaseResponseDTO saveImages(@RequestParam("images") MultipartFile[] images) {
        log.debug("REST request to save images");
        List<String> filePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageFilePath = fileUtils.autoCompressImageAndSave(image);
            filePaths.add(imageFilePath);
        }

        return BaseResponseDTO.builder().ok(filePaths);
    }
}
