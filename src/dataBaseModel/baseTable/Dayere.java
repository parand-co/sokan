package dataBaseModel.baseTable;

import java.io.Serializable;

public class Dayere implements Serializable {
    private long id;
    private String code;
    private String title;
    private Paygah paygah;

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

    public Paygah getPaygah() {
        return paygah;
    }

    public void setPaygah(Paygah paygah) {
        this.paygah = paygah;
    }
}
