<%@page import="org.atmars.dao.Message"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<count><s:property value="qiangCount" /></count>
<%

	java.util.List l=(java.util.List)request.getAttribute("qiang");
	int i = 0;
	while(i<l.size())
	{
 %>
	<messageId><%=((Message)l.get(i)).getMessageId() %></messageId>
	<image><%=((Message)l.get(i)).getImage() %></image>
	<text><%=((Message)l.get(i)).getText() %><text>
	<%
	
	i++;
	}
	
	
	 %>
