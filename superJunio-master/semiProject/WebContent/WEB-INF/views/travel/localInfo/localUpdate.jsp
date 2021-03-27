<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- c 태그를 쓰면 라이브러리 선언해준다!!!! -->

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>지역정보 수정</title>
<link rel="stylesheet" href="join.css" type="text/css">

<!-- 구글 폰트 -->
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

<!-- 이미지 파일 업로드 버튼 경로를 위한 제이쿼리 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>


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
}

/* -------------------------- 내용(컨텐츠부분) ---------------------------- */
.main {
	width: 1100px;
/* 	width: 900px; */
	height: 100%;
	float: left;
	padding-left: 10px;
	/* 왼쪽 간격 띄우기 */
}

/* ------------------------------------------ */
.btn_class {
	background-color: #8ad2d5;
}

.btn-primary:hover {
	color: #8ad2d5;
	background-color: #ffffff;
	border-color: #8ad2d5;
}

/* --------------- 업로드 버튼 --------------- */

/* 파일 선택 필드 숨기기 */
.filebox input[type="file"] {
	position: absolute;
	width: 0;
	height: 0;
	padding: 0;
	overflow: hidden;
	border: 0;
}

/* 업로드 버튼 */
.filebox label {
	display: inline-block;
	padding: 6px 12px 6px 12px;
	color: #8ad2d5;
	/* vertical-align: middle; */
	background-color: #fdfdfd;
	cursor: pointer;
	border: 1px solid #8ad2d5;
	border-radius: 5px;
}

/* 파일경로 부분 스타일 */
.filebox .file-upload-name {
	display: inline-block;
	padding: 6px 12px 6px 12px;
	/* vertical-align: middle; */
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-radius: 5px;
	color: gray;
}

/* 등록 취소 버튼 */
.btn_class {
	border-radius: 5px;
	color: #fff;
	border: 1px solid #8bd2d6;
	background-color: #8bd2d6;
	cursor: pointer;
	outline: none;
	width: 70px;
	height: 40px;
}

.btn_item {
	margin-left: 45%;
}

.btn_class:hover {
	background-color: #17a2b8;
}


/* 이미지에 커서를 댔을 때 마우스 모양으로 변경 */
.boardImg{
   cursor : pointer;
}

