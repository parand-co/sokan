package ezafekari.model;

import com.google.gson.annotations.Expose;

public class HosnAnjamKarModel {

    @Expose
    private String radif;
    @Expose
    private String shp;
    @Expose
    private String nameFamily;
    @Expose
    private String project;
    @Expose
    private String startTime;
    @Expose
    private String startDastgah;
    @Expose
    private String endTime;
    @Expose
    private String endDastgah;
    @Expose
    private String karkard;

    public String getRadif() {
        return radif;
    }

    public void setRadif(String radif) {
        this.radif = radif;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDastgah() {
        return startDastgah;
    }

    public void setStartDastgah(String startDastgah) {
        this.startDastgah = startDastgah;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDastgah() {
        return endDastgah;
    }

    public void setEndDastgah(String endDastgah) {
        this.endDastgah = endDastgah;
    }

    public String getKarkard() {
        return karkard;
    }

    public void setKarkard(String karkard) {
        this.karkard = karkard;
    }
}