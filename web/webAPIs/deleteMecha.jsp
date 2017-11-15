<%@page contentType="application/json" pageEncoding="UTF-8"%> 
<%@page language="java" import="dbUtils.DbConn"%> 
<%@page language="java" import="model.mecha.*"%> 

<%@page language="java" import="com.google.gson.*" %>

<%
    /*  http://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type 
     The MIME media type for JSON text is application/json. The default encoding is UTF-8. (Source: RFC 4627).
     */

    // This is the object we get from the GSON library.
    Gson gson = new Gson();

    DbConn dbc = new DbConn();
    String deleteId = request.getParameter("id");
    System.out.println("ready to delete person "+deleteId);
    
    // just so we have an actual pojo (plain old java object)
    // to convert to json. 
    StringData person = new StringData();
    
    person.errorMsg = "";

    if (deleteId == null) {
        person.errorMsg = "Cannot delete -- no id was received";
    } else {
        person.errorMsg = dbc.getErr();
        if (person.errorMsg.length() == 0) { // means db connection is ok
            //System.out.println("personDelete.jsp ready to delete id "+deleteId);
            person.errorMsg = TableMods.deleteById(deleteId, dbc);
        }
    }
    System.out.println("result of that delete is: "+person.errorMsg+"(empty string means worked)");
    out.print(gson.toJson(person));
    dbc.close();
%>