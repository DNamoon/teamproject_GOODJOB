package com.goodjob.post.util;

import com.goodjob.company.Company;
import com.goodjob.post.Address;
import com.goodjob.post.Post;
import com.goodjob.post.fileupload.UploadFile;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.postdto.*;
import com.goodjob.post.salary.PostSalary;
import org.springframework.web.util.HtmlUtils;

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
    default PostDetailsDTO entityToDtoForRead(Post post){
        Date now = new Date();
        long difDay = (post.getPostEndDate().getTime()-now.getTime())/1000;
        String remainDay = String.valueOf(difDay/ (24*60*60));
        remainDay = "D - "+remainDay;
        return PostDetailsDTO.builder()
                .postId(post.getPostId())
                .title(HtmlUtils.htmlUnescape(post.getPostTitle()))
                .content(HtmlUtils.htmlUnescape(post.getPostContent()))
                .startDate(post.getPostStartDate().toString())
                .endDate(post.getPostEndDate().toString())
                .remainDay(remainDay)
                .salary(post.getPostSalary().getSalaryRange())
                .recruitNum(HtmlUtils.htmlUnescape(post.getPostRecruitNum()))
                .postAddress1(HtmlUtils.htmlUnescape(post.getAddress().getAddress1()))
                .postAddress2(HtmlUtils.htmlUnescape(post.getAddress().getAddress2()))
                .occName(post.getPostOccCode().getOccName())
                .comName(post.getPostComId().getComName())
                .build();
    }
    default PostCardDTO entityToDtoInMain(Post post) {
        Date now = new Date();
        long difDay = (post.getPostEndDate().getTime()-now.getTime())/1000;
        String remainDay = String.valueOf(difDay/ (24*60*60));
        remainDay = remainDay.equals("0")? "오늘 종료": "D - "+remainDay;
        return PostCardDTO.builder()
                .id(post.getPostId())
                .title(HtmlUtils.htmlUnescape(post.getPostTitle()))
                .regionName(post.getAddress().getAddress1())
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
        return PostComMyPageDTO.builder()
                .postId(post.getPostId())
                .title(HtmlUtils.htmlUnescape(post.getPostTitle()))
                .salaryRange(post.getPostSalary().getSalaryRange())
                .startDate(startDate)
                .endDate(endDate)
                .recruitNum(HtmlUtils.htmlUnescape(post.getPostRecruitNum()))
                .gender(HtmlUtils.htmlUnescape(post.getPostGender()))
                .address(HtmlUtils.htmlUnescape(post.getAddress().getAddress1()))
                .count(post.getPostReadCount())
                .occName(post.getPostOccCode().getOccName())
                .comName(post.getPostComId().getComName())
                .build();
    }

    default PostInsertDTO entityToDtoForUpdate(Post post){
        return PostInsertDTO.builder()
                .id(post.getPostId())
                .postTitle(HtmlUtils.htmlEscape(post.getPostTitle()))
                .postOccCode(post.getPostOccCode().getOccId())
                .postRecruitNum(HtmlUtils.htmlEscape(post.getPostRecruitNum()))
                .postGender(HtmlUtils.htmlEscape(post.getPostGender()))
                .postStartDate(post.getPostStartDate())
                .postEndDate(post.getPostEndDate())
//                .postImg(post.getPostImg())
                .postAddress(HtmlUtils.htmlEscape(post.getAddress().getAddress1()))
                .postDetailAddress(HtmlUtils.htmlEscape(post.getAddress().getAddress2()))
                .postcode(post.getAddress().getZipCode())
                .etc(HtmlUtils.htmlEscape(post.getAddress().getEtc()))
                .postSalaryId(post.getPostSalary().getSalaryId())
                .postContent(HtmlUtils.htmlEscape(post.getPostContent()))
                .build();
    }
    default Post dtoToEntityForInsert(PostInsertDTO postInsertDTO, Occupation occ, Company com, PostSalary postSalary, List<UploadFile> uploadFileList,Address address){

        if(postInsertDTO.getId() != null){
            return Post.builder()
                    .postId(postInsertDTO.getId())
                    .postTitle(HtmlUtils.htmlEscape(postInsertDTO.getPostTitle()))
                    .postContent(HtmlUtils.htmlEscape(postInsertDTO.getPostContent()))
                    .postRecruitNum(HtmlUtils.htmlEscape(postInsertDTO.getPostRecruitNum()))
                    .postStartDate(postInsertDTO.getPostStartDate())
                    .postEndDate(postInsertDTO.getPostEndDate())
                    .postGender(HtmlUtils.htmlEscape(postInsertDTO.getPostGender()))
                    .postSalary(postSalary)
                    .postOccCode(occ)
                    .postComId(com)
                    .postReadCount(0)
                    .postImg(uploadFileList)
                    .address(address)
                    .build();

        } else {

            return Post.builder()
                    .postTitle(HtmlUtils.htmlEscape(postInsertDTO.getPostTitle()))
                    .postContent(HtmlUtils.htmlEscape(postInsertDTO.getPostContent()))
                    .postRecruitNum(HtmlUtils.htmlEscape(postInsertDTO.getPostRecruitNum()))
                    .postStartDate(postInsertDTO.getPostStartDate())
                    .postEndDate(postInsertDTO.getPostEndDate())
                    .postGender(HtmlUtils.htmlEscape(postInsertDTO.getPostGender()))
                    .postSalary(postSalary)
                    .postOccCode(occ)
                    .postComId(com)
                    .postReadCount(0)
                    .postImg(uploadFileList)
                    .address(address)
                    .build();
        }



    }



}
