package com.goodjob.post.occupation.repository;

import com.goodjob.post.occupation.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OccupationRepository extends JpaRepository<Occupation, Long > {
    @Query("select o from Occupation o where o.occName = :occName")
    Occupation findByOccName(String occName);

    //search 드롭박스용
    @Query("select o.occName from Occupation o ")
    List<String> occuName();
}
