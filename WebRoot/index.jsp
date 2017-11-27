<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="pojo.SNSUserInfo,java.lang.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>网页授权</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 <style type="text/css">
        *{margin:0; padding:0}
        table{border:1px dashed #B9B9DD;font-size:12pt}
        td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
    </style>
  </head>
  
  <body>
    <%
    	//获取由OAuthServlet中传入的参数
    	SNSUserInfo user=(SNSUserInfo)request.getAttribute("snsUserInfo");
    	String state=request.getAttribute("state").toString();
    	if(null!=user){
     %>
     <table>
     	<tr><td width="20%">属性</td><td width="80%">值</td></tr>
     	<tr><td>OpenID</td><td><%=user.getOpenId() %></td></tr>
     	<tr><td>昵称</td><td><%=user.getNickname() %></td></tr>
     	<tr><td>性别</td><td><%=user.getSex() %></td></tr>
     	<tr><td>国家</td><td><%=user.getCountry() %></td></tr>
     	<tr><td>省份</td><td><%=user.getProvince() %></td></tr>
     	<tr><td>城市</td><td><%=user.getCity() %></td></tr>
     	<tr><td>头像</td><td><%=user.getHeadImgUrl() %></td></tr>
     	<tr><td>特权</td><td><%=user.getPrivilegeList() %></td></tr>
     	<tr><td>state:</td><td><%=state %></td></tr>
     </table>
     <%
     	}
     	else
     		out.println("用户不同意授权，未获取到用户信息！");
      %>
  </body>
</html>
