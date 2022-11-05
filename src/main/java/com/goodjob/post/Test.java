package com.goodjob.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@PostInsertForm
public class Test {
    private List<MultipartFile> files;

    String str;
    TestIn tn;

    @Data
    public static class TestIn{
        private String str2;
        private int in;
    }
}