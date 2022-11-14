package com.goodjob.post.fileupload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Embeddable
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile() {
    }
}
