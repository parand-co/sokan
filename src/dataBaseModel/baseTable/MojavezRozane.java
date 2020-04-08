package dataBaseModel.baseTable;

import java.io.Serializable;

public class MojavezRozane implements Serializable {
    private long id;
    private String code;
    private String title;
    private boolean roozaneOrSaati;
    private String roozaneSaatiTitle;

    public MojavezRozane() {
    }

    public MojavezRozane(long id) {
        this.id = id;
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


    public boolean isRoozaneOrSaati() {
        return roozaneOrSaati;
    }

    public void setRoozaneOrSaati(boolean roozaneOrSaati) {
        this.roozaneOrSaati = roozaneOrSaati;
    }

    public String getRoozaneSaatiTitle() {
        return roozaneSaatiTitle;
    }

    public void setRoozaneSaatiTitle(String roozaneSaatiTitle) {
        this.roozaneSaatiTitle = roozaneSaatiTitle;
    }
}
