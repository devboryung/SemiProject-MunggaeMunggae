<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NoticeCenter</title>
<!-- 부트스트랩을 사용을 위한 css 추가 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<!-- 부트스트랩 사용을 위한 라이브러리 추가 -->
<!-- jquery가 항상 먼저여야된다! -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>
</head>
<style>
#bbbb {
	float: left;
	height: 1000px;
	width: 200px;
}
#aa {
	float: left;
	height: 1000px;
	width: 900px;
/* 	text-align:center;
 */}

#aaaaa {
	width: 1100px;
	height: 1000px;
	margin: 0px auto;
}

ul>li {
	list-style: none;
/* 	line-height: 50px;
 *//* 	margin-top: 3px;
 */}

.pagination {
	justify-content: center;
}


table {
	/* 	text-align: center;
	margin: auto; */
 
 */}

#uls {
/* 	margin-top: 30px;
 */	
}
#uls > li{
/* 	 line-height : 80px;
 */}
.pagination  a{
	color :black;
	
}
a{
  color:black;
}
#list-table{

width: 100%;
height :100%;

}
.tableTd:hover{
  cursor : pointer;
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
<body>

	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>
	<div id="aaaaa">
		<div class="aside">
			<ul id="uls">
				<li><a href="${contextPath}/notice/notice.do">공지사항</a></li>
				<li><a href="${contextPath}/faq/faq.do">자주묻는질문</a></li>
				<li><a href="${contextPath}/qna/qna.do">Q&A</a></li>
			</ul>
		</div>
		<div class='content' id="aa">
 				<h2 style="text-align:center; margin-top:10px;">공지사항</h2>
  		
  		<form>	
  			<table class="table" id="list-Table">
				<thead class="" style="background-color:#8ad2d5;">
					<tr>
						<th scope="row" width="" style="  text-align:center; color:white; ">번호</th>
						<th scope="row" width="" style=" text-align:center;color:white;">제목</th>
						<th scope="row" width="" style=" text-align:center;color:white;">닉네임</th>
						<th scope="row" width=""  style=" text-align:center;color:white;">작성일</th>
						<th scope="row"width=""  style=" text-align:center;color:white;">조회</th>
					</tr>
				</thead>
				
				  <c:choose>
                  
                  <c:when test="${empty bList}">
                     <tr>
                        <td colspan="5" style="text-align:center;">존재하는 게시글이 없습니다.</td>
                     </tr>
                  
                  </c:when>
                  <c:otherwise>
				   <c:forEach var="noticeList" items="${bList}">
					<tr>
					<!-- 	<th scope="row" width="50px;" style="text-align:center "></th> -->
						<th scope="row" style="text-align:center;">${noticeList.noticeNo}</th>
						<td class="tableTd" style="">${noticeList.noticeTitle}</td>
						<td class="tableTd" style="text-align:center;">${noticeList.memNickName}</td>
						<td class="tableTd" style="text-align:center;">
						  <fmt:formatDate var="createDate" value="${noticeList.noticeCreateDt}" pattern="yyyy-MM-dd" />
                          <fmt:formatDate var="today" value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd" />
                              
                              <c:choose>
                                 <%-- 글 작성일이 오늘이 아닐 경우 --%>
                                 <c:when test="${createDate != today}">
                                    ${createDate}
                                 </c:when>
                                 <%-- 글 작성일이 오늘일 경우 --%>
                                 <c:otherwise>
                                    <fmt:formatDate value="${noticeList.noticeCreateDt}" pattern="HH:mm" />
                                 </c:otherwise>
                              </c:choose>
						
						</td>
						<td class="tableTd" style="text-align:center;">${noticeList.noticeReadCount}</td>
					</tr>
					</c:forEach>
                  
                  </c:otherwise>
                  
                  </c:choose>
				
		
			</table>
			</form>
			
 			<c:if test="${!empty loginMember && loginMember.memberAdmin =='A'}">
 				<div style="text-align: right;" id="insertBtn">
					<input type="button" id="insertBtn"class="btn" value="글쓰기" 
					style=" background-color:#8bd2d5; color:white; border: 3px solid #8bd2d6;
	   								 border-radius: 5px;">
				</div>
 			</c:if>

	      <c:choose>
	                  <%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
	          <c:when test="${!empty param.sk && !empty param.sv}">
	              <c:url var="pageUrl" value="/centerSearch.do"/>
	                   <%--쿼리스트링으로 사용할 내용을 변수에 저장 --%>
	              <c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
	          </c:when>  
	      
	           <c:otherwise>
					<c:url var="pageUrl" value="/notice/notice.do"/>
	           </c:otherwise>
	      </c:choose>		

			<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
			<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>

			<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / 10}" integerOnly="true"/>
			<fmt:parseNumber var="prev" value="${c1 * 10}" integerOnly="true"/>
			<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}"/>
			
			<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / 10}" integerOnly="true"/>
			<fmt:parseNumber var="next" value="${c2 * 10 +1}" integerOnly="true"/>
			<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}"/>



			<div class="my-5">
				<ul class="pagination pagination-sm justify-content-center">
				
				<c:if test="${pInfo.currentPage > 10 }">
					<li><a class="page-link" href="${firstPage}">&lt;&lt;</a></li>
					
					<li><a class="page-link" href="${prevPage}">&lt;</a></li>
				</c:if>


			 <c:forEach var="page" begin="${pInfo.startPage}"  end="${pInfo.endPage}">
				<c:choose>
					<c:when test="${pInfo.currentPage == page }">
					<li><a class="page-link" style="color:orange">${page}</a></li>
					</c:when>
					<c:otherwise>
					<li><a class="page-link" href="${pageUrl}?cp=${page}${searchStr}">${page}</a></li>
					</c:otherwise>
					
				</c:choose>
			</c:forEach>
					
				 <c:if test="${next <= pInfo.maxPage }">
					
					<li><a class="page-link" href="${nextPage}">&gt;</a></li>
					<li><a class="page-link" href="${lastPage}">&gt;&gt;</a></li>
				</c:if>
				
				
				</ul>
					<div class="my-5">
						<form action="${contextPath}/centerSearch/notice.do" method="GET" class="text-center" id="searchForm">
						<select name="sk" class="form-control col-md-2" style="display:inline-block; 
   								 border-radius: 5px;">
							  <option value="noticeTitle">글제목 </option>
							  <option value="noticeContent">내용</option>
							  <option value="noticeTitCont">제목+내용</option>
							  <option value="noticeWriter">닉네임</option>
						</select>
							<input type="text" name="sv" class="form-control"
								style="width: 350px;  border: 3px solid #8bd2d6;
   								 border-radius: 5px; display: inline-block;" required>
							<button class="form-control"
								style="width: 100px; display: inline-block; background-color:#8bd2d6; color:white;margin-bottom: 5px;">검색</button>
						</form>

					</div>
			
			</div>

		</div>

	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>



