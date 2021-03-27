<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지_회원탈퇴</title>
<head>
<!--   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    
    부트스트랩 사용을 위한 라이브러리 추가
    jquery가 항상 먼저여야된다!
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script> -->
<style>
.withdrawalTitle {
	text-align: center;
	height: 70px;
	line-height: 100px;
	font-size: 25px;
}

.memberWithdrawal > hr {
	border: 1px solid orange;
}

#p1 {
	font-size: 20px;
}

.memberWithdrawal {
	width: 400px;
	margin: 0 auto;
	margin-right: 28ch;
}

#withdrawalDiv{
	line-height : 10px;
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
	margin-top : -15px;
	margin-bottom : 51px;
}

#submitBtn {
	margin: 0 auto;
	height: 40px;
	width: 100%;
	font-size: 16px;
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	border-radius: 5px;
	color: white
}

#submitBtn:hover {
	background-color: #17a2b8;
}


</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<jsp:include page="myPageSideMenu.jsp"></jsp:include>

	<div class="wrapper2">
		<div class="withdrawalTitle">
			<p style="font-size: 30px;">회원 탈퇴</p>
		</div>
		<br>


		<form action="withdrawal.do" method="POST" onsubmit="return withdrawalValidate();">
			<div class="memberWithdrawal">
				<hr>
				<br>

				<div id="withdrawalDiv">
					<p id="p1">비밀번호를 입력 후 회원 탈퇴가 가능합니다.</p>
					<p id="p2">탈퇴를 진행하면 계정의 모든 정보가 삭제됩니다.</p>
				</div>

				<br>

				<!-- 비밀번호 입력 -->
				<div>
					<div class="lb">
						<label for="pwd1">비밀번호</label> <br>
					</div>
					<div class="ip">
						<input type="password" class="inputTag" id="currentPw" name="currentPw" required>
					</div>
				</div>

				<br>
				<br>
				
				<div class="panel panel-default">

							<div class="panel-body">
								<div class="form-group pull-left">
									<label class="control-label"> 회원 탈퇴 약관 </label>
									<div class="col-xs-12">
										<textarea class="form-control" readonly rows="10" cols="100">
제1조 (회원 탈퇴 및 자격 상실)

① 회원자격은 뭉개뭉개 가입 후 탈퇴하는 시점까지 유지됩니다.

②  회원은 회사에 언제든지 회원 탈퇴를 요청할 수 있으며 회사는 요청을 받은 즉시 해당 회원의 회원 탈퇴를 위한 절차를 밟아 개인정보취급방침에 따라 회원 등록을 말소합니다.

③ 회원이 서비스 이용에 있어서 본 양관 제 제 10조 내용을 위반하거나, 다음 각 호의 사유에 해당하는 경우 회사는 직권으로 회원자격 상실 및 회원탈퇴의 조치를 할 수 있습니다.
1. 다른 사람의 명의를 사용하여 가입 신청한 경우
2. 신청 시 필수 작성 사항을 허위로 기재한 경우
3. 관계법령의 위반을 목적으로 신청하거나 그러한 행위를 하는 경우

④ 회사가 직권으로 회원 탈퇴 처리를 하고자 하는 경우에는 말소 전에 회원에게 소명의 기회를 부여합니다.


제2조 (개인 정보 및 계정)

① 회원 탈퇴 후 뭉개뭉개 서비스 이용이 불가능합니다.

② 회원 탈퇴 후 모든 개인 정보는 삭제됩니다.

③ 회원 탈퇴 후 계정을 복구할 수 없습니다.
</textarea>
									</div>
									<div class="checkbox pull-right">
										<div class="custom-checkbox">
											<div class="form-check">
												<input type="checkbox" name="agree" id="agree"
													class="form-check-input custom-control-input"> <br>
												<label class="form-check-label custom-control-label"
													for="agree">위 약관에 동의합니다.</label>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>















				<br>
				<br>
				<div class="submit">
					<input id="submitBtn" type="submit" name="" value="다음">
				</div>
			</div>
			</form>
	</div>

	<script src="${contextPath}/resources/js/semi_member.js"></script>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>






</body>
</html>