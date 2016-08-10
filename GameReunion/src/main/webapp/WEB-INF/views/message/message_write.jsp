<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

function onClickBtn(flag) {
	if (flag == 'list') {
		location.href = '/message/list?isBack=true';
	}
	else if (flag == 'write') {
		var toMemberIds = $('#toMemberIds').val();
		var title = $('#title').val();
		var contents = $('#contents').val();
		
		if (toMemberIds.length == 0) {
			alert('받는 사람을 입력하세요.');
			return false;
		}
		
		if (title.length == 0) {
			alert('제목을 입력하세요.');
			return false;
		}
		
		if (contents.length == 0) {
			alert('내용을 입력하세요.');
			return false;
		}
		
		$.ajax({
			url: '/message/check/tomemberids',
			method: 'POST',
			dataType: 'json',
			data: {
				toMemberIds: toMemberIds
			},
			success: function(data, textStatus, jqXHR) {
				if (data.result) {
					$('#messageWriteForm').submit();
				}
				else {
					alert('받는 사람 중 존재하지 않는 사용자가 있습니다.');
					return false;
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
				return false;
			}
		});
	}
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
		
		<form id="messageWriteForm" action="/message/write" method="post">
			
			<div class="form-group">
				<input type="text" class="form-control" name="toMemberIds" id="toMemberIds" placeholder="받는사람 / 여러명은 (;)로 구분 / 최대 10명">
			</div>
			
			<div class="form-group">
				<input type="text" class="form-control" name="title" id="title" placeholder="제목">
			</div>
			
			<textarea class="form-control" name="contents" id="contents" rows="10" placeholder="내용 (최대 200자)"></textarea>
		</form>
		
		<div id="writeBtnArea">
			<button type="button" class="btn btn-default" onclick="onClickBtn('write');">
				<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;&nbsp;보내기
			</button>
			<button type="button" class="btn btn-default" onclick="onClickBtn('list');">
				<span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp;&nbsp;목록
			</button>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
