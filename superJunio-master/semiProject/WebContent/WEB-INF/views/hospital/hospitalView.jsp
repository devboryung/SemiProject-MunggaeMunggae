<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동물병원 상세조회 페이지</title>



<link rel="stylesheet" href="${contextPath}/resources/css/hospital/hospitalView.css" type="text/css">

<!-- 부트스트랩 사용을 위한 css 추가 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
    integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

<!-- 부트스트랩 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
    crossorigin="anonymous"></script>

</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"/>

	<div class="wrapper">




        <!-- 이미지 출력 -->
        
        
        <c:choose>
         <c:when test="${!empty fList }">
            <div class="carousel slide boardImgArea imageArea" id="hospital-image">
               
               <!-- 이미지 선택 버튼 -->
               <ol class="carousel-indicators ">
                  <c:forEach var="file" items="${fList}" varStatus="vs">
                     
                     <li data-slide-to="${vs.index }" data-target="#hospital-image"  
                           <c:if test="${vs.first}"> class="active" </c:if> >
                     </li>
                  </c:forEach>
               </ol>
               
               
               <!-- 출력되는 이미지 -->
               <div class="carousel-inner ">
                  <c:forEach var="file" items="${fList}" varStatus="vs">
                  
                     <div class="carousel-item imageArea <c:if test="${vs.first}">active</c:if>">
                        <img class="d-block w-100 imageArea boardImg" id="${file.fileNo}" 
                           src="${contextPath}/resources/image/uploadHospitalImages/${file.fileName}">
                     </div>
                     
                  </c:forEach>
               
               </div> 
               
               <!-- 좌우 화살표 -->
               <a class="carousel-control-prev" href="#hospital-image" data-slide="prev">
               		<span class="carousel-control-prev-icon"></span> <span class="sr-only">Previous</span>
               </a> 
               <a class="carousel-control-next" href="#hospital-image" data-slide="next">
               		<span class="carousel-control-next-icon"></span> <span class="sr-only">Next</span>
               </a>
            </div>
        </c:when>
        
        <c:otherwise>
        	<div class="imageArea" >
        		<img  src="${contextPath}/resources/image/icon/nonImage.png">
        	</div>
        </c:otherwise>
        
        </c:choose>
        
        
     
    
    
    
        <!-- 동물병원 이름 -->
        <div class="row-item" >
            <p id="hospitalName">${hospital.hospNm }</p>
        </div> 

        <!-- 조회수 -->
        <div class="row-item">
            <div class="viewInfo iconArea" style="margin-left: 1020px;">
                <span><img src="${contextPath}/resources/image/icon/view.png" class="icon" style="margin-right: 0px;"></span>
                <div class="count">${hospital.viewCount }</div><!-- 최대 999,999 -->
            </div>
        </div>
        
        <!-- 동물병원 주소 -->
        <div class="row-item" >
            <span><img class="icon" src="${contextPath}/resources/image/icon/site.png"></span>
            <span id="hospitalAddress">${hospital.location2 } </span>
        </div> 
        
        
        
    
        <!-- 동물병원 전화번호 -->
        <div class="row-item" >
            <span><img class="icon" src="${contextPath}/resources/image/icon/phone.png"></span>
            <span id="hospitalPhone">전화번호 : ${hospital.phone } </span>
        </div> 

        <!-- 동물병원 운영시간 -->
        <div class="row-item" >
            <span><img class="icon" src="${contextPath}/resources/image/icon/clock.png"></span>
            <span id="hospitalHours">운영시간 : ${hospital.openingTime } ~ ${hospital.closingTime }</span>
        </div> 

        <hr>




