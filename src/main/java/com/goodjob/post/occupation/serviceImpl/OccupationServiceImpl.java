package com.goodjob.post.occupation.serviceImpl;

import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.occupation.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OccupationServiceImpl implements OccupationService {

    private final OccupationRepository occR;


    @Override
    public List<OccupationDto> getAll() {
        List<Occupation> result = occR.findAll();
        return result.stream().map(this::entityToDto).collect(Collectors.toList());
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

    @Override
    public List<String> searchOccName(){
        return  occR.occuName();
    }
}
