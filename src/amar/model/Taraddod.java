package amar.model;

import dataBaseModel.baseTable.Goroh;
import dataBaseModel.baseTable.MojavezRozane;
import dataBaseModel.baseTable.Taghvim;

/**
 * Created by nedaja on 01/01/2020.
 */
public class Taraddod {

    private long id;
    private Personel personel;
    private Integer saat;
    private Integer shomareDastgah;
    private Taghvim taghvim;
    private MojavezRozane vaziat;
    private Goroh goroh;
    private boolean edited;


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

    public Integer getSaat() {
        return saat;
    }

    public void setSaat(Integer saat) {
        this.saat = saat;
    }

    public Integer getShomareDastgah() {
        return shomareDastgah;
    }

    public void setShomareDastgah(Integer shomareDastgah) {
        this.shomareDastgah = shomareDastgah;
    }

    public Taghvim getTaghvim() {
        return taghvim;
    }

    public void setTaghvim(Taghvim taghvim) {
        this.taghvim = taghvim;
    }

    public MojavezRozane getVaziat() {
        return vaziat;
    }

    public void setVaziat(MojavezRozane vaziat) {
        this.vaziat = vaziat;
    }

    public Goroh getGoroh() {
        return goroh;
    }

    public void setGoroh(Goroh goroh) {
        this.goroh = goroh;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
