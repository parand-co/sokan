package net.hokmeKar.model;

import dataBaseModel.baseTable.*;

public class SabtHokmeKar {

    private long id;
    private TypeHokmeKar noeHokmeKar;
    private String shomareDarkhast;
    private Bakhsh yegan;
    private String hozeKari;
    private String codeShenavar;
    private ArjaiyatHokmeKar arjaiyatHokmeKar;
    private VaziyatAnjamHokmeKar vaziyatAnjamHokmeKar;
    private AnjamDahandeHokmeKar anjamDahandeHokmeKar;
    private TypeKarHokmeKar noeKareHokmeKar;
    private Boolean emamReza;
    private String tarikhSabt;
    private String tarikhDarkhast;
    private String sharh;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeHokmeKar getNoeHokmeKar() {
        return noeHokmeKar;
    }

    public void setNoeHokmeKar(TypeHokmeKar noeHokmeKar) {
        this.noeHokmeKar = noeHokmeKar;
    }

    public String getShomareDarkhast() {
        return shomareDarkhast;
    }

    public void setShomareDarkhast(String shomareDarkhast) {
        this.shomareDarkhast = shomareDarkhast;
    }

    public Bakhsh getYegan() {
        return yegan;
    }

    public void setYegan(Bakhsh yegan) {
        this.yegan = yegan;
    }

    public String getHozeKari() {
        return hozeKari;
    }

    public void setHozeKari(String hozeKari) {
        this.hozeKari = hozeKari;
    }

    public String getCodeShenavar() {
        return codeShenavar;
    }

    public void setCodeShenavar(String codeShenavar) {
        this.codeShenavar = codeShenavar;
    }

    public ArjaiyatHokmeKar getArjaiyatHokmeKar() {
        return arjaiyatHokmeKar;
    }

    public void setArjaiyatHokmeKar(ArjaiyatHokmeKar arjaiyatHokmeKar) {
        this.arjaiyatHokmeKar = arjaiyatHokmeKar;
    }

    public VaziyatAnjamHokmeKar getVaziyatAnjamHokmeKar() {
        return vaziyatAnjamHokmeKar;
    }

    public void setVaziyatAnjamHokmeKar(VaziyatAnjamHokmeKar vaziyatAnjamHokmeKar) {
        this.vaziyatAnjamHokmeKar = vaziyatAnjamHokmeKar;
    }

    public AnjamDahandeHokmeKar getAnjamDahandeHokmeKar() {
        return anjamDahandeHokmeKar;
    }

    public void setAnjamDahandeHokmeKar(AnjamDahandeHokmeKar anjamDahandeHokmeKar) {
        this.anjamDahandeHokmeKar = anjamDahandeHokmeKar;
    }

    public TypeKarHokmeKar getNoeKareHokmeKar() {
        return noeKareHokmeKar;
    }

    public void setNoeKareHokmeKar(TypeKarHokmeKar noeKareHokmeKar) {
        this.noeKareHokmeKar = noeKareHokmeKar;
    }

    public Boolean getEmamReza() {
        return emamReza;
    }

    public void setEmamReza(Boolean emamReza) {
        this.emamReza = emamReza;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
    }

    public String getTarikhDarkhast() {
        return tarikhDarkhast;
    }

    public void setTarikhDarkhast(String tarikhDarkhast) {
        this.tarikhDarkhast = tarikhDarkhast;
    }

    public String getSharh() {
        return sharh;
    }

    public void setSharh(String sharh) {
        this.sharh = sharh;
    }
}