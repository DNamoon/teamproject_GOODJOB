package com.goodjob.post.occupation.occupationdto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupationDto {
    private Long occId;
    private String occCode;
    private String occName;
}
