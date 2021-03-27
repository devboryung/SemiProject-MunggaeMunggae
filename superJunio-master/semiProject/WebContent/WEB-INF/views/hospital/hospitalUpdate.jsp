<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동물병원 수정</title>




<!-- css연결  -->
<link rel="stylesheet" href="${contextPath}/resources/css/hospital/hospitalUpdate.css" type="text/css">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"/>

<!-- 전화번호를 구분자를 이용하여 분리된 배열 형태로 저장  -->
<c:set var="phone" value="${fn:split(hospital.phone,'-') }"/>


<!-- 동물병원 수정 -->
<div class="wrapper">

        <div class="main">



            <div class="row-item">
                <div id="page_name">병원 수정</div>
                <hr id="hr_tag">
            </div>
            
         <c:if test="${!empty param.sk && !empty param.sv}">
			<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
		</c:if>
            
            
            <div class="update_hospital">
                <form action="${contextPath}/hospital/update?cp=${param.cp}&hospitalNo=${param.hospitalNo}${searchStr}" method="post"  
                      enctype="multipart/form-data"  role="form" onsubmit="return hospitalInsertValidate();">
                    
                    <div class="row-item">
                        <div class="label_name">
                            <label for="location1">
                        	 	<span class="highlighter">지역</span>
                            </label>
                        </div>
                        <div class="input_tag">
                            <select class="full_input" id="location1" name="location1" required>
                                <option value="강원도">강원도</option>
                                <option value="경기도">경기도</option>
                                <option value="경상도">경상도</option>
                                <option value="광주">광주</option>
                                <option value="대구">대구</option>
                                <option value="대전">대전</option>
                                <option value="부산">부산</option>
                                <option value="서울">서울</option>
                                <option value="세종">세종</option>
                                <option value="울산">울산</option>
                                <option value="인천">인천</option>
                                <option value="전라도">전라도</option>
                                <option value="제주">제주</option>
                                <option value="충청도">충청도</option>
                            </select>
                        </div>
                    </div>


                    <div class="row-item">
                        <div class="label_name">
                            <label for="companyName">
                            	<span class="highlighter">병원명</span>
                            </label>
                        </div>
                        <div class="input_tag">
                            <input type="text" class="full_input" id="companyName"  name="hospNm" value="${hospital.hospNm }" autocomplete="off" required>
                        </div>
                    </div>

                    <div class="row-item">
                        <div class="label_name">
                            <label for="phone">
                            	<span class="highlighter">전화번호</span>
                            </label>
                        </div>
                        <div class="input_tag">
                            <select class="phone" id="phone1" name="phone1" required> 
                                <option>02</option>
                                <option>031</option>
                                <option>032</option>
                                <option>033</option>
                                <option>041</option>
                                <option>042</option>
                                <option>043</option>
                                <option>044</option>
                                <option>051</option>
                                <option>052</option>
                                <option>053</option>
                                <option>054</option>
                                <option>055</option>
                                <option>061</option>
                                <option>062</option>
                                <option>063</option>
                                <option>064</option>
                                <option>070</option>
                            </select>
                            &nbsp;-&nbsp;&nbsp;
                            <input type="number" class="phone phoneTest" id="phone2" name="phone2" value="${phone[1] }" required>
                            &nbsp;-&nbsp;
                            <input type="number" class="phone phoneTest" id="phone3" name="phone3" value="${phone[2] }"  required>
                        </div>
                    </div>

                    
                    <div class="row-item">
                        <div class="label_name">
                            <label for="location2">
                            	<span class="highlighter">상세주소</span>
                            </label>
                        </div>
                        <div class="input_tag">
                            <input type="text" class="full_input" id="location2" name="location2" value="${hospital.location2 }" autocomplete="off" required>
                        </div>
                    </div>

                    <div class="row-item">
                        <div class="label_name">
                            <label for="office_hours">
                            	<span class="highlighter">영업시간</span>
                            </label>
                        </div>
                        <div class="input_tag">

                            <input type="text" class="office_hours" id="openTime" placeholder="00:00" name="openTime" value="${hospital.openingTime }" autocomplete="off" required>
                            &nbsp;~&nbsp;&nbsp;
                            <input type="text" class="office_hours" id="closeTime" placeholder="00:00" name="closeTime" value="${hospital.closingTime }" autocomplete="off" required>
                        </div>
                    </div>

                    <div class="row-item">
                        <div class="label_name">
                            <label for="facility">
                            	<span class="highlighter">병원 시설</span>
                            </label>
                        </div>
                        <div class="input_tag">
                           <label for="Wifi" style="cursor:pointer"><input type="checkbox" class="facility" name="hosp_facility" id="Wifi" value="WiFi">WiFi</label>
                           <label for="parcking" style="cursor:pointer"><input type="checkbox" class="facility" name="hosp_facility" id="parcking" value="주차">주차</label>
                           <label for="appointment" style="cursor:pointer"><input type="checkbox" class="facility" name="hosp_facility" id="appointment" value="예약">예약</label>
                          <label for="24hour" style="cursor:pointer"> <input type="checkbox" class="facility" name="hosp_facility" id="24hour" value="24시간">24시간</label>
                        </div>
                    </div>


                    <div class="row-item">
                        <div class="label_name" style="vertical-align:80px;" >
                            <label for="hospital_info" >
                            	<span class="highlighter">동물병원 정보</span>
                            </label>
                        </div>
                        <div class="input_tag">
                            <textarea class="full_input hospital_info" id="hospital_info" rows="10"
                                name ="hospital_info">${hospital.hospInfo }</textarea>
                        </div>
                    </div>
                    
                    

					<!-- 파일 업로드  -->

                    <div class="row-item">
                    	<div class="label_name">
							<label for="titleImgArea">
								<span class="highlighter">썸네일</span>
							</label>
                    	</div>
					<div class="hospitalImg input_tag" id="titleImgArea">
						<img id="titleImg" width="360" height="100" >
					</div>
				</div>

				<div class="row-item"  >
					<div class="label_name">
						<label class="img">
							<span class="highlighter">업로드 이미지</span>
						</label>
					</div>
					<div class="input_tag">
						<div class="hospitalImg imgarea" id="hotpitalImgArea1" style="margin-right:5px;">
							<img id="hospitalImg1" width="65" height="65" >
						</div>
						<div class="hospitalImg imgarea" id="hotpitalImgArea2" style="margin-right:4px;">
							<img id="hospitalImg2" width="65" height="65" >
						</div>
						<div class="hospitalImg imgarea" id="hotpitalImgArea3" style="margin-right:5px;"> 
							<img id="hospitalImg3" width="65" height="65" >
						</div>
						<div class="hospitalImg imgarea" id="hotpitalImgArea4" style="margin-right:5px;">	
							<img id="hospitalImg4" width="65" height="65" >
						</div>	
						<div class="hospitalImg imgarea" id="hotpitalImgArea5">	
							<img id="hospitalImg5" width="65" height="65">
						</div>
					</div>
				</div>
			
			<!-- 파일 업로드 버튼 (숨기기) -->
					<div id="fileArea">
						<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)">
						<!-- multiple 속성 = 사진 여러개 선택 가능  --> 
						<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
						<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)"> 
						<input type="file" id="img3" name="img3" onchange="LoadImg(this,3)">
						<input type="file" id="img4" name="img4" onchange="LoadImg(this,4)">
						<input type="file" id="img5" name="img5" onchange="LoadImg(this,5)">
					</div>



                    <!-- 등록 / 취소 버튼  -->
                    <div class="row-item">
                        <div class="btn_item">
                            <button class= "btn_class"  id="updateBtn" type="submit">수정</button>
                            <button class= "btn_class"  id="resetBtn" type="button">취소</button>
                        </div>
                    </div>
                </form>
	        </div>
	    </div>
	</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>


