package com.goodjob.company.repository;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region,String>{

    //search 드롭박스용
    @Query("select r.regName from Region r ")
    List<String> regName();
}
