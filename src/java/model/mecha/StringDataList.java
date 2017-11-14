package model.mecha;

import java.util.ArrayList;

public class StringDataList {

    public String dbError = "";
    public ArrayList<StringData> userList = new ArrayList();

    public StringDataList() {
    }
    


    public void add(StringData stringData) {
        this.userList.add(stringData);
    }
}
