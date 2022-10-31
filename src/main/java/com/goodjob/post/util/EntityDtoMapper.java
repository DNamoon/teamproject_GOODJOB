package com.goodjob.post.util;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.post.Post;
import com.goodjob.post.fileupload.UploadFile;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.postdto.*;
import com.goodjob.post.salary.PostSalary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface EntityDtoMapper {
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

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
    default PostDetailsDTO entityToDtoForRead(Post post){
        Date now = new Date();
        long difDay = (post.getPostEndDate().getTime()-now.getTime())/1000;
        String remainDay = String.valueOf(difDay/ (24*60*60));
        remainDay = "D - "+remainDay;
        return PostDetailsDTO.builder()
                .postId(post.getPostId())
                .title(post.getPostTitle())
                .regionName(post.getPostRegion().getRegName())
                .content(post.getPostContent())
                .startDate(post.getPostStartDate().toString())
                .endDate(post.getPostStartDate().toString())
                .remainDay(remainDay)
                .postAddress(post.getPostAddress())
                .occName(post.getPostOccCode().getOccName())
                .comName(post.getPostComId().getComName())
                .build();
    }
    default PostCardDTO entityToDtoInMain(Post post) {
        Date now = new Date();
        long difDay = (post.getPostEndDate().getTime()-now.getTime())/1000;
        String remainDay = String.valueOf(difDay/ (24*60*60));
        remainDay = remainDay.equals("0")? "오늘 종료": "D - "+remainDay;
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
    default PostComMyPageDTO entityToDtoInComMyPage(Post post) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(post.getPostEndDate());
        cal.add(Calendar.DATE,1);

        long difDay = (cal.getTime().getTime()-now.getTime())/1000;
        long difDay2 = (now.getTime()-post.getPostStartDate().getTime())/1000;
        String endDateMinusNow = String.valueOf(difDay/ (24*60*60)); // 0보다 크면 모집 종료 전
        String startDateMinusNow = String.valueOf(difDay2/(24*60*60)); // 0보다 작으면 모집 시작전
        String remainDay;
        if(endDateMinusNow.equals("0")){
            remainDay = "(오늘) 모집 종료";
        } else if (endDateMinusNow.contains("-")) {
            remainDay = "(종료됨)";
        } else {
            remainDay = "(D - "+endDateMinusNow+") 모집 종료";
        }
        String[] startDateArr = post.getPostStartDate().toString().split("-");
        String startDate = startDateArr[0]+"년 "+startDateArr[1]+"월 "+startDateArr[2]+"일 ";
        String[] endDateArr = post.getPostEndDate().toString().split("-");
        String endDate = endDateArr[0]+"년 "+endDateArr[1]+"월 "+endDateArr[2]+"일"+remainDay;
        System.out.println(remainDay);
        return PostComMyPageDTO.builder()
                .postId(post.getPostId())
                .title(post.getPostTitle())
                .regionName(post.getPostRegion().getRegName())
                .salaryRange(post.getPostSalary().getSalaryRange())
                .startDate(startDate)
                .endDate(endDate)
                .recruitNum(post.getPostRecruitNum())
                .gender(post.getPostGender())
//                .address(post.getPostAddress())
                .count(post.getPostReadCount())
                .region(post.getPostRegion().getRegName())
                .occName(post.getPostOccCode().getOccName())
                .comName(post.getPostComId().getComName())
                .build();
    }

    // PostDto -> Post (save or update)
//    default Post dtoToEntity(PostDTO postDTO, Occupation occ, Company com, Region postRegion, PostSalary postSalary) throws ParseException {
//        if(postDTO.getId() != null){
//            return Post.builder()
//                    .postId(postDTO.getId())
//                    .postTitle(postDTO.getTitle())
//                    .postContent(postDTO.getContent())
//                    .postRecruitNum(postDTO.getRecruitNum())
//                    .postStartDate(java.sql.Date.valueOf(postDTO.getStartDate()))
//                    .postEndDate(java.sql.Date.valueOf(postDTO.getEndDate()))
//                    .postGender(postDTO.getGender())
//                    .postRegion(postRegion)
//                    .postSalary(postSalary)
//                    .postOccCode(occ)
//                    .postComId(com)
//                    .build();
//
//        } else {
//
//            return Post.builder()
//                    .postTitle(postDTO.getTitle())
//                    .postContent(postDTO.getContent())
//                    .postRecruitNum(postDTO.getRecruitNum())
//                    .postStartDate(java.sql.Date.valueOf(postDTO.getStartDate()))
//                    .postEndDate(java.sql.Date.valueOf(postDTO.getEndDate()))
//                    .postGender(postDTO.getGender())
//                    .postRegion(postRegion)
//                    .postSalary(postSalary)
//                    .postOccCode(occ)
//                    .postComId(com)
//                    .build();
//        }
//
//
//
//    }
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
