/**
 * HO - 2022.10.05
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Comdiv {

    @Id
    private String comdivCode;

    @Column
    private String comdivName;

}
