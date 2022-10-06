package com.goodjob.Admin;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AdminTest {

    @Autowired
    AdminRepository adminRepository;

    @Test
    @Commit
    public void save(){
        Admin admin = new Admin(1L,"admin","1234", AdminConst.ADMIN);
        adminRepository.save(admin);
    }
}
