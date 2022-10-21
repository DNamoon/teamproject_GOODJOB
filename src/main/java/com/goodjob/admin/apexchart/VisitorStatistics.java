package com.goodjob.admin.apexchart;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * 10.6 방문자 Entity By.OH
 * JSON형식으로 보낼 때 ApexChart가 지원하는 형식을 맞추기 위해 필드명이 x,y
 */
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VisitorStatistics {

    @Id
    @DateTimeFormat(pattern = "MM-dd",fallbackPatterns = "MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM-dd")
    private LocalDate x; // 방문날짜

    @Column
    private Long y; // 방문자 카운트
}
