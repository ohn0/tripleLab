<%@page contentType="application/json" pageEncoding="UTF-8"%> 
<%@page language="java" import="dbUtils.DbConn"%> 
<%@page language="java" import="model.user.*"%>

<%@page language="java" import="com.google.gson.*" %>

<%
    /*  http://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type 
     The MIME media type for JSON text is application/json. The default encoding is UTF-8. (Source: RFC 4627).
     */

    // This is the object we get from the GSON library.
    Gson gson = new Gson();

    DbConn dbc = new DbConn();
    StringData errorMsgs = new StringData();
    String jsonInsertData = request.getParameter("jsonData");
    if (jsonInsertData == null) {
        errorMsgs.errorMsg = "Cannot insert -- no data was received";
        System.out.println(errorMsgs.errorMsg);
    } else {
        System.out.println("jsonInsertData is " + jsonInsertData);
        errorMsgs.errorMsg = dbc.getErr();
        if (errorMsgs.errorMsg.length() == 0) { // means db connection is ok
            System.out.println("userInsert.jsp ready to insert");
            StringData insertData = gson.fromJson(jsonInsertData, StringData.class);
            System.out.println("jsonInsertData is " + insertData.toString());

            errorMsgs = TableMods.insert(insertData, dbc); // this is the form level message
        }
    }

    out.print(gson.toJson(errorMsgs).trim());
    dbc.close();

%>