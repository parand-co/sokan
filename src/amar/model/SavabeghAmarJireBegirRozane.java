package amar.model;

import amar.model.amarRozaneJireBegir.*;
import com.google.gson.annotations.Expose;
import dataBaseModel.baseTable.*;

import java.io.Serializable;
import java.util.Set;

public class SavabeghAmarJireBegirRozane implements Serializable {
    private long id;

    //    har dayere emkane sabt amare karkonan khod ra dashte bashad
    @Expose
    private AmarAfsaranYekVaDo amarAfsaranYekVaDo;
    @Expose
    private AmarAfsaranSe amarAfsaranSe;
    @Expose
    private AmarDarajeDaran amarDarajeDaran;
    @Expose
    private AmarKarmandanElmi amarKarmandanElmi;
    @Expose
    private AmarKarmandanTajrobi amarKarmandanTajrobi;
    @Expose
    private AmarAfsarVazife amarAfsarVazife;
    @Expose
    private AmarDaneshjoVazife amarDaneshjoVazife;
    @Expose
    private AmarDarajeDarVazife amarDarajeDarVazife;
    @Expose
    private AmarNavi amarNavi;
    @Expose
    private Dayere dayere;
    @Expose
    private Bakhsh bakhsh;
    @Expose
    private Taghvim taghvim;
    private String sarparasteShobeAmar;
    private String farmandeYegan;
    private String sarparasteShobeAmarNiroEnsani;
    //taghiratAmariPayvar//
    private int nModavematKari=0;
    private int nEzafekari=0;
    private int tedadJireNahar=0;
    private String molahezat;
    private int nAzMorakhasiPayvar;
    private int nBeMorakhasiPayvar;
    private int nAzNahastPayvar;
    private int nBeNahastPayvar;
    private int nAzBastariPayvar;
    private int nBeBastariPayvar;
    private int nAzMamoriyatPayvar;
    private int nBeMamoriyatPayvar;
    private int nKasrAzAmarPayvar;
    private int nEzafBeAmarPayvar;
    private int nEzafBeMamorBeEdePayvar;
    private int nKasrAzMamorBeEdePayvar;
    private int nBeEntezarBeKhedmatPayvar;
    private int nAzEntezarKhedmatPayvar;
    private int nBeBazdashtPayvar;
    private int nAzBazdashtPayvar;
    //   \   //
    //taghiratAmariNaviyan//
    private int nAzMorakhasiVazife;
    private int nBeMorakhasiVazife;
    private int nAzNahastVazife;
    private int nBeNahastVazife;
    private int nAzBastariVazife;
    private int nBeBastariVazife;
    private int nAzMamoriyatVazife;
    private int nBeMamoriyatVazife;
    private int nKasrAzAmarVazife;
    private int nEzafBeAmarVazife;
    private int nEzafBeMamorBeEdeVazife;
    private int nKasrAzMamorBeEdeVazife;
    private int nBeEntezarBeKhedmatVazife;
    private int nAzEntezarKhedmatVazife;
    private int nBeBazdashtVazife;
    private int nAzBazdashtVazife;
    private int nEdeKolJame;
    private int nMorakhasiJame;
    private int nBastariJame;
    private int nNahastJame;
    private int nFararJame;
    private int nBazdashtJame;
    private int nMontazerBekhedmatJame;
    private int nMamorAzEdeJame;
    private int nJameMotafaregheJame;
    private int nMamorBeEdeJame;
    private int nJameHazerJame;
    private int nSobhaneJame;
    private int nNaharJame;
    private int nShamJame;
    private String erja;

    //    \    //


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public Taghvim getTaghvim() {
        return taghvim;
    }

    public void setTaghvim(Taghvim taghvim) {
        this.taghvim = taghvim;
    }



    public int getnAzMorakhasiPayvar() {
        return nAzMorakhasiPayvar;
    }

