package com.goodjob.post.postdto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

/**
 * 22.10.26 saveForm 클래스 By.OH
 */
@Data
public class PostInsertDTO {

    private Long id;

    private String postTitle;

    private Long postOccCode;

    private Long postComId;

    private String postContent;

    private String postRecruitNum;

    private Date postStartDate;

    private Date postEndDate;

    private String postGender;

    private String postRegion;

    private List<MultipartFile> postImg;

    private String salary;

    private String postAddress;

    private String postDetailAddress;

    private String ComLoginId;

}