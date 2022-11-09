package com.goodjob.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * 22.10.19 By.OH
 */
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public Optional<Notice> findOne(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }

    @Override
    public Page<Notice> findNoticeList(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Page<Notice> findNoticeListWithStatus(Pageable pageable, String noticeStatus) {
        return noticeRepository.findAllByNoticeStatus(pageable,noticeStatus);
    }

    @Override
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }


    @Override
    public Notice insertNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    @Override
    public void updateNoticeStatus(Long id) {
        noticeRepository.updateStatus(id);
    }
}