<script>
/* 취소 버튼이 눌리면 확인창이 뜬다.  */
$("#resetBtn").on("click",function(){
	
 	if( confirm("수정을 취소합니다.")){
 		
 		location.href = "${header.referer}";
 	}
});


/* --------------------유효성 검사---------------  */

function hospitalInsertValidate(){
	
	// 시간 입력 정규식 00:00
	var regExp = /^(0[0-9]|1[0-9]|2[0-4]):([0-5][0-9])$/;
	
	var open = $("#openTime").val();
	var close = $("#closeTime").val();
	
	if(!regExp.test(open) || !regExp.test(close)){
		alert("영업 시간의 형식이 유효하지 않습니다.");
		return false;
	}
	
	/* 병원정보에 내용이 입력이 안 된다면*/
	
	if ($("#hospital_info").val().trim().length ==0){
		alert("동물 병원 정보를 입력해 주세요.");
		$("#hospital_info").focus();
		return false;
	}
	
	
	/* 전화번호 3/4글자 입력  */
	var regExp1 = /^\d{3,4}$/;
	var regExp2 = /^\d{4}$/;
	 var v1 = $("#phone2").val();
	 var v2 = $("#phone3").val();
	 if(!regExp1.test(v1) || !regExp2.test(v2)){
			alert("전화번호를 다시 입력해 주세요.");
			$("#phone2").focus();
		    return false;
		 }	
}


