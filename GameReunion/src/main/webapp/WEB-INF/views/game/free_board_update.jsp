<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

function onClickBtn(flag) {
	if (flag == 'cancel') {
		history.back();
	}
	else if (flag == 'update') {
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
		
		var form = $('#freeUpdateForm');
		form.append($('<input type="hidden" name="no" value="${freeBoard.no}">'))
		form.submit();
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
		
		<form id="freeUpdateForm" action="/game/free/update" method="post">
			<div class="form-group">
				<input type="text" class="form-control" name="title" id="title" placeholder="제목" value="${freeBoard.title}">
			</div>
			
			<textarea class="form-control" name="contents" id="contents" rows="10" placeholder="내용">${freeBoard.contents}</textarea>
		</form>
		
		<div id="writeBtnArea">
			<button type="button" class="btn btn-default" onclick="onClickBtn('update');">
				<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;&nbsp;저장
			</button>
			<button type="button" class="btn btn-default" onclick="onClickBtn('cancel');">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;취소
			</button>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
