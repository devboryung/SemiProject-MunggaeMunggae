<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    <style>
    /* 전체 글씨체 지정 */
*{
    font-family: 'Noto Sans KR', sans-serif;
    /* font-weight: 100; --> 굵기 지정 */
    font-weight: 500;
}

/* 전체 화면 */
.wrapper{
    width : 1100px;
    padding-bottom :5%;
    padding-top: 3%;
    margin : 0 auto;
    background-color : #f7f7f7 ;
}

/* 타이틀 */
.joinTitle{
    text-align : center;
    height: 80px;
    line-height : 60px;
    font-size : 30px;
}

/* 회원가입 입력 화면 */
.memberJoin{
    width: 400px;
    padding:0;
    margin : 0 auto;
    box-sizing: border-box;
}

.memberJoin > hr {
	border : 1px solid orange;
	margin-top : -10px;
}

/* 레이블 태그 class명*/
.lb{
    font-weight : bold;
    line-height: 25px;
    font-size: 13px;
    margin: 0 auto;
}

/* 인풋 태그 class명 */
.inputTag{
    margin: 0 auto;
    height:40px;
    width: 100%;
    padding : 0;
    border : 1px solid #bdbbbb;
}

/* inline-block 스타일 들어가야 하는 곳 전부 class 속성값 추가 */
.display-ib{
    display:inline-block;
}

/* 이메일 1번째 칸 */
.email{
    height:40px;
    width:180px;
}

/* 이메일 두번째 칸(옵션)*/
#email2{
    height:45px;
    width:  195px;
}

/* 이메일 인증번호 입력하는 인풋창 */
#verifyEmail{
    width:260px;
    height:30px;
    margin-right : 25px;
}

/* 이메일 인증 버튼 */
#emailBtn{
    height : 32px;
    width : 108px;
    padding : 0;
}


/* number타입의 화살표 지우기 */
input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button{
        -webkit-appearance: none;
		margin: 0;
}

/* 핸드폰번호 입력 input창 */
.phone{
    width : 118px;
    height : 45px;   
}


.gender{
    width: 100%;
}


/* 회원가입 버튼 */
#submitBtn{
    margin: 0 auto;
    height:40px;
    width: 100%;
    font-size : 16px;
}

.btn_class:hover{
	background-color: #17a2b8;
}

.btn_class{
    height : 40px;
    width : 108px;
    padding : 0;
    border : 1px solid  #8bd2d6;
    background-color: #8bd2d6;
    color : #fffefe;
    cursor: pointer;
    outline:none;
}


#nextBtn {
	margin-left : 140px;
}


</style>
</head>


