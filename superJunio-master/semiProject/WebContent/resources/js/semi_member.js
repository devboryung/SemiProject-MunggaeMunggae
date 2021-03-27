

// 입력값들이 유효성 검사가 진행되었는지 확인하기 위한  객체 생성
var validateCheck = {
    "id" : false,
    "pwd1" : false,
    "pwd2" : false,
    "phone2" : false,
    "email" : false,
    "nickName" : false
}

// 아이디 유효성 검사
// 첫 글자는 영어 소문자, 나머지 글자는 영어 대,소문자 + 숫자, 총 6~12글자
$("#userId").on("input",function(){
    var regExp = /^[a-z][a-zA-Z\d]{5,11}$/;

    var value = $("#userId").val();
    if(!regExp.test(value)){
        $("#checkId").text("아이디 형식이 유효하지 않습니다.")
                        .css("color", "red");
        validateCheck.id = false;   

    }else{
        // $("#checkId").text("유효한 아이디 형식입니다.")
        //                 .css("color", "green");


        // ajax를 이용한 실시간 아이디 중복 검사
        $.ajax({
            url : "idDupCheck.do",
            data : {"userId" : value},
            type : "POST",
            success : function(result){
            // $("#checkId").text("유효한 아이디 형식입니다.").css("color", "green");
            
            if(result == 0){    // 중복되지 않은 경우
                $("#checkId").text("사용 가능한 아이디 입니다.")
                .css("color", "green");
                validateCheck.id = true;
            }else{
                $("#checkId").text("이미 사용중인 아이디입니다.")
                .css("color", "red");
                validateCheck.id = false;
            }

            },
            error : function(){
                console.log("아이디 중복검사 실패")
            }
        });



    }

    // 아이디가 입력되는 경우 hidden 태그의 값을 false로 변경
    // $("#idDup").val("false");
});





$("#email1").on("input", function(){
    var e1 = $("#email1").val();
    var regExp = /^[a-zA-Z\d_]{4,10}$/;
        if(!regExp.test(e1)){
            $("#checkEmail").text("이메일 형식이 유효하지 않습니다.")
            .css("color", "red");
            validateCheck.email = false;
        }else{
            $("#checkEmail").text("유효한 이메일 형식입니다.")
            .css("color", "green");
             validateCheck.email = true;
        }
    
    
});

// 비밀번호 유효성 검사
// 영어 대,소문자 + 숫자, 총 6~12글자
// + 비밀번호, 비밀번호 확인의 일치 여부
// + 비밀번호를 입력하지 않거나 유효하지 않은 상태로
//   비밀번호 확인을 작성하는 경우

$("#pwd1, #pwd2").on("input", function () {

    // 비밀번호 유효성 검사 
    var regExp = /^[A-Za-z\d]{6,12}$/;
    var v1 = $("#pwd1").val();
    var v2 = $("#pwd2").val();

    if (!regExp.test(v1)) {
        $("#checkPwd1").text("비밀번호 형식이 유효하지 않습니다.").css("color", "red");
        validateCheck.pwd1 = false;
    } else {
        $("#checkPwd1").text("유효한 비밀번호 형식입니다.").css("color", "green");
        validateCheck.pwd1 = true;
    }
    // 비밀번호가 유효하지 않은 상태에서 비밀번호 확인 작성 시
    if (!validateCheck.pwd1 && v2.length > 0) {
        alert("유효한 비밀번호를 먼저 작성해주세요.");
        $("#pwd2").val(""); // 비밀번호 확인에 입력한 값 삭제
        $("#pwd1").focus();
    } else {
        // 비밀번호, 비밀번호 확인의 일치 여부
        if (v1.length == 0 || v2.length == 0) {
            $("#checkPwd2").html("&nbsp;");
        
        } else if(v1 == v2){
            $("#checkPwd2").text("비밀번호 일치").css("color", "green");
            validateCheck.pwd2 = true;
       

        } else {
            $("#checkPwd2").text("비밀번호 불일치").css("color", "red");
            validateCheck.pwd2 = false;
        }
    }
});


