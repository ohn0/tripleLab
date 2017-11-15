package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.*;
import model.mechaTable.*;

public class Mechaview {

    public static StringData extractPerson(ResultSet results) {
        StringData mecha = new StringData();
        try {
            mecha.mechaDescriptor = FormatUtils.formatInteger(results.getObject("mechaDescriptor"));
            mecha.mechaURL = FormatUtils.formatString(results.getObject("mechaURL"));
            mecha.mechaName = FormatUtils.formatString(results.getObject("mechaName"));
            mecha.mechaHeight = FormatUtils.formatString(results.getObject("mechaHeight"));
        } catch (Exception e) {
            mecha.errorMsg = "Data Exception thrown in PersonView.extractPerson(): " + e.getMessage();
            System.out.println("*****" + mecha.errorMsg);
        }
        return mecha;
    }

    public static StringDataList buildPersonList(DbConn dbc) {

        StringDataList mechaList = new StringDataList();

        mechaList.dbError = dbc.getErr();
        if (mechaList.dbError.length() == 0) {

            String sql = "SELECT userID, userPassword, userNickname FROM userTable ORDER BY userID";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                ResultSet results = stmt.executeQuery();

                while (results.next()) {
                    mechaList.add(extractPerson(results));
                }
            } catch (Exception e) {
                mechaList.dbError = "SQL Excepption thrown in PersonView.BuildPersonList(): " + e.getMessage();
                System.out.println("*****" + mechaList.dbError);
            }
        }
        return mechaList;
    }

    public static StringData findPersonById(DbConn dbc, String id) {

        StringData mecha = new StringData();

        if (id == null) {
            mecha.errorMsg = "Cannot find mecha with null id.";
            return mecha;
        }

        mecha.errorMsg = dbc.getErr();
        if (mecha.errorMsg.length() == 0) {

            String sql = "SELECT userID, userEmail, userPassword, userNickname, "
                    + "userRolename FROM userTable ORDER BY userID";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, id);
                ResultSet results = stmt.executeQuery();

                if (results.next()) {
                    mecha = extractPerson(results);
                }
            } catch (Exception e) {
                mecha.errorMsg = "SQL Exception thrown in PersonView.BuildPerson(): " + e.getMessage();
                System.out.println("*****" + mecha.errorMsg);
            }
        }
        return mecha;
    }

}
