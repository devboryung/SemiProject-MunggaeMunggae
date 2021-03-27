<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행 후기 게시판</title>

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
	
	/* .btn-danger{
		float: right;
	} */
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

<div class="container  my-5">
	<div>
		<div id="board-area">
			<!-- Title -->
				<h3 class="mt-4 display-3">${board.boardTitle }</h3>	
				
				<c:if test="${!empty loginMember && board.memberId != loginMember.memberId }">
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
		
				<c:if test="${!empty trList }">
					<c:forEach var="file" items="${trList}">
						<div class="imgdiv">
							<img class="boardImg" id="${file.fileNo}"
								src="${contextPath}/resources/uploadTripImages/${file.fileName}">
					</div>
					</c:forEach>
				
				</c:if>
				
		
				<hr>
				
				
				<div id="board-content">${board.boardContent }</div>
				
				<div>
				
				
				
				<c:if test="${!empty loginMember && (board.memberId == loginMember.memberId) || loginMember.memberAdmin == 'A'}">
						<button id="deleteBtn" class="btn btn-info float-right">삭제</button>
				
						<a href="updateForm.do?cp=${param.cp}&no=${param.no}${searchStr}" class="btn btn-info float-right ml-1 mr-1">수정</a>
				</c:if>
				
				</div>
				<c:choose>
						<c:when test="${!empty param.sk && !empty param.sv }">
							<c:url var="goToList" value="../search2.do">
								<c:param name="cp">${param.cp}</c:param>
								<c:param name="sk">${param.sk}</c:param>
								<c:param name="sv">${param.sv}</c:param>
							</c:url>
						</c:when>
						
						<c:otherwise>
							<c:url var="goToList" value="tripList.do">
								<c:param name="cp">${param.cp}</c:param>
							</c:url>
						</c:otherwise>
					</c:choose>
					
					
					<a href="${goToList}" class="btn btn-info float-right">목록으로</a>
				
			 <jsp:include page="reply2.jsp"></jsp:include>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="${contextPath}/tripBoard/reportAction.do" method="post">
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
			
		});
			
			
			$('#reportBtn').click(function(e){
				e.preventDefault();
				$('#reportModal').modal("show");
				
			});
		</script>
</body>
</html>