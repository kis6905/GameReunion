<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

function onClickSearch() {
	var search = $('#search').val();
	location.href = '/game/list?search=' + search;
}

function onKeyDown() {
	if (event.keyCode == 13) {
		onClickSearch();
		return;
	}
}

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div id="gameSearchArea">
			<div class="row">
				<div class="col-lg-6">
					<div class="input-group">
						<input type="text" class="form-control" id="search" onkeydown="onKeyDown();" placeholder="게임 명..." value="${search}">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="onClickSearch();">검색</button>
						</span>
					</div>
				</div>
			</div>
		</div>
		
		<div id="bookmarkArea">
			<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>북마크
			
			<div class="bs-glyphicons" id="bookmarkList">
				<c:forEach items="${bookmarkList}" var="bookmark">
					<span class="label label-info" id="bookmarkItem" onclick="javascript:location.href='/game/detail?gameCode=${bookmark.gameCode}'">${bookmark.gameName}</span>
				</c:forEach>
			</div>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
