// ====== localhost 주소 ====
const url = "localhost:8080"
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

// ====================================================================================


// get('jsonplaceholder.typicode.com','users')
//     .then(data => {
//         console.log('data : '+ data);
//         jsonList(data);
//     })
//     .catch(error =>console.log(`error : ${error}`)); // fetch는 요청 자체가 실패한 경우를 제외하고는  catch로 error가 넘어가지 않는다.



// post('jsonplaceholder.typicode.com','users',sampleJson)
//     .then(data => {
//         console.log('data : '+ data);
//         jsonList(data);
//     })
//     .catch((error)=>console.log(`error : ${error}`));

// 객체 분해 함수
// function jsonList(json){ //json 형태의 파일을 get할 경우 실행되는 함수.
//     json.forEach(element => { // 반복문이 value를 리턴한다. 배열에 콜백함수가 필요할 때 사용한다.
//         console.log('================================================');
//         for(keyName in element){ // 반복문이 value를 리턴한다. 객체의 경우 key값을 리턴.
//             console.log(`${keyName} : ${element[keyName]}`);
//         }
//     });
// }

export {get, post};