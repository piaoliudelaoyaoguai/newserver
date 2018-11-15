<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${pageContext.request.contextPath }/resources/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/resources/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/resources/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<style type="text/css">
a:link {
	color: black;
	text-decoration: none;
} /*未访问：黑色、无下划线*/
a:active {
	color: black;
} /*激活：红色 */
a:visited {
	color: black;
	text-decoration: none;
} /*已访问：黑色、无下划线   */
a:hover {
	color: black;
	text-decoration: none;
} /*鼠标移近：红色、无下划线*/
</style>
<title>登录</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" id="loginform_" name="loginform_" action="login" method="post">
      <div id="checkmegess" style="margin: -10px auto -5px;color: #ff0000;width: 300px;height:22px; text-align: center;">
      		${message }
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="loginname" name="loginname" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="loginpassword" name="loginpassword" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input id="codeword" class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value=''}" onclick="if(this.value=='验证码'){this.value='';}" value="" style="width:150px;">
          <!-- <img src="images/VerifyCode.aspx.png"> <a id="kanbuq" href="javascript:;">看不清，换一张</a>  -->
          <a onclick="createCode()" title="看不清，换一张"><font id="imgcode" style="text-align:center;cursor: pointer;letter-spacing:5px;font-size: 150%;background-color: #CAEFFF;font-family:楷体;position: relative;top:3px; " class="input-text size-L"></font></a>
			<font color="#ccc" size="4">不区分大小写</font>
        </div>
      </div>
      <div class="row">
       <!--  <div class="formControls col-8 col-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value=""> 我保持登录状态</label>
        </div> -->
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input name="" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" onclick="login()">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;" style="margin-left: 100px;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright &copy;xxxx有限公司 by 2016-2020</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/H-ui.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/coderegist.js"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));


// szp 添加 2015年12月9日
//去除前后空格的方法
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, '');
};

function login(){// 登陆
	var msg = $("#checkmegess");
	loginname = $("#loginname");
	loginpassword = $("#loginpassword");
	codeword = $("#codeword");
	if(loginname.val().trim()==""){
		msg.html("账户不能为空");
		msg.css("display","block");
		return;
	}
	if(loginpassword.val().trim()==""){
		msg.html("密码不能为空");
		msg.css("display","block");
		return;
	}
	if(codeword.val().trim()==""){
		msg.html("验证码不能为空");
		msg.css("display","block");
		return;
	} 
	if(codeword.val().trim().toLocaleUpperCase()!=code){
		msg.html("验证码错误");
		codeword.val("");
		msg.css("display","block");
		code = createCode('imgcode');
		return;
	}
	$("#loginform_")[0].submit();
}

$(function(){
	code = createCode();
	
	// 按回车直接可以进行查询
	$("#codeword").keyup(function(event) {
		var keycode = event.which;
		if (keycode == 13) {
			login();
		}
	});
	$("#loginname").keyup(function(event) {
		var keycode = event.which;
		if (keycode == 13) {
			login();
		}
	});
	$("#loginpassword").keyup(function(event) {
		var keycode = event.which;
		if (keycode == 13) {
			login();
		}
	});
});
</script>
</body>
</html>