</style>
</head>
<body>

	<!-- --------------------- header 연결 --------------------- -->
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>

	<div id="container">
		<!-- --------------------- 사이드 메뉴 연결 --------------------- -->
		
		<%-- <jsp:include page="/WEB-INF/views/travel/travelSideMenu.jsp"></jsp:include> --%>

		<!-- --------------------- 메인Contents --------------------- -->
		<div class="main">

			<br>
			<h3>지역정보 글쓰기</h3>
			<hr>

			<form action="${contextPath}/travel/localUpdate.do?cp=${param.cp}&no=${param.no}${searchStr}" method="post" 
              enctype="multipart/form-data" role="form" onsubmit="return boardValidate();">
				<div class="form-group row">
					<label for="my-1 mr-sm-2" class="col-sm-2 col-form-label">지역</label>
					<div class="col-sm-10">
							<select class="custom-select my-1 mr-sm-2" name="travelLocation">
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

				<div class="form-group row">
					<label for="inputTitle" class="col-sm-2 col-form-label">제목</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="boardTitle"
							name="travelTitle" placeholder="제목을 입력하세요" value="${travel.travelTitle }">
					</div>
				</div>

				<div class="form-group row">
					<label for="file" class="col-sm-2 col-form-label">이미지 업로드</label>
					<div class="col-sm-10">

						<!-- 이미지 업로드 버튼 -->
						<!-- 
						<div class="filebox">
							<div>
								<label for="file">업로드</label> <input type="file" id="file"
									name="img1" onchange="LoadImg(this,1)"> <input
									class="file-upload-name" value="파일경로..">
							</div>
						</div> -->
						
						<!-- 강사님 ver 이미지 업로드  -->
						<div class="form-inline mb-2">
			               <label class="input-group-addon mr-3 insert-label">썸네일</label>
			               <div class="boardImg" id="titleImgArea">
			                  <img id="titleImg" width="100" height="100">
			               </div>
			            </div>
			
			            <div class="form-inline mb-2">
			               <label class="input-group-addon mr-3 insert-label">업로드<br>이미지</label>
			               <div class="mr-2 boardImg" id="contentImgArea1">
			                  <img id="contentImg1" width="100" height="100">
			               </div>
			
			               <div class="mr-2 boardImg" id="contentImgArea2">
			                  <img id="contentImg2" width="100" height="100">
			               </div>
			
			               <div class="mr-2 boardImg" id="contentImgArea3">
			                  <img id="contentImg3" width="100" height="100">
			               </div>
			            </div>
						
						
						<!-- 강사님 ver 파일 업로드 하는 부분 -->
			            <div id="fileArea">
			               <input type="file" id="img0" name="img0" onchange="LoadImg(this,0)"> 
			               <input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
			               <input type="file" id="img2" name="img2" onchange="LoadImg(this,2)"> 
			               <input type="file" id="img3" name="img3" onchange="LoadImg(this,3)">
			            </div>

					</div>
				</div>

				<div class="form-group row">
					<label for="writingDate" class="col-sm-2 col-form-label">작성일</label>
					<div class="col-sm-10">
						<div class="my-0" id="today">
							<fmt:formatDate value="${travel.travelBoardDate }" pattern="yyyy년 MM월 dd일 HH:mm:ss"/>
						</div>
						<!-- 작성일 오늘 날짜 출력 -->
					</div>
				</div>

				<div class="form-group row">
					<label for="writingContent" class="col-sm-2 col-form-label">내용</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="boardContent"
							name="travelContent" rows="5" placeholder="내용을 입력하세요">${travel.travelContent }</textarea>
					</div>
				</div>

				<!-- 등록 / 취소 버튼  -->
				<div class="row-item">
					<div class="btn_item">
						<button class="btn_class" id="insertBtn" type="submit">등록</button>
						<button class="btn_class" id="resetBtn" type="reset" onclick ="location.href='${header.referer}'">취소</button>
					</div>
				</div>
			</form>

		</div>
	</div>


	<!-- ************************* 푸터 연결 ************************* -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>


	<script>
		/* 이미지 파일 첨부 경로 뜨게 하는 구문 */
		$("#file").on('change', function() {
			var fileName = $("#file").val();
			$(".file-upload-name").val(fileName);
		});


		// 유효성 검사 
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
		
		// -------------------------------------------------
	      
	      
	      // 이미지 영역을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
	      // 페이지 로딩이 끝나고나면 #fileArea 요소를 숨김.
	      $(function(){
	         $("#fileArea").hide(); 
	         
	         $(".boardImg").on("click",function(){// 이미지 영역이 클릭 되었을 때
	            // 클릭한 이미지 영역 인덱스 얻어오기
	            var index = $(".boardImg").index(this);
	            // 클릭된 요소가 .boardImg 중 몇 번째 인덱스인지 반환
	            
	            //console.log(index);
	            
	            // 클릭된 영역 인덱스에 맞는 input file 태그 클릭
	            $("#img" + index).click();
	            
	            
	         });
	      });
	      

	       
	     // 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
	     function LoadImg(value, num) {
	    	 // value.files : 파일이 업로드되어 있으면 true
	    	 // &&value.files[0] : 여러 파일 중 첫번째 파일이 업로드 되어 있으면 true
	    	 
	    	 if(value.files && value.files[0]) { // 해당 요소에 업로드된 파일이 있을 경우
	    		 
	    		var reader = new FileReader();
	    		// 자바스크립트 FileReader
	        	// 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여
	        	// 읽을 파일을 가리키는 File 혹은 Blob객체를 이용해
	        	// 파일의 내용을 읽고 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체

	    	 	reader.readAsDataURL(value.files[0]);
	    	 	// FileReader.readAsDataURL()
		      	// 지정된의 내용을 읽기 시작합니다.
		      	// Blob완료되면 result속성 data:에 파일 데이터를 나타내는 URL이 포함 됩니다.
		      	
		      	reader.onload = function(e){
			    // FileReader.onload
				// load 이벤트의 핸들러.
				// 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생합니다.
		      	
				// 읽어들인 내용(이미지 파일)을 화면에 출력
				
				$(".boardImg").eq(num).children("img").attr("src", e.target.result);
			    // e.target.result : 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용 
			    
		      	}
	    	 }
	     }
	     
	     
	     
	 	
	 	// 카테고리 초기값 지정
	 	(function(){
	 		$("select[name='travelLocation'] > option").each(function(index, item){
	 			if($(item).text() == "${travel.travelLocation}"){
	 				
	 				$(item).prop("selected", true); 
	 			}
	 		});
	 	})();


	 	// 이미지 배치
	 	<c:forEach var="file" items="${fList}">
	 		$(".boardImg").eq( ${file.fileLevel} ).children("img")
	 			.attr("src", "${contextPath}/resources/uploadImages/travel/${file.fileName}");
	 		
	 	</c:forEach>
	 	
	 	
	 	
	 	
	 	/* 취소 버튼이 눌리면 확인창이 뜬다.  */
	 	$("#resetBtn").on("click",function(){
	 		
	 	 	if( confirm("수정을 취소하시겠습니까?")){
	 	 		
	 	 		location.href = "${header.referer}";
	 	 	}
	 	});
	 	
	     
	     
	     
	     
	</script>


</body>
</html>