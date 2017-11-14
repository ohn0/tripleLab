package dbUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

/** Formats various data types. Most methods wrap formatted data with an HTML <td>
tag. When wrapping in <td> tag, substitute "&nbsp;" (HTML non-breaking space) 
for empty string. If this was not done, the cell in the HTML table will not  
show its border and usually would not look right. */
public class FormatUtils {

    // DecimalFormat percentFormat = new DecimalFormat("%###.##");
    // Turns a date into a nicely formatted String.
    public static String formatDate(Object obj) {
        String out = "";
        if (obj == null) {
            return out;
        }
        try {
            java.util.Date dateval = (java.util.Date) obj;
            if (dateval != null) {
                SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
                dateformat.setLenient(false);
                out = dateformat.format(dateval);
            }
        } catch (Exception e) {
            out = "bad date in FormatUtils.formatDate: " + obj.toString() + " error: " + e.getMessage();
        }
        return out;
    } // formatDate

    public static String formatDateTd(Object obj) {
        String out = "<td style='text-align:center'>";
        String strDate = formatDate(obj);
        if (strDate.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strDate;
        }
        out += "</td>";
        return out;
    } // formatDateTd

    public static String formatDollar(Object obj) {
        // null gets converted to empty string
        String out = "";
        if (obj == null) {
            return out;
        }
        BigDecimal bd = (BigDecimal) obj;
        try {
            DecimalFormat intFormat = new DecimalFormat("$###,###,###,##0.00");
            out += intFormat.format(bd);
        } catch (Exception e) {
            out += "bad Dollar Amount in FormatUtils:" + obj.toString() + " Error:" + e.getMessage();
        }
        return out;
    } // formatDollar

    public static String formatDollarTd(Object obj) {
        String out = "<td style='text-align:right'>";
        String strDollarAmt = formatDollar(obj);
        if (strDollarAmt.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strDollarAmt;
        }
        out += "</td>";
        return out;
    } // formatDollarTd

    public static String formatInteger(Object obj) {
        String out = "";
        if (obj == null) {
            return out;
        } else {
            try {
                Integer ival = (Integer) obj;
                DecimalFormat intFormat = new DecimalFormat("###,###,###,##0");
                out += intFormat.format(ival);
            } catch (Exception e) {
                out += "bad Integer in FormatUtils:" + obj.toString() + " Error:" + e.getMessage();
            }
        }
        return out;
    } // formatInteger

    public static String formatIntegerTd(Object obj) {
        String out = "<td style='text-align:right'>";
        String strInteger = formatInteger(obj);
        if (strInteger.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strInteger;
        }
        out += "</td>";
        return out;
    } // formatIntegerTd

    // this is not really formatting, but just converting to string type.
    public static String formatString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return (String) obj;
        }
    } // formatString

    public static String formatStringTd(Object obj) {
        String out = "<td style='text-align:left'>";
        String str = formatString(obj);
        if (str.length() == 0) {
            out += "&nbsp;";
        } else {
            out += str;
        }
        out += "</td>";
        return out;
    } // formatString

    public static String objectToString(Object o) {
        if (o == null) {
            return "";
        }
        try {
            return o.toString();
        } catch (Exception e) {
            return "Exception converting object to string FormatUtils.objectToString(): " + e.getMessage();
        }
    }
    
    public static String doubleQuoted(String str) {
        return "\"" + str + "\"";
    }
    
} // FormatUtils class