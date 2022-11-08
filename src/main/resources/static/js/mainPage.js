let common = {
    postPath:{
        hostname:"localhost:8080",
        path:"/post/",
        hostNameAndPath: `${this.hostname}${this.path}`, // localhost:8080/post/
        comMyPagePost:"comMyPagePost",
        readPost:"readPost/"
    }
}
// mainPage.html JS
$(document).ready(function(){
    havePass();
    signupCheck();
})

function havePass(){
    if($("#havePass").val() === 'true'){
        Swal.fire({
            title: '서류 합격을 축하드립니다',
            text: '이후 전형에 대한 안내는 이력서의 이메일을 확인해주세요',
            icon: 'info'
        });
    }

    $.ajax({
        url: "/status/changeStatShow",
        type: "post"
    })
}
function signupCheck() {
    if ($("#signupcheck").val() === "1") {
            Swal.fire("회원가입 ",'회원가입이 완료되었습니다.',"success");
    }
}

// mainPage.html 에서 쓰이는 JS
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
    },
    showApplierList(postId,postTitle){   //박채원 22.11.01 추가
        location.href=`/com/myPageApplier/${postId}`;
    }

}




// searchPage.html JS
let searchPage = {
    init(){
        const _this=this;
        _this.infiniteScroll();
    },
    searchPageUri:"searchPage",

    infiniteScroll () {
        const _this = this;
        const controller = new AbortController();
        let page = 2;
        console.log("셋팅 시작")

        document.addEventListener('scroll',onScroll,{signal:controller.signal})
        function onScroll () {
            const occ = document.querySelector("#occSelect").value;
            const addr = document.querySelector("#addSelect").value;
            const sal = document.querySelector("#salarySelect").value;
            const keyword = document.querySelector("#search").value;

            if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                console.log("무한스크롤 작동")
                setTimeout(function(){
                    _this.insertPostListCard("infinite",page,8,"new",occ,addr,sal,keyword)
                }, 500)
                page += 1;
            }
            if(document.querySelector(".empty_box")!==null){
                console.log("삭제 발동2")
                controller.abort();
            }
        }
    },

    pageRequestDTOForMainPage(page,size,sort,filterOccupation,filterAddress,filterSalary,keyword){
        return {
            page: page,
            size: size,
            sort: sort,
            filterOccupation: filterOccupation,
            filterAddress: filterAddress,
            filterSalary: filterSalary,
            keyword: keyword
        }
    },
    insertPostListCard(targetDomClassName,page,size,sort,filterOccupation, filterAddress, filterSalary){
        const _this = this;
        const changeContentDiv = document.querySelector(`.${targetDomClassName}`);

        // console.log(document.querySelector(".postDetailOccName").textContent)
        fetchJs.post(fetchJs.url, 'getPagingPostList', _this.pageRequestDTOForMainPage(page, size, sort, filterOccupation, filterAddress, filterSalary))
            .then(data => {
                console.log(page+"<="+data.totalPage)
                if (page <= data.totalPage) {
                    for (let dto of data.dtoList) {
                        changeContentDiv.appendChild(postJS.makePostListHtml(dto));
                    }
                    searchPage.page +=1;


                } else if(page > data.totalPage){
                    const ifEmptyDiv = document.createElement('div');
                    ifEmptyDiv.classList.add('empty_box');
                    const div = document.createElement('div');
                    div.style.margin = "auto";
                    div.style.textAlign = "center";
                    ifEmptyDiv.appendChild(div);
                    div.innerHTML = '<p class="empty_box-message">더이상 자료가 없습니다</p>'
                    changeContentDiv.appendChild(ifEmptyDiv);
                }
            })
            .catch(error => console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.
    }

}
// postInsertForm.html JS
let postInsertForm ={
    init(){
      const _this = this;
      _this.setSaveBtn();
      _this.setInputDateDefaultToday()
    },
    setInputDateDefaultToday(){
        const startDate = document.getElementById('postStartDate').valueAsDate = new Date();
        const endDate = document.getElementById('postEndDate').valueAsDate = new Date();

    },
    setSaveBtn(){
        const _this = this;
        document.querySelector("#saveBtn").addEventListener("click",function (e){
            e.preventDefault();
            const form = $("#savePostForm")[0]
            let dataWithFile = new FormData(form);
            _this.postSave(dataWithFile)
      })
    },
    postSave(dataWithFile){
        fetchJs.postWithFile(fetchJs.url,fetchJs.uri+"savePost",dataWithFile)

            .then(response => {
                console.log(dataWithFile)
                if(response.ok){
                    console.log(response);
                    response.json().then(json => {
                        console.log("요청 성공")
                        alert("저장에 성공하였습니다.")
                        console.log(json.id);
                        location.replace(`${common.postPath.path}${common.postPath.readPost}${json.id}`)
                    })
                } else {
                    response.json().then(data =>{
                        console.log("요청 실패")
                        console.log(data)
                        console.log(data.errors)
                        let criticalErrorCount = 0;
                        for(let error of data.errors){
                            const titleDom = document.querySelector(".post-error-style-title")
                            const occupationDom =  document.querySelector(".post-error-style-occupation")
                            const recruitNumDom = document.querySelector(".post-error-style-recruit_number")
                            const genderDom = document.querySelector(".post-error-style-gender")
                            const startDateDom = document.querySelector(".post-error-style-start_date")
                            const endDateDom = document.querySelector(".post-error-style-end_date")
                            const attachmentDom = document.querySelector(".post-error-style-attachment")
                            const zipcodeDom = document.querySelector(".post-error-style-zipcode")
                            const address1Dom = document.querySelector(".post-error-style-address1")
                            const address2Dom = document.querySelector(".post-error-style-address2")
                            const salaryDom = document.querySelector(".post-error-style-salary")
                            const contentDom = document.querySelector(".post-error-style-content")
                            // titleDom.innerHTML=""
                            // occupationDom.innerHTML=""
                            // recruitNumDom.innerHTML=""
                            // genderDom.innerHTML=""
                            // startDateDom.innerHTML=""
                            // endDateDom.innerHTML=""
                            // attachmentDom.innerHTML=""
                            // zipcodeDom.innerHTML=""
                            // address1Dom.innerHTML=""
                            // address2Dom.innerHTML=""
                            // salaryDom.innerHTML=""
                            // contentDom.innerHTML=""
                            const reason = error.reason;
                            switch (error.field){
                                case "postTitle":
                                    // const postInsertTitleDom = document.querySelector(".post-Insert-Title")
                                    // postInsertTitleDom.style.display = "block"
                                    if(reason==="Empty Title"){
                                        titleDom.innerHTML=`제목은 필수 값입니다.`
                                    } else if(reason ==="Max length(50)"){
                                        titleDom.innerHTML=`제목 길이 제한은 50 문자입니다.`
                                    }
                                    break;
                                case "postOccCode":
                                    if(reason==="Invalid value"){
                                        occupationDom.innerHTML=`비정상적인 데이터입니다`
                                        criticalErrorCount+=1;
                                    }
                                    if(reason==="Not Selected"){
                                        occupationDom.innerHTML=`모집 직종은 필수값입니다.`
                                    }
                                    break;
                                case "postRecruitNum":
                                    if(reason==="Empty Recruit Number"){
                                        recruitNumDom.innerHTML=`모집인원은 필수값입니다.`
                                    }
                                    break;
                                case "postGender":
                                    if(reason==="Empty Gender"){
                                        genderDom.innerHTML=`모집 성별은 필수값입니다.`
                                    }
                                    if(reason==="Gender values must be either male, female, or gender-independent"){
                                        genderDom.innerHTML=`비정상적인 데이터입니다.`
                                        criticalErrorCount+=1;
                                    }
                                    break;
                                case "postStartDate":
                                    if(reason==="The start date must be after today."){
                                        startDateDom.innerHTML=`시작일은 오늘 이후여야 합니다.`
                                    }
                                    if(reason==="The start date must be before the end date."){
                                        startDateDom.innerHTML=`시작일은 모집 종료일 이전이어야 합니다.`
                                    }
                                    break;
                                case "postEndDate":
                                    if(reason==="The end date must be after today."){
                                        endDateDom.innerHTML=`모집 종료일은 오늘 이후여야 합니다.`
                                    }
                                    if(reason==="The end date must be after the start date."){
                                        endDateDom.innerHTML=`모집 종료일은 시작일 이후이어야 합니다.`
                                    }
                                    break;
                                case "postImg":
                                    if(reason==="Empty Img"){
                                        attachmentDom.innerHTML=`공고 사진은 필수값입니다.`
                                    }
                                    break;
                                case "postcode":
                                    if(reason==="Empty zipcode"){
                                        zipcodeDom.innerHTML=`우편번호는 필수값입니다.`
                                    }
                                    if(reason==="The length of zipcode must be five"){
                                        zipcodeDom.innerHTML=`우편번호는 5자리입니다.`
                                    }
                                    break;
                                case "postAddress":
                                    if(reason==="Empty Address1"){
                                        address1Dom.innerHTML=`주소는 필수값입니다.`
                                    }
                                    if(reason==="Max length(255)"){
                                        address1Dom.innerHTML=`주소 길이 제한은 255 문자입니다.`
                                    }
                                    break;
                                case "postDetailAddress":
                                    if(reason==="Empty Address2"){
                                        address2Dom.innerHTML=`상세주소는 필수값입니다.`
                                    }
                                    if(reason==="Max length(255)"){
                                        address2Dom.innerHTML=`상세주소 길이 제한은 255 문자입니다.`
                                    }
                                    break;
                                case "postSalaryId":
                                    if(reason==="Invalid value"){
                                        salaryDom.innerHTML=`비정상적인 데이터입니다.`
                                        criticalErrorCount+=1;
                                    }
                                    if(reason==="Not Selected"){
                                        salaryDom.innerHTML=`연봉대는 필수값입니다.`
                                    }
                                    break;
                                case "postContent":
                                    if(reason==="Empty content"){
                                        contentDom.innerHTML=`내용은 필수값입니다.`
                                    }
                                    if(reason==="Max length(5000)"){
                                        contentDom.innerHTML=`내용 길이 제한은 5000 문자입니다.`
                                    }
                                    break;
                            }
                        }
                        if(criticalErrorCount>0){
                            alert("비정상적인 접근입니다. 메인으로 이동합니다.")
                            // location.replace(`/`)
                        }

                    })
                }

            })
            .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.

    },
    backToRedirectedFrom(redirectedFrom){
        console.log(redirectedFrom);
        location.href=`/post/${redirectedFrom}`;
    },
    redirectToRegisterPage(redirectedFrom){
        location.href=`/${fetchJs.uri}savePost?redirectedFrom=${redirectedFrom}`
    }
}
let postUpdateForm = {
    init(){
        postInsertForm.setSaveBtn();
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
        return await response.json();
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
        return await response.json();
    },
    // POST FETCH (multipart/form-data)
    postWithFile : async function post(host, path, body){
        const url = `http://${host}/${path}`;
        const options = {
            method: 'POST',
            body: body,
        };
        return await fetch(url, options);
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
