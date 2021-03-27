<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>숙소 수정</title>

<!-- css연결  -->
<link rel="stylesheet"
	href="${contextPath}/resources/css/room/roomUpdate.css" type="text/css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>


	<!-- 주소  -->
	<c:set var="address" value="${fn:split(comMember.comAddress,',') }" />

	<!-- 숙소 등록하기 -->
	<div class="wrapper">
		<div class="main">

			<div class="row-item">
				<div id="page_name">숙소 수정</div>
				<hr id="hr_tag">
			</div>

			<c:if test="${!empty param.sk && !empty param.sv}">
				<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
			</c:if>

			<div class="insert_room">
				<form
					action="${contextPath}/room/update?cp=${param.cp}&roomNo=${param.roomNo}${searchStr}"
					method="post" enctype="multipart/form-data" role="form"
					onsubmit="return roomUpdateValidate();">


					<div class="row-item">
						<div class="label_name">
							<label for="companyName"> <span class="highlighter">숙소명</span>
							</label>
						</div>
						<div class="input_tag" style="font-size: 20px; font-weight: bold;">
							${comMember.comName}</div>
					</div>


					<div class="row-item">
						<div class="label_name">
							<label for="phone"> <span class="highlighter">전화번호</span>
							</label>
						</div>
						<div class="input_tag">${comMember.comPhone }</div>
					</div>


					<div class="row-item">
						<div class="label_name">
							<label for="location2"> <span class="highlighter">상세주소</span>
							</label>
						</div>
						<div class="input_tag">${address[1]}</div>
					</div>

					<div class="row-item">
						<div class="label_name">
							<label for="checkIn"> <span class="highlighter">체크인/체크아웃</span>
							</label>
						</div>
						<div class="input_tag">
							<input type="text" class="checkHours" id="checkIn" name="checkin"
								value="${room.checkin }" autocomplete="off" required>
							&nbsp;/ <input type="text" class="checkHours" id="checkOut"
								name="checkout" value="${room.checkout }" autocomplete="off"
								required>
						</div>
					</div>

					<div class="row-item">
						<div class="label_name">
							<label for="facility"> <span class="highlighter">숙소
									부대 시설</span>
							</label>
						</div>
						<div class="input_tag">
							<label for="WiFi" style="cursor: pointer"> <input
								type="checkbox" class="facility" name="facility" id="WiFi"
								value="WiFi">WiFi
							</label> <label for="주차장" style="cursor: pointer"><input
								type="checkbox" class="facility" name="facility" id="주차장"
								value="주차장">주차장</label> <label for="수영장" style="cursor: pointer"><input
								type="checkbox" class="facility" name="facility" id="수영장"
								value="수영장">수영장</label> <label for="BBQ" style="cursor: pointer"><input
								type="checkbox" class="facility" name="facility" id="BBQ"
								value="BBQ">BBQ</label> <label for="마당" style="cursor: pointer"><input
								type="checkbox" class="facility" name="facility" id="마당"
								value="마당">마당</label>
						</div>
					</div>

					<div class="row-item">
						<div class="label_name">
							<label for="dog"> <span class="highlighter">출입 가능
									견종</span>
							</label>
						</div>
						<div class="input_tag">
							<label for="소형견" style="cursor: pointer"><input
								type="checkbox" class="dog" name="dog" id="소형견" value="소형견">소형견</label>
							<label for="중형견" style="cursor: pointer"><input
								type="checkbox" class="dog" name="dog" id="중형견" value="중형견">중형견</label>
							<label for="대형견" style="cursor: pointer"> <input
								type="checkbox" class="dog" name="dog" id="대형견" value="대형견">대형견
							</label>
						</div>
					</div>

					<div class="row-item">
						<div class="label_name" style="vertical-align: 80px;">
							<label for="room_info"> <span class="highlighter">숙소
									상세 정보</span>
							</label>
						</div>
						<div class="input_tag">
							<textarea class="full_input room_info" id="room_info" rows="10"
								name="room_info">${room.roomInfo }</textarea>
						</div>
					</div>




					<!-- 파일 업로드  -->

					<div class="row-item">
						<div class="label_name">
							<label for="titleImgArea"> <span class="highlighter">썸네일</span>
							</label>
						</div>
						<div class="roomImg input_tag" id="titleImgArea">
							<img id="titleImg" width="360" height="100">
						</div>
					</div>

					<div class="row-item">
						<div class="label_name">
							<label class="img"> <span class="highlighter">업로드
									이미지</span>
							</label>
						</div>
						<div class="input_tag">
							<div class="roomImg imgarea" id="roomImgArea1"
								style="margin-right: 5px;">
								<img id="roomImg1" width="65" height="65">
							</div>
							<div class="roomImg imgarea" id="roomImgArea2"
								style="margin-right: 4px;">
								<img id="roomImg2" width="65" height="65">
							</div>
							<div class="roomImg imgarea" id="roomImgArea3"
								style="margin-right: 5px;">
								<img id="roomImg3" width="65" height="65">
							</div>
							<div class="roomImg imgarea" id="roomImgArea4"
								style="margin-right: 5px;">
								<img id="roomImg4" width="65" height="65">
							</div>
							<div class="roomImg imgarea" id="roomImgArea5">
								<img id="roomImg5" width="65" height="65">
							</div>
						</div>
					</div>

					<!-- 파일 업로드 버튼 (숨기기) -->
					<div id="fileArea">
						<input type="file" id="img0" name="img0"
							onchange="LoadImg(this,0)">
						<!-- multiple 속성 = 사진 여러개 선택 가능  -->
						<input type="file" id="img1" name="img1"
							onchange="LoadImg(this,1)"> <input type="file" id="img2"
							name="img2" onchange="LoadImg(this,2)"> <input
							type="file" id="img3" name="img3" onchange="LoadImg(this,3)">
						<input type="file" id="img4" name="img4"
							onchange="LoadImg(this,4)"> <input type="file" id="img5"
							name="img4" onchange="LoadImg(this,5)">
					</div>



					<!-- 수정 / 취소 버튼  -->
					<div class="row-item">
						<div class="btn_item">
							<button class="btn_class" id="updateBtn" type="submit">수정</button>
							<button class="btn_class" id="resetBtn" type="button">취소</button>
						</div>
					</div>
				</form>

			</div>

		</div>

	</div>
	<!-- wrapper -->

	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>



	<script>
    /* --------------------유효성 검사---------------  */

    function roomUpdateValidate(){
    	
    	// 시간 입력 정규식 00:00
    	var regExp = /^(0[0-9]|1[0-9]|2[0-4]):([0-5][0-9])$/;
    	
    	var checkIn = $("#checkIn").val();
    	var checkOut = $("#checkOut").val();
    	
    	if(!regExp.test(checkIn) || !regExp.test(checkOut)){
    		alert("체크인/체크아웃 시간의 형식이 유효하지 않습니다.");
    		$("#checkIn").focus();
    		return false;
    	}
    	

    	
    	/* 병원정보에 내용이 입력이 안 된다면*/
    	
    	if ($("#room_info").val().trim().length ==0){
    		alert("숙소 정보를 입력해 주세요.");
    		$("#room_info").focus();
    		return false;
    	}
    	
    	/* 견종 필수 체크  */
    	if(!checkedDog()){
    		return false;
    	}
    	
    }



  		/* 견종 필수 체크  */
  	function checkedDog(){
  		var checkedDog = document.getElementsByName("dog");
  		
  		for(var i=0; i< checkedDog.length; i++){
  			if(checkedDog[i].checked == true){
  				return true;
  			}
  		}
  		alert("출입 가능 견종을 하나 이상 선택해 주세요.");
  		return false
  	}

  	/* 취소 버튼이 눌리면 확인창이 뜬다.  */
  	$("#resetBtn").on("click",function(){
  		
  	 	if( confirm("수정을 취소합니다.")){
  	 		
  	 		location.href = "${header.referer}";
  	 	}
  	});

  
    
    
    
    
    /* --------------------이미지 첨부---------------  */

