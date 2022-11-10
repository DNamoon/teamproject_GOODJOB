/**
 * 박채원 22.10.23 작성
 */

$(document).ready(function () {
    var pageNum = 0;
    getJSONResumeList();
    getApplyList(pageNum);
    setMenuValue();

    //이력서 등록버튼 누르면 이력서 번호부터 등록하고 시작
    $("#registerResume").click(function () {
        var size = $("h5[name='resumeTitle']").length;

        if (size >= 5) {
            Swal.fire({
                title: '이미 이력서를 5개 작성하였습니다',
                icon: 'error'
            });
        } else {
            $.ajax({
                type: "get",
                url: "/resume/registerResume",
                dataType: "text",
                success: function (data) {
                    var openNewWindow = window.open("about:blank");
                    openNewWindow.location.href = "/resume/resumeStep1/" + data;
                    getJSONResumeList($("input[id=sessionInput]").val());
                }
            });
        }
    })
})

//이력서 리스트 출력
function getJSONResumeList() {
    //지원하기와 이력서 관리페이지의 이력서 리스트 뿌리는 과정을 같은 메소드를 사용해 구현하려고 type을 줌
    var type = "my";
    $.getJSON('/member/getResumeList/' + type, function (arr) {
        var list = '';

        if(arr.length > 0){
            $.each(arr, function (idx, resume) {
                list += '<div class="row border-bottom">';
                list += '<div class="col-2 mt-4 text-center" style="margin-top: 8px">';
                list += '<input class="form-check-input me-1" type="checkbox" id="resumeCheckBox" value="' + resume.resumeId + '" name="resumeCheckBox" style="background-color: #e4e1e4">'
                list += '</div>';
                list += '<div class="col-8 mt-4" style="margin-top: 8px">';
                list += '<h5 class="text-bold" name="resumeTitle" onclick="changeTitleForm(this)">' + resume.resumeTitle + '</h5>';
                list += '<h6 style="color: #bbb8bb">이력서 등록날짜 | ' + resume.resumeUpdateDate + '</h6>';
                list += '</div>';
                list += '<div class="col-2">';
                list += '<input id="resumeId" type="hidden" value="' + resume.resumeId + '">'

                if(resume.submitted === true){
                    list += '<h6 style="color: #bbb8bb; text-align: center">' + '작성완료 ' + '</h6>';
                }else{
                    list += '<h6 style="color: #bbb8bb; text-align: center">' + '작성중 ' + '</h6>';
                }
                
                list += '<button type="button" class="btn btn-sm btn-outline-secondary form-control" style="margin-bottom: 8px" onclick="updateResume(this)">이력서수정</button>';
                list += '<br>';
                list += '<button type="button" class="btn btn-sm btn-outline-danger form-control" style="margin-bottom: 8px" onclick="deleteResume(this)">이력서삭제</button>';
                list += '</div>';
                list += '</div>';
            })
        } else{
            list += '<h6 class="text-center">작성한 이력서가 없습니다.</h6>';
        }

        $(".resumeList").html(list);
    })
}

//이력서 개별 삭제
function deleteResume(data) {
    var resumeIdList = [];
    var resumeId = $(data).parent().find("input[id=resumeId]").val();
    resumeIdList.push(resumeId);

    Swal.fire({
        title: '이력서를 삭제하시겠습니까?',
        text: '삭제된 이력서는 복구할 수 없습니다.',
        icon: 'warning',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#344767', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '확인', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로

    }).then(result => {
        // 만약 Promise리턴을 받으면,
        if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
            $.ajax({
                url: "/resume/deleteResume",
                type: "get",
                data: {"resumeId": JSON.stringify(resumeIdList)},
                success: function (result) {
                    if (result === 'success') {
                        Swal.fire('이력서가 삭제되었습니다', '', 'success');
                        getJSONResumeList();
                    }
                }
            });
        }else {
            getJSONResumeList();
        }
    });
}

//이력서 수정
function updateResume(data) {
    var resumeId = $(data).parent().find("input[id=resumeId]").val();
    var openNewWindow = window.open("about:blank");
    openNewWindow.location.href = "/resume/goPreviousStep1/" + resumeId;
}

//이력서 제목 수정폼
function changeTitleForm(data) {
    var titleInput = '';
    titleInput += '<input class="form-control col-md-8" type="text" name="changeTitle" placeholder="ex) OO회사 이력서, OO직종 이력서">';
    titleInput += '<button class="btn btn-sm btn-outline-secondary" type="button" onclick="changeTitle(this)">수정</button>';

    $(data).replaceWith(titleInput);
}

