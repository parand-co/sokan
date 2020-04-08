package dataBaseModel.baseTable;

import java.io.Serializable;

public class Daraje implements Serializable {
    private long id;
    private String code;
    private String title;
    private long saghfEzfkar;

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

    public long getSaghfEzfkar() {
        return saghfEzfkar;
    }

    public void setSaghfEzfkar(long saghfEzfkar) {
        this.saghfEzfkar = saghfEzfkar;
    }
}
