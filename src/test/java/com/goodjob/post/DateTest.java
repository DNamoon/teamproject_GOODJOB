package com.goodjob.post;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@Log4j2
public class DateTest {
    @Test
    public void test() throws ParseException {
        Date now = new Date(); //2022-10-26
        String endDate = "2022/10/01";

        Date format1 = new SimpleDateFormat("yyyy/MM/dd").parse(endDate);
        long difSec = (now.getTime() - format1.getTime())/ 1000; // 초 차이
        long difMin = (now.getTime() - format1.getTime())/ 60000; // 분 차이
        long difHor = (now.getTime() - format1.getTime())/ 3600000; // 분 차이
        long difDay = difSec / (24*60*60); //일 차이
        System.out.println("===================================================");
        log.info("Test.............. dateTest........"+difSec+"초");
        // Test.............. dateTest........2197415초
        log.info("Test.............. dateTest........"+difMin+"분");
        // Test.............. dateTest........36623분
        log.info("Test.............. dateTest........"+difHor+"시간");
        // Test.............. dateTest........610시간
        log.info("Test.............. dateTest........"+difDay+"일");
        // Test.............. dateTest........25일
        System.out.println("===================================================");
    }
}
