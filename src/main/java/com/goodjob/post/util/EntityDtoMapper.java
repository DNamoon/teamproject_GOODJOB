package com.goodjob.post.util;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.post.Post;
import com.goodjob.post.fileupload.UploadFile;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostCardDTO;
import com.goodjob.post.postdto.PostInsertDTO;
import com.goodjob.post.salary.PostSalary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
                .salaryId(post.getPostSalary().getSalaryId())
                .salaryRange(post.getPostSalary().getSalaryRange())
                .count(post.getPostReadCount())
                .occId(post.getPostOccCode().getOccId())
                .occName(post.getPostOccCode().getOccName())
                .comLoginId(post.getPostComId().getComLoginId())
                .comName(post.getPostComId().getComName())
                .build();
    }
    default PostCardDTO entityToDtoInMain(Post post) {
        Date now = new Date();
        long difDay = (post.getPostEndDate().getTime()-now.getTime())/1000;
        String remainDay = String.valueOf(difDay/ (24*60*60));
         if(remainDay.equals("0")){
             remainDay = "오늘 종료";
         } else{
             remainDay = "D - "+remainDay;
         }
        System.out.println(remainDay);
        return PostCardDTO.builder()
                .id(post.getPostId())
                .title(post.getPostTitle())
                .regionName(post.getPostRegion().getRegName())
                .remainDay(remainDay)
                .salaryRange(post.getPostSalary().getSalaryRange())
                .occName(post.getPostOccCode().getOccName())
                .comName(post.getPostComId().getComName())
                .build();
    }

    // PostDto -> Post (save or update)
    default Post dtoToEntity(PostDTO postDTO, Occupation occ, Company com, Region postRegion, PostSalary postSalary) throws ParseException {
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
                    .postSalary(postSalary)
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
                    .postSalary(postSalary)
                    .postOccCode(occ)
                    .postComId(com)
                    .build();
        }



    }
    default Post dtoToEntityForInsert(PostInsertDTO postInsertDTO, Occupation occ, Company com, Region postRegion, PostSalary postSalary, List<UploadFile> uploadFileList){
        if(postInsertDTO.getId() != null){
            return Post.builder()
                    .postId(postInsertDTO.getId())
                    .postTitle(postInsertDTO.getPostTitle())
                    .postContent(postInsertDTO.getPostContent())
                    .postRecruitNum(postInsertDTO.getPostRecruitNum())
                    .postStartDate(postInsertDTO.getPostStartDate())
                    .postEndDate(postInsertDTO.getPostEndDate())
                    .postGender(postInsertDTO.getPostGender())
                    .postRegion(postRegion)
                    .postSalary(postSalary)
                    .postOccCode(occ)
                    .postComId(com)
                    .postReadCount(0)
                    .postImg(uploadFileList)
                    .postAddress(postInsertDTO.getPostAddress())
                    .postDetailAddress(postInsertDTO.getPostDetailAddress())
                    .build();

        } else {

            return Post.builder()
                    .postTitle(postInsertDTO.getPostTitle())
                    .postContent(postInsertDTO.getPostContent())
                    .postRecruitNum(postInsertDTO.getPostRecruitNum())
                    .postStartDate(postInsertDTO.getPostStartDate())
                    .postEndDate(postInsertDTO.getPostEndDate())
                    .postGender(postInsertDTO.getPostGender())
                    .postRegion(postRegion)
                    .postSalary(postSalary)
                    .postOccCode(occ)
                    .postComId(com)
                    .postReadCount(0)
                    .postImg(uploadFileList)
                    .postAddress(postInsertDTO.getPostAddress())
                    .postDetailAddress(postInsertDTO.getPostDetailAddress())
                    .build();
        }



    }



}
