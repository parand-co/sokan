package omorkoliAndgharardadi.model;

import com.google.gson.annotations.Expose;

public class EsterahatPezeshkiReportModel {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String neshan;
    @Expose
    private String daraje;
    @Expose
    private String dayere;
    @Expose
    private String ghesmat;
    @Expose
    private Integer modat;
    @Expose
    private String shoro;
    @Expose
    private String payan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeshan() {
        return neshan;
    }

    public void setNeshan(String neshan) {
        this.neshan = neshan;
    }

    public String getDaraje() {
        return daraje;
    }

    public void setDaraje(String daraje) {
        this.daraje = daraje;
    }

    public String getDayere() {
        return dayere;
    }

    public void setDayere(String dayere) {
        this.dayere = dayere;
    }

    public String getGhesmat() {
        return ghesmat;
    }

    public void setGhesmat(String ghesmat) {
        this.ghesmat = ghesmat;
    }

    public Integer getModat() {
        return modat;
    }

    public void setModat(Integer modat) {
        this.modat = modat;
    }

    public String getShoro() {
        return shoro;
    }

    public void setShoro(String shoro) {
        this.shoro = shoro;
    }

    public String getPayan() {
        return payan;
    }

    public void setPayan(String payan) {
        this.payan = payan;
    }
}