    public void setnAzMorakhasiPayvar(int nAzMorakhasiPayvar) {
        this.nAzMorakhasiPayvar = nAzMorakhasiPayvar;
    }

    public int getnBeMorakhasiPayvar() {
        return nBeMorakhasiPayvar;
    }

    public void setnBeMorakhasiPayvar(int nBeMorakhasiPayvar) {
        this.nBeMorakhasiPayvar = nBeMorakhasiPayvar;
    }

    public int getnAzNahastPayvar() {
        return nAzNahastPayvar;
    }

    public void setnAzNahastPayvar(int nAzNahastPayvar) {
        this.nAzNahastPayvar = nAzNahastPayvar;
    }

    public int getnBeNahastPayvar() {
        return nBeNahastPayvar;
    }

    public void setnBeNahastPayvar(int nBeNahastPayvar) {
        this.nBeNahastPayvar = nBeNahastPayvar;
    }

    public int getnAzBastariPayvar() {
        return nAzBastariPayvar;
    }

    public void setnAzBastariPayvar(int nAzBastariPayvar) {
        this.nAzBastariPayvar = nAzBastariPayvar;
    }

    public int getnBeBastariPayvar() {
        return nBeBastariPayvar;
    }

    public void setnBeBastariPayvar(int nBeBastariPayvar) {
        this.nBeBastariPayvar = nBeBastariPayvar;
    }

    public int getnAzMamoriyatPayvar() {
        return nAzMamoriyatPayvar;
    }

    public void setnAzMamoriyatPayvar(int nAzMamoriyatPayvar) {
        this.nAzMamoriyatPayvar = nAzMamoriyatPayvar;
    }

    public int getnBeMamoriyatPayvar() {
        return nBeMamoriyatPayvar;
    }

    public void setnBeMamoriyatPayvar(int nBeMamoriyatPayvar) {
        this.nBeMamoriyatPayvar = nBeMamoriyatPayvar;
    }

    public int getnKasrAzAmarPayvar() {
        return nKasrAzAmarPayvar;
    }

    public void setnKasrAzAmarPayvar(int nKasrAzAmarPayvar) {
        this.nKasrAzAmarPayvar = nKasrAzAmarPayvar;
    }

    public int getnEzafBeAmarPayvar() {
        return nEzafBeAmarPayvar;
    }

    public void setnEzafBeAmarPayvar(int nEzafBeAmarPayvar) {
        this.nEzafBeAmarPayvar = nEzafBeAmarPayvar;
    }

    public int getnEzafBeMamorBeEdePayvar() {
        return nEzafBeMamorBeEdePayvar;
    }

    public void setnEzafBeMamorBeEdePayvar(int nEzafBeMamorBeEdePayvar) {
        this.nEzafBeMamorBeEdePayvar = nEzafBeMamorBeEdePayvar;
    }

    public int getnKasrAzMamorBeEdePayvar() {
        return nKasrAzMamorBeEdePayvar;
    }

    public void setnKasrAzMamorBeEdePayvar(int nKasrAzMamorBeEdePayvar) {
        this.nKasrAzMamorBeEdePayvar = nKasrAzMamorBeEdePayvar;
    }

    public int getnBeEntezarBeKhedmatPayvar() {
        return nBeEntezarBeKhedmatPayvar;
    }

    public void setnBeEntezarBeKhedmatPayvar(int nBeEntezarBeKhedmatPayvar) {
        this.nBeEntezarBeKhedmatPayvar = nBeEntezarBeKhedmatPayvar;
    }

    public int getnAzEntezarKhedmatPayvar() {
        return nAzEntezarKhedmatPayvar;
    }

    public void setnAzEntezarKhedmatPayvar(int nAzEntezarKhedmatPayvar) {
        this.nAzEntezarKhedmatPayvar = nAzEntezarKhedmatPayvar;
    }

    public int getnBeBazdashtPayvar() {
        return nBeBazdashtPayvar;
    }

