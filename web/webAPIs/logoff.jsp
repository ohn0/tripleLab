<% 
    session.invalidate();
    try {
        response.sendRedirect("htmlPartials/mechaList.html");
    } catch (Exception e) {
        System.out.println("**** Exception was thrown in logoff.jsp: " + e.getMessage());
    }
%>