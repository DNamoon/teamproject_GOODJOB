package com.goodjob.status;

import com.goodjob.member.Member;
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

/**
 * 박채원 22.10.26 수정 - 합불합, 지원날짜 추가
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
    
    @Column
    @CreationTimestamp
    private Date statApplyDate;

    @ManyToOne
    @JoinColumn(name = "statPostId")
    private Post statPostId;

    @ManyToOne
    @JoinColumn(name = "statResumeId")
    private Resume statResumeId;

    @ManyToOne
    @JoinColumn(name = "statMemId")
    private Member statMemId;

}
