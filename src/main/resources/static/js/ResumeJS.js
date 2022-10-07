$(document).ready(function(){
    $("#registerResume").click(function(){
        $.ajax({
            type: "get",
            url: "/resume/registerResume",
            dataType: "text",
            success: function(data){
                location.href = "/resume/resumeStep1/" + data;
            }
        });
    })

    $(".findSchool").click(function(){
        var schoolName = $(".findSchoolName").val();

        $.ajax({
            type: "get",
            url: "/resume/findSchool/" + schoolName,
            dataType: "json",
            async: false,
            success: function (data){
                $(".schoolList").empty();
                for(var i=0; i<data.length; i++){
                    $(".schoolList").append('<input class="form-check-input" type="radio" name="selectSchoolName" value="' + data[i].schName +'">' + data[i].schName +'<br/>');  <!-- 여기 value값에 data[i].schName 넣는 방법이랑 왜 schName이 undefined로 나오는지 -->
                }
                $(".doneFindSchool").click(function (){
                    $("#schoolName").val($("input:radio[name='selectSchoolName']:checked").val());
                });
            }
        });
    });

    $(".findMajor").click(function(){
        var majorName = $(".findMajorName").val();

        $.ajax({
            type: "get",
            url: "/resume/findMajor/" + majorName,
            dataType: "json",
            async: false,
            success: function (data){
                $(".majorList").empty();
                for(var i=0; i<data.length; i++){
                    $(".majorList").append('<input class="form-check-input" type="radio" name="selectMajorName" value="' + data[i].majName +'">' + data[i].majName +'<br/>');
                }
                $(".doneFindMajor").click(function (){
                    $("#majorName").val($("input:radio[name='selectMajorName']:checked").val());
                });
            }
        });
    });

    $(".findCerti").click(function () {
        var certiName = $(".findCertiName").val();

        $.ajax({
            type: "get",
            url: "/resume/findCerti/" + certiName,
            dataType: "json",
            async: false,
            success: function (data) {
                $(".certiList").empty();
                for (var i = 0; i < data.length; i++) {
                    $(".certiList").append('<input class="form-check-input" type="radio" name="selectCertiName" value="' + data[i].certiName + '">' + data[i].certiName + '<br/>');
                }
                $(".doneFindCerti").click(function () {
                    var inputId = $("#modalId").val();
                    $("#" + inputId).val($("input:radio[name='selectCertiName']:checked").val());
                });
            }
        });
    });

    $(".addCertiInfo").click(function(){
        addCertiInfo();
        var size = $("input[name='certificateName']").length;
        for(var i = 1; i < size; i++){
            $("input[name='certificateName']").eq(i).attr("id","certiifcateName"+i);
        }
    });
});

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

//자격증 추가
function addCertiInfo() {
    var certiInfo = '';

    certiInfo += '<hr>';
    certiInfo += '<div class="row" style="float: inline-start;">';
    certiInfo += '<div class="col-md-6" style="width: 400px;">';
    certiInfo += '<label>자격증명</label>';
    certiInfo += '<div class="input-group mb-4">';
    certiInfo += '<input class="form-control" type="text" name="certificateName" id="certificateName" readonly>';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-2" style="margin-left: 0px;">';
    certiInfo += '<label>&nbsp;</label>';
    certiInfo += '<button type="button" class="btn bg-gradient-dark w-100" onclick="getInputId(this)" data-bs-toggle="modal" data-bs-target="#findCertiModal">자격증찾기</button>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6" style="width: 200px;">';
    certiInfo += '<label>취득년월</label>';
    certiInfo += '<div class="input-group mb-4">';
    certiInfo += '<input class="form-control" placeholder="YYYY-MM-DD" type="date" value="2010-01-01" name="certiGetDate">';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6" style="width: 200px;">';
    certiInfo += '<label>점수</label>';
    certiInfo += '<div class="input-group mb-4">';
    certiInfo += '<input class="form-control" type="text" type="text" name="certiScore">';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '</div>';

    $(".addCertiInfoList").append(certiInfo);
}

function getInputId(data){
    $("#modalId").val($(data).parent().parent().find("input").attr("id"));
}

//경력 추가
function addCareerInfo() {
    var careerInfo = '';

    careerInfo += '<div class="row" style="float: inline-start;">';
    careerInfo += '<div class="col-md-6" style="width: 400px;">';
    careerInfo += '<label>회사명</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="text" >';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 200px;">';
    careerInfo += '<label>근무기간</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" placeholder="YYYYMM" type="date" >';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 5px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '~';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 200px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" placeholder="YYYYMM" type="date" >';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="form-group mb-4">';
    careerInfo += '<label>주요업무</label>';
    careerInfo += '<textarea name="message" class="form-control" placeholder="근무했던 부서 및 담당 업무를 간단하게 작성해 주세요." id="message" rows="3"></textarea>';
    careerInfo += '</div>';

    $(".addCareerInfo").replaceWith(careerInfo);
}

//입력검증
//휴대폰 번호 - 실
function isFourNumber(phoneNum){
    var exp = /\d\d\d\d/;
    if(phoneNum.value.match(exp)){
        return true;
    } else {
        console.log("dd");
        $("#phoneNumVeriDiv").append("4자리 숫자로 입력해주세요");
        phoneNum.focus();
        return false;
    }
}

//학점 - 실
function rangeCredit(){
    if(Number($("#eduGetCredit").val()) > Number($("#eduTotalCredit").val())){
        $("#eduGetCredit").val($('#eduTotalCredit').val().replace('/',''));
    }else{
        $("#eduGetCredit").val($('#eduGetCredit').val());
    }
}