/* 전화번호 4글자 이상 입력 안 되게 지정  */
$(".phoneTest").on("input",function(){
	 if ($(this).val().length > 4) {
	    $(this).val( $(this).val().slice(0, 4));
	 }  
	
})










/* --------------------이미지 첨부---------------  */

//이미지 영역을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
// 페이지 로딩이 끝나고나면 #fileArea 요소를 숨김.
$(function(){
	$("#fileArea").hide(); 
	
	$(".hospitalImg").on("click",function(){// 이미지 영역이 클릭 되었을 때
		// 클릭한 이미지 영역 인덱스 얻어오기
		var index = $(".hospitalImg").index(this);
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
				$(".hospitalImg").eq(num).children("img").attr("src", e.target.result);
	    	// e.target.result : 이벤트가 발생한 요소의 결과 , 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용
	  
		 }
	  }

}
// 이미지 배치
		<c:forEach var="file" items="${fList}">
			$(".hospitalImg").eq(${file.fileLevel}).children("img").attr("src","${contextPath}/resources/image/uploadHospitalImages/${file.fileName}");
		</c:forEach>





		//지역 초기값 지정
		(function(){
			$("#location1>option").each(function(index,item){
				if($(item).val() == "${hospital.location1}"){
					$(item).prop("selected",true);
				}
			});
		})();



		// 번호 앞 부분 지정
		(function(){
				// #phone1의 자식 중 option 태그들을 반복 접근
			$("#phone1 > option").each(function(index, item){
				// index : 현재 접근중인 인덱스
				// item : 현재 접근중인 요소
				
					// 현재 접근한 요소에 써져있는 값과 전화번호 배열의 첫번째 값이 같다면
				if( $(item).text() == "${phone[0]}"){
					// 현재 접근한 요소에 seleted라는 옵션을 추가
					$(item).prop("selected",true);
				}
			});
		})();	




		//*** 등록된 부대시설  체크하기 ***
		(function(){

			// 부대시설에서 문자열을 얻어와 ' , '를 구분자로 하여 분리하기
			var facility = "${hospital.hospFacility}".split(",");
			
			// 체크 박스 요소를 모두 선택하여 반복 접근
			$("input[name='hosp_facility']").each(function(index, item){
			   
			   // interest 배열 내에
			   // 현재 접근 중인 체크박스의 value와 일치하는 요소가 있는지 확인
			   if(facility.indexOf( $(item).val()) != -1 ){
			      $(item).prop("checked", true);
			   }
			});
		})();






</script>

</body>
</html>