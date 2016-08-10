<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

function onClickBtn(flag) {
	if (flag == 'list') {
		location.href = '/game/free/list?isBack=true';
	}
	else if (flag == 'write') {
		var title = $('#title').val();
		var contents = $('#contents').val();
		
		if (title.length == 0) {
			alert('제목을 입력하세요.');
			return false;
		}
		
		if (contents.length == 0) {
			alert('내용을 입력하세요.');
			return false;
		}
		
		$('#freeWriteForm').submit();
	}
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
				<small> &gt; 자유게시판</small>
			</h3>
		</div>
		
		<form id="freeWriteForm" action="/game/free/write" method="post">
			<div class="form-group">
				<input type="text" class="form-control" name="title" id="title" placeholder="제목">
			</div>
			
			<textarea class="form-control" name="contents" id="contents" rows="10" placeholder="내용"></textarea>
		</form>
		
		<div id="writeBtnArea">
			<button type="button" class="btn btn-default" onclick="onClickBtn('write');">
				<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;&nbsp;저장
			</button>
			<button type="button" class="btn btn-default" onclick="onClickBtn('list');">
				<span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp;&nbsp;목록
			</button>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
