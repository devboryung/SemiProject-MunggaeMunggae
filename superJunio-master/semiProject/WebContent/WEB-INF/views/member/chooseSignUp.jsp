<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입 선택</title>

<!-- 구글 폰트 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<style>
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	background: #8ad2d5;
}

* {
	font-family: 'Noto Sans KR', sans-serif;
	/* font-weight: 100; --> 굵기 지정 */
	font-weight: 500;
	color: #212529;
}


#joinDiv {
	text-align: center;
	margin: 10px;
}

#userJoin, #companyJoin {
	width: 307px;
	height: 300px;
	margin: 20px;
	border: white;
	background: white;
	color: #46a2a5;
	border-radius: 10px;
	font-size: 30px;
	border: 1px solid #46a2a5;
}


#userJoin:hover {
	cursor: pointer;
}

#companyJoin:hover {
	cursor: pointer;
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

#userJoin:hover, #companyJoin:hover {
	font-weight: bold;
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

		<a href="${contextPath}">
			<div id="logoDiv" style="width: 150px; height: 300px">
				<img src="${pageContext.request.contextPath}/resources/image/common/logo.png" id="logo">
			</div>
		</a>

	<div id="joinDiv">
		<a href="${contextPath}/member/nomalSignUpForm.do"><button
				type="button" id="userJoin">
				일반<br>회원가입
			</button></a> <a href="${contextPath}/member/companySignUpForm.do"><button
				type="button" id="companyJoin">
				업체<br> 회원가입
			</button></a>
	</div>

	<div id="cloudDiv">
		<img
			src="${pageContext.request.contextPath}/resources/image/common/cloud.png"
			id="cloud">
	</div>




</body>
</html>