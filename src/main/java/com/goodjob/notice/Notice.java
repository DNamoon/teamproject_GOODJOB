package com.goodjob.notice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 22.10.19 By.OH
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;

    @Column
    private String noticeTitle;

    @Column(length = 5000)
    private String noticeContent;

    @Column
    private LocalDate noticeDate;

    @Column(columnDefinition = "boolean default 0")
    private String noticeStatus;
}
