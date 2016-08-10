<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/header.jsp" %>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		
		<!-- Tab 버튼 -->
		<div id="tabBtnArea" class="btn-group btn-group-justified" role="group">
			<div class="btn-group" role="group">
				<button type="button" id="tabMessageBtn" class="btn btn-primary" title="쪽지" onClick="getTabView('message');"><i class="glyphicon glyphicon-user"></i></button>
			</div>
			<div class="btn-group" role="group">
				<button type="button" id="tabChatBtn" class="btn btn-default" title="채팅" onClick="getTabView('chat');"><i class="glyphicon glyphicon-search"></i></button>
			</div>
		</div>
		
		<!-- 쪽지 -->
		<div class="mypage-body-area" id="messageListArea">
		</div>
		
		<!-- 친구 찾기 -->
		<div class="mypage-body-area" id="chatListArea">
		</div>
		
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
