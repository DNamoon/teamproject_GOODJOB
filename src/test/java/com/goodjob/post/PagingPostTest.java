package com.goodjob.post;

import com.goodjob.company.Company;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.post.repository.PostRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
public class PagingPostTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private  CompanyRepository companyRepository;

    public PagingPostTest() {
    }

    @Test
    public void paging() {

        PageRequest pageRequest = PageRequest.of(0,5);
        Company com = companyRepository.findById(33L).get();
        Page<Post> page = postRepository.findAllByPostComId(com,pageRequest);
        List<Post> content = page.getContent();


        System.out.println("content: "+content); // 조회된 데이터 객체 리스트
        System.out.println("content.size() :"+content.size()); // article 갯수
        System.out.println("page.getTotalElements() :"+page.getTotalElements()); // 전체 데이터 수
        System.out.println("page.getNumber() :"+page.getNumber()); // 페이지 번호. 시작번호는 0
        System.out.println("page.getTotalPages() :"+page.getTotalPages()); // 페이지 총 갯수
        System.out.println("page.isFirst() :"+page.isFirst()); // 첫 페이지 인가?
        System.out.println("page.hasNext() :"+page.hasNext()); // 다음 페이지가 있는가?
    }

    @Test
    public void pagingSlice(){

        PageRequest pageRequest = PageRequest.of(0,5);
        Company com = companyRepository.findById(33L).get();
        Page<Post> page = postRepository.findAllByPostComId(com,pageRequest);
        List<Post> content = page.getContent();


        System.out.println("content: "+content); // 조회된 데이터 객체 리스트
        System.out.println("content.size() :"+content.size()); // article 갯수
        System.out.println("page.getTotalElements() :"+page.getTotalElements()); // 전체 데이터 수
        System.out.println("page.getNumber() :"+page.getNumber()); // 페이지 번호. 시작번호는 0
        System.out.println("page.getTotalPages() :"+page.getTotalPages()); // 페이지 총 갯수
        System.out.println("page.isFirst() :"+page.isFirst()); // 첫 페이지 인가?
        System.out.println("page.hasNext() :"+page.hasNext()); // 다음 페이지가 있는가?

    }
}
