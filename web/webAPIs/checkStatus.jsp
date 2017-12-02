<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.user.StringData" %>   
<%@page import="logonUtils.Logon" %>   
<%@page language="java" import="com.google.gson.*" %>


<%
    StringData userData = (StringData) session.getAttribute("email");
    if(userData == null){
        System.out.println("Null user data when checking status.");
    }else{
        Gson gson = new Gson();
        out.print(gson.toJson(userData).trim());
    }
%>