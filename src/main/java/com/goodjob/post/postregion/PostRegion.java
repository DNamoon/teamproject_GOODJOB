package com.goodjob.post.postregion;
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
