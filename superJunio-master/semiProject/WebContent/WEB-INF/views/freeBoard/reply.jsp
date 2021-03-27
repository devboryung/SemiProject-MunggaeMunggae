<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<style>
/*댓글*/
.replyWrite>table {
	width: 90%;
	margin-top: 100px;
}

#replyContentArea {
	width: 90%;
}

#replyContentArea > textarea {
	resize: none;
	width: 100%;
	border: 1px solid #17a2b8;;
	border-radius : 5px;
	margin : 0px;
}

#replyWrite {
	margin-right : 400px;
}

#replyBtnArea {
	width: 100px; 
	display: inline-block;

}

.rWriter {
	display: inline-block;
	margin-right: 30px;
	vertical-align: top;
}

.rDate {
	display: inline-block;
	font-size: 0.5em;
	color: gray;
}

#replyListArea {
	list-style-type: none;
}

.rContent, .replyBtnArea {
	height: 100%;
	width: 100%;
}

.replyBtnArea {
	text-align: right;
	marign:0px;]
}

.replyUpdateContent {
	resize: none;
	width: 100%;
}

.reply-row{
	border-top : 1px solid #ccc;
	padding : 15px 0;
}

.btn1{
	background-color: #8ad2d5;
	color: white;
	border: white;
	border-radius: 5px;
	width : 60px;
	height: 55px;
}

.btn1:hover{
	background-color: #17a2b8;
}

#updateReply, #deleteReply {
	background-color: #8ad2d5;
	color: white;
	border: white;
	border-radius: 5px;
	width : 60px;
	height: 55px;
}



</style>
<div id="reply-area ">
	<!-- 댓글 작성 부분 -->
	<div class="replyWrite">
		<table align="center">
			<tr>
				<td id="replyContentArea">
					<textArea rows="2" id="replyContent"></textArea>
				</td>
				<td id="replyBtnArea">
					<button class="btn1" id="addReply">
						등록
					</button>
				</td>
			</tr>
		</table>
	</div>


	<!-- 댓글 출력 부분 -->
	<div class="replyList mt-5 pt-2">
		<ul id="replyListArea">
			
			<!-- 로그인 x 또는 댓글 작성자가 아닌 회원의 화면 -->
			<li class="reply-row">
				<div>
					<p class="rWriter">작성자</p>
					<p class="rDate">작성일 : 2021년 01월 11일 10:30</p>
				</div>
				
				<p class="rContent">댓글 테스트1</p>
			</li>

			
			<!-- 로그인한 회원이 댓글 작성자인 경우 -->
			<li class="reply-row">
				<div>
					<p class="rWriter">작성자</p>
					<p class="rDate">작성일 : 2021.01.11 10:30</p>
				</div>

				<p class="rContent">댓글 테스트2</p>
				
				
				
				<div class="replyBtnArea">
					<button class="btn btn2" id="updateReply" style="background-color:orange;" onclick="showUpdateReply(2, this)">수정</button>
					<button class="btn btn3" id="deleteReply" onclick="deleteReply(2)">삭제</button>
				</div>
			</li>
	
		</ul>
	</div>

</div>


<script>
var loginMemberId = "${loginMember.memberId}";
var parentBoardNo = ${board.boardNo};

$(function(){
	selectReplyList();
});


//해당 게시글 댓글 목록 조회 함수(ajax)
function selectReplyList(){
 $.ajax({
	 url : "${contextPath}/reply/selectList.do",
	 data : {"parentBoardNo" : parentBoardNo},
	 type : "post",
	 dataType : "JSON",
	 success : function(rList){
		 console.log(rList);
		 
		 $("#replyListArea").html("");
		 
		 $.each(rList, function(index, item){
			 
			 var li =$("<li>").addClass("reply-row");
			 var rWriter = $("<p>").addClass("rWriter").text(item.memberId);
			 var rDate = $("<p>").addClass("rDate").text("작성일 : " + item.replyCreateDate);
			 
			 var div = $("<div>");
			 div.append(rWriter).append(rDate);
			 
			 var rContent = $("<p>").addClass("rContent").html(item.replyContent);
			 
			 li.append(div).append(rContent);
			 
				// 현재 댓글의 작성자와 로그인한 멤버의 아이디가 같을 때 버튼 추가
				if(item.memberId == loginMemberId){
					
				// 수정, 삭제 버튼 영역
				var replyBtnArea = $("<div>").addClass("replyBtnArea");
				
					// ** 추가되는 댓글에 onclick 이벤트를 부여하여 버튼 클릭 시 수정, 삭제를 수행할 수 있는 함수를 이벤트 핸들러로 추가함. 
					var showUpdate = $("<button>").addClass("btn btn-outline-info").text("수정").attr("onclick", "showUpdateReply("+item.replyNo+", this)");
					var deleteReply = $("<button>").addClass("btn btn-outline-info").text("삭제").attr("onclick", "deleteReply("+item.replyNo+")");
					
					replyBtnArea.append(showUpdate).append(deleteReply);
					
					li.append(replyBtnArea);
				}
			 
			 $("#replyListArea").append(li);
			 
		 });
		 
	 },
	 error : function(){
		 console.log("댓글 목록 조회 실패");
	 }
	 
 });
}

//---------------------------------------------------------------------------------------------------

