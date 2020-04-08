package ezafekari.model;

import amar.model.Personel;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Taghvim;

public class EzafeKari {

    private long id;
    private Personel personel;
    private Taghvim tarikh;
    private Integer saatStart;
    private Integer saatEnd;
    private Integer realSaatStart;
    private Integer shomareDastgahStart;
    private Integer shomareDastgahEnd;
    private Integer realSaatEnd;
    private int modat;
    private long mablagh;
    private Sanad sanad;
    private String vaziat;
    private Bakhsh personelBakhsh;

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


    public Integer getSaatStart() {
        return saatStart;
    }

    public void setSaatStart(Integer saatStart) {
        this.saatStart = saatStart;
    }

    public Integer getSaatEnd() {
        return saatEnd;
    }

    public void setSaatEnd(Integer saatEnd) {
        this.saatEnd = saatEnd;
    }

    public int getModat() {
        return modat;
    }

    public void setModat(int modat) {
        this.modat = modat;
    }

    public long getMablagh() {
        return mablagh;
    }

    public void setMablagh(long mablagh) {
        this.mablagh = mablagh;
    }

    public Sanad getSanad() {
        return sanad;
    }

    public void setSanad(Sanad sanad) {
        this.sanad = sanad;
    }

    public Taghvim getTarikh() {
        return tarikh;
    }

    public void setTarikh(Taghvim tarikh) {
        this.tarikh = tarikh;
    }

    public String getVaziat() {
        return vaziat;
    }

    public void setVaziat(String vaziat) {
        this.vaziat = vaziat;
    }

    public Integer getRealSaatStart() {
        return realSaatStart;
    }

    public void setRealSaatStart(Integer realSaatStart) {
        this.realSaatStart = realSaatStart;
    }

    public Integer getRealSaatEnd() {
        return realSaatEnd;
    }

    public void setRealSaatEnd(Integer realSaatEnd) {
        this.realSaatEnd = realSaatEnd;
    }

    public Integer getShomareDastgahStart() {
        return shomareDastgahStart;
    }

    public void setShomareDastgahStart(Integer shomareDastgahStart) {
        this.shomareDastgahStart = shomareDastgahStart;
    }

    public Integer getShomareDastgahEnd() {
        return shomareDastgahEnd;
    }

    public void setShomareDastgahEnd(Integer shomareDastgahEnd) {
        this.shomareDastgahEnd = shomareDastgahEnd;
    }



    public Bakhsh getPersonelBakhsh() {
        return personelBakhsh;
    }

    public void setPersonelBakhsh(Bakhsh personelBakhsh) {
        this.personelBakhsh = personelBakhsh;
    }
}


