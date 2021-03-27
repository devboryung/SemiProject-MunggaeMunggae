<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<style>
    .insert-label {
      display: inline-block;
      width: 80px;
      line-height: 40px
    }
    
    .boardImg{
    	cursor : pointer;
    }
</style>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>


<div class="container my-5">
	<h3 align="center">게시글 수정</h3><hr>


	<c:if test="${!empty param.sk && !empty param.sv}">
		<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
	</c:if>
	
	<form action="update.do?cp=${param.cp}&no=${param.no}${searchStr}" method="post" 
			  enctype="multipart/form-data" role="form" onsubmit="return updateValidate();">
			  
			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">제목</label> 
				<input type="text" class="form-control" id="boardTitle" name="boardTitle" size="70" value="${board.boardTitle }">
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">작성자</label>
				<h5 class="my-0" id="writer">${board.memberId }</h5>
			</div>
			
			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">작성일</label> <fmt:formatDate value="${board.boardCreateDate }" pattern="yyyy년 MM월 dd일 HH:mm:ss"/>
			</div>
			<hr>
			
			<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">업로드<br>이미지</label>
					<div class="mr-2 boardImg" id="contentImgArea1">
						<img id="contentImg0" width="150" height="150">
					</div>
					<div class="mr-2 boardImg" id="contentImgArea1">
						<img id="contentImg1" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea2">
						<img id="contentImg2" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea3">
						<img id="contentImg3" width="150" height="150">
					</div>
			</div>
			
			<div id="fileArea">
				<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)"> 
				<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
				<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)"> 
				<input type="file" id="img3" name="img3" onchange="LoadImg(this,3)">
			</div>
			
			<div class="form-group">
				<div>
					<label for="content">내용</label>
				</div>
				<textarea class="form-control" id="boardContent" name="boardContent" rows="15" style="resize: none;">${board.boardContent }</textarea>
			</div>
			
			
			<div class="text-center">
				<button type="submit" class="btn btn-info">수정</button>
				<button type="button" class="btn btn-info"
				onclick="location.href='${header.referer}'">이전으로</button>
			</div>
			  
	</form>

</div>


	<script>

		// 유효성 검사 
		function updateValidate() {
			if ($("#boardTitle").val().trim().length == 0) {
				alert("제목을 입력해 주세요.");
				$("#boardTitle").focus();
				return false;
			}

			if ($("#boardContent").val().trim().length == 0) {
				alert("내용을 입력해 주세요.");
				$("#boardContent").focus();
				return false;
			}
		}
		
		 // 이미지 영역을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
    $(function () {
       $("#fileArea").hide();

      $(".boardImg").on("click",function(){
        var index = $(".boardImg").index(this);
        $("#img" + index).click();
      });
    });
		 

    // 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
    function LoadImg(value, num) {
      if (value.files && value.files[0]) {
        var reader = new FileReader();
        // 자바스크립트 FileReader
       	// 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는 File 혹은 Blob객체를 이용해 파일의 내용을 읽고 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체
				
        reader.readAsDataURL(value.files[0]);
        // FileReader.readAsDataURL()
      	// 지정된의 내용을 읽기 시작합니다. Blob완료되면 result속성 data:에 파일 데이터를 나타내는 URL이 포함 됩니다.
      	
       	// FileReader.onload
				// load 이벤트의 핸들러. 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생합니다.
        reader.onload = function (e) {
        	//console.log(e.target.result);
        	// e.target.result
        	// -> 파일 읽기 동작을 성공한 객체에(fileTag) 올라간 결과(이미지 또는 파일)
        	
          $(".boardImg").eq(num).children("img").attr("src", e.target.result);
        }

      }
    }
    
	// 이미지 배치        여기서 오류나는거 신경x 스트립트
    <c:forEach var="file" items="${fList}">
       $(".boardImg").eq( ${file.fileLevel} ).children("img")
             .attr("src", "${contextPath}/resources/uploadImages/${file.fileName}");
       
    </c:forEach>    

	</script>
	



<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

</body>
</html>