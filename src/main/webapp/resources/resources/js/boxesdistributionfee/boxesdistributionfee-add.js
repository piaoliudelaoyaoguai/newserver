
//查询店铺
function selectstore(){
	var disflag = $("#disflag").val();
	$.ajax({
		 type: "post",
		 url: $("#projectname").val()+"/boxesdistributionfee/selectlist?flag="+disflag,
		 dataType: "json", 
       success: function (data) {
      	 $('#storediv').find('#shopid').remove();
      	 	var str = '<select id="shopid" name="shopid" class="select" datatype="*" nullmsg="请选择一个店铺" errormsg="必须选择一个店铺">';
      	 		str+='<option value="" disabled="disabled" selected="selected" >请选择店铺</option>';
	        	 	for(var i =0;i<data.list.length;i++){ 
		        		 str+=""+" <option value='"+data.list[i].id+"'>"+data.list[i].storename+"</option>";
		        	 }
      	 	str +='</select>';
      	 	$('#storediv').append(str);
      	 $("#shopname").css("display","block");
       } 
	})
}
