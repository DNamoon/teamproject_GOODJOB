/**
 * 박채원 22.10.26 작성
 */

$(document).ready(function(){
    $(".applyBtn").click(function(){
        getJSONResumeList($("input[id=sessionInput]").val());
    });

    $(".doneSelectResume").click(function(){
        doneSelectResume();
    });
});

function getJSONResumeList(memId) {
    $.getJSON('/member/getResumeList/' + memId, function (arr) {
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
            //지원완료되면 뭐라해야할지 모르겠어
        }
    })
}