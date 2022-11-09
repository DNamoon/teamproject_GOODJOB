package com.goodjob.certification;

import com.goodjob.resume.Resume;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Date;

/**
 *박채원 22.10.02 작성
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "resume")
@Getter
@DynamicInsert
@Builder
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certiId;

    @Column(columnDefinition = "date default '2000-01-01'")
    private Date certiGetDate;

    @Column(length = 45)
    private String certiScore;

    @ManyToOne
    @JoinColumn(name = "certiResumeId", nullable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "certiCertiName", nullable = false)
    private CertificateName certificateName;
}