//이력서 제목 수정
function changeTitle(data) {
    var resumeId = $(data).parent().parent().find("input[id=resumeId]").val();
    var title = $("input[name=changeTitle]").val();

    if(title == ''){
        title = '제목 없음';
    }

    $.ajax({
        url: "/resume/changeTitle/" + resumeId,
        type: "get",
        data: {"title": title},
        success: function () {
            getJSONResumeList();
        }
    })
}

//체크된 이력서들을 삭제함
function deleteCheckedResume() {
    var checkedList = [];
    var size = $("input:checkbox[name=resumeCheckBox]:checked").length;

    for (i = 0; i < size; i++) {
        checkedList.push($("input:checkbox[name=resumeCheckBox]:checked").eq(i).val());
    }
    ;

    Swal.fire({
        title: '이력서를 삭제하시겠습니까?',
        text: '삭제된 이력서는 복구할 수 없습니다.',
        icon: 'warning',

        showCancelButton: true,
        confirmButtonColor: '#344767',
        cancelButtonColor: '#d33',
        confirmButtonText: '확인',
        cancelButtonText: '취소',

        reverseButtons: true,

    }).then(result => {
        if (result.isConfirmed) {
            $.ajax({
                url: "/resume/deleteResume",
                type: "get",
                data: {"resumeId": JSON.stringify(checkedList)},
                success: function (result) {
                    if (result === 'success') {
                        Swal.fire('이력서가 삭제되었습니다', '', 'success');
                        getJSONResumeList();
                    }
                }
            });
        }else {
            getJSONResumeList();
        }
    });
}

//지원현황 리스트
function getApplyList(pageNum) {
    $.getJSON('/status/getApplyList/' + pageNum, function (result) {
        var list = '';
        var loginId = $("#sessionInput").val();

        if(result.dtoList.length > 0){
            $.each(result.dtoList, function (applyIdx, apply) {
                list += '    <tr>\n' +
                    '      <th scope="row">' + (applyIdx + 1) + '</th>\n' +
                    '      <td><a onclick="alertDeleted(' + apply.statPostId + ')">' + apply.postName.substr(0, 10) + "..." + '</a></td>\n' +
                    '      <td>' + apply.companyName + '</td>\n' +
                    '      <td><a href="/resume/resumeRead/' + loginId + '/'+ apply.statResumeId +'" target="_blank">' + apply.resumeTitle.substr(0, 6) + "..." + '</a></td>\n' +
                    '      <td>' + apply.statApplyDate + '</td>\n' +
                    '      <td>' + apply.statPass + '</td>\n' +
                    '    </tr>\n';
            })

            $(".applyTable").html(list);

            var pageBtn = '';

            pageBtn += '<li class="page-item" th:if="${'+ result.prev +'}">';
            pageBtn += '<a class="page-link" onclick="getApplyList(' + (result.start - 1) + ')" tabindex="-1"><<</a>';
            pageBtn += '</li>';
            for(i = 0; i < result.totalPage; i++){
                pageBtn += '<a class="page-link" onclick="getApplyList('+ i +')"><li class="page-item">'+ (i + 1) +'</li></a>';
            }
            pageBtn += '<li class="page-item" th:if="${'+ result.next +'}">';
            pageBtn += '<a class="page-link" onclick="getApplyList(' + (result.end) + ')">>></a>';
            pageBtn += '</li>';

            $(".pagination").html(pageBtn);
        }else {
            list += '<h6 class="text-center">지원한 회사가 없습니다.</h6>';
            $(".noData").html(list);
        }

    });
}

function alertDeleted(id){
    if(id === 0){
        Swal.fire({
            title: '탈퇴한 기업의 공고입니다',
            test: '더 이상 공고를 열람할 수 없습니다.',
            icon: 'error'
        });
    }else{
        location.href = "/post/readPost/" + id;
    }
}

function getMenuValue(){
    var valueJson = {};
    var valueArray = [];

    $.ajax({
        url: "/resume/getMenuValue",
        type: "get",
        async: false,
        dataType: "json",
        success(data){
            $.each(data, function(key, value){
                valueJson.id = key;
                valueJson.val = value;
                valueArray.push({...valueJson});
            });
        }
    })
    return valueArray;
}

function setMenuValue(){
    $(".resumeCount").text(getMenuValue()[0].val);
    $(".applyCount").text(getMenuValue()[1].val);
    $(".passCount").text(getMenuValue()[2].val);
    $(".unPassCount").text(getMenuValue()[3].val);
}