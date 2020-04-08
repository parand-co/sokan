package amar.model;


import dataBaseModel.baseTable.Goroh;
import dataBaseModel.baseTable.MojavezRozane;
import dataBaseModel.baseTable.Taghvim;

import java.io.Serializable;

public class SavabeghTaradod implements Serializable {
    private Long id;
    private Taghvim taghvim;
    private Personel personel;
    private MojavezRozane vazeyatTaraddod;
    private MojavezRozane vazeyatKhoroj;
    private Goroh goroh;
    private String saat1;
    private String saat2;
    private String saat3;
    private String saat4;
    private String saat5;
    private String saat6;
    private String saat7;
    private String saat8;
    private String saat9;
    private String saat10;
    private String saat11;
    private String saat12;
    private int shomareDastgah;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Taghvim getTaghvim() {
        return taghvim;
    }

    public void setTaghvim(Taghvim taghvim) {
        this.taghvim = taghvim;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public String getSaat1() {
        return saat1;
    }

    public void setSaat1(String saat1) {
        this.saat1 = saat1;
    }

    public String getSaat2() {
        return saat2;
    }

    public void setSaat2(String saat2) {
        this.saat2 = saat2;
    }

    public String getSaat3() {
        return saat3;
    }

    public void setSaat3(String saat3) {
        this.saat3 = saat3;
    }

    public String getSaat4() {
        return saat4;
    }

    public void setSaat4(String saat4) {
        this.saat4 = saat4;
    }

    public String getSaat5() {
        return saat5;
    }

    public void setSaat5(String saat5) {
        this.saat5 = saat5;
    }

    public String getSaat6() {
        return saat6;
    }

    public void setSaat6(String saat6) {
        this.saat6 = saat6;
    }

    public String getSaat7() {
        return saat7;
    }

    public void setSaat7(String saat7) {
        this.saat7 = saat7;
    }

    public String getSaat8() {
        return saat8;
    }

    public void setSaat8(String saat8) {
        this.saat8 = saat8;
    }

    public String getSaat9() {
        return saat9;
    }

    public void setSaat9(String saat9) {
        this.saat9 = saat9;
    }

    public String getSaat10() {
        return saat10;
    }

    public void setSaat10(String saat10) {
        this.saat10 = saat10;
    }

    public String getSaat11() {
        return saat11;
    }

    public void setSaat11(String saat11) {
        this.saat11 = saat11;
    }

    public String getSaat12() {
        return saat12;
    }

    public void setSaat12(String saat12) {
        this.saat12 = saat12;
    }

    public MojavezRozane getVazeyatTaraddod() {
        return vazeyatTaraddod;
    }

    public void setVazeyatTaraddod(MojavezRozane vazeyatTaraddod) {
        this.vazeyatTaraddod = vazeyatTaraddod;
    }

    public int getShomareDastgah() {
        return shomareDastgah;
    }

    public void setShomareDastgah(int shomareDastgah) {
        this.shomareDastgah = shomareDastgah;
    }

    public Goroh getGoroh() {
        return goroh;
    }

    public void setGoroh(Goroh goroh) {
        this.goroh = goroh;
    }

    public MojavezRozane getVazeyatKhoroj() {
        return vazeyatKhoroj;
    }

    public void setVazeyatKhoroj(MojavezRozane vazeyatKhoroj) {
        this.vazeyatKhoroj = vazeyatKhoroj;
    }
}