package com.goodjob.admin.admindto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AdminDTO {

    @NotBlank(message = "ID를 입력하세요")
    private String adLoginId;
    @NotBlank(message = "PASSWORD를 입력하세요")
    private String adPw;

}
