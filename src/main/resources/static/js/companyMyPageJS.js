$(document).ready(function(){
    console.log("실행");
    var pageNum = 0;
    getApplierList(pageNum);
})

function getApplierList(pageNum){
    $.getJSON('/status/getApplierList/' + pageNum, function (result){
        var list = '';
        console.log(result);

        $.each(result.dtoList, function (applyIdx, applier) {
            list += '    <tr>\n' +
                '      <th scope="row">' + (applyIdx + 1) + '</th>\n' +
                '      <td>' + applier.applierName + '</td>\n' +
                '      <td>' + applier.postTitle.substr(0, 10) + "..." + '</td>\n' +
                '      <td>' + applier.postOccupation + '</td>\n' +
                '      <td>' + applier.statApplyDate + '</td>\n' +
                '    <td><a href="/resume/resumeRead/'+ applier.applierId +'/'+ applier.statResumeId +'" target="_blank"><button class="btn btn-sm btn-info">이력서 열람</button></a></td>\n';

            if(applier.statPass === '합격'){
                list += '      <td style="color: #0a53be;">' + applier.statPass + '</td>\n';
            }else if(applier.statPass === '불합격'){
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

function clickPass(statId){
    if(confirm("합격처리하시겠습니까?")){
        $.ajax({
            url: "/status/changePass/" + statId,
            type: "get",
            success: function(){
                alert("합격 처리하였습니다.");
                $(".passBtn" + statId).replaceWith('<td style="color: #0a53be;">합격</td>');
            }
        })
    }
}

function clickUnPass(statId){
    if(confirm("불합격처리하시겠습니까?")){
        $.ajax({
            url: "/status/changeUnPass/" + statId,
            type: "get",
            success: function(){
                alert("불합격 처리하였습니다.");
                $(".passBtn" + statId).replaceWith('<td style="color: red;">불합격</td>');
            }
        })
    }
}