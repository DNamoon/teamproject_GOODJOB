package com.goodjob.admin.service;

import com.goodjob.admin.Admin;
import com.goodjob.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 10.4 By. OH
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin login(String adLoginId, String adPw) {
        return adminRepository.findAdminByAdLoginId(adLoginId).filter(
                m -> m.getAdPw().equals(adPw)).orElse(null);

    }
}
