package omorkoliAndgharardadi.model;

import amar.model.Personel;
import dataBaseModel.baseTable.Tabaghe;

public class SavabeghTabaghePezeshki {
    private long id;
    private Personel personel;
    private Tabaghe sath;
    private String sharh;
    private String tarikhShoro;
    private String tarikhPayan;
    private int modat;
    private String tarikhSabt;


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

    public String getSharh() {
        return sharh;
    }

    public void setSharh(String sharh) {
        this.sharh = sharh;
    }

    public Tabaghe getSath() {
        return sath;
    }

    public void setSath(Tabaghe sath) {
        this.sath = sath;
    }

    public String getTarikhShoro() {
        return tarikhShoro;
    }

    public void setTarikhShoro(String tarikhShoro) {
        this.tarikhShoro = tarikhShoro;
    }

    public String getTarikhPayan() {
        return tarikhPayan;
    }

    public void setTarikhPayan(String tarikhPayan) {
        this.tarikhPayan = tarikhPayan;
    }

    public int getModat() {
        return modat;
    }

    public void setModat(int modat) {
        this.modat = modat;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
    }
}
