<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- c 태그를 쓰면 라이브러리 선언해준다!!!! -->

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>글 상세조회</title>
<link rel="stylesheet" href="join.css" type="text/css">

<!-- 구글 폰트 -->
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

<!-- 이미지 파일 업로드 버튼 경로를 위한 제이쿼리 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>


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
	height: 800px;
	display: block;
	margin: auto;
}

/* -------------------------- 내용(컨텐츠부분) ---------------------------- */
.main {
	width: 900px;
	height: 100%;
	float: left;
	padding-left: 10px;
	/* 왼쪽 간격 띄우기 */
}



/* ------------------------ 상단 빅배너 ------------------------ */
#localInfo-bigBanner {
	width: 900px;
	height: 200px;
	position: relative;
}

/* ------------------------------------------ */
.btn_class{
	background-color: #8ad2d5;
}

.btn-primary:hover {
	color: #8ad2d5;
	background-color: #ffffff;
	border-color: #8ad2d5;
}

/* --------------- 버튼 --------------- */

.btn_class{
    border-radius: 5px;
    color: #fff;
    border : 1px solid  #8bd2d6;
    background-color: #8bd2d6;
    cursor: pointer;
    outline:none;
    width : 70px;
    height: 40px;
}
.btn_item{
    margin-left: 35%;
}

.btn_class:hover{
	background-color: #17a2b8;
}



</style>
</head>
<body>

	<!-- --------------------- header 연결 --------------------- -->
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<div id="container">
		<!-- --------------------- 사이드 메뉴 연결 --------------------- -->
		<jsp:include page="/WEB-INF/views/travel/travelSideMenu.jsp"></jsp:include>

		<!-- --------------------- 메인Contents --------------------- -->
		<div class="main">
			<br>
			지역정보>상세글
			<br><br>
			
			<div id="localInfo-bigBanner">
				<img
					src="${pageContext.request.contextPath}/resources/image/travel/localInfo/local-bigbanner(900x200)_seoul.jpg">
			</div>
			
			<br>
			<!-- 도시 -->
			서울<br>
			${travel.travelLocation}
			<h3>숭인근린공원</h3>
			모든 견종 <br>
			주소 서울 종로구 숭인동 58-70 <br>
			
			<hr>
			
			작성일 2021-01-17 <br>
			조회수 <br> 
			
			<hr>
			
			야경이 좋습니다...  
			
			<hr>
			
			
           	<!-- 목록으로 / 수정 / 삭제 버튼  -->
            <div class="row-item">
                <div class="btn_item">
                	<button class="btn_class" id="" type="button" style="width:84px">목록으로</button>
                    <button class= "btn_class"  id="" type="submit">수정</button>
                    <button class= "btn_class"  id="" type="reset">삭제</button>
                </div>
            </div>

		</div>
	</div>


	<!-- ************************* 푸터 연결 ************************* -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>


</body>
</html>