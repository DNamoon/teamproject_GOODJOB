package com.goodjob.resume.selfIntroduction;

import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SelfIntroduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selfId;

    @OneToOne
    @JoinColumn(name = "selfResumeId")
    private Resume selfResumeId;

    @Column(length = 5000)
    private String selfInterActivity;

    @Column(length = 5000)
    private String selfLetter;

}