    public void setnBeBazdashtPayvar(int nBeBazdashtPayvar) {
        this.nBeBazdashtPayvar = nBeBazdashtPayvar;
    }

    public int getnAzBazdashtPayvar() {
        return nAzBazdashtPayvar;
    }

    public void setnAzBazdashtPayvar(int nAzBazdashtPayvar) {
        this.nAzBazdashtPayvar = nAzBazdashtPayvar;
    }

    public int getnAzMorakhasiVazife() {
        return nAzMorakhasiVazife;
    }

    public void setnAzMorakhasiVazife(int nAzMorakhasiVazife) {
        this.nAzMorakhasiVazife = nAzMorakhasiVazife;
    }

    public int getnBeMorakhasiVazife() {
        return nBeMorakhasiVazife;
    }

    public void setnBeMorakhasiVazife(int nBeMorakhasiVazife) {
        this.nBeMorakhasiVazife = nBeMorakhasiVazife;
    }

    public int getnAzNahastVazife() {
        return nAzNahastVazife;
    }

    public void setnAzNahastVazife(int nAzNahastVazife) {
        this.nAzNahastVazife = nAzNahastVazife;
    }

    public int getnBeNahastVazife() {
        return nBeNahastVazife;
    }

    public void setnBeNahastVazife(int nBeNahastVazife) {
        this.nBeNahastVazife = nBeNahastVazife;
    }

    public int getnAzBastariVazife() {
        return nAzBastariVazife;
    }

    public void setnAzBastariVazife(int nAzBastariVazife) {
        this.nAzBastariVazife = nAzBastariVazife;
    }

    public int getnBeBastariVazife() {
        return nBeBastariVazife;
    }

    public void setnBeBastariVazife(int nBeBastariVazife) {
        this.nBeBastariVazife = nBeBastariVazife;
    }

    public int getnAzMamoriyatVazife() {
        return nAzMamoriyatVazife;
    }

    public void setnAzMamoriyatVazife(int nAzMamoriyatVazife) {
        this.nAzMamoriyatVazife = nAzMamoriyatVazife;
    }

    public int getnBeMamoriyatVazife() {
        return nBeMamoriyatVazife;
    }

    public void setnBeMamoriyatVazife(int nBeMamoriyatVazife) {
        this.nBeMamoriyatVazife = nBeMamoriyatVazife;
    }

    public int getnKasrAzAmarVazife() {
        return nKasrAzAmarVazife;
    }

    public void setnKasrAzAmarVazife(int nKasrAzAmarVazife) {
        this.nKasrAzAmarVazife = nKasrAzAmarVazife;
    }

    public int getnEzafBeAmarVazife() {
        return nEzafBeAmarVazife;
    }

    public void setnEzafBeAmarVazife(int nEzafBeAmarVazife) {
        this.nEzafBeAmarVazife = nEzafBeAmarVazife;
    }

    public int getnEzafBeMamorBeEdeVazife() {
        return nEzafBeMamorBeEdeVazife;
    }

    public void setnEzafBeMamorBeEdeVazife(int nEzafBeMamorBeEdeVazife) {
        this.nEzafBeMamorBeEdeVazife = nEzafBeMamorBeEdeVazife;
    }

    public int getnKasrAzMamorBeEdeVazife() {
        return nKasrAzMamorBeEdeVazife;
    }

    public void setnKasrAzMamorBeEdeVazife(int nKasrAzMamorBeEdeVazife) {
        this.nKasrAzMamorBeEdeVazife = nKasrAzMamorBeEdeVazife;
    }

    public int getnBeEntezarBeKhedmatVazife() {
        return nBeEntezarBeKhedmatVazife;
    }

    public void setnBeEntezarBeKhedmatVazife(int nBeEntezarBeKhedmatVazife) {
        this.nBeEntezarBeKhedmatVazife = nBeEntezarBeKhedmatVazife;
    }

