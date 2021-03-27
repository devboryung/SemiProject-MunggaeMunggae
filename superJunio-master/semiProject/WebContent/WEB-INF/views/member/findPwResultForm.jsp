<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>아이디,비밀번호 찾기</title>
<!-- 구글 폰트 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<style>
* {
	font-family: 'Noto Sans KR', sans-serif;
	/* font-weight: 100; --> 굵기 지정 */
	font-weight: 500;
}

html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	background: #8ad2d5;
}

#logoDiv {
	width: 300px;
	display: block;
	margin: 0 auto;
}

/* 아이디,비밀번호 찾기 버튼 & 효과 */
#btnDiv {
	width: 300px;
	height: 30px;
	margin: 0 auto;
	text-align: center;
	padding: 40px;
}

#idBtn, #pwBtn {
	width: 130px;
	height: 40px;
	background-color: #379194;
	border-radius: 10px;
	border: white;
	color: white;
	font-weight: 200;
}

#idBtn:hover, #pwBtn:hover {
	font-weight: 500;
}

/* 찾기 전체 Div */
#findWrapper {
	border-radius: 10px;
	border: 1px solid white;
	background-color: white;
	width: 700px;
	height: 334px;
	margin: 0 auto;
	text-agign: center;
}

/* 찾기 Div 모음 */
#findDiv1 {
	margin: 30px;
}

#findDiv2 {
	width: 250px;
	height: 160px;
	margin: 0 auto;
	text-align: center;
}

#findDiv2>label {
	margin: 3px;
	float: left;
}

#findDiv2>input, #findDiv2>button {
	margin: 3px;
	float: right;
}

/* p 태그 */
#p1 {
	font-size: 20px;
	font-weight: 700;
	margin: auto;
	text-align: center;
}

#p2 {
	font-size: 14px;
	font-weight: normal;
	margin: auto;
	text-align: center;
}

/* 이메일 버튼 & 효과 */
#eCode {
	border: white;
	background-color: #379194;
	color: white;
	font-weight: 10;
	border-radius: 5px;
}

#eCode:hover {
	font-weight: 500;
}

/* 다음 버튼, 구름 이미지 Div*/
#loginDiv {
	margin: auto;
	text-align: center;
}

#loginBtn {
	width: 65px;
	height: 35px;
	background-color: #379194;
	border-radius: 10px;
	border: white;
	color: white;
	font-weight: 200;
	margin: 30px;
}

#loginBtn:hover {
	font-weight: 500;
}

#coludDiv {
	position: absolute;
	right: 0;
	bottom: 0;
}
</style>
</head>

<body>


	<div id="wrapper">

		<a href="${contextPath}">
			<div id="logoDiv" style="width: 150px; height: 300px">
				<img
					src="${pageContext.request.contextPath}/resources/image/common/logo.png"
					id="logo">
			</div>
		</a>

		<div id="btnDiv">
			<a href="${contextPath}/member/findIdForm"><button id="idBtn">아이디
					찾기</button></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
				href="${contextPath}/member/findPwForm"><button id="pwBtn">비밀번호
					찾기</button></a>
		</div>

		<div id="findWrapper">
			<div id="findDiv1">
				<p id="p1">비밀번호 변경 완료!</p>
				<p id="p2">비밀번호가 변경되었습니다. 새로운 비밀번호로 로그인해주세요.</p>
			</div>

			<div id="loginDiv">
				<a href="${contextPath}/member/loginForm.do"><button
						id="loginBtn">로그인</button></a>
			</div>
		</div>


		<div id="cloudDiv">
			<img
				src="${pageContext.request.contextPath}/resources/image/common/cloud.png"
				id="cloud">
		</div>
	</div>
</body>
</html>