package com.goodjob.member;

import com.goodjob.bookmark.BookMark;
import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member")
@Builder
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memId;

    @Column(unique = true, columnDefinition = "varbinary(128)")
    private String memLoginId;

//    @OneToMany(mappedBy = "resumeMemId", cascade = CascadeType.ALL)
//    private List<Resume> memResume = new ArrayList<>();
    // 오성훈 22.10.30
    @OneToMany(mappedBy = "inquiryPostMemberId", cascade = CascadeType.ALL)
    private List<CustomerInquiryPost> customerInquiryPosts = new ArrayList<>();

    @Column
    private String memPw;

    @Column
    private String memPhone;

    @Column(unique = true, columnDefinition = "varbinary(128)")
    private String memEmail;

    @Column(columnDefinition = "varbinary(128)")
    private String memName;

    @Column
    private Date memBirthDate;

    @Column
    private String memAddress;

    @Column
    private String memGender;

    @Column(length = 2)
    private String memTerms;

    @OneToMany(mappedBy = "bookMarkMemId", cascade = CascadeType.ALL)
    private List<BookMark> memberBookMark;

    /**
     * 비밀번호 변경
     **/
    public void updatePassword(String password) {
        this.memPw = password;
    }

}
