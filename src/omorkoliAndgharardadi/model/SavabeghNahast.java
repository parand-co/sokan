package omorkoliAndgharardadi.model;

import amar.model.Personel;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;

public class SavabeghNahast {
    private long id;
    private Personel personel;
    private int modat;
    private String tarikhShoro;
    private String tarikhPayan;
    private String tanbih;
    private String marhale;
    private String molahezat;
    private String tarikhSabt;
    private Dayere dayere;
    private Bakhsh bakhsh;





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

    public int getModat() {
        return modat;
    }

    public void setModat(int modat) {
        this.modat = modat;
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

    public String getTanbih() {
        return tanbih;
    }

    public void setTanbih(String tanbih) {
        this.tanbih = tanbih;
    }

    public String getMarhale() {
        return marhale;
    }

    public void setMarhale(String marhale) {
        this.marhale = marhale;
    }

    public String getMolahezat() {
        return molahezat;
    }

    public void setMolahezat(String molahezat) {
        this.molahezat = molahezat;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
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
}
