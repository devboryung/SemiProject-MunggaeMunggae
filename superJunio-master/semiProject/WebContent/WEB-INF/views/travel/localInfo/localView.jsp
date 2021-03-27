<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	min-height: 800px;
	display: block;
	margin: auto;
}

/* -------------------------- 내용(컨텐츠부분) ---------------------------- */
.main {
	width: 1100px;
/* 	width: 900px; */
	height: 100%;
	float: left;
	
	margin-bottom: 100px;
}

/* ------------------------ 상단 빅배너 ------------------------ */
#localInfo-bigBanner {
	width: 900px;
	height: 200px;
	position: relative;
}

/*
img{
min-width:900px;
}*/


.boardImgArea {
	height: 500px;
}

.boardImg {
/* 	width: 100%;
	height: 100%; */
	width: 1100px;
	height: 500px;
	max-width: 1100px;
	max-height: 500px;
	margin: auto;
}

/* 이미지 화살표 색 조정
	-> fill='%23000' 부분의 000을
	   RGB 16진수 값을 작성하여 변경 가능 
	 */
.carousel-control-prev-icon {
	background-image:
		url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E")
		!important;
}

.carousel-control-next-icon {
	background-image:
		url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E")
		!important;
}



/* ------------------------------------------ */
.btn_class {
	background-color: #8ad2d5;
}

.btn-primary {
    color: #fff;
    background-color: #8ad2d5 !important;
    border-color: #8ad2d5 !important;
}

.btn-primary:hover {
	color: #8ad2d5;
	background-color: #17a2b8 !important;
	border-color: #8ad2d5 !important;
}

/* --------------- 버튼 --------------- */
.btn_class {
	border-radius: 5px;
	color: #fff;
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	cursor: pointer;
	outline: none;
	width: 70px;
	height: 40px;
}

.btn_item {
	margin-left: 35%;
}

