package com.goodjob.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
}
