<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="dbUtils.*"%> 
<%@page language="java" import="view.Userview"%>
<%@page language="java" import="model.user.*"%>

<%@page language="java" import="com.google.gson.*" %>

<%
    /*  http://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type 
     The MIME media type for JSON text is application/json. The default encoding is UTF-8. (Source: RFC 4627).
     */

    DbConn dbc = new DbConn();
    
    // buildCustomerList will check for db connection errors (and put error message into personList).
    StringData cust = (StringData) session.getAttribute("cust");
    
//    System.out.println(cust.toString());
    if(cust != null){
        cust = null;
        StringDataList customerList = Userview.buildUserList(dbc);
        Gson gson = new Gson();
        System.out.println("||||"+customerList.toString());
        out.print(gson.toJson(customerList).trim());
    }
    else{
        out.print("wtf");
    }
    // PREVENT DB connection leaks:
    dbc.close(); // EVERY code path that opens a db connection, must also close it.

%>