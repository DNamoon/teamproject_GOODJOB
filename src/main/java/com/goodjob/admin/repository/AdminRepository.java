package com.goodjob.admin.repository;

import com.goodjob.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByAdLoginId(String adLoginId);

}
