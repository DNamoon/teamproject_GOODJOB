package com.goodjob.post;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class PostServiceTest {
//    @Autowired
//    private PService pService;
//    @Test
//    // PageRequestDTO가 주어질 때 PageResultDTO를 가져올 수 있는지 확인.
//    public void testList(){
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
//        PageResultDTO<Post, PostBbsDto> pageResultDTO = pService.getList(pageRequestDTO);
//
//        System.out.println("PREV:"+pageResultDTO.isPrev());
//        System.out.println("NEXT:"+pageResultDTO.isNext());
//        System.out.println("TOTAL PAGE:"+pageResultDTO.getTotalPage());
//        System.out.println("CUR PAGE:"+pageResultDTO.getPage());
//
//        for(PostBbsDto postBbsDto : pageResultDTO.getDtoList()){
//            System.out.println(postBbsDto);
//        }
//        System.out.println("======================================");
//        pageResultDTO.getPageList().forEach(System.out::println);
//    }
//
//
//}
