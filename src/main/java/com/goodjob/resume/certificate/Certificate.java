package com.goodjob.resume.certificate;

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
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certiId;

    @OneToOne
    @JoinColumn(name = "certiCertiName")
    private CertificateName certiCertiName;

    @ManyToOne
    @JoinColumn(name = "certiResumeId")
    private Resume certiResumeId;

    @Column
    private Date certiGetDate;

    @Column
    private String certiScore;

}
