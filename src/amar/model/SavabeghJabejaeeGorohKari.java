package amar.model;

import dataBaseModel.baseTable.Goroh;

public class SavabeghJabejaeeGorohKari {
    private long id;
    private Personel personel;
    private Goroh gorohJadid;
    private Goroh gorohGhabli;
    private String tarikhShoro;
    private String modat;
    private String tarikhPayan;
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

    public String getTarikhShoro() {
        return tarikhShoro;
    }

    public void setTarikhShoro(String tarikhShoro) {
        this.tarikhShoro = tarikhShoro;
    }

    public String getModat() {
        return modat;
    }

    public void setModat(String modat) {
        this.modat = modat;
    }

    public String getTarikhPayan() {
        return tarikhPayan;
    }

    public void setTarikhPayan(String tarikhPayan) {
        this.tarikhPayan = tarikhPayan;
    }

    public Goroh getGorohGhabli() {
        return gorohGhabli;
    }

    public void setGorohGhabli(Goroh gorohGhabli) {
        this.gorohGhabli = gorohGhabli;
    }

    public Goroh getGorohJadid() {
        return gorohJadid;
    }

    public void setGorohJadid(Goroh gorohJadid) {
        this.gorohJadid = gorohJadid;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
    }
}
