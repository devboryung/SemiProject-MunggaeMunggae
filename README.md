# SemiProject - SuperJuni5

# ![강아지](https://user-images.githubusercontent.com/73421820/112724211-97667b00-8f55-11eb-997b-520fc526278c.png) 개요 

* 프로젝트 명 : 뭉개뭉개
* 일정 :2020.12.07 ~ 2021.01.15
* 개발 목적 : 많은 사람들이 마음 편히 반려 동물과 여행을 갈 수 있도록 반려견에 맞춰 여행 서비스를 제공하는 사이트를 구현.
* 개발 환경
  - OS : Windows 10
  - WAS : Apache Tomcat 8.5
  - 사용 프로그램 : Java Eclipse , Oracle, SQL Developer, VS Code
  - 사용 기술 : Servlet , JSP, HTML5, CSS3, javascript, jQuery
  - API : KAKAO MAP API, 이메일 인증 API
  - 라이브러리 : cos.jar/ gson-2.8.6.jar/ json-simple-1.1.1.jar/ mail-1.4.7.jar/ojdbc6.jar/taglibs-standard-impl-1.2.5.jar/taglibs-standard-jstlel-1.2.5.jar/taglibs-standard-spec-1.2.5.jar
  - 형상 관리 : GitHub

------------

##### ![팀원](https://user-images.githubusercontent.com/73421820/112724869-adc20600-8f58-11eb-99c4-6c104cb1c4ec.png) 팀원별 역할

```
공통 : 기획, 요구 사항 정의, 와이어 프레임, DB 설계
강보령 : 동물병원, 숙소 CRUD
김정훈 : 일반 회원, 업체 회원, 관리자 마이페이지
박지호 : 자유게시판, 여행 후기 CRUD 
신주희 : 여행정보 CRUD
유지권 : 고객센터 CRUD
```

------------

##### ![체크](https://user-images.githubusercontent.com/73421820/112724945-0d201600-8f59-11eb-9c07-d79ad0d5775f.png) 설계의 주안점
```
- 회원가입 및 회원 정보 수정 시 이메일 인증 API를 이용하기
- 동물병원, 숙소 위치 정보를 kakao Map API를 이용해서 출력하기
```
------------

##### ![체크](https://user-images.githubusercontent.com/73421820/112724945-0d201600-8f59-11eb-9c07-d79ad0d5775f.png) DB 설계
![db](https://user-images.githubusercontent.com/73421820/112725197-51f87c80-8f5a-11eb-9907-bf0048d3e5ef.png)

------------

# ![노트북](https://user-images.githubusercontent.com/73421820/112725392-55403800-8f5b-11eb-9247-c772e4c09493.png) 구현 기능

## 숙소

### 숙소 목록 조회

- 화면 설명 : 사이트에 등록된 숙소 리스트가 조회됩니다.
![image](https://user-images.githubusercontent.com/73421820/112725568-3d1ce880-8f5c-11eb-88b9-d95abc4c4b8d.png)

- 구현 기능 설명
  - 페이지의 Header에서 숙소를 클릭하면 사이트에 등록된 숙소의 목록이 출력됩니다.
  - 숙소 등록 시 썸네일로 지정한 이미지와, 숙소 명, 주소가 출력됩니다.
  - 한 페이지당 6개의 숙소가 나타나며 페이징 바를 이용해 다른 페이지로 이동할 수 있습니다.
  - 원하는 숙소를 클릭하면 해당 숙소의 상세조회 페이지로 이동됩니다.

### 숙소 등록하기
- 화면 설명 : 등록하기 버튼을 누르면 숙소 등록 화면으로 이동됩니다.
![숙소등록](https://user-images.githubusercontent.com/73421820/112726001-6c345980-8f5e-11eb-8596-4c39a712f422.gif)
- 구현 기능 설명
  - 등록하기 버튼은 업체 회원으로 로그인했을 경우 나타납니다.
  - 회원가입 시 입력한 숙소 명, 전화번호, 상세 주소가 자동으로 입력됩니다.
  - 체크인/체크아웃 시간은 00:00~24:00 형태로 입력할 수 있으며, 유효성 검사에 위배될 경우 alert창이 나타납니다.
  - 숙소에 해당하는 부대 시설을 체크할 수 있습니다.
  - 반려동물 동반 가능한 숙소만 입력할 수 있기 때문에 출입 가능 견종은 필수로 체크되어야 합니다. 체크가 되지 않을 경우 alert 창이 나타납니다.
  - 사진은 썸네일 1장, 그 외 5장으로 총 6장 업로드 가능합니다. 썸네일 칸에 업로드된 사진이 목록 조회 화면에서 나타나게 됩니다.
  - 취소 버튼을 누르면 confirm창이 뜨며 확인을 누를 경우 목록으로 돌아갑니다.
  - 등록 버튼을 누르면 숙소가 등록되며 해당 숙소의 상세조회 페이지로 이동합니다.
