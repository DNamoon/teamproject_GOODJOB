//22.10.19 ho - 마이페이지 js파일 + 다음 주소찾기 fuction

//22.10.24 ho - 회원정보 수정시 비밀번호 확인
$(document).ready(function(){
    $('#confirm').click(function (){
        const password = $("#password_confirm").val();
        console.log(password);

        $.ajax({
            type:"post",
            url:"/com/confirm",
            data: "pw=" +password,
            success : function (result) {
                if(result == 1) {
                    alert("회원정보를 수정합니다.");
                    $("#contact-form").submit();
                } else {
                    alert("비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.");
                }
            }

        })
    });
})

//수정하기 버튼 클릭시 disabled가 false 되며 입력할수 있게 변함.
function btnActive(){
    //정보 수정할 수 있게 입력창 활성화
    const target = document.getElementById("btn_target");
    target.disabled = false;

    //'이전으로 돌아가기'버튼 보이기
    const target2 = document.getElementById("btn_hidden");
    target2.hidden = false;

    //'정보 수정하기'버튼 숨기기
    const target3 = document.getElementById("btn");
    if(target3.style.display == 'block')
    target3.style.display = 'none';

    //'정보 수정완료'버튼 보이기
    const target7 = document.getElementById("modal_open_btn");
    target7.hidden = false;
    // target7.disabled = true;


    //로그인 아이디, 주소찾기는 계속 disabled  -> 정보수정할때 disabled 값 못 받아오는 문제 발견. 삭제.
    // const target4 = document.getElementById("loginId");
    // const target5 = document.getElementById("sample6_postcode");
    // const target6 = document.getElementById("sample6_address");
    // target4.disabled = true;
    // target5.disabled = true;
    // target6.disabled = true;
}

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
    const target3 = document.getElementById("btn");
    if(target3.style.display == 'none')
        target3.style.display = 'block';

    //정보 수정 못 하게 입력창 막기
    const target = document.getElementById("btn_target");
    target.disabled = true;

    //'정보 수정완료'버튼 숨기기
    const target7 = document.getElementById("modal_open_btn");
    target7.hidden = true;
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
