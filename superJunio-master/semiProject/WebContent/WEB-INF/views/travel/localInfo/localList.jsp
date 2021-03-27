<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- c 태그를 쓰면 라이브러리 선언해준다!!!! -->


	
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>지역정보</title>

<!-- 구글폰트 사용 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<!-- 부트스트랩 사용을 위한 css 추가 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<!-- 부트스트랩 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>

<style>
* {
	font-family: 'Noto Sans KR', sans-serif;
	font-weight: 500; /* 굵기 지정(100, 300, 400, 500, 700) */
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

.wrapper {
	margin: auto;
}

/* ------------------------------------------------------ */
#container {
	width: 1100px;
	min-height: 800px;
	display: block;
	margin: auto;
	box-sizing: border-box;
	/* background-color: ghostwhite; */
}

/* ------------------------ 내용(컨텐츠부분) ------------------------------ */

.main {
	width: 1100px;
	/* width: 900px; */
	height: 100%;
	float: left;
}

.main-title{
    font-size: 30px;
    font-weight: 700;
    padding-top: 25px;
}

/* ------------------------ 상단 빅배너 ------------------------ */
#localInfo-bigBanner {
	width: 1100px;
	height: 200px;
	position: relative;
}

#big-banner-title {
	/* background-color: yellow; */
	color: white;
	font-size: 45px;
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

/* ------------------------ 인기도시 ------------------------ */
.hot-city {
	margin: 30px 0px 40px 0px;
}

.hot-city-area {
	width: 1100px;
	height: 100px;
	display: flex;
	margin-top: 20px;
	/* border:1px solid red; */
}

.hot-city-box {
	width: 100px;
	height: 100px;
	/* background-color:lightgrey; */
	/* padding: 0px 5px 0px 5px ; */
	margin: 0px 14px 0px 0px;
}

.hot-city-thumbnail-img {
	width: 100px;
	height: 60px;
	/* border: 1px solid red; */
}

.hot-city-title {
	width: 100px;
	height: 40px;
	text-align: center;
	/* border:1px solid blue; */
}

/* ----------------------지역 선택------------------------------- */
.row-item{
	margin : 30px 0px 30px 0px;
}

.locationSelect{
    margin : 0 0 30px 83%;
    
}

.locationNm{
    background-color : #fff;
    font-size: 16px;
    width:75px;
    border: 3px solid #8bd2d6;
    border-radius: 5px;
}

/* ------------------------- 테이블 ---------------------------- */
.post-thumbnail-img {
	width: 200px;
	height: 100px;
	background-color: lightgray;
}


/* 썸네일 이미지 크기 */
.boardThumbnail>img {
   width: 300px;
   height: 150px;
}




/* 테이블 : 수직 가운데 정렬 */
.table td, .table th {
	text-align: center;
	vertical-align: middle !important;
}

/* 테이블 가로 간격 */
.table-1st { width: 70px; } /* 글번호 */
.table-2th { width: 70px; } /* 지역 */
.table-3rd { width: 350px; } /* 사진 */
.table-4th { width: 350px; } /* 제목 */

/* 페이징 색 변경 */
.page-item>a { color: #8ad2d5; }

.page-item>a:hover { color: #3d8588; }

/* ------------------------ 글쓰기버튼 ---------------------------------- */
.row-item {
	width: 100%;
	box-sizing: border-box;
}

/* 버튼 */
.btn_class {
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	cursor: pointer;
	outline: none;
}

.btn_class:hover {
	background-color: #17a2b8;
}

#insertLocal {
	width: 100px;
	height: 40px;
	margin: 40px 0 50px 89%;
	line-height: 20px;
	border-radius: 5px;
	color: #fff;
	font-size: 17px;
}

/*---------------------------- 페이징(부트스트랩) ---------------------------------  */
.page-item>a {
	color: black;
	text-decoration: none;
}

.page-item>a:hover {
	color: hotpink;
}

/* ---------------------------- 검색창 스타일 ---------------------------- */
 
#searchForm > * {
	top: 0;
	vertical-align: top;
}

select[name='searchKey']{
	width: 100px; 
	display: inline-block;
}

input[name='searchValue']{
	width: 25%; 
	display: inline-block;
}

#searchBtn{
	width: 100px; 
	display: inline-block;
	
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
}

button#searchBtn:hover{
	background-color: #17a2b8;
}

.list-wrapper{
	height: 540px;
}

/* 목록에 마우스를 올릴 경우 손가락 모양으로 변경 */
#list-table td:hover{
	cursor : pointer;
}



/* 페이징 색 */

.page-item>a:hover {
	color: orange;
}


</style>
</head>

