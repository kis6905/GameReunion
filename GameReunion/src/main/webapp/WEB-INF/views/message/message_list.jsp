<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

var table;
var pageNumber = 1;
var search = '';
var refresh = false;
var isBack = '${isBack}' == 'true' ? true : false;
var messageKind = 'received'; // 처음 들어오면 무조건 받은 쪽지다.

$(document).ready(function() {
	if (isBack) {
		pageNumber = $.cookie('pagaNumber');
		search = $.cookie('search');
		messageKind = $.cookie('messageKind');
		
		$('.search input').val(search);
		$('#messageKind').val(messageKind);
	}
	
	// 테이블 생성
	table = $('#table').bootstrapTable({
		method: 'post',
		url: '/message/list',
		contentType: 'application/json',
		dataType: 'json',
		queryParams: function(params) {
			location.href = '#';
			
			if (refresh) {
				refresh = false;
				params['offset'] = 0;
			}
			
			var searchText = $('.search input').val();
			$.cookie('pagaNumber', (params.offset / params.limit) + 1);
			$.cookie('search', searchText);
			$.cookie('messageKind', messageKind);
			
			params['search'] = searchText;
			params['messageKind'] = $('#messageKind').val();
			
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
			location.href = '/message/detail?seq=' + row.seq + '&messageKind=' + messageKind;
		},
		columns: [{
			field: 'title',
			title: '제목',
			width: '64%',
			align: 'left',
			valign: 'middle',
			sortable: false,
			formatter: messsageTitleFormatter
		}, {
			field: 'nickname',
			title: '닉네임',
			width: '19%',
			align: 'center',
			valign: 'middle',
			sortable: false
// 			formatter: nicknameFormatter
		}, {
			field: 'registeredDate',
			title: '받은 날짜',
			width: '17%',
			align: 'center',
			valign: 'middle',
			sortable: false,
			formatter: registeredDateFormatter
		}]
	});
});

function onChangeMessageKind() {
	messageKind = $('#messageKind').val();
	isBack = false;
	refresh = true;
	pageNumber = 1;
	search = '';
	table.bootstrapTable('refresh');
}

// 쪽지 목록의 제목 formatter
var messsageTitleFormatter = function(value, row, index) {
	var result = row.title;
	if (row.confirmStatus == 'N' && messageKind == 'received')
		result += '&nbsp;&nbsp;<span class=\"label label-danger\">N</span>';
	
	return result;
}

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<h3>쪽지</h3>
		</div>
		
		<div class="form-group">
			<select class="form-control" name="messageKind" id="messageKind" onchange="onChangeMessageKind()">
				<option value="received">받은 쪽지</option>
				<option value="sent">보낸 쪽지</option>
			</select>
		</div>
		
		<div id="listBtnArea">
			<button type="button" class="btn btn-default" onclick="javascript:location.href='/message/write'">
				<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;&nbsp;쓰기
			</button>
		</div>
		<table id="table"></table>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
