<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title><!-- 로그인 성공, 실패, 로그아웃 메시지 알림 -->
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script>
	$(document).ready(function(){
		$("#btnLogin").click(function(){
			//태그.val() : 태그에 입력된 값
			//태그.val("값") : 태그의 값을 변견
			var userId=$("#userId").val();
			var userPw=$("#userPw").val();
			if(userId==""){
				alert("아이디를 입력하세요");
				$("#userId").focus(); //입력 포커스 이동
				return; //함수 종료
			}
			if(userPw==""){
				alert("비밀번호를 입력하세요");
				$("#userPw").focus();
				return;
			}
			//폼 내부의 데이터를 전송할 주소
			document.form1.action="${path }/member/login_ok.do";
			//제출
			document.form1.submit();
		});
	});
</script>
</head>
<body>
<h2>로그인</h2><!-- action="${path }/member/login_ok.do" -->
<form name="form1" method="post" >
	<table border="1" width="400px">
		<tr>
			<td>아이디</td>
			<td><input name="userId" id="userId"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="userPw" id="userPw"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="button" id="btnLogin">로그인</button>
<%-- 				<c:if test="${msg=='failure' }">
					<div style="color:red">아이디 또는 비밀번호가 일치하지 않습니다</div>
				</c:if>
				<c:if test="${msg=='logout' }">
					<div style="color:red">로그아웃 되었습니다</div>
				</c:if> --%>
			</td>
		</tr>
		
		<div id="naver_id_login">naver_id_login</div>
		
	</table>
</form>

	<!-- "네이버 아이디로 로그인" 버튼 노출 영역 -->
	<script type="text/javascript">
 		var naver_id_login = new naver_id_login("p1SdIeQnCgBNAcOrb_fu", "http://localhost:3306/member/loginNaver");
 		var state = naver_id_login.getUniqState();
		
 		naver_id_login.setButton("green", 3, 40);
 		naver_id_login.setDomain("http://localhost:3306/member/loginGET");
 		naver_id_login.setState(state);
 		naver_id_login.init_naver_id_login();
	</script>
	
</body>
</html>