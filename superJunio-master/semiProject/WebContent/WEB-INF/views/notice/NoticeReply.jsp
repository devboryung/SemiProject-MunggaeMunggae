<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.replyWrite {
width:  870px;
margin-left:15px;

}
#replyWrite{
width:30px;
}

#replyContentArea {
width: 60%;
	
}

#replyContentArea > textarea {
resize: none;
width: 80%;

}
#replyBtnArea {
width: 50px;
	
}

.rWriter {
display: inline-block;
/* margin-left: 20px ;
 */vertical-align: top;
font-size: medium;
}
.rContent{
font-size: small;
margin-top :10px;
/* margin-left: 20px;
 */width :100px;


}

.rDate {
display: inline-block;
font-size: 0.7em;
color: gray;
margin-left:20px;
 }

 #replyListArea {
float: left;
width: 900px;
height:100%;
}

.rContent, .replyBtnArea {
height: 100%;
width: 100%;
}

.replyBtnArea {
text-align:right;
/* width:  100%;
 */
}

.replyUpdateContent {
resize: none;
width: 100%;
}

.reply-row{
/* border-bottom : 1px solid #ccc;
 */}
.replyBtn{
cursor: pointer;
font-size:0.7em;
color:gray;
margin-right:5px;

}
.replyList{
}
.con{

 margin-left:30px;
}

#addReply{
 background-color:#8bd2d6; 
 color:white;
}
.addReply{
 background-color:#8bd2d6; 
 color:white;
}



</style>

<div class="replyWrite" style="margin-top: px;">
<table>
    <tr>
      <c:choose>
    	<c:when test="${!empty loginMember}">
	       	 <td width="11%;">${loginMember.memberNickName}</td>
    	</c:when>
    	<c:otherwise>
    	    	<td width="12%;"style="font-size:0.9em;color:gray;">로그인 이후<br> 이용해주세요.</td>
    	</c:otherwise>
      </c:choose>
         <td id="replyContentArea" style="text-align:center;">
            <textArea rows="2" id="replyContent" style="width:700px; border: 3px solid #8bd2d6;
		border-radius: 5px;"></textArea>
        </td>
        <td id="replyBtnArea"  style="text-align:right; align:center;">
                <button class="btn" id="addReply">
                                          등록
                </button>
            </td>
    </table>
</div>



      <div id="reply-area">
            <div class="replyList"style="border-top:1px soild black;">
                 <div id="replyListArea">
                    <div class="reply-row">
                        <div>
                            <div class="rWriter con"><strong></strong></div>
                            <div class="rDate con"></div>
                        </div>
                        <div class="rContent con" id="content">댓글이 없습니다...</div>
                        
                        <div class="replyBtnArea">
                            <a class="replyBtn"  id="updateReply" onclick="showUpdateReply(2, this)"></a>
                            <a class="replyBtn"  id="deleteReply" onclick="deleteReply(2)"></a>
                        </div>
                    </div>
                 </div>
           	  </div>
        </div>
    
<script>

var loginMemberNo = "${loginMember.memberNo}";
var parentNoticeNo = ${notice.noticeNo};

