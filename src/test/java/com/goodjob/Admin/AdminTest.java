package com.goodjob.Admin;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.apexchart.VisitorStatistics;
import com.goodjob.admin.apexchart.VisitorStatisticsRepository;
import com.goodjob.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    VisitorStatisticsRepository visitorStatisticsRepository;

    @Test
    @Commit
    public void save() {
        Admin admin = new Admin(1L, "admin", "1234", AdminConst.ADMIN);
        adminRepository.save(admin);
    }

    @Test
    @Commit
    public void visitTest() {
//        int dayOfMonth = LocalDate.now().getDayOfMonth();
//        System.out.println("dayOfMonth = " + dayOfMonth);

//        VisitorStatistics visitorStatistics = new VisitorStatistics(LocalDate.of(2022,10,4), 34L);
//        visitorStatisticsRepository.save(visitorStatistics);
//        VisitorStatistics visitorStatistics2 = new VisitorStatistics(LocalDate.of(2022,10,5), 64L);
//        visitorStatisticsRepository.save(visitorStatistics2);
//        VisitorStatistics visitorStatistics3 = new VisitorStatistics(LocalDate.of(2022,10,6), 12L);
//        visitorStatisticsRepository.save(visitorStatistics3);

//        Long longs = visitorStatisticsRepository.sumVisitor();
//        System.out.println("aLong = " + longs);
        visitorStatisticsRepository.updateVisitor(LocalDate.now());

//        LocalDate localDate = LocalDate.now().minusDays(7L);
//        System.out.println("localDateTime = " + localDate);

//        LocalDate startDate = LocalDate.of(2022, 10, 4);
//        LocalDate endDate = LocalDate.of(2022, 10, 6);
//        List<VisitorStatistics> allByXBetween = visitorStatisticsRepository.findAllByXBetween(startDate, endDate);
//        System.out.println("allByXBetween = " + allByXBetween);

//        List<VisitorStatistics> all = visitorStatisticsRepository.findAll();
//        VisitorStatistics visitorStatistics = all.get(0);
//        System.out.println("visitorStatistics = " + visitorStatistics);
    }
}
