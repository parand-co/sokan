package amar.model;

import dataBaseModel.baseTable.*;

import java.io.Serializable;

public class Personel implements Serializable {

    private long id;


    //shenasname
    private String shomarePersoneli;
    private String codeMeli;
    private String name;
    private String neshan;
    private String axe;
    private String namePedar;
    private String shomareShenasname;
    private String tellSabet;
    private String tellHamrah;
    private String address;
    private String shomareHesabHekmat;
    private String shomareHesabSepah;
    private String tarikhTavalod;
    private Jensiyat jensiyat;
    private VazeyatTaahol vazeyatTaahol;


//    estekhdam
    private String shomareKart;
    private String tarikhEstekhdam;//    expire
    private String tarikhEngheza;
    private String elateGhateKar;
    private boolean active;
    private int saghfeMorakhasi;
    private String tarikhEnteghal;
    private boolean DakhelOrKharej;
    private NoeEstekhdam noeEstekhdam;
    private Madrak madrak;
    private Tabaghe noeTabaghe;

//    private String takhireMojazeRozane;
//    private String tajilMojazRozane;

//    khedmati
    private Boolean vazeyatShoghli;
    private Dayere dayere;
//    private Ghesmat ghesmat;
    private Bakhsh bakhsh;
    private Semat semat;
    private Daraje daraje;
    private Daraje darajeShoghl;
    private Raste raste;
    private Hoviyat hoviyat; //    noePersonel   pishbini shavad (payvar-gharadadi-bazneshaste)
    private Yegan yegan;
    private FarmandehiMostaghel farmandehiMostaghel;
    private Goroh gorohKari;

//   shoghl
    private String seri;
    private String band;
    private String satr;
    private String onvaneShoghl;
    private long haghShoghl;
    private long hagheShaghel;
    private long fogholadeModiriyat;
    private long saghfeEzafeKar;
    private String mahalShoghlSazmani;


    public Personel() {
    }

