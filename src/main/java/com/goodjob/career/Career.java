package com.goodjob.career;

import com.goodjob.resume.Resume;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.sql.Date;


/**
 * 박채원 22.10.02 작성
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "resume")
@Getter
@DynamicInsert
@Builder
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @Column(length = 45)
    private String careerCompanyName;

    @Column(columnDefinition = "date default '2000-01-01'")
    private Date careerRetireDate;

    @Column(columnDefinition = "date default '2000-01-01'")
    private Date careerJoinedDate;

    @Column(length = 45)
    private String careerTask;

    @ManyToOne
    @JoinColumn(name = "careerResumeId", nullable = false)
    private Resume resume;
}
