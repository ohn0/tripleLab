<%@page import="model.user.StringData"%>
<%
    StringData user = (StringData) session.getAttribute("email");
    System.out.println("?");
    if(user != null){
        response.sendRedirect("../welcome.html?user="+user.email);
    }else{
        response.sendRedirect("../deny.html");
    }
%>