$(function(){
// 이메일 인증번호
$("#checkEmail").click(function() {
   $.ajax({
      type : "POST",
      url : "login/mailConfirm",
      data : {
         "email" : $("#memail").val(),
      },
      success : function(data){ //code
         alert("해당 이메일로 인증번호 발송이 완료되었습니다.")
         console.log("data : " + data);
         chkEmailConfirm(data, memailconfirm, memailconfirmTxt);
      }
   })
})
});

// 이메일 인증번호 체크 함수
function chkEmailConfirm(data, memailconfirm, memailconfirmTxt){
	$("#memailconfirm").on("keyup", function(){
		if (data != $("#memailconfirm").val()) { //
			emconfirmchk = false;
			$("#memailconfirmTxt").html("<span id='emconfirmchk'>인증번호가 잘못되었습니다</span>")
			$("#emconfirmchk").css({
				"color" : "#FA3E3E",
				"font-weight" : "bold",
				"font-size" : "10px"
			})
            $("#emconfirmchk").attr('value',emconfirmchk)
			//console.log("중복아이디");
		} else { // 아니면 중복아님
			emconfirmchk = true;
			$("#memailconfirmTxt").html("<span id='emconfirmchk'>인증번호 확인 완료</span>")
			$("#emconfirmchk").css({
				"color" : "#0D6EFD",
				"font-weight" : "bold",
				"font-size" : "10px"
			})
            $("#emconfirmchk").attr('value',emconfirmchk)
		}
	})
}

function submitCheck(){
    var result = $('#emconfirmchk').html();
    console.log("result : " + result);
    if(result=="인증번호가 잘못되었습니다"){
        alert("메일 인증번호를 확인하세요");
        return false;
    }
}