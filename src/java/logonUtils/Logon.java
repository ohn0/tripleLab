package logonUtils;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.user.StringData;

public class Logon {

    // return the first part of the email address, the part that is 
    // before the "." or the "@" (whichever comes first) -- or (if there
    // is no "." and no "@", just return the whole emailAddress back as the 
    // nickName. 
    public static String makeNickName(String emailAddress) {

        // this is to help while debugging (to avoid the "null pointer exception"
        if (emailAddress == null) {
            return "Can't make nickname from null email Address.";
        }

        int nickNameLength = emailAddress.length();

        // create nickname as email address without the "@..."
        int atPosition = emailAddress.indexOf("@"); // value is -1 if not found
        if (atPosition > 0) {
            nickNameLength = atPosition;
        }

        // create nickname as what's before a period 
        int periodPosition = emailAddress.indexOf(".");
        if ((periodPosition > 0) && (periodPosition < nickNameLength)) {
            nickNameLength = periodPosition;
        }

        // return the first part of the emailAddress (up to right before the "." and/or the "@"
        return (emailAddress.substring(0, nickNameLength));

    } // makeNickName

    // Return a partially populated Customer StringData object if a Customer
    // can be found (in the database) with the given emailAddress, and userPwd.
    // We do not populate all the fields, since they are not all needed for 
    // the purposes of logging in. 
    // If such a customer record cannot be found, return null. 
    // If there is a problem with the database (any kind of exception thrown), 
    // return a Customer StringData object with the errorMsg field containing 
    // the database exception error message (and all other fields empty string).
    public static StringData find(DbConn dbc, String emailAddress, String userPwd) {

        StringData foundCust = new StringData(); // default constructor sets all fields to "" (empty string)

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            System.out.println("*** before preparing statement");

            String sql = "select UserPassword, UserEmail, UserNickname "
                    + "from userTable where UserEmail= ? and UserPassword= ?";

            stmt = dbc.getConn().prepareStatement(sql);
            System.out.println("*** statement prepared- no sql compile errors");

            // this puts the user's input (from variable emailAddress)
            // into the 1st question mark of the sql statement above.
            stmt.setString(1, emailAddress);
            System.out.println("*** email address substituted into the sql");

            // this puts the user's input (from variable userPwd)
            // into the 2nd question mark of the sql statement above.
            stmt.setString(2, userPwd);
            System.out.println("*** pwd substituted into the sql");
            System.out.println("*** searching for " + emailAddress + ' ' + userPwd);
            results = stmt.executeQuery();
            System.out.println("*** query executed");

            // since the email address is required (in database) to be unique, 
            // we don't need a while loop like we did for the display data lab.
            if (results.next()) {
                System.out.println("*** record selected");
                foundCust.email = results.getObject("UserEmail").toString();
                foundCust.password = results.getObject("UserPassword").toString(); // we can take this from input parameter instead of db.
//                foundCust.creditLimit = FormatUtils.formatDollar(results.getObject("credit_limit"));
                foundCust.nickname = results.getObject("UserNickname").toString();
                System.out.println("*** 3 fields extracted from result set");
                results.close();
                stmt.close();
                return foundCust;
            } else {
                System.out.println("Login info not found.");
                return null; // means customer not found with given credentials.
            }
        } catch (Exception e) {
            foundCust.errorMsg = "Exception thrown in logOn.LogonUtils.find(): " + e.getMessage();
            return foundCust;
        }
    }
}
