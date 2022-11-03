// mainPage.html JS
let postJS = {
    init(){
      const _this = this;
      _this.insertPostListCard('changeContentDiv',1,8,'new');
    },
    pageRequestDTOForMainPage(page,size,sort,filterOccupation,filterAddress,filterSalary){
        return {
        page: page,
        size: size,
        sort: sort,
        filterOccupation: filterOccupation,
        filterAddress: filterAddress,
        filterSalary: filterSalary
        }
    },
    insertPostListCard(targetDomClassName,page,size,sort,filterOccupation, filterAddress, filterSalary){
        const _this = this;
        const changeContentDiv = document.querySelector(`.${targetDomClassName}`);

        // console.log(document.querySelector(".postDetailOccName").textContent)
        fetchJs.post(fetchJs.url,'getPagingPostList',_this.pageRequestDTOForMainPage(page,size,sort,filterOccupation,filterAddress,filterSalary))
            .then(data => {
                console.log("data :" + data);
                // ajax 받아오기 전에 자식노드들 삭제
                while(changeContentDiv.hasChildNodes()){
                    changeContentDiv.removeChild(changeContentDiv.firstChild);
                }
                if(data.totalCount !==0){
                    for(let dto of data.dtoList){
                        console.log(dto);
                        changeContentDiv.appendChild(this.makePostListHtml(dto));
                    }
                } else {
                    const ifEmptyDiv = document.createElement('div');
                    ifEmptyDiv.classList.add('empty_box');
                    const div = document.createElement('div');
                    div.style.margin="auto";
                    div.style.textAlign="center";
                    ifEmptyDiv.appendChild(div);
                    div.innerHTML='<p class="empty_box-message">일치하는 자료가 없습니다</p>'
                    changeContentDiv.appendChild(ifEmptyDiv);
                }
            })
            .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
    },
    makePostListHtml(dto){
        // 공고 html 생성 코드
        const postWrapper = document.createElement("div");
        postWrapper.classList.add("mainPage-wrapper");
        const div1 = document.createElement("div");
        div1.classList.add("col", "p-0");
        const div2 = document.createElement("div");
        div2.classList.add("p-0", "m-2", "card-css");

        const div3 = document.createElement("div");
        div3.classList.add("w-100","p-0","h-50","img-css");
        div3.setAttribute("onclick",`location.href="/post/readPost/${dto.id}"`)
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
        p6.innerHTML=`${dto.remainDay}`;
        div4.appendChild(p6);

        div2.appendChild(div3);
        div2.appendChild(div4);
        div1.appendChild(div2);
        postWrapper.appendChild(div1);
        return postWrapper;
    }

}
// comMyPagePost.html JS
let comMyPagePost = {
    init(){
      const _this = this;
      _this.setClearSearchCondition();
    },
    comMyPagePostUri: "comMyPagePost",
    getFormDom : function(){
        return document.querySelector(".comMyPage-post-search-form");
    },

    formSubmit:function (){
        this.getFormDom().submit();
    },
    formSubmitChangeSize:function (size){
        const changeSizeInput = document.querySelector(".changeSizeInput");
        changeSizeInput.value=size;
        this.getFormDom().submit();
    },
    formSubmitChangeOutOfDateState:function (outOfDateState){
        const changeOutOfDateStateInput = document.querySelector(".changeOutOfDateStateInput");
        changeOutOfDateStateInput.value=outOfDateState;
        this.getFormDom().submit();
    },
    formSubmitChangePage:function (page){
        const changePageInput = document.querySelector(".changePageInput");
        changePageInput.value=page;
        this.getFormDom().submit();
    },
    deleteByPostIdWithAjax:function (postId){
        const deleteMapping = "deletePost"
        fetchJs.delete(fetchJs.url,`${fetchJs.uri}${deleteMapping}/${postId}`)
            .then(data => {
                console.log("data :" + data)
                let result = confirm("삭제하시겠습니다?")
                result? this.formSubmit(): null;
            })
        .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
    },
    readPost:function (postId){
        location.href=`/post/readPost/${postId}`;
    },
    setClearSearchCondition(){
        const _this = this;
        const clearBtn = document.querySelector(".comMyPage-post-search-clear_btn");
        clearBtn.addEventListener("click", function(e)
        {
            e.preventDefault()
            location.href=`/${fetchJs.uri}${_this.comMyPagePostUri}`
        });
    },
    updatePost(postId){
        location.href=`/post/updatePost/${postId}`;
    }

}

// searchPage.html JS
let searchPage = {
    init(){
        const _this=this;
    },

    searchPageUri:"searchPage",


}
// postInsertForm.html JS
let postInsertForm ={
    backToRedirectedFrom(redirectedFrom){
        console.log(redirectedFrom);
        location.href=`/post/${redirectedFrom}`;
    },
    redirectToRegisterPage(redirectedFrom){
        location.href=`/${fetchJs.uri}savePost?redirectedFrom=${redirectedFrom}`
    }
}

// postDetailViewWithMap.html JS
let postDetailViewWithMap = {
    init(dto){
        console.log(dto);
        const _this = this;
        console.log(dto.occName);
        postJS.insertPostListCard('changePostDiv',1,4,'count',dto.occName);
        _this.setRedirectToUpdatePage(_this.postDetailViewWithMap,dto.postId);
    },
    postDetailViewWithMap:"postDetailViewWithMap",
    redirectToUpdatePage(redirectedFrom, postId){
        location.href=`/${fetchJs.uri}updatePost/${postId}`
    },
    setRedirectToUpdatePage(redirectedFrom, postId){
        const _this = this;
        const btn = document.querySelector(".post-read-side_bar-toModifyBtn");
        btn.addEventListener("click",function (){
            _this.redirectToUpdatePage(redirectedFrom,postId);
        })
    }


}


// fetch api 사용을 위한 공용 JS
const fetchJs = {
    // ======================== FETCH FUNCTION(custom) ==========================================
    url : "localhost:8080",
    uri :"post/",
    // GET FETCH
    get : async function get(host, path, headers ={}){
        const url = `http://${host}/${path}`;
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                ...headers,
            }
        };
        const response = await fetch(url, options); // Response {status: 200, ok: true, redirected: false, type: "cors", url: "url", …}
        const data = await response.json();
        const message = 'Error with Status Code: ' + response.status;
        console.log("response =============")
        console.log(response);
        console.log("data =============")
        console.log(data);
        return response.ok ? data : new Error(message); // Error: [object Response]
    },

    // POST FETCH
    post : async function post(host, path, body, headers ={}){
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
    },
    // DELETE FETCH
    delete : async function deleteById(host,path){
        const url = `http://${host}/${path}`;
        const options = {
            method: 'DELETE'
        };
        const response = await fetch(url, options); // Response {type: "cors", url: "url", redirected: false, status: 201, ok: true, …}
        const message = 'Error with Status Code: ' + response.status;
        return response.ok ? response : new Error(message);
    }
}
