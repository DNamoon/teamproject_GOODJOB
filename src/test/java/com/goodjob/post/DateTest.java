package com.goodjob.post;

import com.goodjob.post.repository.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@Log4j2
@Transactional
public class DateTest {
    private final PostRepository postRepository;
    @Autowired
    public DateTest(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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

        LocalDate curDate = LocalDate.now(); // 2020-08-30
        LocalDate targetDate = LocalDate.of(2020, 8, 30); // 2020-08-30

        LocalTime curTime = LocalTime.now(); // 22:09.02.831
        LocalTime targetTime = LocalTime.of(22, 8, 30); // 22:08:30 인자를 4개까지 쓸 수 있는데 nano 시간

        LocalDateTime curDateTime = LocalDateTime.now();

        LocalDate curDate1 = LocalDate.now();
        LocalTime curTime1 = LocalTime.now();
        LocalDateTime targetDateTime = LocalDateTime.of(curDate, curTime);

        System.out.println("===================================================");
        System.out.println(curDateTime); // 2020-08-30T22:12:43.946
        System.out.println(targetDateTime); // 2020-08-30T22:12:43.946
        System.out.println(curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


    }
    @Test
    public void test2(){
        System.out.println("=================================================================");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFromDb = postRepository.getById(1L).getPostEndDate(); // 2022-10-30
        Date now = new Date(); // Mon Oct 31 12:38:45 KST 2022
        long difDay = (dateFromDb.getTime()-now.getTime())/1000;
        String difStr = String.valueOf(difDay/ (24*60*60));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFromDb);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(now);
        // Date 날짜 밀리 세컨드로 변환
        // Date Calendar 를 활용해서 년일월 더하기
        // Calendar 를 Date 타입 변환 후 밀리 세컨드로 변환하기.

        // Date 특정 포맷일 경우 시간 비교는 어떻게 되나?
        log.info("dateFromDb : "+dateFromDb); // 2022-10-15
        log.info("dateFromDb.getTime() :"+dateFromDb.getTime()); // 1665759600000
        log.info("now : "+now); // Mon Oct 31 12:44:28 KST 2022
        log.info("now.getTime() : "+now.getTime()); // 1667187868132
        log.info(dateFromDb.getTime()-now.getTime()); // -1428462878
        log.info(now.getTime()-dateFromDb.getTime()); // 1428462878

        log.info("calendar.toString() : "+calendar.getTime());
        log.info("cal2.getTime() : "+cal2.getTime());
        log.info("format.format(calendar.getTime()) : "+format.format(calendar.getTime())); // 2022-10-30
        calendar.add(Calendar.DATE, 1);
        log.info("format.format(calendar.getTime()) + 1 : "+format.format(calendar.getTime())); // 2022-10-31
    }
}

// date -> cal 로 변환 후 날짜 더하기나 뺴기
// cal -> date -> getTime() 로 변환 후 계산하기
//
