/**
 * jQuery checkimgsize
 * 
 * @_file 图片文件对象（是dom对象）
 * @_width 图片文件的要限定的宽
 * @_height 图片文件的要限定的高
 * @_size 图片文件的要限定的尺寸大小
 * @_msg 上传图片的消息（dom对象）
 * @使用： $.checkimgsize('_file','_width','_height','_size','_msg')
 */

var istrue = false;

jQuery.checkimgsize = function(_file, _width, _height, _size, _msg) {
	var formdata = new FormData();
	var fileObj = _file.files;
	formdata.append("imgFile", fileObj[0]);
	// 获取带"/"的项目名，如：/uimcardprj
	var pathName = window.document.location.pathname;
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	//alert("路径"+projectName);
	$.ajax({
		url : projectName + '/isimgsizetrue_do?width=' + _width + '&height='
				+ _height + "&size=" + _size,
		type : "POST",
		data : formdata,
		cache : false,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data) {
			if(null!=data){
				var result = data["result"];
				if ("success" == result) {
					_msg.innerHTML = '<span class="Validform_checktip Validform_right"> </span>';
					istrue = true;
				}
				if ("e_width" == result) {
					_msg.innerHTML = '<span class="Validform_checktip Validform_wrong">上传的图片宽度 ' + data["width"] + ' 大于 要上传的最大宽度 '+ _width + ' ！</span>';
					istrue = false;
				}
				if ("e_height" == result) {
					_msg.innerHTML = '<span class="Validform_checktip Validform_wrong">上传的图片高度 ' + data["height"] + ' 大于 要上传的最大高度 '+ _height + ' ！</span>';
					istrue = false;
				}
				if ("e_size" == result) {
					_msg.innerHTML = '<span class="Validform_checktip Validform_wrong">上传的图片大小 ' + data["size"] + ' 大于 要上传的最大尺寸大小 '+ _size + ' ！</span>';
					istrue = false;
				}
				if("error" == result){
					_msg.innerHTML = '<span class="Validform_checktip Validform_wrong">上传的图片有误!</span>';
					istrue = false;
				}
			}else{
				istrue = true; 
			}
		},
		error : function(data) {
			_msg.innerHTML = '<span class="Validform_checktip Validform_wrong">上传的图片有误!</span>';
			istrue = false;
		}
	});
	return istrue;
};
