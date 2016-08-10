<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	getCommentList();
});

function getCommentList() {
	$.ajax({
		url: '/game/free/comment/list',
		method: 'POST',
		dataType: 'json',
		data: {
			boardNo : '${freeBoard.no}'
		},
		success: function(data, textStatus, jqXHR) {
			var html = '';
			var sessionMemberId = '${MEMBER_ID}';
			var commentList = data.commentList;
			var comment;
			for (var inx = 0; inx < commentList.length; inx++) {
				comment = commentList[inx];
				html += '<div class="list-group-item">'
					  + '<h5 class="list-group-item-heading">'
					  + comment.nickname + '&nbsp;&nbsp;<small>' + comment.registeredDate + '</small> &nbsp;';
				if (comment.memberId != sessionMemberId) {
					html += '<a href="#" onclick="onClickCommentBtn(\'message\', \'' + comment.memberId + '\', \'\'); return false;" title="쪽지"><i class="glyphicon glyphicon-send"></i></a>&nbsp;'
						  + '<a href="#" onclick="onClickCommentBtn(\'chat\', \'' + comment.memberId + '\', \'\'); return false;" title="대화"><i class="glyphicon glyphicon-comment"></i></a>';
				}
				else {
					html += '<a href="#" onclick="onClickCommentBtn(\'delete\', \'\', ' + comment.commentNo + '); return false;" title="삭제"><i class="glyphicon glyphicon-remove"></i></a>';
				}
				html += '</h5>'	
					  + '<p class="list-group-item-text">' + comment.contents + '</p>'
					  + '</div>';
			}
			$('#commentListArea').html(html);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('댓글 리스트를 가져오는데 실패했습니다. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
		}
	});
}

function onClickBtn(flag) {
	if (flag == 'list') {
		location.href = '/game/free/list?isBack=true';
	}
	else if (flag == 'update') {
		location.href = '/game/free/update?no=' + '${freeBoard.no}';
	}
	else if (flag == 'delete') {
		if (confirm('삭제하시겠습니까?')) {
			$('<form></form>')
			.attr('action', '/game/free/delete')
			.attr('method', 'post')
			.append($('<input type="hidden" name="no" value="${freeBoard.no}">'))
			.submit();
		}
		else {
			return false;
		}
	}
	else if (flag == 'write') {
		location.href = '/game/free/write';
	}
}

<%-- 댓글 등록, 삭제, 쪽지, 채팅 버튼 처리 --%>
function onClickCommentBtn(flag, targetId, commentNo) {
	if (flag == 'message') {
		alert('쪽지 기능은 아직 미 구현 기능입니다.');
	}
	else if (flag == 'chat') {
		alert('채팅 기능은 아직 미 구현 기능입니다.');
	}
	else if (flag == 'delete') {
		if (confirm('삭제하시겠습니까?'))
			deleteComment(commentNo);
	}
	else if (flag == 'write') {
		var commentContents = $('#commentContents').val();
		if (commentContents.length > 50) {
			alert('50자 이내로 작성해주세요.');
			return false;
		}
		writeComment(commentContents);
	}
}

<%-- 댓글 등록 처리 --%>
function writeComment(commentContents) {
	$.ajax({
		url: '/game/free/comment/write',
		method: 'POST',
		dataType: 'json',
		data: {
			boardNo : '${freeBoard.no}',
			commentContents: commentContents
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result) {
				getCommentList();
				$('#commentContents').val('');
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

<%-- 댓글 삭제 처리 --%>
function deleteComment(commentNo) {
	$.ajax({
		url: '/game/free/comment/delete',
		method: 'POST',
		dataType: 'json',
		data: {
			commentNo: commentNo
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result) {
				getCommentList();
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

</script>

</head>

<body>
	<%@ include file="../include/nav_bar.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<h3>
				<span onclick="location.href='/game/detail'">${game.gameName}</span>
				<small> &gt; 자유게시판</small>
			</h3>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">${freeBoard.title}</h3>
				<small>${freeBoard.nickname} | ${freeBoard.registeredDate}</small>
			</div>
			<div class="panel-body" id="detailContentsArea">${freeBoard.contents}</div>
		</div>

		<div id="detailBtnArea">
			<button type="button" class="btn btn-default" onclick="onClickBtn('write');">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;&nbsp;작성
			</button>
			
			<c:if test="${freeBoard.memberId == MEMBER_ID}">
				<button type="button" class="btn btn-default" onclick="onClickBtn('update');">
					<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;&nbsp;수정
				</button>
				<button type="button" class="btn btn-default" onclick="onClickBtn('delete');">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;삭제
				</button>
			</c:if>
			
			<button type="button" class="btn btn-default" onclick="onClickBtn('list');">
				<span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp;&nbsp;목록
			</button>
		</div>
		
		<hr>
		
		<div id="commentArea">
			<div class="list-group" id="commentListArea"></div>
			
			<textarea class="form-control" id="commentContents" rows="3" placeholder="50자 이내로 작성해주세요."></textarea>
			
			<div id="detailCommentBtnArea">
				<button type="button" class="btn btn-default" onclick="onClickCommentBtn('write');">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;&nbsp;등록
				</button>
			</div>
		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
