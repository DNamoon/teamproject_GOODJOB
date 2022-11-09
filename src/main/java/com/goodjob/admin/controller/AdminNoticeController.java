package com.goodjob.admin.controller;

import com.goodjob.notice.Notice;
import com.goodjob.notice.NoticeDTO;
import com.goodjob.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 22.10.19 공지사항 관련 컨트롤러 By.oh
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    //JPA 페이징 관련 메소드
    @GetMapping({"/{pageNum}",""})
    public String noticeListForm(@PathVariable(required = false) int pageNum, Model model) {
        pageNum = (pageNum == 0) ? 0 : (pageNum - 1);
        Sort sort = Sort.by("noticeId").descending();
        Pageable pageable = PageRequest.of(pageNum, 10, sort);
        Page<Notice> noticeList = noticeService.findNoticeList(pageable);
        model.addAttribute("noticeList", noticeList);
        return "/admin/notice/adminNotice";
    }

    @GetMapping("/insert")
    public String noticeForm() {
        return "/admin/notice/adminNoticeInsertForm";
    }

    @PostMapping("/insert")
    public String noticeInsert(@ModelAttribute NoticeDTO noticeDTO) {
        Notice notice = Notice.builder()
                .noticeTitle(noticeDTO.getNoticeTitle())
                .noticeContent(noticeDTO.getNoticeContent())
                .noticeDate(LocalDate.now())
                .noticeStatus("0")
                .build();

        noticeService.insertNotice(notice);
        return "redirect:/admin/notice/1";
    }

    @GetMapping("/{noticeId}/update")
    public String noticeUpdateForm(@PathVariable Long noticeId, Model model) {
        model.addAttribute("findNotice", noticeService.findOne(noticeId).orElse(null));
        return "/admin/notice/adminNoticeUpdateForm";
    }

    @PostMapping("/{noticeId}/update")
    public String noticeUpdate(@ModelAttribute NoticeDTO noticeDTO, @PathVariable Long noticeId) {

        Notice notice = Notice.builder()
                .noticeId(noticeId)
                .noticeTitle(noticeDTO.getNoticeTitle())
                .noticeContent(noticeDTO.getNoticeContent())
                .noticeDate(LocalDate.now())
                .noticeStatus("0")
                .build();

        noticeService.insertNotice(notice);
        return "redirect:/admin/notice/"+noticeId+"/details";
    }

    @GetMapping("/{noticeId}/details")
    public String noticeDetailForm(@PathVariable Long noticeId, Model model) {
        model.addAttribute("notice", noticeService.findOne(noticeId).get());
        return "/admin/notice/adminNoticeDetails";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteNotice(@Param("noticeId") Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return "/admin/notice/1";
    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public String updateNoticeStatus(@Param("noticeId") Long noticeId) {
        noticeService.updateNoticeStatus(noticeId);
        return "/admin/notice/1";
    }
}
