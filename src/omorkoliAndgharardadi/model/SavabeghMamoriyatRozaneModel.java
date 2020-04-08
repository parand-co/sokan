package omorkoliAndgharardadi.model;

import com.google.gson.annotations.Expose;

public class SavabeghMamoriyatRozaneModel {
    @Expose
    private String radif;
    @Expose
    private String shomarePersoneli;
    @Expose
    private String daraje;
    @Expose
    private String nam;
    @Expose
    private String famil;
    @Expose
    private String dayere;
    @Expose
    private String bakhsh;
    @Expose
    private String startDate;
    @Expose
    private String endDate;
    @Expose
    private String modat;

    public String getRadif() {
        return radif;
    }

    public void setRadif(String radif) {
        this.radif = radif;
    }

    public String getShomarePersoneli() {
        return shomarePersoneli;
    }

    public void setShomarePersoneli(String shomarePersoneli) {
        this.shomarePersoneli = shomarePersoneli;
    }

    public String getDaraje() {
        return daraje;
    }

    public void setDaraje(String daraje) {
        this.daraje = daraje;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getFamil() {
        return famil;
    }

    public void setFamil(String famil) {
        this.famil = famil;
    }

    public String getDayere() {
        return dayere;
    }

    public void setDayere(String dayere) {
        this.dayere = dayere;
    }

    public String getBakhsh() {
        return bakhsh;
    }

    public void setBakhsh(String bakhsh) {
        this.bakhsh = bakhsh;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getModat() {
        return modat;
    }

    public void setModat(String modat) {
        this.modat = modat;
    }
}
