package dataBaseModel.baseTable;


import java.io.Serializable;

public class Taghvim implements Serializable  {

    private long id;
    private long code;
    private String tarikh;
    private String roozHafte; //shanbe 1shanbe 2shanbe ...
    private DayType dayType; //aaddi nimeTatil tatil ...

    private DayType dayTypeG1; //aaddi nimeTatil tatil ...
    private DayType dayTypeG2; //aaddi nimeTatil tatil ...
    private DayType dayTypeG3; //aaddi nimeTatil tatil ...
    private DayType dayTypeG4; //aaddi nimeTatil tatil ...
    private DayType dayTypeG5; //aaddi nimeTatil tatil ...
    private DayType dayTypeG6; //aaddi nimeTatil tatil ...
    private DayType dayTypeG7; //aaddi nimeTatil tatil ...

    private String tarikhMiladi;

    public Taghvim() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getRoozHafte() {
        return roozHafte;
    }

    public void setRoozHafte(String roozHafte) {
        this.roozHafte = roozHafte;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public String getTarikhMiladi() {
        return tarikhMiladi;
    }

    public void setTarikhMiladi(String tarikhMiladi) {
        this.tarikhMiladi = tarikhMiladi;
    }

    public DayType getDayTypeG1() {
        return dayTypeG1;
    }

    public void setDayTypeG1(DayType dayTypeG1) {
        this.dayTypeG1 = dayTypeG1;
    }

    public DayType getDayTypeG2() {
        return dayTypeG2;
    }

    public void setDayTypeG2(DayType dayTypeG2) {
        this.dayTypeG2 = dayTypeG2;
    }

    public DayType getDayTypeG3() {
        return dayTypeG3;
    }

    public void setDayTypeG3(DayType dayTypeG3) {
        this.dayTypeG3 = dayTypeG3;
    }

    public DayType getDayTypeG4() {
        return dayTypeG4;
    }

    public void setDayTypeG4(DayType dayTypeG4) {
        this.dayTypeG4 = dayTypeG4;
    }

    public DayType getDayTypeG5() {
        return dayTypeG5;
    }

    public void setDayTypeG5(DayType dayTypeG5) {
        this.dayTypeG5 = dayTypeG5;
    }

    public DayType getDayTypeG6() {
        return dayTypeG6;
    }

    public void setDayTypeG6(DayType dayTypeG6) {
        this.dayTypeG6 = dayTypeG6;
    }

    public DayType getDayTypeG7() {
        return dayTypeG7;
    }

    public void setDayTypeG7(DayType dayTypeG7) {
        this.dayTypeG7 = dayTypeG7;
    }
}
