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
		/*window.location.href = "order-list-page.html?"+requrl+"pageNo="+val+"&mark=b";*/
		window.location.href = "order-list-page.html?"+requrl+"pageNo="+val;
	});
})

var program = $("#program").val();

function detaillist_info(title,url,orderno,w,h,pageNo){
	url = url + "?orderno="+orderno+"&pageNo="+pageNo;
	layer_show_max(title,url,w,h);
}
function detail_info(title,url,orderno,w,h,pageNo){
	url = url + "?orderno="+orderno+"&pageNo="+pageNo;
	layer_show_max(title,url,w,h);
}

function edit_info(title,url,id,w,h,pageNo){
	url = url + "?id="+id+"&pageNo="+pageNo;
	layer_show(title,url,w,h);
}

function add_info(title,url,w,h,pageNo){
	layer_show(title,url+"?pageNo="+pageNo,w,h);
}

function confirm_info(orderno,pageNo,mark){
	layer.confirm('此订单确认吗？',function(index){
		window.location.href = "order-confirmorder.html?pageNo="+pageNo+"&orderno="+orderno+"&mark="+mark;
	});
}
function peihuo_info(orderno,pageNo,mark){
	layer.confirm('此订单配货吗？',function(index){
		var requrl = $('#requrl').val();
		requrl = encodeURI(requrl);  // 解决tomcat8以下 乱码问题
		requrl = encodeURI(requrl);
		window.location.href = "order-peihuoorder.html?"+requrl+"pageNo="+pageNo+"&orderno="+orderno/*+"&mark="+mark*/;
	});
}

function print_info(title,url,orderno,w,h,pageNo){
	url = url + "?orderno="+orderno+"&pageNo="+pageNo;
	layer_show(title,url,w,h);
}


function del_info(id,pageNo){
	layer.confirm('确认要删除吗？',function(index){
		window.location.href = "order-delete.html?pageNo="+pageNo+"&id="+id;
	});
}

function confirmorders(pageNo,mark){
	var order = $('input[type="checkbox"][name="order"]:checked');
	var orders = '';
	if(order.length<=0){
		layer.msg('请选择要接单的订单',{icon: 7,time:1000});
		return;
	}else{
		order.each(function(){
			orders += $(this).val()+",";
		});
		orders = orders.substring(0,orders.length - 1);
	}
	layer.confirm('确认要批量接单这些订单吗？',function(index){
		window.location.href = "order-confirmorders.html?pageNo="+pageNo+"&ordernos="+orders+"&mark="+mark;
	});
}