.btn_class:hover {
	background-color: #17a2b8;
}
</style>
</head>
<body>

	<!-- --------------------- header 연결 --------------------- -->
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<div id="container">
		<!-- --------------------- 사이드 메뉴 연결 --------------------- -->
		
		<%-- <jsp:include page="/WEB-INF/views/travel/travelSideMenu.jsp"></jsp:include>
		 --%>
		
		<!-- --------------------- 메인Contents --------------------- -->
		<div class="main">
			<%--  <br> 지역정보>상세글 <br>
			<br> --%>
			
			
			<%-- <div id="localInfo-bigBanner">
				<img
					src="${pageContext.request.contextPath}/resources/image/travel/localInfo/local-bigbanner(900x200)_seoul.jpg">
			</div> --%>

				<!-- 이미지 출력 -->
				<c:if test="${!empty fList}">
					<div class="carousel slide boardImgArea" id="board-image">

						<!-- 이미지 선택 버튼(인디케이터) -->
						<!-- 이미지가 업로드 된 개수만큼 인디케이터 개수가 생기게 함 -->
						<ol class="carousel-indicators">
							<c:forEach var="file" items="${fList}" varStatus="vs">

								<li data-slide-to="${vs.index}" data-target="#board-image"
									<c:if test="${vs.first}"> class="active" </c:if>></li>

							</c:forEach>

						</ol>

						<!-- 출력되는 이미지 -->
						<div class="carousel-inner">

							<c:forEach var="file" items="${fList}" varStatus="vs">

								<div
									class="carousel-item <c:if test="${vs.first}">active</c:if>">

									<img class="d-block w-100 boardImg" id="${file.fileNo}"
										src="${contextPath}/resources/uploadImages/travel/${file.fileName}">
								</div>

							</c:forEach>

						</div>

						<!-- 좌우 화살표 -->
						<a class="carousel-control-prev" href="#board-image"
							data-slide="prev"> <span class="carousel-control-prev-icon"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="carousel-control-next" href="#board-image"
							data-slide="next"> <span class="carousel-control-next-icon"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
				</c:if>
				
				
			<br>
			<!-- 도시 -->
			<h5 style="font-weight:600;">[${travel.travelLocation}]</h5>

			<!-- Title -->
			<h2>${travel.travelTitle}</h2>

			<hr>
			글번호 : ${travel.travelNo}<br> 작성일 : ${travel.travelBoardDate} <br>
			조회수 : ${travel.travelReadCount} <br>
			<hr>
			
			
			<!-- 내용 -->
			${travel.travelContent}
			
			
			<hr>



			<c:choose>
				<c:when test="${!empty param.sk && !empty param.sv }">

					<%-- .. 상위 주소 --%>
					<c:url var="goToList" value="../localList.do">
						<c:param name="cp">${param.cp}</c:param>
						<c:param name="sk">${param.sk}</c:param>
						<c:param name="sv">${param.sv}</c:param>
					</c:url>

				</c:when>

				<c:otherwise>
					<%-- 목록으로 이동 --%>
					<c:url var="goToList" value="localList.do">
						<c:param name="cp">1</c:param>
					</c:url>
				</c:otherwise>
			</c:choose>



			<!-- 내가만든..... 목록으로 / 수정 / 삭제 버튼  -->
			<!-- 
			<div class="row-item">
				<div class="btn_item" style="margin-bottom:100px;">
					<button class="btn_class" id="" type="button" style="width: 84px"
						onclick="location.href = '${goToList}'">목록으로</button>
					<button class="btn_class" id="" type="submit">수정</button>
					<button class="btn_class" id="" type="reset">삭제</button>
				</div>
			</div>
			  -->
			  
			  
			<!-- ----------------------- 목록으로... -------------------------- -->
			<div>

					<%-- 로그인된 회원과 해당 글 작성자가 같은 경우--%>
					<c:if
						test="${!empty loginMember && (travel.memNo == loginMember.memberNo )}">
						<button id="deleteBtn" class="btn btn-primary float-right">삭제</button>


						<%-- 
						상세 조회 -> 수정 버튼 클릭 -> 수정 화면 -> 수정 성공 -> 상세 조회 
																	?cp=1&no=505
						
						검색 -> 검색목록 -> 상세조회 -> 수정 버튼 클릭 -> 수정 화면 -> 수정 성공 -> 상세 조회
										?cp=1&no=505&sk=title&sv=파일
						 --%>


						<%-- 게시글 수정 후 상세조회 페이지로 돌아오기 위한 url 조합 --%>
						<c:if test="${!empty param.sv && !empty param.sk }">
							<%-- 검색을 통해 들어온 상세 조회 페이지인 경우 --%>

							<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
						</c:if>

						<a href="updateForm.do?cp=${param.cp}&no=${param.no}${searchStr}"
							class="btn btn-primary float-right ml-1 mr-1">수정</a>
					</c:if>

					<%-- 
						상대경로 작성법
						- 앞에 아무것도 없음 : 현재 위치 (주소 제일 마지막 / 뒷부분)
						- 앞에 .. 붙음 : 현재 위치에서 한단계 상위 (주소 제일 마지막 / 보다 왼쪽으로 한칸 앞 / ) 
						http://localhost:8080/wsp/search.do
					 --%>


					<c:choose>
						<c:when test="${!empty param.sk && !empty param.sv }">

							<%-- .. 상위 주소 --%>
							<c:url var="goToList" value="../search.do">
								<c:param name="cp">${param.cp}</c:param>
								<c:param name="sk">${param.sk}</c:param>
								<c:param name="sv">${param.sv}</c:param>
							</c:url>

						</c:when>

						<c:otherwise>
							<%-- 목록으로 이동 --%>
							<c:url var="goToList" value="localList.do">
								<c:param name="cp">${param.cp}</c:param>
							</c:url>
						</c:otherwise>
					</c:choose>


					<a href="${goToList}" class="btn btn-primary float-right">목록으로</a>
				</div>


		</div>
	<!-- ************************* 푸터 연결 ************************* -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	</div>





	<script>
		// 삭제 버튼 이벤트
		$("#deleteBtn").on("click", function(){
			if(confirm("정말 삭제 하시겠습니까?")){
				location.href = "delete.do?no=${travel.travelNo}";
			}
		});
	</script>

</body>
</html>