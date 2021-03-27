<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동물병원</title>

<!-- css연결  -->
<link rel="stylesheet"
	href="${contextPath}/resources/css/hospital/hospitalList.css"
	type="text/css">



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


		<!-- 검색창 -->

		<div class="row-item" style="margin-bottom: 90px;">
			<form action="${contextPath }/hospital/search" method="GET"
				id="searchForm">
				<div class="bg-image-full"
					style="background-image: url('https://cdn.pixabay.com/photo/2016/01/19/17/41/friends-1149841_960_720.jpg');">

					<div class="search">
						<select name="sk" id="searchOption">
							<option value="hospitalName">병원명</option>
							<option value="location">주소</option>
						</select> <input type="text" name="sv" class="searchBar"
							placeholder="검색어를 입력해 주세요." autocomplete="off" maxlength='15'>
						<button class="searchBar btn_class" id="searchBtn">
							<img src="${contextPath}/resources/image/icon/searchIcon.png"
								id="searchIcon" style="display: inline-block; margin: 0 auto;">
						</button>
					</div>

				</div>
			</form>
		</div>




		<!-- 지역 선택/옵션 -->

		<!--     <div class="row-item">
                <div class="locationSelect">
                    <span style="font-size:16px; font-weight:bold;">대한민국 ></span>
		                    <select class="locationNm" name="location1" id="locationName">
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
          </form>  -->


		<!-- 동물 병원 리스트 -->

		<c:choose>
			<c:when test="${empty hList }">
				<!-- hList가 비어있을 때 : 게시글 목록 조회에서 조회되지 않았을 때  -->
				<div class="row-item">
					<div style="text-align: center; font-size: 18px;">등록된 병원이
						없습니다.</div>
				</div>
			</c:when>

			<c:otherwise>
				<!-- 조회된 게시글 목록이 있을 때  -->

				<c:forEach var="hospital" items="${hList}">
					<!-- hList에서 하나씩 꺼내와 hospital에 담는다.  -->



					<div class="row-item " style="margin-bottom: 40px;">

						<div class="thumbnail">
							<div class="thumbnail_img">



								<!------------------------ 썸네일 출력  --------------------->
								<c:set var="flag" value="true" />
								<c:forEach var="thumbnail" items="${fList }">
									<c:if test="${hospital.hospNo == thumbnail.hospNo }">
										<%-- 현재 출력하려는 게시글 번호와 썸네일 목록 중 부모게시글번호가 일치하는 썸네일 정보가 있다면  --%>
										<img class="hospital_img"
											src="${contextPath }/resources/image/uploadHospitalImages/${thumbnail.fileName}">
										<c:set var="flag" value="false" />
									</c:if>
								</c:forEach>

								<c:if test="${flag == 'true'}">
									<img class="hospital_img"
										src="${contextPath }/resources/image/icon/nonImage.png">
								</c:if>

								<!-------------------------------------------------------->

							</div>
							<div class="thumbnail_info numberSelect" style="cursor: pointer;">
								<div class="hospital_info">
									<span id="hospital_name"> ${hospital.hospNm }</span>
								</div>
								<div class="hospital_info">
									<img class="icon"
										src="${contextPath}/resources/image/icon/site.png">주소 :
									${hospital.location2 }
								</div>
								<div class="hospital_info">
									<img class="icon"
										src="${contextPath}/resources/image/icon/phone.png">연락처
									: ${hospital.phone }
								</div>
								<div class="hospital_info">
									<img class="icon"
										src="${contextPath}/resources/image/icon/clock.png">영업시간
									: ${hospital.openingTime } ~ ${hospital.closingTime }
								</div>
							</div>
							<span style="visibility: hidden">${hospital.hospNo }</span>
						</div>
					</div>




				</c:forEach>
			</c:otherwise>
		</c:choose>
		<!-- 한 페이지 6개씩 보이기 -->








		<!-- 등록하기 버튼  (관리자로 로그인 했을 때만 보인다.-->
		<c:if test="${!empty loginMember && loginMember.memberAdmin == 'A' }">
			<div class="row-item">
				<button type="button" class="btn_class" id="insertHospital"
					onclick="location.href = '${contextPath}/hospital/insertForm'">등록하기</button>
			</div>
		</c:if>




		<%---------------------- Pagination ----------------------%>
		<%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
		<c:choose>
			<%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
			<c:when test="${!empty param.sk && !empty param.sv }">
				<c:url var="pageUrl" value="/hospital/search" />

				<%-- 쿼리스트링으로 사용할 내용을 변수에 저장함. --%>
				<c:set var="searchStr" value="&sk=${param.sk }&sv=${param.sv }" />
			</c:when>
			<c:otherwise>
				<c:url var="pageUrl" value="/hospital/list" />
			</c:otherwise>
		</c:choose>


		<!-- 화살표에 들어갈 주소를 변수로 생성 -->
		<%-- 검색을 안 했을 때 : /hospital/list?cp=1 
         			검색을 했을 때 : /search?cp=1&location=서울&sv=49   --%>
		<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr }" />
		<c:set var="lastPage"
			value="${pageUrl}?cp=${pInfo.maxPage}${searchStr }" />

		<%-- EL을 이용한 숫자 연산의 단점 : 연산이 자료형에 영향을 받지 않는다. --%>
		<%-- <fmt:parseNumber> : 숫자 형태를 지정하여 변수 선언 
            integerOnly="true" : 정수로만 숫자 표현 (소수점 버림)
         --%>


		<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1)/10}"
			integerOnly="true" />
		<fmt:parseNumber var="prev" value="${c1 * 10}" integerOnly="true" />
		<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr }" />
		<!-- /board/list/do?cp=10  -->

		<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9)/10}"
			integerOnly="true" />
		<fmt:parseNumber var="next" value="${c2 * 10 + 1}" integerOnly="true" />
		<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr }" />





		<!-- 페이징 -->
		<div class="paging">
			<nav aria-label="Page navigation example">
				<ul id="pagingBtn"
					class="pagination pagination-sm justify-content-center">

					<%-- 현재 페이지가 10페이지 초과인 경우 --%>
					<c:if test="${pInfo.currentPage>10}">
						<!-- 첫 페이지로 이동(<<) -->
						<li class="page-item"><a class="page-link"
							href="${firstPage}">&lt;&lt;</a></li>

						<!-- 이전 페이지로 이동(<)  -->
						<li class="page-item"><a class="page-link" href="${prevPage}">&lt;</a></li>
					</c:if>

					<!-- 페이지 목록  -->
					<c:forEach var="page" begin="${pInfo.startPage}"
						end="${pInfo.endPage}">
						<c:choose>
							<c:when test="${pInfo.currentPage == page }">
								<!-- 현재 보고 있는 페이지는 클릭이 안 되게 한다.  -->
								<li class="page-item"><a class="page-link"
									style="color: orange;">${page }</a></li>
							</c:when>

							<c:otherwise>
								<li class="page-item"><a class="page-link"
									href="${pageUrl }?cp=${page}${searchStr}">${page}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
					<c:if test="${next <= pInfo.maxPage }">
						<!-- 다음 페이지로 이동  -->
						<li class="page-item"><a class="page-link"
							href="${nextPage }">&gt;</a></li>
						<li class="page-item"><a class="page-link"
							href="${lastPage }">&gt;&gt;</a></li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>


	<script>


//동물병원 상세조회

$(".numberSelect").on("click", function(){
	var hospitalNo = $(this).siblings("span").text();
	
	var url = "${contextPath}/hospital/view?cp=${pInfo.currentPage}&hospitalNo="+ hospitalNo +"${searchStr}";
	
	location.href=url;
});


	



// 검색 내용이 있을 경우 검색창에 해당 내용을 작성해두는 기능
(function(){
	
	var searchKey = "${param.sk}";
	
	var searchValue ="${param.sv}";
	
	// select의 option을 반복 접근
	$("select[name=sk]>option").each(function(index,item){
		// index : 현재 접근중인 요소의 인덱스
		// item : 현재 접근중인 요소
		
		if($(item).val() == searchKey){
			$(item).prop("selected",true);
		}
	});
	
	// 검색어 입력창에 searchValue 값 출력
	$("input[name=sv]").val(searchValue);
	
	
})();





</script>
</body>
</html>