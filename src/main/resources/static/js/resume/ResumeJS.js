$(document).ready(function(){
    maxDate();
    isXBtnDisabled();

    //뒤로가기 방지
    $(document).keydown(function(e){
        if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA"){
            if(e.keyCode === 8){
                return false;
            }
        }
    });

    window.history.forward(0);

    var size = $("input[name='certificateName']").length;
    for(var i = 1; i < size; i++){
        $("input[name='certificateName']").eq(i).attr("id","certificateName"+i);
    }
    
    //학교 찾기
    $(".findSchool").click(function(){
        var schoolName = $(".findSchoolName").val();
        $(".schoolList").empty();

        $.ajax({
            type: "get",
            url: "/resume/findSchool/" + schoolName,
            dataType: "json",
            async: false,
            success: function (data){
                if(data.length > 0){
                    for(var i=0; i<data.length; i++){
                        $(".schoolList").append('<input class="form-check-input me-1" type="radio" name="selectSchoolName" value="' + data[i].schName +'" style="background-color: #e4e1e4">' + data[i].schName +'<br/>');
                    }
                    $(".doneFindSchool").click(function (){
                        $("#schoolName").attr("value", $("input:radio[name='selectSchoolName']:checked").val());
                        $(".schoolList").empty();
                        $(".findSchoolName").empty();
                        confirmValidSchool();
                    });
                }else{
                    $(".schoolList").append('<h6 class="text-center">검색결과가 없습니다.</h6>');
                }
            }
        });
        $(".close").click(function(){
            $(".schoolList").empty();
            $(".findSchoolName").empty();
            confirmValidSchool();
        })
    });

    //전공 찾기
    $(".findMajor").click(function(){
        var majorName = $(".findMajorName").val();
        $(".majorList").empty();

        $.ajax({
            type: "get",
            url: "/resume/findMajor/" + majorName,
            dataType: "json",
            async: false,
            success: function (data){
                if(data.length > 0){
                    for(var i=0; i<data.length; i++){
                        $(".majorList").append('<input class="form-check-input me-1" type="radio" name="selectMajorName" value="' + data[i].majName +'" style="background-color: #e4e1e4">' + data[i].majName +'<br/>');
                    }
                    $(".doneFindMajor").click(function (){
                        $("#majorName").attr("value", $("input:radio[name='selectMajorName']:checked").val());
                        $(".majorList").empty();
                        $(".findMajorName").empty();
                        confirmValidMajor();
                    });
                }else{
                    $(".majorList").append('<h6 class="text-center">검색결과가 없습니다.</h6>');
                }
            }
        });
        $(".close").click(function(){
            $(".majorList").empty();
            $(".findMajorName").empty();
            confirmValidMajor();
        })
    });

    //자격증 찾기
    $(".findCerti").click(function () {
        var certiName = $(".findCertiName").val();
        $(".certiList").empty();

        $.ajax({
            type: "get",
            url: "/resume/findCerti/" + certiName,
            dataType: "json",
            async: false,
            success: function (data) {
                if(data.length > 0){
                    for (var i = 0; i < data.length; i++) {
                        $(".certiList").append('<input class="form-check-input me-1" type="radio" name="selectCertiName" value="' + data[i].certiName + '" style="background-color: #e4e1e4">' + data[i].certiName + '<br/>');
                    }
                    $(".doneFindCerti").click(function () {
                        //이미 작성한 자격증인지 확인하고 작성하지 않은 자격증일 경우에만 값이 입력되도록함(중복 방지)
                        var inputId = $("#modalId").val();
                        var checkCerti = [];
                        var inputSize = $("input[name=certificateName]").length;

                        for(var i = 0; i < inputSize; i++){
                            if($("input:radio[name=selectCertiName]:checked").val() !== $("input[name=certificateName]").eq(i).val()){
                                checkCerti.push('같지않음');
                            }else{
                                checkCerti.push('같음');
                            }
                        }

                        if(checkCerti.includes('같음')){
                            Swal.fire({
                                title: '이미 작성한 자격증입니다',
                                icon: 'error'
                            });
                            deleteAddInfo($("#" + inputId).parent());
                            $(".certiList").empty();
                            $(".findCertiName").empty();
                        }else{
                            $("#" + inputId).attr("value", $("input:radio[name='selectCertiName']:checked").val());
                            $(".certiList").empty();
                            $(".findCertiName").empty();
                        }
                    });
                }else{
                    $(".certiList").append('<h6 class="text-center">검색결과가 없습니다.</h6>');
                }
            }
        });
        $(".close").click(function(){
            $(".certiList").empty();
            $(".findCertiName").empty();
        })
    });

    //자격증 항목 추가
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
                isXBtnDisabled();
            }
        })
    });

    //경력항목 추가
    $(".addCareerInfo").click(function(){
        addCareerInfo();
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
                isXBtnDisabled();
            }
        })
    });

    //입력 검증하고 step2로 이동
    $("#submitStep1").click(function(){
        step1Valid();
    });

    $("#goBackStep1").click(function(){
        if(checkCertiNCareerBlank()){
            getListToPrev();
        }
    });

    $("#submitStep2").click(function(){
        if(checkCertiNCareerBlank()){
            getListToNext();
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

//STEP2용 자격증 추가
function addCertiInfo() {
    maxDate();
    var certiInfo = '';

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
    certiInfo += '<input class="form-control certiGetDate" type="date" name="certiGetDate" max="" onclick="maxDate()">';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6" style="width: 200px;">';
    certiInfo += '<label>점수</label>';
    certiInfo += '<div class="input-group mb-4">';
    certiInfo += '<input class="form-control" type="text" type="text" name="certiScore">';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6 mx-auto" style="width: 24px;">';
    certiInfo += '<label>&nbsp;</label>';
    certiInfo += '<button type="button" id="deleteCertiBtn" class="btn-close" style="background-color: #96a2b8;" onclick="deleteAddInfo(this)"></button>';
    certiInfo += '</div>';
    certiInfo += '</div>';

    $(".addCertiInfoList").append(certiInfo);
}

//추가한 자격증항목 삭제하기
function deleteAddInfo(data){
    $(data).parent().parent().remove();
}

//STEP2WithContent용 자격증 추가
function addCertiInfoWC(data) {
    maxDate();
    var certiInfo = '';

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
    certiInfo += '<input class="form-control certiGetDate" type="date" name="certiGetDate" max="" onclick="maxDate()">';
    certiInfo += '</div>';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6" style="width: 200px;">';
    certiInfo += '<label>점수</label>';
    certiInfo += '<div class="input-group mb-4">';
    certiInfo += '<input class="form-control" type="text" type="text" name="certiScore">';
    certiInfo += '</div>';
    certiInfo += '<input type="hidden" value="' + data + '" name="certiId">';
    certiInfo += '</div>';
    certiInfo += '<div class="col-md-6 mx-auto" style="width: 24px;">';
    certiInfo += '<label>&nbsp;</label>';
    certiInfo += '<button type="button" id="deleteCertiBtn" class="btn-close" style="background-color: #96a2b8;" onclick="deleteCertiWC(this,' + data + ')"></button>';
    certiInfo += '</div>';
    certiInfo += '</div>';

    $(".addCertiInfoList").append(certiInfo);
}

//Step2WC에서 추가한 자격증 항목 삭제하기
function deleteCertiWC(dataBtn, certiId){
    $.ajax({
        url: "/resume/deleteCertiList/" + certiId,
        type: "get",
        success: function(){
            $(dataBtn).parent().parent().remove();
            isXBtnDisabled();
        }
    });
}

//자격증, 경력 항목을 삭제했을 때 항목의 수가 1개이면 x버튼을 비활성화 함
function isXBtnDisabled(){
    if($("button[id=deleteCertiBtn]").length <= 1){
        $("#deleteCertiBtn").eq(0).attr("disabled", true);
    }else{
        $("#deleteCertiBtn").eq(0).attr("disabled", false);
    }

    if($("button[id=deleteCareerBtn]").length <= 1){
        $("#deleteCareerBtn").eq(0).attr("disabled", true);
    }else{
        $("#deleteCareerBtn").eq(0).attr("disabled", false);
    }
}

//모달에서 선택한 자격증 이름을 같은 열의 input에 넣기위해서 input의 아이디를 모달에 넘겨주는 함수
function getInputId(data){
    $("#modalId").val($(data).parent().parent().find("input").attr("id"));
}

//step2에서 다음 페이지로 갈 때 작성내용을 db에 저장하기 위한 함수
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
            location.replace("/resume/resumeStep3/" + resumeId);
        }
    });
}

