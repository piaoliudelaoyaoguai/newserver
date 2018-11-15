
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


//查询城市
function selectprovince() {
	var shengid = $("#sheng").val();
	$.ajax({
		 type: "post",
		 url: $("#projectname").val()+"/martgoods/selectlist?id="+shengid+"&flag=1",
		 dataType: "json", 
       success: function (data) {
      	 $('#provincediv').find('#province').remove();
      	 	var str = '<select id="province" name="province" class="select" datatype="*" nullmsg="请选择一个城市" errormsg="必须选择一个城市" onchange="selectarea()">';
      	 		str+='<option value="" disabled="disabled" selected="selected" >请选择城市</option>';
	        	 	for(var i =0;i<data.list.length;i++){ 
		        		 str+=""+" <option value='"+data.list[i].id+"'>"+data.list[i].province+"</option>";
		        	 }
      	 	str +='</select>';
      	 	$('#provincediv').append(str);
      	 $("#provinceselect").css("display","block");
       } 
	})
}


//查询区域
function selectarea() {
	var provinceid = $("#province").val();
	$.ajax({
		 type: "post",
		 url: $("#projectname").val()+"/martgoods/selectlist?id="+provinceid+"&flag=2",
		 dataType: "json", 
       success: function (data) {
      	 $('#areadiv').find('#area').remove();
      	 	var str = '<select id="area" name="area" class="select" datatype="*" nullmsg="请选择一个区域" errormsg="必须选择一个区域" onchange="selectschoolname()">';
      	 		str+='<option value="" disabled="disabled" selected="selected" >请选择区域</option>';
	        	 	for(var i =0;i<data.list.length;i++){ 
		        		 str+=""+" <option value='"+data.list[i].id+"'>"+data.list[i].area+"</option>";
		        	 }
      	 	str +='</select>';
      	 	$('#areadiv').append(str);
      	 $("#areaselect").css("display","block");
       } 
	})
	if(provinceid==""){
		 $("#areaselect").css("display","none");
	}else{
		 $("#areaselect").css("display","block");
	}
}


//查询学校
function selectschoolname() {
	var areaid = $("#area").val();
	$.ajax({
		 type: "post",
		 url: $("#projectname").val()+"/martgoods/selectlist?id="+areaid+"&flag=3",
		 dataType: "json", 
       success: function (data) {
      	 $('#schoolnamediv').find('#schoolname').remove();
      	 	var str = '<select id="schoolname" name="schoolareaid" class="select" datatype="*" nullmsg="请选择一个学校" errormsg="必须选择一个学校" onchange="selectclassfy()">';
      	 		str+='<option value="" disabled="disabled" selected="selected" >请选择学校</option>';
	        	 	for(var i =0;i<data.list.length;i++){ 
		        		 str+=""+" <option value='"+data.list[i].id+"'>"+data.list[i].schoolname+"</option>";
		        	 }
      	 	str +='</select>';
      	 	$('#schoolnamediv').append(str);
      	 $("#schoolnameselect").css("display","block");
       } 
	})
}

//查询分类
function selectclassfy(){
	var schoolnameid = $("#schoolname").val();
	$.ajax({
		 type: "post",
		 url: $("#projectname").val()+"/martgoods/selectlist2?schoolareaid="+schoolnameid+"&schoolareaflag=3",
		 dataType: "json", 
       success: function (data) {
      	 $('#martclassfydiv').find('#martclassfy').remove();
      	 	var str = '<select id="martclassfy" name="classifyid" class="select" datatype="*" nullmsg="请选择一个分类" errormsg="必须选择一个分类">';
      	 		str+='<option value="" disabled="disabled" selected="selected" >请选择分类</option>';
	        	 	for(var i =0;i<data.list2.length;i++){ 
		        		 str+=""+" <option value='"+data.list2[i].id+"'>"+data.list2[i].classifyname+"</option>";
		        	 }
      	 	str +='</select>';
      	 	$('#martclassfydiv').append(str);
      	 $("#martclassfyselect").css("display","block");
       } 
	})
}




