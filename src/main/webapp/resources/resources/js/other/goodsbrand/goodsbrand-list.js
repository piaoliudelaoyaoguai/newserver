$(function(){
	// 分页数据的显示
	$('#DataTables_table_paginate .paginate_button').on( 'click', function () {
		var cal = $(this).attr('class');
		var val;
		if(cal.indexOf('disabled')!=-1){
			val1 = $('#pageNoval').val();
			var re = /^[\d]+$/;
			if(!re.test(val1)){
				layer.msg('输入的必须为<font color="red">数字</font>！',{icon: 7,time:1500});
				return;
			}
			val = parseInt(val1);
			if(val <= 0){
				layer.msg('输入的数值不能 <font color="red">小于</font> 1！',{icon: 7,time:1500});
				return;
			}
			if(val > $(this).parents().find('.last_').attr('_val')){
				layer.msg('输入的数值不能 <font color="red">大于</font> 最大页码！',{icon: 7,time:1500});
				return;
			}
		}else{
			val = $(this).attr('_val');
		}
		var requrl = $('#requrl').val();
		requrl = encodeURI(requrl);  // 解决tomcat8以下 乱码问题
		requrl = encodeURI(requrl);
		window.location.href = "goodsbrand-list-page.html?"+requrl+"pageNo="+val;
	});
	
})

function detail_info(title,url,id,w,h){
	url = url + "?id="+id;
	layer_show(title,url,w,h);
}

function edit_info(title,url,id,w,h){
	url = url + "?id="+id;
	layer_show(title,url,w,h);
}

function add_info(title,url,w,h){
	layer_show(title,url,w,h);
}

function del_info(id,pageNo){
	layer.confirm('确认要删除吗？',function(index){
		window.location.href = "goodsbrand-delete.html?pageNo="+pageNo+"&id="+id;
	});
}

function datadel(pageNo){
	var id = $('input[type="checkbox"][name="id"]:checked');
	var ids = '';
	if(id.length<=0){
		layer.msg('请选择要删除的数据',{icon: 7,time:1000});
		return;
	}else{
		id.each(function(){
			ids += $(this).val()+",";
		});
		ids = ids.substring(0,ids.length - 1);
	}
	layer.confirm('确认要删除这些信息吗？',function(index){
		window.location.href = "goodsbrand-deletes.html?pageNo="+pageNo+"&ids="+ids;
	});
}
