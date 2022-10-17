$(document).ready(function(){
    maxDate();

    var size = $("input[name='certificateName']").length;
    for(var i = 1; i < size; i++){
        $("input[name='certificateName']").eq(i).attr("id","certificateName"+i);
    }

    //이력서 등록버튼 눌리면 이력서 번호부터 등록하고 시작
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
    
    //학교 찾기
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
                    $(".schoolList").append('<input class="form-check-input" type="radio" name="selectSchoolName" value="' + data[i].schName +'">' + data[i].schName +'<br/>');
                }
                $(".doneFindSchool").click(function (){
                    $("#schoolName").val($("input:radio[name='selectSchoolName']:checked").val());
                });
            }
        });
    });

    //전공 찾기
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

    //자격증 찾기
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
                    // $("#" + inputId).val($("input:radio[name='selectCertiName']:checked").val());
                    $("#" + inputId).attr("value", $("input:radio[name='selectCertiName']:checked").val());
                });
            }
        });
    });

    $(".addCertiInfo").click(function(){
        addCertiInfo();
        var size = $("input[name='certificateName']").length;
        for(var i = 1; i < size; i++){
            $("input[name='certificateName']").eq(i).attr("id","certificateName"+i);
        }
    });

    //step2WithContent에서 새로 추가한 자격증을 DB에 넣기 위함
    $(".addCertiInfoWC").click(function(){
        var resumeId = $("input[name='resumeId']").val();
        $.ajax({
            url: "/resume/addCertiInfo/" + resumeId,
            type: "get",
            dataType: "text",
            success: function(data){
                addCertiInfoWC(data);
            }
        })
    });

    //step2WithContent에서 새로 추가한 경력사항을 DB에 넣기 위함
    $(".addCareerInfoWC").click(function(){
        var resumeId = $("input[name='resumeId']").val();

        $.ajax({
            url: "/resume/addCareerInfo/" + resumeId,
            type: "get",
            dataType: "text",
            success: function(data){
                addCareerInfoWC(data);
            }
        })
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

//STEP2용 자격증 추가
function addCertiInfo() {
    maxDate();
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
    certiInfo += '<input class="form-control certiGetDate" type="date" name="certiGetDate" max="">';
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

//STEP2WithContent용 자격증 추가
function addCertiInfoWC(data) {
    maxDate();
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
    certiInfo += '<input class="form-control certiGetDate" type="date" name="certiGetDate" max="">';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6" style="width: 200px;">';
    certiInfo += '<label>점수</label>';
    certiInfo += '<div class="input-group mb-4">';
    certiInfo += '<input class="form-control" type="text" type="text" name="certiScore">';
    certiInfo += '</div>';
    certiInfo += '<input type="hidden" value="' + data + '" name="certiId">';
    certiInfo += '</div>';
    certiInfo += '</div>';

    $(".addCertiInfoList").append(certiInfo);
}

function getInputId(data){
    $("#modalId").val($(data).parent().parent().find("input").attr("id"));
}

function getListToNext(){
    var certiSize = $("input[name='certificateName']").length;
    var careerSize = $("input[name='careerCompanyName']").length;
    var resumeId = $("input[name='resumeId']").val();

    let CertificateDTO = function (resumeId, certiId, certificateName, certiGetDate, certiScore){
        this.resumeId = resumeId;
        this.certiId = certiId;
        this.certificateName = certificateName;
        this.certiGetDate = certiGetDate;
        this.certiScore = certiScore;
    }

    let CareerDTO = function (careerId, careerCompanyName, careerJoinedDate, careerRetireDate, careerTask, resumeId){
        this.resumeId = resumeId;
        this.careerId = careerId;
        this.careerCompanyName = careerCompanyName;
        this.careerJoinedDate = careerJoinedDate;
        this.careerRetireDate = careerRetireDate;
        this.careerTask = careerTask;
    }

    var certificateList = [];
    for(i = 0; i < certiSize; i++){
        var certificateDTO = new CertificateDTO(
            $("input[name='resumeId']").val(),
            $("input[name='certiId']").eq(i).val(),
            $("input[name='certificateName']").eq(i).val(),
            $("input[name='certiGetDate']").eq(i).val(),
            $("input[name='certiScore']").eq(i).val()
        )
        certificateList.push(certificateDTO);
    }

    var careerList = [];
    for(i = 0; i < careerSize; i++){
        var careerDTO = new CareerDTO(
            $("input[name='careerId']").eq(i).val(),
            $("input[name='careerCompanyName']").eq(i).val(),
            $("input[name='careerJoinedDate']").eq(i).val(),
            $("input[name='careerRetireDate']").eq(i).val(),
            $("textarea[name='careerTask']").eq(i).val(),
            $("input[name='resumeId']").val()
        )
        careerList.push(careerDTO);
    }

    $.ajax({
        type: "get",
        url: "/resume/insertStep2",
        data: {"certificateList" : JSON.stringify(certificateList), "careerList" : JSON.stringify(careerList)},
        success: function(data){
            location.href = "/resume/resumeStep3/" + resumeId;
        }
    });
}

function getListToPrev(){
    var certiSize = $("input[name='certificateName']").length;
    var careerSize = $("input[name='careerCompanyName']").length;
    var resumeId = $("input[name='resumeId']").val();

    let CertificateDTO = function (resumeId, certiId, certificateName, certiGetDate, certiScore){
        this.resumeId = resumeId;
        this.certiId = certiId;
        this.certificateName = certificateName;
        this.certiGetDate = certiGetDate;
        this.certiScore = certiScore;
    }

    let CareerDTO = function (careerId, careerCompanyName, careerJoinedDate, careerRetireDate, careerTask, resumeId){
        this.resumeId = resumeId;
        this.careerId = careerId;
        this.careerCompanyName = careerCompanyName;
        this.careerJoinedDate = careerJoinedDate;
        this.careerRetireDate = careerRetireDate;
        this.careerTask = careerTask;
    }

    var certificateList = [];
    for(i = 0; i < certiSize; i++){
        var certificateDTO = new CertificateDTO(
            $("input[name='resumeId']").val(),
            $("input[name='certiId']").eq(i).val(),
            $("input[name='certificateName']").eq(i).val(),
            $("input[name='certiGetDate']").eq(i).val(),
            $("input[name='certiScore']").eq(i).val()
        )
        certificateList.push(certificateDTO);
    }

    var careerList = [];
    for(i = 0; i < careerSize; i++){
        var careerDTO = new CareerDTO(
            $("input[name= 'careerId']").eq(i).val(),
            $("input[name='careerCompanyName']").eq(i).val(),
            $("input[name='careerJoinedDate']").eq(i).val(),
            $("input[name='careerRetireDate']").eq(i).val(),
            $("textarea[name='careerTask']").eq(i).val(),
            $("input[name='resumeId']").val()
        )
        careerList.push(careerDTO);
    }

    $.ajax({
        type: "get",
        url: "/resume/insertStep2",
        data: {"certificateList" : JSON.stringify(certificateList), "careerList" : JSON.stringify(careerList)},
        success: function(data) {
            location.href = "/resume/goPreviousStep1/" + resumeId;
        }
    });
}

//STEP2용 경력 추가
function addCareerInfo() {
    maxDate();     // 추가되는 애들한테는 최댓값이 적용이 안됨
    var careerInfo = '';

    careerInfo += '<div class="row" style="float: inline-start;">';
    careerInfo += '<div class="col-md-6" style="width: 400px;">';
    careerInfo += '<label>회사명</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="text" name="careerCompanyName">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 200px;">';
    careerInfo += '<label>근무기간</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="date" name="careerJoinedDate">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 5px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '~';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 200px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control careerRetireDate" type="date" name="careerRetireDate" max="">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="form-group mb-4">';
    careerInfo += '<label>주요업무</label>';
    careerInfo += '<textarea class="form-control" placeholder="근무했던 부서 및 담당 업무를 간단하게 작성해 주세요." id="message" rows="3" name="careerTask"></textarea>';
    careerInfo += '</div>';

    $(".addCareerInfo").replaceWith(careerInfo);
}

//STEP2WithContent용 경력 추가
function addCareerInfoWC(data) {
    maxDate();
    var careerInfo = '';

    careerInfo += '<div class="row" style="float: inline-start;">';
    careerInfo += '<div class="col-md-6" style="width: 400px;">';
    careerInfo += '<label>회사명</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="text" name="careerCompanyName">';
    careerInfo += '</div>';
    careerInfo += '<input type="hidden" value="' + data + '" name="careerId">';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 200px;">';
    careerInfo += '<label>근무기간</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="date" name="careerJoinedDate">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 5px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '~';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-6" style="width: 200px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control careerRetireDate" type="date" name="careerRetireDate" max="">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="form-group mb-4">';
    careerInfo += '<label>주요업무</label>';
    careerInfo += '<textarea class="form-control" placeholder="근무했던 부서 및 담당 업무를 간단하게 작성해 주세요." id="message" rows="3" name="careerTask"></textarea>';
    careerInfo += '</div>';

    $(".addCareerInfo").replaceWith(careerInfo);
}

//입력검증
//날짜 최댓값을 오늘로 설정
function maxDate(){
    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = today.getMonth() + 1;  //1월이 0임
    var dd = today.getDate();

    if(dd < 10){
        dd = '0' + dd;
    }
    if(mm < 10){
        mm = '0' + mm;
    }

    today = yyyy + '-' + mm + '-' + dd;
    $(".eduGraduationDate").attr("max", today);
    $(".careerRetireDate").attr("max", today);
    $(".certiGetDate").attr("max", today);
}

function careerRetireDateMin(){     // min에는 함수를 쓸 수 없음, 같은 div안에 있는 careerJoinedDate값을 최솟값으로 가져야 함
    return $(".careerJoinedDate").val();
}

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