    public int getnAzEntezarKhedmatVazife() {
        return nAzEntezarKhedmatVazife;
    }

    public void setnAzEntezarKhedmatVazife(int nAzEntezarKhedmatVazife) {
        this.nAzEntezarKhedmatVazife = nAzEntezarKhedmatVazife;
    }

    public int getnBeBazdashtVazife() {
        return nBeBazdashtVazife;
    }

    public void setnBeBazdashtVazife(int nBeBazdashtVazife) {
        this.nBeBazdashtVazife = nBeBazdashtVazife;
    }

    public int getnAzBazdashtVazife() {
        return nAzBazdashtVazife;
    }

    public void setnAzBazdashtVazife(int nAzBazdashtVazife) {
        this.nAzBazdashtVazife = nAzBazdashtVazife;
    }

    public int getnModavematKari() {
        return nModavematKari;
    }

    public void setnModavematKari(int nModavematKari) {
        this.nModavematKari = nModavematKari;
    }

    public int getnEzafekari() {
        return nEzafekari;
    }

    public void setnEzafekari(int nEzafekari) {
        this.nEzafekari = nEzafekari;
    }

    public String getMolahezat() {
        return molahezat;
    }

    public void setMolahezat(String molahezat) {
        this.molahezat = molahezat;
    }

    public AmarAfsaranYekVaDo getAmarAfsaranYekVaDo() {
        return amarAfsaranYekVaDo;
    }

    public void setAmarAfsaranYekVaDo(AmarAfsaranYekVaDo amarAfsaranYekVaDo) {
        this.amarAfsaranYekVaDo = amarAfsaranYekVaDo;
    }

    public AmarAfsaranSe getAmarAfsaranSe() {
        return amarAfsaranSe;
    }

    public void setAmarAfsaranSe(AmarAfsaranSe amarAfsaranSe) {
        this.amarAfsaranSe = amarAfsaranSe;
    }

    public AmarDarajeDaran getAmarDarajeDaran() {
        return amarDarajeDaran;
    }

    public void setAmarDarajeDaran(AmarDarajeDaran amarDarajeDaran) {
        this.amarDarajeDaran = amarDarajeDaran;
    }

    public AmarKarmandanElmi getAmarKarmandanElmi() {
        return amarKarmandanElmi;
    }

    public void setAmarKarmandanElmi(AmarKarmandanElmi amarKarmandanElmi) {
        this.amarKarmandanElmi = amarKarmandanElmi;
    }

    public AmarKarmandanTajrobi getAmarKarmandanTajrobi() {
        return amarKarmandanTajrobi;
    }

    public void setAmarKarmandanTajrobi(AmarKarmandanTajrobi amarKarmandanTajrobi) {
        this.amarKarmandanTajrobi = amarKarmandanTajrobi;
    }

    public AmarAfsarVazife getAmarAfsarVazife() {
        return amarAfsarVazife;
    }

    public void setAmarAfsarVazife(AmarAfsarVazife amarAfsarVazife) {
        this.amarAfsarVazife = amarAfsarVazife;
    }

    public AmarDaneshjoVazife getAmarDaneshjoVazife() {
        return amarDaneshjoVazife;
    }

    public void setAmarDaneshjoVazife(AmarDaneshjoVazife amarDaneshjoVazife) {
        this.amarDaneshjoVazife = amarDaneshjoVazife;
    }

    public AmarDarajeDarVazife getAmarDarajeDarVazife() {
        return amarDarajeDarVazife;
    }

    public void setAmarDarajeDarVazife(AmarDarajeDarVazife amarDarajeDarVazife) {
        this.amarDarajeDarVazife = amarDarajeDarVazife;
    }

    public AmarNavi getAmarNavi() {
        return amarNavi;
    }

    public void setAmarNavi(AmarNavi amarNavi) {
        this.amarNavi = amarNavi;
    }

    public int getnEdeKolJame() {
        return nEdeKolJame;
    }

