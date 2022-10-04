package com.goodjob.status;

import com.goodjob.post.Post;
import com.goodjob.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statId;

    @ManyToOne
    @JoinColumn(name = "statPostId")
    private Post statPostId;

    @ManyToOne
    @JoinColumn(name = "statResumeId")
    private Resume statResumeId;

}
