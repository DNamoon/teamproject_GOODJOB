/**
 * 박채원 22.10.23 작성
 */

$(document).ready(function(){
    getJSONResumeList(5);
})

function getJSONResumeList(memId){
    $.getJSON('/member/getResumeList/' + memId, function(arr){
        console.log(arr);

        var list = '';

        $.each(arr, function(idx, resume){
            list += '<div class="row">';
            list += '<div class="col-2 text-center" style="margin-top: 8px">';
            list += '<input type="hidden" value="' + resume.resumeId + '">'
            list += '<input class="form-check-input me-1" type="checkbox" value="" id="firstCheckbox" style="background-color: #e4e1e4">'
            list += '</div>';
            list += '<div class="col-8" style="margin-top: 8px">';
            list += '<h5 class="text-bold">' + resume.resumeTitle + '</h5>';
            list += '<h6 style="color: #bbb8bb">최종수정날짜 | ' + resume.resumeUpdateDate + '</h6>';
            list += '</div>';
            list += '<div class="col-2">';
            list += '<button type="button" class="btn btn-sm btn-outline-secondary" style="margin-bottom: 8px">이력서수정</button>';
            list += '<br>';
            list += '<button type="button" class="btn btn-sm btn-outline-danger" style="margin-bottom: 8px">이력서삭제</button>';
            list += '</div>';
            list += '</div>';
            list += '<hr style="margin-bottom: 25px">';
        })

        $(".resumeList").html(list);
    })
}