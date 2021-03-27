<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>html문서 제목</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    
    <!-- 부트스트랩 사용을 위한 라이브러리 추가 -->
    <!-- jquery가 항상 먼저여야된다! -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>


</head>
<style>
 
/*     #side{
        float: left;
        width: 200px;
        height: 800px;
} */

#wrapper{
width: 1100px;
height: 800px;
margin: 0px auto;
}   
#wrapper2{
float: left;
width: 900px;
margin-top: 20px;
} 


/* #uls{
    margin-top: 30px;
}  */

/*    #uls > li{
        line-height: 80px;
        list-style: none;
    } */
/*     div{
        border: 1px solid;
    } */
#img > div{
display: inline-block;
border: 1px solid gray;
border-radius: 5px;
	    
 }
 #img{
 width: 895px;
 }
 
a{
color:black;
}
	
	
.aside {
width: 200px;
height: 100%;
float: left;
border-right: 1px solid #e5e5e5;
}

.aside>ul {
list-style-type: none;
/* 불렛 없음 */
padding: 0;
}

/* 메뉴 위아래 간격 */
.aside>ul>li {
	padding: 10px 0px 10px 0px;
}

.aside>ul>li>a {
	text-decoration: none;
	/* 불렛 없음 */
	font-weight: 700;
	color: black;

	/* border: 1px solid red; */
}

.aside>ul>li>a:hover {
	color: orange;
}


</style>
<body>
<jsp:include page="/WEB-INF/views/common/otherHeader.jsp"></jsp:include>
 <form  action ="${contextPath}/faq/faqInsert.do" method="post"
      enctype="multipart/form-data" role="form" onsubmit="return faqValidate();">
<div id="wrapper">
    <div class="aside">
        <ul id="">
			<li><a href="${contextPath}/notice/notice.do">공지사항</a></li>
			<li><a href="${contextPath}/faq/faq.do">자주묻는질문</a></li>
			<li><a href="${contextPath}/qna/qna.do">Q&A</a></li>
        </ul>
    </div>
    <div id="wrapper2">
     
		 <div style="margin-left:10px;">
            <h3>자주묻는질문 등록</h3>
        </div>
            <hr>
        <div>
            <input type="text" name="faqTitle" id="faqTitle" style="width:80%; border:none;margin-left:10px;" placeholder="제목을입력하세요.">
            <hr>
        </div>
        <div>
            <p style="color: gray;margin-left:10px;">이미지 업로드</p>
        </div>
        <div id="img" style="margin-left:10px;" >
            <div class="boardImg">
                <img id="contentImg0"style="width: 100px; height:100px;">
            </div>
             <div class="boardImg">
                <img id="contentImg1" style="width: 100px; height:100px;">
            </div >
            <div class="boardImg">
                <img   id="contentImg2" style="width: 100px; height:100px;">
            </div>
            <div class="boardImg">
                <img  id="contentImg3"  style="width: 100px; height:100px;">
            </div>
            <div class="boardImg">
                <img   id="contentImg4" style="width: 100px; height:100px;">
            </div>
            <div class="boardImg">
                <img   id="contentImg5" style="width: 100px; height:100px;">
            </div>
            <div class="boardImg">
                <img  id="contentImg6" style="width: 100px; height:100px;">
            </div>
            <div class="boardImg">
                <img id="contentImg7" style="width: 100px; height:100px;">
            </div>
        </div> 
	     <div id="faqFileArea">
			<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)"> 
			<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
			<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)"> 
			<input type="file" id="img3" name="img3" onchange="LoadImg(this,3)">
			<input type="file" id="img4" name="img4" onchange="LoadImg(this,4)"> 
			<input type="file" id="img5" name="img5" onchange="LoadImg(this,5)"> 
			<input type="file" id="img6" name="img6" onchange="LoadImg(this,6)">
			<input type="file" id="img7" name="img7" onchange="LoadImg(this,7)">
		</div>
        <hr>
          <p style="color:gray; margin-left:10px;">내용</p>
          <textarea rows="13" id="faqContent"name="faqContent" style="resize: none; width: 890px; border: 3px solid #8bd2d6;
   								 border-radius: 5px; margin-left:10px;"></textarea> 

        <div class="float-right">
            <button type="submit" class="btn" style="background-color:#8bd2d6; color:white;">등록</button>
           
<%--                <c:choose>	
				  <c:when test="${!empty param.sk && !empty param.sv}">
				   
				    <c:url var="goToList" value="../search/faq.do">
		                <c:param name="cp">${param.cp}</c:param>
		                <c:param name="sk">${param.sk}</c:param>
		                <c:param name="sv">${param.sv}</c:param>
				    </c:url>
				  
				  </c:when>
				  <c:otherwise>
						<c:url var="goToList" value="faq.do">
		             	  <c:param name="cp">${param.cp}</c:param>					 
						</c:url>
				  </c:otherwise>
			</c:choose>  
            --%>
           
            <button type="button"  class="btn" style="background-color:#8bd2d6; color:white;"
            <%-- onclick="location.href='${goToList}'" --%> onclick="history.back()">취소</button>
        </div>

    	</div> 
</div>
  </form>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
<script>


// 유효성 검사 
function faqValidate() {
	if ($("#faqTitle").val().trim().length == 0) {
		alert("제목을 입력해 주세요.");
		$("#faqTitle").focus();
		return false;
	}

	if ($("#faqContent").val().trim().length == 0) {
		alert("내용을 입력해 주세요.");
		$("#faqContent").focus();
		return false;
	}
}



$(function(){
	
	
	  $("#faqFileArea").hide(); // #fileArea 요소를 숨김,
	  
	   $(".boardImg").on("click",function(){ // 이미지 영역이 클릭 되었을 때
		   
		  // 클릭한 이미지 영역 인덱스 얻어오기
		  var index = $(".boardImg").index(this);
		          // -> 클릭된 요소가 .boardImg 중 몇번째 인덱스인지 반환
		   
		   console.log(index);
		          
		   // 클릭된 영역 인덱스에 맞는 input file 태그 클릭
		   $("#img" + index).click();
	    });
	  
	  
	 });
// 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
function LoadImg(value, num) {
	  // value.files : 파일이  업로드되어 있으면 true
	  // && value.files[0] : 여러 파일 중  첫 번째 파일이 업로드 되어 있으면 true
	 if(value.files && value.files[0]){  // 해당 요소에 업로드 된 파일이 있을 경우
		  
		  
	 var reader = new FileReader();
		// 자바스크립트 FileReader
  // 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는 File 
  // 혹은 Blob객체를 이용해 파일의 내용을 읽고 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체
	  
  reader.readAsDataURL(value.files[0]);
  // FileReader.readAsDataURL()
// 지정된의 내용을 읽기 시작합니다. Blob완료되면 result속성 data:에 파일 데이터를 나타내는 
// URL이 포함 됩니다.

		reader.onload = function(e){
			     	  
		// FileReader.onload
	  // load 이벤트의 핸들러. 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생합니다.
			  
	  // 읽어들인 내용(이미지 파일)을 화면에 출력
	  
	  $(".boardImg").eq(num).children("img").attr("src",e.target.result);
		// e.target.result : 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용


}

	 }
	  

}



</script>
</body>
</html>
