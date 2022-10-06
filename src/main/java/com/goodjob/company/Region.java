/**
 * 2022.10.05 - HO
 * @Id regCode 타입 Long에서 String으로 변경
 * (지역코드 02 입력시 2로 들어가서)
 *
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

    //타입변환 Long -> String
    @Id
    private String regCode;
    @Column(nullable = false)
    private String regName;

}
