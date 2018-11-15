<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="${pageContext.request.contextPath }/favicon.ico" >
<LINK rel="Shortcut Icon" href="${pageContext.request.contextPath }/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/html5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/respond.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${pageContext.request.contextPath }/resources/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/resources/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/resources/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="${pageContext.request.contextPath }/resources/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>管理平台</title>
<style type="text/css">
.Hui-subtitle{
	padding-top: 0px;
}
.Hui-userbar{
	width: 230px;
	right: 0px;
	height: 60px;
}
#Hui-nav > ul > li, #Hui-nav > ul > li > a{
	height: 60px;
	line-height:60px;
}
.audioplayer{
	display: none;
}
</style>
</head>
<body>
<header class="Hui-header cl"> <a class="Hui-logo l" title="" href="#"><span class="Hui-subtitle l" style="margin-left: -10px;"><img alt="" src="resources/resources/images/baseimg/logo.png" style="width: 190px;height: 60px;"></span></a> <!-- <a class="Hui-logo-m l" href="/" title="H-ui.admin">H-ui</a> <span class="Hui-subtitle l">V2.3</span> -->
	<ul class="Hui-userbar">
		<li>
			用户
		</li>
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A">${admin.name } <i class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a onclick="detail_info('${admin.name} 个人信息','admin/admin-detail.html','${admin.id}','','510')"href="javascript:;" title="个人信息">个人信息</a></li>
				<li><a onClick="change_password('修改密码','admin/admin-jumppasswordupdate.html','${admin.id}','600','270')" href="javascript:;" title="修改密码">密码修改</a></li>
				<li><a onClick="qiehuan()" href="javascript:;">切换账户</a></li>
				<li><a onClick="out_login()" href="javascript:;">退出</a></li>
			</ul>
		</li>
		<li id="Hui-msg"> <a onclick="clickhref()" href="javascript:void(0)" title="消息"><span class="badge badge-danger" id="kb"></span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（蓝色）">默认（蓝色）</a></li>
				<!-- <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li> -->
				<li><a href="javascript:;" data-val="black" title="黑色">黑色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul>
		</li>
		<!-- 2015年12月9日 szp 添加  // 2015年12月09日 11:35:59   星期三-->
		<li id="Hui-time">
			<audio controls="controls" preload="auto">
				<source src="${pageContext.request.contextPath }/resources/song/order.mp3" />
				<source src="${pageContext.request.contextPath }/resources/song/order.ogg" />
				<source src="${pageContext.request.contextPath }/resources/song/order.wav" />
			</audio>
			<span class="spantime" id="langcion"></span>
		</li>
	</ul>
	<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a> </header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-system">
					<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								
								<li><a _href="admin/admin-list.html" href="javascript:void(0)">管理员管理</a></li>
								<li><a _href="power/power-list.html" href="javascript:void(0)">权限管理</a></li>
								<!-- <li><a _href="system-shielding.html" href="javascript:void(0)">屏蔽词</a></li>
								<li><a _href="system-log.html" href="javascript:void(0)">系统日志</a></li> -->
							</ul>
						</dd>
					</dl>
	
		<%-- 
				<c:if test="${admin.roleid eq 'S'}">
					<dl id="menu-system">
						<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								
								<li><a _href="admin/admin-list.html" href="javascript:void(0)">管理员管理</a></li>
								<li><a _href="power/power-list.html" href="javascript:void(0)">权限管理</a></li>
								<!-- <li><a _href="system-shielding.html" href="javascript:void(0)">屏蔽词</a></li>
								<li><a _href="system-log.html" href="javascript:void(0)">系统日志</a></li> -->
							</ul>
						</dd>
					</dl>
				</c:if>
				<!-- <dl id="menu-admin">
					<dt><i class="Hui-iconfont">&#xe62d;</i> 部门管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="department/department-list.html" href="javascript:void(0)">总部</a></li>
							<li><a _href="department/department-list.html" href="javascript:void(0)">省部</a></li>
							<li><a _href="department/department-list.html" href="javascript:void(0)">市部</a></li>
							<li><a _href="department/department-list.html" href="javascript:void(0)">校区</a></li>
							<li><a _href="admin-role.html" href="javascript:void(0)">角色管理</a></li>
							<li><a _href="admin-permission.html" href="javascript:void(0)">权限管理</a></li> 
							<li><a _href="unitinfo/unitinfo-one-detail.html" href="javascript:void(0)">校区信息详细</a></li>
							<li><a _href="unitinfo/unitinfo-one-update.html" href="javascript:void(0)">校区信息修改</a></li>
							<li><a _href="manager/manager-list.html" href="javascript:void(0)">管理员列表</a></li>
						</ul>
					</dd>
				</dl> -->
				<dl id="menu-article">
					<dt><i class="Hui-iconfont">&#xe616;</i> 订单管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="order/order-list.html?mark=a" href="javascript:void(0)">待支付订单</a></li>
							<li><a _href="order/order-list.html?mark=b" href="javascript:void(0)">待发货订单</a></li>
							<li><a _href="order/order-list.html?mark=c" href="javascript:void(0)">待收货订单</a></li>
							<li><a _href="order/order-list.html?mark=d" href="javascript:void(0)">待评价订单</a></li>
							<li><a _href="order/order-list.html?mark=e" href="javascript:void(0)">历史订单</a></li>
							<!-- <li><a _href="system-category.html" href="javascript:void(0)">家长管理</a></li>
							<li><a _href="student/student-list.html?unit_id=${admin.unit_id}" href="javascript:void(0)">学生管理</a></li> -->
						</ul>
					</dd>
				</dl>
				<dl id="menu-picture">
					<dt><i class="Hui-iconfont">&#xe61a;</i>分类管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<!-- <li><a _href="classinfo/classinfo-list.html" href="javascript:void(0)">班级信息</a></li>
							<li><a _href="classinfo/classcourse-list.html" href="javascript:void(0)">班级课程</a></li>
							<li><a _href="" href="javascript:void(0)">选修课</a></li> -->
							<li><a _href="martclassfy/martclassfy-list.html?flag=1" href="javascript:void(0)">易考拉超市分类</a></li>
							<!-- <li><a _href="martclassfy/martclassfy-list.html" href="javascript:void(0)">商铺分类</a></li> -->
						</ul>
					</dd>
				</dl>
				
				<dl id="menu-product">
					<dt><i class="Hui-iconfont">&#xe620;</i>商品管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<!-- <li><a _href="martgoods/martgoods-list0.html" href="javascript:void(0)">所有商品</a></li> -->
							<li><a _href="martgoods/martgoods-list2.html" href="javascript:void(0)">热销</a></li>
							<li><a _href="martgoods/martgoods-list1.html" href="javascript:void(0)">特价</a></li>
							<li><a _href="martgoods/martgoods-list6.html" href="javascript:void(0)">活动详情商品</a></li>
							<li><a _href="martgoods/martgoods-list3.html" href="javascript:void(0)">易考拉超市</a></li>
							<!-- <li><a _href="martgoods/martgoods-list4.html" href="javascript:void(0)">海外购</a></li>
							<li><a _href="martgoods/martgoods-list5.html" href="javascript:void(0)">校内超市</a></li> -->
							<li><a _href="promotion/promotion-list.html" href="javascript:void(0)">轮播图</a></li>
						</ul>
					</dd>
				</dl>
				<dl id="menu-page">
					<dt><i class="Hui-iconfont">&#xe626;</i>业务规则<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="scorerole/scorerole-list.html" href="javascript:void(0)">积分规则管理</a></li>
							<li><a _href="scorerole/scoreexchange-list.html" href="javascript:void(0)">积分兑换规则管理</a></li>
							<li><a _href="redpocketmanage/redpocketgrant-list.html" href="javascript:void(0)">红包发放规则管理</a></li>
							<li><a _href="redpocketmanage/redpocketuse-list.html" href="javascript:void(0)">红包使用规则管理</a></li>
							<li><a _href="boxesdistributionfee/boxesdistributionfee-list.html" href="javascript:void(0)">配送费/餐盒费规则</a></li>
							<li><a _href="preferentialactivity/preferentialactivity-list.html" href="javascript:void(0)">满减优惠规则管理</a></li>
							<li><a _href="redpacketset/redpacketset-list.html" href="javascript:void(0)">抢红包规则管理</a></li>
						</ul>
					</dd>
				</dl>
				
				<dl id="menu-order">
					<dt><i class="Hui-iconfont">&#xe63a;</i>兼职车辆<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="car/car-list.html" href="javascript:;">车辆管理</a></li>
							<li><a _href="sender/sender-list.html" href="javascript:;">人员管理</a></li>
						</ul>
					</dd>
				</dl>
				<dl id="menu-member">
					<dt><i class="Hui-iconfont">&#xe60d;</i> 会员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="user/user-list.html" href="javascript:;">会员资料管理</a></li>
							<li><a _href="comment/comment-list.html" href="javascript:;">会员评论管理</a></li>
							
							<!-- <li><a _href="member-del.html" href="javascript:;">删除的会员</a></li>
							<li><a _href="member-level.html" href="javascript:;">等级管理</a></li>
							<li><a _href="member-scoreoperation.html" href="javascript:;">积分管理</a></li>
							<li><a _href="member-record-browse.html" href="javascript:void(0)">浏览记录</a></li>
							<li><a _href="member-record-download.html" href="javascript:void(0)">下载记录</a></li>
							<li><a _href="member-record-share.html" href="javascript:void(0)">分享记录</a></li> -->
						</ul>
					</dd>
				</dl>
				<dl id="menu-tongji">
					<dt><i class="Hui-iconfont">&#xe61a;</i>校区管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="schoolarea/schoolarea-list.html?flag=0&pid=null" href="javascript:void(0)">校区管理</a></li>
							<li><a _href="distance/distance-list.html" href="javascript:void(0)">定位距离管理</a></li>
						</ul>
					</dd>
				</dl>
				<dl id="menu-message">
					<dt><i class="Hui-iconfont">&#xe622;</i>推广促销<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="message/message-list.html" href="javascript:;">推广促销信息管理</a></li>
							<!-- <li><a _href="feedback-list.html" href="javascript:void(0)">意见反馈</a></li> -->
						</ul>
					</dd>
				</dl>
				
				
		--%>
	</div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="welcome.html">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<!-- <iframe scrolling="yes" frameborder="0" src="welcome.html"></iframe> -->
			<div style="font-size: 60px;font-weight: bolder;margin-top: 100px;text-align:center">
				欢&nbsp;&nbsp;迎&nbsp;&nbsp;使&nbsp;&nbsp;用&nbsp;&nbsp;本&nbsp;&nbsp;后&nbsp;&nbsp;台&nbsp;&nbsp;管&nbsp;&nbsp;理&nbsp;&nbsp;系&nbsp;&nbsp;统&nbsp;&nbsp;!
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/audioplayer-main.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/audioplayer.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/H-ui.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/comet4j.js"></script> 
<script type="text/javascript">
	$(function(){
		setInterval( function(){
			var date = new Date();
			var time = date.getFullYear()+'年'+(date.getMonth()+1)+'月'
					  +date.getDate()+'日'+' '+date.getHours()+':'+date.getMinutes()+':'+(date.getSeconds()>9?date.getSeconds():'0'+date.getSeconds())
					  +' 星期'+'日一二三四五六'.charAt(date.getDay());
			document.getElementById('langcion').innerHTML=time;
		}, 1000);// 显示时间的
		init();
	});
	
	setTimeout(function(){
		$( 'audio' ).audioPlayer();//播放声音
	},0);
	
	function change_password(title,url,id,w,h){
		url = url + "?id="+id+"&pageNo=1";
		layer_show(title,url,w,h);
	}
	
	function detail_info(title,url,id,w,h){
		url = url + "?id="+id+"&pageNo=1";
		layer_show(title,url,w,h);
	}

	function out_login() {
		layer.confirm('确定要退出吗？',function(index){
			window.location.href = "out_login_do";
		});
	}
	
	function qiehuan(){
		window.location.href = "out_login_do";
	}
	
	
	// 监听订单
	function init(){
        var kbDom = document.getElementById('kb');
        JS.Engine.on({
                hello : function(ordersend){//侦听一个channel
                        kbDom.innerHTML = ordersend.ordernum;
                		var no = ordersend.no;
                		if(no > 0 ){
                			//for(var i =0;i<=no.length;i++){
                				$('.audioplayer-playpause').click();
                			//}
                		}
                }
        });
        JS.Engine.start('conn');
        JS.Engine.on(
        'start',function(cId,channelList,engine){
        	 /*  alert('连接已建立，连接ID为：' + cId); */
        });
	}

	function clickhref(){
		$('#menu-article').find('dt').click();
		$('#menu-article').find('li').eq(1).find('a').click();
	}
	
</script>
<script type="text/javascript">
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s)})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>



</body>
</html>