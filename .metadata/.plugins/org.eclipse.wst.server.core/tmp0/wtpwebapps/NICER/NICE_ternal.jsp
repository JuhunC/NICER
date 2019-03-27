<%@ page language="java" contentType="text/html; charset=EUC-KR"
 pageEncoding="EUC-KR"%>
 <%
 String top = (String)request.getAttribute("top");
 String login = (String)request.getAttribute("login");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01

 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>NICE</title>
</head>
<body>
<script >
if("<%= login %>"=="no") alert("You have to log in to use that.");
</script>
    <jsp:include page="<%=top %>"/> 
</body>
</html>

