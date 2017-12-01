<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="view.Userview" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="simpleStyle.css" rel="stylesheet" type="text/css" />
        <style>
            td {
                background-color: #CCCCCC;
            }
        </style>
    </head>
    <body>

        <%
            String msg = "";

            // try to get database connection and then check if it worked
            // DbConn.getErr() returns empty String "" if no there was no db connection error.
            DbConn dbc = new DbConn();
            msg = dbc.getErr();
            if (msg.length() == 0) { // got open database connection OK

                // now print out the whole table
                msg = Userview.customersByName("resultSetFormat", dbc);

                // PREVENT DB connection leaks:
                // EVERY code path that opens a db connection, must also close it.
                dbc.close();
            }
        %>

        <jsp:include page="links.jsp" />      

        <h2>Customer List</h2>
        <%=msg%>
    </body>
</html>

