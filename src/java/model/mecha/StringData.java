package model.mecha;


/* The purpose of this class is just to "bundle together" all the 
 * character data that the user might type in when they want to 
 * add a new Customer or edit an existing customer.  This String
 * data is "pre-validated" data, meaning they might have typed 
 * in a character string where a number was expected.
 * 
 * There are no getter or setter methods since we are not trying to
 * protect this data in any way.  We want to let the JSP page have
 * free access to put data in or take it out. */
public class StringData {

    public String mechaName = "";
//    public String id = "";
    public String mechaHeight= "";
    public String mechaDescriptor = "";
    public String mechaImgURL= "";
    public String errorMsg = "";

    // default constructor leaves all data members with empty string.
    public StringData() {

    }

    public int getCharacterCount() {
        String s =  this.mechaName + this.mechaHeight + this.mechaDescriptor +
                   this.mechaImgURL + this.errorMsg ;
        return s.length();
    }
}
