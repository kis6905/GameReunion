<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	var pageNumber = 1;
	var search = '';
	var isBack = '${isBack}' == 'true' ? true : false;
	if (isBack) {
		pageNumber = $.cookie('pagaNumber');
		search = $.cookie('search');
		
		$('.search input').val(search);
	}
	
	// 테이블 생성
	var table = $('#table').bootstrapTable({
		method: 'post',
		url: '/game/free/list',
		contentType: 'application/json',
		dataType: 'json',
		queryParams: function(params) {
			location.href = '#';
			
			pageNumber = $.cookie('pagaNumber', (params.offset / params.limit) + 1);
			var searchText = $('.search input').val();
			$.cookie('search', searchText);
			params['search'] = searchText;
			
			return params;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageNumber: pageNumber,
		pageSize: 15,
		search: true,
		showHeader: false,
		showColumns: false,
		showRefresh: false,
		minimumCountColumns: 3,
		clickToSelect: false,
		onClickRow: function(row, $element) {
			location.href = '/game/free/detail?no=' + row.no;
		},
		columns: [{
			field: 'no',
			title: 'No',
			align: 'center',
			valign: 'middle',
			visible: false
		}, {
			field: 'title',
			title: '제목',
			width: '64%',
			align: 'left',
			valign: 'middle',
			sortable: false,
			formatter: boardTitleFormatter
		}, {
			field: 'nickname',
			title: '작성자',
			width: '19%',
			align: 'center',
			valign: 'middle',
			sortable: false,
			formatter: nicknameFormatter
		}, {
			field: 'registeredDate',
			title: '등록 일',
			width: '17%',
			align: 'center',
			valign: 'middle',
			sortable: false,
			formatter: registeredDateFormatter
		}]
	});
});

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<h3>
				<span onclick="location.href='/game/detail?gameCode=${game.gameCode}'">${game.gameName}</span>
				<small> &gt; 자유게시판</small>
			</h3>
		</div>
		
		<div id="listBtnArea">
			<button type="button" class="btn btn-default" onclick="javascript:location.href='/game/free/write'">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;&nbsp;작성
			</button>
		</div>
		<table id="table"></table>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
