package ezafekari.model;

import amar.model.Personel;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;

public class Jire {

    private long id;
    private Personel personel;
    private int tedadjire;
    private String mah;
    private String sal;
    private String rooz;
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

    public int getTedadjire() {
        return tedadjire;
    }

    public void setTedadjire(int tedadjire) {
        this.tedadjire = tedadjire;
    }

    public String getMah() {
        return mah;
    }

    public void setMah(String mah) {
        this.mah = mah;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public String getRooz() {
        return rooz;
    }

    public void setRooz(String rooz) {
        this.rooz = rooz;
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
