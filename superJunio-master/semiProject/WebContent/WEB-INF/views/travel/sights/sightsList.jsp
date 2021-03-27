<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>지역정보</title>

<!-- 구글폰트 사용 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<!-- 부트스트랩 사용을 위한 css 추가 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<!-- 부트스트랩 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>

<style>
* {
	font-family: 'Noto Sans KR', sans-serif;
	font-weight: 500; /* 굵기 지정(100, 300, 400, 500, 700) */
	font-size: 16px;
	color: #212529;
	box-sizing: border-box;
	margin: 0;
	/* border : 1px solid black;  */
}

div {
	/* border : 1px solid black;  */
	box-sizing: border-box;
}

.wrapper {
	margin: auto;
}

/* ------------------------------------------------------ */
#container {
	width: 1100px;
	height: 1600px;
	display: block;
	margin: auto;
	box-sizing: border-box;
	/* background-color: ghostwhite; */
}

/* ------------------------ 내용(컨텐츠부분) ------------------------------ */

.main {
	width: 900px;
	height: 100%;
	float: left;
}

.main-title{
    font-size: 30px;
    font-weight: 700;
    padding-top: 25px;
}

/* ------------------------ 상단 빅배너 ------------------------ */
#localInfo-bigBanner {
	width: 900px;
	height: 200px;
	position: relative;
}

#big-banner-title {
	/* background-color: yellow; */
	color: white;
	font-size: 45px;
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

/* ------------------------ 인기도시 ------------------------ */
.hot-city {
	margin: 30px 0px 30px 0px;
}

.hot-city-area {
	width: 900px;
	height: 100px;
	display: flex;
	margin-top: 20px;
	/* border:1px solid red; */
}

.hot-city-box {
	width: 100px;
	height: 100px;
	/* background-color:lightgrey; */
	/* padding: 0px 5px 0px 5px ; */
	margin: 0px 14px 0px 0px;
}

.hot-city-thumbnail-img {
	width: 100px;
	height: 60px;
	border: 1px solid red;
}

.hot-city-title {
	width: 100px;
	height: 40px;
	text-align: center;
	/* border:1px solid blue; */
}

/* ----------------------지역 선택------------------------------- */
.row-item{
	margin : 30px 0px 30px 0px;
}

.locationSelect{
    margin : 0 0 30px 83%;
    
}

.locationNm{
    background-color : #fff;
    font-size: 16px;
    width:75px;
    border: 3px solid #8bd2d6;
    border-radius: 5px;
}

/* ------------------------- 테이블 ---------------------------- */
.post-thumbnail-img {
	width: 200px;
	height: 100px;
	background-color: lightgray;
}

/* 테이블 : 수직 가운데 정렬 */
.table td, .table th {
	text-align: center;
	vertical-align: middle;
}

/* 테이블 가로 간격 */
.table-1st { width: 70px; } /* 글번호 */
.table-2th { width: 200px; } /* 이미지 */
.table-3rd { width: 400px; } /* 제목 */

/* 페이징 색 변경 */
.page-item>a { color: #8ad2d5; }

.page-item>a:hover { color: #3d8588; }

/* ------------------------ 글쓰기버튼 ---------------------------------- */
.row-item {
	width: 100%;
	box-sizing: border-box;
}

/* 버튼 */
.btn_class {
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	cursor: pointer;
	outline: none;
}

.btn_class:hover {
	background-color: #17a2b8;
}

#insertLocal {
	width: 100px;
	height: 40px;
	margin: 40px 0 50px 89%;
	line-height: 20px;
	border-radius: 5px;
	color: #fff;
	font-size: 17px;
}

/*---------------------------- 페이징(부트스트랩) ---------------------------------  */
.page-item>a {
	color: black;
	text-decoration: none;
}

.page-item>a:hover {
	color: hotpink;
}

/* ---------------------------- 검색창 스타일 ---------------------------- */
 
#searchForm > * {
	top: 0;
	vertical-align: top;
}

select[name='searchKey']{
	width: 100px; 
	display: inline-block;
}

input[name='searchValue']{
	width: 25%; 
	display: inline-block;
}

#searchBtn{
	width: 100px; 
	display: inline-block;
	
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
}

button#searchBtn:hover{
	background-color: #17a2b8;
}

.list-wrapper{
	height: 540px;
}

/* 목록에 마우스를 올릴 경우 손가락 모양으로 변경 */
#list-table td:hover{
	cursor : pointer;
}


</style>
</head>

