<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>


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
	display: block;
	margin-left: auto;
	margin-right: auto;
}

#logo {
	width: 300px;
	margin: 110px;
	margin-left: -75px;
}

/* 아이디,비밀번호 찾기 버튼 & 효과 */
#btnDiv {
	width: 300px;
	height: 30px;
	margin: 0 auto;
	margin-top: -30px;
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
	height : 334px;
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
#sendMail {
	border: white;
	background-color: #379194;
	color: white;
	font-weight: 10;
	border-radius: 5px;
}

#sendMail:hover, #nextBtn1:hover {
	font-weight: 500;
}

/* 다음 버튼, 구름 이미지 Div*/
#nextDiv1 {
	margin: auto;
	text-align: center;
}

#nextBtn1 {
	width: 65px;
	height: 35px;
	background-color: #379194;
	border-radius: 10px;
	border: white;
	color: white;
	font-weight: 200;
	margin: 30px;
	margin-top : 40px;
}

#nextDiv {
	margin: auto;
	margin-top : -30px;
	text-align: center;
}

#coludDiv {
	position: absolute;
	right: 0;
	bottom: 0;
}

#cloud {
	vertical-align: bottom;
}

#findDiv2 > span {
	line-height : 90px;
	font-size : 12px;
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


	<div id="wrapper">
		<div id="logoDiv" style="width: 150px; height: 300px">
			<a href="${contextPath}"><img
				src="${pageContext.request.contextPath}/resources/image/common/logo.png"
				id="logo"></a>
		</div>

		<div id="btnDiv">
			<a href="${contextPath}/member/findIdForm.do"><button id="idBtn">아이디
					찾기</button></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
				href="${contextPath}/member/findPwForm.do"><button id="pwBtn">비밀번호
					찾기</button></a>
		</div>


		<div id="findWrapper">
			<form action="${contextPath}/member/findPwReady.do" method="post"
				onsubmit="return submitCheck();">
				<div id="findDiv1">
					<p id="p1">회원 정보에 등록한 이메일로 인증</p>
					<p id="p2">회원 가입을 하신 아이디와 이메일 주소를 입력해 주세요.</p>
				</div>

				<div id="findDiv2">
					<label for="id">아이디</label> <input type="text" id="id" name="id"
						required> <br> <label for="mail">이메일</label> <input
						type="text" id="mail" name="mail" required>
					<button id="sendMail">인증번호 받기</button>


					<label for="inputEmail">&nbsp;</label> <input type="text"
						id="inputEmail" placeholder="인증번호 입력" required> <span
						id="checkFl"></span>
						
					<div id="nextDiv">	
					<button type="submit" id="nextBtn1">다음</button>
					</div>
				</div>

			</form>
		</div>

	</div>


	<div id="cloudDiv">
		<img
			src="${pageContext.request.contextPath}/resources/image/common/cloud.png"
			id="cloud">
	</div>



	<script>
	
	var key;
	console.log(key);
	
	$("#sendMail").click(function() {// 메일 입력 유효성 검사
		var mail = $("#mail").val(); //사용자의 이메일 입력값. 
		
		if (mail == "") {
			alert("메일 주소가 입력되지 않았습니다.");
		} else {
			$.ajax({
				type : 'post',
				url : '${contextPath}/member/CheckPwMail',
				data : {
					mail:mail
					},
				
				success : function(result){
					key = result;
					
				}
			});
		alert("인증번호가 전송되었습니다.");
		}
	});
	
		$("#inputEmail").on("propertychange change keyup paste input", function() {
			if ($("#inputEmail").val() == key) {   //인증 키 값을 비교를 위해 텍스트인풋과 벨류를 비교
				$("#checkFl").text("일치!").css("color", "green");
				isCertification = true;  //인증 성공여부 check
			} else {
				$("#checkFl").text("불일치!").css("color", "red");
				isCertification = false; //인증 실패
			}
		});
	
		$("#nextBtn1").click(function submitCheck(){
			if(isCertification==false){
				alert("메일 인증이 완료되지 않았습니다.");
				return false;
			}
		});
	
	
	
	
	
	</script>


</body>
</html>