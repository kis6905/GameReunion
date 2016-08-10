<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	getTabView('list');
});

<%------------------- 친구 목록 탭 -------------------%>
/* 
 * 친구 리스트를 가져와 화면에 뿌림
 */
function getFriendList() {
	$.ajax({
		url: '/friend/list',
		method: 'POST',
		dataType: 'json',
		data: {},
		success: function(data, textStatus, jqXHR) {
			var friendList = data.friendList;
			var html = '<ul class="list-group">';
			if (friendList.length > 0) {
				var friend;
				for (var inx = 0; inx < friendList.length; inx++) {
					friend = friendList[inx];
					html += '<li class="list-group-item">' + friend.nickname 
							+ '<span class="icon">'
					 			+ '<a href="#" onclick="onClickBtnInList(\'delete\', \'' + friend.toMemberId + '\'); return false;"><i class="glyphicon glyphicon-remove"></i></a>'
					 		+ '</span>'
						 + '</li>';
				}
			}
			else {
				html += '<li class="list-group-item">친구가 없습니다.</li>';
			}
			html += '</ul>';
			$('#friendListArea').html(html);
		},
		error: function(jqXHR, textStatus, errorThrown) {
// 			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

/*
 * 친구 삭제
 */
function deleteFriend(memberId) {
	$.ajax({
		url: '/friend/delete',
		method: 'POST',
		dataType: 'json',
		data: {
			deleteMemberId: memberId
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result)
				getFriendList();
			else
				alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');	
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

/**
 * 버튼 클릭 처리
 */
function onClickBtnInList(flag, memberId) {
	if (flag == 'delete') {
		if (confirm('삭제하시겠습니까?'))
			deleteFriend(memberId);
	}
}
<%------------------- 친구 목록 탭 END -------------------%>

<%------------------- 친구 검색 탭 -------------------%>
/* 
 * 검색한 친구 리스트를 가져와 화면에 뿌림
 */
function getSearchFriendList() {
	$.ajax({
		url: '/friend/search',
		method: 'POST',
		dataType: 'json',
		data: {
			search: $('#search').val()
		},
		success: function(data, textStatus, jqXHR) {
			var memberList = data.memberList;
			var html = '<ul class="list-group">';
			if (memberList.length > 0) {
				var member;
				for (var inx = 0; inx < memberList.length; inx++) {
					member = memberList[inx];
					html += '<li class="list-group-item">' + member.nickname;
					if (!member.isFriend)
						html += '<span class="icon"><a href="#" onclick="onClickBtnInSearch(\'request\', \'' + member.memberId + '\'); return false;"><i class="glyphicon glyphicon-plus-sign"></i></a></span>'
					html += '</li>';
				}
			}
			else {
				html += '<li class="list-group-item">검색 결과가 없습니다.</li>';
			}
			html += '</ul>';
			$('#serarchResultArea').html(html);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

/*
 * 친구 요청
 */
function requestFriend(toMemberId) {
	$.ajax({
		url: '/friend/request',
		method: 'POST',
		dataType: 'json',
		data: {
			toMemberId: toMemberId
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result == COMMON_SERVER_ERROR)
				alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');	
			else
				alert('친구 요청을 했습니다. \n상대방이 수락하면 친구목록에 추가됩니다.');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

/**
 * 버튼 클릭 처리
 */
function onClickBtnInSearch(flag, toMemberId) {
	if (flag == 'search') { // 검색
		getSearchFriendList();
	}
	else if (flag == 'request') { // 친구추가 요청
		requestFriend(toMemberId);
	}
}
<%------------------- 친구 검색 탭 END -------------------%>

<%------------------- 친구 추가 탭 -------------------%>
/*
 * 나에게 친구요청 한 리스트 가져와 화면에 뿌림
 */
function getReceiveFriendList() {
	$.ajax({
		url: '/friend/receive/list',
		method: 'POST',
		dataType: 'json',
		data: {},
		success: function(data, textStatus, jqXHR) {
			var receiveList = data.receiveList;
			var html = '<ul class="list-group">';
			if (receiveList.length > 0) {
				var friend;
				for (var inx = 0; inx < receiveList.length; inx++) {
					friend = receiveList[inx];
					html += '<li class="list-group-item">' + friend.nickname
							 + '<span class="icon">'
							 	+ '<a href="#" onclick="onClickBtnInAdd(\'accept\', \'' + friend.fromMemberId + '\'); return false;"><i class="glyphicon glyphicon-ok"></i></a>'
							 	+ '&nbsp;&nbsp;&nbsp;&nbsp;'
							 	+ '<a href="#" onclick="onClickBtnInAdd(\'refuse\', \'' + friend.fromMemberId + '\'); return false;"><i class="glyphicon glyphicon-remove"></i></a>'
							 + '</span>'
						 + '</li>';
				}
			}
			else {
				html += '<li class="list-group-item">목록이 없습니다.</li>';
			}
			html += '</ul>';
			$('#addFriendArea').html(html);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

/*
 * 친구 수락 or 거절
 */
function changeFriendStatus(flag, fromMemberId) {
	$.ajax({
		url: '/friend/changestatus',
		method: 'POST',
		dataType: 'json',
		data: {
			fromMemberId: fromMemberId,
			status: flag
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result)
				getReceiveFriendList(); // 나에게 요청한 목록 리로드
			else
				alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');		
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

/**
 * 버튼 클릭 처리
 */
function onClickBtnInAdd(flag, fromMemberId) {
	var request = false;
	if (flag == 'accept')
		request = confirm('수락하시겠습니까?');
	else if (flag == 'refuse')
		request = confirm('거절할 경우 해당 사용자는 당신에게 더 이상 친구 요청을 할 수 없습니다. \n계속하시겠습니까?');
	
	if (request)
		changeFriendStatus(flag, fromMemberId);
}
<%------------------- 친구 추가 탭 END -------------------%>


/*
 * 탭 버튼(리스트, 검색, 추가) 클릭 처리
 */
function getTabView(tab) {
	if (tab == 'list') {
		$('#friendListArea').show();
		$('#searchFriendArea').hide();
		$('#addFriendArea').hide();
		
		$('#tabListBtn').removeClass().addClass('btn btn-primary');
		$('#tabSearchBtn').removeClass().addClass('btn btn-default');
		$('#tabAddBtn').removeClass().addClass('btn btn-default');
		
		getFriendList();
	}
	else if (tab == 'search') {
		$('#friendListArea').hide();
		$('#searchFriendArea').show();
		$('#addFriendArea').hide();
		
		$('#tabListBtn').removeClass().addClass('btn btn-default');
		$('#tabSearchBtn').removeClass().addClass('btn btn-primary');
		$('#tabAddBtn').removeClass().addClass('btn btn-default');
		
		$('#serarchResultArea').html(''); // 이전에 검색한 결과 삭제
	}
	else if (tab == 'add') {
		$('#friendListArea').hide();
		$('#searchFriendArea').hide();
		$('#addFriendArea').show();
		
		$('#tabListBtn').removeClass().addClass('btn btn-default');
		$('#tabSearchBtn').removeClass().addClass('btn btn-default');
		$('#tabAddBtn').removeClass().addClass('btn btn-primary');
		
		getReceiveFriendList();
	}
}

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		
		<!-- Tab 버튼 -->
		<div id="tabBtnArea" class="btn-group btn-group-justified" role="group">
			<div class="btn-group" role="group">
				<button type="button" id="tabListBtn" class="btn btn-primary" title="친구 목록" onClick="getTabView('list');"><i class="glyphicon glyphicon-user"></i></button>
			</div>
			<div class="btn-group" role="group">
				<button type="button" id="tabSearchBtn" class="btn btn-default" title="친구 검색" onClick="getTabView('search');"><i class="glyphicon glyphicon-search"></i></button>
			</div>
			<div class="btn-group" role="group">
				<button type="button" id="tabAddBtn" class="btn btn-default" title="친구 추가" onClick="getTabView('add');"><i class="glyphicon glyphicon-plus"></i></button>
			</div>
		</div>
		
		<!-- 친구 목록 -->
		<div class="friend-body-area" id="friendListArea">
		</div>
		
		<!-- 친구 찾기 -->
		<div class="friend-body-area" id="searchFriendArea">
			<div class="row">
				<div class="col-lg-6">
					<div class="input-group">
						<input type="text" class="form-control" id="search" placeholder="검색할 닉네임을 입력하세요.">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="onClickBtnInSearch('search');">검색</button>
						</span>
					</div>
				</div>
			</div>
			
			<div id="serarchResultArea">
			</div>
		</div>
		
		<!-- 친구 추가 -->
		<div class="friend-body-area" id="addFriendArea">
		</div>
		
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
