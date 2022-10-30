package com.goodjob.admin;

import com.goodjob.admin.apexchart.VisitorStatistics;
import com.goodjob.admin.apexchart.VisitorStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.time.LocalDate;

/**
 * 10.6 세션 리스너 설정클래스 By.OH
 */
@WebListener
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SessionConfig implements HttpSessionListener, HttpSessionAttributeListener {

    private final VisitorStatisticsRepository vs;

    // 세션이 생성될 때 호출.
    @Override
    public void sessionCreated(HttpSessionEvent se) {

        boolean existsByToday = vs.existsByX(LocalDate.now());
        if (existsByToday == false) {
            VisitorStatistics visitorStatistics = new VisitorStatistics(LocalDate.now(), 1L);
            vs.save(visitorStatistics);
        } else {
            vs.updateVisitor(LocalDate.now());
        }
    }

    // 세션이 지워지면 호출
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
    }

    // 세션이 만료되면 호출
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