<body>

		<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>업체 회원가입</title>
    <link rel="stylesheet" href="join.css" type="text/css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="wrapper">
        <div class="joinTitle">
            <p style="font-size:30px;"> 일반 회원가입</p>
        </div>
        <br>

        
        <form action="signUp.do" method="POST" onsubmit="return memberJoinvalidate();">
            <div class="memberJoin">
                <hr>

                <!-- 아이디 입력 -->
                <div>
                    <div class="lb">
                        <label for="userId">아이디</label> <br>
                    </div>
                    <div class="ip">
                        <input type="text" class="inputTag" id="userId" name="userId" autocomplete="off" required>
                    </div>
                    <div>
                        <span id="checkId" >&nbsp;</span>
                    </div>    
                </div>
                
                <!-- 비밀번호 입력 -->
                <div>
                    <div class="lb">
                        <label for="pwd1">비밀번호</label>  <br>
                    </div>
                    <div class="ip">
                        <input type="password" class="inputTag"  id="pwd1" name="pwd1"  required>
                    </div>     
                    <div>
                        <span id="checkPwd1" >&nbsp;</span>
                    </div>  
                </div>

                <!-- 비밀번호 재확인 -->
                <div>
                    <div class="lb">
                        <label for="pwd2">비밀번호 재확인</label> <br>
                    </div>
                    <div class="ip">
                        <input type="password" class="inputTag"  id="pwd2" name="pwd2"   required>
                    </div>
                    <div>
                        <span id="checkPwd2" >&nbsp;</span>
                    </div>  
                </div>
                
                <!-- 닉네임 -->
                <div>
                    <div class="lb">
                        <label for="userName">닉네임</label> <br>
                    </div>
                    <div class="ip">
                        <input type="text" class="inputTag"  id="userName" name="userName" required>
                    </div>
                    <div>
                        <span id="checkUserName" >&nbsp;</span>
                    </div> 
                </div>
                
                <!-- 이메일 -->
                <div>
                    <div class="lb">
                        <label for="email">이메일</label> <br>
                    </div>
                    <div class="ip">
                        <input type="text" class="inputTag display-ib email"  id="email1" name="email1" autocomplete="off" required> 
                        @
                        <select class="inputTag display-ib email" id="email2" name="email2" required>
                            <option style="color:gray;">이메일 주소 선택</option>
                            <option>daum.net</option>
                            <option>naver.com</option>
                            <option>gmail.com</option>
                            <option>nate.com</option>
                            <option>hanmail.net</option>
                        </select>
                    </div>
                    <br>  
                    <div class="ip display-ib">
                        <input type="text" class="inputTag email" id="verifyEmail" placeholder="인증번호를 입력해주세요." required>
                    </div>
                    <div class= "verifyBtn display-ib">
                        <button class="btn_class" type="button" id="sendMail" name="sendMail" >인증번호 받기</button>
                    </div>
                    <span id="checkFl"></span>
                    <div>
                        <span id="checkEmail" >&nbsp;</span>
                    </div> 
                </div>

                <!-- 전화번호 -->
                <div>
                    <div class="lb">
                        <label for="phone">전화번호</label>
                    </div>
                    <div class="ip">
                        <select class="display-ib inputTag phone" id="phone1" name="phone1" required> 
                            <option>010</option>
                            <option>011</option>
                            <option>016</option>
                            <option>017</option>
                            <option>019</option>
                        </select>
                        &nbsp;-&nbsp;
                        <input type="number" class="display-ib inputTag phone" id="phone2" name="phone2" required>
                        &nbsp;-&nbsp;
                        <input type="number" class="display-ib inputTag phone" id="phone3" name="phone3" required>
                    </div>
                    <div>
                        <span id="checkPhone" >&nbsp;</span>
                    </div> 
                </div>

                <!-- 성별 -->
                <div>
                    <div class="lb">
                        <label for="gender">성별</label>
                    </div>
                    <div class="ip">
                        <select class="inputTag gender" id="gender" name="gender" required>
                            <option>성별</option>
                            <option>여자</option>
                            <option>남자</option>
                            <option>선택안함</option>
                        </select>
                    </div>
                </div>

                <br><br>
                <div class="submit">
                    <button class="btn_class" id="nextBtn" type="submit">회원가입</button>
                </div>
            </div>
        </form>
        
        
        <script src="${contextPath}/resources/js/semi_member.js"></script>
    </div>

     <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
     
     
     <script>
     
     
     var key;
 	
 	$("#sendMail").click(function() {// 메일 입력 유효성 검사
 		var mail = $("#email1").val() + "@" + $("#email2").val(); //사용자의 이메일 입력값. 
 		
 		if (mail == "") {
 			alert("메일 주소가 입력되지 않았습니다.");
 		} else {
 			$.ajax({
 				type : 'post',
 				url : '${contextPath}/member/normalSignUpMail',
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
 	
 		$("#verifyEmail").on("propertychange change keyup paste input", function() {
 			if ($("#verifyEmail").val() == key) {   //인증 키 값을 비교를 위해 텍스트인풋과 벨류를 비교
 				$("#checkFl").text("인증 성공!").css("color", "green");
 				isCertification = true;  //인증 성공여부 check
 			} else {
 				$("#checkFl").text("불일치!").css("color", "red");
 				isCertification = false; //인증 실패
 			}
 		});
 		
 		
 		$("#nextBtn").click(function memberJoinvalidate(){
 			if(isCertification==false){
 				alert("메일 인증이 완료되지 않았습니다.");
 				return false;
 			}
 		}); 
     
     </script>
     
     

</body>
</html>