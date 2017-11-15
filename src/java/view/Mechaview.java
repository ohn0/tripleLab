package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.*;
import model.mecha.*;

public class Mechaview {

    public static StringData extractMecha(ResultSet results) {
        StringData mecha = new StringData();
        try {
            mecha.mechaDescriptor = FormatUtils.formatString(results.getObject("mechaDescriptor"));
            mecha.mechaImgURL = FormatUtils.formatString(results.getObject("mechaURL"));
            mecha.mechaName = FormatUtils.formatString(results.getObject("mechaName"));
            mecha.mechaHeight = FormatUtils.formatInteger(results.getObject("mechaHeight"));
            mecha.mechaID = FormatUtils.formatInteger(results.getObject("mechaTable_ID"));
        } catch (Exception e) {
            mecha.errorMsg = "Data Exception thrown in Mechaview.extractMecha(): " + e.getMessage();
            System.out.println("*****" + mecha.errorMsg);
        }
        return mecha;
    }

    public static StringDataList buildMechaList(DbConn dbc) {

        StringDataList mechaList = new StringDataList();

        mechaList.dbError = dbc.getErr();
        if (mechaList.dbError.length() == 0) {

            String sql = "SELECT mechaTable_ID, mechaDescriptor, mechaName, mechaURL, FROM mechaTable ORDER BY mechaTable_ID";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                ResultSet results = stmt.executeQuery();

                while (results.next()) {
                    mechaList.add(extractMecha(results));
                }
            } catch (Exception e) {
                mechaList.dbError = "SQL Excepption thrown in Mechaview.buildMechaList(): " + e.getMessage();
                System.out.println("*****" + mechaList.dbError);
            }
        }
        return mechaList;
    }

    public static StringData findMechaByID(DbConn dbc, String id) {

        StringData mecha = new StringData();

        if (id == null) {
            mecha.errorMsg = "Cannot find mecha with null id.";
            return mecha;
        }

        mecha.errorMsg = dbc.getErr();
        if (mecha.errorMsg.length() == 0) {

            String sql = "SELECT mechaTable_ID, mechaDescriptor, mechaHeight, mechaURL, mechaName "
                    + "FROM mechaTable WHERE mechaTable_ID = ?";

            try {
                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, id);
                ResultSet results = stmt.executeQuery();

                if (results.next()) {
                    mecha = extractMecha(results);
                }
            } catch (Exception e) {
                mecha.errorMsg = "SQL Exception thrown in Mechaview.findMecha(): " + e.getMessage();
                System.out.println("*****" + mecha.errorMsg);
            }
        }
        return mecha;
    }

}
