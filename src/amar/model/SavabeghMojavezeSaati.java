package amar.model;

import dataBaseModel.baseTable.MojavezSaati;

public class SavabeghMojavezeSaati {

    private long id;
    private Personel personel;
    private MojavezSaati mojavezSaati;
    private String tarikh;
    private String zaman;


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

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public MojavezSaati getMojavezSaati() {
        return mojavezSaati;
    }

    public void setMojavezSaati(MojavezSaati mojavezSaati) {
        this.mojavezSaati = mojavezSaati;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }
}
