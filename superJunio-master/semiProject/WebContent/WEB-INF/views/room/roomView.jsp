<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>숙소 상세조회</title>



<!-- 카카오 API  -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0195b24c7dce0dc71f3dbcf7ca0a12c4&libraries=services,clusterer,drawing"></script>


<!-- css연결  -->
<link rel="stylesheet"
	href="${contextPath}/resources/css/room/roomView.css" type="text/css">

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



</head>
<body>
	<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>


	<div class="wrapper">


		<c:choose>
			<c:when test="${!empty fList }">

				<div class="carousel slide boardImgArea imageArea" id="room-image">

					<!-- 이미지 선택 버튼 -->
					<ol class="carousel-indicators ">
						<c:forEach var="file" items="${fList}" varStatus="vs">

							<li data-slide-to="${vs.index }" data-target="#room-image"
								<c:if test="${vs.first}"> class="active" </c:if>></li>
						</c:forEach>
					</ol>


					<!-- 출력되는 이미지 -->
					<div class="carousel-inner ">
						<c:forEach var="file" items="${fList}" varStatus="vs">

							<div
								class="carousel-item imageArea <c:if test="${vs.first}">active</c:if>">
								<img class="d-block w-100 imageArea boardImg"
									id="${file.fileNo}"
									src="${contextPath}/resources/image/uploadRoomImages/${file.fileName}">
							</div>

						</c:forEach>

					</div>

					<!-- 좌우 화살표 -->
					<a class="carousel-control-prev" href="#room-image"
						data-slide="prev"> <span class="carousel-control-prev-icon"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="carousel-control-next" href="#room-image"
						data-slide="next"> <span class="carousel-control-next-icon"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</c:when>

			<c:otherwise>
				<div class="imageArea">
					<img src="${contextPath}/resources/image/icon/nonImage.png">
				</div>
			</c:otherwise>

		</c:choose>








		<!-- 숙소 이름 -->
		<div class="row-item">
			<p id="roomName">${room.roomName }</p>
		</div>

		<!-- 조회수/좋아요 -->
		<div class="row-item">
			<div class="viewInfo iconArea" style="margin-left: 1020px;">
				<span><img src="${contextPath}/resources/image/icon/view.png"
					class="icon"></span>
				<div class="count">${room.viewCount }</div>
				<!-- 최대 999,999 -->
			</div>
		</div>

		<!-- 숙소 주소 -->
		<div class="row-item">
			<span><img class="icon"
				src="${contextPath}/resources/image/icon/site.png"></span> <span
				id="roomAddress">숙소 주소 : ${room.location2 } </span>
		</div>

		<!-- 숙소 전화번호 -->
		<div class="row-item">
			<span><img class="icon"
				src="${contextPath}/resources/image/icon/phone.png"></span> <span
				id="roomPhone">전화번호 : ${room.phone } </span>
		</div>

		<!-- 숙소 체크인/체크아웃시간 -->
		<div class="row-item">
			<span><img class="icon"
				src="${contextPath}/resources/image/icon/clock.png"></span> <span
				id="roomCheckIn">체크인 : ${room.checkin} </span>
		</div>
		<div class="row-item">
			<span><img class="icon"
				src="${contextPath}/resources/image/icon/clock2.png"></span> <span
				id="roomCheckOut">체크아웃 : ${room.checkout} </span>
		</div>

		<div class="row-item">
			<span><img class="icon"
				src="${contextPath}/resources/image/icon/dog.png"></span> <span
				id="roomCheckIn">출입가능 견종 : ${room.dog } </span>
		</div>

		<hr>




		<c:set var="facilityArr" value="${fn:split(room.facility, ',') }" />

		<div class="row-item" style="margin: 0;">

			<c:forEach var="facility" items="${facilityArr }">
				<c:choose>
					<c:when test="${facility == 'WiFi'  }">
						<div class="facility">
							<div class="icon_area">
								<img class="facility_icon"
									src="${contextPath}/resources/image/icon/WiFi.png">
							</div>
							<div class="text_area">WiFi</div>
						</div>
					</c:when>

					<c:when test="${facility == '주차장'  }">
						<div class="facility">
							<div class="icon_area">
								<img class="facility_icon"
									src="${contextPath}/resources/image/icon/park.png">
							</div>
							<div class="text_area">주차장</div>
						</div>
					</c:when>

					<c:when test="${facility == '수영장'  }">
						<div class="facility">
							<div class="icon_area">
								<img class="facility_icon"
									src="${contextPath}/resources/image/icon/pool.png">
							</div>
							<div class="text_area">수영장</div>
						</div>
					</c:when>

					<c:when test="${facility == 'BBQ' }">
						<div class="facility">
							<div class="icon_area">
								<img class="facility_icon"
									src="${contextPath}/resources/image/icon/BBQ.png">
							</div>
							<div class="text_area">BBQ</div>
						</div>
					</c:when>

					<c:when test="${facility == '마당'  }">
						<div class="facility">
							<div class="icon_area">
								<img class="facility_icon"
									src="${contextPath}/resources/image/icon/yard.png">
							</div>
							<div class="text_area">마당</div>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>





		<hr style="margin-bottom: 15px;">

		<div class="row-item">
			<span class="highlighter">숙소 정보</span>

			<div style="font-size: 15px;">${room.roomInfo }</div>
		</div>
		<hr style="margin-bottom: 15px;">


		<div class="row-item">

			<span class="highlighter">상세위치</span>

			<div id="map" style="height: 400px;">map</div>
		</div>




		<div class="row-item">

			<c:choose>
				<c:when test="${!empty param.sk && !empty param.sv }">
					<c:url var="goToList" value="../room/search">
						<!--../ : 상위 주소로 이동 <상대경로>  -->
						<c:param name="cp">${param.cp }</c:param>
						<c:param name="sk">${param.sk }</c:param>
						<c:param name="sv">${param.sv }</c:param>
					</c:url>
				</c:when>

				<c:otherwise>
					<c:url var="goToList" value="/room/list">
						<c:param name="cp">${param.cp}</c:param>
					</c:url>
				</c:otherwise>
			</c:choose>


			<a href="${goToList }" class="btn_class" id="back">돌아가기</a>

		</div>


		<!-- 업체/관리자만 보이는 버튼 -->
		<c:if
			test="${!empty loginMember && (loginMember.memberAdmin == 'C' && room.memNo == loginMember.memberNo) || loginMember.memberAdmin == 'A'}">
			<div class="row-item" style="margin-top: 50px;">
				<div class="btn_item">

					<%-- 게시글 수정 후 상세조회 페이지로 돌아오기 위한 url 조합 --%>
					<c:if test="${!empty param.sv && !empty param.sk }">
						<%-- 검색을 통해 들어온 상세 조회 페이지인 경우 --%>
						<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
					</c:if>
					<a
						href="updateForm?cp=${param.cp}&roomNo=${param.roomNo}${searchStr}"
						class="btn_class" id="updateBtn">수정</a>
					<button class="btn_class" id="deleteBtn" type="button">삭제</button>
				</div>
			</div>
		</c:if>


	</div>
	<!-- wrapper -->

	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

	<script>
		/*삭제 버튼 클릭했을 때  */
		$("#deleteBtn").on("click", function() {
			if (confirm("해당 숙소를 삭제하시겠습니까?")) {
				location.href = "delete?roomNo=${param.roomNo}"
			}
		});
	</script>



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
						'${room.location2 }',
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
											content : '<div style="font-size: 13px;width:150px;text-align:center;padding:6px 0;">${room.roomName }</div>'
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
