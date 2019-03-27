<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <jsp:useBean id="dao" class="data.MemberDao"/>
    <jsp:useBean id="vo" class="data.MemberVo"/>
 <jsp:setProperty property="*" name="vo"/>
<%
 int rst = 0;
 request.setCharacterEncoding("euc-kr");
 rst = dao.insertMember(vo);
 if(rst>0){
%>
<script type="text/javascript">
alert("Membership success");
location.href="http://localhost:8080/hihi/index.html"; 
</script>
<%}else{ %>
<script type="text/javascript">
alert("Failed membership");
history.go(-1);
location.href="http://localhost:8080/hihi/Signup.html"; 
</script>
<%} %>