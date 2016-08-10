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
	else if (flag == 'delete') {
		if (confirm('삭제하시겠습니까?')) {
			$('<form></form>')
			.attr('action', '/message/delete')
			.attr('method', 'post')
			.append($('<input type="hidden" name="seq" value="${message.seq}">'))
			.submit();
		}
		else {
			return false;
		}
	}
	else if (flag == 'write') {
		location.href = '/message/write';
	}
}

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<h3>${messageKindTitle}</h3>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">${message.title}</h3>
				<small>
					<c:if test="${message.messageKind == 'received'}">
						${message.fromNickname}
					</c:if>
					<c:if test="${message.messageKind == 'sent'}">
						${message.toNickname}
					</c:if>
					| ${message.registeredDate}
				</small>
			</div>
			<div class="panel-body" id="detailContentsArea">${message.contents}</div>
		</div>

		<div id="detailBtnArea">
			<button type="button" class="btn btn-default" onclick="onClickBtn('write');">
				<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;&nbsp;쓰기
			</button>
			
			<button type="button" class="btn btn-default" onclick="onClickBtn('delete');">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;삭제
			</button>
			
			<button type="button" class="btn btn-default" onclick="onClickBtn('list');">
				<span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp;&nbsp;목록
			</button>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
