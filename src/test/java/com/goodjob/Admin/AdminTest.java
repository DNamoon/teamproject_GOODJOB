package com.goodjob.Admin;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTest {

    @Autowired
    AdminRepository adminRepository;

    @Test
    public void save(){
        Admin admin = new Admin(1L,"test","1234", AdminConst.ADMIN);
        adminRepository.save(admin);
    }
}
