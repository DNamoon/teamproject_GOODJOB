package com.goodjob.bookmark.bookmarkdto;

import com.goodjob.bookmark.BookMark;
import com.goodjob.member.Member;
import com.goodjob.post.Post;
import lombok.Data;

@Data
public class BookMarkDTO {

    private Member bookMarkMemId;

    private Post bookMarkPostId;

    public BookMark toEntity() {
        return BookMark.builder()
                .bookMarkPostId(bookMarkPostId)
                .bookMarkMemId(bookMarkMemId)
                .build();
    }
}