<!-- 부대시설 출력  -->
		<%-- 부대시설을  구분자를 이용하여 분리된 배열 형태로 저장 --%>
		<c:set var="facilityArr" value="${fn:split(hospital.hospFacility,',') }"/>
		<!-- ${facility[0]}  : WiFi  -->

        <div class="row-item" style="margin:0;">
        
   	    <c:forEach var="facility" items="${facilityArr }">
   	    	<c:choose>
					<c:when test="${facility == 'WiFi' }">
		      	      <div class="facility">
		        	        <div class="icon_area">
		        	            <img class="facility_icon" src="${contextPath}/resources/image/icon/WiFi.png">
		        	        </div>
		        	        <div class="text_area"> 
		         	           WiFi
		          	      </div>
		         	   </div>
					</c:when>
		          
			        <c:when test="${facility == '주차' }">    
			            <div class="facility">
			                <div class="icon_area">
			                    <img class="facility_icon" src="${contextPath}/resources/image/icon/park.png">
			                </div>
			                <div class="text_area"> 
			                    주차
			                </div>
			            </div>
			        </c:when>    
			        
			        <c:when test="${facility == '예약' }">    
				            <div class="facility">
				                <div class="icon_area">
				                    <img class="facility_icon" src="${contextPath}/resources/image/icon/appointment.png">
				                </div>
				                <div class="text_area"> 
				                    예약
				                </div>
				            </div>
			            
			          </c:when>   
		            
		           <c:when test="${facility == '24시간' }">      
			            <div class="facility">
			                <div class="icon_area">
			                    <img class="facility_icon" src="${contextPath}/resources/image/icon/24hour.png">
			                </div>
			                <div class="text_area"> 
			                    24시
			                </div>
			            </div>
	             </c:when>    
	   	    	</c:choose>
	   	    </c:forEach> 
     
        </div> 
        
        
        
        <hr style="margin-bottom: 15px;">

        <div class="row-item" >
            	<span class="highlighter">병원 정보</span>
            <div style="font-size:15px;">
	               ${hospital.hospInfo }
            </div>
        </div>
        <hr style="margin-bottom: 15px;">


        <div class="row-item">
        	
            <span class="highlighter">상세위치</span>

            <div id="map">
            </div>

        </div>
        
	
	
	<div class="row-item">
	
		<c:choose>
			<c:when test="${!empty param.sk && !empty param.sv }">
				<c:url var="goToList" value="../hospital/search">
												<!--../ : 상위 주소로 이동 <상대경로>  -->
					<c:param name="cp">${param.cp }</c:param>
					<c:param name="sk">${param.sk }</c:param>
					<c:param name="sv">${param.sv }</c:param>
				</c:url>
			</c:when>
			
			<c:otherwise>
				<c:url var="goToList" value="/hospital/list">
					<c:param name="cp">${param.cp}</c:param>
				</c:url>
			</c:otherwise>	
		</c:choose>


		<a href="${goToList }" class="btn_class" id="back">돌아가기</a>
		
	</div>
		
		
		    <!-- 관리자만 보이는 버튼 -->
        <c:if test="${!empty loginMember && loginMember.memberAdmin == 'A'   }">
	        <div class="row-item" style="margin-top:50px;margin-bottom: 50px;">
	            <div class="btn_item">
	            	
	            	<!-- 	수정 버튼 클릭 -> 수정 화면 -> 수정 성공 -> 상세조회 화면
					검색 -> 검색목록 -> 상세조회 -> 수정 버튼 클릭 -> 수정화면 -> 수정 성공 -> 상세조회 화면
					 -->	 
					<%-- 게시글 수정 후 상세조회 페이지로 돌아오기 위한 url 조합 --%>
					 <c:if test="${!empty param.sv && !empty param.sk }">
					 		<%-- 검색을 통해 들어온 상세 조회 페이지인 경우 --%>
					 	<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
					 </c:if>
	                <a href="updateForm?cp=${param.cp}&hospitalNo=${param.hospitalNo}${searchStr}" 
	                			class= "btn_class"  id="updateBtn">수정</a>
	                <button class= "btn_class"  id="deleteBtn" type="button">삭제</button>
	            
	        </div>
        </c:if>

    </div><!-- wrapper -->


	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
<script>
	
	
	/*삭제 버튼 클릭했을 때  */
	$("#deleteBtn").on("click",function(){
		if(confirm("해당 병원을 삭제하시겠습니까?")){
			location.href="delete?hospitalNo=${param.hospitalNo}"
		}
	});
	
	</script>
	
	

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0195b24c7dce0dc71f3dbcf7ca0a12c4&libraries=services,clusterer,drawing"></script>

	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};

		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption);
		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();

		// 주소로 좌표를 검색합니다
		geocoder
				.addressSearch(
						'${hospital.location2 }',
						function(result, status) {

							// 정상적으로 검색이 완료됐으면 
							if (status === kakao.maps.services.Status.OK) {

								var coords = new kakao.maps.LatLng(result[0].y,
										result[0].x);

								// 결과값으로 받은 위치를 마커로 표시합니다
								var marker = new kakao.maps.Marker({
									map : map,
									position : coords
								});

								// 인포윈도우로 장소에 대한 설명을 표시합니다
								var infowindow = new kakao.maps.InfoWindow(
										{
											content : '<div style="font-size: 13px;width:150px;text-align:center;padding:6px 0;">${hospital.hospNm }</div>'
										});
								infowindow.open(map, marker);

								// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
								map.setCenter(coords);
							} else {
								console.log(result);
							}
						});
	</script>
</body>
</html>