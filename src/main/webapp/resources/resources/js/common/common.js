$(function(){
	// 选中表格中的某一行
	$('.table-sort tbody').on( 'click', 'tr', function () {
		if ( $(this).hasClass('selected') ) {
			$(this).removeClass('selected');
		}
		else {
			$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});
});