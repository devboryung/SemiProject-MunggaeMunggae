<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>

/*
#container {
	width: 1100px;
	height: 800px;
	display: block;
	margin: auto;
} */ 

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
}

/* 메뉴 위아래 간격 */
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

	<div class="aside">

		<ul>
			<!-- 일반회원시 일반회원수정 / 업체회원시 업체회원수정 각자 마이페이지로 이동시키는 기능추가해야함 -->
			<li><a href="${contextPath}/travel/localList.do"
					class="aside-items" id="aside-localInfo">지역정보</a></li>
			<li><a href="${contextPath}/travel/sightsList.do"
					class="aside-items" id="aside-touristSpot">관광지</a></li>
		</ul>
	</div>