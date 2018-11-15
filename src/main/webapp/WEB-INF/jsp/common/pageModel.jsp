<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="dataTables_info" id="DataTables_Table_info" role="status" aria-live="polite">
	<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_paginate_">
		<span >第  <span class="paginate_button" style="cursor:text;background: #5a98de;color: #FFF;" >${pageModel.pageNo }</span> 页  / 
		共 <span class="paginate_button" style="cursor:text;background: #5a98de;color: #FFF;" >${pageModel.totalPages }</span> 页</span>
	</div>
</div>
<div class="dataTables_paginate paging_simple_numbers" id="DataTables_table_paginate">
	<a class="paginate_button disabled" aria-controls="DataTables_Table" data-dt-idx="0" tabindex="0"  id="DataTables_Table_first">跳转 >></a>
	<input value="${pageModel.pageNo }" id="pageNoval" style="width: 40px;height: 26px;border: 1px #ccc solid;line-height: 26px;padding-left: 4px;margin-left: 5px;position: relative;top:-1px;"/>
	<a class="paginate_button first_" aria-controls="DataTables_Table" data-dt-idx="0" tabindex="0"  _val="${pageModel.topPageNo }" id="DataTables_Table_first" style="margin-left: 15px;">首页</a>
	<a class="paginate_button previous_" aria-controls="DataTables_Table" data-dt-idx="0" tabindex="0" _val="${pageModel.previousPageNo }" id="DataTables_Table_previous">上一页</a>
	<a class="paginate_button next_" aria-controls="DataTables_Table" data-dt-idx="3" tabindex="0" _val="${pageModel.nextPageNo }" id="DataTables_Table_next">下一页</a>
	<a class="paginate_button last_" aria-controls="DataTables_Table" data-dt-idx="3" tabindex="0" _val="${pageModel.buttomPageNo }"  id="DataTables_Table_last">尾页</a>
</div>