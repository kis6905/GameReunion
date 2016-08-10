<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	var pageNumber = 1;
	var search = '';
	var first = false;
	var isBack = '${isBack}' == 'true' ? true : false;
	if (isBack) {
		pageNumber = $.cookie('pagaNumber');
		search = $.cookie('search');
		first = true;
	}
	
	// 테이블 생성
	var table = $('#table').bootstrapTable({
		method: 'post',
		url: '/game/find/list',
		contentType: 'application/json',
		dataType: 'json',
		queryParams: function(params) {
			location.href = '#';
			
			if (isBack && first) {
				first = false;
				$('.search input').val(search);
			}
			
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
		pageSize: 10,
		cardView: true,
		search: true,
		showHeader: true,
		showColumns: false,
		showRefresh: false,
		minimumCountColumns: 4,
		clickToSelect: false,
		onClickRow: function(row, $element) {
			// row 클릭 시 할 동작 있으면 추가
		},
		columns: [{
			field: 'no',
			visible: false
		}, {
			field: 'memberId',
			visible: false
		}, {
			field: 'nickname',
			title: '작성자',
			valign: 'middle',
			sortable: false
		}, {
			field: 'registeredDate',
			title: '등록 일',
			valign: 'middle',
			sortable: false
		}, {
			field: 'serverName',
			title: '서버',
			valign: 'middle',
			sortable: false,
			formatter: function(value, row, index) {
				if (row.serverName == null || row.serverName == '')
					return '-';
				else
					return row.serverName;
			}
		}, {
			field: 'contents',
			title: '내용',
			valign: 'middle',
			sortable: false
		}, {
			field: 'btnSpace',
			title: '',
			valign: 'middle',
			sortable: false,
			formatter: function(value, row, index) {
				var html = '';
				
				// 자신이 쓴 글 일 경우 수정, 삭제 버튼
				if (row.memberId == '${MEMBER_ID}') {
				<%-- 
					지금은 수정 기능 없음. 추후 추가 할 경우 사용 
 					html = '<a class="like" href="#" onclick="onClickBtn(\'update\', \'\', \'' + row.no + '\'); return false;" title="수정">'
 						+ '<i class="glyphicon glyphicon-edit"></i>'
 						+ '</a>'
 						+ '&nbsp;&nbsp;'
				--%>
					html = '<a class="like" href="#" onclick="onClickBtn(\'delete\', \'\', \'' + row.no + '\'); return false;" title="삭제">'
						+ '<i class="glyphicon glyphicon-remove"></i>'
						+ '</a>';
				}
				// 자신이 쓴 글이 아닐 경우 쪽지, 대화 버튼
				else {
					html = '<a class="like" href="#" onclick="onClickBtn(\'message\', \'' + row.memberId + '\'); return false;" title="쪽지">'
						+ '<i class="glyphicon glyphicon-send"></i>'
						+ '</a>'
						+ '&nbsp;&nbsp;'
						+ '<a class="like" href="#" onclick="onClickBtn(\'chat\', \'' + row.memberId + '\'); return false;" title="채팅">'
						+ '<i class="glyphicon glyphicon-comment"></i>'
						+ '</a>';
				}
				return html;
			}
		}]
	});
});

function onClickBtn(flag, targetId, no) {
	if (flag == 'message') {
		alert('쪽지 기능은 아직 미 구현 기능입니다.');
	}
	else if (flag == 'chat') {
		alert('채팅 기능은 아직 미 구현 기능입니다.');
	}
// 	else if (flag == 'update') { 
// 		수정 기능은 없음
// 	}
	else if (flag == 'delete') {
		if (confirm('삭제하시겠습니까?'))
			deleteFindBoard(no);
	}
	else if (flag == 'write') {
		if ($('#contents').val().length > 50) {
			alert('내용은 50자 이내로 작성해주세요.');
			return false;
		}
		
		writeFindBoard();
	}
}

<%-- 글 삭제 처리 --%>
function deleteFindBoard(no) {
	$.ajax({
		url: '/game/find/delete',
		method: 'POST',
		dataType: 'json',
		data: {
			no: no
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result) {
				$('#table').bootstrapTable('refresh', '');
				$('#serverName').val('');
				$('#contents').val('');
			}
			else {
				alert('삭제가 실패했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

<%-- 글 등록 처리 --%>
function writeFindBoard() {
	$.ajax({
		url: '/game/find/write',
		method: 'POST',
		dataType: 'json',
		data: $('#findWriteForm').serialize(),
		success: function(data, textStatus, jqXHR) {
			if (data.result) {
				$('#table').bootstrapTable('refresh', '');
				$('#serverName').val('');
				$('#contents').val('');
			}
			else {
				alert('등록이 실패했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<h3>
				<span onclick="location.href='/game/detail?gameCode=${game.gameCode}'">${game.gameName}</span>
				<small> &gt; 친구 찾기</small>
			</h3>
		</div>
		
		<!-- 작성 란 -->
		<form id="findWriteForm">
			<div class="form-group">
				<select class="form-control" name="serverName" id="serverName">
					<option value="">전체</option>
					<c:forEach items="${gameServerList}" var="gameServer">
						<option value="${gameServer.serverName}">${gameServer.serverName}</option>
					</c:forEach>
				</select>
			</div>
			<textarea class="form-control" name="contents" id="contents" rows="3" placeholder="50자 이내로 작성해주세요."></textarea>
			<div id="findListBtnArea">
				<button type="button" class="btn btn-default" onclick="onClickBtn('write');">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;&nbsp;등록
				</button>
			</div>
		</form>
		<!-- 작성 란 끝 -->
		
		<hr> <!-- 작성 란과 리스트 구분 선 -->
		
		<table id="table"></table>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
