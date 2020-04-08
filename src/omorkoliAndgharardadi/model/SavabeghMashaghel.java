package omorkoliAndgharardadi.model;

import amar.model.Personel;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Daraje;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.Yegan;

public class SavabeghMashaghel {
    private long id;
    private Personel personel;
    private Dayere dayere;  //emkan sabt dashteBashad
    private Bakhsh bakhsh;  //emkan sabt dashteBashad
    private String tarikhEntesab;
    private String tarikhEnfesal;
    private String titleShoghlSazmani;
    private String titleShoghlAmali;
    private String seri;
    private String band;
    private String satr;
    private Daraje daraje;
    private boolean vazeyatShagheli; //shaghel dar karkhanejat OR shaghel dar kharej az karkhanejat


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public Dayere getDayere() {
        return dayere;
    }

    public void setDayere(Dayere dayere) {
        this.dayere = dayere;
    }

    public Bakhsh getBakhsh() {
        return bakhsh;
    }

    public void setBakhsh(Bakhsh bakhsh) {
        this.bakhsh = bakhsh;
    }

    public String getTarikhEntesab() {
        return tarikhEntesab;
    }

    public void setTarikhEntesab(String tarikhEntesab) {
        this.tarikhEntesab = tarikhEntesab;
    }

    public String getTarikhEnfesal() {
        return tarikhEnfesal;
    }

    public void setTarikhEnfesal(String tarikhEnfesal) {
        this.tarikhEnfesal = tarikhEnfesal;
    }

    public String getTitleShoghlSazmani() {
        return titleShoghlSazmani;
    }

    public void setTitleShoghlSazmani(String titleShoghlSazmani) {
        this.titleShoghlSazmani = titleShoghlSazmani;
    }

    public String getTitleShoghlAmali() {
        return titleShoghlAmali;
    }

    public void setTitleShoghlAmali(String titleShoghlAmali) {
        this.titleShoghlAmali = titleShoghlAmali;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getSatr() {
        return satr;
    }

    public void setSatr(String satr) {
        this.satr = satr;
    }

    public Daraje getDaraje() {
        return daraje;
    }

    public void setDaraje(Daraje daraje) {
        this.daraje = daraje;
    }

    public boolean isVazeyatShagheli() {
        return vazeyatShagheli;
    }

    public void setVazeyatShagheli(boolean vazeyatShagheli) {
        this.vazeyatShagheli = vazeyatShagheli;
    }
}