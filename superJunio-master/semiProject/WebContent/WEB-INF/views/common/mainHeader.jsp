<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 구글 폰트 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>



<style>
* {
	font-family: 'Noto Sans KR', sans-serif;
	font-weight: 500; /* 굵기 지정(100, 300, 400, 500, 700) */
	font-size: 16px;
	color: #212529;
	box-sizing: border-box;
	margin: 0;
}

a {
	text-decoration-line: none;
}

#header {
	height: 250px;
	margin: 0;
	padding: 0;
	background: #8ad2d5;
	position: relative;
	background-image:
		url("${pageContext.request.contextPath}/resources/image/common/cloud.png");
	background-size: 100%;
	background-repeat: repeat-x; /* x축 반복 */
	background-position: center bottom; /* 아래 정렬 */
	bottom: 0;
}

/* #header {min-height:100%;} */
/* 위의 부분이 div를 화면에 꽉 차게 만들어 주는 스타일 */
#header-group {
	height: 56px;
	display: flex;
	align-items: center;
	margin: auto;
	width: 1100px;
	position: relative;
}

#logo {
	top: 25px;
	height: 200px;
	display: block;
	position: absolute;
	right: 40%;
}

.header-right-items {
	display: flex;
	margin-left: auto; /* 오른쪽 정렬 */
}

.header-item {
	margin-left: 20px;
	font-size: 16px;
}

/* .search-input{
        background-color: #F5F6F8;
        height: auto;
        padding: .375rem .75rem;
        border: 1px solid #ced4da;
    } */

/* ------------------------------------------- */
#nav {
	height: 56px;
	border-bottom: 1px solid #e5e5e5;
	width: 1100px;
	display: flex;
	margin: auto;
	align-items: center;
	font-size: 16px;
	justify-content: space-between !important;
}

#nav>ul {
	list-style-type: none; /* 불렛 없음 */
}

#nav>ul>li>a {
	text-decoration: none; /* a태그 밑줄 없음 */
	font-size: 16px;
	color: black;
	font-weight: bold;
	display: flex;
	/* margin : 0px 65px 0 65px; */
	margin: 0px 50px 0px 50px;
}

#nav>ul>li {
	display: inline-block;
}

/* 메뉴에 마우스 오버했을 경우 민트색으로 변경  */
#nav>ul>li>a:hover {
	color: #17a2b8;
}

#header-signUp > a:hover {
	color : white;
}
</style>
</head>
<body>
	<%-- 
		프로젝트의 시작주소(context root)를 얻어와 간단하게 사용할 수 있도록
		별도의 변수를 생성
	--%>
	<c:set var="contextPath" scope="application"
		value="${pageContext.servletContext.contextPath}" />

	<c:if test="${!empty sessionScope.swalTitle }">
		<script>
			swal({
				icon : "${swalIcon}",
				title : "${swalTitle}",
				text : "${swalText}"
			});
		</script>

		<%-- 2) 한 번 출력한 메세지를 Session에서 삭제 --%>
		<c:remove var="swalIcon" />
		<c:remove var="swalTitle" />
		<c:remove var="swalText" />
	</c:if>


	<div id="header">
		<a href="${contextPath}">
			<div id="logoDiv">
				<img src="${pageContext.request.contextPath}/resources/image/common/logo.png" id="logo">
			</div>
		</a>

		<div id="header-group">
			<!-- <form class="search">
                <input class="search-input" type="text" placeholder="검색어를 입력해주세요" aria-label="Search">
            </form> -->

			<c:choose>
				<c:when test="${empty sessionScope.loginMember}">
					<div class="header-right-items">
						<div class="header-item" id="header-login">
							<a href="${contextPath}/member/loginForm.do">로그인</a>
						</div>
						<div class="header-item" id="header-signUp">
							<a href="${contextPath}/member/signUpForm.do">회원가입</a>
						</div>
					</div>
				</c:when>

				<c:otherwise>
				<div class="header-right-items">
					<div class="header-item" id="header-login">
						${loginMember.memberNickName}님
					</div>
					<div class="header-item" id="header-signUp">
						<a href="${contextPath}/member/logout.do">로그아웃</a>
					</div>
			</div>
				</c:otherwise>
			</c:choose>

		</div>
	</div>

	<!-- ------------------------------------------------------------------------- -->

	<div id="nav">
		<ul>
			<li><a href="#" class="nav-items" id="nav-home">홈</a></li>
			<li><a href="${contextPath}/travel/localList.do"
				class="nav-items" id="nav-travel">여행</a></li>
			<li><a href="${contextPath }/room/list" class="nav-items"
				id="nav-room">숙소</a></li>
			<li><a href="${contextPath }/hospital/list" class="nav-items"
				id="nav-animalHospital">동물병원</a></li>
			<li><a href="${contextPath}/freeBoard/freeList.do"
				class="nav-items" id="nav-board">게시판</a></li>

			<c:choose>
				<%-- 로그인이 되어있지 않을 때 == session에 loginMember라는 값이 없을 때 --%>
				<c:when test="${empty sessionScope.loginMember}">
					<li><a href="${contextPath}/member/loginForm.do" class="nav-items" id="nav-mypage">마이페이지</a></li>
					<script>
						// 로그인이 안되있을 경우 마이페이지 클릭 시 경고창
						var loginMemberId = "${loginMember.memberId}";
						$("#nav-mypage").on("click", function() {
								alert("로그인 후 이용해 주세요.");
						});
					</script>
				</c:when>
				
				<%-- 일반 회원일 때 --%>
				<c:when test="${!empty loginMember && (loginMember.memberAdmin == 'G') }">
					<li><a href="${contextPath}/member/myPageNormal.do" class="nav-items" id="nav-mypage">마이페이지</a></li>
				</c:when>
				
				<%-- 업체 회원일 때 --%>
				<c:when test="${!empty loginMember && (loginMember.memberAdmin == 'C') }">
					<li><a href="${contextPath}/member/myPageCompany.do" class="nav-items" id="nav-mypage">마이페이지</a></li>
				</c:when>
				
				<%-- 관리자일 때 --%>
				<c:otherwise>
					<li><a href="${contextPath}/manager/managerNormal.do" class="nav-items" id="nav-mypage">마이페이지</a></li>
				</c:otherwise>
				
			</c:choose>





			<li><a href="${contextPath}/notice/notice.do" class="nav-items" id="nav-serviceCenter">고객센터</a></li>
		</ul>
	</div>


</body>
</html>