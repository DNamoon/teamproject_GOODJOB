/**
 * 박채원 22.10.23 작성
 */

$(document).ready(function () {
    getJSONResumeList(5);   //memId를 받아와야하는데 어떻게?

    //이력서 등록버튼 눌리면 이력서 번호부터 등록하고 시작
    $("#registerResume").click(function () {
        var size = $("h5[name='resumeTitle']").length;

        if (size >= 5) {
            alert("이미 이력서를 5개 작성하였습니다.");
        } else {
            $.ajax({
                type: "get",
                url: "/resume/registerResume",
                dataType: "text",
                success: function (data) {
                    location.href = "/resume/resumeStep1/" + data;
                }
            });
        }
    })
})

function getJSONResumeList(memId) {
    $.getJSON('/member/getResumeList/' + memId, function (arr) {
        var list = '';

        $.each(arr, function (idx, resume) {
            list += '<div class="row">';
            list += '<div class="col-2 text-center" style="margin-top: 8px">';
            list += '<input class="form-check-input me-1" type="checkbox" value="" id="firstCheckbox" style="background-color: #e4e1e4">'
            list += '</div>';
            list += '<div class="col-8" style="margin-top: 8px">';
            list += '<h5 class="text-bold" name="resumeTitle">' + resume.resumeTitle + '</h5>';
            list += '<h6 style="color: #bbb8bb">최종수정날짜 | ' + resume.resumeUpdateDate + '</h6>';
            list += '</div>';
            list += '<div class="col-2">';
            list += '<input id="resumeId" type="hidden" value="' + resume.resumeId + '">'
            list += '<button type="button" class="btn btn-sm btn-outline-secondary" style="margin-bottom: 8px" onclick="updateResume(this)">이력서수정</button>';
            list += '<br>';
            list += '<button type="button" class="btn btn-sm btn-outline-danger" style="margin-bottom: 8px" onclick="deleteResume(this)">이력서삭제</button>';
            list += '</div>';
            list += '</div>';
            list += '<hr style="margin-bottom: 25px">';
        })

        $(".resumeList").html(list);
    })
}

//이력서 삭제
function deleteResume(data) {
    var resumeId = $(data).parent().find("input[id=resumeId]").val();
    console.log(resumeId);


    if (confirm("이력서를 삭제하겠습니까?") == true) {
        $.ajax({
            url: "/resume/deleteResume/" + resumeId,
            type: "get",
            success: function (result) {
                if (result === 'success') {
                    alert("이력서가 삭제되었습니다.");
                    getJSONResumeList(5);
                }
            }
        });
    } else{
        getJSONResumeList(5);
    }
}

//이력서 수정
function updateResume(data) {
    var resumeId = $(data).parent().find("input[id=resumeId]").val();
    location.href = "/resume/goPreviousStep1/" + resumeId;
}