/**
 * 박채원 22.10.26 작성
 */

$(document).ready(function(){
    $(".applyBtn").click(function(){
        getJSONResumeList();
    });

    $(".doneSelectResume").click(function(){
        doneSelectResume();
    });
});

//지원하기 버튼 눌렀을 때 작성한 이력서 리스트 출력
function getJSONResumeList() {
    $.getJSON('/member/getResumeList', function (arr) {
        var list = '';

        $.each(arr, function (idx, resume) {
            list += '<div class="row">';
            list += '<div class="col text-center" style="margin-top: 8px">';
            list += '<input class="form-check-input me-1" type="radio" value="' + resume.resumeId + '" name="selectResumeId" style="background-color: #e4e1e4">'
            list += '</div>';
            list += '<div class="col-8" style="margin-top: 8px">';
            list += '<h5 class="text-bold" name="resumeTitle">' + resume.resumeTitle + '</h5>';
            list += '<h6 style="color: #bbb8bb">이력서 등록날짜 | ' + resume.resumeUpdateDate + '</h6>';
            list += '<input id="resumeId" type="hidden" value="' + resume.resumeId + '">'
            list += '</div>';
            list += '</div>';
        })

        $(".resumeList").html(list);
    })
}

function doneSelectResume(){
    var selectResumeId = $("input:radio[name='selectResumeId']:checked").val();
    var postId = $("#postId").val();

    $.ajax({
        url: "/status/applyResume/" + postId,
        type: "post",
        data : {selectResumeId : selectResumeId},
        success: function() {
            Swal.fire({
                title: '이력서를 지원하였습니다',
                text: '서류 결과는 메일 및 마이페이지에서 확인할 수 있습니다.',
                icon: 'info'
            });
        }, error: function () {
            Swal.fire({
                title: '이미 지원한 회사입니다',
                text: '지원 결과는 메일 및 마이페이지에서 확인해 주세요.',
                icon: 'error'
            });
        }
    })
}