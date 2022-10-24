package com.goodjob.admin.controller;

import com.goodjob.admin.apexchart.GenderDTO;
import com.goodjob.admin.apexchart.PostStatistics;
import com.goodjob.admin.apexchart.VisitorStatistics;
import com.goodjob.admin.apexchart.VisitorStatisticsRepository;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 10.6 ApexChart관련 Rest방식을 위한 RestController By.OH
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

    private final MemberRepository memberRepository;
    private final VisitorStatisticsRepository vs;
    private final PostRepository postRepository;

    @GetMapping("/genderStatistics")
    public GenderDTO countGender() {
        GenderDTO genderDTO = new GenderDTO();
        Integer female = memberRepository.countByMemGender("여");
        Integer male = memberRepository.countByMemGender("남");
        genderDTO.setFemale(female);
        genderDTO.setMale(male);
        return genderDTO;
    }

    @GetMapping("/visitorStatistics")
    public List<VisitorStatistics> visitorStatistics() {
        List<VisitorStatistics> result = vs.findAllByXBetween(LocalDate.now().minusDays(6L), LocalDate.now());
        return result;
    }

    @GetMapping("/postStatistics")
    public PostStatistics postStatistics() {
        Long allCount = postRepository.count();
        Long endPostCount = postRepository.countAllByPostEndDateBefore(Date.valueOf(LocalDate.now()));

        Long progressPostCount = allCount - endPostCount;

        return new PostStatistics(progressPostCount, endPostCount);
    }

    @GetMapping("/totalMember")
    public Long countTotalMember() {
        return memberRepository.count();
    }

    @GetMapping("/dailyVisitor")
    public Long dailyVisitor() {
        Optional<VisitorStatistics> result = vs.findById(LocalDate.now());
        return result.get().getY();
    }

    @GetMapping("/totalVisitor")
    public Long totalVisitor() {
        return vs.sumVisitor();
    }

    @PostMapping("/deletePost")
    public void deletePost(@Param("postId")Long postId){
        postRepository.deleteById(postId);
    }
    @PostMapping("/logout")
    public void adminLogout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
