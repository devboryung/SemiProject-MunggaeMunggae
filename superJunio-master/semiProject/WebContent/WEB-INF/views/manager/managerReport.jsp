<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>신고 조회</title>
<head>

	<!-- 구글 폰트 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
        rel="stylesheet">

    <!-- 부트스트랩 사용을 위한 css 추가 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

    <!-- 부트스트랩 사용을 위한 라이브러리 추가 (반드시 jQuery가 항상 먼저여야한다. 순서 중요!) -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>

<style>
.main{
            width:900px;
            height:100%;
            float: left;
        }

        th, td {
            font-size: 13px;
        }

        #reportBtn {
            margin-top: 15px;
            margin-left: 15px;
            background-color : #8ad2d5 ;
            color: white;
            border: white;
            border-radius: 5px;
            width: 150px;
            height: 40px;
        }
        
        #reportBtn:hover, #searchBtn:hover {
            background-color: #17a2b8;
        }

        #searchBtn {
            background-color : #8ad2d5 ;
            color: white;
            border: white;
            border-radius: 5px;
            height: 38px;
        }

        h6 {
            margin-left: 15px;
            height: 30px;
        }

        .page-item > a{
            color : black;
        }

        .page-item > a:hover{
            color: orange;
        }
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<jsp:include page="managerSideMenu.jsp"></jsp:include>

            <div class="main">

                <div id="btnDiv">
                    <a href="#"><button type="menu" id="reportBtn">신고 게시물 조회</button></a>
                </div>

                <br>
                
                <h6>신고 게시물 조회 결과</h6>

                <div id="resultDiv">
                    <table class="table table-hover table-striped text-center" id="result">
                        <thead>
                            <tr>
                                <th>글 번호</th>
                                <th>글 제목</th>
                                <th>아이디</th>
                                <th>신고 사유</th>
                                <th>신고 내용</th>
                            </tr>
                        </thead>
											
											<c:choose>
											<c:when test="${empty report}">
											<tr>
												<td colspan="5">제보된 신고가 없습니다.</td>
											</tr>
											</c:when>
											
											<c:otherwise>
											<c:forEach var="report" items="${report}">
                        <tr>
                            <td>${report.reportNo }</td>
                            <th>${report.boardTitle }</th>
                            <td>${report.memId }</td>
                            <td>${report.reportTitle }</td>
                            <td>${report.reportContent }</td>
                        </tr>
                        </c:forEach>
                        
											</c:otherwise>
                        </c:choose>
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
				
				<c:otherwise><c:url var="pageUrl" value="/manager/managerReport.do"/></c:otherwise>
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

                <div class="search">
                    <form action="${contextPath}/search.do" method="GET" class="text-center" id="searchForm">
                        <select name="sk" class="form-control" style="width: 100px; display: inline-block;">
                            <option value="id">아이디</option>
                            <option value="nickName">닉네임</option>
                            <option value="companyName">업체명</option>
                            <option value="phone">전화번호</option>
                        </select>

                        <input type="text" name="sv" class="form-control" style="width: 25%; display: inline-block;">
                        <button id="searchBtn" style="width: 100px; display: inline-block;">검색</button>
                    </form>
                </div>
            </div>


	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>






</body>
</html>