</body>
 <script>
 
 	// 글쓰기 버튼과 같이 사용하기 위해서
  	var NoticeBoardNo;
  	var url;
  	 

	
  	// 게시글 상세보기 기능
	$("#list-Table .tableTd").on("click",function(){

		NoticeBoardNo = $(this).parent().children().eq(0).text();
		url = "${contextPath}/notice/noticeView.do?cp=${pInfo.currentPage}&no="+NoticeBoardNo +"${searchStr}";

		location.href = url; 
		
		
	});
	
 	// 글쓰기 버튼 클릭시
	$("#insertBtn").on("click",function(){
		
 		url = "${contextPath}/notice/noticeInsertForm.do?cp=${pInfo.currentPage}&no="+NoticeBoardNo +"${searchStr}";
		
		location.href = url; 
		
		
	});
 	
/*  	// 댓글 수 조회 
	$(document).ready(function() {
	    
		
		  $.ajax({
				
			  url :"${contextPath}/centerReply/selectNoticeList.do",
			  data : {"parentNoticeNo" : NoticeBoardNo },
			  type : "get",
			  dataType : "JSON",
			  success : function(listCount) {
				  
				 console.log(listCount);

			
		  },
		  error : function(){
			  console.log("댓글 수 조회 실패");
		  }
	  
	  
	  	});
			  
	  }); */
 
	    
	    
	
	
 	// 검색 내용이 있을 경우 검색창에 해당 내용을 작성해두는 기능
	(function(){
		var searchKey="${param.sk}"; 
		// 파라미터 중 sk가 있을 경우  ex) "49"
		// 파라미터 중 sk가 없을 경우  ex)  " "
		
		var searchValue = "${param.sv}";

		
		// 검색창 select의 option을 반복 접근
		$("select[name=sk] > option").each(function(index,item){
			 // index : 현재 접근중인 요소의 인덱스
			 // item :현재 접근중인 요소
			 
			  // title             title
			 if($(item).val() == searchKey ){ 
				 $(item).prop("selected" , true);
				
			 }
		});	 			
		
		// 검색어 입력창에  searchValue 값 출력
		$("input[name=sv]").val(searchValue);
				
		
	})();
 
 
 
 </script>
</html>