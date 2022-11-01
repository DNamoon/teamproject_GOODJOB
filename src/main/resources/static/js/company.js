//22.10.19 ho - 마이페이지 js파일 + 다음 주소찾기 fuction




//아이디 찾기 기업별, 멤버별 구분
function UserTypeFindId() {
    const form = document.forms['frm'];
    const type = $('#chk').val();
    console.log(type);

    if(type=="member"){
        form.action = "/member/findId";
        form.submit();
    }else if(type=="none"){
        alert("회원타입을 선택해주세요.")
    }else {
        form.action = "/com/findId";
        form.submit();
    }
}


//아이디 찾기
$(document).ready(function() {
    $('#btn_find_id').on('click', function () {
        whatId();
    })
});
function whatId() {
    const id = $('#id').val();
    const email = $('#email').val();
    console.log(email);

    if (!id || id.trim() === "") {
        alert("이름을 입력하세요.");
    } else {
        if (!email || email.trim() === "") {
            alert("이메일을 입력하세요");
        } else {
            $.ajax({
                type: 'post',
                url: '/com/findId',
                data: {
                    'comName': id,
                    'comEmail' : email
                },
                dataType: "text",
            }).done(function (result) {
                console.log("result :" + result);
                if (result == null) {
                    // sendEmail();
                    alert('가입된 회원정보를 찾을 수 없습니다.');
                } else if (result == "false") {
                    alert('회원님의 아이디는 ['+result+']입니다.');
                    window.location.href = "/login";
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
}



// 22.10.27 회원가입 완료 메시지 sweetalert2 사용 실패

// $(document).ready(function (){
//     var loginId = $('#id_input').val();
//
//     $('#sign_up').click(function (){
//         Swal.fire({
//             icon: 'success',
//             title: '회원가입 완료',
//             text: loginId + '님 회원가입을 축하드립니다!'
//         }).then(function (){
//             location.href = 'redirect:/';
//         });
//     });
// });

//비밀번호 변경
$(document).ready(function(){
    $('#change_confirm').click(function (){
        const password = $('#change_password_confirm').val();

        $.ajax({
            type:"post",
            url:"/com/changePw",
            data:"pw="+password,
            success : function (result) {
                if(result == 1) {
                    Swal.fire("비밀번호 변경페이지로 이동!","","success").then(function (){
                        location.href = "/com/changePassword";
                    })
                    // alert("비밀번호 변경 페이지로 이동합니다.");
                    // location.href = "/com/changePassword";
                } else {
                    Swal.fire("비밀번호를 확인해주세요!","","error");
                }
            }
        });
    });
});


// 회원탈퇴
$(document).ready(function(){
    $('#delete_confirm').click(function (){
        const password = $('#delete_password_confirm').val();

        $.ajax({
            type:"post",
            url:"/com/delete",
            data:"pw="+password,
            success : function (result) {
                if(result == 1) {
                    Swal.fire("회원을 탈퇴합니다!","","success").then(function (){
                        location.replace('/');  //.replace : 뒤로가기 불가능
                        //$("#contact-form").submit();
                        // location.href("/com/myPage");
                    })
                    //alert("회원을 탈퇴합니다.");
                } else {
                    Swal.fire("비밀번호를 확인해주세요!","","error");
                    //alert("비밀번호를 확인해주세요");
                }
            }
        });
    });
});


//22.10.24 ho - 회원정보 수정시 비밀번호 확인
$(document).ready(function(){
    $('#confirm').click(function (){
        const password = $("#password_confirm").val();

        $.ajax({
            type:"post",
            url:"/com/confirm",
            data: "pw=" +password,
            success : function (result) {
                if(result == 1) {
                    Swal.fire("회원정보를 수정합니다!","","success").then(function (){
                        $("#contact-form").submit();
                        // location.href("/com/myPage");
                    })
                    //alert("회원정보를 수정합니다.");
                } else {
                    Swal.fire("비밀번호를 확인해주세요!","","error");
                    //alert("비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.");
                }
            }

        })
    });
})

//수정하기 버튼 클릭시 disabled가 false 되며 입력할수 있게 변함. (jquery로 업데이트 10.27)
$(document).ready(function () {
    $('#btn_modify').click(function () {
        //입력창 활성화
        $('#btn_target').attr('disabled', false);
        //'정보 수정하기'버튼 숨기기
        $('#btn_modify').css('display', 'none')
        //'이전으로 돌아가기'버튼 생성
        //$('#btn_back').append('<input type="button" value="이전으로 돌아가기" onClick="btnDisabled()" id="btn_hidden" class="btn bg-gradient-dark w-100">');
        //'이전으로 돌아가기'버튼 보이기
        $('#btn_hidden').removeAttr('hidden');
        //'회원정보 수정완료'버튼 보이기
        $('#modal_open_btn').removeAttr('hidden');
        //'비밀번호 변경하기'버튼 보이기
        $('#btn_change_pw').removeAttr('hidden');
    })
})

//수정하기 버튼 클릭시 disabled가 false 되며 입력할수 있게 변함.
// function btnActive(){
//     //정보 수정할 수 있게 입력창 활성화
//     const target = document.getElementById("btn_target");
//     target.disabled = false;
//
//     //'정보 수정하기'버튼 숨기기
//     const target3 = document.getElementById("btn");
//     if(target3.style.display == 'block')
//     target3.style.display = 'none';
//
//     //'이전으로 돌아가기'버튼 보이기
//     const target2 = document.getElementById("btn_hidden");
//     target2.hidden = false;
//
//     const target8 = document.getElementById("btn_change_pw");
//     const target9 = document.createElement('input');
//
//
//
//     //'정보 수정완료'버튼 보이기
//     const target7 = document.getElementById("modal_open_btn");
//     target7.hidden = false;
    // target7.disabled = true;


    //로그인 아이디, 주소찾기는 계속 disabled  -> 정보수정할때 disabled 값 못 받아오는 문제 발견. 삭제.
    // const target4 = document.getElementById("loginId");
    // const target5 = document.getElementById("sample6_postcode");
    // const target6 = document.getElementById("sample6_address");
    // target4.disabled = true;
    // target5.disabled = true;
    // target6.disabled = true;
//}

// $('#comComdivCode').click(function () {
//     if($('#comComdivCode').value == 0){
//         $('#modal_open_btn').disabled = true;
//     } else {
//         $('#modal_open_btn').disabled = false;
//     }
// })

//'이전으로 돌아가기'버튼 클릭시
//function btnDisabled(){
    // //'이전으로 돌아가기'버튼 숨기기
    // const target2 = document.getElementById("btn_hidden");
    // target2.hidden = true;
    //
    // //'정보 수정하기'버튼 보이기
    // const target3 = document.getElementById("btn_modify");
    // if(target3.style.display == 'none')
    //     target3.style.display = 'block';
    //
    // //정보 수정 못 하게 입력창 막기
    // const target = document.getElementById("btn_target");
    // target.disabled = true;
    //
    // //'정보 수정완료'버튼 숨기기
    // const target7 = document.getElementById("modal_open_btn");
    // target7.hidden = true;
    //
    // const target8 = document.getElementById("btn_change_pw");
    // target8.hidden = true;
    // //$('#btn_change_pw').removeAttr('hidden');

    //location.href = "/com/myPage";

//}

//Daum 주소 찾기API function
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

//view - 사업자 등록번호 양식 일치 3-2-5
$('#comBusiNum').blur(function (){
    let busiNum = $(this).val();
    let regExp = /^\d{3}-\d{2}-\d{5}$/;
    let busiNum_error = $('#busiNum_error');

    if(!regExp.test(busiNum)) {
        busiNum_error.css("color","#ff0000");
        busiNum_error.show().text("형식이 맞지 않습니다. 다시 확인해주세요.");
    }else {
        busiNum_error.css("color","#54b254");
        busiNum_error.show().text("올바른 형식입니다.");
    }
})

//view - 전화번호 양식 일치(2~3)-(3~4)-4
$('#phone').blur(function (){
    let phone = $('#phone').val();
    let regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
    let phone_error = $('#phone_error');

    if(!regExp.test(phone)) {
        phone_error.css("color","#ff0000");
        phone_error.show().text("형식이 맞지 않습니다. 다시 확인해주세요.");
    }else {
        phone_error.css("color","#54b254");
        phone_error.show().text("올바른 형식입니다.");
    }
})

//view - 이메일 양식일치
$('#email').keyup(function (){
    let email = $('#email').val();
    let regExp = /^[A-Z0-9a-z._%+-]{2,64}$/;
    let emailError = $('#email_error');
    emailError.css("color","#ff0000");

    if(!regExp.test(email)) {
        emailError.show().text("올바른 이메일 양식이 아닙니다!")
    } else {
        emailError.show().text("");
    }
})


//view - 비밀번호1,2 일치 여부 보여주는 function
function passwordConfirm() {
    var password = document.getElementById('comPw1');
    var passwordConfirm = document.getElementById('comPw2');
    var confirmMsg = document.getElementById('confirmMsg')  //확인 메시지

    var passwordError = document.getElementById('password_error');

    var correctColor = "#54b254"; //맞았을 때 출력되는 색깔.
    var wrongColor = "#ff0000";   //틀렸을 때 출력되는 색깔.

    var regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{3,25}$/g; //최소 8자 최대 25자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
//                ^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$

    if(!regExp.test(password.value)){
        passwordError.style.color = wrongColor;
        passwordError.innerHTML = "비번은 3~25자로 최소 하나의 영문자, 숫자, 특수문자가 들어가야 합니다."
        confirmMsg.style.color = wrongColor;
        confirmMsg.innerHTML = "올바른 비밀번호를 입력해주세요."
    } else {
        if(password.value == passwordConfirm.value) {
            passwordError.style.color = correctColor;
            passwordError.innerHTML = ""
            confirmMsg.style.color = correctColor;
            confirmMsg.innerHTML = "비밀번호 일치";
        } else {
            passwordError.style.color = correctColor;
            passwordError.innerHTML = ""
            confirmMsg.style.color = wrongColor;
            confirmMsg.innerHTML = "비밀번호 불일치";
        }

    }

}


//view - 아이디 중복검사
//onkeyup이 여기서는 "change keyup"이다!!!
$('#id_input').on("change keyup", function(){

    console.log("keyup 테스트");
    var comLoginId = $('#id_input').val();			// .id_input에 입력되는 값
    var data = {comLoginId : comLoginId};			// '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)'
    var idError = $('.id_error');
    idError.css("color", "#ff0000");

    $.ajax({
        type : "post",
        url : "/com/check",
        data : data,
        success : function(result){
            if(result != 'fail'){  //중복되는 아이디가 없을 때
                let regExp = /^[a-z0-9_-]{3,15}$/; //아이디 정규식(영문 소문자, 숫자만 허용. 길이제한 3~15)
                if(comLoginId === null || comLoginId ==="") {  //아이디 입력을 안 했을 때
                    idError.show().text("필수 입력값입니다. 아이디를 입력해주세요.");
                } else {
                    if(!regExp.test(comLoginId)) {  //아이디 정규식이 맞지 않을 때
                        idError.show().text("아이디는 3~15자의 영문 소문자와 숫자,특수기호(_),(-)만 사용 가능합니다.")
                    } else { //아이디 중복도 없고 정규식도 올바를 때
                        idError.css("color","#54b254");
                        idError.show().text("사용할 수 있는 아이디입니다!");
                    }
                }
            } else {  //중복되는 아이디가 있을 경우
                idError.show().text("이미 존재하는 아이디입니다.");
            }

        }
    });
});

//회원가입 유효성 검사
function btnRexExp() {

    //아이디 정규식 확인용 변수
    var comLoginId = $('#id_input').val();			// .id_input에 입력되는 값
    let regExp1 = /^[a-z0-9_-]{3,15}$/g; //아이디 정규식(영문 소문자, 숫자만 허용. 길이제한 3~15)

    //비밀번호 정규식 확인용 변수
    var password = $('#comPw1').val();

    //이메일 정규식 확인용 변수
    let email = $('#email').val();
    let regExp3 = /^[A-Z0-9a-z._%+-]{2,64}$/;

    //사업자 등록번호 확인용 변수
    let busiNum = $('#comBusiNum').val();
    let regExp4 = /^\d{3}-\d{2}-\d{5}$/;

    //사업자 등록번호 정규식 확인
    let result4 = "false";
    if(!regExp4.test(busiNum)) {
        $('#comBusiNum').focus();
    }else {
        result4 = "true";
    }

    //전화번호 확인용 변수
    let phone = $('#phone').val();
    let regExp5 = /^\d{2,3}-\d{3,4}-\d{4}$/;

    //전화번호 정규식 확인
    let result5 = "false";
    if(!regExp5.test(phone)) {
        $('#phone').focus();
    }else {
        result5 = "true";
    }

    //이메일 정규식 확인
    let result3 = "false";
    if(!regExp3.test(email)) {
        //Swal.fire("이메일 양식을 확인해주세요.","","error");
        $('#email').focus();
    } else {
        result3 = "true";
    }

    //비밀번호 정규식 확인
    var result2 = "false";
    let regExp2 = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{3,25}$/g; //최소 8자 최대 25자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
    if (!regExp2.test(password)) {
        $('#comPw1').focus();
        //Swal.fire("비밀번호 양식을 확인해주세요.","","error");
    } else {
        result2 = "true";
    }

    //아이디 정규식 확인
    var result1 = "false";
    if (comLoginId === null || comLoginId === "") {  //아이디 입력을 안 했을 때
        //Swal.fire("아이디를 입력해주세요!","","error");
        $('#id_input').focus();
    } else {
        if (!regExp1.test(comLoginId)) {  //아이디 정규식이 맞지 않을 때
            //Swal.fire("아이디 양식을 확인해주세요!","","error");
            $('#id_input').focus();
        } else { //아이디 중복도 없고 정규식도 올바를 때
            result1 = "true";
        }
    }

    if (result1 == "true" && result2 == "true" && result3 == "true" && result4 == "true" && result5 == "true") {
        // $('#contact-form').submit();
        Swal.fire("회원가입 완료",comLoginId+"님 회원가입을 축하드립니다!","success")
            .then(function (){  //alert창 확인 누르면 submit
                $('#contact-form').submit();
            });
    } else {
    }
}

//비밀번호 변경 폼 유효성 검사
function btn_passwordChange() {

    //비밀번호 정규식 확인용 변수
    var password = $('#comPw1').val();
    var passwordConfirm = $('#comPw2').val();

    //비밀번호 정규식 확인
    let regExp2 = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{3,25}$/g; //최소 8자 최대 25자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
    if (!regExp2.test(password)) {
        Swal.fire("비밀번호 사용 불가!","비밀번호는 3~25자로 최소 하나의 영문자, 숫자, 특수문자가 들어가야 합니다.","error");
        return false;
    } else {
        if(password == passwordConfirm){
            $('#form_change_pw').submit();
        } else {
            Swal.fire("비밀번호 불일치!","","error");
        }
    }

}
//
// function regExpForComBusiNum(){
//     let busiNum = $('#comBusiNum').val();
//     let regExp4 = /^\d{3}-\d{2}-\d{5}$/;
//
//     //사업자 등록번호 정규식 확인
//     let result4 = "false";
//     if(!regExp4.test(busiNum)) {
//         $('#comBusiNum').focus();
//     }else{
//         result4 = "true";
//     }
//
//     return result4;
// }

//회원정보 수정 유효성 검사
function btn_change_info(){
    /** 순서 중요 - 이메일,전화번호,사업자 거꾸로 확인하여 맨 위에 양식 안 맞는 것부터 focus()*/
    //이메일 정규식 확인용 변수
    let email = $('#email').val();
    let regExp3 = /^[A-Z0-9a-z._%+-]{2,64}$/;

    //이메일 정규식 확인
    let result3 = "false";
    if(!regExp3.test(email)){
        $('#email').focus();
    } else {
        result3 = "true";
    }


    //전화번호 확인용 변수
    let phone = $('#phone').val();
    let regExp5 = /^\d{2,3}-\d{3,4}-\d{4}$/;

    //전화번호 정규식 확인
    let result5 = "false";
    if(!regExp5.test(phone)) {
        $('#phone_alert').show();
        $('#phone').focus();
    }else {
        $('#phone_alert').hide();
        result5 = "true";
    }

    //사업자 등록번호 확인용 변수
    let busiNum = $('#comBusiNum').val();
    let regExp4 = /^\d{3}-\d{2}-\d{5}$/;

    //사업자 등록번호 정규식 확인
    let result4 = "false";
    if(!regExp4.test(busiNum)) {
        $('#busiNum_alert').show();
        $('#comBusiNum').focus();
    }else {
        $('#busiNum_alert').hide();
        result4 = "true";
    }

    if(result3 != "false" && result4 != "false" && result5 != "false") {
        $('#modal_open_btn').attr("data-toggle", "modal");
        $("#confirmPasswordModal").modal();
    } else {
        $('#modal_open_btn').removeAttr("data-toggle");
    }
}


/////////////////////
// 사론님 로그인 중복체크(정규표현식) 여기서 사용은 안 함.
$("#userId").blur(function () {
    let idError = $("#idError");
    idError.css("color", "#ff6289");

    ///ajax 조건식//
    $.ajax({
        url: '/idCheck',
        type: 'get',
        data: {userId : $("#userId").val()},
        dataType: 'text',
        success: function (check) {	// 통신 성공 시 "true" 혹은 "false" 반환
            if (check === "true") { // 아이디가 이미 존재함
                //console.log(check);	// 확인용
                idError.show().text("중복 아이디입니다.");
                return resId=false;
            } else {	// "false" 일 경우 - 아이디가 존재하지 않을 경우
                //console.log(check);	// 확인용
                let regExp = /^[a-z0-9_-]{5,20}$/g; //아이디 정규식(영문 소문자, 숫자, 특수문자(-,_) 만 가능)
                let id = $("#userId").val();

                if (id === null || id === "") { //값이 없을 때
                    idError.show().text("필수 정보입니다.");
                    return resId=false;
                } else if (!regExp.test(id)) { //정규식에 맞지 않을 때
                    idError.show().text("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
                    return resId=false;
                } else { //조건에 맞는 아이디일 때 true 반환
                    idError.css("color", "#77ab59");
                    idError.show().text("멋진 아이디네요!");
                    return resId = true;
                }
            }
        },
        error: function () {
            console.log("아이디 체크 오류");
        }
    });
});