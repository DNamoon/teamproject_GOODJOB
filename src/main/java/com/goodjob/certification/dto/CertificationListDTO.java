package com.goodjob.certification.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JsonParser;


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

//    public void setCertificationDTOList(ArrayList<CertificationDTO> certificationDTOList) {
//        JSONObJECT
//       certificationDTOList = new ArrayList<>();
//        JsonParser parser = new JSONParser();
//        JSONArray arr =  (JSONArray) parser.parse(param);
//        certificationDTOList = arr;
//        this.certificationDTOList = certificationDTOList;
//    }

}
