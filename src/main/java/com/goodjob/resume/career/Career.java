package com.goodjob.resume.career;

import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @ManyToOne
    @JoinColumn(name = "careerResumeId")
    private Resume careerResumeId;

    @Column(length = 100)
    private String careerCompanyName;

    @Column(length = 100)
    private String careerPeriod;

    @Column(length = 100)
    private String careerTask;

}
