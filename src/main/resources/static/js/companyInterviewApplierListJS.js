$(document).ready(function () {
    var pageNum = 0;
    var postId = $("#postId").val();
    getApplierList(postId, pageNum);
})

//지원자 리스트 출력
function getApplierList(postId, pageNum) {
    $.getJSON('/status/getIntervieweeList/' + postId + '/' + pageNum, function (result){
        var list = '';
        $(".postTitle").text("공고명 [" + result.dtoList.applierName + "] 의 서류 합격자입니다.");

        $.each(result.dtoList, function (applyIdx, applier) {
            list += '    <tr>\n' +
                '      <th scope="row">' + (applyIdx + 1) + '</th>\n' +
                '      <td>' + applier.applierName + '</td>\n' +
                '      <td>' + applier.postOccupation + '</td>\n';

            if(applier.interviewPlace === null){
                list += '      <td><input class="form-control" type="text" id="interviewPlace" placeholder="면접장소를 지정해주세요"></td>\n' +
                    '      <td><input class="form-control" type="datetime-local" id="interviewDate"></td>\n' +
                    '      <td><button class="btn btn-sm btn-secondary" onclick="sendMail(this,' + applier.statId + ' )">메일전송</button></td>\n';
            }else{
                list += '      <td>' + applier.interviewPlace + '</td>\n' +
                    '      <td>' + applier.interviewDate + '</td>\n'+
                    '      <td>메일전송완료</td>\n';
            }

            if(applier.statPass === '최종합격'){
                list += '      <td style="color: #0a53be;">' + applier.statPass + '</td>\n';
            }else if(applier.statPass === '면접불합격'){
                list += '      <td style="color: red;">' + applier.statPass + '</td>\n';
            }
            else{
                list += '<td class="passBtn' + applier.statId + '"><button class="btn btn-sm btn-info" onclick="clickPass('+ applier.statId +')">합격</button>' +
                    '<button class="btn btn-sm btn-danger" onclick="clickUnPass('+ applier.statId +')">불합격</button>' +
                    '</td>';
            }

            list += '    </tr>\n';
        })

        $(".applierTable").html(list);

        var pageBtn = '';

        pageBtn += '<li class="page-item" th:if="${'+ result.prev +'}">';
        pageBtn += '<a class="page-link" onclick="getApplierList(' + (result.start - 1) + ')" tabindex="-1"><<</a>';
        pageBtn += '</li>';
        for(i = 0; i < result.totalPage; i++){
            pageBtn += '<a class="page-link" onclick="getApplierList('+ i +')"><li class="page-item">'+ (i + 1) +'</li></a>';
        }
        pageBtn += '<li class="page-item" th:if="${'+ result.next +'}">';
        pageBtn += '<a class="page-link" onclick="getApplierList(' + (result.end) + ')">>></a>';
        pageBtn += '</li>';

        $(".pagination").html(pageBtn);
    })
}

function clickPass(statId) {
    if (confirm("면접 합격 처리하시겠습니까?")) {
        $.ajax({
            url: "/status/changePass/" + statId,
            type: "get",
            data: {applyResult : "최종합격"},
            success: function () {
                Swal.fire({
                    title: '최종합격 처리하였습니다',
                    icon: 'info'
                });
                $(".passBtn" + statId).replaceWith('<td style="color: #0a53be;">최종합격</td>');
            }
        })
    }
}

function clickUnPass(statId) {
    if (confirm("면접 불합격 처리하시겠습니까?")) {
        $.ajax({
            url: "/status/changeUnPass/" + statId,
            type: "get",
            data: {applyResult : "면접불합격"},
            success: function () {
                Swal.fire({
                    title: '불합격 처리하였습니다',
                    icon: 'info'
                });
                $(".passBtn" + statId).replaceWith('<td style="color: red;">면접불합격</td>');
            }
        })
    }
}

function sendMail(data, statId){
    var place = $(data).parent().parent().find("input[id=interviewPlace]").val();
    var date = $(data).parent().parent().find("input[id=interviewDate]").val();
    var postId = $("#postId").val();

    $.ajax({
        url: "/status/updateInterviewInfo/" + statId,
        type: "get",
        data: {place : place, date : date},
        success: function (){
            Swal.fire({
                title: '메일을 전송하였습니다',
                icon: 'info'
            });
            getApplierList(postId, 0);
        }
    })
}