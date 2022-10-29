
//id 중복 확인
 function checkMassage(){
     var id = $('#id').val(); //id값이 "id"인 입력란의 값을 저장
     $.ajax({
         url:'/member/checkId?id='+id, //Controller에서 요청 받을 주소
         success:function(result){ //컨트롤러에서 값을 받는다
             console.log(result)
             if(result != 0){ //cnt가 1이 아니면(=0일 경우) -> 이미 존재하는 아이디
                 $('#checkMassage').css('color','red')
                 $('#checkMassage').html("사용할 수 없는 아이디입니다.")
                 $("#btn").prop('disabled',true);

             } else { // cnt가 1일 경우 -> 사용 가능한 아이디
                 $('#checkMassage').css('color','blue')
                 $('#checkMassage').html("사용할 수 있는 아이디입니다.")
                 $("#btn").prop('disabled',false);
             }
         }
     });
 };
 //주소찾기
 function execPostCode() {
     daum.postcode.load(function () {
         new daum.Postcode({
             oncomplete: function (data) {
                 var addr = '';

                 if (data.userSelectedType === 'R') {
                     addr = data.roadAddress;
                 } else {
                     addr = data.jibunAddress;
                 }

                 document.getElementById("userAddress").value = '(' + data.zonecode + ') ' + addr;
                 document.getElementById("userDetailAddress").focus();
             }
         }).open();
     });
 }
 //회원가입 비밀번호 확인
 function test() {
     var p1 = document.getElementById('pw1').value;
     var p2 = document.getElementById('pw2').value;
     if(p1 != p2){
         $('#confirmMsg').css('color','red')
         $('#confirmMsg').html("비밀번호 불일치")
         $("#btn").prop('disabled',true);

     } else { // cnt가 1일 경우 -> 사용 가능한 아이디
         $('#confirmMsg').css('color','blue')
         $('#confirmMsg').html("비밀번호 일치")
         $("#btn").prop('disabled',false);
     }

 }
 // login 페이지 기업 or 개인 회원 체크
 function checkOnlyOneLogin(element) {
     //checkbox 중복 선택 불가
     const checkboxes
         = document.getElementsByName("type");

     checkboxes.forEach((cb) => {
         cb.checked = false;
     })

     element.checked = true;
     $('#chk').val(element.value) //
 }
//로그인 시 type 확인 후 해당 db조회
 function UserTypeLogin() {
     const form = document.forms['frm'];
     const type = $('#chk').val();
     console.log(type);

     if(type=="member"){
         form.action = "/member/login";
         form.submit();
     }else if(type=="none"){
         alert("회원타입을 선택해주세요.")
     }else {
         form.action = "/com/login";
         form.submit();
     }
 }
 // 개인정보 수정버튼 function
$(document).ready(function() {
    $("#bb").click(function() {
        $("#dis").removeAttr("disabled");
        $("#bb").css("display", 'none');
        $("#realUpdate").append("<button type=\"button\" class=\"btn bg-gradient-dark w-100\" style=\"font-size:15px\" data-target=\"#pwCheck\" data-toggle=\"modal\">수정완료</button>");
    })
});
 //개인정보 수정 시 비밀번호 확인
$(document).ready(function() {
    $("#updatebtn").click(function () {
        $.ajax({
            type: "post",
            url: "/member/checkPW",
            data: "loginId="+$("#id").val()+"&pw="+$('#pw').val(),
            success: function (data) {
              if(data=="0"){
                document.getElementById("formUpdate").submit();
            }else {
                alert("비밀번호를 확인해주세요.")
            }
        }
     })
    })
});
//회원삭제 function
$(document).ready(function() {
    $("#deletebtn").click(function () {
        $.ajax({
            type: "post",
            url: "/member/delete",
            data: "memId="+$("#memId").val()+"&loginId="+$("#id").val()+"&deletePw="+$('#deletePw').val(),
            success: function (data) {
                if(data=="0"){
                    Swal.fire({
                        icon: 'success',                         // Alert 타입
                        title: '회원탈퇴',         // Alert 제목
                        text: '회원탈퇴가 완료되었습니다.'  // Alert 내용
                    }).then(function (){
                        location.href="/";
                    });
                }else {
                    alert("비밀번호를 확인해주세요.")
                }
            }
        })
    })
});
//비밀번호 찾기
$(document).ready(function() {
    $('#checkEmail').on('click', function () {
        checkEmail();
    })
});
function checkEmail(){
    const email = $('#email').val();
    console.log(email);
    if(!email || email.trim() === ""){
        alert("이메일을 입력하세요.");
    } else {
        $.ajax({
            type: 'post',
            url: '/member/checkEmail',
            data: {
                'memberEmail': email
            },
            dataType: "text",

        }).done(function(result){
            console.log("result :" + result);
            if (result == "true") {
                sendEmail();
                alert('임시비밀번호를 전송 했습니다.');
                window.location.href="/login";
            } else if (result == "false") {
                alert('가입되지 않은 이메일입니다.');
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }
};
//임시비밀번호 메일발송
function sendEmail(){
    const memberEmail = $('#email').val();

    $.ajax({
        type: 'POST',
        url: '/member/sendPw',
        data: {
            'memberEmail' : memberEmail
        },success: function(result){
            console.log(result);
        },
        error: function(error){
            alert(JSON.stringify(error)+"ㅇㅇㅇㅇㅇㅇ");
        }
    })
}
//비밀번호 변경 비번 check 모달
$(document).ready(function() {
    $("#changePwCheck").click(function () {
        $.ajax({
            type: "post",
            url: "/member/changePw",
            data: "loginId=" + $("#id").val() + "&checkPw=" + $('#checkPw').val(),
            success: function (data) {
                if (data == "0") {
                    document.getElementById("changePwForm").submit();
                } else {
                    alert("비밀번호를 확인해주세요.")
                }
            }
        })
    })
});
// $(document).ready(function() {
//     $("#").click(function () {
//             const pw2 = $('#pw2').val();
//             const memId =$('#memId').val();
//             $.ajax({
//                 type: 'POST',
//                 url: '/member/changePw',
//                 data: {
//                     "memId="+$("#memId").val()+"&loginId="+$("#id").val()+"&deletePw="+$('#deletePw').val(),
//                 },success: function (data) {
//                     if(data=="success"){
//                         Swal.fire({
//                             icon: 'success',                         // Alert 타입
//                             title: '비밀번호 변경',         // Alert 제목
//                             text: '비밀번호 변경이 완료되었습니다.'  // Alert 내용
//                         }).then(function (){
//                             location.href="/member/myPage";
//                         });
//                     }else {
//                         alert("비밀번호를 확인해주세요.")
//                     }
//                 },
//                 error: function(error){
//                     alert(JSON.stringify(error));
//                 }
//             })
//     })
// });