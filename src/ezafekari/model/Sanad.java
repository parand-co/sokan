package ezafekari.model;

import java.util.Set;

public class Sanad {

    private long id;
    private String sal;
    private String mah;
    private String code;
    private Set<EzafeKari> ezafeKariSet;
    private boolean ghofl;
    private Set<EzafatoKosoorat> ezafatoKosooratSet;
    private long mablaghJirePayvar;
    private long mablaghJireGharardadi;
    private long kasrYeganiPayvar;
    private long kasrYeganiPeymani;
    private long kasrYeganiBaz;
    private long kasrYeganiGharardadi;

    public Sanad() {
    }

    public Sanad(long sanadId) {
        this.id = sanadId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public String getMah() {
        return mah;
    }

    public void setMah(String mah) {
        this.mah = mah;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<EzafeKari> getEzafeKariSet() {
        return ezafeKariSet;
    }

    public void setEzafeKariSet(Set<EzafeKari> ezafeKariSet) {
        this.ezafeKariSet = ezafeKariSet;
    }

    public boolean isGhofl() {
        return ghofl;
    }

    public void setGhofl(boolean ghofl) {
        this.ghofl = ghofl;
    }

    public Set<EzafatoKosoorat> getEzafatoKosooratSet() {
        return ezafatoKosooratSet;
    }

    public void setEzafatoKosooratSet(Set<EzafatoKosoorat> ezafatoKosooratSet) {
        this.ezafatoKosooratSet = ezafatoKosooratSet;
    }

    public long getMablaghJirePayvar() {
        return mablaghJirePayvar;
    }

    public void setMablaghJirePayvar(long mablaghJirePayvar) {
        this.mablaghJirePayvar = mablaghJirePayvar;
    }

    public long getMablaghJireGharardadi() {
        return mablaghJireGharardadi;
    }

    public void setMablaghJireGharardadi(long mablaghJireGharardadi) {
        this.mablaghJireGharardadi = mablaghJireGharardadi;
    }

    public long getKasrYeganiPayvar() {
        return kasrYeganiPayvar;
    }

    public void setKasrYeganiPayvar(long kasrYeganiPayvar) {
        this.kasrYeganiPayvar = kasrYeganiPayvar;
    }

    public long getKasrYeganiPeymani() {
        return kasrYeganiPeymani;
    }

    public void setKasrYeganiPeymani(long kasrYeganiPeymani) {
        this.kasrYeganiPeymani = kasrYeganiPeymani;
    }

    public long getKasrYeganiBaz() {
        return kasrYeganiBaz;
    }

    public void setKasrYeganiBaz(long kasrYeganiBaz) {
        this.kasrYeganiBaz = kasrYeganiBaz;
    }

    public long getKasrYeganiGharardadi() {
        return kasrYeganiGharardadi;
    }

    public void setKasrYeganiGharardadi(long kasrYeganiGharardadi) {
        this.kasrYeganiGharardadi = kasrYeganiGharardadi;
    }
}
