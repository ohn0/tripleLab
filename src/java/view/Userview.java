package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.*;
import model.user.*;

public class Userview {

    public static StringData extractPerson(ResultSet results) {
        StringData person = new StringData();
        try {
            person.id = FormatUtils.formatInteger(results.getObject("userID"));
            person.email = FormatUtils.formatString(results.getObject("userEmail"));
            person.password = FormatUtils.formatString(results.getObject("userPassword"));
            person.nickname = FormatUtils.formatString(results.getObject("userNickname"));
            person.rolename = FormatUtils.formatString(results.getObject("userRolename"));
        } catch (Exception e) {
            person.errorMsg = "Data Exception thrown in PersonView.extractPerson(): " + e.getMessage();
            System.out.println("*****" + person.errorMsg);
        }
        return person;
    }

    public static StringDataList buildPersonList(DbConn dbc) {

        StringDataList personList = new StringDataList();

        personList.dbError = dbc.getErr();
        if (personList.dbError.length() == 0) {

            String sql = "SELECT userEmail, userID, userPassword, userNickname, userRolename "
                    + "FROM userTable ORDER BY userID";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                ResultSet results = stmt.executeQuery();

                while (results.next()) {
                    personList.add(extractPerson(results));
                }
            } catch (Exception e) {
                personList.dbError = "SQL Excepption thrown in PersonView.BuildPersonList(): " + e.getMessage();
                System.out.println("*****" + personList.dbError);
            }
        }
        return personList;
    }

    public static StringData findPersonById(DbConn dbc, String id) {

        StringData person = new StringData();

        if (id == null) {
            person.errorMsg = "Cannot find person with null id.";
            return person;
        }

        person.errorMsg = dbc.getErr();
        if (person.errorMsg.length() == 0) {

            String sql = "SELECT userID, userEmail, userPassword, userNickname, "
                    + "userRolename FROM userTable WHERE userID=?";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, id);
                ResultSet results = stmt.executeQuery();

                if (results.next()) {
                    person = extractPerson(results);
                }
            } catch (Exception e) {
                person.errorMsg = "SQL Exception thrown in PersonView.BuildPerson(): " + e.getMessage();
                System.out.println("*****" + person.errorMsg);
            }
        }
        return person;
    }

}
