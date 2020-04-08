package dataBaseModel.baseTable;

import java.io.Serializable;

public class DayType implements Serializable {

    private long id;
    private String type;
    private String code;

    public DayType() {
    }

    public DayType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
