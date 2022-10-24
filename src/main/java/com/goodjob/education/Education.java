package com.goodjob.education;

import com.goodjob.resume.Resume;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "resume")
@Getter
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduId;

    @Column
    private Date eduGraduationDate;

    @Column(length = 45)
    private String eduCredit;

    @OneToOne
    @JoinColumn(name = "eduResumeId", nullable = false, unique = true)
    private Resume resume;

    @OneToOne
    @JoinColumn(name = "eduSchName")
    private SchoolName schoolName;

    @OneToOne
    @JoinColumn(name = "eduMajName")
    private MajorName majorName;


}
