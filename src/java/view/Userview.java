package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.*;
import model.user.*;

public class Userview {

    public static StringData extractUser(ResultSet results) {
        StringData user = new StringData();
        try {
            user.id = FormatUtils.formatInteger(results.getObject("userID"));
            user.email = FormatUtils.formatString(results.getObject("userEmail"));
            user.password = FormatUtils.formatString(results.getObject("userPassword"));
            user.nickname = FormatUtils.formatString(results.getObject("userNickname"));
            user.rolename = FormatUtils.formatString(results.getObject("userRolename"));
        } catch (Exception e) {
            user.errorMsg = "Data Exception thrown in Userview.extractUser(): " + e.getMessage();
            System.out.println("*****" + user.errorMsg);
        }
        return user;
    }

    public static StringDataList buildUserList(DbConn dbc) {

        StringDataList userList = new StringDataList();

        userList.dbError = dbc.getErr();
        if (userList.dbError.length() == 0) {

            String sql = "SELECT userEmail, userID, userPassword, userNickname, userRolename "
                    + "FROM userTable ORDER BY userID";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                ResultSet results = stmt.executeQuery();

                while (results.next()) {
                    userList.add(extractUser(results));
                }
            } catch (Exception e) {
                userList.dbError = "SQL Excepption thrown in Userview.BuildUserList(): " + e.getMessage();
                System.out.println("*****" + userList.dbError);
            }
        }
        return userList;
    }
    
    public static String usersByName(String cssClassForResultSetTable, DbConn dbc) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");

            // String sql = "select last_name, first_name, customer_id, address, city, state, zip, credit_limit "+
            //       "from customer order by last_name, first_name";
            String sql = "select customer_id, first_name, last_name, email_address, pwd, "
                    + " address, city, state, zip, credit_limit "
                    + " from customer order by last_name, first_name";

            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssClassForResultSetTable);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'>Email</th>");
            sb.append("<th style='text-align:left'>Password</th>");
            sb.append("<th style='text-align:left'>First Name</th>");
            sb.append("<th style='text-align:left'>Last Name</th>");
            sb.append("<th style='text-align:right'>Credit Limit</th>");
            sb.append("</tr>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append(FormatUtils.formatStringTd(results.getObject("email_address")));
                sb.append(FormatUtils.formatStringTd(results.getObject("pwd")));
                sb.append(FormatUtils.formatStringTd(results.getObject("first_name")));
                sb.append(FormatUtils.formatStringTd(results.getObject("last_name")));
                sb.append(FormatUtils.formatDollarTd(results.getObject("credit_limit")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in CustomerView.CustomersByName(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }


    public static StringData findUserById(DbConn dbc, String id) {

        StringData user = new StringData();

        if (id == null) {
            user.errorMsg = "Cannot find person with null id.";
            return user;
        }

        user.errorMsg = dbc.getErr();
        if (user.errorMsg.length() == 0) {

            String sql = "SELECT userID, userEmail, userPassword, userNickname, "
                    + "userRolename FROM userTable WHERE userID=?";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, id);
                ResultSet results = stmt.executeQuery();

                if (results.next()) {
                    user = extractUser(results);
                }
            } catch (Exception e) {
                user.errorMsg = "SQL Exception thrown in Userview.Builduser(): " + e.getMessage();
                System.out.println("*****" + user.errorMsg);
            }
        }
        return user;
    }

}
