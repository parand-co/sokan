package amar.model.amarRozaneJireBegir;

import dataBaseModel.baseTable.*;

import java.io.Serializable;

public class AmarAfsaranYekVaDo implements Serializable {


    private long id;
    private String sharh;
    private int nEdeKol=0;
    //motafareghe//
    private int nMorakhasi=0;
    private int nBastari=0;
    private int nNahast=0;
    private int nFarar=0;
    private int nBazdasht=0;
    private int nMontazerBekhedmat=0;
    private int nMamorAzEde=0;
    // \ //


    private int nJameMotafareghe=0;
    private int nMamorBeEde=0;
    private int nJameHazer=0;



    //negahbani//
    private int nSobhane=0;
    private int nNahar=0;
    private int nSham=0;
    // \//




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getnEdeKol() {
        return nEdeKol;
    }

    public void setnEdeKol(int nEdeKol) {
        this.nEdeKol = nEdeKol;
    }

    public int getnMorakhasi() {
        return nMorakhasi;
    }

    public void setnMorakhasi(int nMorakhasi) {
        this.nMorakhasi = nMorakhasi;
    }

    public int getnBastari() {
        return nBastari;
    }

    public void setnBastari(int nBastari) {
        this.nBastari = nBastari;
    }

    public int getnNahast() {
        return nNahast;
    }

    public void setnNahast(int nNahast) {
        this.nNahast = nNahast;
    }

    public int getnFarar() {
        return nFarar;
    }

    public void setnFarar(int nFarar) {
        this.nFarar = nFarar;
    }

    public int getnBazdasht() {
        return nBazdasht;
    }

    public void setnBazdasht(int nBazdasht) {
        this.nBazdasht = nBazdasht;
    }

    public int getnMontazerBekhedmat() {
        return nMontazerBekhedmat;
    }

    public void setnMontazerBekhedmat(int nMontazerBekhedmat) {
        this.nMontazerBekhedmat = nMontazerBekhedmat;
    }

    public int getnMamorAzEde() {
        return nMamorAzEde;
    }

    public void setnMamorAzEde(int nMamorAzEde) {
        this.nMamorAzEde = nMamorAzEde;
    }

    public int getnJameMotafareghe() {
        return nJameMotafareghe;
    }

    public void setnJameMotafareghe(int nJameMotafareghe) {
        this.nJameMotafareghe = nJameMotafareghe;
    }

    public int getnMamorBeEde() {
        return nMamorBeEde;
    }

    public void setnMamorBeEde(int nMamorBeEde) {
        this.nMamorBeEde = nMamorBeEde;
    }

    public int getnJameHazer() {
        return nJameHazer;
    }

    public void setnJameHazer(int nJameHazer) {
        this.nJameHazer = nJameHazer;
    }

    public int getnSobhane() {
        return nSobhane;
    }

    public void setnSobhane(int nSobhane) {
        this.nSobhane = nSobhane;
    }

    public int getnNahar() {
        return nNahar;
    }

    public void setnNahar(int nNahar) {
        this.nNahar = nNahar;
    }

    public int getnSham() {
        return nSham;
    }

    public void setnSham(int nSham) {
        this.nSham = nSham;
    }

    public String getSharh() {
        return sharh;
    }

    public void setSharh(String sharh) {
        this.sharh = sharh;
    }
}
