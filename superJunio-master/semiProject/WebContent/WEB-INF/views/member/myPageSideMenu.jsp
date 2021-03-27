<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#container {
	width: 1100px;
	height: 800px;
	display: block;
	margin: auto;

	/* background-color: ghostwhite; */
}

.aside {
	width: 200px;
	height: 100%;
	float: left;
	border-right: 1px solid #e5e5e5;

	/* border: 1px solid red; */
}

.aside>ul {
	list-style-type: none;
	/* 불렛 없음 */
	padding: 0;
	line-height : 45px;
}

/* 메뉴 위아래 간격 */q
.aside>ul>li {
	padding: 10px 0px 10px 0px;
}

.aside>ul>li>a {
	text-decoration: none;
	/* 불렛 없음 */
	font-weight: 700;
	color: black;

	/* border: 1px solid red; */
}

.aside>ul>li>a:hover {
	color: orange;
}
</style>

<div id="container">
	<div class="aside">

		<ul>
			<c:choose>
			<%-- 일반 회원일 때 --%>
				<c:when test="${!empty loginMember && (loginMember.memberAdmin == 'G') }">
					<li><a href="${contextPath}/member/myPageNormal.do" class="nav-items" id="nav-mypage">내 정보 수정</a></li>
				</c:when>
				
				<%-- 업체 회원일 때 --%>
				<c:when test="${!empty loginMember && (loginMember.memberAdmin == 'C') }">
					<li><a href="${contextPath}/member/myPageCompany.do" class="nav-items" id="nav-mypage">내 정보 수정</a></li>
				</c:when>
			</c:choose>
			
			<li><a href="${contextPath}/member/myPageUpdatePw.do" class="aside-items" id="changePw">비밀번호 변경</a></li>
			<li><a href="${contextPath}/member/myPageInquiryPost.do" class="aside-items" id="postCheck">내가 쓴 글 조회</a></li>
			<li><a href="${contextPath}/member/myPageWithdrawal.do" class="aside-items" id="withdrawal">회원 탈퇴</a></li>
		</ul>
	</div>
