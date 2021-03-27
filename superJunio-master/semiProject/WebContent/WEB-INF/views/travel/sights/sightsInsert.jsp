<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관광지 글쓰기</title>
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
	height: 800px;
	display: block;
	margin: auto;
}

/* -------------------------- 내용(컨텐츠부분) ---------------------------- */
.main {
	width: 900px;
	height: 100%;
	float: left;
	padding-left: 10px;
	/* 왼쪽 간격 띄우기 */
}

/* ------------------------------------------ */
.btn_class{
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
.btn_class{
    border-radius: 5px;
    color: #fff;
    border : 1px solid  #8bd2d6;
    background-color: #8bd2d6;
    cursor: pointer;
    outline:none;
    width : 70px;
    height: 40px;
}
.btn_item{
    margin-left: 45%;
}

.btn_class:hover{
	background-color: #17a2b8;
}



</style>
</head>
<body>

	<!-- --------------------- header 연결 --------------------- -->
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>
ㄴ
	<div id="container">
		<!-- --------------------- 사이드 메뉴 연결 --------------------- -->
		<jsp:include page="/WEB-INF/views/travel/travelSideMenu.jsp"></jsp:include>

		<!-- --------------------- 메인Contents --------------------- -->
		<div class="main">

			<br>
			<h3>관광지 글쓰기</h3>
			<hr>

			<form>
				<div class="form-group row">
					<label for="my-1 mr-sm-2" class="col-sm-2 col-form-label">지역</label>
					<div class="col-sm-10">
						<form class="form-inline">
							<select class="custom-select my-1 mr-sm-2" id="">
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
						</form>
					</div>
				</div>

				<div class="form-group row">
					<label for="inputTitle" class="col-sm-2 col-form-label">장소</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputTitle"
							placeholder="장소명을 입력하세요">
					</div>
				</div>

				<div class="form-group row">
					<label for="inputTitle" class="col-sm-2 col-form-label">주소</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputTitle"
							placeholder="주소를 입력하세요">
					</div>
				</div>
				

				<div class="form-group row">
					<label for="file" class="col-sm-2 col-form-label">이미지 업로드</label>
					<div class="col-sm-10">

						<!-- 이미지 업로드 버튼 -->
						<div class="filebox">
							<label for="file">업로드</label> <input type="file" id="file">
							<input class="file-upload-name" value="파일경로..">
						</div>
					</div>
				</div>
				
				<div class="form-group row">
					<label for="inputTitle" class="col-sm-2 col-form-label">동반견종</label>
					<div class="col-sm-10">
				        <div class="input_tag">
                           <input type="checkbox" class="dog" name="" value="small">소형견
                           <input type="checkbox" class="dog" name="" value="medium">중형견
                           <input type="checkbox" class="dog" name="" value="large">대형견
                        </div>
					</div>
				</div>
				

				<div class="form-group row">
					<label for="writingDate" class="col-sm-2 col-form-label">작성일</label>
					<div class="col-sm-10">
						<input type="text" class="form-control-plaintext" id="writingDate"
							value="2021-01-12">
					</div>
				</div>

				<div class="form-group row">
					<label for="writingContent" class="col-sm-2 col-form-label">내용</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="writingContent" rows="5"
							placeholder="내용을 입력하세요"></textarea>
					</div>
				</div>
			</form>
			
           	<!-- 등록 / 취소 버튼  -->
            <div class="row-item">
                <div class="btn_item">
                    <button class= "btn_class"  id="insertBtn" type="submit">등록</button>
                    <button class= "btn_class"  id="resetBtn" type="reset">취소</button>
                </div>
            </div>

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
	</script>


</body>
</html>