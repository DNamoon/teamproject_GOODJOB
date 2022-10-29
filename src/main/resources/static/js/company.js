//22.10.19 ho - 마이페이지 js파일 + 다음 주소찾기 fuction

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
function btnDisabled(){
    //'이전으로 돌아가기'버튼 숨기기
    const target2 = document.getElementById("btn_hidden");
    target2.hidden = true;

    //'정보 수정하기'버튼 보이기
    const target3 = document.getElementById("btn_modify");
    if(target3.style.display == 'none')
        target3.style.display = 'block';

    //정보 수정 못 하게 입력창 막기
    const target = document.getElementById("btn_target");
    target.disabled = true;

    //'정보 수정완료'버튼 숨기기
    const target7 = document.getElementById("modal_open_btn");
    target7.hidden = true;

    const target8 = document.getElementById("btn_change_pw");
    target8.hidden = true;
    //$('#btn_change_pw').removeAttr('hidden');

}

function password_confirm() {
    const password = document.getElementById("password_confirm");

    $.ajax("/confirm")
}

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

//비밀번호1,2 일치 여부 보여주는 fuction
function passwordConfirm() {
    var password = document.getElementById('comPw1');
    var passwordConfirm = document.getElementById('comPw2');
    var confirmMsg = document.getElementById('confirmMsg')  //확인 메시지
    var correctColor = "#73b973"; //맞았을 때 출력되는 색깔.
    var wrongColor = "#ff0000";   //틀렸을 때 출력되는 색깔.

    if(password.value == passwordConfirm.value) {
        confirmMsg.style.color = correctColor;
        confirmMsg.innerHTML = "비밀번호 일치";
    } else {
        confirmMsg.style.color = wrongColor;
        confirmMsg.innerHTML = "비밀번호 불일치";
    }
}


//아이디 중복검사
//onkeyup이 여기서는 "change keyup"이다!!!
$('#id_input').on("change keyup", function(){

    console.log("keyup 테스트");
    var comLoginId = $('#id_input').val();			// .id_input에 입력되는 값
    var data = {comLoginId : comLoginId};			// '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)'

    $.ajax({
        type : "post",
        url : "/com/check",
        data : data,
        success : function(result){
            if(result != 'fail'){
                $('.id_input_re_1').css("display","inline-block");
                $('.id_input_re_2').css("display", "none");
            } else {
                $('.id_input_re_2').css("display","inline-block");
                $('.id_input_re_1').css("display", "none");
            }

        }
    });
});


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


//다른분 아이디 중복체크 - 여기서는 사용 안함.
var $id = $("#id");
// 아이디 정규식
$id.on("keyup", function() { // 키보드에서 손을 땠을 때 실행
    var regExp = /^[a-z]+[a-z0-9]{5,15}$/g;

    if (!regExp.test($id.val())) { // id 가 공백인 경우 체크
        idchk = false;
        $id.html("<span id='check'>사용할 수 없는 아이디입니다.</span>");
        $("#check").css({
            "color" : "#FA3E3E",
            "font-weight" : "bold",
            "font-size" : "10px"
        })
    } else { // 공백아니면 중복체크
        $.ajax({
            type : "POST", // http 방식
            url : "/com/register", // ajax 통신 url
            data : { // ajax 내용 => 파라미터 : 값 이라고 생각해도 무방
                "type" : "user",
                "id" : $id.val()
            },
            success : function(data) {
                if (data == 1) { // 1이면 중복
                    idchk = false;
                    $id.html("<span id='check'>이미 존재하는 아이디입니다</span>")
                    $("#check").css({
                        "color" : "#FA3E3E",
                        "font-weight" : "bold",
                        "font-size" : "10px"

                    })
                    //console.log("중복아이디");
                } else { // 아니면 중복아님
                    idchk = true;
                    $id.html("<span id='check'>사용가능한 아이디입니다</span>")

                    $("#check").css({
                        "color" : "#0D6EFD",
                        "font-weight" : "bold",
                        "font-size" : "10px"

                    })
                    //console.log("중복아닌 아이디");
                }
            }
        })
    }
});