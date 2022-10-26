package com.goodjob.post.util;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostMainCardDTO;
import com.goodjob.post.salary.Salary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface EntityDtoMapper {
//    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    //    Date to = transFormat.parse(from);
    //    String to = transFormat.format(from);



    // Occupation -> OccupationDto
    default OccupationDto entityToDto(Occupation occ){
        return OccupationDto.builder()
                .occId(occ.getOccId())
                .occCode(occ.getOccCode())
                .occName(occ.getOccName())
                .build();
    }
    // OccupationDto -> Occupation
    default Occupation dtoToEntity(OccupationDto occDto){
        if(occDto.getOccId()!=null){
            return  Occupation.builder()
                    .occCode(occDto.getOccCode())
                    .occName(occDto.getOccName())
                    .build();

        } else {
            return Occupation.builder()
                    .occId(occDto.getOccId())
                    .occCode(occDto.getOccCode())
                    .occName(occDto.getOccName())
                    .build();
        }

    }
//    default CompanyDTO entityToDto(Company com){
//        return CompanyDTO.builder()
//                .comLoginId(com.getComLoginId())
//                .comRegCode(com.getComRegCode().getRegCode())
//                .comComdivCode(com.getComComdivCode().getComdivCode())
//                .comName(com.getComName())
//                .comPhone(com.getComPhone())
//                .comAddress1(com.getComAddress())
//                .comInfo(com.getComInfo())
//                .comEmail1(com.getComEmail())
//                .build();
//    }
    // Company -> CompanyDto  <<<<< 채워넣어야한다.


    // Post  -> PostDto(전체 List 용)
    default PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getPostId())
                .title(post.getPostTitle())
                .content(post.getPostContent())
                .recruitNum(post.getPostRecruitNum())
                .startDate(transFormat.format(post.getPostStartDate()))
                .endDate(transFormat.format(post.getPostEndDate()))
                .gender(post.getPostGender())
                .regionId(post.getPostRegion().getRegName())
                .regionName(post.getPostRegion().getRegName())
                .salaryId(post.getSalary().getSalaryId())
                .salaryRange(post.getSalary().getSalaryRange())
                .count(post.getCount())
                .occId(post.getPostOccCode().getOccId())
                .occName(post.getPostOccCode().getOccName())
                .comLoginId(post.getPostComId().getComLoginId())
                .comName(post.getPostComId().getComName())
                .build();
    }
    default PostMainCardDTO entityToDtoInMain(Post post) {

        long difDay = (post.getPostEndDate().getTime()-post.getPostStartDate().getTime())/1000;
        long remainDay = difDay/ (24*60*60);
        return PostMainCardDTO.builder()
                .id(post.getPostId())
                .title(post.getPostTitle())
//                .startDate(transFormat.format(post.getPostStartDate()))
//                .endDate(transFormat.format(post.getPostEndDate()))
                .remainDay(String.valueOf(remainDay))
                .regionName(post.getPostRegion().getRegName())
                .salaryId(post.getSalary().getSalaryId())
                .salaryRange(post.getSalary().getSalaryRange())
                .occName(post.getPostOccCode().getOccName())
                .comName(post.getPostComId().getComName())
                .build();
    }

    // PostDto -> Post (save or update)
    default Post dtoToEntity(PostDTO postDTO, Occupation occ, Company com, Region postRegion,Salary salary) throws ParseException {
        if(postDTO.getId() != null){
            return Post.builder()
                    .postId(postDTO.getId())
                    .postTitle(postDTO.getTitle())
                    .postContent(postDTO.getContent())
                    .postRecruitNum(postDTO.getRecruitNum())
                    .postStartDate(java.sql.Date.valueOf(postDTO.getStartDate()))
                    .postEndDate(java.sql.Date.valueOf(postDTO.getEndDate()))
                    .postGender(postDTO.getGender())
                    .postRegion(postRegion)
                    .salary(salary)
                    .postOccCode(occ)
                    .postComId(com)
                    .build();

        } else {

            return Post.builder()
                    .postTitle(postDTO.getTitle())
                    .postContent(postDTO.getContent())
                    .postRecruitNum(postDTO.getRecruitNum())
                    .postStartDate(java.sql.Date.valueOf(postDTO.getStartDate()))
                    .postEndDate(java.sql.Date.valueOf(postDTO.getEndDate()))
                    .postGender(postDTO.getGender())
                    .postRegion(postRegion)
                    .salary(salary)
                    .postOccCode(occ)
                    .postComId(com)
                    .build();
        }



    }



}
