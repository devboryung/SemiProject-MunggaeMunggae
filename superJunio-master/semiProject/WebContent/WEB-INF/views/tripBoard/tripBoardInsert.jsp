<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
<style>
    .boardImg{
    	cursor : pointer;
    }
    

</style>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>
<div class="container my-5">
	<h3 class="display-3">게시글 등록</h3>
	<hr>
	
	<form action="${contextPath}/tripBoard/insert.do" method="post" 
				  enctype="multipart/form-data" role="form" onsubmit="return boardValidate();">
	
		<div class="form-inline mb-2">
			<label class="input-group-addon mr-3 insert-label">제목</label> 
			<input type="text" class="form-control" id="boardTitle" name="boardTitle" size="70">
		</div>	
	
		<div class="form-inline mb-2">
			<label class="input-group-addon mr-3 insert-label">작성자</label>
			<h5 class="my-0" id="writer">${loginMember.memberId }</h5>
		</div>
	
		<div class="form-inline mb-2">
			<label class="input-group-addon mr-3 insert-label">작성일</label>
			<h5 class="my-0" id="today"></h5>
		</div>
	
		<hr>
		
		<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">썸네일</label>
					<div class="boardImg" id="titleImgArea">
						<img id="titleImg" width="200" height="200">
					</div>
				</div>
		
		<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">업로드<br>이미지</label>
					<div class="mr-2 boardImg" id="contentImgArea1">
						<img id="contentImg0" width="150" height="150">
					</div>
					<div class="mr-2 boardImg" id="contentImgArea2">
						<img id="contentImg1" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea3">
						<img id="contentImg2" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea4">
						<img id="contentImg3" width="150" height="150">
					</div>
				</div>
	
			<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
					<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)"> 
					<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
					<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)"> 
					<input type="file" id="img3" name="img3" onchange="LoadImg(this,3)">
					<input type="file" id="img4" name="img4" onchange="LoadImg(this,4)">
				</div>
				
				<div class="form-group">
					<div>
						<label for="content">내용</label>
					</div>
					<textarea class="form-control" id="boardContent" name="boardContent" rows="15" style="resize: none;"></textarea>
				</div>
				
				<div class="text-center">
					<button type="submit" class="btn btn-info">게시글등록</button>
					<button type="button" class="btn btn-info">목록으로</button>
				</div>
				
	</form>
</div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

<script>
(function printToday(){
	// 오늘 날짜 출력 
		var today = new Date();
	var month = (today.getMonth()+1);
	var date = today.getDate();

	var str = today.getFullYear() + "-"
    		+ (month < 10 ? "0"+month : month) + "-"
    		+ (date < 10 ? "0"+date : date);
	$("#today").html(str);
})();


//유효성 검사 
function boardValidate() {
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
$(function(){
	$("#fileArea").hide();	// #fileArea 요소를 숨김
	
	$(".boardImg").on("click", function(){	// 이미지 영역이 클릭되었을 때
			
		// 클릭한 이미지 영역 인덱스 얻어오기
		var index = $(".boardImg").index(this);
			// -> 클릭된 요소가 .boardImg중 몇번째 인덱스인지 반환
		
			//console.log(index);
		
			// 클릭된 영역 인덱스에 맞는 input file 태그 클릭
			$("#img" + index).click();
			
	});
	
});
 
// 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
function LoadImg(value, num) {
	
  // value.files : 파일이 업로드되어 있으면 true
  // value.files[0] : 여러 파일 중 첫 번째 파일이 업로드 되어 있으면 true
  if(value.files && value.files[0]){	// 해당 요소에 업로드 된 파일이 있을 경우
	  
	  var reader = new FileReader();	// 읽을 수 있는 객체 생성
		// 자바스크립트 FileReader
    // 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 
    // 읽을 파일을 가리키는 File 혹은 Blob객체를 이용해 파일의 내용을 읽고 
    // 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체

    reader.readAsDataURL(value.files[0]);	// 읽어
 		// FileReader.readAsDataURL()
  // 지정된의 내용을 읽기 시작합니다. Blob완료되면 result속성 data:에 파일 데이터를 나타내는 URL이 포함 됩니다.
  
	reader.onload = function(e){	// 다 읽었을 때
		// FileReader.onload
			// load 이벤트의 핸들러. 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생합니다.
		
			// 읽어들인 내용(이미지 파일)을 화면에 출력
			$(".boardImg").eq(num).children("img").attr("src", e.target.result)
			// e.target.result : 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용
			
  }

	  
  }
}


</script>


</body>
</html>