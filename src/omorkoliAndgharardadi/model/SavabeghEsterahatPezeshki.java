package omorkoliAndgharardadi.model;

import amar.model.Personel;

public class SavabeghEsterahatPezeshki {
    private long id;
    private Personel personel;
    private String namePezeshk;
    private String neshanPezeshk;
    private String elat;
    private int modat;
    private String tarikhShoro;
    private String tarikhPayan;
    private String marhale;
    private boolean taeed;  //taeed shode ya nashode
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

    public String getNamePezeshk() {
        return namePezeshk;
    }

    public void setNamePezeshk(String namePezeshk) {
        this.namePezeshk = namePezeshk;
    }

    public String getNeshanPezeshk() {
        return neshanPezeshk;
    }

    public void setNeshanPezeshk(String neshanPezeshk) {
        this.neshanPezeshk = neshanPezeshk;
    }

    public String getElat() {
        return elat;
    }

    public void setElat(String elat) {
        this.elat = elat;
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

    public String getMarhale() {
        return marhale;
    }

    public void setMarhale(String marhale) {
        this.marhale = marhale;
    }

    public boolean isTaeed() {
        return taeed;
    }

    public void setTaeed(boolean taeed) {
        this.taeed = taeed;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
    }
}
