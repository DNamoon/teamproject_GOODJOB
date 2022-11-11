package com.goodjob.admin.controller;

import com.goodjob.admin.AdminConst;
import com.goodjob.admin.apexchart.GenderDTO;
import com.goodjob.admin.apexchart.PostStatistics;
import com.goodjob.admin.apexchart.VisitorStatistics;
import com.goodjob.admin.apexchart.VisitorStatisticsRepository;
import com.goodjob.customerInquiry.service.CustomerInquiryService;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.post.repository.PostRepository;
import com.goodjob.status.service.StatusService;
import com.goodjob.status.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private final CustomerInquiryService customerInquiryService;
    private final StatusService statusService;
    private final StatusRepository statusRepository;
    @Value("${editor.img.save.url}")
    private String saveUrl;

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
    public void deletePost(@Param("postId") Long postId) {
        statusRepository.setPostIdNull(postId);
        postRepository.deleteById(postId);

    }

    @PostMapping("/inquiryPost/delete")
    public String deleteInquiryPost(@Param("inquiryPostId") Long inquiryPostId) {
        customerInquiryService.deleteByInquiryPostId(inquiryPostId);
        return "/admin/customerInquiry/1?sort=inquiryPostId";
    }

    @PostMapping("/logout")
    public void adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @PostMapping(value = "/image", produces = "application/json; charset=utf8")
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(saveUrl + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            org.apache.commons.io.FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.append("url", saveUrl + savedFileName);// 저장할 내부 폴더명 + 파일명
            jsonObject.append("responseCode", "success");

        } catch (IOException e) {
            org.apache.commons.io.FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.append("responseCode", "error");
            e.printStackTrace();
        }
        String jsonString = jsonObject.toString();
        return jsonString;
    }

    @GetMapping("/customerInquiry/update")
    public String inquiryPostReply(@Param("id") Long id, Model model,
                                   @Param("inquiryPostAnswer") String inquiryPostAnswer) {
        customerInquiryService.updateInquiryPostWithAnswer(id, inquiryPostAnswer, AdminConst.ADMIN, LocalDateTime.now(), "0");
        return inquiryPostAnswer;
    }
    @GetMapping("/customerInquiry/count")
    public Long inquiryPostCountByUnanswered(){
        return customerInquiryService.countByUnanswered();
    }
}
