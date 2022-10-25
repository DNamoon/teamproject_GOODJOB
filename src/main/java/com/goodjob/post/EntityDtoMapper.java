package com.goodjob.post;

import com.goodjob.company.Company;
import com.goodjob.company.dto.CompanyDTO;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.postdto.PostDTO;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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


    // Post  -> PostDto(list용)
    default PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getPostId())
                .title(post.getPostTitle())
                .content(post.getPostContent())
                .recruitNum(post.getPostRecruitNum())
                .startDate(transFormat.format(post.getPostStartDate()))
                .endDate(transFormat.format(post.getPostEndDate()))
                .gender(post.getPostGender())
                .occId(post.getPostOccCode().getOccId())
                .occName(post.getPostOccCode().getOccName())
                .comLoginId(post.getPostComId().getComLoginId())
                .comName(post.getPostComId().getComName())
                .build();
    }
    // PostDto -> Post (save or update)
    default Post dtoToEntity(PostDTO postDTO, Occupation occ, Company com) throws ParseException {
        if(postDTO.getId() != null){
            return Post.builder()
                    .postId(postDTO.getId())
                    .postTitle(postDTO.getTitle())
                    .postContent(postDTO.getContent())
                    .postRecruitNum(postDTO.getRecruitNum())
                    .postStartDate(java.sql.Date.valueOf(postDTO.getStartDate()))
                    .postEndDate(java.sql.Date.valueOf(postDTO.getEndDate()))
                    .postGender(postDTO.getGender())
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
                    .postOccCode(occ)
                    .postComId(com)
                    .build();
        }



    }



}
