package com.goodjob.member;

import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memId;

    @Column(unique = true)
    private String memLoginId;

    @OneToOne
    @JoinColumn(name = "memMemdivCode")
    private Memberdiv memMemdivCode;

    @OneToMany(mappedBy = "resumeMemId", cascade = CascadeType.ALL)
    private List<Resume> memResume = new ArrayList<>();

    @Column
    private String memPw;

    @Column
    private String memPhone;

    @Column
    private String memEmail;

    @Column
    private String memName;

    @Column
    @DateTimeFormat(pattern = "yyyy-mm-DD")
    private Date memBirthDate;

    @Column
    private String memAddress;

    @Column
    private String memGender;

    @Column(length = 1)
    private String memTerms;

    public Member(Long memId, String memLoginId, Memberdiv memMemdivCode, String memPw, String memPhone, String memEmail, String memName, Date memBirthDate, String memAddress, String memGender, String memTerms) {
        this.memId = memId;
        this.memLoginId = memLoginId;
        this.memMemdivCode = memMemdivCode;
        this.memPw = memPw;
        this.memPhone = memPhone;
        this.memEmail = memEmail;
        this.memName = memName;
        this.memBirthDate = memBirthDate;
        this.memAddress = memAddress;
        this.memGender = memGender;
        this.memTerms = memTerms;
    }
}
