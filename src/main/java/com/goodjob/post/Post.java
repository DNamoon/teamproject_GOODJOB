package com.goodjob.post;

import com.goodjob.company.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