// 닉네임 유효성 검사
$("#userName").on("input", function(){
    var regExp = /^[a-zA-Z가-힣\d]{3,8}$/;

    var v1 = $("#userName").val();

    if(!regExp.test(v1)){
        $("#checkUserName").text("닉네임 형식이 유효하지 않습니다.").css("color", "red");
        validateCheck.nickName = false;
    } else {
        $("#checkUserName").text("유효한 닉네임 형식입니다.").css("color", "green");
        validateCheck.nickName = true;
    }
    
});




// 전화번호 유효성 검사
$(".phone").on("input", function(){

    // 전화번호 input 태그에 4글자 초과 입력하지 못하게 하는 이벤트
	if ($(this).val().length > 4) {
		$(this).val( $(this).val().slice(0, 4));
    }
    
    var regExp1 = /^\d{3,4}$/;
    var regExp2 = /^\d{4}$/;

    var v1 = $("#phone2").val();
    var v2 = $("#phone3").val();

    if(!regExp1.test(v1) || !regExp2.test(v2)){
        $("#checkPhone").text("전화번호가 유효하지 않습니다.")
            .css("color", "red");
        validateCheck.phone2 = false;
    }else{
        $("#checkPhone").text("유효한 형식의 전화번호 입니다.")
            .css("color", "green");
        validateCheck.phone2 = true;
    }

});


function memberJoinvalidate(){

    // 아이디 중복검사 여부 확인
/*     if($("#idDup").val() != "true"){
        swal("아이디 중복 검사를 진행해 주세요.");
        $("#idDupCheck").focus();

        return false;
    }
 */

    // 유효성 검사 여부 확인
    for(var key in validateCheck){
        if(!validateCheck[key]){
            var msg;
            switch(key){
                case "id" : msg="아이디가"; break;
                case "pwd1" : 
                case "pwd2" : msg="비밀번호가"; break;
                case "nickName" : msg="이름이"; break;
                case "phone2" : msg="전화번호가"; break;
                case "email" : msg="이메일이"; break;
            }

            alert(msg + " 유효하지 않습니다.");

            $("#"+key).focus();

            return false;
        }
    }
    

}


// 내 정보 수정 ------------------------------------------------------------------
// 유효성 검사
function memberUpdateValidate(){
	
	var updateCheck = {"nickName" : false,
						"email1" : false,
						"phone2" : false}
	
	var regExp1 = /^[a-zA-Z가-힣\d]{3,8}$/;
	var regExp2 = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/;
	var regExp3 = /^\d{3,4}$/; // 숫자 3~4 글자
   	var regExp4 = /^\d{4,4}$/; // 숫자 4 글자
	
	// 닉네임
	if(!regExp3.test( $("#userName").val() )){
        updateCheck.userName = false;
    }else{
        updateCheck.userName = true;
    }
	
	// 이메일
	if(!regExp3.test( $("#email").val() )){
        updateCheck.email = false;
    }else{
        updateCheck.email = true;
    }

	// 전화번호
	var p2 = $("#phone2").val();
    var p3 = $("#phone3").val();
    if(!regExp1.test(p2) || !regExp2.test(p3)){
        updateCheck.phone2 = false;
    }else{
        updateCheck.phone2 = true;
    }
}


//------------- 비밀번호 변경 유효성 검사---------------
function memberChangevalidate(){
	var regExp = /^[a-zA-Z\d]{6,12}$/;

    if( !regExp.test($("#newPwd2").val()) ){
				swalIcon("warning")
        swalText("비밀번호 형식이 유효하지 않습니다.");
        $("#newPwd2").focus();

        return false;
    }

    // 새로운 비밀번호와 확인이 일치하지 않을 때 
    if( $("#newPwd2").val() != $("#newPwd3").val() ){
        swal("새로운 비밀번호가 일치하지 않습니다.");

        $("#newPwd2").focus();
        $("#newPwd2").val("");
        $("#newPwd3").val("");

        return false;
    }
}

// 회원 탈퇴 약관 동의 체크 확인 ----------------------------------------------------------------------
function withdrawalValidate(){

    // #agree체크박스가 체크되어 있지 않다면
    if( !$("#agree").prop("checked") ){
        swal("약관에 동의해 주세요.");
        return false;
    }else{
        return confirm("정말로 탈퇴하시겠습니까?");
    }

}





