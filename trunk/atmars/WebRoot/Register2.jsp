<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Register2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   
    Please fulfill your personnel information<br>
    <s:form action="performRegister" method="post" enctype="multipart/form-data">
       <s:file name="upload" ></s:file>
       <s:textfield name="email" label="Email"></s:textfield>
       <s:password name="password" label="password"></s:password>
       <s:textfield name="nickname" label="nickname"></s:textfield>
       <s:textfield name="gender" label="gender"></s:textfield>
       <s:submit label="register now"></s:submit>
    </s:form>
  </body>
</html>