//댓글 등록 (ajax)
$("#addReply").on("click", function(){
	
	// 댓글 내용을 얻어와서 변수에 저장
	var replyContent = $("#replyContent").val().trim();
	
	// 로그인이 되어 있지 않은 경우 == loginMemberId 전역변수에 저장된 값이 "" 일 경우
	if(loginMemberId == ""){
		alert("로그인 후 이용해주세요.");
		
	}else{	// 로그인이 되어있는 경우
		
		// 댓글 내용이 작성되어 있는지 확인
		if(replyContent.length == 0){
			alert("댓글 작성 후 클릭해주세요.");
			
		}else{	// 로그인도 되어있고, 댓글 내용도 작성된 경우
			
			// 회원 번호를 얻어와서 변수에 저장
			var replyWriter = "${loginMember.memberNo}";
			
			$.ajax({
				url : "${contextPath}/reply/insertReply.do",
				data : {"replyWriter" : replyWriter, "replyContent" : replyContent,
								"parentBoardNo" : parentBoardNo},
								
				type : "post",
				success : function(result){
					
					if(result > 0){		// 댓글 삽입 성공 시
						
						// 댓글 작성 내용 삭제
						$("#replyContent").val("");
						
						// 성공 메세지 출력
						swal({"icon" : "success", "title" : "댓글 등록 성공"});
						
						// 댓글 목록을 다시 조회 -> 새로 삽입한 댓글도 조회하여 화면에 출력
						selectReplyList();
					}
					
				},
				error : function(){
					console.log("댓글 등록 실패");
				}
				
			});
			
			
			
		}
		
	}
	
});


var beforeReplyRow;

//댓글 수정 폼 출력 함수
function showUpdateReply(replyNo, el){
	
	//console.log($(".replyUpdateContent").length);
	
	// 이미 열려있는 댓글 수정 창이 있을 경우 닫아주기
	if($(".replyUpdateContent").length > 0){	// 하나라도 있을경우 배열에 0번 인덱스에 존재
		$(".replyUpdateContent").eq(0).parent().html(beforeReplyRow);
		// 열려있는 수정 창 요소의 부모
	}
		
	
	// 댓글 수정화면 출력 전 요소를 저장해둠.
	beforeReplyRow = $(el).parent().parent().html();
	//console.log(beforeReplyRow);
	
	
	// 작성되어있던 내용(수정 전 댓글 내용) 
	var beforeContent = $(el).parent().prev().html();
	
	
	// 이전 댓글 내용의 크로스사이트 스크립트 처리 해제, 개행문자 변경
	// -> 자바스크립트에는 replaceAll() 메소드가 없으므로 정규 표현식을 이용하여 변경
	beforeContent = beforeContent.replace(/&amp;/g, "&");	
	beforeContent = beforeContent.replace(/&lt;/g, "<");	
	beforeContent = beforeContent.replace(/&gt;/g, ">");	
	beforeContent = beforeContent.replace(/&quot;/g, "\"");	
	
	beforeContent = beforeContent.replace(/<br>/g, "\n");	
	//console.log(beforeContent)
	
	
	// 기존 댓글 영역을 삭제하고 textarea를 추가 
	$(el).parent().prev().remove();
	var textarea = $("<textarea>").addClass("replyUpdateContent").attr("rows", "3").val(beforeContent);
	$(el).parent().before(textarea);
	
	//console.log(replyBtnArea);
	
	
	// 수정 버튼
	var updateReply = $("<button>").addClass("btn btn-outline-info mb-4").text("댓글 수정").attr("onclick", "updateReply(" + replyNo + ", this)");
	
	// 취소 버튼
	var cancelBtn = $("<button>").addClass("btn btn-outline-info mb-4").text("취소").attr("onclick", "updateCancel(this)");
	
	var replyBtnArea = $(el).parent();
	
	$(replyBtnArea).empty(); 
	$(replyBtnArea).append(updateReply); 
	$(replyBtnArea).append(cancelBtn); 
	
}



//댓글 수정 함수
function updateReply(replyNo, el){
	
	// 수정된 댓글 내용
	var replyContent = $(el).parent().prev().val();
	$.ajax({
 		url : "${contextPath}/reply/updateReply.do",
		type : "post",
		data : {"replyNo" : replyNo, "replyContent" : replyContent},
		success : function(result){
			if(result > 0){
				
				selectReplyList(parentBoardNo);
				
				swal({"icon" : "success" , "title" : "댓글 수정 성공"});
			}
			
		}, error : function(){
			console.log("댓글 수정 실패");
		}		
	});
	
}



//댓글 수정 취소 시 원래대로 돌아가는 함수
function updateCancel(el){
	//console.log(beforeReplyRow);
	$(el).parent().parent().html(beforeReplyRow);
}


//-----------------------------------------------------------------------------------------

//댓글 삭제 함수
function deleteReply(replyNo){
	if(confirm("정말로 삭제하시겠습니까?")){
		var url = "${contextPath}/reply/deleteReply.do";
		
		$.ajax({
			url : url,
			data : {"replyNo" : replyNo},
			success : function(result){
				if(result > 0){
					selectReplyList(parentBoardNo);
					
					swal({"icon" : "success" , "title" : "댓글 삭제 성공"});
				}
				
			}, error : function(){
				console.log("ajax 통신 실패");
			}
		});
	}

	
}


</script>