//step2에서 이전으로 갈 때 작성내용을 db에 저장하기 위한 함수(위의 함수와 차이점은 ajax의 url)
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
            location.replace("/resume/goPreviousStep1/" + resumeId);
        }
    });
}

//STEP2용 경력 추가
function addCareerInfo() {
    var careerInfo = '';

    careerInfo += '<div>';
    careerInfo += '<div class="row" style="float: inline-start;">';
    careerInfo += '<div class="col-md-6">';
    careerInfo += '<label>회사명</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="text" name="careerCompanyName">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-2" style="width: 200px;">';
    careerInfo += '<label>근무기간</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control careerJoinedDate" id="careerJoinedDate" type="date" name="careerJoinedDate">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-2" style="width: 5px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '~';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-2" style="width: 200px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control careerRetireDate" type="date" name="careerRetireDate" max="" min="" onclick="rangeDate(this)">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div style="width: 10px">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<button type="button" class="btn-close" style="background-color: #96a2b8; display: block;" onclick="deleteCareerInfo(this)"></button>';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="form-group mb-4">';
    careerInfo += '<label>주요업무</label>';
    careerInfo += '<textarea class="form-control" placeholder="근무했던 부서 및 담당 업무를 간단하게 작성해 주세요." id="message" rows="3" name="careerTask"></textarea>';
    careerInfo += '</div>';

    $(".addCareerInfoList").append(careerInfo);
}

