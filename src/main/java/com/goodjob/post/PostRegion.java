package com.goodjob.post;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class PostRegion {
    @Id
    private String regCode;
    private String regName;
}
