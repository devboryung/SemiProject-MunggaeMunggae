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
	border-right: 1px solid #e5e5e5;
	
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
/* 	line-height: 50px; */
/* 	margin-top: 3px;
 */}

.pagination {
	justify-content: center;
}


table {
	/* 	text-align: center;
	margin: auto; */
/* 	margin-top: 55px;
 */}

#uls {
/* 	margin-top: 30px;
 */	
}
#uls > li{
	/*  line-height : 80px; */
}
.pagination  a{
	color :black;
	
}
a{
  color:black;
}
.publicTd:hover{
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
.yes{
	color:#B2EBF4;
}
.no{
	color:#FFA7A7;
}

font{
color:gray;
font-size:0.7em;
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
 				<h2 style="text-align:center; margin-top:10px;">Q&A</h2>
 	
 		<form>	
 			<table class="table" id="list-Table">
				<thead class="" style="background-color:#8ad2d5;">
					
					<tr>
						<th scope="col"  style="text-align:center; color:white; ">번호</th>
						<th scope="col"  style="text-align:center; color:white;">제목</th>
						<th scope="col"  style="text-align:center;color:white;">닉네임</th>
						<th scope="col"  style="text-align:center; color:white;">작성일</th>
						<th scope="col"  style="text-align:center; color:white;">조회</th>
					</tr>
				</thead>
				<tbody>
				
				<c:choose>
					<c:when test="${empty bList}">
						<td colspan="5" style="color:black; text-align:center;">존재하는 글이 없습니다.</td>
					</c:when>
					
					<c:otherwise>
				<c:forEach var="board" items="${bList}">
					<tr>
						<th scope="row" style="text-align:center">${board.qnaNo}</th>
				
    			<%-- 관리자 로그인 시 --%>
				<c:choose>
					<c:when test="${loginMember.memberAdmin == 'A'}"> 
					 <c:choose>
							<c:when test="${board.qnaPrivate == 'N' && board.qnaReplyResponse == 'N'}">
								<td class="publicTd" style="color:black;">${board.qnaTitle}<font>  공개글</font> <font class="no">  처리중</font></td>
						 	</c:when>
						
							<c:when test="${board.qnaPrivate == 'Y' && board.qnaReplyResponse == 'N'}">
								<td class="publicTd" style="color:gray;">${board.qnaTitle}<font>  비공개글</font><font class="no">  처리중</font></td>
						 	</c:when>
						 	
							<c:when test="${board.qnaPrivate == 'Y' && board.qnaReplyResponse == 'Y'}">
								<td class="publicTd"style="color:gray;">${board.qnaTitle}<font>  비공개글</font><font class="yes">  답변완료</font></td>
						 	</c:when>
						 	
						 	<c:when test="${board.qnaPrivate == 'N' && board.qnaReplyResponse == 'Y'}">
								<td  class="publicTd" style="color:black;">${board.qnaTitle}<font>  공개글</font><font class="yes">  답변완료</font></td>
						 	</c:when>
					 </c:choose>
				   </c:when>
    			
    			<%-- 회원 본인이  쓴 글 일 경우 --%>
    			    <c:when test="${loginMember.memberNo == board.memNo}">
    			    
    			    <c:choose>
	    			    	
				 		<c:when test="${board.qnaPrivate == 'N' && board.qnaReplyResponse == 'N'}">
							<td class="publicTd" style="color:black;">${board.qnaTitle}<font> 내가 문의한 글</font> <font class="no">  처리중</font></td>
					 	</c:when>
						
						<c:when test="${board.qnaPrivate == 'N' && board.qnaReplyResponse == 'Y'}">
							<td  class="publicTd" style="color:black;">${board.qnaTitle} <font> 내가 문의한 글</font> <font class="yes">  답변완료</font></td>
					 	</c:when>
						
						<c:when test="${board.qnaPrivate == 'Y' && board.qnaReplyResponse == 'N'}">
							<td class="publicTd" style="color:black;">${board.qnaTitle} <font> 내가 문의한 글</font> <font class="no"> 처리중</font></td>
					 	</c:when>
					 	
					 	<c:when test="${board.qnaPrivate == 'Y' && board.qnaReplyResponse == 'Y'}">
								<td class="publicTd"style="color:black;">${board.qnaTitle}<font> 내가 문의한 글</font> <font class="yes"> 답변완료</font></td>
						</c:when>
				
    			    	</c:choose>
    			    </c:when>
    			
    			
    			<%-- 회원 , 비 회원 --%>
    				<c:otherwise>
    				  <c:choose>
					 	<c:when test="${board.qnaPrivate == 'N' && board.qnaReplyResponse == 'N'}">
							<td class="publicTd" style="color:black;">${board.qnaTitle}<font class="no"> 처리중</font></td>
					 	</c:when>
						
						<c:when test="${board.qnaPrivate == 'N' && board.qnaReplyResponse == 'Y'}">
							<td  class="publicTd" style="color:black;">${board.qnaTitle} <font class="yes"> 답변완료</font></td>
					 	</c:when>
						
						<c:when test="${board.qnaPrivate == 'Y' && board.qnaReplyResponse == 'N'}">
							<td class="privateTd" style="color:gray;">비공개  <font class="no"> 처리중</font></td>
					 	</c:when>
					 	
					 	<c:when test="${board.qnaPrivate == 'Y' && board.qnaReplyResponse == 'Y'}">
								<td class="privateTd"style="color:gray;">비공개  <font class="yes"> 답변완료</font></td>
						</c:when>
						
					  </c:choose>
					</c:otherwise>
				</c:choose>
					  
						<td style="text-align:center">${board.memNickName}</td>
						<td style="text-align:center">
						   <%-- 날짜 출력 모양 지정 --%>
                           <fmt:formatDate var="createDate" value="${board.qnaCreateDt}" pattern="yyyy-MM-dd" />
                           <fmt:formatDate var="today" value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd" />
						
						   <c:choose>
                              <%-- 글 작성일이 오늘이 아닐 경우 --%>
                             <c:when test="${createDate != today}">
                                ${createDate}
                             </c:when>
                             <%-- 글 작성일이 오늘일 경우 --%>
                             <c:otherwise>
                                <fmt:formatDate value="${board.qnaCreateDt}" pattern="HH:mm" />
                             </c:otherwise>
                         </c:choose>
						</td>
						<td style="text-align:center">${board.qnaReadCount}</td>
					</tr>
				 </c:forEach>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			</form>
		
			<div id="btns"style="text-align: right; margin-top:10px;">
				
				<input type="button" id="listALL" class="btn-sm" value="모든 문의내역" 
				style=" background-color:#8bd2d5; color:white; border: 3px solid #8bd2d6;
   								 border-radius: 5px;">
				
			    <input type="button" id="publickBtn" class="btn-sm" value="공개 문의내역" 
				style=" background-color:#8bd2d5; color:white; border: 3px solid #8bd2d6;
   								 border-radius: 5px;">
   				
   			  <c:if test="${loginMember.memberAdmin =='A'}">
   				<input type="button" id="incompleteBtn" class="btn-sm" value="미답변 문의내역"
				style=" background-color:#8bd2d5; color:white; border: 3px solid #8bd2d6;
   								 border-radius: 5px;">
		 	   </c:if>
		 		
		 	   <c:if test="${loginMember.memberAdmin != 'A' }">
		 		<input type="button" id="myQna" class="btn-sm" value= "나의 문의내역"
				style=" background-color:#8bd2d5; color:white; border: 3px solid #8bd2d6;
   				border-radius: 5px;">
			  </c:if>
			
			</div>
			<div style="text-align: right; margin-top:10px;" id="">
		
			<input type="button" id="insertBtn" class="btn" value="문의하기" 
			style=" background-color:#8bd2d5; color:white; border: 3px solid #8bd2d6;
 								 border-radius: 5px;">
			</div>
	<%---------------------- Pagination ----------------------%>
	<%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
      <c:choose>
	 <%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
	       <c:when test="${!empty param.sk && !empty param.sv}">
	           <c:url var="pageUrl" value="/centerSearch/qna.do"/>
     <%--쿼리스트링으로 사용할 내용을 변수에 저장 --%>
              <c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
          </c:when>  
           <c:otherwise>
				<c:url var="pageUrl" value="/qna/qna.do"/>
           </c:otherwise>
      </c:choose>			
			
			
		
	<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
	<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>		
			
			
	
	<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / 10}" integerOnly="true"/>
	<fmt:parseNumber var="prev" value="${c1 * 10}" integerOnly="true"/>
	<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}"/>
	
	<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / 10}" integerOnly="true"/>
	<fmt:parseNumber var="next" value="${c2 * 10 +1}" integerOnly="true"/>
	<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr} "/>
			

			<div class="my-5">
				<ul class="pagination pagination-sm justify-content-center">
				<%--현재 페이지가 10페이지 초과인 경우 --%>
				 <c:if test="${pInfo.currentPage > 10 }">
					<li><a class="page-link" href="${firstPage}">&lt;&lt;</a></li>
					<li><a class="page-link" href="${prevPage}">&lt;</a></li>
				</c:if>
					
					
			    <c:forEach var="page" begin="${pInfo.startPage}"  end="${pInfo.endPage}">
					<c:choose>
						<c:when test="${pInfo.currentPage == page}">
							<li><a class="page-link" style="color:orange">${page}</a></li>
						</c:when>
					
					
					<c:otherwise>
						<li><a class="page-link" href="${pageUrl}?cp=${page}${searchStr}">${page}</a></li>
					</c:otherwise>
					
					</c:choose>
				</c:forEach>
	
					<c:if test="${next <= pInfo.maxPage}">
						<li><a class="page-link" href="${nextPage}">&gt;</a></li>
						<li><a class="page-link" href="${lastPage}">&gt;&gt;</a></li>
					</c:if>

				</ul>
				
				
					<div class="my-5">
					  <form action="${contextPath}/centerSearch/qna.do" method="GET" class="text-center" id="searchForm">
						<select name="sk" class="form-control  col-md-2" style="display:inline-block; 
   								 border-radius: 5px;">
							  <option value="qnaTitle">글제목 </option>
							  <option value="qnaContent">내용</option>
							  <option value="qnaTitcont">제목+내용</option>
							  <option value="qnaWriter">닉네임</option>
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
 
	var loginMemberNo="${loginMember.memberNo}";
	console.log(loginMemberNo);
	var url;
 	var qnaNo;
  	var publicBtn ='public';
  	var incompleteBtn ='incompleteBtn';
 	var myQna ='myQna';
 
 	// 공개된 글 클래스만   클릭 시 
	$(".publicTd").on("click",function(){
		
	   qnaNo = $(this).parent().children().eq(0).text();

		 url = "${contextPath}/qna/qnaView.do?cp=${pInfo.currentPage}&no="+qnaNo + "${searchStr}";
		
		location.href = url;
		
	});
	
 	
 	// 전체 문의 내역 조회
	$("#listALL").on("click",function(){
		
 		url = "${contextPath}/qna/qna.do";
		
		location.href = url; 

		
	}); 
 	

 	// 공개된 버튼 클릭시
	$("#publickBtn").on("click",function(){
		
 		url = "${contextPath}/qna/qna.do?public="+publicBtn;
		
		location.href = url; 

		
	}); 
	
	
	// 처리중인 문의내역  버튼 클릭시
	$("#incompleteBtn").on("click",function(){
		
 		url = "${contextPath}/qna/qna.do?incompleteBtn="+incompleteBtn;
		
		location.href = url; 
		
	});
	
	// 나의 문의내역  버튼 클릭시
 	$("#myQna").on("click",function(){
 		
 		if(loginMemberNo ==""){
			    
 			swal({"icon" : "error" ,"title" :"로그인 이후 이용해주세요."});

 			
 		}else{
			url = "${contextPath}/qna/qna.do?myQna="+myQna+"&memNo="+loginMemberNo;
			location.href = url; 
 		}
 		 
		
	}); 
	
	// 문의하기 버튼 클릭 시
 	$("#insertBtn").on("click",function(){
 		
 		if(loginMemberNo ==""){
			    
 			swal({"icon" : "error" ,"title" :"로그인 이후 이용해주세요."});

 			
 		}else{
 			 url = "${contextPath}/qna/qnaForm.do?cp=${pInfo.currentPage}&no="+qnaNo +"${searchStr}";
 			 location.href = url; 
 		}
 		 
		
	}); 
	
	
	
	
	
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