//step2에서 추가된 경력항목을 삭제하기 위한 함수
function deleteCareerInfo(data){
    $(data).parent().parent().parent().remove();
}

//STEP2WithContent용 경력 추가
function addCareerInfoWC(data) {
    var careerInfo = '';

    careerInfo += '<div>';
    careerInfo += '<div class="row" style="float: inline-start;">';
    careerInfo += '<div class="col-md-6">';
    careerInfo += '<label>회사명</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control" type="text" name="careerCompanyName">';
    careerInfo += '</div>';
    careerInfo += '<input type="hidden" value="' + data + '" name="careerId">';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-2" style="width: 200px;">';
    careerInfo += '<label>근무기간</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control careerJoinedDate" id="careerJoinedDate" type="date" name="careerJoinedDate">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-2" style="width: 5px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '~';
    careerInfo += '</div>';
    careerInfo += '<div class="col-md-2" style="width: 200px;">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<div class="input-group mb-4">';
    careerInfo += '<input class="form-control careerRetireDate" type="date" name="careerRetireDate" min="" max="" onclick="rangeDate(this)">';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div style="width: 10px">';
    careerInfo += '<label>&nbsp;</label>';
    careerInfo += '<button type="button" id="deleteCareerBtn" class="btn-close" style="background-color: #96a2b8; display: block;" onclick="deleteCareerWC(this,' + data + ')"></button>';
    careerInfo += '</div>';
    careerInfo += '</div>';
    careerInfo += '<div class="form-group mb-4">';
    careerInfo += '<label>주요업무</label>';
    careerInfo += '<textarea class="form-control" placeholder="근무했던 부서 및 담당 업무를 간단하게 작성해 주세요." id="message" rows="3" name="careerTask"></textarea>';
    careerInfo += '</div>';
    careerInfo += '</div>';

    $(".addCareerInfoList").append(careerInfo);
}

//step2WC에서 추가된 경력항목을 삭제하기 위함
function deleteCareerWC(deleteBtn, careerId){
    $.ajax({
        url: "/resume/deleteCareerList/" + careerId,
        type: "get",
        success: function () {
            $(deleteBtn).parent().parent().parent().remove();
            isXBtnDisabled();
        }
    });
}

//이력서 열람 페이지 닫기 버튼
function WinClose() {
    window.open('','_self').close();
}

//이력서 열람 페이지 인쇄 버튼
function WinPrint() {
    var g_oBeforeBody = document.getElementById('readResumeForm').innerHTML;
    window.onbeforeprint = function (ev) {
        document.body.innerHTML = g_oBeforeBody;
    };
    window.print();
    location.reload();
}

