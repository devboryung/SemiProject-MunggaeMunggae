<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지_비밀번호 수정</title>
<!-- 구글 폰트 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
        rel="stylesheet">
<head>
<!--   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    
    부트스트랩 사용을 위한 라이브러리 추가
    jquery가 항상 먼저여야된다!
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script> -->
<style>
	* {
            font-family: 'Noto Sans KR', sans-serif;
            font-weight: 500;
            /* 굵기 지정(100, 300, 400, 500, 700) */
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
        
        
		.changeTitle {
            text-align: center;
            height: 70px;
            line-height: 100px;
            font-size: 25px;
        }

        .memberChange > hr {
            border: 1px solid orange;
            
        }

        .memberChange {
            width: 400px;
            margin: 0 auto;
            margin-right: 28ch;
        }

        /* 레이블 태그 class명*/
        .lb {
            font-weight: bold;
            line-height: 25px;
            font-size: 13px;
            margin: 0 auto;
        }

        /* 인풋 태그 class명 */
        .inputTag {
            margin: 0 auto;
            height: 40px;
            width: 100%;
            padding: 0;
            border: 1px solid #bdbbbb;
        }

        /* inline-block 스타일 들어가야 하는 곳 전부 class 속성값 추가 */
        .display-ib {
            display: inline-block;
        }

		.submit {
			margin-bottom : 270px;
		}
		
        #submitBtn {
            margin: 0 auto;
            height: 40px;
            width: 100%;
            font-size: 16px;
            border: 1px solid #8bd2d6;
            background-color: #8bd2d6;
            border-radius: 5px;
            color : white
        }

        #submitBtn:hover{
            background-color: #17a2b8;
        }
		
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<jsp:include page="myPageSideMenu.jsp"></jsp:include>

	
	<div class="wrapper2">
                <div class="changeTitle">
                    <p style="font-size:30px;">비밀번호 변경</p>
                </div>
                <br>


                <form action="${contextPath}/member/UpdatePw.do" method="POST" onsubmit="return memberChangevalidate();">
                    <div class="memberChange">
                        <hr>
                        <br>


                        <!-- 비밀번호 입력 -->
                <div>
                    <div class="lb">
                        <label for="currentPwd">현재 비밀번호</label>  <br>
                    </div>
                    <div class="ip">
                        <input type="password" class="inputTag"  id="currentPwd" name="currentPwd"  required>
                    </div>     
                    <div>
                        <span id="checkPwd1" >&nbsp;</span>
                    </div>  
                </div>
                
                <div>
                    <div class="lb">
                        <label for="newPwd2">새 비밀번호</label>  <br>
                    </div>
                    <div class="ip">
                        <input type="password" class="inputTag"  id="newPwd2" name="newPwd2"  required>
                    </div>     
                    <div>
                        <span id="checkPwd2" >&nbsp;</span>
                    </div>  
                </div>

                <!-- 비밀번호 재확인 -->
                <div>
                    <div class="lb">
                        <label for="newPwd3">새 비밀번호 재확인</label> <br>
                    </div>
                    <div class="ip">
                        <input type="password" class="inputTag"  id="newPwd3" name="newPwd3"   required>
                    </div>
                    <div>
                        <span id="checkPwd3" >&nbsp;</span>
                    </div>  
                </div>

                        <br><br>
                        <div class="submit">
                            <input id="submitBtn" type="submit" name="" value="변경하기">
                        </div>
                    </div>
                </form>
            </div>
		


	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	<script src="${contextPath}/resources/js/semi_member.js"></script> 





</body>
</html>