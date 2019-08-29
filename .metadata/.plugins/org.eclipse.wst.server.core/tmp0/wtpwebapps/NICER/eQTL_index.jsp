<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
 String loginId = (String)session.getAttribute("loginId");
	request.setAttribute("top", "/eQTL.html");
	request.setAttribute("login", "ok");
 /*
 if(loginId==null){
  request.setAttribute("top", "/loginForm.jsp");
  request.setAttribute("login", "no");
 }else{
  request.setAttribute("top", "/eQTL.html");
  request.setAttribute("login", "ok");
 }
*/
%>
<jsp:forward page="/eQTL_ternal.jsp"/>