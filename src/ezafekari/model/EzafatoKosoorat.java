package ezafekari.model;

import amar.model.Personel;

public class EzafatoKosoorat {

    private long id;
    private Personel personel;
    private int saatEK;
    private String sharh;
    private boolean ezafOrKasr;
    private Sanad sanad;


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

    public boolean isEzafOrKasr() {
        return ezafOrKasr;
    }

    public void setEzafOrKasr(boolean ezafOrKasr) {
        this.ezafOrKasr = ezafOrKasr;
    }

    public Sanad getSanad() {
        return sanad;
    }

    public void setSanad(Sanad sanad) {
        this.sanad = sanad;
    }

    public int getSaatEK() {
        return saatEK;
    }

    public void setSaatEK(int saatEK) {
        this.saatEK = saatEK;
    }
}
