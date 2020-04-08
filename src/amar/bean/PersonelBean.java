package amar.bean;

import amar.model.Personel;
import baseCode.alert.Alert;
import baseCode.style.Style;
import baseCode.util.Checking;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dashboard.DashboardBean;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.PersonelDao;
import dataBaseModel.util.HibernateUtil;
import manage.bean.IndexBean;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.primefaces.model.ByteArrayContent;
import util.FillList;
import util.Pic;

import javax.crypto.Cipher;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class PersonelBean implements Serializable {
    private List<Personel> personelList = new ArrayList<>();
    private List<Personel> filteredPersonel = new ArrayList<>();
    private Personel personel = new Personel();
    private String URL = "amar/Personel.xhtml";
    private Alert alert = new Alert();
    private Style style = new Style();
    private String danger = "";
    private String displaySave = "";
    private String displayEditOrNuller = "";
    private Boolean disable = true;
    private String DisplayCheckCodeMeli = "";
    private String vazMeliCode = "";
    private boolean showVazMeliCode = true;
    private Personel selectionTable;
    private Boolean disableEdit = true;
    private Boolean disableNoeEstekhdam = false;

    private List<Jensiyat> jensiyatList = new ArrayList<>();
    private List<VazeyatTaahol> vazeyatTaaholList = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdamList = new ArrayList<>();
    private List<Madrak> madrakList = new ArrayList<>();
    private List<Tabaghe> tabagheList = new ArrayList<>();
    private List<Dayere> dayereList = new ArrayList<>();
    private List<Bakhsh> bakhshList = new ArrayList<>();
    private List<Semat> sematList = new ArrayList<>();
    private List<Daraje> darajeList = new ArrayList<>();
    private List<Raste> rasteList = new ArrayList<>();
    private List<Hoviyat> hoviyatList = new ArrayList<>();
    private List<Yegan> yeganList = new ArrayList<>();
    private List<FarmandehiMostaghel> farmandehiMostaghelList = new ArrayList<>();
    private List<Goroh> gorohList = new ArrayList<>();
    private Long jensiyatId;
    private Long vazeyatTaaholId;
    private Long noeEstekhdamId;
    private Long madrakId;
    private Long tabagheId;
    private Long dayereId;
    private Long bakhshId;
    private Long sematId;
    private Long darajeId;
    private Long rasteId;
    private Long hoviyatId;
    private Long yeganId;
    private Long farmandehiMostaghelId;
    private Long gorohId;

    private long mojodi;

    private Part avatar;

    private String emroz = "";
    private String styleEngheza = "color: block;";
    private long countEngheza = 0;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new ULocale("en", "IR"));
    private Calendar today = Calendar.getInstance();

    /////
    private Permission permission;
    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    ///////
    public PersonelBean() {

        URL = IndexBean.url;

        permissions = IndexBean.permissions;

        if (permissions.size() > 0) {
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        insertToTable();
    }

    private void insertToTable() {
        setEmroz(dateFormat.format(today.getTime()));
        FillList fillList = new FillList();
        dayereList = fillList.dayeres(permissions.get(0));
        noeEstekhdamList = fillList.noeEstekhdams(permissions.get(0));
        bakhshList = fillList.bakhsh(permissions.get(0));
        personelList = fillList.personels(permissions.get(0), "", "", "", "");
        Session session = HibernateUtil.getSession();
        jensiyatList = session.createQuery("from Jensiyat ").list();
        vazeyatTaaholList = session.createQuery("from VazeyatTaahol").list();
        madrakList = session.createQuery("from Madrak").list();
        tabagheList = session.createQuery("from Tabaghe").list();
        dayereList = session.createQuery("from Dayere").list();
        bakhshList = session.createQuery("from Bakhsh").list();
        sematList = session.createQuery("from Semat").list();
        darajeList = session.createQuery("from Daraje").list();
        rasteList = session.createQuery("from Raste").list();
        hoviyatList = session.createQuery("from Hoviyat").list();
        yeganList = session.createQuery("from Yegan where code='0050000133' OR code='0051000018' OR code='0052000056'").list();
        farmandehiMostaghelList = session.createQuery("from FarmandehiMostaghel").list();
        gorohList = session.createQuery("from Goroh").list();
        String mounth = String.valueOf(Integer.parseInt(emroz.substring(5, 7)) + 1);
        String nextMounth = emroz.substring(0, 4) + "/" + mounth + "/" + emroz.substring(8, 10);
        countEngheza = (long) session.createQuery("select COUNT(id) from Personel where '" + emroz + "'<=tarikhEngheza and tarikhEngheza <='" + nextMounth + "'").list().get(0);
        session.close();
        mojodi = personelList.size();


    }


    public void checkPersonel(String shp, String shoKart) {
        long noeEstekhdam = noeEstekhdamId;
        nuller();
        if (!shp.equals("")) {
            personel.setShomarePersoneli(shp);
        }
        if (!shoKart.equals("")) {
            personel.setShomareKart(shoKart);
        }
        noeEstekhdamId = noeEstekhdam;
        List<Personel> testPList = new ArrayList<>();

        if (personel.getShomarePersoneli()!=null) {
            if (personel.getShomarePersoneli().length() == 9) {
                Session session = HibernateUtil.getSession();
                testPList = session.createQuery("from Personel  where active=true AND shomarePersoneli='" + personel.getShomarePersoneli() + "'").list();
                session.close();
            }
        } else if (personel.getShomareKart().length() == 4 || personel.getShomareKart().length() == 5) {
            Session session = HibernateUtil.getSession();
            testPList = session.createQuery("from Personel where active=true AND  shomareKart='" + personel.getShomareKart() + "'").list();
            session.close();
        }

        if (testPList.size() > 0) {
            danger = style.dangerRedText();
            setDisplayEditOrNuller(style.displayBlock());
            setDisplaySave(style.displayNone());
            setPersonel(testPList.get(0));
            if (personel.getJensiyat() != null) {
                jensiyatId = personel.getJensiyat().getId();
            }
            if (personel.getVazeyatTaahol() != null) {
                vazeyatTaaholId = personel.getVazeyatTaahol().getId();
            }
            if (personel.getNoeEstekhdam() != null) {
                noeEstekhdamId = personel.getNoeEstekhdam().getId();
            }
            if (personel.getMadrak() != null) {
                madrakId = personel.getMadrak().getId();
            }
            if (personel.getNoeTabaghe() != null) {
                tabagheId = personel.getNoeTabaghe().getId();
            }
            if (personel.getDayere() != null) {
                dayereId = personel.getDayere().getId();
            }
            if (personel.getBakhsh() != null) {
                bakhshId = personel.getBakhsh().getId();
            }
            if (personel.getSemat() != null) {
                sematId = personel.getSemat().getId();
            }
            if (personel.getDaraje() != null) {
                darajeId = personel.getDaraje().getId();
            }
            if (personel.getRaste() != null) {
                rasteId = personel.getRaste().getId();
            }
            if (personel.getHoviyat() != null) {
                hoviyatId = personel.getHoviyat().getId();
            }
            if (personel.getYegan() != null) {
                yeganId = personel.getYegan().getId();
            }
            if (personel.getFarmandehiMostaghel() != null) {
                farmandehiMostaghelId = personel.getFarmandehiMostaghel().getId();
            }
            if (personel.getGorohKari() != null) {
                gorohId = personel.getGorohKari().getId();
            }
        } else {
            setDisable(false);
            danger = style.successText();
            setDisplayEditOrNuller(style.displayNone());
            setDisplaySave(style.displayBlock());
        }
    }


    public String checkEngheza(String engheza) {
        if (engheza != null) {
            if (!engheza.equals("") && engheza.length() == 10) {
                String test = (emroz.substring(0, 4));
                String test3 = (engheza.substring(0, 4));
                String test2 = engheza.substring(5, 7);
                if (emroz.substring(0, 4).equals(engheza.substring(0, 4))) {
                    int i = Integer.parseInt(emroz.substring(5, 7)) + 1;
                    if (Objects.equals(emroz.substring(5, 7), "12")) {
                        i = 1;
                        if (engheza.substring(5, 7).equals(String.valueOf(i))) {
                            setStyleEngheza("color: red;");
                            return engheza;
                        }
                    } else if (engheza.substring(5, 7).equals(String.valueOf(i))) {
                        setStyleEngheza("color: red;");
                        return engheza;
                    } else if (emroz.substring(5, 7).equals(engheza.substring(5, 7))) {
                        setStyleEngheza("color: red;");
                        return engheza;
                    }
                }
            } else {
                setStyleEngheza("color:block;");
                return engheza;
            }
        } else {
            setStyleEngheza("color:block;");
            return engheza;
        }
        setStyleEngheza("color:block;");
        return engheza;

    }

    public String checkCodeMeli(String codeMeli) {
        Checking checking = new Checking();
        Boolean check = checking.checkCodeMeli(codeMeli);
        if (check) {
            setDisplayCheckCodeMeli(style.successText());
            setVazMeliCode("تایید");
            setDisplaySave(style.displayBlock());
            List<Personel> testPList2 = new ArrayList<>();
            Session session = HibernateUtil.getSession();
            testPList2 = session.createQuery("from Personel  where active=1 AND codeMeli='" + codeMeli + "'").list();
            session.close();
            if (testPList2.size() > 0) {
                danger = style.dangerRedText();
                setDisplayEditOrNuller(style.displayBlock());
                setDisplaySave(style.displayNone());
                setPersonel(testPList2.get(0));
                if (personel.getJensiyat() != null) {
                    jensiyatId = personel.getJensiyat().getId();
                }
                if (personel.getVazeyatTaahol() != null) {
                    vazeyatTaaholId = personel.getVazeyatTaahol().getId();
                }
                if (personel.getNoeEstekhdam() != null) {
                    noeEstekhdamId = personel.getNoeEstekhdam().getId();
                }
                if (personel.getMadrak() != null) {
                    madrakId = personel.getMadrak().getId();
                }
                if (personel.getNoeTabaghe() != null) {
                    tabagheId = personel.getNoeTabaghe().getId();
                }
                if (personel.getDayere() != null) {
                    dayereId = personel.getDayere().getId();
                }
                if (personel.getBakhsh() != null) {
                    bakhshId = personel.getBakhsh().getId();
                }
                if (personel.getSemat() != null) {
                    sematId = personel.getSemat().getId();
                }
                if (personel.getDaraje() != null) {
                    darajeId = personel.getDaraje().getId();
                }
                if (personel.getRaste() != null) {
                    rasteId = personel.getRaste().getId();
                }
                if (personel.getHoviyat() != null) {
                    hoviyatId = personel.getHoviyat().getId();
                }
                if (personel.getYegan() != null) {
                    yeganId = personel.getYegan().getId();
                }
                if (personel.getFarmandehiMostaghel() != null) {
                    farmandehiMostaghelId = personel.getFarmandehiMostaghel().getId();
                }
                if (personel.getGorohKari() != null) {
                    gorohId = personel.getGorohKari().getId();
                }
            } else {
                setDisable(false);
                danger = style.successText();
                setDisplayEditOrNuller(style.displayNone());
                setDisplaySave(style.displayBlock());
            }
        } else {
            setDisplayCheckCodeMeli(style.dangerRedText());
            setVazMeliCode("نامعتبر");
            setDisplaySave(new Style().displayNone());
        }
        return getDisplayCheckCodeMeli();
    }

    public void checkNoeEstekhdam(long id) {
        nuller();
        setNoeEstekhdamId(id);
        List<Personel> personelList = new ArrayList<>();
        Session session0 = HibernateUtil.getSession();
        personelList = session0.createQuery(" from Personel where active=true AND  shomarePersoneli is not null order by shomareKart Desc  ").list();
        String shKart = personelList.get(0).getShomareKart();
        int shkart = Integer.parseInt(shKart) + 1;
        session0.close();
        if (shkart == 9999) {
            shkart = shkart + 1;
        }
        if (id == 5) {
            personel.setShomareKart("9999");
        } else {
            personel.setShomareKart(String.valueOf(shkart));
        }

        personelList = new ArrayList<>();
        if (id == 4 || id == 7 || id == 5) {
            Session session = HibernateUtil.getSession();
            personelList = session.createQuery("from Personel where   ( noeEstekhdam.id = 7 or noeEstekhdam.id = 4 or noeEstekhdam.id = 5 ) order by shomarePersoneli Desc ").list();
            session.close();
            String shp = String.valueOf(Integer.parseInt(personelList.get(0).getShomarePersoneli()) + 1);
            if (shp.length() == 9) {
                personel.setShomarePersoneli(shp);
            } else if (shp.length() < 9) {
                personel.setShomarePersoneli("0" + shp);
            }
        }
        setDisableEdit(false);
    }

    public void save() {
        Personel model = new Personel();
        model = personel;
        if (jensiyatId != null) {
            for (Jensiyat obj : jensiyatList) {
                if (obj.getId() == jensiyatId) {
                    model.setJensiyat(obj);
                }
            }
        }
        if (vazeyatTaaholId != null) {
            for (VazeyatTaahol obj : vazeyatTaaholList) {
                if (obj.getId() == vazeyatTaaholId) {
                    model.setVazeyatTaahol(obj);
                    break;
                }
            }

        }
        if (noeEstekhdamId != null) {
            for (NoeEstekhdam obj : noeEstekhdamList) {
                if (obj.getId() == noeEstekhdamId) {
                    model.setNoeEstekhdam(obj);
                    break;
                }
            }
        }
        if (madrakId != null) {
            for (Madrak obj : madrakList) {
                if (obj.getId() == madrakId) {
                    model.setMadrak(obj);
                    break;
                }
            }

        }
        if (tabagheId != null) {
            for (Tabaghe obj : tabagheList) {
                if (obj.getId() == tabagheId) {
                    model.setNoeTabaghe(obj);
                    break;
                }
            }
        }
        if (dayereId != null) {
            for (Dayere obj : dayereList) {
                if (obj.getId() == dayereId) {
                    model.setDayere(obj);
                    break;
                }
            }

        }
        if (bakhshId != null) {
            for (Bakhsh obj : bakhshList) {
                if (obj.getId() == bakhshId) {
                    model.setBakhsh(obj);
                    break;
                }
            }
        }
        if (sematId != null) {
            for (Semat obj : sematList) {
                if (obj.getId() == sematId) {
                    model.setSemat(obj);
                    break;
                }
            }

        }
        if (darajeId != null) {
            for (Daraje obj : darajeList) {
                if (obj.getId() == darajeId) {
                    model.setDaraje(obj);
                    break;
                }
            }
        }
        if (rasteId != null) {
            for (Raste obj : rasteList) {
                if (obj.getId() == rasteId) {
                    model.setRaste(obj);
                    break;
                }
            }

        }
        if (hoviyatId != null) {
            for (Hoviyat obj : hoviyatList) {
                if (obj.getId() == hoviyatId) {
                    model.setHoviyat(obj);
                    break;
                }
            }
        }
        if (yeganId != null) {
            for (Yegan obj : yeganList) {
                if (obj.getId() == yeganId) {
                    model.setYegan(obj);
                    break;
                }
            }

        }
        if (farmandehiMostaghelId != null) {
            for (FarmandehiMostaghel obj : farmandehiMostaghelList) {
                if (obj.getId() == farmandehiMostaghelId) {
                    model.setFarmandehiMostaghel(obj);
                    break;
                }
            }
        }
        if (gorohId != null) {
            for (Goroh obj : gorohList) {
                if (obj.getId() == gorohId) {
                    model.setGorohKari(obj);
                    break;
                }
            }
        }

        model.setAxe(saveAvatar());

        try {
            PersonelDao.getInstance().getBaseQuery().create(model, URL);
            personelList.add(personel);
            alert.successSave();
            nuller();
        } catch (Exception e) {
            alert.unSuccessSave();
        }
    }

    public void dispach() {
        setDisableEdit(true);
        setDisableNoeEstekhdam(true);
        setDisable(false);
        setDisplaySave(style.displayNone());
        personel = new Personel();
        personel = selectionTable;
        if (personel.getNoeEstekhdam() != null) {
            setNoeEstekhdamId(personel.getNoeEstekhdam().getId());
        }
        if (personel.getJensiyat() != null) {
            jensiyatId = personel.getJensiyat().getId();
        }
        if (personel.getVazeyatTaahol() != null) {
            vazeyatTaaholId = personel.getVazeyatTaahol().getId();
        }
        if (personel.getNoeEstekhdam() != null) {
            noeEstekhdamId = personel.getNoeEstekhdam().getId();
        }
        if (personel.getMadrak() != null) {
            madrakId = personel.getMadrak().getId();
        }
        if (personel.getNoeTabaghe() != null) {
            tabagheId = personel.getNoeTabaghe().getId();
        }
        if (personel.getDayere() != null) {
            dayereId = personel.getDayere().getId();
        }
        if (personel.getBakhsh() != null) {
            bakhshId = personel.getBakhsh().getId();
        }
        if (personel.getSemat() != null) {
            sematId = personel.getSemat().getId();
        }
        if (personel.getDaraje() != null) {
            darajeId = personel.getDaraje().getId();
        }
        if (personel.getRaste() != null) {
            rasteId = personel.getRaste().getId();
        }
        if (personel.getHoviyat() != null) {
            hoviyatId = personel.getHoviyat().getId();
        }
        if (personel.getYegan() != null) {
            yeganId = personel.getYegan().getId();
        }
        if (personel.getFarmandehiMostaghel() != null) {
            farmandehiMostaghelId = personel.getFarmandehiMostaghel().getId();
        }
        if (personel.getGorohKari() != null) {
            gorohId = personel.getGorohKari().getId();
        }
    }

    public void edit() {
        Personel model = personel;
        if (jensiyatId != null) {
            for (Jensiyat obj : jensiyatList) {
                if (obj.getId() == jensiyatId) {
                    model.setJensiyat(obj);
                    break;
                }
            }
        }
        if (vazeyatTaaholId != null) {
            for (VazeyatTaahol obj : vazeyatTaaholList) {
                if (obj.getId() == vazeyatTaaholId) {
                    model.setVazeyatTaahol(obj);
                    break;
                }
            }

        }
        if (noeEstekhdamId != null) {
            for (NoeEstekhdam obj : noeEstekhdamList) {
                if (obj.getId() == noeEstekhdamId) {
                    model.setNoeEstekhdam(obj);
                    break;
                }
            }
        }
        if (madrakId != null) {
            for (Madrak obj : madrakList) {
                if (obj.getId() == madrakId) {
                    model.setMadrak(obj);
                    break;
                }
            }

        }
        if (tabagheId != null) {
            for (Tabaghe obj : tabagheList) {
                if (obj.getId() == tabagheId) {
                    model.setNoeTabaghe(obj);
                    break;
                }
            }
        }
        if (dayereId != null) {
            for (Dayere obj : dayereList) {
                if (obj.getId() == dayereId) {
                    model.setDayere(obj);
                    break;
                }
            }

        }
        if (bakhshId != null) {
            for (Bakhsh obj : bakhshList) {
                if (obj.getId() == bakhshId) {
                    model.setBakhsh(obj);
                    break;
                }
            }
        }
        if (sematId != null) {
            for (Semat obj : sematList) {
                if (obj.getId() == sematId) {
                    model.setSemat(obj);
                    break;
                }
            }

        }
        if (darajeId != null) {
            for (Daraje obj : darajeList) {
                if (obj.getId() == darajeId) {
                    model.setDaraje(obj);
                    break;
                }
            }
        }
        if (rasteId != null) {
            for (Raste obj : rasteList) {
                if (obj.getId() == rasteId) {
                    model.setRaste(obj);
                    break;
                }
            }

        }
        if (hoviyatId != null) {
            for (Hoviyat obj : hoviyatList) {
                if (obj.getId() == hoviyatId) {
                    model.setHoviyat(obj);
                    break;
                }
            }
        }
        if (yeganId != null) {
            for (Yegan obj : yeganList) {
                if (obj.getId() == yeganId) {
                    model.setYegan(obj);
                    break;
                }
            }

        }
        if (farmandehiMostaghelId != null) {
            for (FarmandehiMostaghel obj : farmandehiMostaghelList) {
                if (obj.getId() == farmandehiMostaghelId) {
                    model.setFarmandehiMostaghel(obj);
                    break;
                }
            }
        }
        if (gorohId != null) {
            for (Goroh obj : gorohList) {
                if (obj.getId() == gorohId) {
                    model.setGorohKari(obj);
                    break;
                }
            }
        }

        model.setAxe(saveAvatar());

        try {
            PersonelDao.getInstance().getBaseQuery().createOrUpdate(model, URL);
            alert.successEdit();
            nuller();

        } catch (Exception e) {
            alert.unSuccessEdit();
        }
    }

    private String saveAvatar() {
        if (personel != null) {
            if (avatar != null) {
                //گرفتن مسیر پروژه در سرور
                String projectPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat("/../pic/avatars/");
                //ساخت فایل مقصد ذخیره سازی
                File destionation = new File(projectPath + "/" + personel.getShomarePersoneli() + ".jpg");
                File enc = new File(projectPath + "/" + personel.getShomarePersoneli() + "_enc.jpg");
                try {
                    //کپی کردن فایل به مقصد
                    InputStream inputStream = avatar.getInputStream();
                    Files.copy(inputStream, destionation.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    //ویرایش کردن فیلد عکس پرسنلی در پایگاه داده به نام شماره پرسنلی و مسیر ذخیره فایل عکس
                    Pic pic = new Pic();
                    pic.encrypt(destionation, enc);

                    Files.delete(destionation.toPath());
                    return "/../pic/avatars/" + personel.getShomarePersoneli() + "_enc.jpg";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void nuller() {
        personel = new Personel();
        danger = "";
        displaySave = "";
        displayEditOrNuller = "";
        disable = true;
        DisplayCheckCodeMeli = "";
        disableNoeEstekhdam = false;
        vazMeliCode = "";
        showVazMeliCode = true;
        jensiyatId = null;
        vazeyatTaaholId = null;
        noeEstekhdamId = null;
        madrakId = null;
        tabagheId = null;
        dayereId = null;
        bakhshId = null;
        sematId = null;
        darajeId = null;
        rasteId = null;
        hoviyatId = null;
        yeganId = null;
        farmandehiMostaghelId = null;
        gorohId = null;
    }


    public void cancelDelete() {
        personel = null;
    }

    public void delete() {
        try {
            PersonelDao.getInstance().getBaseQuery().delete(personel, URL);
            personelList.remove(personel);
            alert.successDelete();
            nuller();
        } catch (Exception ex) {
            alert.unSuccessDelete();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////

    public List<Personel> getPersonelList() {
        return personelList;
    }

    public void setPersonelList(List<Personel> personelList) {
        this.personelList = personelList;
    }

    public List<Personel> getFilteredPersonel() {
        return filteredPersonel;
    }

    public void setFilteredPersonel(List<Personel> filteredPersonel) {
        this.filteredPersonel = filteredPersonel;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getDisplaySave() {
        return displaySave;
    }

    public void setDisplaySave(String displaySave) {
        this.displaySave = displaySave;
    }

    public String getDisplayEditOrNuller() {
        return displayEditOrNuller;
    }

    public void setDisplayEditOrNuller(String displayEditOrNuller) {
        this.displayEditOrNuller = displayEditOrNuller;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getDisplayCheckCodeMeli() {
        return DisplayCheckCodeMeli;
    }

    public void setDisplayCheckCodeMeli(String displayCheckCodeMeli) {
        DisplayCheckCodeMeli = displayCheckCodeMeli;
    }

    public String getVazMeliCode() {
        return vazMeliCode;
    }

    public void setVazMeliCode(String vazMeliCode) {
        this.vazMeliCode = vazMeliCode;
    }

    public boolean isShowVazMeliCode() {
        return showVazMeliCode;
    }

    public void setShowVazMeliCode(boolean showVazMeliCode) {
        this.showVazMeliCode = showVazMeliCode;
    }

    public List<Jensiyat> getJensiyatList() {
        return jensiyatList;
    }

    public void setJensiyatList(List<Jensiyat> jensiyatList) {
        this.jensiyatList = jensiyatList;
    }

    public List<VazeyatTaahol> getVazeyatTaaholList() {
        return vazeyatTaaholList;
    }

    public void setVazeyatTaaholList(List<VazeyatTaahol> vazeyatTaaholList) {
        this.vazeyatTaaholList = vazeyatTaaholList;
    }

    public List<NoeEstekhdam> getNoeEstekhdamList() {
        return noeEstekhdamList;
    }

    public void setNoeEstekhdamList(List<NoeEstekhdam> noeEstekhdamList) {
        this.noeEstekhdamList = noeEstekhdamList;
    }

    public List<Madrak> getMadrakList() {
        return madrakList;
    }

    public void setMadrakList(List<Madrak> madrakList) {
        this.madrakList = madrakList;
    }

    public List<Tabaghe> getTabagheList() {
        return tabagheList;
    }

    public void setTabagheList(List<Tabaghe> tabagheList) {
        this.tabagheList = tabagheList;
    }

    public List<Dayere> getDayereList() {
        return dayereList;
    }

    public void setDayereList(List<Dayere> dayereList) {
        this.dayereList = dayereList;
    }

    public List<Bakhsh> getBakhshList() {
        return bakhshList;
    }

    public void setBakhshList(List<Bakhsh> bakhshList) {
        this.bakhshList = bakhshList;
    }

    public List<Semat> getSematList() {
        return sematList;
    }

    public void setSematList(List<Semat> sematList) {
        this.sematList = sematList;
    }

    public List<Daraje> getDarajeList() {
        return darajeList;
    }

    public void setDarajeList(List<Daraje> darajeList) {
        this.darajeList = darajeList;
    }

    public List<Raste> getRasteList() {
        return rasteList;
    }

    public void setRasteList(List<Raste> rasteList) {
        this.rasteList = rasteList;
    }

    public List<Hoviyat> getHoviyatList() {
        return hoviyatList;
    }

    public void setHoviyatList(List<Hoviyat> hoviyatList) {
        this.hoviyatList = hoviyatList;
    }

    public List<Yegan> getYeganList() {
        return yeganList;
    }

    public void setYeganList(List<Yegan> yeganList) {
        this.yeganList = yeganList;
    }

    public List<FarmandehiMostaghel> getFarmandehiMostaghelList() {
        return farmandehiMostaghelList;
    }

    public void setFarmandehiMostaghelList(List<FarmandehiMostaghel> farmandehiMostaghelList) {
        this.farmandehiMostaghelList = farmandehiMostaghelList;
    }

    public List<Goroh> getGorohList() {
        return gorohList;
    }

    public void setGorohList(List<Goroh> gorohList) {
        this.gorohList = gorohList;
    }

    public Long getJensiyatId() {
        return jensiyatId;
    }

    public void setJensiyatId(Long jensiyatId) {
        this.jensiyatId = jensiyatId;
    }

    public Long getVazeyatTaaholId() {
        return vazeyatTaaholId;
    }

    public void setVazeyatTaaholId(Long vazeyatTaaholId) {
        this.vazeyatTaaholId = vazeyatTaaholId;
    }

    public Long getNoeEstekhdamId() {
        return noeEstekhdamId;
    }

    public void setNoeEstekhdamId(Long noeEstekhdamId) {
        this.noeEstekhdamId = noeEstekhdamId;
    }

    public Long getMadrakId() {
        return madrakId;
    }

    public void setMadrakId(Long madrakId) {
        this.madrakId = madrakId;
    }

    public Long getTabagheId() {
        return tabagheId;
    }

    public void setTabagheId(Long tabagheId) {
        this.tabagheId = tabagheId;
    }

    public Long getDayereId() {
        return dayereId;
    }

    public void setDayereId(Long dayereId) {
        this.dayereId = dayereId;
    }

    public Long getBakhshId() {
        return bakhshId;
    }

    public void setBakhshId(Long bakhshId) {
        this.bakhshId = bakhshId;
    }

    public Long getSematId() {
        return sematId;
    }

    public void setSematId(Long sematId) {
        this.sematId = sematId;
    }

    public Long getDarajeId() {
        return darajeId;
    }

    public void setDarajeId(Long darajeId) {
        this.darajeId = darajeId;
    }

    public Long getRasteId() {
        return rasteId;
    }

    public void setRasteId(Long rasteId) {
        this.rasteId = rasteId;
    }

    public Long getHoviyatId() {
        return hoviyatId;
    }

    public void setHoviyatId(Long hoviyatId) {
        this.hoviyatId = hoviyatId;
    }

    public Long getYeganId() {
        return yeganId;
    }

    public void setYeganId(Long yeganId) {
        this.yeganId = yeganId;
    }

    public Long getFarmandehiMostaghelId() {
        return farmandehiMostaghelId;
    }

    public void setFarmandehiMostaghelId(Long farmandehiMostaghelId) {
        this.farmandehiMostaghelId = farmandehiMostaghelId;
    }

    public Long getGorohId() {
        return gorohId;
    }

    public void setGorohId(Long gorohId) {
        this.gorohId = gorohId;
    }

    public Personel getSelectionTable() {
        return selectionTable;
    }

    public void setSelectionTable(Personel selectionTable) {
        this.selectionTable = selectionTable;
    }

    public Boolean getDisableEdit() {
        return disableEdit;
    }

    public void setDisableEdit(Boolean disableEdit) {
        this.disableEdit = disableEdit;
    }

    public long getMojodi() {
        return mojodi;
    }

    public void setMojodi(long mojodi) {
        this.mojodi = mojodi;
    }

    public Boolean getDisableNoeEstekhdam() {
        return disableNoeEstekhdam;
    }

    public void setDisableNoeEstekhdam(Boolean disableNoeEstekhdam) {
        this.disableNoeEstekhdam = disableNoeEstekhdam;
    }

    public Part getAvatar() {
        return avatar;
    }

    public void setAvatar(Part avatar) {
        this.avatar = avatar;
    }

    public String getEmroz() {
        return emroz;
    }

    public void setEmroz(String emroz) {
        this.emroz = emroz;
    }

    public String getStyleEngheza() {
        return styleEngheza;
    }

    public void setStyleEngheza(String styleEngheza) {
        this.styleEngheza = styleEngheza;
    }

    public long getCountEngheza() {
        return countEngheza;
    }

    public void setCountEngheza(long countEngheza) {
        this.countEngheza = countEngheza;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean isCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(boolean createPermission) {
        this.createPermission = createPermission;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isUpdatePermission() {
        return updatePermission;
    }

    public void setUpdatePermission(boolean updatePermission) {
        this.updatePermission = updatePermission;
    }

    public boolean isDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(boolean deletePermission) {
        this.deletePermission = deletePermission;
    }
}