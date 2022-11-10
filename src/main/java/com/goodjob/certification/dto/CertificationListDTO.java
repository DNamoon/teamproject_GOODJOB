package com.goodjob.certification.dto;

import lombok.*;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CertificationListDTO {
    //뷰의 자격증 항목을 리스트로 가져오기 위함
    private ArrayList<CertificationDTO> certificationDTOList;

    public ArrayList<CertificationDTO> getCertificationDTOList() {
        return certificationDTOList;
    }


}
