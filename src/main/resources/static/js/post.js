
$(document).ready(function(){
    postJS.init();

})

const url = "localhost:8080"
const uri = "post/" // 직종 관련 매핑주소
let postJS = {
    init:function (){
        let _this = this;
        _this.list();
    },

    list:function(){
        // const dom = $(".postContainer");

        const dom = $(".postBox");
        let str ="";

        get(url,uri+'listInMain')
            .then(data => {

                console.log("data :" + data);

                for(let dto of data.dtoList){
                    console.log(dto);
                    str += `
                        <a href="/post/read?postId=${dto.id}"><div class="col" style="height:250px; margin-bottom: 10px">
                            <div class="card h-100" style="padding-top: 10px">
                                <div class="card-body">
                                    <h5 class="card-title">${dto.title}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">${dto.comName}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">${dto.regionName}</h6>
                                    <p class="card-text">${dto.occName}</p>
                                    <a href="#" class="card-link">D-${dto.remainDay}일</a>
                                </div>
                            </div>
                        </div></a>`;
                }
                dom.html(str);
            })
            .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
        return null;


    }
}




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