package com.goodjob.selfIntroduction;

import com.goodjob.resume.Resume;
import lombok.*;
import javax.persistence.*;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@Table(name = "self_introduction")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "resume")
@Getter
@Builder
public class SelfIntroduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selfId;

    @Column(columnDefinition = "text")
    private String selfInterActivity;

    @Column(columnDefinition = "text not null")
    private String selfLetter;

    @OneToOne
    @JoinColumn(name = "selfResumeId", nullable = false, unique = true)
    private Resume resume;
}
