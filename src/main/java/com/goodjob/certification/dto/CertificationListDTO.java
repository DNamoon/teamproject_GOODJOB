package com.goodjob.certification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;


@Getter
@Setter
@ToString
public class CertificationListDTO {
    //뷰의 자격증 항목을 리스트로 가져오기 위함
    private ArrayList<CertificationDTO> certificationDTOList;

    public ArrayList<CertificationDTO> getCertificationDTOList() {
        return certificationDTOList;
    }


}
