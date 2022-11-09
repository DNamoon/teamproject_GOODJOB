package com.goodjob.post;

import com.goodjob.bookmark.BookMark;
import com.goodjob.company.Company;
import com.goodjob.post.fileupload.UploadFile;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.salary.PostSalary;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postStartDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postEndDate;

    @Column
    private String postGender; // 성별

    @ManyToOne
    @JoinColumn(name = "salaryId")
    private PostSalary postSalary; // 연봉

    @Column
    private int postReadCount; // 조회수

    @ElementCollection
    @CollectionTable(name = "postImg", joinColumns = @JoinColumn(name = "postImgId", referencedColumnName = "postId"))
    private List<UploadFile> postImg;

    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "post_zipCode"))
    @AttributeOverride(name = "address1", column = @Column(name = "post_address"))
    @AttributeOverride(name = "address2", column = @Column(name = "post_address2"))
    @AttributeOverride(name = "etc", column = @Column(name = "post_etc"))
    private Address address;

    @OneToMany(mappedBy = "bookMarkPostId", cascade = CascadeType.ALL)
    private List<BookMark> postBookMark;

    private String postComName;

}
