/**
 * HO - 2022.10.05
 * 서비스 클래스
 * 기업회원 회원가입 메서드
 * + 2022.10.06 회원가입시 아이디 중복확인 메서드
 *
 * +2022.10.17
 * 로그인 폼 기업/개인 통일 위해 로그인, 비밀번호 창 name 통일. 필드도 loginId,pw 통일(38라인 변경)
 */
package com.goodjob.company.service;

import com.goodjob.company.Comdiv;
import com.goodjob.company.Company;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.company.dto.CompanyDTO;
import com.goodjob.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CompanyService {
    private final CompanyRepository companyRepository;

    //22.10.09 - 비밀번호 암호화를 위해 추가
    private final PasswordEncoder passwordEncoder;

    private final PostRepository postRepository;

    //비밀번호 변경
    public void changePw(CompanyDTO companyDTO,String comLoginId){
        companyDTO.setPw(passwordEncoder.encode(companyDTO.getPw()));
        Company company = companyDTO.toEntityForFindId();
        companyRepository.updatePassword(company.getComPw(),comLoginId);

    }

    //22.10.29 아이디 찾기
    public String findId(CompanyDTO companyDTO){

        Company company1 = companyDTO.toEntityForFindId();
        Long num = companyRepository.countByComName(company1.getComName());
        if(num == 0) {
            return "fail";
        } else {
            Optional<Company> company = companyRepository.findByComName(company1.getComName());
            return company.get().getComLoginId();
        }

    }


    //기업회원가입정보 DB에 저장하는 메서드
    @Transactional
    public Long createCompanyUser(CompanyDTO companyDTO) {
        //22.10.09 - 비밀번호 암호화를 위해 추가
        //companyDTO를 가져와서 여기서 비밀번호를 암호화하는 방법.
        //ho - 22.10.17 getcomPw1 -> getPw (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일)
        companyDTO.setPw(passwordEncoder.encode(companyDTO.getPw()));

        Company newCompanyUser = companyDTO.toEntity();
        companyRepository.save(newCompanyUser);
        return newCompanyUser.getComId();
    }

    //아이디 중복확인 메서드
    public int checkId2(String comLoginId) throws Exception {
        return companyRepository.checkId2(comLoginId);
    }

    public Optional<Company> loginIdCheck(String comLoginId) {
        return companyRepository.findByComLoginId(comLoginId);
    }

    public CompanyDTO entityToDTO2(Company company) {
        String comLoginId = company.getComLoginId();
        String comName = company.getComName();
        String comBusiNum = company.getComBusiNum();
        String comPhone = company.getComPhone();
        Comdiv comComdivCode = company.getComComdivCode();

        String comEmail = company.getComEmail();
        String[] email = comEmail.split("@");

        String comAddress = company.getComAddress();
        String[] address = comAddress.split("@");

        String comInfo = company.getComInfo();

        /** 2022.10.25 - 주소 4의 값 없을 때 보여줄 때 에러 발생 -> null일때  "null"을 DB에 넣기로 함. */

        if (address.length == 3) {//주소3 O, 주소4 X
            String[] newAddress = Arrays.copyOf(address, address.length + 1);
            newAddress[address.length] = "null";
            return getCompanyDTO(comLoginId, comName, comBusiNum, comPhone, comComdivCode, email, comInfo, newAddress);
        } else if (address.length == 2) {  //주소3 X, 주소4 X
            String[] newAddress = Arrays.copyOf(address, address.length + 2);
            newAddress[address.length] = "null";
            newAddress[address.length + 1] = "null";
            return getCompanyDTO(comLoginId, comName, comBusiNum, comPhone, comComdivCode, email, comInfo, newAddress);
        } else {  //주소1,2,3,4 다 존재하거나 주소 4가 존재할 때
            return getCompanyDTO(comLoginId, comName, comBusiNum, comPhone, comComdivCode, email, comInfo, address);

        }
    }

    /** 2022.10.25 - 주소 4의 값 없을 때 보여줄 때 에러 발생 -> null일때  "null"을 DB에 넣기로 함.
     * 메서드 추가 */
    private CompanyDTO getCompanyDTO(String comLoginId, String comName, String comBusiNum, String comPhone, Comdiv comComdivCode, String[] email, String comInfo, String[] address) {
        CompanyDTO dto = CompanyDTO.builder()
                .loginId(comLoginId)
                .comName(comName)
                .comBusiNum(comBusiNum)
                .comPhone(comPhone)
                .comComdivCode(comComdivCode.getComdivCode())
                .comComdivName(comComdivCode.getComdivName())
                .comEmail1(email[0])
                .comEmail2(email[1])
                .comAddress1(address[0])
                .comAddress2(address[1])
                .comAddress3(address[2])
                .comAddress4(address[3])
                .comInfo(comInfo)
                .build();

        return dto;
    }

    //22.10.18 - ho 기업회원정보 수정하고 DB에 저장.
    public void companyInfoUpdate(CompanyDTO companyDTO){
        Company company = companyDTO.toEntity();
        companyRepository.updateInfo(company);
    }

    //22.10.25 - ho 기업회원 탈퇴
    public void delete(Long comId) {
        postRepository.setComIdNull(comId);
        companyRepository.deleteById(comId);
    }

    //22.11.01 ho - 아이디 찾기용 DTO만들기 메서드.
    public CompanyDTO getCompanyDTOForFindId(String name, String email) {
        return CompanyDTO.builder()
                .comName(name)
                .comEmail1(email)
                .build();
    }

    //22.11.01 ho - 아이디 찾기. String 받아서 DTO 만들어서 찾기.
    public String findId2(String name, String email) {
        CompanyDTO companyDTO = getCompanyDTOForFindId(name, email);
        Company company1 = companyDTO.toEntityForFindId();
        String newName = company1.getComName();
        String newEmail = company1.getComEmail();

        Long num = companyRepository.countByComNameAndComEmail(newName,newEmail);
        if(num == 0){
            return "fail";
        } else {
            Optional<Company> company = companyRepository.findByComNameAndComEmail(newName, newEmail);
            return company.get().getComLoginId();
        }

    }

    //박채원 22.11.08 추가
    public String getPostTitle(Long postId){
        return postRepository.findPostByPostId(postId).getPostTitle();
    }

}