<body>

	<!--------------------- header 연결 -------------------- -->
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<div id="container">
		<!-- --------------------- 사이드 메뉴 연결 --------------------- -->
		<jsp:include page="/WEB-INF/views/travel/travelSideMenu.jsp"></jsp:include>

		<!-- --------------------- 메인Contents --------------------- -->
		<div class="main">
			
			<div class="main-title">관광지를 모아 보여드려요🧐</div>
			
			<div class="hot-city">
				<h4>인기도시</h4>
				<div class="hot-city-area">
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="서울">서울</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="인천">인천</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="대구">대구</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="부산">부산</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="강원도">강원도</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="경상도">경상도</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="전라도">전라도</div>
					</div>
					<div class="hot-city-box">
						<div class="hot-city-thumbnail-img"></div>
						<div class="hot-city-title" name="제주도">제주도</div>
					</div>
				</div>
			</div>

			<div id="localInfo-bigBanner">
				<img
					src="${pageContext.request.contextPath}/resources/image/travel/localInfo/local-bigbanner(900x200)_seoul.jpg">
				<div id="big-banner-title">서울</div>
			</div>
			
			<!----------------------------- 지역 선택/옵션 ----------------------------->
            
            <div class="row-item">
                <div class="locationSelect">
                    <span style="font-size:16px; font-weight:bold;">대한민국 ></span>
                    <select class="locationNm" name="location">
                        <option value="강원도">강원도</option>
                        <option value="경기도">경기도</option>
                        <option value="경상도">경상도</option>
                        <option value="광주">광주</option>
                        <option value="대구">대구</option>
                        <option value="대전">대전</option>
                        <option value="부산">부산</option>
                        <option value="서울" selected>서울</option>
                        <option value="세종">세종</option>
                        <option value="울산">울산</option>
                        <option value="인천">인천</option>
                        <option value="전라도">전라도</option>
                        <option value="제주">제주</option>
                        <option value="충청도">충청도</option>
                    </select>
                </div>
            </div>
			

			<!-- ------------------------------------------------------------------------- -->

			<table class="table">
				<thead>
					<tr>
						<th scope="col" class="table-1st">글번호</th>
						<th scope="col" class="table-2th"></th>
						<th scope="col" class="table-3rd">제목</th>
						<th scope="col" class="table-4th">조회수</th>
						<th scope="col" class="table-5th">작성일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">1</th>
						<td>
							<div class="post-thumbnail-img"></div>
						</td>
						<td>
							<h4>숭인근린공원</h4>
							모든 견종
						</td>
						<td>0</td>
						<td>2021-01-16</td>
					</tr>
					<tr>
						<th scope="row">2</th>
						<td>
							<div class="post-thumbnail-img"></div>
						</td>
						<td>
							<h4>여의도공원</h4>
							모든 견종
						</td>
						<td>0</td>
						<td>2021-01-16</td>
					</tr>
					<tr>
						<th scope="row">3</th>
						<td>
							<div class="post-thumbnail-img"></div>
						</td>
						<td>
							<h4>하늘공원</h4>
							모든 견종
						</td>
						<td>0</td>
						<td>2021-01-16</td>
					</tr>
					<tr>
						<th scope="row">4</th>
						<td>
							<div class="post-thumbnail-img"></div>
						</td>
						<td>
							<h4>서울숲</h4>
							모든 견종
						</td>
						<td>0</td>
						<td>2021-01-16</td>
					</tr>
					<tr>
						<th scope="row">5</th>
						<td>
							<div class="post-thumbnail-img"></div>
						</td>
						<td>
							<h4>올림픽공원</h4>
							모든 견종
						
						</td>
						<td>0</td>
						<td>2021-01-16</td>
					</tr>
				</tbody>
			</table>

			<!-- 등록하기 버튼  -->
			<div class="row-item">
				<button type="button" class="btn_class" id="insertLocal"
					onclick="location.href = '${contextPath}/travel/sightsInsert.do'">
					등록하기</button>
			</div>

			<!-- 페이징 -->
			<div class="paging">
				<nav aria-label="Page navigation example">
					<ul id="pagingBtn"
						class="pagination pagination-sm justify-content-center">
						<li class="page-item"><a class="page-link" href="#">&lt;</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">4</a></li>
						<li class="page-item"><a class="page-link" href="#">5</a></li>
						<li class="page-item"><a class="page-link" href="#">6</a></li>
						<li class="page-item"><a class="page-link" href="#">7</a></li>
						<li class="page-item"><a class="page-link" href="#">8</a></li>
						<li class="page-item"><a class="page-link" href="#">9</a></li>
						<li class="page-item"><a class="page-link" href="#">10</a></li>
						<li class="page-item"><a class="page-link" href="#">&gt;</a></li>
					</ul>
				</nav>
			</div>
			
			<!-- 검색필드 -->
			<div class="mb-5">
			<form action="search" method="GET" class="text-center" id="searchForm">
				<select name="searchKey" class="form-control">
					<option value="title">글제목</option>
					<option value="content">내용</option>
					<option value="titcont">제목+내용</option>
				</select>
				<input type="text" name="searchValue" class="form-control">
				<button class="form-control btn btn-primary" id="searchBtn">검색</button>
			</form>


		</div>
			
			
			
			
		</div>
	</div>



	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>



</body>
</html>