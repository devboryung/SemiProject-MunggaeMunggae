 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>html문서 제목</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<!-- Bootstrap core JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

<!-- sweetalert : alert창을 꾸밀 수 있게 해주는 라이브러리 https://sweetalert.js.org/ -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<style>

/*  #wrapper {
    width: 1100px;
 	height: 100%;
    margin:  auto;
    border: 1px soild black;
    display: block;
   }	 */
  
  #wrapper{ 
   	width: 1100px;
	height: 100%;
	margin: 0px auto;
   }
   
/*  .aside {
	width: 200px;
	height: 100%;
	float: left;
	border-right: 1px solid #e5e5e5;
}
 */

 #contenthead{
	width: 900px;
    height: 100%;
    float:left;
	border-left: 1px solid #e5e5e5;
	
} 

/* #uls > li{
    list-style: none;
 	 line-height: 80px;
    
} */
#button > button{
 background-color:#8bd2d6; 
 color:white;
}

.g{
    font-size: 13px;
}
 
 .aside {
	width: 200px;
	height: 100%;
	float: left;
/* 	border-right: 1px solid #e5e5e5;
 */
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
 
/* 이미지 관련 스타일 */
 .boardImg{
width : 100%;
height: 100%;
max-width : 600px;
max-height: 300px;
margin : auto;
}
.boardImgArea{
 height: 300px;
}
.carousel-indicators > li{
background-color: #ccc !important;
}
.carousel-control-prev-icon {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E") !important;
}

.carousel-control-next-icon {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E") !important;
}
li{
    list-style: none;
}
a{
  color:black;
}
.bigImg:hover{
  cursor : pointer;
}


/* 댓글 스타일 */


</style>
<body>
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>
 <div id="wrapper">
     <div class="aside" id="aside">
         <ul id="uls">
	        <li><a href="${contextPath}/notice/notice.do" class="">공지사항</a></li>
			<li><a href="${contextPath}/faq/faq.do" class="">자주묻는질문</a></li>
			<li><a href="${contextPath}/qna/qna.do" class="">Q&A</a></li>
         </ul>
    </div>  
    <div id="contenthead">
     <div class="">
            <div class="">
                 <div class="" style="margin-left:30px;">
                    <h2 style="margin-top:30px;">${qna.qnaTitle}</h2>   
 			    </div>
                    <div class="col-md-12" style="text-align:right; font-size: 0.5em;
					color: gray;">조회수:${qna.qnaReadCount}</div>
       </div>
        </div>  
        <hr>         
         <div class="" style="margin-left:30px;">
                <div class="row">
                    <div class="col-md-8" style="color:gray;font-size: 0.8em;">
                 	       글쓴이 :${qna.memNickName}
                    </div>
                    <div class="col-md-4" style="text-align:right; font-size: 0.5em;
					color: gray;">
			                        작성일 :<fmt:formatDate value="${qna.qnaCreateDt}" pattern="yyyy년MM월dd일HH:mm:ss"/>
                    </div>
            </div>

           <div class="">
          	 <c:if test="${!empty fList}">
                 <div class="row"  style="margin-top:50px;">
                   
                    <div class="col-md-12">
                       
                        <div class="carousel slide boardImgArea" id="board-image">
                            <ol class="carousel-indicators">
                             
                             <c:forEach var="file" items="${fList}" varStatus="vs">
                                
                                 <c:if test = "${fn:length(fList) > 1}">
	                                <li data-slide-to="${vs.index}" data-target="#board-image"
		                 					<c:if test="${vs.first}">class="active"</c:if>>
	                                </li>
                              	 </c:if>
                              
                              </c:forEach>
                           
                            </ol>
                            
                            <div class="carousel-inner">
                               
                              <c:forEach var="file" items="${fList}" varStatus="vs">
                               
                                <div class="carousel-item <c:if test="${vs.first}">active</c:if>">
                                    <img class="d-block w-100 boardImg bigImg"  id="${file.qnaImgNo}" 
                                    src="${contextPath}/resources/image/center/qnaImg/${file.qnaImgName}"/>
                                </div>
                             
                             </c:forEach>
                            </div> 
                            
                           <c:if test = "${fn:length(fList) > 1}">
                            <a class="carousel-control-prev" href="#board-image" data-slide="prev"><span class="carousel-control-prev-icon"></span> <span class="sr-only">Previous</span></a> 
                            <a class="carousel-control-next" href="#board-image" data-slide="next"><span class="carousel-control-next-icon"></span> <span class="sr-only">Next</span></a>
                		   </c:if>
                		  </div>
                    </div>
                </div> 
               		<p style="color:gray;font-size:0.8em;margin-left:119px;">*클릭 시 새 창으로 원본 이미지를 볼 수 있습니다.</p>
                </c:if>
            <div id="" style="height: 250px; margin-top:20px;">
		        ${qna.qnaContent}
            </div>
           </div>
           
          
              <c:choose>	
				  <c:when test="${!empty param.sk && !empty param.sv}">
				   
				    <c:url var="goToList" value="../centerSearch/qna.do">
		                <c:param name="cp">${param.cp}</c:param>
		                <c:param name="sk">${param.sk}</c:param>
		                <c:param name="sv">${param.sv}</c:param>
				    </c:url>
				  
				  </c:when>
				  <c:otherwise>
						<c:url var="goToList" value="qna.do">
		             	  <c:param name="cp">${param.cp}</c:param>					 
						</c:url>
				  </c:otherwise>
			</c:choose>
           
        
        <div class="float-right" id="button">
            <button id="" class=" btn" onclick="location.href='${goToList}'">목록으로</button>
            
            
                        
			<c:if test="${!empty loginMember && ((qna.memNo == loginMember.memberNo) || (loginMember.memberAdmin== 'A'))}">
	            <%-- 게시글 수정 후 상세조회 페이지로 돌아오기 위한 url 조합 --%>
				<c:if test="${!empty param.sv && !empty param.sk}">
				 <%-- 검색을 통해 들어온 상세 조회 페이지인 경우 --%>
					<c:set var="searchStr" value ="&sk=${param.sk}&sv=${param.sv}"/>
			    </c:if>
            
            <button type="button" id="updateBtn"class=" btn  ml-1 mr-1">수정</button>
            <button type="button" id="deleteBtn"class=" btn  ml-1 mr-1">삭제</button>
        	</c:if>
        </div>
        
        
     
        
         </div> 
	          <div style="margin-top:80px; margin-left:20px;">
	    		  답변
	         </div> 
	          <hr>
	    <jsp:include page="/WEB-INF/views/qna/QnaReply.jsp"></jsp:include>
        </div>
  	</div>
     
    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

<script>
var parentQnaNo = "${qna.qnaNo}";
var loginMember ="${loginMember.memberAdmin}";
console.log(parentQnaNo);

// 삭제 버튼 이벤트
$("#deleteBtn").on("click",function(){
	
	
	
	
	swal({
		  title: "정말 삭제하시겠습니까?",
		  text: "삭제되면 데이터를 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		
			  location.href = "deleteQna.do?no=${qna.qnaNo}";			  

		  } else {
		    swal("삭제를 취소하셨습니다.");
		  }
		});
	
	
});

 //수정 버튼 이벤트
