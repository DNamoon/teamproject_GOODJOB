package com.goodjob.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 22.10.19 공지사항 관련 컨트롤러 By.oh
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customerInquiry/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/{pageNum}")
    public String noticeListForm(@PathVariable int pageNum, Model model) {
        Sort sort = Sort.by("noticeId").descending();
        Pageable pageable = PageRequest.of(pageNum-1, 10, sort);
        Page<Notice> noticeListWithStatus = noticeService.findNoticeListWithStatus(pageable, "0");
        model.addAttribute("noticeList", noticeListWithStatus);
        return "/customerInquiry/customerInquiryNotice";
    }
    @GetMapping("/detail")
    @ResponseBody
    public String loadNoticeContent(@RequestParam("noticeId")Long noticeId){
        return noticeService.findOne(noticeId).orElse(null).getNoticeContent();
    }
}
