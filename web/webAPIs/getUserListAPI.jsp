<%@page import="model.user.StringDataList"%>
<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.user.*" %>
<%@page language="java" import="view.Userview" %>
<%@page language="java" import="java.util.ArrayList" %>

<%@page language="java" import="com.google.gson.*" %>

<%

    StringDataList list = new StringDataList();
    Userview users = new Userview();
    DbConn dbc = new DbConn();
    list.dbError = dbc.getErr(); // returns "" if connection is good, else db error msg.

    if (list.dbError.length() == 0) { // got open connection 

        list = users.buildPersonList(dbc);
    } 

    // PREVENT DB connection leaks:
    dbc.close(); // EVERY code path that opens a db connection, must also close it.

    Gson gson = new Gson();
    out.print(gson.toJson(list).trim()); 
%>