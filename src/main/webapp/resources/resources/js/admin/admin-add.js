
function checkphone(){
	var phone = $("#phone").val();
	var re = /^1[3-8]\d{9}$/;
	if(!re.test(phone)){
		$("#checkphone1").css("display","block");
		$("#checkphone1").find("#checkphone2").html("手机号码格式不正确");
		return false;
	}
	$("#checkphone1").css("display","none");
	return true;
}


/*function addsubject(){
	var subject1 = $("#subject1").val();
	var subject2 = $("#subject2").val();
	var subject3 = $("#subject3").val();
	var subject4 = $("#subject4").val();
	var subject5 = $("#subject5").val();
	if($("#kecheng2").css("display","none")&&
			$("#kecheng3").css("display","none")&&
			$("#kecheng4").css("display","none")&&
			$("#kecheng5").css("display","none")){
		$("#kecheng2").css("display","block");
	}else if($("#kecheng3").css("display","none")&&
			$("#kecheng4").css("display","none")&&
			$("#kecheng5").css("display","none")){
		$("#kecheng3").css("display","block");
	}else if($("#kecheng4").css("display","none")&&
			$("#kecheng5").css("display","none")){
		$("#kecheng4").css("display","block");
	}else if($("#kecheng5").css("display","none")){
		$("#kecheng5").css("display","block");
	}
}*/



