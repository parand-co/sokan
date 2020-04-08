package amar.bean;

import amar.model.Personel;
import amar.model.SavabeghJabejaeeGorohKari;
import amar.model.SavabeghTaradod;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dashboard.DashboardBean;
import dashboard.login.Login;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.*;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import manage.bean.IndexBean;
import omorkoliAndgharardadi.model.SavabeghEsterahatPezeshki;
import omorkoliAndgharardadi.model.SavabeghMamoriyatRozane;
import omorkoliAndgharardadi.model.SavabeghMorakhasi;
import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Session;
import org.primefaces.event.RowEditEvent;
import util.Convert;
import util.FillList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SavabeghTaradodBean implements Serializable {
    private String URL;
    private List<SavabeghTaradod> STlList = new ArrayList<>();
    private List<SavabeghTaradod> savabeghTaradods = new ArrayList<>();
    private List<SavabeghJabejaeeGorohKari> SJlist = new ArrayList<>();
    private List<MojavezRozane> mojavezRozanes;
    private Personel personel = new Personel();
    private List<Month> monthList = new ArrayList<>();
    private List<Goroh> gorohList = new ArrayList<>();
    private String codeMeliF = "";
    private String shKartF = "";
    private String shPF = "";
    private String month = "";
    private String beginDate = "";
    private String endDate = "";
    private String dayere = "";
    private String bakhsh = "";
    private String sal;

    private String vorod;
    private String khoroj;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emroz = simpleDateFormat.format(today.getTime());

    private String goroh;
    private Alert alert = new Alert();

    private Convert convert = new Convert();

    private List<Permission> permissions;
    private boolean createPermission;
    private boolean readPermission;
    private boolean updatePermission;
    private boolean deletePermission;

    private boolean disableChangeGroup = true;

    public SavabeghTaradodBean() {
        sal = emroz.substring(0,4);
        insertToList();
        insertTogoroh();

        Session session = HibernateUtil.getSession();
        mojavezRozanes = session.createQuery("FROM MojavezRozane ORDER BY roozaneOrSaati").list();
        session.close();

        permissions = IndexBean.permissions;

        URL = IndexBean.url;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }
    }

    private void insertToList() {
        Session session = HibernateUtil.getSession();
        monthList = session.createQuery("from Month").list();
        session.close();
    }

    private void insertTogoroh() {
        Session session = HibernateUtil.getSession();
        gorohList = session.createQuery("from Goroh ").list();
        session.close();
    }

    public void search() {

        FillList fillList = new FillList();
        List<Personel> personels = fillList.personels(permissions.get(0), shPF, codeMeliF, shKartF, "");

        List<Taghvim> taghvims;

        if(personels.size() > 0){
            if(emroz.substring(0, 7).equals(sal + "/" + month)){
                Session session = HibernateUtil.getSession();
                taghvims = session.createQuery("FROM Taghvim WHERE SUBSTRING(tarikh,1,7) >= ? AND tarikh <= ?").setString(0, sal + "/" + month).setString(1, emroz).list();
                session.close();
            } else {
                Session session = HibernateUtil.getSession();
                taghvims = session.createQuery("FROM Taghvim WHERE SUBSTRING(tarikh,1,7) = ?").setString(0, sal + "/" + month).list();
                session.close();
            }

            STlList.clear();
            savabeghTaradods.clear();
            String query = "FROM Taraddod WHERE SUBSTRING(taghvim.tarikh,1,7) = '" + sal + "/" + month + "'";
            StringBuilder builder = new StringBuilder();
            builder.append(query);
            if (!shKartF.equals("")) {
                builder.append(" AND personel.shomareKart=").append(shKartF);
            }
            if (!shPF.equals("")) {
                builder.append(" AND personel.shomarePersoneli='").append(shPF).append("'");
            }
            if (!codeMeliF.equals("")) {
                builder.append(" AND personel.codeMeli=").append(codeMeliF);
            }

            Session session1 = HibernateUtil.getSession();
            List<Taraddod> taraddods = session1.createQuery(builder.toString()).list();
            session1.close();

            if(taraddods.size() > 0){
                setPersonel(taraddods.get(0).getPersonel());
                if (taraddods.get(0).getPersonel().getDayere() != null) {
                    setDayere(taraddods.get(0).getPersonel().getDayere().getTitle());
                }
                if (taraddods.get(0).getPersonel().getBakhsh() != null) {
                    setBakhsh(taraddods.get(0).getPersonel().getBakhsh().getTitle());
                }

                SavabeghTaradod model = new SavabeghTaradod();
                String tarikh = null;

                int count = taraddods.size();
                for (Taghvim taghvim : taghvims) {
                    for (Taraddod taraddod : taraddods) {
                        Taraddod td = taraddods.stream().filter(o -> o.getTaghvim().getTarikh().equals(taghvim.getTarikh())).findFirst().orElse(null);
                        if(td != null && count != 0){
                            count--;
                            if(tarikh == null || tarikh.equals("")){
                                tarikh = taraddod.getTaghvim().getTarikh();
                            }
                            if(!tarikh.equals(taraddod.getTaghvim().getTarikh())){
                                STlList.add(model);
                                savabeghTaradods.add((SavabeghTaradod) SerializationUtils.clone(model));
                                model = new SavabeghTaradod();
                                tarikh = taraddod.getTaghvim().getTarikh();
                            }

                            model.setPersonel(taraddod.getPersonel());
                            model.setTaghvim(taraddod.getTaghvim());

                            if(model.getSaat1() == null){
                                model.setGoroh(taraddod.getGoroh());
                                if(taraddod.getShomareDastgah() != null) {
                                    model.setShomareDastgah(taraddod.getShomareDastgah());
                                }
                                model.setSaat1(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                model.setVazeyatTaraddod(taraddod.getVaziat());
                                continue;
                            }

                            if(model.getSaat2() == null){
                                model.setSaat2(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                model.setVazeyatKhoroj(taraddod.getVaziat());
                                continue;
                            }

                            if(model.getSaat3() == null){
                                model.setSaat3(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat4() == null){
                                model.setSaat4(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat5() == null){
                                model.setSaat5(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat6() == null){
                                model.setSaat6(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat7() == null){
                                model.setSaat7(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat8() == null){
                                model.setSaat8(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat9() == null){
                                model.setSaat9(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat10() == null){
                                model.setSaat10(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat11() == null){
                                model.setSaat11(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                                continue;
                            }

                            if(model.getSaat12() == null){
                                model.setSaat12(convert.clockStr(taraddod.getSaat(), taraddod.isEdited()));
                            }
                        } else {
                            SavabeghTaradod st = STlList.stream().filter(o -> o.getTaghvim().getTarikh().equals(taghvim.getTarikh())).findFirst().orElse(null);
                            if(st != null){
                                continue;
                            }
                            model.setGoroh(taraddods.get(0).getGoroh());
                            model.setPersonel(taraddods.get(0).getPersonel());
                            model.setTaghvim(taghvim);
                            model.setShomareDastgah(0);
                            if(taghvim.getDayType().getId() == 3){
                                model.setVazeyatTaraddod(mojavezRozanes.stream().filter(o -> o.getTitle().equals("تعطیل")).findFirst().orElse(null));
                            } else {
                                model.setVazeyatTaraddod(mojavezRozanes.stream().filter(o -> o.getTitle().equals("نهست")).findFirst().orElse(null));
                            }

                            STlList.add(model);
                            savabeghTaradods.add((SavabeghTaradod) SerializationUtils.clone(model));
                            model = new SavabeghTaradod();
                        }
                    }

                    if(model.getSaat1() != null){
                        STlList.add(model);
                        savabeghTaradods.add((SavabeghTaradod) SerializationUtils.clone(model));
                        model = new SavabeghTaradod();
                    }
                }
                disableChangeGroup = false;
            } else {
                disableChangeGroup = true;
                alert.unSuccessSearch();
            }
        } else {
            disableChangeGroup = true;
            alert.unSuccessSearch();
        }
    }

    public String colorRows(MojavezRozane taraddod) {
        if(taraddod != null){
            if(taraddod.isRoozaneOrSaati()){
                if(taraddod.getId() == 5){
                    return "#f32c2c";
                } else if(taraddod.getId() == 9){
                    return "#8b2cf3";
                } else if (taraddod.getId() == 10){
                    return "#2ff32c";
                } else if (taraddod.getId() == 11){
                    return "#2196f3";
                } else {
                    return "#ff9800";
                }
            }
            if (taraddod.getId() == 4 || taraddod.getId() == 12){
                return "red";
            }
        }
        return "#414141";
    }

    public String colorClock(String saat){
        if(saat != null && !saat.equals("")){
            if(saat.contains("*")){
                return "#2f887f";
            }
        }
        return "#414141";
    }

    public void taradodEdit(RowEditEvent editEvent) {
        SavabeghTaradod model = (SavabeghTaradod) editEvent.getObject();

        Integer id = null;
        String saat = null;
        MojavezRozane vaziyatVorod = null;
        MojavezRozane vaziyatKhoroj = null;

        for (SavabeghTaradod savabeghTaradod : savabeghTaradods){
            if (savabeghTaradod.getPersonel().getShomarePersoneli().equals(model.getPersonel().getShomarePersoneli()) &&
                    savabeghTaradod.getTaghvim().getTarikh().equals(model.getTaghvim().getTarikh())) {
                id = null;
                if(savabeghTaradod.getVazeyatTaraddod() != null){
                    if(model.getVazeyatTaraddod() != null){
                        if(!model.getVazeyatTaraddod().getCode().equals(savabeghTaradod.getVazeyatTaraddod().getCode()) || (vorod != null && !vorod.equals(""))){
                            vaziyatVorod = mojavezRozanes.stream().filter(o -> o.getCode().equals(vorod)).findFirst().orElse(null);
                        }
                    } else {
                        vaziyatVorod = mojavezRozanes.stream().filter(o -> o.getCode().equals(vorod)).findFirst().orElse(null);
                    }
                } else {
                    if(vorod != null && !vorod.equals("")){
                        vaziyatVorod = mojavezRozanes.stream().filter(o -> o.getCode().equals(vorod)).findFirst().orElse(null);
                    }
                }
                if(savabeghTaradod.getVazeyatKhoroj() != null) {
                    if(model.getVazeyatKhoroj() != null){
                        if (!model.getVazeyatKhoroj().getCode().equals(savabeghTaradod.getVazeyatKhoroj().getCode()) || (khoroj != null && !khoroj.equals(""))) {
                            vaziyatKhoroj = mojavezRozanes.stream().filter(o -> o.getCode().equals(khoroj)).findFirst().orElse(null);
                        }
                    } else {
                        vaziyatKhoroj = mojavezRozanes.stream().filter(o -> o.getCode().equals(khoroj)).findFirst().orElse(null);
                    }
                } else {
                    if (khoroj != null && !khoroj.equals("")) {
                        vaziyatKhoroj = mojavezRozanes.stream().filter(o -> o.getCode().equals(khoroj)).findFirst().orElse(null);
                    }
                }

                if (!model.getSaat1().equals(savabeghTaradod.getSaat1()) && !model.getSaat1().equals("")) {
                    id = 0;
                    saat = model.getSaat1();
                    continue;
                } else if(model.getSaat1().equals(savabeghTaradod.getSaat1()) && !model.getSaat1().equals("")){
                    id = 0;
                    saat = model.getSaat1();
                    continue;
                }
                if (!model.getSaat2().equals(savabeghTaradod.getSaat2()) && !model.getSaat2().equals("")) {
                    id = 1;
                    saat = model.getSaat2();
                    continue;
                } else if(model.getSaat2().equals(savabeghTaradod.getSaat2()) && !model.getSaat2().equals("")){
                    id = 1;
                    saat = model.getSaat2();
                    continue;
                }
                if (!model.getSaat3().equals(savabeghTaradod.getSaat3()) && !model.getSaat3().equals("")) {
                    id = 2;
                    saat = model.getSaat3();
                    continue;
                } else if(model.getSaat3().equals(savabeghTaradod.getSaat3()) && !model.getSaat3().equals("")){
                    id = 2;
                    saat = model.getSaat3();
                    continue;
                }
                if (!model.getSaat4().equals(savabeghTaradod.getSaat4()) && !model.getSaat4().equals("")) {
                    id = 3;
                    saat = model.getSaat4();
                    continue;
                } else if(model.getSaat4().equals(savabeghTaradod.getSaat4()) && !model.getSaat4().equals("")){
                    id = 3;
                    saat = model.getSaat4();
                    continue;
                }
                if (!model.getSaat5().equals(savabeghTaradod.getSaat5()) && !model.getSaat5().equals("")) {
                    id = 4;
                    saat = model.getSaat5();
                    continue;
                } else if(model.getSaat5().equals(savabeghTaradod.getSaat5()) && !model.getSaat5().equals("")){
                    id = 4;
                    saat = model.getSaat5();
                    continue;
                }
                if (!model.getSaat6().equals(savabeghTaradod.getSaat6()) && !model.getSaat6().equals("")) {
                    id = 5;
                    saat = model.getSaat6();
                    continue;
                } else if(model.getSaat6().equals(savabeghTaradod.getSaat6()) && !model.getSaat6().equals("")){
                    id = 5;
                    saat = model.getSaat6();
                    continue;
                }
                if (!model.getSaat7().equals(savabeghTaradod.getSaat7()) && !model.getSaat7().equals("")) {
                    id = 6;
                    saat = model.getSaat7();
                    continue;
                } else if(model.getSaat7().equals(savabeghTaradod.getSaat7()) && !model.getSaat7().equals("")){
                    id = 6;
                    saat = model.getSaat7();
                    continue;
                }
                if (!model.getSaat8().equals(savabeghTaradod.getSaat8()) && !model.getSaat8().equals("")) {
                    id = 7;
                    saat = model.getSaat8();
                    continue;
                } else if(model.getSaat8().equals(savabeghTaradod.getSaat8()) && !model.getSaat8().equals("")){
                    id = 7;
                    saat = model.getSaat8();
                    continue;
                }
                if (!model.getSaat9().equals(savabeghTaradod.getSaat9()) && !model.getSaat9().equals("")) {
                    id = 8;
                    saat = model.getSaat9();
                    continue;
                } else if(model.getSaat9().equals(savabeghTaradod.getSaat9()) && !model.getSaat9().equals("")){
                    id = 8;
                    saat = model.getSaat9();
                    continue;
                }
                if (!model.getSaat10().equals(savabeghTaradod.getSaat10()) && !model.getSaat10().equals("")) {
                    id = 9;
                    saat = model.getSaat10();
                    continue;
                } else if(model.getSaat10().equals(savabeghTaradod.getSaat10()) && !model.getSaat10().equals("")){
                    id = 9;
                    saat = model.getSaat10();
                    continue;
                }
                if (!model.getSaat11().equals(savabeghTaradod.getSaat11()) && !model.getSaat11().equals("")) {
                    id = 10;
                    saat = model.getSaat11();
                    continue;
                } else if(model.getSaat11().equals(savabeghTaradod.getSaat11()) && !model.getSaat11().equals("")){
                    id = 10;
                    saat = model.getSaat11();
                    continue;
                }
                if (!model.getSaat12().equals(savabeghTaradod.getSaat12()) && !model.getSaat12().equals("")) {
                    id = 11;
                    saat = model.getSaat12();
                } else if(model.getSaat12().equals(savabeghTaradod.getSaat12()) && !model.getSaat12().equals("")){
                    id = 11;
                    saat = model.getSaat12();
                }
            }
        }

        Session session = HibernateUtil.getSession();
        List<Taraddod> taraddods = session.createQuery("FROM Taraddod WHERE personel.shomarePersoneli = ? AND taghvim.tarikh = ?")
                .setString(0,model.getPersonel().getShomarePersoneli())
                .setString(1,model.getTaghvim().getTarikh())
                .list();
        session.close();

        if(id != null){
            if(vaziyatVorod != null){
                Session session1 = HibernateUtil.getSession();
                Long countMorakhasi = (Long) session1.createQuery("SELECT count(*) FROM SavabeghMorakhasi WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ?")
                        .setLong(0, model.getPersonel().getId())
                        .setString(1, model.getTaghvim().getTarikh())
                        .setString(2, model.getTaghvim().getTarikh())
                        .uniqueResult();
                Long countMamoriyatRozane = (Long) session1.createQuery("SELECT count(*) FROM SavabeghMamoriyatRozane WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ? AND noeMamoriyat.id = 6")
                        .setLong(0, model.getPersonel().getId())
                        .setString(1, model.getTaghvim().getTarikh())
                        .setString(2, model.getTaghvim().getTarikh())
                        .uniqueResult();
                Long countMamoriyatSaati = (Long) session1.createQuery("SELECT count(*) FROM SavabeghMamoriyatRozane WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ? AND noeMamoriyat.id = 11")
                        .setLong(0, model.getPersonel().getId())
                        .setString(1, model.getTaghvim().getTarikh())
                        .setString(2, model.getTaghvim().getTarikh())
                        .uniqueResult();
                Long countEsterahatPezeshki = (Long) session1.createQuery("SELECT count(*) FROM SavabeghEsterahatPezeshki WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ?")
                        .setLong(0, model.getPersonel().getId())
                        .setString(1, model.getTaghvim().getTarikh())
                        .setString(2, model.getTaghvim().getTarikh())
                        .uniqueResult();
                session1.close();

                if(vaziyatVorod.getId() == 4){
                    if(countMorakhasi == 0){
                        createMorakhasi(model);
                    }
                    if(countMamoriyatRozane > 0){
                        deleteMamoriyatRozane(model);
                    }
                    if(countMamoriyatSaati > 0){
                        deleteMamoriyatSaati(model);
                    }
                    if(countEsterahatPezeshki > 0){
                        deleteEsterahatPezeshki(model);
                    }

                } else if(vaziyatVorod.getId() == 6){
                    if(countMorakhasi > 0){
                        deleteMorakhasi(model);
                    }
                    if(countMamoriyatRozane == 0){
                        createMamoriyatRozane(model);
                    }
                    if(countMamoriyatSaati > 0){
                        deleteMamoriyatSaati(model);
                    }
                    if(countEsterahatPezeshki > 0){
                        deleteEsterahatPezeshki(model);
                    }
                } else if(vaziyatVorod.getId() == 11){
                    if(countMorakhasi > 0){
                        deleteMorakhasi(model);
                    }
                    if(countMamoriyatRozane > 0){
                        deleteMamoriyatRozane(model);
                    }
                    if(countMamoriyatSaati == 0){
                        createMamoriyatSaati(model);
                    }
                    if(countEsterahatPezeshki > 0){
                        deleteEsterahatPezeshki(model);
                    }
                } else if(vaziyatVorod.getId() == 7){
                    if(countMorakhasi > 0){
                        deleteMorakhasi(model);
                    }
                    if(countMamoriyatRozane > 0){
                        deleteMamoriyatRozane(model);
                    }
                    if(countMamoriyatSaati > 0){
                        deleteMamoriyatSaati(model);
                    }
                    if(countEsterahatPezeshki == 0){
                        createEsterahatPezeshki(model);
                    }
                }
            }

            if(taraddods.size() > id){
                Taraddod taraddod = taraddods.get(id);
                if(saat != null && !saat.equals("")){
                    taraddod.setSaat(convert.clockInt(saat.replace("*", "").trim()));
                }
                if(id == 0){
                    if(vaziyatVorod != null) {
                        taraddod.setVaziat(vaziyatVorod);
                    }
                } else if (id == 1){
                    if(vaziyatKhoroj != null) {
                        taraddod.setVaziat(vaziyatKhoroj);
                    }
                }
                taraddod.setEdited(true);
                TaraddodDao.getInstance().getBaseQuery().createOrUpdate(taraddod, URL);
            } else {
                Taraddod taraddod = new Taraddod();
                taraddod.setPersonel(model.getPersonel());
                taraddod.setTaghvim(model.getTaghvim());
                taraddod.setShomareDastgah(0);
                taraddod.setGoroh(model.getGoroh());
                if(id == 0) {
                    if(vorod != null && !vorod.equals("")) {
                        taraddod.setVaziat(mojavezRozanes.stream().filter(o -> o.getCode().equals(vorod)).findFirst().orElse(null));
                    }
                } else if(id == 1) {
                    if(khoroj != null && !khoroj.equals("")) {
                        taraddod.setVaziat(mojavezRozanes.stream().filter(o -> o.getCode().equals(khoroj)).findFirst().orElse(null));
                    }
                } else {
                    taraddod.setVaziat(mojavezRozanes.stream().filter(o -> o.getTitle().equals("حاضر")).findFirst().orElse(null));
                }
                if(saat != null && !saat.equals("")){
                    taraddod.setSaat(convert.clockInt(saat.replace("*", "").trim()));
                }
                taraddod.setEdited(true);

                TaraddodDao.getInstance().getBaseQuery().create(taraddod, URL);
            }
        }

        vorod = null;
        khoroj = null;

        search();
    }

    private void deleteMorakhasi(SavabeghTaradod model){
        Session session = HibernateUtil.getSession();
        List<SavabeghMorakhasi> savabeghMorakhasis = session.createQuery("FROM SavabeghMorakhasi WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ?")
                .setLong(0, model.getPersonel().getId())
                .setString(1, model.getTaghvim().getTarikh())
                .setString(2, model.getTaghvim().getTarikh())
                .list();
        session.close();
        for (SavabeghMorakhasi savabeghMorakhasi : savabeghMorakhasis) {
            SavabeghMorakhasiDao.getInstance().getBaseQuery().delete(savabeghMorakhasi, URL);
        }
    }

    private void deleteMamoriyatRozane(SavabeghTaradod model){
        Session session = HibernateUtil.getSession();
        List<SavabeghMamoriyatRozane> savabeghMamoriyatRozanes = session.createQuery("FROM SavabeghMamoriyatRozane WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ? AND noeMamoriyat.id = 6")
                .setLong(0, model.getPersonel().getId())
                .setString(1, model.getTaghvim().getTarikh())
                .setString(2, model.getTaghvim().getTarikh())
                .list();
        session.close();
        for (SavabeghMamoriyatRozane savabeghMamoriyatRozane : savabeghMamoriyatRozanes) {
            SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().delete(savabeghMamoriyatRozane, URL);
        }
    }

    private void deleteMamoriyatSaati(SavabeghTaradod model){
        Session session = HibernateUtil.getSession();
        List<SavabeghMamoriyatRozane> savabeghMamoriyatRozanes = session.createQuery("FROM SavabeghMamoriyatRozane WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ? AND noeMamoriyat.id = 11")
                .setLong(0, model.getPersonel().getId())
                .setString(1, model.getTaghvim().getTarikh())
                .setString(2, model.getTaghvim().getTarikh())
                .list();
        session.close();
        for (SavabeghMamoriyatRozane savabeghMamoriyatRozane : savabeghMamoriyatRozanes) {
            SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().delete(savabeghMamoriyatRozane, URL);
        }
    }

    private void deleteEsterahatPezeshki(SavabeghTaradod model){
        Session session = HibernateUtil.getSession();
        List<SavabeghEsterahatPezeshki> savabeghEsterahatPezeshkis = session.createQuery("FROM SavabeghEsterahatPezeshki WHERE personel.id = ? AND tarikhShoro = ? AND tarikhPayan = ?")
                .setLong(0, model.getPersonel().getId())
                .setString(1, model.getTaghvim().getTarikh())
                .setString(2, model.getTaghvim().getTarikh())
                .list();
        session.close();
        for (SavabeghEsterahatPezeshki savabeghMamoriyatRozane : savabeghEsterahatPezeshkis) {
            SavabeghEsterahatPezeshkiDao.getInstance().getBaseQuery().delete(savabeghMamoriyatRozane, URL);
        }
    }

    private void createMorakhasi(SavabeghTaradod model){
        SavabeghMorakhasi savabeghMorakhasi = new SavabeghMorakhasi();

        Session session = HibernateUtil.getSession();
        NoeMorakhasi noeMorakhasi = (NoeMorakhasi) session.createQuery("FROM NoeMorakhasi WHERE id = 1").list().get(0);
        session.close();

        savabeghMorakhasi.setPersonel(model.getPersonel());
        savabeghMorakhasi.setNoeMorakhasi(noeMorakhasi);
        savabeghMorakhasi.setVazeyat(null);
        savabeghMorakhasi.setTarikhShoro(model.getTaghvim().getTarikh());
        savabeghMorakhasi.setTarikhPayan(model.getTaghvim().getTarikh());
        savabeghMorakhasi.setModat(1);
        savabeghMorakhasi.setTedadEstehghaghi(1);
        savabeghMorakhasi.setTarikhSabt(emroz);

        SavabeghMorakhasiDao.getInstance().getBaseQuery().create(savabeghMorakhasi, URL);
    }

    private void createMamoriyatRozane(SavabeghTaradod model){
        SavabeghMamoriyatRozane savabeghMamoriyatRozane = new SavabeghMamoriyatRozane();

        Session session = HibernateUtil.getSession();
        MojavezRozane mojavezRozane = (MojavezRozane) session.createQuery("FROM MojavezRozane WHERE id = 6").list().get(0);
        session.close();

        savabeghMamoriyatRozane.setPersonel(model.getPersonel());
        savabeghMamoriyatRozane.setTarikhShoro(model.getTaghvim().getTarikh());
        savabeghMamoriyatRozane.setTarikhPayan(model.getTaghvim().getTarikh());
        savabeghMamoriyatRozane.setModat(1);
        savabeghMamoriyatRozane.setNoeMamoriyat(mojavezRozane);
        savabeghMamoriyatRozane.setTarikhSabt(emroz);

        SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().create(savabeghMamoriyatRozane, URL);
    }

    private void createMamoriyatSaati(SavabeghTaradod model){
        SavabeghMamoriyatRozane savabeghMamoriyatRozane = new SavabeghMamoriyatRozane();

        Session session = HibernateUtil.getSession();
        MojavezRozane mojavezRozane = (MojavezRozane) session.createQuery("FROM MojavezRozane WHERE id = 11").list().get(0);
        session.close();

        savabeghMamoriyatRozane.setPersonel(model.getPersonel());
        savabeghMamoriyatRozane.setTarikhShoro(model.getTaghvim().getTarikh());
        savabeghMamoriyatRozane.setTarikhPayan(model.getTaghvim().getTarikh());
        savabeghMamoriyatRozane.setModat(1);
        savabeghMamoriyatRozane.setNoeMamoriyat(mojavezRozane);
        savabeghMamoriyatRozane.setTarikhSabt(emroz);

        SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().create(savabeghMamoriyatRozane, URL);
    }

    private void createEsterahatPezeshki(SavabeghTaradod model){
        SavabeghEsterahatPezeshki savabeghEsterahatPezeshki = new SavabeghEsterahatPezeshki();

        savabeghEsterahatPezeshki.setPersonel(model.getPersonel());
        savabeghEsterahatPezeshki.setTarikhShoro(model.getTaghvim().getTarikh());
        savabeghEsterahatPezeshki.setTarikhPayan(model.getTaghvim().getTarikh());
        savabeghEsterahatPezeshki.setTarikhSabt(emroz);
        savabeghEsterahatPezeshki.setTaeed(true);

        SavabeghEsterahatPezeshkiDao.getInstance().getBaseQuery().create(savabeghEsterahatPezeshki, URL);
    }

    public void searchsavabeghjabejaee() {
        SJlist = new ArrayList<>();
        String query = "from SavabeghJabejaeeGorohKari where SUBSTRING(tarikhSabt,1,7)='" + sal + "/" + month + "'";
        StringBuilder builder = new StringBuilder();
        builder.append(query);

        if (!shKartF.equals("")) {
            builder.append(" AND personel.shomareKart=").append(shKartF);
        }
        if (!shPF.equals("")) {
            builder.append(" AND personel.shomarePersoneli='").append(shPF).append("'");
        }
        if (!codeMeliF.equals("")) {
            builder.append(" AND personel.codeMeli=").append(codeMeliF);
        }

        Session session = HibernateUtil.getSession();
        SJlist = session.createQuery(builder.toString()).list();
        session.close();
    }

    public void saveJabejaee() {
        if(STlList != null && STlList.size() > 0){
            if(beginDate != null && !beginDate.equals("")){
                if(endDate != null && !endDate.equals("")){
                    if(goroh != null && !goroh.equals("")){
                        if(Integer.valueOf(beginDate.replace("/", "")) < Integer.valueOf(endDate.replace("/", ""))){
                            SavabeghJabejaeeGorohKari model = new SavabeghJabejaeeGorohKari();

                            model.setPersonel(personel);
                            model.setTarikhShoro(beginDate);
                            model.setTarikhPayan(endDate);
                            model.setTarikhSabt(emroz);
                            model.setModat(mohasebeModat(beginDate, endDate));
                            model.setGorohJadid(gorohList.stream().filter(o -> o.getCode().equals(goroh)).findFirst().orElse(null));
                            model.setGorohGhabli(personel.getGorohKari());

                            SavabeghJabejaeeGorohKariDao.getInstance().getBaseQuery().create(model, URL);
                            SJlist.add(model);

                            if(Integer.valueOf(beginDate.replace("/", "")) <= Integer.valueOf(emroz.replace("/", ""))){
                                Session session = HibernateUtil.getSession();
                                List<Taraddod> taraddods = session.createQuery("FROM Taraddod WHERE taghvim.tarikh >= ? AND taghvim.tarikh <= ? ").setString(0, beginDate).setString(1, endDate).list();
                                session.close();

                                for (Taraddod taraddod : taraddods) {
                                    taraddod.setGoroh(gorohList.stream().filter(o -> o.getCode().equals(goroh)).findFirst().orElse(null));
                                    TaraddodDao.getInstance().getBaseQuery().createOrUpdate(taraddod, URL);
                                }
                            }

                        } else {
                            alert.startDateIsBiger();
                        }
                    } else {
                        alert.fieldIsEmpty("گروه");
                    }
                } else {
                    alert.fieldIsEmpty("تاریخ پایان");
                }
            } else {
                alert.fieldIsEmpty("تاریخ شروع");
            }
        } else {
            alert.firstSearchPersonel();
        }
    }

    private String mohasebeModat(String start, String end){
        Integer a = Integer.valueOf(start.replace("/", ""));
        Integer b = Integer.valueOf(end.replace("/", ""));

        int c = b - a + 1;
        return String.valueOf(c);
    }

//////////////////////////////////////////////////////////////////////////////////////////

    public List<SavabeghTaradod> getSTlList() {
        return STlList;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setSTlList(List<SavabeghTaradod> STlList) {
        this.STlList = STlList;
    }

    public String getCodeMeliF() {
        return codeMeliF;
    }

    public void setCodeMeliF(String codeMeliF) {
        this.codeMeliF = codeMeliF;
    }

    public String getShKartF() {
        return shKartF;
    }

    public void setShKartF(String shKartF) {
        this.shKartF = shKartF;
    }

    public String getShPF() {
        return shPF;
    }

    public void setShPF(String shPF) {
        this.shPF = shPF;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public String getDayere() {
        return dayere;
    }

    public void setDayere(String dayere) {
        this.dayere = dayere;
    }

    public String getBakhsh() {
        return bakhsh;
    }

    public void setBakhsh(String bakhsh) {
        this.bakhsh = bakhsh;
    }

    public List<SavabeghJabejaeeGorohKari> getSJlist() {
        return SJlist;
    }

    public void setSJlist(List<SavabeghJabejaeeGorohKari> SJlist) {
        this.SJlist = SJlist;
    }

    public String getGoroh() {
        return goroh;
    }

    public void setGoroh(String goroh) {
        this.goroh = goroh;
    }

    public List<Goroh> getGorohList() {
        return gorohList;
    }

    public void setGorohList(List<Goroh> gorohList) {
        this.gorohList = gorohList;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<MojavezRozane> getMojavezRozanes() {
        return mojavezRozanes;
    }

    public void setMojavezRozanes(List<MojavezRozane> mojavezRozanes) {
        this.mojavezRozanes = mojavezRozanes;
    }

    public String getVorod() {
        return vorod;
    }

    public void setVorod(String vorod) {
        this.vorod = vorod;
    }

    public String getKhoroj() {
        return khoroj;
    }

    public void setKhoroj(String khoroj) {
        this.khoroj = khoroj;
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

    public boolean isDisableChangeGroup() {
        return disableChangeGroup;
    }

    public void setDisableChangeGroup(boolean disableChangeGroup) {
        this.disableChangeGroup = disableChangeGroup;
    }
}