//이미지 영역을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
// 페이지 로딩이 끝나고나면 #fileArea 요소를 숨김.
$(function(){
	$("#fileArea").hide(); 
	
	$(".roomImg").on("click",function(){// 이미지 영역이 클릭 되었을 때
		// 클릭한 이미지 영역 인덱스 얻어오기
		var index = $(".roomImg").index(this);
		// 클릭된 요소가 .hospitalImg 중 몇 번째 인덱스인지 반환
		
		// console.log(index);

		// 클릭된 영역 인덱스에 맞는 input file 태그 클릭
		$("#img" + index).click();
	});
});




// 각 영역에 파일을 첨부했을 때 미리보기 가능하게 하는 함수
function LoadImg(value,num){
	// value.files == input태그에 파일이 업로드되어 있으면 true
	// value.files[0] : 여러 파일 중 첫번째 파일이 업로드 되어있으면 true
	if(value.files && value.files[0] ){ // 해당 요소에 업로드된 파일이 있을 경우
		  var reader = new FileReader();
			// 자바스크립트 FileReader
		    // 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 
		    // 읽을 파일을 가리키는 File 혹은 Blob객체를 이용해 파일의 내용을 읽고 
		    // 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체
	    
	    reader.readAsDataURL(value.files[0]);
	 		// FileReader.readAsDataURL()
   			// 지정된의 내용을 읽기 시작합니다. 
    		// Blob완료되면 result속성 data:에 파일 데이터를 나타내는 URL이 포함 됨.
    
    reader.onload = function(e){
	    	// FileReader.onload
				// load 이벤트의 핸들러. 
				// 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생함.
				
				// 읽어들인 내용(이미지 파일)을 화면에 출력
				$(".roomImg").eq(num).children("img").attr("src", e.target.result);
	    	// e.target.result : 이벤트가 발생한 요소의 결과 , 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용
	  
		 }
	  }
}





		



// 이미지 배치
	<c:forEach var="file" items="${fList}">
		$(".roomImg").eq(${file.fileLevel}).children("img").attr("src","${contextPath}/resources/image/uploadRoomImages/${file.fileName}");
	</c:forEach>

		



// *** 등록된 부대시설  체크하기 ***
(function(){
   
   // 부대시설에서 문자열을 얻어와 ' , '를 구분자로 하여 분리하기
   var facility = "${room.facility}".split(",");
   
   // 체크 박스 요소를 모두 선택하여 반복 접근
   $("input[name='facility']").each(function(index, item){
      
      // interest 배열 내에
      // 현재 접근 중인 체크박스의 value와 일치하는 요소가 있는지 확인
      if(facility.indexOf( $(item).val()) != -1 ){
         $(item).prop("checked", true);
      }
   });
})();

// *** 등록된 견종 체크하기
(function(){
   
   // 부대시설에서 문자열을 얻어와 ' , '를 구분자로 하여 분리하기
   var dog = "${room.dog}".split(",");
   
   // 체크 박스 요소를 모두 선택하여 반복 접근
   $("input[name='dog']").each(function(index, item){
      
      // interest 배열 내에
      // 현재 접근 중인 체크박스의 value와 일치하는 요소가 있는지 확인
      if(dog.indexOf( $(item).val()) != -1 ){
         $(item).prop("checked", true);
      }
   });
})();

    </script>

</body>
</html>