    public void setnEdeKolJame(int nEdeKolJame) {
        this.nEdeKolJame = nEdeKolJame;
    }

    public int getnMorakhasiJame() {
        return nMorakhasiJame;
    }

    public void setnMorakhasiJame(int nMorakhasiJame) {
        this.nMorakhasiJame = nMorakhasiJame;
    }

    public int getnBastariJame() {
        return nBastariJame;
    }

    public void setnBastariJame(int nBastariJame) {
        this.nBastariJame = nBastariJame;
    }

    public int getnNahastJame() {
        return nNahastJame;
    }

    public void setnNahastJame(int nNahastJame) {
        this.nNahastJame = nNahastJame;
    }

    public int getnFararJame() {
        return nFararJame;
    }

    public void setnFararJame(int nFararJame) {
        this.nFararJame = nFararJame;
    }

    public int getnBazdashtJame() {
        return nBazdashtJame;
    }

    public void setnBazdashtJame(int nBazdashtJame) {
        this.nBazdashtJame = nBazdashtJame;
    }

    public int getnMontazerBekhedmatJame() {
        return nMontazerBekhedmatJame;
    }

    public void setnMontazerBekhedmatJame(int nMontazerBekhedmatJame) {
        this.nMontazerBekhedmatJame = nMontazerBekhedmatJame;
    }

    public int getnMamorAzEdeJame() {
        return nMamorAzEdeJame;
    }

    public void setnMamorAzEdeJame(int nMamorAzEdeJame) {
        this.nMamorAzEdeJame = nMamorAzEdeJame;
    }

    public int getnJameMotafaregheJame() {
        return nJameMotafaregheJame;
    }

    public void setnJameMotafaregheJame(int nJameMotafaregheJame) {
        this.nJameMotafaregheJame = nJameMotafaregheJame;
    }

    public int getnMamorBeEdeJame() {
        return nMamorBeEdeJame;
    }

    public void setnMamorBeEdeJame(int nMamorBeEdeJame) {
        this.nMamorBeEdeJame = nMamorBeEdeJame;
    }

    public int getnJameHazerJame() {
        return nJameHazerJame;
    }

    public void setnJameHazerJame(int nJameHazerJame) {
        this.nJameHazerJame = nJameHazerJame;
    }

    public int getnSobhaneJame() {
        return nSobhaneJame;
    }

    public void setnSobhaneJame(int nSobhaneJame) {
        this.nSobhaneJame = nSobhaneJame;
    }

    public int getnNaharJame() {
        return nNaharJame;
    }

    public void setnNaharJame(int nNaharJame) {
        this.nNaharJame = nNaharJame;
    }

    public int getnShamJame() {
        return nShamJame;
    }

    public void setnShamJame(int nShamJame) {
        this.nShamJame = nShamJame;
    }

    public String getErja() {
        return erja;
    }

    public void setErja(String erja) {
        this.erja = erja;
    }

    public String getSarparasteShobeAmar() {
        return sarparasteShobeAmar;
    }

    public void setSarparasteShobeAmar(String sarparasteShobeAmar) {
        this.sarparasteShobeAmar = sarparasteShobeAmar;
    }

    public String getFarmandeYegan() {
        return farmandeYegan;
    }

    public void setFarmandeYegan(String farmandeYegan) {
        this.farmandeYegan = farmandeYegan;
    }

    public String getSarparasteShobeAmarNiroEnsani() {
        return sarparasteShobeAmarNiroEnsani;
    }

    public void setSarparasteShobeAmarNiroEnsani(String sarparasteShobeAmarNiroEnsani) {
        this.sarparasteShobeAmarNiroEnsani = sarparasteShobeAmarNiroEnsani;
    }

    public int getTedadJireNahar() {
        return tedadJireNahar;
    }

    public void setTedadJireNahar(int tedadJireNahar) {
        this.tedadJireNahar = tedadJireNahar;
    }
}
