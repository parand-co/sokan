package amar.model;

import com.google.gson.annotations.Expose;

public class KarkardRozaneModel {

    @Expose
    private String tarikh;
    @Expose
    private String tajil;
    @Expose
    private String takhir;
    @Expose
    private String v1;
    @Expose
    private String v2;
    @Expose
    private String v3;
    @Expose
    private String v4;
    @Expose
    private String v5;
    @Expose
    private String v6;
    @Expose
    private String v7;
    @Expose
    private String v8;
    @Expose
    private String mojavez;
    private String taghvim;

    private Personel personel;

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getTajil() {
        return tajil;
    }

    public void setTajil(String tajil) {
        this.tajil = tajil;
    }

    public String getTakhir() {
        return takhir;
    }

    public void setTakhir(String takhir) {
        this.takhir = takhir;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }

    public String getV5() {
        return v5;
    }

    public void setV5(String v5) {
        this.v5 = v5;
    }

    public String getV6() {
        return v6;
    }

    public void setV6(String v6) {
        this.v6 = v6;
    }

    public String getV7() {
        return v7;
    }

    public void setV7(String v7) {
        this.v7 = v7;
    }

    public String getV8() {
        return v8;
    }

    public void setV8(String v8) {
        this.v8 = v8;
    }

    public String getMojavez() {
        return mojavez;
    }

    public void setMojavez(String mojavez) {
        this.mojavez = mojavez;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public String getTaghvim() {
        return taghvim;
    }

    public void setTaghvim(String taghvim) {
        this.taghvim = taghvim;
    }
}