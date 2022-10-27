//
// $(document).ready(function(){
//     postJS.init();
//
// })

const url = "localhost:8080";
const uri ="post/"
let postJS = {
    init:function (){
        let _this = this;
        _this.list();
    },

    list:function(sortType){
        const pageRequestDTO = {
            page:1,
            size: 8,
            sort : sortType
        }
        post(url,'getPagingPostList',pageRequestDTO)
            .then(data => {
                console.log("data :" + data);

                // ajax 받아오기 전에 자식노드들 삭제
                const changeContentDiv = document.querySelector(".changeContentDiv");
                while(changeContentDiv.hasChildNodes()){
                    changeContentDiv.removeChild(changeContentDiv.firstChild);
                }
                for(let dto of data.dtoList){
                    console.log(dto);
                    this.makePostListHtml(dto,changeContentDiv);
                }

            })
            .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
    },
    makePostListHtml:function(dto,changeContentDiv){
        // 공고 html 생성 코드
        const postWrapper = document.createElement("div");
        postWrapper.classList.add("mainPage-wrapper");
        const goToPostDetails = document.createElement("a");
        goToPostDetails.setAttribute("href",`/mainPage/read?postId=${dto.id}`);
        const div1 = document.createElement("div");
        div1.classList.add("col", "p-0");
        const div2 = document.createElement("div");
        div2.classList.add("p-0", "m-2", "card-css");

        const div3 = document.createElement("div");
        div3.classList.add("w-100","p-0","h-50","img-css");
        const img = document.createElement("img");
        img.setAttribute("src","https://t1.daumcdn.net/cfile/tistory/237DF34C53EA159E08");
        img.classList.add("w-100","h-100","card-img-post");
        div3.appendChild(img);

        const div4 = document.createElement("div");
        div4.classList.add("h-41", "align-self-auto","pt-3","m-0","card-body-css");
        const p1 = document.createElement("p");
        p1.classList.add("card-title-post");
        p1.innerHTML=`${dto.title}`;
        div4.appendChild(p1);
        const p2 = document.createElement("p");
        p2.classList.add("card-company-post");
        p2.innerHTML=`${dto.comName}`;
        div4.appendChild(p2);
        const p3 = document.createElement("p");
        p3.classList.add("card-text-post");
        p3.innerHTML=`연봉 | ${dto.salaryRange}`;
        div4.appendChild(p3);
        const p4 = document.createElement("p");
        p4.classList.add("card-text-post");
        p4.innerHTML=`위치 | ${dto.regionName}`;
        div4.appendChild(p4);
        const p5 = document.createElement("p");
        p5.classList.add("card-text-post");
        p5.innerHTML=`직종 | ${dto.occName}`;
        div4.appendChild(p5);
        const p6 = document.createElement("p");
        p6.classList.add("card-dDay-post");
        p6.innerHTML=`D - ${dto.remainDay}`;
        div4.appendChild(p6);

        div2.appendChild(div3);
        div2.appendChild(div4);
        div1.appendChild(div2);
        goToPostDetails.appendChild(div1);
        postWrapper.appendChild(goToPostDetails);
        changeContentDiv.appendChild(postWrapper);



    }

}
// ======================== FETCH FUNCTION(custom) ==========================================

// GET FETCH
const get = async function get(host, path, body, headers ={}){
    const url = `http://${host}/${path}`;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            ...headers,
        },
        body: JSON.stringify(body), // 객체를 Json 형식으로 변환한다.
    };
    const response = await fetch(url, options); // Response {status: 200, ok: true, redirected: false, type: "cors", url: "url", …}
    const data = await response.json();
    console.log("response =============")
    console.log(response);
    console.log("data =============")
    console.log(data);
    return response.ok ? data : new Error(response); // Error: [object Response]
}
// GET FETCH HTML

// POST FETCH
const post = async function post(host, path, body, headers ={}){
    const url = `http://${host}/${path}`;
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...headers,
        },
        body: JSON.stringify(body),
    };
    const response = await fetch(url, options); // Response {type: "cors", url: "url", redirected: false, status: 201, ok: true, …}
    const data = await response.json();
    return response.ok ? data : new Error(data);
}