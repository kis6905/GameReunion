<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<%@ include file="../include/header.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	
	var kind = '${kind}';
	
	if (kind == 'write')
		alert('글 작성이 실패했습니다. 다시 시도해주세요.');
	else if (kind == 'delete')
		alert('글 삭제가 실패했습니다. 다시 시도해주세요.');
	else if (kind == 'update')
		alert('글 수정이 실패했습니다. 다시 시도해주세요.');
	else
		alert('서버 에러 입니다. 다시 시도해주세요.');
	
	history.back();
});

</script>

</head>

<body>
</body>
</html>
