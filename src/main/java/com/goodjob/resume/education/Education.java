package com.goodjob.resume.education;

import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduId;

    @OneToOne
    @JoinColumn(name = "eduResumeId")
    private Resume eduResumeId;

    @OneToOne
    @JoinColumn(name = "eduSchName")
    private SchoolName eduSchName;

    @OneToOne
    @JoinColumn(name = "eduMajName")
    private MajorName eduMajName;

    @Column
    private Date eduGraduationDate;

    @Column
    private String eduCredit;

}
