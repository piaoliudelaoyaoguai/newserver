/*
 * 时间格式处理
 * 
 */
function StringToDate(str) {// 字符串转化成日期格式（字符串格式为20140916121200日期格式为2014-09-16
							// 12:12:00
	var dateStr = '';
	if (typeof (str) == 'string') {
		if (str != null && str != "") {
			var strs = str.split("");
			for (var i = 0; i < 4; i++)
				dateStr += strs[i];
			dateStr += '-';
			for (var i = 4; i < 6; i++)
				dateStr += strs[i];
			dateStr += '-';
			for (var i = 6; i < 8; i++)
				dateStr += strs[i];
			dateStr += ' ';
			for (var i = 8; i < 10; i++)
				dateStr += strs[i];
			dateStr += ':';
			for (var i = 10; i < 12; i++)
				dateStr += strs[i];
			dateStr += ':';
			for (var i = 12; i < 14; i++)
				dateStr += strs[i];
		}
	}
	return dateStr;
}
/*
 * 时间格式处理
 * 
 */
function StringToDate2(str) {// 字符串转化成日期格式（字符串格式为20140916日期格式为2014-09-16
	var dateStr = '';
	if (typeof (str) == 'string') {
		if (str != null && str != "") {
			var strs = str.split("");
			for (var i = 0; i < 4; i++)
				dateStr += strs[i];
			dateStr += '-';
			for (var i = 4; i < 6; i++)
				dateStr += strs[i];
			dateStr += '-';
			for (var i = 6; i < 8; i++)
				dateStr += strs[i];
		}
	}
	return dateStr;
}
/*
 * 时间格式处理
 * 
 */
function StringToDate3(str) {// 字符串转化成日期格式（字符串格式为20140916日期格式为2014-09-16
	var dateStr = '';
	if (typeof (str) == 'string') {
		if (str != null && str != "") {
			var strs = str.split("");
			for (var i = 0; i < 4; i++)
				dateStr += strs[i];
			dateStr += '-';
			for (var i = 4; i < 6; i++)
				dateStr += strs[i];
		}
	}
	return dateStr;
}
/*
 * 计算年龄
 * */
function ages(str) {
	str = StringToDate2(str);
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null)
		return "";
	var d = new Date(r[1], r[3] - 1, r[4]);
	if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
			&& d.getDate() == r[4]) {
		var Y = new Date().getFullYear();
		return (Y - r[1]).toString();
	}
	return "";
}

function stringToDateRemoveMark(timestr) {// 转化成时间格式的字符串,去除中间的一些符号
	if (timestr != "") {
		var timetemp = '';
		var times = timestr.split(" ");
		var times_1 = times[0].split("-");
		var times_2 = times[1].split(":");
		for (var i = 0; i < times_1.length; i++) {
			timetemp += times_1[i];
		}
		for (var i = 0; i < times_2.length; i++) {
			timetemp += times_2[i];
		}
		return timetemp;
	}
	return "";
};
function getSystime() {
	// 得到系统当前的时间
	var sysdatetime = new Date();
	var sysdatetimestr = sysdatetime.format('yyyy-MM-dd hh:mm:ss').toString();
	sysdatetimestr = stringToDateRemoveMark(sysdatetimestr);
	return sysdatetimestr;
}
Date.prototype.format = function(format) {// 时间格式化成yyyy-MM-dd hh:mm:ss
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds(), // millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
function compareTo(strbefore, strafter) {// 比较两个时间的大小
	var intbefore;
	var intafter;
	var flag = "";
	for (var i = 0; i < strbefore.length; i++) {
		intbefore = parseInt(strbefore.charAt(i));
		intafter = parseInt(strafter.charAt(i));
		if (intbefore > intafter) {// 第一个大于第二个
			flag = "-1";
			break;
		} else if (intbefore < intafter) {// 第一个小于第二个
			flag = "1";
			break;
		} else {// 第一个等于第二个
			flag = "0";
		}
	}
	return flag;
}