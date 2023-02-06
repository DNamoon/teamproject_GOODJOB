# teamproject_GOODJOB
team project - recruitment site (20220926~)

## 프로젝트명 GOODJOB
실제 다양한 채용 사이트를 사용하면서 구인자 및 구직자 모두가 간편하게 사용 가능한 단순한 형태의 채용 사이트의 필요성을 느끼고 기획하였습니다.

다음은 팀프로젝트를 마치고 팀원들끼리 정리한 ppt중 제가 맡은 부분을 따로 정리하여 README에 올리는 것을 알려드립니다.   
저는 이 프로젝트에서 **기업회원 회원가입 및 정보수정과 아이디 찾기 기능 구현**을 담당하였습니다.   
(이 레포지토리는 fork된 레포지토리입니다!)

![image](https://user-images.githubusercontent.com/95398932/216991337-4c43a5c7-2273-41fc-8ff7-e6a45c4876d0.png)

![image](https://user-images.githubusercontent.com/95398932/216991462-0a1522dc-e7e9-4e0d-b2d2-1505385f814f.png)

![image](https://user-images.githubusercontent.com/95398932/216991554-0dba8c8d-54ce-461f-9172-fdd1ac225e7d.png)

![image](https://user-images.githubusercontent.com/95398932/216991803-b28566fb-fed3-4efe-8efc-2759918c5271.png)

![image](https://user-images.githubusercontent.com/95398932/216992102-cd715ea4-1a88-4627-82ab-6e2567cfe0e4.png)

![image](https://user-images.githubusercontent.com/95398932/216992222-0dfe33d0-80ba-42b1-8b7d-855f2b6eca1c.png)

![image](https://user-images.githubusercontent.com/95398932/216992355-a8fd78f8-bf3b-4f0b-97c2-4610b38a7cac.png)

![image](https://user-images.githubusercontent.com/95398932/216993500-be242d42-8028-4fe3-af2b-f64f2b317ee4.png)

![image](https://user-images.githubusercontent.com/95398932/216993784-07b4bd81-b5d6-41c4-b7ad-e8f64bb5be45.png)

![image](https://user-images.githubusercontent.com/95398932/216993892-27a5f2af-b758-435f-8752-cc58bf0259e8.png)
위와 같이 아이디 중복 확인, 이메일 중복 확인기능과 아이디, 비밀번호, 사업자등록번호(10자리), 이메일의 유효성검사 기능을 구현하였습니다.   
여기서 가장 중요하게 생각했던 것은 유효성 검사 부분입니다! 프론트 단에서는 회원가입 버튼에 js 함수를 적용하여 버튼 클릭시 유효성 검사를 진행하게 하였고, 
백엔드 단에서는 @NotBlank, @Pattern 어노테이션등을 이용하여 유효성 검사를 진행하였습니다.   
***

![image](https://user-images.githubusercontent.com/95398932/216994009-46a561e3-c3bb-4b2a-a0f5-3dd003ae9b08.png)
다음은 회원정보 페이지 입니다. 다음과 같이 첫 페이지에서는 모든 정보 창이 비활성화되어있습니다. <정보 수정하기> 버튼을 누르면 다음과 같이 페이지가 변합니다.

![image](https://user-images.githubusercontent.com/95398932/216994063-3cd4af2a-4d87-48ee-b49d-1469ddada4f9.png)

![image](https://user-images.githubusercontent.com/95398932/216994161-c066b84a-9742-4be3-8288-4bcfeb17d303.png)
회원정보 수정시에도 유효성 검사를 진행하였고, 이메일의 유효성 검사는 본인 계정의 이메일일 때는 문제없다고 판단하고 다른 이메일일 때 중복 및 유효성 검사를 진행합니다.   
정보 수정시에는 비밀번호를 확인하고 틀리면 수정이 되지 않고 맞을 시에만 수정이 됩니다.

![image](https://user-images.githubusercontent.com/95398932/216994339-5d06ad80-90e2-4c63-919b-0982a70d029a.png)

![image](https://user-images.githubusercontent.com/95398932/216994400-3bd720f6-ea19-48ad-83ea-3a149ed8b87b.png)

![image](https://user-images.githubusercontent.com/95398932/216994557-10974af9-28a9-4a52-8a47-82380e535b23.png)
다음과 같이 수정된 회원정보를 확인할 수 있습니다.   
***

![image](https://user-images.githubusercontent.com/95398932/216994614-0ad9f74f-d75a-49d8-86be-d33a4f3220eb.png)
다음은 비밀번호 변경하기입니다. <비밀번호 변경> 버튼을 누르면 다음과 같이 비밀번호 확인창이 뜨게 됩니다. 비밀번호가 틀릴시에는 비밀번호를 확인해달라는 안내문구가 나오며
맞을시에만 비밀번호 변경 페이지로 이동하게 됩니다.

![스크린샷 2023-02-06 오후 11 16 17](https://user-images.githubusercontent.com/95398932/217001726-752bd628-1299-4122-a2b2-55407adb2303.jpg)
비밀번호 변경페이지에서도 유효성검사를 진행합니다. 실제로는 블록처리되지만 보여드리기 위해 블록을 풀었습니다.   
유효성검사를 통과 못 할시에는 다음과 같은 알림창이 뜨게 됩니다.

![스크린샷 2023-02-06 오후 11 16 35](https://user-images.githubusercontent.com/95398932/217001864-9d8deaa8-058c-4045-a8e5-6f98301decf5.jpg)
비밀번호와 비밀번호확인에 입력한 문자가 맞는지도 확인합니다.   
유효성 검사와 비밀번호 일치를 통과할시에만 비밀번호가 변경됩니다.
***

![image](https://user-images.githubusercontent.com/95398932/216994831-cff69d59-e5a6-4798-9020-77c3ced424fa.png)

![image](https://user-images.githubusercontent.com/95398932/216994891-b610064a-2879-45a7-8cef-dddedb5a0072.png)
회원탈퇴시 DB에서 관련 데이터를 삭제하기 때문에 한 번 더 묻는 알림창을 띄웁니다. 이 단계에서 <아니오>를 선택할 시 계정은 삭제되지 않습니다.
***

![image](https://user-images.githubusercontent.com/95398932/216994992-5ca08427-617c-4d29-a5ce-aa3c65e12291.png)
아이디 찾기입니다. 로그인 페이지에서 버튼을 이용하여 아이디 찾기 페이지로 이동합니다.

![image](https://user-images.githubusercontent.com/95398932/216995077-0144cf02-57fc-4137-9072-325432fc0317.png)
아이디는 개인회원과 기업회원 모두 동시에 검색할 수 있게 되어있습니다. 그래서 회원가입시 사용한 이름 또는 기업명을 입력하고 이메일을 입력하여 아이디를 찾을 수 있습니다.
이 페이지는 없는 회원일 경우 나타내는 알림창입니다.

![image](https://user-images.githubusercontent.com/95398932/216995141-3a820e7d-98d7-4ec8-808b-0de3e57ae102.png)
올바른 기업명과 이메일을 입력하였을 경우 <기업회원>이라는 알림멘트가 위에 뜨게 되고 개인회원도 마찬가지입니다.

