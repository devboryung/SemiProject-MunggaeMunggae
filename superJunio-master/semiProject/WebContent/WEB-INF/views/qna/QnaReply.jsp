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
	width: 900px;

}
#replyWrite{
	width:30px;
	font-size: medium;
	
}

#replyContentArea {
	width: 80%;
	
}

#replyContentArea > textarea {
	resize: none;
	width: 80%;

}
#replyBtnArea {
	width: 100px;
	
}

.rWriter {
	display: inline-block;
	margin-right: 30px;
	vertical-align: top;
    font-size: medium;
}
.rContent{
    font-size: small;

}

.rDate {
	display: inline-block;
	font-size: 0.7em;
	color: gray;
}

#replyListArea {
    float: left;
    width: 900px;
}

.rContent, .replyBtnArea {
	height: 100%;
	width: 100%;
}

.replyBtnArea {
	text-align:right;
}

.replyUpdateContent {
	resize: none;
	width: 100%;
}

.reply-row{
	padding : 15px 0;
 
}
.replyBtn{
cursor: pointer;
font-size:0.7em;
color:gray;
margin-right:5px;

}
.replyList{

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

      <div id="reply-area" style="margin-left:18px;">
        
            <div class="replyList">
                
                 <div id="replyListArea">
                    <div class="reply-row">
                        <div>
                            <p class="rWriter"><strong>관리자</strong></p>
                            <p class="rDate"></p>
                        </div>
        
                        <p class="rContent">문의내용을 처리중입니다.. 조금만 기다려 주세요</p>
                     
                        <div class="replyBtnArea">
                            <a class="replyBtn"  id="updateReply" onclick="showUpdateReply(2, this)">수정</a>
                            <a class="replyBtn"  id="deleteReply" onclick="deleteReply(2)">삭제</a>
                        </div>
                    </div>
                 </div>
	       	   <c:if test="${loginMember.memberAdmin == 'A'}">
	     	     <div class="replyWrite" style="margin-top: 20px;">
	                    <table align="center">
	                        <tr>
	                            <td id="replyContentArea">
	                                <textArea rows="2" id="replyContent" style="width: 830px; border: 3px solid #8bd2d6;
	   								 border-radius: 5px;"></textArea>
	                            </td>
	                            <td id="replyBtnArea"  style="text-align:right; align:center;">
	                                <button class="btn addReply" id="addReply">
	                                                                     등록
	                                </button>
	                            </td>
	                        </tr>
	                    </table>
	                </div>
 	               </c:if>
           </div>
        </div>

<script>
 
var loginMemberId = "${loginMember.memberNickName}";
var parentQnaNo = ${qna.qnaNo};
var check;
//console.log(check);

$(function(){
	selectReplyQnaList();
});  

  
  function selectReplyQnaList(){
	  
	  $.ajax({
		
		  url :"${contextPath}/centerReply/selectQnaList.do",
		  data : {"parentQnaNo" : parentQnaNo },
		  type : "post",
		  dataType : "JSON",
		  success : function(qrList) {
			  
			 console.log(qrList);
/* 			if(qrList == ""){
				
	 			var div = $("<div>").addClass("rContent");

				
				
			}else{ */

 		  $("#replyListArea").html("");
 
		 
		  $.each(qrList,function(index,item){
 			
 			  var div = $("<div>").addClass("reply-row");
			  var rWriter = $("<p>").addClass("rWriter").text(item.memNickName);
			  var rDate = $("<p>").addClass("rDate").text("작성일: " + item.qnaReplyCreateDt);
			  
			  var div = $("<div>");
			  div.append(rWriter).append(rDate);
			  
			  var rContent = $("<p>").addClass("rContent").html(item.qnaReplyContent);
			  
			  div.append(div).append(rContent); 
			  
			  
 			  if(item.memNickName == loginMemberId){
				  
				  
			   var replyBtnArea = $("<div>").addClass("replyBtnArea");
 
				  
			   var showUpdate = $("<a>").addClass("replyBtn").text("수정").css("color","gray").attr("onclick", "showUpdateReply("+item.qnaReplyNo+", this)");
		       var deleteReply = $("<a>").addClass("replyBtn").text("삭제").css("color","gray").attr("onclick", "deleteReply("+item.qnaReplyNo+")");
		            
		       
		       replyBtnArea.append(showUpdate).append(deleteReply);
				
		       div.append(replyBtnArea);
				  
			  } 
			  
			  
			$("#replyListArea").append(div);

			  
		  });
/* 		}
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
	  
		 $.ajax({
			  url : "${contextPath}/centerReply/replyCheck.do",
			  data : { "parentQnaNo" : parentQnaNo,
				 
				       },
			  
			  type: "post",
			  success :function(result){
				  
				  	check = result;
			
				  	console.log(check);
				  
				  	if(check == 'Y'){
						  
				  		swal({"icon" : "error" , "title" : "이미 답변한 문의입니다. 수정하거나 삭제하세요."});
				  			  
				  		
				  		
				  	}else{
				  		  
				  		  
				  			// 답변 내용을 얻어와서 변수에 저장
				  		var replyContent = $("#replyContent").val().trim();
				  		
				  			
				  			
				  			
				  	    if(replyContent.length == 0){
				  	    	
					  		swal({"icon" : "error" , "title" : "답변을 작성 후 등록해주세요."});
				  	    
				  	    
				  	    }else{
				  	    	
				  	    	
				  		 var replyWriter = "${loginMember.memberNo}";
				  		
				  		 $.ajax({
				  			  url : "${contextPath}/centerReply/insertQnaReply.do",
				  			  data : {"replyWriter" : replyWriter,
				  				      "replyContent" : replyContent,
				  				      "parentQnaNo" : parentQnaNo,
				  				       "replyResponse": 'Y',
				  				       },
				  			  
				  			  type: "post",
				  			  success :function(result){
				  				  
				  				  
				  				  if(result > 0){ // 답변 삽입 성공 시
				  					  
				  					  // 답변 작성 내용 삭제
				  					  $("#replyContent").val("");
				  				   
				  				    // 성공 메세지 출력
				  				    swal({"icon" : "success" ,"title" :"답변완료"});
				  					  
				  				    // 댓글 목록을 다시 조회  -> 새로 삽입한 댓글도 조회하여 화면에 출력
				  				    selectReplyQnaList();
				  					  
				  				  }
				  				  
				  			
				  			  },error : function(){
				  				  
				  				  console.log("댓글 등록 실패")
				  			  }
				  			  
				  			  
				  		  });
				  	    }

				  } 	  	
				  	
			
			  },error : function(){
				  
				  console.log("답변 확인 과정 실패")
			  }
			  
			  
		  });
	  
	  

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
	  
	  
	  var updateReply = $("<button>").addClass("btn btn-sm ml-1 mb-4 addReply").text("답변 수정").attr("onclick", "updateReply(" + replyNo + ", this)");

	  var cancelBtn = $("<button>").addClass("btn  btn-sm ml-1 mb-4 addReply").text("취소").attr("onclick", "updateCancel(this)");

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
	    url : "${contextPath}/centerReply/updateQnaReply.do",
	    type : "post",
	    data : {"replyNo" : replyNo, "replyContent" : replyContent},
	    success : function(result){

	    	if(result > 0){
	    
	    		selectReplyQnaList(parentQnaNo);
			 
		     swal({"icon" : "success" , "title" : "댓글 수정 성공"});

	    
	    	}
	    },error : function(){
	    	console.log("댓글 수정 실패");
	    }
		
	    
	    
	});
	
}
  
  
  
  // 댓글 답변 취소 시 원래대로 돌아가는 함수
  function updateCancel(el){
	$(el).parent().parent().html(beforeReplyRow);	
}
  
  // 답변 삭제 함수
  function deleteReply(replyNo){
		
	  
		swal({
			  title: "수정하시겠습니까?",
			  text: "이미 답변 처리가 완료된 문의글입니다.",
			  icon: "warning",
			  buttons: true,
			  dangerMode: true,
			})
			.then((willDelete) => {
			  if (willDelete) {
			
				 var url = "${contextPath}/centerReply/deleteQnaReply.do";
			    
				 $.ajax({
			      url : url,
			      data : {"replyNo" : replyNo ,
			    	  	   "replyResponse": "N" ,
			    	  	    "parentQnaNo" : parentQnaNo,
			      			},
			      success : function(result){
			        
			    	  if(result > 0){
			        	selectReplyQnaList(parentQnaNo);
			          
			          swal({"icon" : "success" , "title" : "댓글 삭제 성공"});
			        }
			        
			      }, error : function(){
			        console.log("ajax 통신 실패");
			      }
			    });
		
				
			  } else {
			    swal("수정을 취소하셨습니다.");
			  }
			
	  });
  } 
	  

  
  
</script>
