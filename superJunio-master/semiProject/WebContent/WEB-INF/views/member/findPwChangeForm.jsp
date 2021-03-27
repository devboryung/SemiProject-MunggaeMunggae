<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        margin : 0 auto;
        margin-top : -24px;
        text-align: center;
        padding : 40px;
    }

    #idBtn, #pwBtn {
        width: 130px;
        height: 40px;
        background-color: #379194;
        border-radius: 10px;
        border: white;
        color: white;
        font-weight: 200;
        margin-top : -90px;
        
    }

    #idBtn:hover, #pwBtn:hover {
        font-weight: 500;
    }

    /* 찾기 전체 Div */

    #findWrapper {
    	width : 700px;
    	margin-top : -5px;
        border-radius: 10px;
        border: 1px solid white;
        background-color: white;
        text-align : center;
        margin : 0 auto;
    }

    /* 찾기 Div 모음 */
    #findDiv1 {
        margin: 30px;
        
    }

    #findDiv2{
        width: 350px;
        height :160px;
        margin : 0 auto;
        text-align: center;
    }

    #findDiv2 > label {
        margin: 3px;
        line-height: 35px;
    }

    #findDiv2 > input, #findDiv2 > button{
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
        background-color:  #379194;
        color: white;
        font-weight: 10;
        border-radius: 5px;
    }

    #eCode:hover {
        font-weight: 500;
    }

    /* 다음 버튼, 구름 이미지 Div*/  

    #changeDiv{
        margin: auto;
        text-align: center;
    }

    #changeBtn {
        width: 65px;
        height: 35px;
        background-color: #379194;
        border-radius: 10px;
        border: white;
        color: white;
        font-weight: 200;
        margin: 30px;
    }

    #changeBtn:hover {
        font-weight: 500;
    }

    #coludDiv{
        position: absolute;
        right: 0;
        bottom: 0;
    }

    /* #cloudImg {
        margin-top: 100px;
    } */
    
     #check{
     margin-top : -18px;
    }
    
    .checkpw {
    margin-left : 38px;
    }
    
    #checkPwd2 {
    	margin-left : 100px;
    }
    
    #ch1 {
    	margin : 0 auto;
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
            <a href="${contextPath}/member/findIdForm.do"><button id="idBtn">아이디 찾기</button></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${contextPath}/member/findPwForm.do"><button id="pwBtn">비밀번호 찾기</button></a>
        </div>
    
        <form action="${contextPath}/member/findPwResult.do" method="post" onsubmit="return validate();">
        <div id="findWrapper">
        <div id="findDiv1">
            <p id="p1">본인 확인 완료!</p> 
            <p id="p2">비밀번호 재설정</p>
        </div>

        <div id="findDiv2">
        		
        
            <label for="id1">현재 아이디</label>
            <input type="text" id="id1" name="currId" required>
            <br>
            <label for="pw1">새 비밀번호</label>
            <input type="password" id="pw1" name="pw1" required>
            <br>
            
            <div id="ch1">
            <span id="checkPwd1"></span>
            </div>
            
            
            <label for="pw2" id="check">새 비밀번호 확인</label>
            <input type="password" id="pw2" class="checkpw" name="pw2" required>
            <br>
            
            <div id="ch2">
            <span id="checkPwd2"></span>
            </div>
            
        </div>

        </div>

        <div id="changeDiv">
            <button type="submit" id="changeBtn">변경</button>
        </div>
        </form>
    </div>


    <div id="cloudDiv">
        <img src="${pageContext.request.contextPath}/resources/image/common/cloud.png" id="cloud">
    </div>
    
    <script>
    var validateCheck = {
    		"pw1" : false,
    	  "pw2" : false
    }

    
    $("#pw1, #pw2").on("input", function () {

        // 비밀번호 유효성 검사 
        var regExp = /^[A-Za-z\d]{6,12}$/;
        var v1 = $("#pw1").val();
        var v2 = $("#pw2").val();

        if (!regExp.test(v1)) {
            $("#checkPwd1").text("비밀번호 형식이 유효하지 않습니다.").css("color", "red");
            validateCheck.pw1 = false;
        } else {
            $("#checkPwd1").text("유효한 비밀번호 형식입니다.").css("color", "green");
            validateCheck.pw1 = true;
        }
        // 비밀번호가 유효하지 않은 상태에서 비밀번호 확인 작성 시
        if (!validateCheck.pw1 && v2.length > 0) {
            alert("유효한 비밀번호를 먼저 작성해주세요.");
            $("#pw2").val(""); // 비밀번호 확인에 입력한 값 삭제
            $("#pw1").focus();
        } else {
            // 비밀번호, 비밀번호 확인의 일치 여부
            if (v1.length == 0 || v2.length == 0) {
                $("#checkPwd2").html("&nbsp;");
            
            } else if(v1 == v2){
                $("#checkPwd2").text("비밀번호 일치").css("color", "green");
                validateCheck.pw2 = true;
           

            } else {
                $("#checkPwd2").text("비밀번호 불일치").css("color", "red");
                validateCheck.pw2 = false;
            }
        }
    });
    
$("#changeBtn").click(function validate(){
    for(var key in validateCheck){
        if(!validateCheck[key]){
            var msg;
            switch(key){
                case "pw1" : 
                case "pw2" : msg="비밀번호가"; break;
            }

            alert(msg + " 유효하지 않습니다.");

            $("#"+key).focus();

            return false;
        }
    }
});
    
    
    </script>

</body>
</html>