    public Personel(Daraje daraje,Yegan yegan,NoeEstekhdam noeEstekhdam,Madrak madrak) {
        this.daraje = daraje;
        this.yegan = yegan;
        this.noeEstekhdam = noeEstekhdam;
        this.madrak = madrak;
        getYegan().setPaygah(new Paygah());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShomarePersoneli() {
        return shomarePersoneli;
    }

    public void setShomarePersoneli(String shomarePersoneli) {
        this.shomarePersoneli = shomarePersoneli;
    }

    public String getShomareKart() {
        return shomareKart;
    }

    public void setShomareKart(String shomareKart) {
        this.shomareKart = shomareKart;
    }

    public String getCodeMeli() {
        return codeMeli;
    }

    public void setCodeMeli(String codeMeli) {
        this.codeMeli = codeMeli;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeshan() {
        return neshan;
    }

    public void setNeshan(String neshan) {
        this.neshan = neshan;
    }

    public Jensiyat getJensiyat() {
        return jensiyat;
    }

    public void setJensiyat(Jensiyat jensiyat) {
        this.jensiyat = jensiyat;
    }

    public VazeyatTaahol getVazeyatTaahol() {
        return vazeyatTaahol;
    }

    public void setVazeyatTaahol(VazeyatTaahol vazeyatTaahol) {
        this.vazeyatTaahol = vazeyatTaahol;
    }

    public String getAxe() {
        return axe;
    }

    public void setAxe(String axe) {
        this.axe = axe;
    }

    public String getNamePedar() {
        return namePedar;
    }

    public void setNamePedar(String namePedar) {
        this.namePedar = namePedar;
    }

    public String getShomareShenasname() {
        return shomareShenasname;
    }

    public void setShomareShenasname(String shomareShenasname) {
        this.shomareShenasname = shomareShenasname;
    }

    public String getTellSabet() {
        return tellSabet;
    }

    public void setTellSabet(String tellSabet) {
        this.tellSabet = tellSabet;
    }

    public String getTellHamrah() {
        return tellHamrah;
    }

    public void setTellHamrah(String tellHamrah) {
        this.tellHamrah = tellHamrah;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShomareHesabHekmat() {
        return shomareHesabHekmat;
    }

    public void setShomareHesabHekmat(String shomareHesabHekmat) {
        this.shomareHesabHekmat = shomareHesabHekmat;
    }

    public String getShomareHesabSepah() {
        return shomareHesabSepah;
    }

    public void setShomareHesabSepah(String shomareHesabSepah) {
        this.shomareHesabSepah = shomareHesabSepah;
    }

    public String getTarikhTavalod() {
        return tarikhTavalod;
    }

    public void setTarikhTavalod(String tarikhTavalod) {
        this.tarikhTavalod = tarikhTavalod;
    }

    public NoeEstekhdam getNoeEstekhdam() {
        return noeEstekhdam;
    }

    public void setNoeEstekhdam(NoeEstekhdam noeEstekhdam) {
        this.noeEstekhdam = noeEstekhdam;
    }

    public Madrak getMadrak() {
        return madrak;
    }

    public void setMadrak(Madrak madrak) {
        this.madrak = madrak;
    }

    public String getTarikhEstekhdam() {
        return tarikhEstekhdam;
    }

    public void setTarikhEstekhdam(String tarikhEstekhdam) {
        this.tarikhEstekhdam = tarikhEstekhdam;
    }

    public String getTarikhEngheza() {
        return tarikhEngheza;
    }

    public void setTarikhEngheza(String tarikhEngheza) {
        this.tarikhEngheza = tarikhEngheza;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSaghfeMorakhasi() {
        return saghfeMorakhasi;
    }

    public void setSaghfeMorakhasi(int saghfeMorakhasi) {
        this.saghfeMorakhasi = saghfeMorakhasi;
    }

    public Tabaghe getNoeTabaghe() {
        return noeTabaghe;
    }

    public void setNoeTabaghe(Tabaghe noeTabaghe) {
        this.noeTabaghe = noeTabaghe;
    }

    public String getTarikhEnteghal() {
        return tarikhEnteghal;
    }

    public void setTarikhEnteghal(String tarikhEnteghal) {
        this.tarikhEnteghal = tarikhEnteghal;
    }

    public boolean isDakhelOrKharej() {
        return DakhelOrKharej;
    }

    public void setDakhelOrKharej(boolean dakhelOrKharej) {
        DakhelOrKharej = dakhelOrKharej;
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

    public Boolean getVazeyatShoghli() {
        return vazeyatShoghli;
    }

    public void setVazeyatShoghli(Boolean vazeyatShoghli) {
        this.vazeyatShoghli = vazeyatShoghli;
    }

    public Semat getSemat() {
        return semat;
    }

    public void setSemat(Semat semat) {
        this.semat = semat;
    }

    public Daraje getDaraje() {
        return daraje;
    }

    public void setDaraje(Daraje daraje) {
        this.daraje = daraje;
    }

    public Raste getRaste() {
        return raste;
    }

    public void setRaste(Raste raste) {
        this.raste = raste;
    }

    public Hoviyat getHoviyat() {
        return hoviyat;
    }

    public void setHoviyat(Hoviyat hoviyat) {
        this.hoviyat = hoviyat;
    }

    public Yegan getYegan() {
        return yegan;
    }

    public void setYegan(Yegan yegan) {
        this.yegan = yegan;
    }

    public FarmandehiMostaghel getFarmandehiMostaghel() {
        return farmandehiMostaghel;
    }

    public void setFarmandehiMostaghel(FarmandehiMostaghel farmandehiMostaghel) {
        this.farmandehiMostaghel = farmandehiMostaghel;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getSatr() {
        return satr;
    }

    public void setSatr(String satr) {
        this.satr = satr;
    }

    public String getOnvaneShoghl() {
        return onvaneShoghl;
    }

    public void setOnvaneShoghl(String onvaneShoghl) {
        this.onvaneShoghl = onvaneShoghl;
    }

    public long getHaghShoghl() {
        return haghShoghl;
    }

    public void setHaghShoghl(long haghShoghl) {
        this.haghShoghl = haghShoghl;
    }

    public long getHagheShaghel() {
        return hagheShaghel;
    }

    public void setHagheShaghel(long hagheShaghel) {
        this.hagheShaghel = hagheShaghel;
    }

    public long getFogholadeModiriyat() {
        return fogholadeModiriyat;
    }

    public void setFogholadeModiriyat(long fogholadeModiriyat) {
        this.fogholadeModiriyat = fogholadeModiriyat;
    }

    public long getSaghfeEzafeKar() {
        return saghfeEzafeKar;
    }

    public void setSaghfeEzafeKar(long saghfeEzafeKar) {
        this.saghfeEzafeKar = saghfeEzafeKar;
    }

    public Goroh getGorohKari() {
        return gorohKari;
    }

    public void setGorohKari(Goroh gorohKari) {
        this.gorohKari = gorohKari;
    }

    public String getElateGhateKar() {
        return elateGhateKar;
    }

    public void setElateGhateKar(String elateGhateKar) {
        this.elateGhateKar = elateGhateKar;
    }

    public Daraje getDarajeShoghl() {
        return darajeShoghl;
    }

    public void setDarajeShoghl(Daraje darajeShoghl) {
        this.darajeShoghl = darajeShoghl;
    }

    public String getMahalShoghlSazmani() {
        return mahalShoghlSazmani;
    }

    public void setMahalShoghlSazmani(String mahalShoghlSazmani) {
        this.mahalShoghlSazmani = mahalShoghlSazmani;
    }
}