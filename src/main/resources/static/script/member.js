

let valid = false;

$(function() {
	$("#memberid").on('keyup', memberidCheck);
	
	$("#regist").on('click', function(){
		if(!valid) {
		alert("데이터를 모두 정확히 입력해 주세요");
		$("#memberid").focus();	
		return;
	}
	
	else 
		$("#memberForm").submit();
	})
	
});

// 아이디 체크
function memberidCheck() {
	valid = false;

	let memberid = $("#memberid").val();

	let sendData = {"memberid": memberid};
	$.ajax({
		url: 'idCheck'
		, method : 'POST'
		, data : sendData
		, success : function(resp){
			if(resp == "success") {
				$("span#memberidResult").css("color", "blue"); 
				$("#memberidResult").text('사용가능한 아이디입니다.');
				valid = true;
			} else {
				$("span#memberidResult").css("color", "red");
				$("#memberidResult").text('사용 할 수 없는 아이디입니다.');
				valid = false;
			}
		}
	});
}