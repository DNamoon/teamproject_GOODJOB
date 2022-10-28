package com.goodjob.member.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

/**
 * 박채원 22.10.02 작성
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResumeMemberDTO bringMemInfo(String loginId) {
        Member member = memberRepository.findLoginInfo(loginId);
        String[] phoneNum = member.getMemPhone().split("-");
        String[] email = member.getMemEmail().split("@");
        String[] address = member.getMemAddress().split("@");
        
        ResumeMemberDTO resumeMemberDTO = entityToDTO(member, phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
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
        Member mem =  memberDTO.toEntity();
        memberRepository.updateInfo(mem);
    }

    @Override
    public boolean checkEmail(String memEmail) {
        return memberRepository.existsByMemEmail(memEmail);
    }
    /** 임시 비밀번호로 업데이트 **/
    @Override
    public void updatePassword(String tmpPw, String memberEmail) {
        String encryptPassword = passwordEncoder.encode(tmpPw);
        Member member = memberRepository.findByMemEmail(memberEmail);

        member.updatePassword(encryptPassword);
    }
    /** 임시 비밀번호 생성 **/
    @Override
    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }
    /** 비밀번호 변경 **/
    @Override
    public void changePassword(String changePw, Long memId) {
        String encryptPassword = passwordEncoder.encode(changePw);
        Member member= memberRepository.findByMemId(memId);
        member.updatePassword(encryptPassword);
        memberRepository.save(member);
    }
    //회원 탈퇴
    @Override
    public void deleteById(Long memId) {
        memberRepository.deleteById(memId);
    }
}
