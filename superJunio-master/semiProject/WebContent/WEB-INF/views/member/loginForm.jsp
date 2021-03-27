<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인</title>

<!-- 구글 폰트 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
	
	
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<!-- sweetalert : alert창을 꾸밀 수 있게 해주는 라이브러리 https://sweetalert.js.org/ -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js">
</script>


<style>

html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	background: #8ad2d5;
}

* {
	font-family: 'Noto Sans KR', sans-serif;
}

#inputDiv {
	text-align: center;
	height: 100px;
}

#userId, #userPw {
	width: 200px;
	height: 30px;
	border: 1px solid white;
	border-radius : 5px;
}

#userId {
	margin: 10px;
}

#loginBtnDiv {
	display: flex;
	justify-content: center;
}

#loginBtn {
	width: 207px;
	height: 50px;
	margin: 35px;
	border: white;
	background: #379194;
	color: white;
	border-radius: 10px;
	font-size: 18px;
}

#loginBtn:hover {
	font-weight: bold;
}

#findsignDiv {
	text-align: center;
	font-size: small;
	color: white;
}

#find, #sign {
	text-decoration: none;
	color: white;
}

#saveDiv {
	margin-left: -130px;
}

#saveId {
	font-size: 12px;
	color: black;
}

#logoDiv {
	display: block;
	margin-left: auto;
	margin-right: auto;
}

#logo {
	width: 300px;
	margin: 110px;
	margin-left: -75px;
}

#cloudDiv {
	font-size: 0;
	line-height: 0;
	display: block;
	margin-bottom: 10px;
}

#cloud {
	vertical-align: bottom;
}

</style>

</head>
<body>

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

	
	<form action="${contextPath}/member/login.do" method="POST">

		<a href="${contextPath}">
			<div id="logoDiv" style="width: 150px; height: 300px">
				<img src="${pageContext.request.contextPath}/resources/image/common/logo.png" id="logo">
			</div>
		</a>


		<div id="inputDiv">
			<input type="text" id="userId" name="userId" placeholder="아이디"
				value="${cookie.saveId.value}"> <br> 
				<input type="password" id="userPw" name="userPw" placeholder="비밀번호">
			<br>

			<div id="saveDiv">
				<input type="checkbox" id="saveIdChk" name="saveId"
					<c:if test="${!empty cookie.saveId.value }">
										checked									
									</c:if>>
				<label for="saveId" id="saveId">아이디 저장</label>
			</div>
		</div>

		<div id="loginBtnDiv">
			<button type="submit" id="loginBtn">로그인</button>
		</div>

		<div id="findsignDiv">
			<a id="find" href="${contextPath}/member/findIdForm.do">ID,PW 찾기</a> 
			&nbsp;|&nbsp; 
			<a id="sign" href="${contextPath}/member/signUpForm.do">회원가입</a>
		</div>
	</form>

	
	<div id="cloudDiv">
		<img
			src="${pageContext.request.contextPath}/resources/image/common/cloud.png"
			id="cloud">
	</div>


</body>
</html>