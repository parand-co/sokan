package baseTable.model;

import dataBaseModel.baseTable.DayType;

public class RoozModel {

    private String rooz;
    private String hafteTitle;
    private DayType dayType;

    public String getRooz() {
        return rooz;
    }

    public void setRooz(String rooz) {
        this.rooz = rooz;
    }

    public String getHafteTitle() {
        return hafteTitle;
    }

    public void setHafteTitle(String hafteTitle) {
        this.hafteTitle = hafteTitle;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }
}
