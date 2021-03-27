<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>

<!-- 카카오 지도 API  -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0195b24c7dce0dc71f3dbcf7ca0a12c4"></script>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- 구글 폰트 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
	

    <style>
        *{
            font-family: 'Noto Sans KR', sans-serif;
            font-weight: 500; /* 굵기 지정(100, 300, 400, 500, 700) */
            font-size: 16px;
            color: #212529;
            box-sizing: border-box;
            margin: 0;
        }

        #main{
            width:1100px;
            display: block;
            margin: auto;
        }
        
        a{
		   color:#000000;
		   text-decoration: none;
		}
		
		a:hover{
		   color:#ffffff;
		   text-decoration: none;
		}

        .big-title{
            font-size: 30px;
            font-weight: 800;
            padding-top: 25px;
        }

        .big-banner{
            display: block;
            margin:auto; /* 가운데 정렬 */
            background-color: #F5F6F8;
            margin-top:20px;
            width:1100px;
            height:362px;
        }

        /* -------------------------------------- */

        .semi-title{
            font-size: 20px;
            font-weight: 700;
            padding-top: 30px;
            padding-bottom: 15px;
        }

        .semi-banner{
            display: flex;
            padding-bottom: 50px;
        }

        .semi-banner1{ margin-right: 25px; }
/*         .semi-banner2{ margin-left: 25px; }
        .semi-banner3{ margin-left: 25px; } */

        .semi-banner-img-area{
            display:inline-block;
            background-color: #F5F6F8;
            width:350px;
            height:250px;
            /* margin : 5px; */
        }
        
        .thumbnail_img{
      	    width:350px;
            height:250px;
        }

        .semi-text1{
            font-weight: 700;
            margin:5px;
            margin-left : 10px;
        }

        .semi-text2{
            font-weight: 400;
             margin:5px;
            margin-left : 10px;
        }



    </style>
</head>

<body>
	<jsp:include page="WEB-INF/views/common/mainHeader.jsp"></jsp:include>
  <div id="main">

      <div id="big">
          <div class="big-title">
              	뭉개뭉개와 함께 어디로 갈까요? 🐶
          </div>
          
          <!-- 메인 빅배너 ***************************************** -->
          <div class="big-banner">
              <%-- <a href='${contextPath}/travel/localView.do?no=22'>
              	<img class="big-banner" src="${contextPath}/resources/image/common/main/메인빅배너_김녕.jpg">
              </a> --%>
              
              <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
			  <ol class="carousel-indicators">
			    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
			  </ol>
			  <div class="carousel-inner">
			    <div class="carousel-item active">
				    <a href='${contextPath}/travel/localView.do?no=22'>
				      <img src="${contextPath}/resources/image/common/main/메인빅배너_김녕.jpg" class="d-block w-100" alt="...">
				    </a>
			    </div>
			    <div class="carousel-item">
			      <a href='${contextPath}/travel/localView.do?no=41'>
			      	<img src="${contextPath}/resources/image/common/main/메인빅배너_인천.jpg" class="d-block w-100" alt="...">
			      </a>
			    </div>
			    <div class="carousel-item">
			      <a href='${contextPath}/travel/localView.do?no=42'>
			      	<img src="${contextPath}/resources/image/common/main/메인빅배너_부산.jpg" class="d-block w-100" alt="...">
			      </a>
			    </div>
			    <div class="carousel-item">
			    	<a href='${contextPath}/travel/localView.do?no=43'>
			      	<img src="${contextPath}/resources/image/common/main/메인빅배너_대구.jpg" class="d-block w-100" alt="...">
			      </a>
			    </div>
			  </div>
			  
			  <!-- 좌우화살표 -->
			  <!-- <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a> -->
			  
			</div>
              
          </div>
      </div>

      <div id="sub">      
          <div class="semi-title">인기 숙소</div>
          <div>
              <div class="semi-banner">
              	<c:choose>
              		<c:when test="${!empty roomList }">
              			<c:forEach var="room" items="${roomList }">
              			
	              			<div class="semi-banner1 numberSelect" style="cursor: pointer;">
	              				
	              				<c:forEach var="thumbnail" items="${fList }">
	              					<c:if test="${room.roomNo == thumbnail.roomNo }">
			                     		 <div class="semi-banner-img-area">
			                     		 	<img class="thumbnail_img" src="${contextPath}/resources/image/uploadRoomImages/${thumbnail.fileName}">
			                     		 </div>
	              					</c:if>
	              				</c:forEach>
			                      <div class="semi-text1 " >${room.roomName }</div>
			                      <div class="semi-text2 " >${room.location2 }</div>
			                      <span style="visibility:hidden">${room.roomNo }</span>
			                  </div>	
              			</c:forEach>
              		</c:when>
              	</c:choose>
              </div>
    	  </div>
      </div>

  	<jsp:include page="WEB-INF/views/common/footer.jsp"></jsp:include>
  </div>
  
<script>
//숙소 상세조회
$(".numberSelect").on("click", function(){
	var roomNo = $(this).children("span").text();
	
	var url = "${contextPath}/room/view?cp=1&roomNo="+ roomNo;
	
	location.href=url;
});
</script>

</body>

</html>