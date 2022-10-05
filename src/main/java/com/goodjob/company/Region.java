/**
 * 2022.10.05 - HO
 * 라인 20 @Builder 추가 - CompanyDTO클래스 toEntity메서드에 사용
 */
package com.goodjob.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Region {

    @Id
    private String regCode;
    @Column(nullable = false)
    private String regName;

}
