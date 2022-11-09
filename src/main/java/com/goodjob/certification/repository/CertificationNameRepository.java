package com.goodjob.certification.repository;

import com.goodjob.certification.CertificateName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 박채원 22.10.04 작성
 * 박채원 22.11.06 수정
 */

public interface CertificationNameRepository extends JpaRepository<CertificateName, String> {
    List<CertificateName> findCertificateNameByCertiNameContaining(String keyword);
}
