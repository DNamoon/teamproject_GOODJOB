package com.goodjob.post.util;

import com.goodjob.company.Company;
import com.goodjob.company.dto.CompanyDTO;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.postdto.PostBbsDto;
import com.goodjob.post.postdto.PostSaveDto;

public interface EntityDtoMapper {

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
    default CompanyDTO entityToDto(Company com){
        return CompanyDTO.builder()
                .comLoginId(com.getComLoginId())
                .comRegCode(com.getComRegCode().getRegCode())
                .comComdivCode(com.getComComdivCode().getComdivCode())
                .comName(com.getComName())
                .comPhone(com.getComPhone())
                .comAddress1(com.getComAddress())
                .comInfo(com.getComInfo())
                .comEmail1(com.getComEmail())
                .build();
    }
    // Company -> CompanyDto  <<<<< 채워넣어야한다.


    // Post  -> PostDto(list용)
    default PostBbsDto entityToDto(Post post) {
        return PostBbsDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
//                .postContent(post.getPostContent())
                .postRecruitNum(post.getPostRecruitNum())
                .postStartDate(post.getPostStartDate())
                .postEndDate(post.getPostEndDate())
                .postGender(post.getPostGender())
                .occId(post.getPostOccCode().getOccId())
                .occName(post.getPostOccCode().getOccName())
                .comLoginId(post.getPostComId().getComLoginId())
                .comName(post.getPostComId().getComName())
                .state(post.getState())
                .build();
    }
    // PostDto -> Post (save or update)
    default Post dtoToEntity(PostSaveDto postSaveDto, Occupation occ, Company com){
        if(postSaveDto.getPostId() != null){
            return Post.builder()
                    .postId(postSaveDto.getPostId())
                    .postTitle(postSaveDto.getPostTitle())
                    .postContent(postSaveDto.getPostContent())
                    .postRecruitNum(postSaveDto.getPostRecruitNum())
                    .postStartDate(postSaveDto.getPostStartDate())
                    .postEndDate(postSaveDto.getPostEndDate())
                    .postGender(postSaveDto.getPostGender())
                    .postOccCode(occ)
                    .postComId(com)
                    .build();

        } else {

            return Post.builder()
                    .postTitle(postSaveDto.getPostTitle())
                    .postContent(postSaveDto.getPostContent())
                    .postRecruitNum(postSaveDto.getPostRecruitNum())
                    .postStartDate(postSaveDto.getPostStartDate())
                    .postEndDate(postSaveDto.getPostEndDate())
                    .postGender(postSaveDto.getPostGender())
                    .postOccCode(occ)
                    .postComId(com)
                    .build();
        }



    }



}
