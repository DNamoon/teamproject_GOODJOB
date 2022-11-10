package com.goodjob.member.service;

import com.goodjob.company.Company;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Optional;

/**
 * 박채원 22.10.02 작성
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ResumeRepository resumeRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResumeMemberDTO bringMemInfo(String loginId) {
        Member member = memberRepository.findLoginInfo(loginId);
        String[] phoneNum = member.getMemPhone().split("-");
        String[] email = member.getMemEmail().split("@");
        String[] address = member.getMemAddress().split("@");

        ResumeMemberDTO resumeMemberDTO = entityToDTO(member, phoneNum[0], phoneNum[1], phoneNum[2], email[0], email[1], address[0], address[1]);
        return resumeMemberDTO;
    }

    /**
     * 김도현 22.9.29 작성
     * 10.20 수정(이메일 확인,임시비밀번호 생성,임시비밀번호 db에 업데이트 메소드)
     **/

    @Override
    public Member register(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public int checkId2(String memLoginId) {
        return memberRepository.checkId2(memLoginId);
    }
    @Override
    public Long countByMemLoginId(String memLoginId) {
        Long result = memberRepository.countByMemLoginId(memLoginId);
        return result;
    }

    @Override
    public Optional<Member> loginIdCheck(String memLoginId) {
        return memberRepository.findByMemLoginId(memLoginId);
    }

    @Override
    public MemberDTO memInfo(String loginId) {
        Member mem = memberRepository.findLoginInfo(loginId);
        String name = mem.getMemName();
        String[] email = mem.getMemEmail().split("@");
        String phone = mem.getMemPhone();
        String gender = mem.getMemGender();
        Date birth = mem.getMemBirthDate();
        String[] address = mem.getMemAddress().split("@");
        Long pk = mem.getMemId();
        MemberDTO memberDTO = MemberDTO.builder().memId(pk).memName(name).memEmail1(email[0]).memEmail2(email[1]).memPhone(phone).memGender(gender)
                .memBirthDate(birth).memAddress(address[0]).detailAddress(address[1]).build();

        return memberDTO;
    }

    @Override
    public void updateMemInfo(MemberDTO memberDTO) {
        Member mem = memberDTO.toEntity();
        memberRepository.updateInfo(mem);
    }

    /**
     * 이메일 가입여부 확인
     **/
    @Override
    public String checkEmail(String memEmail) {
        Company com = companyRepository.findByComEmail(memEmail);
        Member mem = memberRepository.findByMemEmail(memEmail);
        if (com != null) {
            return "com";
        }
        if (mem != null) {
            return "mem";
        }
        return "false";
    }
    //개인정보 수정-이메일 중복 체크
    @Override
    public String updateEmailCheck(String memEmail, HttpSession session) {
        String id = (String) session.getAttribute("sessionId");
        Company com = companyRepository.findByComEmail(memEmail);
        Member mem = memberRepository.findByMemEmail(memEmail);

        if(mem == null && com == null) {
            return "false";

        } else if (mem.getMemLoginId().equals(id)){
            return "mine";
        }
        return "no";
    }

    // 임시 비밀번호로 업데이트

    @Override
    public void updatePassword(String tmpPw, String memEmail, String type) {
        String encryptPassword = passwordEncoder.encode(tmpPw);
        if (type.equals("mem")) {
            Member member = memberRepository.findByMemEmail(memEmail);
            member.updatePassword(encryptPassword);

        } else {
            Company company = companyRepository.findByComEmail(memEmail);
            company.updatePassword(encryptPassword);
        }
    }

   // 임시 비밀번호 생성

    @Override
    public String getTmpPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','!',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }

    // 비밀번호 변경

    @Override
    public void changePassword(String changePw, Long id) {
        System.out.println(changePw + ">>>>" + id);
        String encryptPassword = passwordEncoder.encode(changePw);
        Member member = memberRepository.findByMemId(id);
        member.updatePassword(encryptPassword);
        memberRepository.save(member);
    }

    //회원 탈퇴
    @Override
    public void deleteById(Long memId) {
        //박채원 22.11.07 추가 (이하 1줄) - 회원탈퇴 전에 이력서의 memId를 null으로 바꿈
        resumeRepository.setMemberIdNull(memId);
        memberRepository.deleteById(memId);
        System.out.println("delete" + memId);
    }

    //22.11.01 ho 추가. 아이디 찾기
    @Override
    public String findId(String name, String email) {
        MemberDTO memberDTO = getMemberDTOForFindId(name, email);
        Member member = memberDTO.toEntityForFindId();
        String newName = member.getMemName();
        System.out.println("??? 개인 아이디 찾기 받아오는 name : " + newName);
        String newEmail = member.getMemEmail();
        System.out.println("??? 개인 아이디 찾기 받아오는 email : " + newEmail);

        Long num = memberRepository.countByMemNameAndMemEmail(newName, newEmail);
        System.out.println("이름과 이메일로 카운트하는 수 num = " + num);
        if (num == 0) {
            return "fail";
        } else {
            Member mem = memberRepository.findByMemNameAndMemEmail(newName, newEmail);
            return mem.getMemLoginId();
        }
    }

    //22.11.01 ho 추가. 아이디 찾기용 MemberDTO 만들기 메서드.
    private MemberDTO getMemberDTOForFindId(String name, String email) {
        return MemberDTO.builder()
                .memName(name)
                .memEmail1(email)
                .build();
    }
}
