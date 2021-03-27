<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판_게시글상세조회</title>
	<link rel="stylesheet" href="./bootstrapt/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./bootstrapt/css/bootstrap.css" />
	<script src="./bootstrapt/js/bootstrap.min.js"></script>
<style>
	.imgdiv{
		height: 300px;
		
	}
	
		.boardImg{
		width : 100%;
		height: 100%;
		
		max-width : 500px;
		max-height: 300px;
		
		display: block;
		
		margin-left : auto;
		margin-right : auto;
	}
	
	.rBtn{
		float: right;
	}
	
	#title {
		font-size : 55px;
	}
	
	
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

<div class="container  my-5">
	<div>
		<div id="board-area">
			<!-- Title -->
				<h6 class="mt-4 display-3" id="title">${board.boardTitle}</h6>
	
				<c:if test="${(!empty loginMember && board.memberId != loginMember.memberId)}">
				<button type="button" class="btn btn-danger rBtn" id="reportBtn" data-bs-toggle="modal" data-bs-target="#reportModal">
  				신고하기
				</button>
				</c:if>
				
				<p class="lead">
					작성자 : ${board.memberId }
				</p>
				
				<hr>
				
				<p>
					<span class="board-dateArea">
						작성일 : <fmt:formatDate value="${board.boardCreateDate}" pattern="yyyy년 MM월 dd일 HH:mm:ss" />
					</span>
					<span class="float-right">조회수 ${board.readCount }</span>
				</p>
		
		
				<hr>
				
				<c:if test="${!empty fList }">
					<c:forEach var="file" items="${fList}">
						<div class="imgdiv">
							<img class="boardImg" id="${file.fileNo}"
								src="${contextPath}/resources/uploadImages/${file.fileName}">
					</div>
					</c:forEach>
				
				</c:if>
				
				
				
				<div id="board-content">${board.boardContent }</div>
				
				<div>
				
				
				
				<c:if test="${!empty loginMember && (board.memberId == loginMember.memberId) || loginMember.memberAdmin == 'A'}">
						<button id="deleteBtn" class="btn btn-info float-right">삭제</button>
				
						<a href="updateForm.do?cp=${param.cp}&no=${param.no}${searchStr}" class="btn btn-info float-right ml-1 mr-1">수정</a>
				</c:if>
				
				</div>
				<c:choose>
						<c:when test="${!empty param.sk && !empty param.sv }">
							<c:url var="goToList" value="../search.do">
								<c:param name="cp">${param.cp}</c:param>
								<c:param name="sk">${param.sk}</c:param>
								<c:param name="sv">${param.sv}</c:param>
							</c:url>
						</c:when>
						
						<c:otherwise>
							<c:url var="goToList" value="freeList.do">
								<c:param name="cp">${param.cp}</c:param>
							</c:url>
						</c:otherwise>
					</c:choose>
					
					
					<a href="${goToList}" class="btn btn-info float-right">목록으로</a>
				
				<jsp:include page="reply.jsp"></jsp:include>
		</div>
	</div>
</div>



<!-- Button trigger modal -->


<!-- Modal -->
<div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="${contextPath}/freeBoard/reportAction.do" method="post">
        	<div class="form-group">
        	<label>신고 제목</label>
        	<input type="text" id="reportTitle" name="reportTitle" class="form-control">
        	</div>
        	<input type="hidden" id="bNum" name="bNum" value="${board.boardNo}"> 
        	<input type="hidden" id="mNum" name="mNum" value="${loginMember.memberNo}"> 
        	<div class="form-group">
        	<label>신고 내용</label>
        	<input type="text" name="reportContent" id="reportContent" class="form-control">
        	</div>
		      <div class="modal-footer">
		      	<button type="reset" class="btn btn-secondary">취소</button>
		      	<button type="submit" class="btn btn-danger">신고하기</button>
		      </div>
        </form>
      </div>
    </div>
  </div>
</div>







    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

<script>
			$("#deleteBtn").on("click", function(){
			
			if(window.confirm("정말 삭제하시겠습니까?")){
				location.href = "delete.do?no=${board.boardNo}";
				
			}
			
			var boardNo = ${board.boardNo};
			console.log(boardNo);
			
		});
			$('#reportBtn').click(function(e){
				e.preventDefault();
				$('#reportModal').modal("show");
				
			});
			
		/* 	$("#reportBtn").on("click", function(){
				$("#no").val(parentBoardNo);
			}); */
			
			

			
	</script>
</body>
</html>