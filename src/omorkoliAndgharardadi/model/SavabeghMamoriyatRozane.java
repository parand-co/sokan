package omorkoliAndgharardadi.model;

import amar.model.Personel;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.MojavezRozane;

public class SavabeghMamoriyatRozane {
    private long id;
    private Personel personel;
    private String tarikhShoro;
    private String tarikhPayan;
    private String saatShoro;
    private String saatEnd;
    private int modat;
    private String modatHours;
    private String modatMinutes;
    private MojavezRozane noeMamoriyat;
    private String tarikhSabt;
    private String mahaleMamoriyat;
    private Dayere dayere;
    private Bakhsh bakhsh;
    private int tamdid;




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

    public String getTarikhPayan() {
        return tarikhPayan;
    }

    public void setTarikhPayan(String tarikhPayan) {
        this.tarikhPayan = tarikhPayan;
    }

    public MojavezRozane getNoeMamoriyat() {
        return noeMamoriyat;
    }

    public void setNoeMamoriyat(MojavezRozane noeMamoriyat) {
        this.noeMamoriyat = noeMamoriyat;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
    }

    public String getMahaleMamoriyat() {
        return mahaleMamoriyat;
    }

    public void setMahaleMamoriyat(String mahaleMamoriyat) {
        this.mahaleMamoriyat = mahaleMamoriyat;
    }

    public String getSaatShoro() {
        return saatShoro;
    }

    public void setSaatShoro(String saatShoro) {
        this.saatShoro = saatShoro;
    }

    public String getSaatEnd() {
        return saatEnd;
    }

    public void setSaatEnd(String saatEnd) {
        this.saatEnd = saatEnd;
    }

    public int getModat() {
        return modat;
    }

    public void setModat(int modat) {
        this.modat = modat;
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

    public int getTamdid() {
        return tamdid;
    }

    public void setTamdid(int tamdid) {
        this.tamdid = tamdid;
    }

    public String getModatHours() {
        return modatHours;
    }

    public void setModatHours(String modatHours) {
        this.modatHours = modatHours;
    }

    public String getModatMinutes() {
        return modatMinutes;
    }

    public void setModatMinutes(String modatMinutes) {
        this.modatMinutes = modatMinutes;
    }
}
