package com.goodjob.member.memDTO;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Data
@Builder
public class MemberPwDTO {
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Pattern(regexp="(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{3,25}",
            message = "비밀번호는 3~25자로 최소 1개의 영문자, 숫자, 특수문자가 들어가야 합니다.")
    private String pw;
    private String pw2;
}
