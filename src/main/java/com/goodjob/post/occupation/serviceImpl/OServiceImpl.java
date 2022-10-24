package com.goodjob.post.occupation.serviceImpl;

import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.occupation.service.OService;
import com.goodjob.post.util.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OServiceImpl implements OService {

    private final OccupationRepository occR;


    @Override
    public ResultDto<Occupation, OccupationDto> getAll() {
        List<Occupation> result = occR.findAll();
        Function<Occupation, OccupationDto> fn = (this::entityToDto);
        return new ResultDto<>(result,fn);
    }
    @Override
    public OccupationDto get(String occName){
        return entityToDto(occR.findByOccName(occName));
    }

    @Override
    public void save(OccupationDto occupationDto){
        Occupation occ = dtoToEntity(occupationDto);
        occR.save(occ);
    }

    @Override
    public void delete(OccupationDto occupationDto) {
        if(!(occupationDto.getOccCode()==null) ){
            occR.deleteById(occupationDto.getOccId());
        } else {
            occR.deleteById(occR.findByOccName(occupationDto.getOccName()).getOccId());
        }
    }

}
