<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

function onclickBookmark(flag) {
	$.ajax({
		url: '/game/changebookmark',
		method: 'POST',
		dataType: 'json',
		data: {
			flag: flag
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result) {
				var html;
				if (flag == 'add') {
					$('#bookmarkItem').attr('class', 'label label-danger');
					$('#bookmarkItem').attr('onclick', 'onclickBookmark(\'remove\');');
					html = '<span class="glyphicon glyphicon-remove" id="glyphicon" aria-hidden="true"></span> 북마크 제거';
					$('#bookmarkItem').html(html);
				}
				else if (flag == 'remove') {
					$('#bookmarkItem').attr('class', 'label label-info');
					$('#bookmarkItem').attr('onclick', 'onclickBookmark(\'add\');');
					html = '<span class="glyphicon glyphicon-bookmark" id="glyphicon" aria-hidden="true"></span> 북마크 추가';
					$('#bookmarkItem').html(html);
				}
			}
			else {
				alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
		}
	});
}

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="panel panel-default" id="gameDetailArea">
			<div class="panel-body">
				<img src="/resources/images/icon/${game.gameCode}.jpg" alt="${game.gameName}">
				<div class="caption">
					<h3>${game.gameName}</h3>
					
					<div class="bs-glyphicons">
						<c:choose>
							<c:when test="${isBookmark}">
								<span class="label label-danger" id="bookmarkItem" onclick="onclickBookmark('remove');">
									<span class="glyphicon glyphicon-remove" id="glyphicon" aria-hidden="true"></span> 북마크 제거
								</span>
							</c:when>
							<c:otherwise>
								<span class="label label-info" id="bookmarkItem" onclick="onclickBookmark('add');">
									<span class="glyphicon glyphicon-bookmark" id="glyphicon" aria-hidden="true"></span> 북마크 추가
								</span>
							</c:otherwise>
						</c:choose>
					</div>
					<br/><br/>
					
					<p>${game.description}</p>
					<p>
						<a href="/game/free/list?isBack=false" class="btn btn-default" role="button">자유게시판</a>
						<a href="/game/find/list" class="btn btn-default" role="button">친구찾기</a>
					</p>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
