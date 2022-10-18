package com.goodjob.post;

import com.goodjob.company.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String postTitle;

    @ManyToOne
    @JoinColumn(name = "postOccCode")
    private Occupation postOccCode;

    @ManyToOne
    @JoinColumn(name = "post_com_id")
    private Company postComId;

    @Column(length = 5000)
    private String postContent;

    @Column
    private String postRecruitNum;

    @Column
    private Date postStartDate;

    @Column
    private Date postEndDate;

    @Column
    private String postGender;

    // 10.7 더미 데이터 생성을 위한 임시 생성자. By.OH
    public Post(String postTitle, Occupation postOccCode, Company postComId, String postContent, String postRecruitNum, Date postStartDate, Date postEndDate, String postGender) {
        this.postTitle = postTitle;
        this.postOccCode = postOccCode;
        this.postComId = postComId;
        this.postContent = postContent;
        this.postRecruitNum = postRecruitNum;
        this.postStartDate = postStartDate;
        this.postEndDate = postEndDate;
        this.postGender = postGender;
    }
}