$("#updateBtn").on("click",function(){
	

	if(loginMember == 'A'){
		
		
		swal({
			  title: "수정하시겠습니까?",
			  text: "정말 수정 하시겠습니까 ?",
			  icon: "warning",
			  buttons: true,
			  dangerMode: true,
			})
			.then((willDelete) => {
			  if (willDelete) {
			
				location.href ="qnaUpdateForm.do?cp=${param.cp}&no=${param.no}${searchStr}";	  

			  } else {
			    swal("수정을 취소하셨습니다.");
			  }
			});
		

	}else{
		
		
		 $.ajax({
 			  url : "${contextPath}/centerReply/replyCheck.do",
 			  data : { "parentQnaNo" : parentQnaNo,
 				       },
 			  
 			  type: "post",
 			  success :function(check){
 				  
 				  
 				  if(check == 'Y'){  
 					  
 				   
 				    swal({"icon" : "error" ,"title" :"이미 문의 답변이 처리 됐습니다. 새로 문의 해주세요."});
 					  
 					  
 				  }else{
 					  
 							
 			         location.href ="qnaUpdateForm.do?cp=${param.cp}&no=${param.no}${searchStr}";		  
 					  
 				  }
 			
 			  },error : function(){
 				  
 				  console.log("수정 체크 중 오류 발생")
 			  }
 			  
 			  
 		  });
	}
	
}); 
 
 // 사진 원본 보기

var img = document.getElementsByTagName("bigImg");
for (var x = 0; x < img.length; x++) {
  img.item(x).onclick=function() {window.open(this.src)}; 
}


</script>



</body>
</html>