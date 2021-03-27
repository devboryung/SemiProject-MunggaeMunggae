<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<div id="container">
	<div class="aside">

		<ul>
			<li><a href="${contextPath}/manager/managerNormal.do" class="aside-items" id="customer">회원 조회</a></li>
			<li><a href="${contextPath}/manager/managerReport.do" class="aside-items" id="report">신고 관리</a></li>
		</ul>
	</div>