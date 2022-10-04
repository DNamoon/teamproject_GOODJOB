package com.goodjob.certification;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@Table(name = "certification_name")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class CertificateName {

    @Id
    private String certiName;

    @Column(length = 45, nullable = false)
    private String certiInst;
}