//입력검증
//이력서에서 근무기간 선택할 때 두번째 달력의 날짜가 첫번째 날짜보다 적게 입력되는거 방지
function rangeDate(data){
    var minDate = $(data).parent().parent().parent().find("input[id=careerJoinedDate]").val();
    $(data).attr("min", minDate);
    maxDate();
}

//날짜 최댓값을 현재날짜로 제한
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

//휴대폰 번호
function isFourNumber(phoneNum){
    var exp = /([0-9]{4})/;
    if(phoneNum.value.match(exp)){
        $("#phoneNumValiDiv").replaceWith('<label id="phoneNumValiDiv"></label>');
        return true;
    } else {
        $("#phoneNumValiDiv").replaceWith('<label id="phoneNumValiDiv" style="color: red;">4자리 숫자로 입력해주세요</label>');
        phoneNum.focus();
        return false;
    }
}

//학점 - 입력한 값이 총점보다 크지 않도록 검증
function rangeCredit(){
    if(Number($("#eduGetCredit").val()) > Number($('#eduTotalCredit').val().substr(1))){
        Swal.fire({
            title: '입력한 값이 총점보다 큽니다',
            text: '학점을 다시 확인해 주세요.',
            icon: 'error'
        });
        $("#eduGetCredit").val($('#eduTotalCredit').val().replace('/',''));
    }else{
        $("#eduGetCredit").val($('#eduGetCredit').val());
    }
}

//졸업년월 not null 방지
function confirmValidGradDate(){
    if($("input[name=eduGraduationDate]").val() == ''){
        $("#graduDateValiDiv").replaceWith('<label id="graduDateValiDiv" style="color: red;">졸업년월을 입력해주세요.</label>');
        return false;
    }else{
        $("#graduDateValiDiv").replaceWith('<label id="graduDateValiDiv"></label>');
    }
    return true;
}

//학교명 not null 방지
function confirmValidSchool(){
    if($("#schoolName").val() == '') {
        $("#schoolNameValiDiv").replaceWith('<label id="schoolNameValiDiv" style="color: red;">학교명을 입력해주세요.</label>');
        return false;
    } else if($("#schoolName").val() == '응답안함'){
        $("#schoolNameValiDiv").replaceWith('<label id="schoolNameValiDiv" style="color: red;">학교명을 다시 선택해 주세요.</label>');
        return false;
    } else{
        $("#schoolNameValiDiv").replaceWith('<label id="schoolNameValiDiv"></label>');
    }
    return true;
}

//전공명 not null 방지
function confirmValidMajor(){
    if($("#majorName").val() == '') {
        $("#majorNameValiDiv").replaceWith('<label id="majorNameValiDiv" style="color: red;">전공명을 입력해주세요.</label>');
        return false;
    }else if($("#majorName").val() == '응답안함'){
        $("#majorNameValiDiv").replaceWith('<label id="majorNameValiDiv" style="color: red;">전공명을 다시 선택해 주세요.</label>');
        return false;
    } else{
        $("#majorNameValiDiv").replaceWith('<label id="majorNameValiDiv"></label>');
    }
    return true;
}

//자격증명이 있을 때 취득날짜를 선택안한 경우, 회사명이 있을 때 업무,날짜가 없는 경우 방지
function checkCertiNCareerBlank(){
    if($("input[name=certificateName]").val() == ''){
        $("#certiNameValiDiv").replaceWith('<label id="certiNameValiDiv" style="color: red;">자격증명을 입력해주세요.</label>');
        $("input[name=certificateName]").focus();
        return false;
    }else{
        $("#certiNameValiDiv").replaceWith('<label id="certiNameValiDiv"></label>');
    }
    return true;
}

//이메일
function emailDuplication(){
    let emailCheck = $('#firstEmail').val() + "@" + $('#endEmail').val();
    let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
    $('#emailValiDiv').css("color", "#ff0000");
    if (!regExp.test(emailCheck)) {
        $('#emailValiDiv').text("올바른 이메일 양식이 아닙니다.")
    } else {
        $('#emailValiDiv').text("");
    }
}

function step1Valid(){
    if(confirmValidSchool() && confirmValidGradDate() && confirmValidMajor()){
        $("#step1Form").attr("action", "/resume/resumeStep2/" + $("#resumeId").val());
        $("#step1Form").submit();
    }
}




