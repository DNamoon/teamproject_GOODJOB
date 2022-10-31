$(document).ready(function(){
    console.log("실행");
    var pageNum = 0;
    getApplierList(pageNum);
})

function getApplierList(pageNum){
    $.getJSON('/status/getApplierList/' + pageNum, function (result){
        var list = '';
        console.log(result);

        $.each(result.dtoList, function (applierIdx, applier){
            if(applierIdx % 3 === 0){
                list += '<div class="row">\n';
            }

            list += '<div class="col-6 col-sm-4 infoCard">\n'+
                '<div class="card" style="width: 18rem; text-align: center;">\n' +
                '  <div class="card-body">\n' +
                '    <h5 class="card-title">' + applier.applierName + '</h5>\n' +
                '    <h6 class="card-subtitle mb-2 text-muted">'+applier.applierGender + ' / ' + applier.applierAge + '</h6>\n' +
                '    <p class="card-text">' + applier.postTitle + '</p>\n' +
                '    <p class="card-text">' + applier.postOccupation + '</p>\n' +
                '    <a href="/resume/resumeRead/'+ applier.statResumeId +'" target="_blank"><button class="btn btn-sm btn-info">이력서 열람</button></a>';

            if(applier.statPass == '합격'){
                list += '<p class="card-text">합격</p>'+
                    '  </div>\n' +
                    '  </div>\n' +
                    '  </div>\n'
            }else if(applier.statPass == '불합격'){
                list += '<p class="card-text">불합격</p>'+
                    '  </div>\n' +
                    '  </div>\n' +
                    '  </div>\n'
            }else{
                list += '    <div class="btn-group-sm btn' + applier.statId + '">\n'+
                    '    <button class="btn btn-sm btn-info" onclick="clickPass('+ applier.statId +')">합격</button>\n' +
                    '    <button class="btn btn-sm btn-danger" onclick="clickUnPass('+ applier.statId +')">불합격</button>\n' +
                    '  </div>\n' +
                    '  </div>\n' +
                    '  </div>\n' +
                    '</div>';
            }

            if(applierIdx % 3 === 2){
                list += '</div>\n' +
                '<div class="container" style="height: 2rem;"></div>';
            }
        })

        $(".applierList").html(list);

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
    $.ajax({
        url: "/status/changePass/" + statId,
        type: "get",
        success: function(){
            alert("합격 처리하였습니다.");
            getApplierList(0);
        }
    })
}

function clickUnPass(statId){
    $.ajax({
        url: "/status/changeUnPass/" + statId,
        type: "get",
        success: function(){
            alert("불합격 처리하였습니다.");
            getApplierList(0);
        }
    })
}