


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
$(document).ready(function() {
    $("#aa").click(function () {
        $.ajax({
            type: "post",
            url: "/member/checkPW",
            data: "id="+$("#id").val()+"&pw="+$('#pw').val(),
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

