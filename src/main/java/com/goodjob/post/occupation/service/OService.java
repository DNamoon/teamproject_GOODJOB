package com.goodjob.post.occupation.service;

import com.goodjob.post.EntityDtoMapper;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;

import java.util.List;

public interface OService extends EntityDtoMapper {

//    ResultDto<Occupation, OccupationDto> getAll();

    List<OccupationDto> getAll();

    OccupationDto get(String occName);

    void save(OccupationDto occupationDto);
    void delete(OccupationDto occupationDto);

//    default OccupationDto entityToDto(Occupation occ){
//        return OccupationDto.builder()
//            .occId(occ.getOccCode())
//            .occName(occ.getOccName())
//            .build();
//    }

}
