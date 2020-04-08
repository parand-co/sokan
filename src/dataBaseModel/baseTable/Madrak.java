package dataBaseModel.baseTable;

import java.io.Serializable;

public class Madrak implements Serializable {

    private long id;
    private String code;
    private String title;
    private long makhaz;

    public Madrak() {
    }

    public Madrak(long madrakID) {
        this.id = madrakID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMakhaz() {
        return makhaz;
    }

    public void setMakhaz(long makhaz) {
        this.makhaz = makhaz;
    }
}