<body>

	<!--------------------- header 연결 -------------------- -->
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<div id="container">
		<!-- --------------------- 사이드 메뉴 연결 --------------------- -->
		<%--
		<jsp:include page="/WEB-INF/views/travel/travelSideMenu.jsp"></jsp:include>
		--%>

		<!-- --------------------- 메인Contents --------------------- -->
		<div class="main">
			
			<div class="main-title">지역별로 여행스팟을 추천해줄게요🚄</div>
			
			<div class="hot-city">
				<h4>인기도시</h4>
				<div class="hot-city-area">
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_서울.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="서울">서울</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_인천.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="인천">인천</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_대구.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="대구">대구</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_부산.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="부산">부산</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_경기도.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="경기도">경기도</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_강원도.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="강원도">강원도</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_경상도.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="경상도">경상도</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_전라도.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="전라도">전라도</div>
					</div>
					<div class="hot-city-box">
						<img src="${pageContext.request.contextPath}/resources/image/travel/localList/여행_인기도시_제주도.jpg"
						class="hot-city-thumbnail-img">
						<div class="hot-city-title" name="제주도">제주도</div>
					</div>
				</div>
			</div>
			
			
			<%-- <div id="localInfo-bigBanner">
				<img
					src="${pageContext.request.contextPath}/resources/image/travel/localList/local-bigbanner(900x200)_seoul.jpg">
				<div id="big-banner-title">서울</div>
			</div> --%>
			
			<!----------------------------- 지역 선택/옵션 ----------------------------->
            
            <!--  
            <div class="row-item">
                <div class="locationSelect">
                    <span style="font-size:16px; font-weight:bold;">대한민국 ></span>
                    <select class="locationNm" name="location">
                        <option value="강원도">강원도</option>
                        <option value="경기도">경기도</option>
                        <option value="경상도">경상도</option>
                        <option value="광주">광주</option>
                        <option value="대구">대구</option>
                        <option value="대전">대전</option>
                        <option value="부산">부산</option>
                        <option value="서울" selected>서울</option>
                        <option value="세종">세종</option>
                        <option value="울산">울산</option>
                        <option value="인천">인천</option>
                        <option value="전라도">전라도</option>
                        <option value="제주">제주</option>
                        <option value="충청도">충청도</option>
                    </select>
                </div>
            </div>
			 -->

			<!-- ------------------------------------------------------------------------- -->

			<table class="table" id="list-table">
				<thead>
					<tr>
						<th scope="col" class="table-1st">글번호</th>
						<th scope="col" class="table-2th">지역</th>
						<th scope="col" class="table-3rd"></th>
						<th scope="col" class="table-4th">제목</th>
						<th scope="col" class="table-5th">조회수</th>
						<th scope="col" class="table-6th">작성일</th>
					</tr>
				</thead>
				
				<%-- 게시글 목록 출력 --%>
				<tbody>
					<c:choose>
						<c:when test="${empty tList}">
	                        <tr>
	                           <td colspan="5">존재하는 게시글이 없습니다.</td>
	                        </tr>
	                    </c:when>
					
						<c:otherwise> <%-- 조회된 게시글 목록이 있을 때 --%>
							<c:forEach var="travel" items="${tList}">
								<tr>
									<th scope="row">${travel.travelNo}</th>
									
									<td>${travel.travelLocation}</td>
									
									<!-- ----------- 썸네일.. ----------- -->
		                            <td class="boardThumbnail">
		                              	
		                              	<%-- 썸네일 출력 --%>
		                              	<c:forEach var="thumbnail" items="${fList}">
		                              		
		                              		<%-- 현재 출력하려는 게시글 번호와 썸네일 목록 중
		                              			부모 게시글 번호가 일치하는 썸네일 정보가 있다면 --%>
		                              		<c:if test="${travel.travelNo == thumbnail.parentBoardNo}">
		                              			<img src="${contextPath}/resources/uploadImages/travel/${thumbnail.fileName}">
		                              		</c:if>
		                              	
		                              	</c:forEach>
		                            </td>	
									
									
									<td>${travel.travelTitle}</td>
									<td>${travel.travelReadCount}</td>
									<td>${travel.travelBoardDate}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<!-- 등록하기 버튼  (관리자로 로그인 했을 때만 보인다.-->
			<c:if test="${loginMember.memberAdmin == 'A' }">
				<div class="row-item">
					<button type="button" class="btn_class" id="insertLocal"
						onclick="location.href = '${contextPath}/travel/localInsertForm.do'">
						등록하기</button>
				</div>
			</c:if>
			
			
			
			<%---------------------- Pagination ----------------------%>
	         <%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
	         <c:choose>
	         	<%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
	         	<c:when test="${!empty param.searchKey && !empty param.searchValue}">
	         		<c:url var="pageUrl" value="/travelSearch.do"/>
	         		
	         		<!-- 쿼리스트링으로 사용할 내용을 변수에 저장 -->
	         		<c:set var="searchStr" value="&sk=${param.searchKey}&sv=${param.searchValue}"/>
	         	</c:when>
	         	
	         	<c:otherwise>
			        <c:url var="pageUrl" value="/travel/localList.do"/>
	         	</c:otherwise>
	         	
	         </c:choose>
	         
	         
	         
	                  <!-- 화살표에 들어갈 주소를 변수로 생성 -->
	         <%-- 
	         	검색을 안했을 때 : /board/list.do?cp=1
	         	검색을 했을 때 :  /search.do?cp=1&sk=title&sv=49
	          --%>
	         
	         <c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
	         <c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>
	         
	         <%-- EL을 이용한 숫자 연산의 단점 : 연산이 자료형에 영향을 받지 않는다. --%>
	         <%--
	          <fmt : parseNumber> : 숫자 형태를 지정하여 변수 선언 
	          integerOnly="true" : 정수로만 숫자 표현(소수점 버림)
	         --%>
	         
	         <fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / 10}" integerOnly="true"/>
	         <fmt:parseNumber var="prev" value="${c1 * 10}" integerOnly="true"/>
	         <c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}" />
	         
	         <fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / 10 }" integerOnly="true" />
	         <fmt:parseNumber var="next" value="${ c2 * 10 + 1}" integerOnly="true" />
	         <c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}" />
	         
	         
			
			
            <!-- 페이징 -->
            <div class="paging">
                <nav aria-label="Page navigation example">
                    <ul id="pagingBtn" class="pagination pagination-sm justify-content-center">
                    
               <%-- 현재 페이지가 10페이지 초과인 경우 --%>
               <c:if test="${pInfo.currentPage>10}">
                  <!-- 첫 페이지로 이동(<<) -->
                  <li class="page-item"><a class="page-link" href="${firstPage}">&lt;&lt;</a></li>
                  
                  <!-- 이전 페이지로 이동(<)  -->
                  <li class="page-item"><a class="page-link" href="${prevPage}">&lt;</a></li>
               </c:if>
               
               <!-- 페이지 목록  -->
               <c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
               		<c:choose> 
               			<c:when test="${pInfo.currentPage == page }">
								<!-- 현재 보고 있는 페이지는 클릭이 안 되게 한다.  -->               								
		                      <li class="page-item"><a class="page-link" style="color:orange;">${page }</a></li>
               			</c:when>
               			
               			<c:otherwise>
		                      <li class="page-item"><a class="page-link" href="${pageUrl }?cp=${page}${searchStr}">${page }</a></li>
               			</c:otherwise>
               		</c:choose>
               </c:forEach>       
               
               <%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
               <c:if test="${next <= pInfo.maxPage }">
               		  <!-- 다음 페이지로 이동  -->
                      <li class="page-item"><a class="page-link" href="${nextPage }">&gt;</a></li>
                      <li class="page-item"><a class="page-link" href="${lastPage }">&gt;&gt;</a></li>
               </c:if>
                    </ul>
                  </nav>
                </div>
                
                
                
                
                
			
			<!-- 검색필드 -->
			<div class="mb-5">
			<form action="${contextPath}/travelSearch.do" method="GET" class="text-center" id="searchForm">
				<select name="searchKey" class="form-control">
					<option value="location">지역</option>
					<option value="title">제목</option>
					<option value="content">내용</option>
				</select>
				<input type="text" name="searchValue" class="form-control">
				<button class="form-control btn btn-primary" id="searchBtn">검색</button>
			</form>


		</div>
			
			
			
			
		</div>
		
	<!-- 푸터 연결 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	
	</div>







	<script>
    // 게시글 상세보기 기능 (jquery를 통해 작업)
    
    $("#list-table td").on("click",function(){
  	  
  	  // 게시글 번호 얻어오기
  	  var travelNo = $(this).parent().children().eq(0).text();
  	  //console.log(travelNo);
  	  // 클릭이 되는지 테스트
  	  
  	  var url = "${contextPath}/travel/localView.do?cp=${pInfo.currentPage}&no="+ travelNo + "${searchStr}";
  	  //var url = "${contextPath}/travel/localView.do?no="+travelNo;
  	  
  	  location.href = url;
    });
	
    
    
    
    
	// 검색 내용이 있을 경우 검색창에 해당 내용을 작성해두는 기능
    (function(){
       var searchKey = "${param.searchKey}";
       // 파라미터 중 sk가 있을 경우 ex)"49"
       // 파라미터 중 sk가 없을 경우 ex) ""
       var searchValue = "${param.searchValue}";
       
       // 검색창 select의 option을 반복 접근
       $("select[name=searchKey] > option").each(function(index, item){
          // index : 현재 접근 중인 요소의 인덱스
          // item : 현재 접근 중인 요소
          
                // title          title
          if( $(item).val() == searchKey ){ // 제목으로 검색한 경우
            $(item).prop("selected", true);
          }
       });
       
       // 검색어 입력창에 searchValue 값 출력
       $("input[name=searchValue]").val(searchValue);
       
    })();
    
    
    
	
	
	</script>
	
	
	
	

</body>
</html>