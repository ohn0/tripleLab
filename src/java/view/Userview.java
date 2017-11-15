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
