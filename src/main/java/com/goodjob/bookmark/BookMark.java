package com.goodjob.bookmark;

import com.goodjob.member.Member;
import com.goodjob.post.Post;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert
@Builder
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne
    @JoinColumn(name = "bookMarkMemId")
    private Member bookMarkMemId;

    @ManyToOne
    @JoinColumn(name = "bookMarkPostId")
    private Post bookMarkPostId;
}
