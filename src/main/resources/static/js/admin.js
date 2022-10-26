import {get, post} from "./util/fetch.js"


$(document).ready(function(){
    admin.init();

})

const url = "localhost:8080"
const uri = "o/" // 직종 관련 매핑주소
let admin = {

    init: function (){
        let _this = this;
        _this.getAll();
        _this.getOccID();
    },

    // 직종 전체 리스트를  fetch로 받는 함수
    getAll: function (){

        const showBtn = document.querySelector('.showBtn');
        showBtn.addEventListener('click',function (){
            $(`.showBtn`).hide();
            $(`.hideBtn`).show();
            fetchAll();
            $(`.occList`).show();

        }, false);

        const hideBtn = document.querySelector('.hideBtn');
        hideBtn.addEventListener('click',function (){
            $(`.showBtn`).show();
            $(`.hideBtn`).hide();
            $(`.occList`).hide();

        }, false);

        function fetchAll(){
            get(url,uri+'getAll')
                .then(data => {
                    console.log(data);
                    let occList = null;
                    occList = data.dtoList;
                    appendBtn(occList);
                })
                .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
            return null;
        }


        // 직종 전체 리스트 버튼을 dom 에 추가하는 함수
        function appendBtn(occList){
            const prevElement = document.querySelector('.occListForRemove');
            if(prevElement !== null){
                prevElement.remove();
            }
            const a = document.createElement('div');
            a.classList.add("btn-toolbar", "col-sm-10", "occListForRemove");
            a.setAttribute("role","toolbar");
            a.setAttribute("id","admin");
            a.setAttribute("aria-label","Toolbar with button groups");

            const b = document.createElement('div');
            b.classList.add("btn-group", "me-1", "mt-1", "mb-1");
            b.setAttribute("role","group");

            const occListElement = document.querySelector('.occList')
            occListElement.appendChild(a);
            a.appendChild(b);



            occList.forEach(e=>{
                const c = document.createElement('button');
                const d = {
                    occId : `${e.occId}`,
                    occCode: `${e.occCode}`,
                    occName: `${e.occName}`
                }
                c.setAttribute("type","button");
                c.classList.add("btn", "btn-info", "m-1");
                c.setAttribute("value",`${e.occId}`);
                c.textContent = `${e.occName}`
                b.appendChild(c);
            })
        }

    },
    get: function (){

    },
    update: function (){

    },
    delete: function (){

    },
    getOccID: function (){
        let occName;
        $('.findIdByOccName').click(()=>{
                // occName = document.querySelector('#occCodeInput-get').value
                occName = $('#occCodeInput-get').val();
                console.log(occName);
                const json = {"occName":occName};
                console.log(json);
                fetchOcc(json);
            }
        )

        function fetchOcc(json){
            post(url,uri+'get',json)
                .then(data => {
                    console.log(data);
                    $('#occCodeInput-get-result').val(data.occId);
                })
                .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
            return null;
        }
    }
}



