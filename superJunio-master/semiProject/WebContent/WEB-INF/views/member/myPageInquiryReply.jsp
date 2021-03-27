<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지_내가 쓴 댓글 조회</title>
<head>
<!-- 구글 폰트 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<!-- 부트스트랩 사용을 위한 css 추가 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<!-- 부트스트랩 사용을 위한 라이브러리 추가 (반드시 jQuery가 항상 먼저여야한다. 순서 중요!) -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>

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

.main {
	width: 900px;
	height: 100%;
	float: left;
}

td {
	font-size: 13px;
	cursor : pointer;
}

th {
	font-size: 13px;
	cursor : default;
}

#resultDiv {
	cursor : default;
}



#inquiryBtn {
	margin-top: 15px;
	margin-left: 15px;
	background-color: #8ad2d5;
	color: white;
	border: white;
	border-radius: 5px;
	width: 150px;
	height: 40px;
}
#inquiryBtn1 {
	margin-top: 15px;
	margin-left: 15px;
	background-color: #8ad2d5;
	color: white;
	border: white;
	border-radius: 5px;
	width: 150px;
	height: 40px;
}

#inquiryBtn:hover, #inquiryBtn1:hover, #searchBtn:hover {
	background-color: #17a2b8;
}

.search{
	margin-bottom : 20px;
}

#searchBtn {
	background-color: #8ad2d5;
	color: white;
	border: white;
	border-radius: 5px;
	height: 38px;
}

h6 {
	margin-left: 15px;
	height: 30px;
}

.page-item>a {
	color: black;
}

.page-item>a:hover {
	color: orange;
}

</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<jsp:include page="myPageSideMenu.jsp"></jsp:include>

	<div class="main">

                <div id="btnDiv">
                    <a href="myPageInquiryPost.do"><button type="menu" id="inquiryBtn">내가 쓴 게시글</button></a>
                    <a href="myPageInquiryReply.do"><button type="menu" id="inquiryBtn1">내가 쓴 댓글</button></a>
                </div>

                <br>
                
                <h6>댓글 조회 결과</h6>

                <div id="resultDiv">
                    <table class="table table-hover table-striped text-center" id="result-table">
                        <thead>
                            <tr>
                                <th id="title1">글 번호</th>
                                <th id="title1">글 제목</th>
                                <th id="title1">댓글 내용</th>
                                <th id="title1">작성일</th>
                            </tr>
                        </thead>
											<tbody>
												<c:choose>
												<c:when test="${empty myReply}">
													<tr>
														<td colspan="4">존재하는 게시글이 없습니다.</td>
													</tr>
												</c:when>
												
												
												<c:otherwise>
												<c:forEach var="myReply1" items="${myReply}">
                        <tr>
                            <td>${myReply1.parentBoardNo}</td>
                            <td>${myReply1.boardTitle}</td>
                            <td>${myReply1.replyContent}</td>
                            <td>
                            	<fmt:formatDate var="createDate" value="${myReply1.replyCreateDate}" pattern="yyyy-MM-dd" /> <!-- 2021-01-05 -->
															<fmt:formatDate var="today" value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" /> <c:choose>
															<%-- 글 작성일이 오늘이 아닐 경우 --%>
																<c:when test="${createDate != today}">${createDate}</c:when>
					
																<%-- 글 작성일이 오늘일 경우 --%>
																<c:otherwise>
																	<fmt:formatDate value="${myReply1.replyCreateDate}" pattern="HH:mm"/>
																	<!-- 오늘 작성한거면 시간이 나온다. -->
																</c:otherwise>
															</c:choose>
                            </td>
                        </tr>
                        </c:forEach>
												</c:otherwise>
												</c:choose>
											</tbody>
                    </table>
                </div>

                <%---------------------- Pagination ----------------------%>
			<%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
			
			<c:choose>
				<%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
				<c:when test="${!empty param.sk && !empty param.sv}">
					<c:url var="pageUrl" value="/search.do"/>
					
					<%-- 쿼리 스트링으로 사용할 내용을 변수에 저장 --%>
					<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/></c:when>
				
				<c:otherwise><c:url var="pageUrl" value="/member/myPageInquiryReply.do"/></c:otherwise>
			</c:choose>
			
			
			<!-- 화살표에 들어갈 주소를 변수로 생성 -->
			<%--
				검색을 안했을 때 : /board/list.do?cp=1
				검색을 했을 때 : /search.do?cp=1&sk=title&sv=49
			 --%>
			<c:set var="firstPage" value="${pageUrl}?cp=1"/>
			<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>
			
			<%-- EL을 이용한 숫자 연산의 단점 : 연산이 자료형에 영향을 받지 않는다. ex) 5/2 = 2.5 --%>
			<%-- <fmt:parseNumber> : 숫자 형태를 지정하여 변수 선언 
				integerOnly="true" : 정수로만 숫자 표현 (소수점 버림)
			--%>
			
			<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1)/10}" integerOnly="true"/>
			<fmt:parseNumber var="prev" value="${c1 * 10}" integerOnly="true"/>
			<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}"/> <!-- /board/list/do?cp=10  -->
			
			<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9)/10}" integerOnly="true"/>
			<fmt:parseNumber var="next" value="${c2 * 10 + 1}" integerOnly="true"/>
			<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}"/>
			
			
			
			<div class="my-5">
				<ul class="pagination pagination-sm justify-content-center">
				
					<%-- 현재 페이지가 10페이지 초과인 경우 --%>
					<c:if test="${pInfo.currentPage>10}">
					
						<li class="page-item"><!-- 첫 페이지로 이동(<<) -->
							<a class="page-link" href="${firstPage}">&lt;&lt;</a>
						</li>
						
						<li class="page-item"> <!-- 이전 페이지로 이동(<) -->
							<a class="page-link" href="${prevPage}">&lt;</a>
						</li>
					</c:if>
					
					<!-- 페이지 목록 (숫자만) ex) 1 2 3 4 5 6 7 8 9 10 -->
					<c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
											<!-- for(int page=0; page<=10; page++) 비슷하다고 생각하면 된다. -->
						<c:choose>
							<c:when test="${pInfo.currentPage == page}">
								<li class="page-item">
									<a class="page-link" style="color:orange;">${page}</a>
								</li>
							</c:when>
							
							<c:otherwise>
								<li class="page-item">
									<a class="page-link" href="${pageUrl}?cp=${page}${searchStr}">${page}</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					
					<%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
					<c:if test="${next <= pInfo.maxPage}">
						<li class="page-item"> <!-- 다음 페이지로 이동(>) -->
							<a class="page-link" href="${nextPage}">&gt;</a>
						</li>
						
						<li class="page-item"><!-- 마지막 페이지로 이동(>>) -->
							<a class="page-link" href="${lastPage}">&gt;&gt;</a>
						</li>
						
					</c:if>

				</ul>
			</div>


</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>


<script>
$("#result-table td").on("click", function(){
	
	var boardNo = $(this).parent().children().eq(0).text();
	var url = "${contextPath}/freeBoard/view.do?cp=${pInfo.currentPage}&no="
		+ boardNo + "${searchStr}";

location.href = url;			
	
	
});


</script>



</body>
</html>