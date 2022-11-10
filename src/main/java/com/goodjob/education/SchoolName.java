package com.goodjob.education;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@Table(name = "school_name")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class SchoolName {

    @Id
    private String schName;
}
