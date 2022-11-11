package com.goodjob.status;

import com.goodjob.post.Post;
import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 박채원 22.10.26 수정 - 합불합, 지원날짜 추가
 * 박채원 22.11.02 수정 - 합격결과 출력여부 컬럼 추가
 * 박채원 22.11.05 수정 - 면접장소, 면접날짜 컬럼 추가
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@DynamicInsert
@EntityListeners(value = {AuditingEntityListener.class})
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statId;

    //boolean 타입으로 합불합을 판단하려고 했는데 디폴트값이 false(불합격)라서 바꿈 - cw
    @Column(columnDefinition = "varchar(20) default '미정'")
    private String statPass;

    @Column(nullable = false)
    private Boolean statShow;
    
    @Column
    @CreationTimestamp
    private Date statApplyDate;

    @Column
    private LocalDateTime statInterviewDate;

    @Column
    private String statInterviewPlace;

    @Column(length = 45)
    private String statPostTitle;

    @Column(length = 45)
    private String statPostComName;

    @ManyToOne
    @JoinColumn(name = "statPostId")
    private Post statPostId;

    @ManyToOne
    @JoinColumn(name = "statResumeId")
    private Resume statResumeId;

}
