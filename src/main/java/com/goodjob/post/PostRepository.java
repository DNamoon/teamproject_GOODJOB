package com.goodjob.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.sql.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 10.7 기간 종료된 공고 개수 조회 by.OH
    Long countAllByPostEndDateBefore(Date Date);

    // 10.7 By.OH
    List<Post> findAllByPostIdBetweenOrderByPostIdDesc(Long starNum, Long endNum);

//    @Transactional
//    @Query(value = "select a from Post a join Company b on a.postComId.comId=b.comId order by a.postId desc")
//    List<Post> findPostList();

}
