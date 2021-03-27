<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지_내 정보 수정(업체)</title>
<head>
<!--   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    
    부트스트랩 사용을 위한 라이브러리 추가
    jquery가 항상 먼저여야된다!
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script> -->
<style>
.changeTitle {
	text-align: center;
	height: 70px;
	line-height: 100px;
	font-size: 25px;
}

.memberChange>hr {
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

/* 이메일 1번째 칸 */
.email {
	height: 40px;
	width: 180px;
}

/* 이메일 두번째 칸(옵션)*/
#email2 {
	height: 45px;
	width: 195px;
}

/* 이메일 인증번호 입력하는 인풋창 */
#verifyEmail {
	width: 260px;
	height: 30px;
	margin-right: 25px;
}

/* 이메일 인증 버튼 */
#emailBtn {
	height: 32px;
	width: 108px;
	padding: 0;
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	color: white;
	border-radius: 5px;
}

#emailBtn:hover {
	background-color: #17a2b8;
}

/* number타입의 화살표 지우기 */
input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}

/* 핸드폰번호 입력 input창 */
.phone {
	width: 118px;
	height: 45px;
}

.gender {
	width: 100%;
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

/* 우편번호 */
.address {
	height: 40px;
	width: 180px;
	margin-right: 30px;
}

.btn {
	height: 40px;
	width: 108px;
	padding: 0;
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	color: white;
	border-radius: 5px;
}

.btn:hover {
	background-color: #17a2b8;
}

#submitBtn {
	margin-bottom : 70px;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<jsp:include page="myPageSideMenu.jsp"></jsp:include>

	<%-- 이메일 전화번호 -> 구분자를 이용하여 분리된 배열 형태로 저장 --%>
	<c:set var="email" value="${fn:split(loginMember.email, '@') }" />
	<c:set var="phone" value="${fn:split(loginMember.phone, '-') }" />
	<c:set var="comPhone" value="${fn:split(comMember.comPhone, '-') }" />
	<c:set var="comAddress" value="${fn:split(comMember.comAddress, ',') }" />
	
	<div class="wrapper2">
	
		<div class="changeTitle">
			<p style="font-size: 30px;">내 정보 수정</p>
		</div>
		<br>


		<form action="${contextPath}/member/myPageUpdateCompany.do" method="POST"
			onsubmit="return memberChangevalidate();">
			<div class="memberChange">
				<hr>
				<br>

				<div class="lb">
						<label for="email">아이디</label><br>
						 <h6>${loginMember.memberId}</h6>
					</div>
					<br>

				<!-- 닉네임 -->
				<div>
					<div class="lb">
						<label for="userName">닉네임</label> <br>
					</div>
					<div class="ip">
						<input type="text" class="inputTag" id="userName" name="userName" value="${loginMember.memberNickName}" required>
					</div>
					<div>
						<span id="checkUserName">&nbsp;</span>
					</div>
				</div>

				<!-- 이메일 -->
				<div>
					<div class="lb">

						<label for="email">이메일</label><br>
						 <h6>${loginMember.email}</h6>
						 

					</div>
				
					<br>

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
						</select> &nbsp;-&nbsp; 
						<input type="number"
							class="display-ib inputTag phone" id="phone2" name="phone2" value="${phone[1]}" required>
						&nbsp;-&nbsp; 
						<input type="number"
							class="display-ib inputTag phone" id="phone3" name="phone3" value="${phone[2]}" required>
					</div>
					<div>
						<span id="checkPhone">&nbsp;</span>
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

				<br>

				<!-- 업체 -->
				<div>
				
					<div class="lb">
						<label for="comName">업체명</label> <h4 style="color:orange">[ ${comMember.comName} ]</h4>
					</div>
					<br>
				
					<div class="lb">
						<label for="phone">업체번호</label>
					</div>
					<div class="ip">
						<select class="display-ib inputTag phone" id="phone4" name="phone4"
							required>
							<option>02</option>
							<option>051</option>
							<option>053</option>
							<option>032</option>
							<option>062</option>
							<option>042</option>
							<option>052</option>
							<option>044</option>
							<option>031</option>
							<option>033</option>
							<option>043</option>
							<option>041</option>
							<option>063</option>
							<option>061</option>
							<option>054</option>
							<option>055</option>
							<option>064</option>
							<option>070</option>
						</select> &nbsp;-&nbsp; 
						<input type="number" class="display-ib inputTag phone" id="phone5" name="phone5" value="${comPhone[1]}" required>
						&nbsp;-&nbsp; 
						<input type="number" class="display-ib inputTag phone" id="phone6" name="phone6" value="${comPhone[2]}" required>
					</div>
					<div>
						<span id="checkPhone">&nbsp;</span>
					</div>
				</div>

				<!-- 주소 API  -->
				<p style="font-size: 16px;">주소</p>
				<div class="row mb-3 form-row">
					<div class="col-md-3">
						<label for="postcodify_search_button"
							style="font-weight: normal; font-size: 13px;">우편번호</label>
					</div>
					<div class="col-md-3 display-ib">
						<input type="text" name="post"
							class="form-control postcodify_postcode5 inputTag address" value="${comAddress[0]}">
					</div>
					<div class="col-md-3 display-ib">
						<button type="button" class="btn btn-info"
							id="postcodify_search_button">검색</button>
					</div>
				</div>

				<div class="row mb-3 form-row">
					<div class="col-md-3">
						<label for="address1"
							style="font-weight: normal; font-size: 13px;">도로명 주소</label>
					</div>
					<div class="col-md-9">
						<input type="text"
							class="form-control postcodify_address inputTag" name="address1"
							id="address1" value="${comAddress[1]}">
					</div>
				</div>

				<div class="row mb-3 form-row">
					<div class="col-md-3">
						<label for="address2"
							style="font-weight: normal; font-size: 13px;">상세주소</label>
					</div>
					<div class="col-md-9">
						<input type="text"
							class="form-control postcodify_details inputTag" name="address2"
							id="address2" value="${comAddress[2]}">
					</div>
					<div>
						<br>
					</div>
				</div>

				<br>
				<br>
				<div class="submit">
					<input id="submitBtn" type="submit" name="" value="변경하기">
				</div>
			</div>
		</form>
	</div>


	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	
	
	<script>
		// 전화번호 옵션 정보 불러오기
		(function() {
			$("#phone1 > option").each(function(index, item) {
				if ($(item).val() == "${phone[0]}") {
					$(item).prop("selected", true);
				}
			});
		})();
		
		// 업체 전화번호 옵션 정보 불러오기
		(function() {
			$("#phone4 > option").each(function(index, item) {
				if ($(item).val() == "${comPhone[0]}") {
					$(item).prop("selected", true);
				}
			});
		})();

		// 이메일 옵션 정보 불러오기
		(function() {
			$("#email2 > option").each(function(index, item) {
				if ($(item).val() == "${email[1]}") {
					$(item).prop("selected", true);
				}
			});
		})();
		
		// 성별 옵션 정보 불러오기
		(function() {
			$("#gender > option").each(function(index, item) {
				if ($(item).val() == "${loginMember.gender}") {
					$(item).prop("selected", true);
					
				}
			});
		})();
		
	    $(function(){
	        $("#postcodify_search_button").postcodifyPopUp();
	    });
	</script>




</body>
</html>