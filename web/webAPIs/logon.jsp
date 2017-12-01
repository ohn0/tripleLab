<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@page language="java" import="com.google.gson.*" %>

<%@page import="dbUtils.DbConn"%>
<%@page import="logonUtils.Logon"%>      
<%@page import="model.user.StringData" %>  

<%
    
    // variables to persist user entered data and give logon success/failure message
    String strEmailAddress = "";
    String strUserPwd = "";
    String errorMsg = ""; // Logon Error message or Database Error message
    String welcomeMsg = "";
    Gson gson = new Gson();
    if ((strEmailAddress = request.getParameter("email")) != null) { // postback

//        strEmailAddress = request.getParameter("email");
        strUserPwd = request.getParameter("password");
        DbConn dbc = new DbConn();

        // put database connection error message to be displayed
        // it will be "" empty string if no error.
        errorMsg = dbc.getErr();
        if (errorMsg.length() == 0) { // no error message -- database connection worked 

            // pass in user's email address and password (along with open db connection)
            // to find method. The find method will return null if not found, else
            // it will return a customer StringData object. 
            StringData loggedOnCust = Logon.find(dbc, strEmailAddress, strUserPwd);
            if (loggedOnCust == null) {
                // Customer's credentials were not found in customer table in DB.
                errorMsg = "Invalid email address or password";
                try {
                    session.invalidate();
                } catch (Exception e) {
                    // don't care. If session was already invalidated, then I dont 
                    // need to do anything.
                }
            } else if (loggedOnCust.errorMsg.length() > 0) {
                // some exception was thrown in the find method, discover the error msg.
                // Normally would not get this unless program has a bug or unless the DB is down.
                out.print(gson.toJson(loggedOnCust).trim());
                try{
                    session.invalidate();
                } catch (Exception e){
                    
                }
                errorMsg = "Error " + loggedOnCust.errorMsg;

            } else {
                welcomeMsg = "Welcome " + loggedOnCust.email;
                out.print(gson.toJson(loggedOnCust).trim());
                // put object loggedOnCust into the session, giving it the name
                // "cust" (you need to use this name, later to pull the object
                // back out of the session.
                session.setAttribute("email", loggedOnCust);
                
                System.out.println("SETTING" + loggedOnCust);
            }
        }
    }
%>           