$(function(){
	selectReplyNoticeList();
});  

  
  function selectReplyNoticeList(){
	  
	  $.ajax({
		
		  url :"${contextPath}/centerReply/selectNoticeList.do",
		  data : {"parentNoticeNo" : parentNoticeNo },
		  type : "post",
		  dataType : "JSON",
		  success : function(nrList) {
			  
/* 
	 	   if(nrList ==""){
 	 		   var div= $("<div>").addClass("rContent");
		  
		   }else{ */

 		  $("#replyListArea").html("");
		  $.each(nrList,function(index,item){
 			
 			  var div = $("<div>").addClass("reply-row");
			  var rWriter = $("<div>").addClass("rWriter").text(item.memNickName);
			  var rDate = $("<div>").addClass("rDate").text("작성일: " + item.noticeRepCreateDt);
			  
			  var div = $("<div>").css("padding","10px").css("border-top", "1px solid #ccc").css("width","100%").css("margin-top","10px");
			  div.append(rWriter).append(rDate);
			  
			  var rContent = $("<div>").addClass("rContent").css("word-break","break-all").html(item.noticeRepContent);
			  
			  div.append(div).append(rContent); 
			  
			  
			  
 			  if(item.noticeRepWriterNo == loginMemberNo){
				  
				  
			   var replyBtnArea = $("<div>").addClass("replyBtnArea");
 
				  
			   var showUpdate = $("<a>").addClass("replyBtn").text("수정").css("color","gray").attr("onclick", "showUpdateReply("+item.noticeRepNo+", this)");
		       var deleteReply = $("<a>").addClass("replyBtn").text("삭제").css("color","gray").attr("onclick", "deleteReply("+item.noticeRepNo+")");
		            
		       
		       replyBtnArea.append(showUpdate).append(deleteReply);
				
		       div.append(replyBtnArea);
				  
			  } 
			  
			$("#replyListArea").append(div);

		  });
/* 	  }
 */		
	  },
	  error : function(){
		  console.log("댓글 목록 조회 실패");
	  }
  
  
  	});
		  
  }
  
  
  //-----------------------------------------------------------------------------
  // 답변 등록 
  
	  
	  $("#addReply").on("click", function(){
		
		var replyContent = $("#replyContent").val().trim();
		
		if(loginMemberNo == ""){
		    swal({"icon" : "error" ,"title" :"로그인 이후 이용해주세요."});
			
		}else{ 
	
			  if(replyContent.length == 0){
				  
				  swal({"icon" : "error" ,"title" :"댓글 작성 후 이용해주세요."});
			 
			  
			  }else{ 
				  		
				  var replyWriter = "${loginMember.memberNo}";
				  
				  $.ajax({
					  url : "${contextPath}/centerReply/insertNoticeReply.do",
					  data : {"replyWriter" : replyWriter,
						         "replyContent" : replyContent,
						         "parentNoticeNo" : parentNoticeNo},
					  
					  type: "post",
					  success :function(result){
						  
						  
						  if(result > 0){ // 댓글 삽입 성공 시
							  
							  // 댓글 작성 내용 삭제
							  $("#replyContent").val("");
						   
						    // 성공 메세지 출력
						    swal({"icon" : "success" ,"title" :"댓글 등록 성공"});
							  
						    // 댓글 목록을 다시 조회  
						    selectReplyNoticeList();
							  
						  }
					
					  },error : function(){
						  
						  console.log("댓글 등록 실패")
					  }
					  
					  
				  });
			
			 }
			
		}
		
		
	});		  	
	
	  

  
  // -------------------------------------------------------
  
  var beforeReplyRow;  
  
  
 //답변 수정 폼 출력 함수

  function showUpdateReply(replyNo, el){
	 
	  // 이미 열려있는 답변 수정 창이 있을 경우 닫아주기
	  if($(".replyUpdateContent").length > 0){
		    $(".replyUpdateContent").eq(0).parent().html(beforeReplyRow);
		  }
	  
	  // 댓글 답변화면 출력 전 요소를 저장해둠.
	  beforeReplyRow = $(el).parent().parent().html();
					
	 
	  // 작성되어있던 내용(답변 전 댓글 내용) 
	  var beforeContent = $(el).parent().prev().html();
	  
	  // 이전 댓글 내용의 크로스사이트 스크립트 처리 해제, 개행문자 변경
	  // -> 자바스크립트에는 replaceAll() 메소드가 없으므로 정규 표현식을 이용하여 변경
	  beforeContent = beforeContent.replace(/&amp;/g, "&"); 
	  beforeContent = beforeContent.replace(/&lt;/g, "<");  
	  beforeContent = beforeContent.replace(/&gt;/g, ">");  
	  beforeContent = beforeContent.replace(/&quot;/g, "\""); 
	  beforeContent = beforeContent.replace(/<br>/g, "\n"); 

	  // 기존의 답변 영역을 삭제 하고 
	  $(el).parent().prev().remove();
	  var textarea = $("<textarea>").addClass("replyUpdateContent").attr("rows", "3").val(beforeContent);
	  $(el).parent().before(textarea);
	  
	  
	  var updateReply = $("<button>").addClass("btn btn-sm ml-1 mb-4 addReply").text("댓글 수정").attr("onclick", "updateReply(" + replyNo + ", this)");

	  var cancelBtn = $("<button>").addClass("btn btn-sm ml-1 mb-4 addReply").text("취소").attr("onclick", "updateCancel(this)");

	  var replyBtnArea = $(el).parent();

	  $(replyBtnArea).empty(); 
	  $(replyBtnArea).append(updateReply); 
	  $(replyBtnArea).append(cancelBtn); 

	  
 }

  // -----------------------------------------
  
  // 답변 수정 함수
 function updateReply(replyNo, el){

	 // 수정된 답변 내용
	var replyContent =$(el).parent().prev().val();

	$.ajax({
	    url : "${contextPath}/centerReply/updateNoticeReply.do",
	    type : "post",
	    data : {"replyNo" : replyNo, "replyContent" : replyContent},
	    success : function(result){

	    	if(result > 0){
		  		 
	    		selectReplyNoticeList(parentNoticeNo);
			 
		     swal({"icon" : "success" , "title" : "댓글 수정 성공"});

	    
	    	}
	    },error : function(){
		     swal({"icon" : "error" , "title" : "댓글 수정 실패"});
	    }
		
	    
	    
	});
	
}
  
  
  
  // 댓글 답변 취소 시 원래대로 돌아가는 함수
  function updateCancel(el){
	$(el).parent().parent().html(beforeReplyRow);	
}
  
  // 답변 삭제 
  function deleteReply(replyNo){
		
	  
		swal({
			  title: "정말 삭제 하시겠습니까?",
			  text: "삭제가 되면 데이터를 되돌릴 수 없습니다.",
			  icon: "warning",
			  buttons: true,
			  dangerMode: true,
			})
			.then((willDelete) => {
			  if (willDelete) {
			
					var url = "${contextPath}/centerReply/deleteNoticeReply.do";
				    $.ajax({
					      url : url,
					      data : {"replyNo" : replyNo ,
					      			},
					      success : function(result){
					        if(result > 0){
					        	selectReplyNoticeList(parentNoticeNo);
					          
					          swal({"icon" : "success" , "title" : "댓글 삭제 성공"});
					        }
					        
					      }, error : function(){
					        console.log("ajax 통신 실패");
					      }
					    });
					  

			  } else {
			    swal("삭제를 취소하셨습니다.");
			  }
			});
	  
  
  }
  
 





  
  
</script>
