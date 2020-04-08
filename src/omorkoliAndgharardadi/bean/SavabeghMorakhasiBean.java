package omorkoliAndgharardadi.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.PersonelDao;
import dataBaseModel.dao.SavabeghMorakhasiDao;
import dataBaseModel.dao.TaraddodDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import ezafekari.model.Emza;
import manage.bean.IndexBean;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import omorkoliAndgharardadi.model.*;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Convert;
import util.Excel;
import util.FillList;
import util.PdfReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

@ManagedBean
@ViewScoped
public class SavabeghMorakhasiBean implements Serializable {
    private String URL = "omorkoliAndGharardadi/SavabeghMorakhasi.xhtml";
    private String READ_PATH = "/resources/uploads/excel/";
    private StreamedContent exelFile;

    private SavabeghMorakhasi savabeghMorakhasi = new SavabeghMorakhasi();
    private SavabeghMorakhasi selected = null;
    private List<SavabeghMorakhasi> dataTable = new ArrayList<>();
    private List<SavabeghMorakhasi> morakhasiList = new ArrayList<>();
    private List<SavabeghMorakhasiReportModel> arrayList = new ArrayList<>();
    private List<MorakhasiSaliyaneReportModel> arrayList2 = new ArrayList<>();

    private List<NoeMorakhasi> noeMorakhasiList = new ArrayList<>();
    private Long noeMorakhasiId;
    private List<VazeyatMorakhasi> vazeyatMorakhasiList = new ArrayList<>();
    private Long vazMorakhasiId;

    private List<Dayere> dayereList = new ArrayList<>();
    private List<Bakhsh> bakhshList = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdamList = new ArrayList<>();

    private String shp;
    private String meliCode;
    private boolean disableDispatch = false;
    private Alert alert = new Alert();


    private String shpFilter;
    private String shKartFilter;
    private String MeliCodeFilter;
    private String tarikhShoroAzFilter;
    private String tarikhShoroTaFilter;
    private String tarikhPayanAzFilter;
    private String tarikhPayanTaFilter;
    private Long noeMorakhasiFilter;
    private Long VazeyatMorakhasiFilter;
    private Long dayereFilter;
    private Long bakhshFilter;

    private int saghfeMorakhasi;

    private long baghimandeMorakhasi = 0;

    private boolean checkBakhsh = false;
    private boolean renderedBtnCrud = false;

    private boolean readSaghfeMorakhasi = true;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa", "IR"));
    private Calendar today = Calendar.getInstance();

    private List<Semat> semats = new ArrayList<>();
    private List<Emza> emzaList = new ArrayList<>();

    private String farmandeh;
    private String girande;
    private String elat = "سالیانه - بندرعباس";


    private Integer estehghagh;
    private Integer tashvighi;
    private Integer torahi;
    private Integer estelaji;
    private Integer tatil;
    private Integer ashae;
    private Integer daryanavardi;
    private Integer enteghal;
    private Long count;


    private String shpReport;
    private String shomareKartReport;
    private String codeMeliReport;
    private String nameReport;
    private String neshanReport;
    private String startDateAzReport;
    private String startDateTaReport;
    private String endDateAzReport;
    private String endDateTaReport;
    private Long dayereIdReport;
    private Long bakhshIdReport;
    private int vazPersonelReport;
    private String masolEdari = "";
    private String raeesDayere = "";

    private String tozihatKholaseVazeyat;
    private String azKholaseVazeyat = "کارخانجات نداجابندرعباس/امورکلی";
    private String beKholaseVazeyat = "مدیریت محترم نیروی انسانی منطقه یکم امامت نداجا/تیم محاسباتی";
    private String MozoeKholaseVazeyat = "خلاصه وضعیت مرخصی استفاده شده نامبرده در سال";
    private String salKholaseVazeyat;
    private String shpKholaseVazeyat;
    private String meliCodeKholaseVazeyat;
    private String peyvastKholaseVazeyat = "ندارد";
    private long emza1 = 2;
    private long emza2 = 3;


    private String shpSalR;
    private String meliCodeSalR;
    private String ShomareKartSalR;
    private String salSalR;


    /////
    private Permission permission;
    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;
///////


    private KholaseVazeyatReportModelField objEmza = new KholaseVazeyatReportModelField();


    public SavabeghMorakhasiBean() {

        savabeghMorakhasi.setTarikhSabt(dateFormat.format(today.getTime()));
        URL = IndexBean.url;
        permissions = IndexBean.permissions;
        if (permissions.size() > 0) {
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }
        FillList fillList = new FillList();
        dayereList = fillList.dayeres(permissions.get(0));
        bakhshList = fillList.bakhsh(permissions.get(0));
        noeEstekhdamList = fillList.noeEstekhdams(permissions.get(0));
        Session session = HibernateUtil.getSession();
        vazeyatMorakhasiList = session.createQuery("from VazeyatMorakhasi ").list();
        noeMorakhasiList = session.createQuery("from NoeMorakhasi ").list();
        semats = session.createQuery("from Semat ").list();
        emzaList = session.createQuery("from Emza ").list();
        session.close();

        Emza f = emzaList.stream().filter(o -> o.getSemat().getCode().equals("05")).findFirst().orElse(null);
        if (f != null) {
            farmandeh = f.getSemat().getTitle() + " " + f.getName() + " " + f.getNeshan();
        }
    }

    public void filterTable() {
        StringBuilder builder = new StringBuilder();
        builder.append(" from SavabeghMorakhasi Where personel.shomarePersoneli IS NOT NULL  ");

        if (!shpFilter.equals("")) {
            builder.append(" AND personel.shomarePersoneli='" + shpFilter + "'");
        }
        if (!shKartFilter.equals("")) {
            builder.append(" AND personel.shomareKart='" + shKartFilter + "'");

        }
        if (!MeliCodeFilter.equals("")) {
            builder.append(" AND personel.codeMeli='" + MeliCodeFilter + "'");

        }
        StringBuilder noeEstekhdamBuilder = new StringBuilder();
        String noeEstekhdamQuery = "";
        if (noeEstekhdamList.size() > 0) {
            if (noeEstekhdamList.size() == 1) {
                noeEstekhdamBuilder.append(" AND personel.noeEstekhdam.id=" + noeEstekhdamList.get(0).getId());
                noeEstekhdamQuery = noeEstekhdamBuilder.toString();
            } else if (noeEstekhdamList.size() > 0) {
                int i = 0;
                noeEstekhdamBuilder.append(" AND ( ");
                for (NoeEstekhdam d : noeEstekhdamList) {
                    i = i + 1;
                    noeEstekhdamBuilder.append(" personel.noeEstekhdam.id =" + d.getId());
                    if (noeEstekhdamList.size() == i) {
                        noeEstekhdamBuilder.append(" ) ");
                    } else {
                        noeEstekhdamBuilder.append(" OR ");
                    }
                }
                noeEstekhdamQuery = noeEstekhdamBuilder.toString();
            }
        }
        builder.append(noeEstekhdamQuery);

        if (!tarikhShoroAzFilter.equals("") && tarikhShoroTaFilter.equals("")) {
            builder.append(" AND tarikhShoro >= '" + tarikhShoroAzFilter + "'");
        } else if (tarikhShoroAzFilter.equals("") && !tarikhShoroTaFilter.equals("")) {
            builder.append(" AND tarikhShoro <= '" + tarikhShoroTaFilter + "'");
        } else if (!tarikhShoroAzFilter.equals("") && !tarikhShoroTaFilter.equals("")) {
            builder.append(" AND tarikhShoro >= '" + tarikhShoroAzFilter + "'" + " AND tarikhShoro <= '" + tarikhShoroTaFilter + "'");
        }

        if (!tarikhPayanAzFilter.equals("") && tarikhPayanTaFilter.equals("")) {
            builder.append(" AND tarikhPayan >= '" + tarikhPayanAzFilter + "'");
        } else if (tarikhPayanAzFilter.equals("") && !tarikhPayanTaFilter.equals("")) {
            builder.append(" AND tarikhPayan <= '" + tarikhPayanTaFilter + "'");
        } else if (!tarikhPayanAzFilter.equals("") && !tarikhPayanTaFilter.equals("")) {
            builder.append(" AND tarikhPayan >= '" + tarikhPayanAzFilter + "'" + " AND tarikhPayan <= '" + tarikhPayanTaFilter + "'");
        }

        if (noeMorakhasiFilter != null) {
            builder.append(" AND noeMorakhasi.id=" + noeMorakhasiFilter);
        }

        if (VazeyatMorakhasiFilter != null) {
            builder.append(" AND vazeyat.id=" + VazeyatMorakhasiFilter);

        }
        String dayereQuery = "";
        StringBuilder dayereBuilder = new StringBuilder();
        StringBuilder bakhshBuilder = new StringBuilder();
        if (dayereFilter != null) {
            dayereQuery = (" AND personel.dayere.id=" + dayereFilter);
        } else {
            if (dayereList.size() == 1) {
                if (dayereList.get(0).getCode().equals("0000")){

                }else {
                    dayereBuilder.append(" AND personel.dayere.id=" + dayereList.get(0).getId());
                    dayereQuery = dayereBuilder.toString();
                }
            } else if (dayereList.size() > 0) {
                int i = 0;
                dayereBuilder.append(" AND ( ");
                for (Dayere d : dayereList) {
                    i = i + 1;
                    dayereBuilder.append(" personel.dayere.id=" + d.getId());
                    if (dayereList.size() == i) {
                        dayereBuilder.append(" ) ");
                    } else {
                        dayereBuilder.append(" OR ");
                    }
                }
                dayereQuery = dayereBuilder.toString();
            }
        }
        builder.append(dayereQuery);


        String bakhshQuery = "";
        if (bakhshFilter != null) {
            bakhshQuery = (" AND personel.bakhsh.id=" + bakhshFilter);
        } else
            if (dayereList.size()==1){
                if (dayereList.get(0).getCode().equals("0000")){

                }else if (bakhshList.size() == 1) {
                    bakhshBuilder.append(" AND personel.bakhsh.id=" + bakhshList.get(0).getId());
                    bakhshQuery = bakhshBuilder.toString();
                } else if (bakhshList.size() > 0) {
                    int i = 0;
                    bakhshBuilder.append(" AND ( ");
                    for (Bakhsh d : bakhshList) {
                        i = i + 1;
                        bakhshBuilder.append(" personel.bakhsh.id=" + d.getId());
                        if (bakhshList.size() == i) {
                            bakhshBuilder.append(" ) ");
                        } else {
                            bakhshBuilder.append(" OR ");
                        }

                    }
                }
            }else if (bakhshList.size() == 1) {
                bakhshBuilder.append(" AND personel.bakhsh.id=" + bakhshList.get(0).getId());
                bakhshQuery = bakhshBuilder.toString();
            } else if (bakhshList.size() > 0) {
                int i = 0;
                bakhshBuilder.append(" AND ( ");
                for (Bakhsh d : bakhshList) {
                    i = i + 1;
                    bakhshBuilder.append(" personel.bakhsh.id=" + d.getId());
                    if (bakhshList.size() == i) {
                        bakhshBuilder.append(" ) ");
                    } else {
                        bakhshBuilder.append(" OR ");
                    }
                }
                bakhshQuery = bakhshBuilder.toString();
            }

        builder.append(bakhshQuery);

        Session session = HibernateUtil.getSession();
        dataTable = session.createQuery(builder.toString()).list();
        session.close();

    }

    public void diffDate() {
        long diif = 0;
        try {
            Date date1 = dateFormat.parse(savabeghMorakhasi.getTarikhShoro());
            Date date2 = dateFormat.parse(savabeghMorakhasi.getTarikhPayan());
            diif = (date2.getTime() - date1.getTime()) / (24 * 60 * 59 * 1000);
            savabeghMorakhasi.setModat(Integer.parseInt(String.valueOf(diif)) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void dispach(SavabeghMorakhasi obj) {
        setReadSaghfeMorakhasi(false);
        setSavabeghMorakhasi(obj);

        if(obj.getNoeMorakhasi() != null) {
            setNoeMorakhasiId(obj.getNoeMorakhasi().getId());
        }
        if(obj.getVazeyat() != null) {
            setVazMorakhasiId(obj.getVazeyat().getId());
        }
        if(obj.getPersonel() != null) {
            setShp(obj.getPersonel().getShomarePersoneli());
            setMeliCode(obj.getPersonel().getCodeMeli());
            setSaghfeMorakhasi(obj.getPersonel().getSaghfeMorakhasi());
        }

        obj.setTedadEstehghaghi(obj.getTedadEstehghaghi() + obj.getTamdid());
        setRenderedBtnCrud(true);
        setDisableDispatch(true);
    }

    public void findPersonel(String shp, String meliCode) {
        List<Personel> personelList = new ArrayList<>();
        FillList fillList = new FillList();

        personelList = fillList.personels(permissions.get(0), shp, meliCode, "", "");
        if (personelList.size() > 0) {
            savabeghMorakhasi.setPersonel(personelList.get(0));
            setShp(personelList.get(0).getShomarePersoneli());
            setMeliCode(personelList.get(0).getCodeMeli());
            setSaghfeMorakhasi(personelList.get(0).getSaghfeMorakhasi());
            String emroz = dateFormat.format(today.getTime());
            String sal = emroz.substring(0, 4);
            List<SavabeghMorakhasi> savabeghMorakhasiList = new ArrayList<>();
            long next = 0;
            Session session = HibernateUtil.getSession();
            next = (long) session.createQuery("SELECT COALESCE(SUM(tedadEstehghaghi),0) FROM SavabeghMorakhasi WHERE personel.shomarePersoneli = '" + savabeghMorakhasi.getPersonel().getShomarePersoneli() + "' AND SUBSTRING(tarikhShoro,1,4) = '" + sal + "' AND SUBSTRING(tarikhPayan,1,4) = '" + sal + "'").iterate().next();
            session.close();
            long saghfM = savabeghMorakhasi.getPersonel().getSaghfeMorakhasi();
            setBaghimandeMorakhasi(saghfM - next);
        } else {
            alert.unSuccessSearch();
        }


    }

    public void save() {
        for (VazeyatMorakhasi vz : vazeyatMorakhasiList) {
            if (vz.getId() == vazMorakhasiId) {
                savabeghMorakhasi.setVazeyat(vz);
                break;
            }
        }

        for (NoeMorakhasi no : noeMorakhasiList) {
            if (no.getId() == noeMorakhasiId) {
                savabeghMorakhasi.setNoeMorakhasi(no);
                break;
            }
        }

        if (savabeghMorakhasi.getPersonel().getDayere() != null) {
            savabeghMorakhasi.setDayere(savabeghMorakhasi.getPersonel().getDayere());
        }
        if (savabeghMorakhasi.getPersonel().getBakhsh() != null) {
            savabeghMorakhasi.setBakhsh(savabeghMorakhasi.getPersonel().getBakhsh());
        }


        try {
            SavabeghMorakhasiDao.getInstance().getBaseQuery().create(savabeghMorakhasi, URL);
            SavabeghMorakhasi obj = savabeghMorakhasi;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    checkSavabeghTaradod(obj);
                }
            });
            thread.start();
            dataTable.add(savabeghMorakhasi);
            alert.successSave();
            nuller();
        } catch (Exception e) {
            alert.unSuccessSave();
            nuller();
        }

    }

    private void checkSavabeghTaradod(SavabeghMorakhasi savabeghMorakhasi) {
        Session session = HibernateUtil.getSession();
        List<Taraddod> savabeghTaradodList = session.createQuery("from  Taraddod   where personel.shomarePersoneli='" + savabeghMorakhasi.getPersonel().getShomarePersoneli() + "' AND ( taghvim.tarikh >= '" + savabeghMorakhasi.getTarikhShoro() + "' AND taghvim.tarikh <='" + savabeghMorakhasi.getTarikhPayan() + "') ").list();
        List<MojavezRozane> mojavezRozaneList = session.createQuery("from MojavezRozane where id=4").list();
        session.close();

        for (Taraddod model : savabeghTaradodList) {
            model.setVaziat(mojavezRozaneList.get(0));
            TaraddodDao.getInstance().getBaseQuery().createOrUpdate(model);
        }
    }


    public void update() {
        for (VazeyatMorakhasi vz : vazeyatMorakhasiList) {
            if (vz.getId() == vazMorakhasiId) {
                savabeghMorakhasi.setVazeyat(vz);
                break;
            }
        }

        for (NoeMorakhasi no : noeMorakhasiList) {
            if (no.getId() == noeMorakhasiId) {
                savabeghMorakhasi.setNoeMorakhasi(no);
                break;
            }
        }

        checkSaghfeMorakhasi();
        try {
            SavabeghMorakhasiDao.getInstance().getBaseQuery().createOrUpdate(savabeghMorakhasi, URL);
            SavabeghMorakhasi obj = savabeghMorakhasi;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    checkSavabeghTaradod(obj);
                }
            });
            thread.start();
            alert = new Alert();
            alert.successEdit();

            nuller();
        } catch (Exception e) {
            alert.unSuccessEdit();
            nuller();
        }
    }

    private void checkSaghfeMorakhasi() {
        if (saghfeMorakhasi != savabeghMorakhasi.getPersonel().getSaghfeMorakhasi()) {
            Personel personel = savabeghMorakhasi.getPersonel();
            personel.setSaghfeMorakhasi(saghfeMorakhasi);
            PersonelDao.getInstance().getBaseQuery().createOrUpdate(personel);
        }
    }

    public void nuller() {
        savabeghMorakhasi = new SavabeghMorakhasi();
        setReadSaghfeMorakhasi(true);
        setShp(null);
        setMeliCode(null);
        setVazMorakhasiId(null);
        setNoeMorakhasiId(null);
        setShpFilter(null);
        setShKartFilter(null);
        setMeliCodeFilter(null);
        setTarikhShoroAzFilter(null);
        setTarikhShoroTaFilter(null);
        setTarikhPayanAzFilter(null);
        setTarikhPayanTaFilter(null);
        setNoeMorakhasiFilter(null);
        setVazeyatMorakhasiFilter(null);
        setDayereFilter(null);
        setBakhshFilter(null);
        setRenderedBtnCrud(false);
        setDisableDispatch(false);
        setBaghimandeMorakhasi(0);
        setSaghfeMorakhasi(0);
    }

    public void selectDayare(long id) {
        Session session = HibernateUtil.getSession();
        bakhshList = session.createQuery("from Bakhsh  where dayere.id=" + id).list();
        session.close();
    }

    public void delete() {
        try {
            SavabeghMorakhasiDao.getInstance().getBaseQuery().delete(savabeghMorakhasi, URL);
            dataTable.remove(savabeghMorakhasi);
            alert.successDelete();
        } catch (Exception e) {
            alert.unSuccessDelete();
        }
    }

    public void startPrint(SavabeghMorakhasi model) {
        selected = model;
        if (model.getPersonel() != null) {
            if (model.getPersonel().getDayere() != null) {
                Semat s = semats.stream().filter(o -> o.getDayere().getCode().equals(model.getPersonel().getDayere().getCode())).findFirst().orElse(null);
                girande = s.getTitle();
            }
        }
    }

    public void pdfReport(SavabeghMorakhasi model, String type) {
        calculatorMorakhasi(model.getPersonel().getId(), model.getTarikhShoro().substring(0, 4), model.getId(), model.getTarikhShoro());
        Map map = new HashMap<>();
        map.put("estehghagh", String.valueOf(estehghagh));
        map.put("tashvigh", String.valueOf(tashvighi));
        map.put("torahi", String.valueOf(torahi));
        map.put("estelaji", String.valueOf(estelaji));
        map.put("tatil", String.valueOf(tatil));
        map.put("ashae", String.valueOf(ashae));
        map.put("daryanavardi", String.valueOf(daryanavardi));
        map.put("enteghl", String.valueOf(enteghal));

        if (model.getPersonel().getDaraje() != null) {
            map.put("name", model.getPersonel().getDaraje().getTitle().trim() + " " + model.getPersonel().getName().trim() + " " + model.getPersonel().getNeshan().trim());
        } else {
            map.put("name", model.getPersonel().getName().trim() + " " + model.getPersonel().getNeshan().trim());
        }

        map.put("shp", model.getPersonel().getShomarePersoneli().trim());
        if (model.getPersonel().getDayere() != null) {
            map.put("dayere", model.getPersonel().getDayere().getTitle().trim());
        }
        Integer sum = model.getTedadEstehghaghi() + model.getTamdid() + model.getTedadTashvighi() + model.getTedadTorahi() + model.getTedadTatili() + model.getTedadAshae() + model.getTedadDaryanavardi() + model.getTedadEnteghal();
        Convert convert = new Convert();
        String result = sum + " " + convert.numberToString(sum) + " " + "روز";
        map.put("jam", result);
        map.put("shoro", model.getTarikhShoro());
        map.put("payan", model.getTarikhPayan());

        map.put("eEstehghagh", String.valueOf(model.getTedadEstehghaghi() + model.getTamdid()));
        map.put("eTashvighi", String.valueOf(model.getTedadTashvighi()));
        map.put("eTorahi", String.valueOf(model.getTedadTorahi()));
        map.put("eTatil", String.valueOf(model.getTedadTatili()));
        map.put("eAshae", String.valueOf(model.getTedadAshae()));
        map.put("eDaryanavardi", String.valueOf(model.getTedadDaryanavardi()));
        map.put("eEnteghal", String.valueOf(model.getTedadEnteghal()));

        map.put("shahrestan", elat);
        map.put("farmande", farmandeh);
        map.put("girande", girande);
        map.put("sabt", model.getTarikhSabt());
        map.put("shomare", String.valueOf(count));

        PdfReport report = new PdfReport();

        if (type.equals("A4")) {
            report.withoutTable(map, "\\omorkoliAndGharardadi\\jasperReport\\bargeMorakhasiA4.jrxml");
        } else if (type.equals("A5")) {
            report.withoutTable(map, "\\omorkoliAndGharardadi\\jasperReport\\bargeMorakhasiA5.jrxml");
        }
    }

    private void calculatorMorakhasi(Long personelId, String sal, Long id, String shoro) {

        Session session = HibernateUtil.getSession();
        estehghagh = (Integer) session.createQuery("SELECT sum(tedadEstehghaghi) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 1, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        tashvighi = (Integer) session.createQuery("SELECT sum(tedadTashvighi) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        torahi = (Integer) session.createQuery("SELECT sum(tedadTorahi) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        estelaji = (Integer) session.createQuery("SELECT sum(tedadEstelaji) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        tatil = (Integer) session.createQuery("SELECT sum(tedadTatili) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        ashae = (Integer) session.createQuery("SELECT sum(tedadAshae) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        daryanavardi = (Integer) session.createQuery("SELECT sum(tedadDaryanavardi) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        enteghal = (Integer) session.createQuery("SELECT sum(tedadEnteghal) FROM SavabeghMorakhasi WHERE personel.id = ? AND id != ? AND SUBSTRING(tarikhShoro, 4) = ? AND tarikhPayan < ?")
                .setLong(0, personelId)
                .setLong(1, id)
                .setString(2, sal)
                .setString(3, shoro)
                .uniqueResult();

        count = (Long) session.createQuery("SELECT count(*) FROM SavabeghMorakhasi WHERE personel.id = ? AND SUBSTRING(tarikhShoro, 1, 4) = ? AND tarikhShoro <= ?")
                .setLong(0, personelId)
                .setString(1, sal)
                .setString(2, shoro)
                .uniqueResult();

        session.close();

        if (estehghagh == null) {
            estehghagh = 0;
        }
        if (tashvighi == null) {
            tashvighi = 0;
        }
        if (torahi == null) {
            torahi = 0;
        }
        if (estelaji == null) {
            estelaji = 0;
        }
        if (tatil == null) {
            tatil = 0;
        }
        if (ashae == null) {
            ashae = 0;
        }
        if (daryanavardi == null) {
            daryanavardi = 0;
        }
        if (enteghal == null) {
            enteghal = 0;
        }
        if (count == null) {
            count = 0L;
        }
    }

    public void reportSavabegh(int id) {
        arrayList = new ArrayList<>();
        if (!shpReport.equals("") || !shomareKartReport.equals("") || !codeMeliReport.equals("")
                || !nameReport.equals("") || !neshanReport.equals("") || !startDateAzReport.equals("") ||
                !endDateAzReport.equals("") || dayereIdReport != null || bakhshIdReport != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("FROM SavabeghMorakhasi where personel.shomarePersoneli is not null ");
            if (!shpReport.equals("")) {
                builder.append(" AND personel.shomarePersoneli='" + shpReport + "'");
            }
            if (!shomareKartReport.equals("")) {
                builder.append(" AND personel.shomareKart='" + shKartFilter + "'");
            }
            if (!codeMeliReport.equals("")) {
                builder.append(" AND personel.codeMeli='" + codeMeliReport + "'");
            }
            if (nameReport.length() >= 3) {
                builder.append(" AND personel.name LIKE '" + nameReport + "%'");
            }
            if (neshanReport.length() >= 3) {
                builder.append(" AND personel.famil LIKE '" + neshanReport + "%'");
            }
            StringBuilder noeEstekhdamBuilder = new StringBuilder();
            String noeEstekhdamQuery = "";
            if (noeEstekhdamList.size() > 0) {
                if (noeEstekhdamList.size() == 1) {
                    noeEstekhdamBuilder.append(" AND personel.noeEstekhdam.id=" + noeEstekhdamList.get(0).getId());
                    noeEstekhdamQuery = noeEstekhdamBuilder.toString();
                } else if (noeEstekhdamList.size() > 0) {
                    int i = 0;
                    noeEstekhdamBuilder.append(" AND ( ");
                    for (NoeEstekhdam d : noeEstekhdamList) {
                        i = i + 1;
                        noeEstekhdamBuilder.append(" personel.noeEstekhdam.id =" + d.getId());
                        if (noeEstekhdamList.size() == i) {
                            noeEstekhdamBuilder.append(" ) ");
                        } else {
                            noeEstekhdamBuilder.append(" OR ");
                        }
                    }
                    noeEstekhdamQuery = noeEstekhdamBuilder.toString();
                }
            }
            builder.append(noeEstekhdamQuery);
            if (!startDateAzReport.equals("") && startDateTaReport.equals("")) {
                builder.append(" AND tarikhShoro >= '" + startDateAzReport + "'");
            } else if (startDateAzReport.equals("") && !startDateTaReport.equals("")) {
                builder.append(" AND tarikhShoro <= '" + startDateTaReport + "'");
            } else if (!startDateAzReport.equals("") && !startDateTaReport.equals("")) {
                builder.append(" AND ( tarikhShoro >= '" + startDateAzReport + "' AND tarikhShoro <='" + startDateTaReport + "') ");
            }


            if (!endDateAzReport.equals("") && endDateTaReport.equals("")) {
                builder.append(" AND tarikhPayan >= '" + endDateAzReport + "'");
            } else if (endDateAzReport.equals("") && !endDateTaReport.equals("")) {
                builder.append(" AND tarikhPayan <= '" + endDateTaReport + "'");
            } else if (!endDateAzReport.equals("") && !endDateTaReport.equals("")) {
                builder.append(" AND ( tarikhPayan >= '" + endDateAzReport + "' AND tarikhPayan <='" + endDateTaReport + "') ");
            }

            String dayereQuery = "";

            StringBuilder dayereBuilder = new StringBuilder();
            StringBuilder bakhshBuilder = new StringBuilder();

            if (dayereIdReport != null) {
                dayereQuery = (" AND personel.dayere.id=" + dayereIdReport);
            } else {
                if (dayereList.size() == 1) {
                    dayereBuilder.append(" AND personel.dayere.id=" + dayereList.get(0).getId());
                    dayereQuery = dayereBuilder.toString();
                } else if (dayereList.size() > 0) {
                    int i = 0;
                    dayereBuilder.append(" AND ( ");
                    for (Dayere d : dayereList) {
                        i = i + 1;
                        dayereBuilder.append(" personel.dayere.id=" + d.getId());
                        if (dayereList.size() == i) {
                            dayereBuilder.append(" ) ");
                        } else {
                            dayereBuilder.append(" OR ");
                        }
                    }
                    dayereQuery = dayereBuilder.toString();
                }
            }

            builder.append(dayereQuery);


            String bakhshQuery = "";
            if (bakhshIdReport != null) {
                bakhshQuery = (" AND personel.bakhsh.id=" + bakhshIdReport);
            } else {
                if (bakhshList.size() == 1) {
                    bakhshBuilder.append(" AND personel.bakhsh.id=" + bakhshList.get(0).getId());
                    bakhshQuery = bakhshBuilder.toString();
                } else if (bakhshList.size() > 0) {
                    int i = 0;
                    bakhshBuilder.append(" AND ( ");
                    for (Bakhsh d : bakhshList) {
                        i = i + 1;
                        bakhshBuilder.append(" personel.bakhsh.id=" + d.getId());
                        if (bakhshList.size() == i) {
                            bakhshBuilder.append(" ) ");
                        } else {
                            bakhshBuilder.append(" OR ");
                        }
                    }
                    bakhshQuery = bakhshBuilder.toString();
                }
            }

            builder.append(bakhshQuery);


            if (vazPersonelReport != 3) {
                builder.append(" AND personel.active=" + vazPersonelReport);
            }

            Session session = HibernateUtil.getSession();
            morakhasiList = session.createQuery(builder.toString()).list();
            session.close();

            for (int i = 0; i < morakhasiList.size(); i++) {
                SavabeghMorakhasi bean = morakhasiList.get(i);
                SavabeghMorakhasiReportModel model = new SavabeghMorakhasiReportModel();
                model.setRadif(String.valueOf(i + 1));
                if (bean.getPersonel() != null) {
                    model.setShomarePersoneli(bean.getPersonel().getShomarePersoneli());
                    model.setNam(bean.getPersonel().getName());
                    model.setFamil(bean.getPersonel().getNeshan());
                    if (bean.getPersonel().getDaraje() != null) {
                        model.setDaraje(bean.getPersonel().getDaraje().getTitle());
                    } else {
                        model.setDaraje("");
                    }

                    if (bean.getDayere() != null) {
                        model.setDayere(bean.getDayere().getTitle());
                    }
                    if (bean.getBakhsh() != null) {
                        model.setBakhsh(bean.getBakhsh().getTitle());
                    }

                    model.setStartDate(bean.getTarikhShoro());
                    model.setEndDate(bean.getTarikhPayan());
                    model.setModat(String.valueOf(bean.getModat()));
                }

                arrayList.add(model);
            }
            if (id == 1) {
                printSavabeghMorakhasiJasper();
            } else if (id == 2) {
                createExel();
            }
        } else {
            new Alert("خطا", "فیلتر مورد نظر را انتخاب کنید");
        }

    }

    public void reportSaliyane() {
        arrayList2 = new ArrayList<>();
        if ((!shpSalR.equals("") || !ShomareKartSalR.equals("") || !meliCodeSalR.equals(""))
                && salSalR.length() == 4) {
            StringBuilder builder = new StringBuilder();
            builder.append("FROM SavabeghMorakhasi where personel.shomarePersoneli is not null ");
            if (!shpSalR.equals("")) {
                builder.append(" AND personel.shomarePersoneli='" + shpSalR + "'");
            }
            if (!ShomareKartSalR.equals("")) {
                builder.append(" AND personel.shomareKart='" + ShomareKartSalR + "'");
            }
            if (!meliCodeSalR.equals("")) {
                builder.append(" AND personel.codeMeli='" + meliCodeSalR + "'");
            }
            if (salSalR.length() == 4) {
                builder.append(" AND (SUBSTRING(tarikhShoro,1,4) = '" + salSalR + "' OR SUBSTRING(tarikhPayan,1,4)='" + salSalR + "' )");

            }

            StringBuilder noeEstekhdamBuilder = new StringBuilder();
            String noeEstekhdamQuery = "";
            if (noeEstekhdamList.size() > 0) {
                if (noeEstekhdamList.size() == 1) {
                    noeEstekhdamBuilder.append(" AND personel.noeEstekhdam.id=" + noeEstekhdamList.get(0).getId());
                    noeEstekhdamQuery = noeEstekhdamBuilder.toString();
                } else if (noeEstekhdamList.size() > 0) {
                    int i = 0;
                    noeEstekhdamBuilder.append(" AND ( ");
                    for (NoeEstekhdam d : noeEstekhdamList) {
                        i = i + 1;
                        noeEstekhdamBuilder.append(" personel.noeEstekhdam.id =" + d.getId());
                        if (noeEstekhdamList.size() == i) {
                            noeEstekhdamBuilder.append(" ) ");
                        } else {
                            noeEstekhdamBuilder.append(" OR ");
                        }
                    }
                    noeEstekhdamQuery = noeEstekhdamBuilder.toString();
                }
            }

            builder.append(noeEstekhdamQuery);


            String dayereQuery = "";

            StringBuilder dayereBuilder = new StringBuilder();
            StringBuilder bakhshBuilder = new StringBuilder();


                if (dayereList.size() == 1) {
                    dayereBuilder.append(" AND personel.dayere.id=" + dayereList.get(0).getId());
                    dayereQuery = dayereBuilder.toString();
                } else if (dayereList.size() > 0) {
                    int i = 0;
                    dayereBuilder.append(" AND ( ");
                    for (Dayere d : dayereList) {
                        i = i + 1;
                        dayereBuilder.append(" personel.dayere.id=" + d.getId());
                        if (dayereList.size() == i) {
                            dayereBuilder.append(" ) ");
                        } else {
                            dayereBuilder.append(" OR ");
                        }
                    dayereQuery = dayereBuilder.toString();
                }
            }

            builder.append(dayereQuery);


            String bakhshQuery = "";
                if (bakhshList.size() == 1) {
                    bakhshBuilder.append(" AND personel.bakhsh.id=" + bakhshList.get(0).getId());
                    bakhshQuery = bakhshBuilder.toString();
                } else if (bakhshList.size() > 0) {
                    int i = 0;
                    bakhshBuilder.append(" AND ( ");
                    for (Bakhsh d : bakhshList) {
                        i = i + 1;
                        bakhshBuilder.append(" personel.bakhsh.id=" + d.getId());
                        if (bakhshList.size() == i) {
                            bakhshBuilder.append(" ) ");
                        } else {
                            bakhshBuilder.append(" OR ");
                        }
                    }
                    bakhshQuery = bakhshBuilder.toString();
                }


            builder.append(bakhshQuery);

            Session session = HibernateUtil.getSession();
            morakhasiList = session.createQuery(builder.toString()).list();
            session.close();

            for (int i = 0; i < morakhasiList.size(); i++) {
                SavabeghMorakhasi bean = morakhasiList.get(i);

                MorakhasiSaliyaneReportModel model = new MorakhasiSaliyaneReportModel();

                model.setRadif(String.valueOf(arrayList2.size() + 1));
                model.setTarikhShoro(bean.getTarikhShoro());
                model.setTarikhPayan(bean.getTarikhPayan());
                model.setKol(String.valueOf(bean.getModat()));
                model.setEstehghaghi(String.valueOf(bean.getTedadEstehghaghi()));
                model.setTashvighi(String.valueOf(bean.getTedadTashvighi()));
                model.setTatili(String.valueOf(bean.getTedadTatili()));
                model.setBodMasafat(String.valueOf(bean.getTedadTorahi()));
                model.setEstelaji(String.valueOf(bean.getTedadEstelaji()));
                model.setAshae(String.valueOf(bean.getTedadAshae()));
                model.setDaryanavardi(String.valueOf(bean.getTedadDaryanavardi()));
                model.setEnteghal(String.valueOf(bean.getTedadEnteghal()));
                model.setTozihat(bean.getTozihat());
                model.setShomareName(bean.getShomareName());
                model.setTarikhName(bean.getTarikhName());
                arrayList2.add(model);
            }

            printSavabeghMorakhasiJasper2();

        } else {
            new Alert("خطا", "فیلتر مورد نظر را انتخاب کنید");
        }

    }

    public void printSavabeghMorakhasiJasper2() {
        int jameKol = 0;
        int jameEstehghagh = 0;
        int jameTashvighi = 0;
        int jameTatili = 0;
        int jameBodMasafat = 0;
        int jameEstelaji = 0;
        for (MorakhasiSaliyaneReportModel o : arrayList2) {
            jameKol += Integer.parseInt(o.getKol());
            jameEstehghagh += Integer.parseInt(o.getEstehghaghi());
            jameTashvighi += Integer.parseInt(o.getTashvighi());
            jameTatili += Integer.parseInt(o.getTatili());
            jameBodMasafat += Integer.parseInt(o.getBodMasafat());
            jameEstelaji += Integer.parseInt(o.getEstelaji());
        }

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\omorkoliAndGharardadi\\jasperReport\\morakhasiSaliyaneReport.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            SavabeghMorakhasi obj = new SavabeghMorakhasi();
            obj = morakhasiList.get(0);
            Map map = new HashMap<>();
            map.put("date", dateFormat.format(com.ibm.icu.util.Calendar.getInstance().getTime()));
            map.put("masolEdari", masolEdari);
            map.put("raeesDayere", raeesDayere);
            map.put("morakhasiList", arrayList2);
            StringBuilder builder = new StringBuilder();
            builder.append("لیست مرخصی سالیانه مربوط به");
            if (obj.getPersonel().getHoviyat() != null) {
                builder.append(obj.getPersonel().getHoviyat().getTitle());
            } else {
                builder.append(".......");
            }
            builder.append(obj.getPersonel().getName());
            builder.append(obj.getPersonel().getNeshan());
            builder.append("شماره پرسنلی:");
            builder.append(obj.getPersonel().getShomarePersoneli());
            builder.append("درسال").append(salSalR);
            map.put("onvan", builder.toString());
            map.put("jameKol", String.valueOf(jameKol));
            map.put("jameEstehghagh", String.valueOf(jameEstehghagh));
            map.put("jameTashvighi", String.valueOf(jameTashvighi));
            map.put("jameTatili", String.valueOf(jameTatili));
            map.put("jameBodMasafat", String.valueOf(jameBodMasafat));
            map.put("jameEstelaji", String.valueOf(jameEstelaji));


            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(jasperReport, map, new JREmptyDataSource());

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();

            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); // your output goes here
            exporter.exportReport();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException | JRException e) {

        }
    }

    public void printSavabeghMorakhasiJasper() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\omorkoliAndGharardadi\\jasperReport\\savabeghMorakhasiReport.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map map = new HashMap<>();
            map.put("date", dateFormat.format(com.ibm.icu.util.Calendar.getInstance().getTime()));
            map.put("masolEdari", masolEdari);
            map.put("raeesDayere", raeesDayere);


            map.put("morakhasiList", arrayList);

            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(jasperReport, map, new JREmptyDataSource());

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();

            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); // your output goes here
            exporter.exportReport();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException | JRException e) {

        }
    }


    public void reportKholaseVazeyat() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\omorkoliAndGharardadi\\jasperReport\\KholaseVazeyatMorakhasi.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            List<KholaseVazeyatMorakhasiReportModel> kholaseVazeyatMorakhasiReportModelList = new ArrayList<>();
            List<SavabeghMorakhasi> savabeghMorakhasiList = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            builder.append("from SavabeghMorakhasi where personel.shomarePersoneli is not null ");
            if (!shpKholaseVazeyat.equals("")) {
                builder.append(" AND personel.shomarePersoneli='" + shpKholaseVazeyat + "'");
            }
            if (!meliCodeKholaseVazeyat.equals("")) {
                builder.append(" AND personel.codeMeli='" + meliCodeKholaseVazeyat + "'");
            }
            if (!salKholaseVazeyat.equals("")) {
                builder.append(" AND ( SUBSTRING(tarikhShoro,1,4)='" + salKholaseVazeyat + "' OR SUBSTRING(tarikhPayan,1,4)='" + salKholaseVazeyat + "' ) ");
            }

            StringBuilder noeEstekhdamBuilder = new StringBuilder();
            String noeEstekhdamQuery = "";
            if (noeEstekhdamList.size() > 0) {
                if (noeEstekhdamList.size() == 1) {
                    noeEstekhdamBuilder.append(" AND personel.noeEstekhdam.id=" + noeEstekhdamList.get(0).getId());
                    noeEstekhdamQuery = noeEstekhdamBuilder.toString();
                } else if (noeEstekhdamList.size() > 0) {
                    int i = 0;
                    noeEstekhdamBuilder.append(" AND ( ");
                    for (NoeEstekhdam d : noeEstekhdamList) {
                        i = i + 1;
                        noeEstekhdamBuilder.append(" personel.noeEstekhdam.id =" + d.getId());
                        if (noeEstekhdamList.size() == i) {
                            noeEstekhdamBuilder.append(" ) ");
                        } else {
                            noeEstekhdamBuilder.append(" OR ");
                        }
                    }
                    noeEstekhdamQuery = noeEstekhdamBuilder.toString();
                }
            }

            builder.append(noeEstekhdamQuery);


            String dayereQuery = "";

            StringBuilder dayereBuilder = new StringBuilder();
            StringBuilder bakhshBuilder = new StringBuilder();


            if (dayereList.size() == 1) {
                dayereBuilder.append(" AND personel.dayere.id=" + dayereList.get(0).getId());
                dayereQuery = dayereBuilder.toString();
            } else if (dayereList.size() > 0) {
                int i = 0;
                dayereBuilder.append(" AND ( ");
                for (Dayere d : dayereList) {
                    i = i + 1;
                    dayereBuilder.append(" personel.dayere.id=" + d.getId());
                    if (dayereList.size() == i) {
                        dayereBuilder.append(" ) ");
                    } else {
                        dayereBuilder.append(" OR ");
                    }
                    dayereQuery = dayereBuilder.toString();
                }
            }

            builder.append(dayereQuery);


            String bakhshQuery = "";
            if (bakhshList.size() == 1) {
                bakhshBuilder.append(" AND personel.bakhsh.id=" + bakhshList.get(0).getId());
                bakhshQuery = bakhshBuilder.toString();
            } else if (bakhshList.size() > 0) {
                int i = 0;
                bakhshBuilder.append(" AND ( ");
                for (Bakhsh d : bakhshList) {
                    i = i + 1;
                    bakhshBuilder.append(" personel.bakhsh.id=" + d.getId());
                    if (bakhshList.size() == i) {
                        bakhshBuilder.append(" ) ");
                    } else {
                        bakhshBuilder.append(" OR ");
                    }
                }
                bakhshQuery = bakhshBuilder.toString();
            }


            builder.append(bakhshQuery);


            Session session = HibernateUtil.getSession();
            savabeghMorakhasiList = session.createQuery(builder.toString()).list();

            StringBuilder build = new StringBuilder();
            build.append(" personel.shomarePersoneli is not null ");
            if (!shpKholaseVazeyat.equals("")) {
                build.append(" AND personel.shomarePersoneli='" + shpKholaseVazeyat + "'");
            }
            if (!meliCodeKholaseVazeyat.equals("")) {
                build.append(" AND personel.codeMeli='" + meliCodeKholaseVazeyat + "'");
            }
            if (!salKholaseVazeyat.equals("")) {
                build.append(" AND ( SUBSTRING(tarikhShoro,1,4)='" + salKholaseVazeyat + "' OR SUBSTRING(tarikhPayan,1,4)='" + salKholaseVazeyat + "' ) ");
            }

            StringBuilder noeEstekhdamBuild = new StringBuilder();
            String noeEstekhdamQuer = "";
            if (noeEstekhdamList.size() > 0) {
                if (noeEstekhdamList.size() == 1) {
                    noeEstekhdamBuild.append(" AND personel.noeEstekhdam.id=" + noeEstekhdamList.get(0).getId());
                    noeEstekhdamQuer = noeEstekhdamBuild.toString();
                } else if (noeEstekhdamList.size() > 0) {
                    int i = 0;
                    noeEstekhdamBuild.append(" AND ( ");
                    for (NoeEstekhdam d : noeEstekhdamList) {
                        i = i + 1;
                        noeEstekhdamBuild.append(" personel.noeEstekhdam.id =" + d.getId());
                        if (noeEstekhdamList.size() == i) {
                            noeEstekhdamBuild.append(" ) ");
                        } else {
                            noeEstekhdamBuild.append(" OR ");
                        }
                    }
                    noeEstekhdamQuer = noeEstekhdamBuild.toString();
                }
            }

            build.append(noeEstekhdamQuer);


            String dayereQuer = "";

            StringBuilder dayereBuild = new StringBuilder();
            StringBuilder bakhshBuild = new StringBuilder();


            if (dayereList.size() == 1) {
                dayereBuild.append(" AND personel.dayere.id=" + dayereList.get(0).getId());
                dayereQuer = dayereBuild.toString();
            } else if (dayereList.size() > 0) {
                int i = 0;
                dayereBuild.append(" AND ( ");
                for (Dayere d : dayereList) {
                    i = i + 1;
                    dayereBuild.append(" personel.dayere.id=" + d.getId());
                    if (dayereList.size() == i) {
                        dayereBuild.append(" ) ");
                    } else {
                        dayereBuild.append(" OR ");
                    }
                    dayereQuer = dayereBuild.toString();
                }
            }

            build.append(dayereQuer);


            String bakhshQuer = "";
            if (bakhshList.size() == 1) {
                bakhshBuild.append(" AND personel.bakhsh.id=" + bakhshList.get(0).getId());
                bakhshQuer = bakhshBuild.toString();
            } else if (bakhshList.size() > 0) {
                int i = 0;
                bakhshBuild.append(" AND ( ");
                for (Bakhsh d : bakhshList) {
                    i = i + 1;
                    bakhshBuild.append(" personel.bakhsh.id=" + d.getId());
                    if (bakhshList.size() == i) {
                        bakhshBuild.append(" ) ");
                    } else {
                        bakhshBuild.append(" OR ");
                    }
                }
                bakhshQuer = bakhshBuild.toString();
            }


            build.append(bakhshQuer);

            Object[] lisi1 = (Object[]) session.createQuery("SELECT\n" +
                    "SUM(modat) ,\n" +
                    "SUM(tamdid) ,\n" +
                    "SUM(tedadTatili) ,\n" +
                    "SUM(tedadTorahi) ,\n" +
                    "SUM(tedadTashvighi) ,\n" +
                    "SUM(tedadAshae),\n" +
                    "SUM(tedadDaryanavardi) ,\n" +
                    "SUM(tedadEstehghaghi) \n" +
                    "FROM\n" +
                    "SavabeghMorakhasi  WHERE "+build.toString() ).list().get(0);
            session.close();

            int i = 0;
            for (SavabeghMorakhasi obj : savabeghMorakhasiList) {
                KholaseVazeyatMorakhasiReportModel model = new KholaseVazeyatMorakhasiReportModel();
                model.setRadif(String.valueOf(kholaseVazeyatMorakhasiReportModelList.size() + 1));
                model.setTarikhShoro(obj.getTarikhShoro());
                model.setModatMosavab(String.valueOf(obj.getModat()));
                model.setTarikhKhateme(obj.getTarikhPayan());
                model.setTamdidMorakhasi(String.valueOf(obj.getTamdid()));
                model.setTatilatRasmi(String.valueOf(obj.getTedadTatili()));
                model.setBoodMasafat(String.valueOf(obj.getTedadTorahi()));
                model.setMorakhasiTashvighi(String.valueOf(obj.getTedadTashvighi()));
                model.setHagheAshae(String.valueOf(obj.getTedadAshae()));
                model.setBazgashZodtarAzMoed("0");
                model.setEsterahatPayanMamoriyat(String.valueOf(obj.getTedadDaryanavardi()));
                model.setModatEstehghaghi(String.valueOf(obj.getTedadEstehghaghi()));
                kholaseVazeyatMorakhasiReportModelList.add(model);
            }

            StringBuilder builder1 = new StringBuilder();
            builder1.append("***(مجموع مدت موثر در مرخصی )-(مجموع مدت تمدیدمرخصی+مجموع مدت مرخصی مصوب)=مرخصی استحقاقی استفاده شده ").append("\n")
                    .append("ازمجموع").append(lisi1[0]).append("روز مرخصی استفاده شده نامبرده در سال").append(savabeghMorakhasiList.get(0).getTarikhShoro().substring(0, 4))
                    .append("مدت").append(lisi1[7]).append("روز مرخصی استحقاقی محاسبه می باشد.").append("\n").append("ضمنا مشارالیه به علت نیاز خدمتی موفق به استفاده از")
                    .append(savabeghMorakhasiList.get(0).getPersonel().getSaghfeMorakhasi() - savabeghMorakhasiList.get(0).getTedadEstehghaghi()).append("روز از مرخصی استحقاقی خود نگردیده است.");
            Map map = new HashMap<>();
            map.put("date", dateFormat.format(com.ibm.icu.util.Calendar.getInstance().getTime()));
            map.put("morakhasiList", kholaseVazeyatMorakhasiReportModelList);
            map.put("logo", "../../resources/images/logo/artesh.png");
            map.put("shomareName", String.valueOf(new Date().getTime()));
            map.put("peyvast", peyvastKholaseVazeyat);
            map.put("matn", builder1.toString());
            if (savabeghMorakhasiList.get(0).getPersonel().getDaraje() != null) {
                map.put("daraje", savabeghMorakhasiList.get(0).getPersonel().getDaraje().getTitle());
            } else {
                map.put("daraje", "");
            }
            if (savabeghMorakhasiList.get(0).getPersonel().getRaste() != null) {
                map.put("raste", savabeghMorakhasiList.get(0).getPersonel().getRaste().getTitle());
            } else {
                map.put("raste", "");

            }
            map.put("nameVaNeshan", savabeghMorakhasiList.get(0).getPersonel().getName() + " " + savabeghMorakhasiList.get(0).getPersonel().getNeshan());
            map.put("shp", String.valueOf(savabeghMorakhasiList.get(0).getPersonel().getShomarePersoneli()));
            map.put("jTamdid", String.valueOf(lisi1[1]));
            map.put("jModatMosavab", String.valueOf(lisi1[0]));
            map.put("jTatilat", String.valueOf(lisi1[2]));
            map.put("jBodMasafat", String.valueOf(lisi1[3]));
            map.put("jTashvighi", String.valueOf(lisi1[4]));
            map.put("jHagheAshae", String.valueOf(lisi1[5]));
            map.put("jBazgashtZodtarAzMoed", "0");
            map.put("jEsterahatPayanMamoriyat", String.valueOf(lisi1[6]));
            map.put("jModatEstehghaghi", String.valueOf(lisi1[7]));
            map.put("tozihat", tozihatKholaseVazeyat);
            map.put("sal", savabeghMorakhasiList.get(0).getTarikhShoro().substring(0, 4));
            map.put("baghimande", String.valueOf(savabeghMorakhasiList.get(0).getPersonel().getSaghfeMorakhasi() - savabeghMorakhasiList.get(0).getTedadEstehghaghi()));
            map.put("az", azKholaseVazeyat);
            map.put("be", beKholaseVazeyat);
            map.put("mozoe", MozoeKholaseVazeyat + savabeghMorakhasiList.get(0).getTarikhShoro().substring(0, 4));
            Emza emza11 = new Emza();
            Emza emza22 = new Emza();
            for (Emza emza : emzaList) {
                if (emza.getId() == emza1) {
                    emza11 = emza;
                }
                if (emza.getId() == emza2) {
                    emza22 = emza;
                }
            }
            map.put("sematTwo", emza22.getSemat().getTitle());
            map.put("darajeEmzaTwo", emza22.getDaraje().getTitle());
            map.put("nameNeshanEmzaTwo", emza22.getName() + " " + emza22.getNeshan());
            map.put("sematOne", emza11.getSemat().getTitle());
            map.put("darajeEmzaOne", emza11.getDaraje().getTitle());
            map.put("nameNeshanEmzaOne", emza11.getName() + " " + emza11.getNeshan());

            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(jasperReport, map, new JREmptyDataSource());

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();

            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); // your output goes here
            exporter.exportReport();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException | JRException e) {
            e.getMessage();
        }
    }


    public void createExel() {
        SessionUtil util = new SessionUtil();
        String filename = "";
        if (util.getPermission() != null) {
            filename = util.getPermission().getUser().getUserName() + dateFormat.format(today.getTime());
        } else {
            filename = "test";
        }


        Excel exel = new Excel(READ_PATH, filename);


        String[] columnsName = {"ردیف", "شماره پرسنلی", "نام", "نشان", "درجه", "دایره", "بخش", "تاریخ شروع", "تاریخ پایان", "مدت"};

        if (exel.create("لیست مرخصی ", columnsName, arrayList)) {
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(READ_PATH + "\\" + filename + ".xlsx");
            exelFile = new DefaultStreamedContent(stream, "application/vnd.ms-exel", "exel.xlsx");
        } else {
            new Alert("خطا", "اشکال وجود دارد");
        }
    }


    /////

    public SavabeghMorakhasi getSavabeghMorakhasi() {
        return savabeghMorakhasi;
    }

    public void setSavabeghMorakhasi(SavabeghMorakhasi savabeghMorakhasi) {
        this.savabeghMorakhasi = savabeghMorakhasi;
    }

    public List<SavabeghMorakhasi> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<SavabeghMorakhasi> dataTable) {
        this.dataTable = dataTable;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public List<NoeMorakhasi> getNoeMorakhasiList() {
        return noeMorakhasiList;
    }

    public void setNoeMorakhasiList(List<NoeMorakhasi> noeMorakhasiList) {
        this.noeMorakhasiList = noeMorakhasiList;
    }

    public List<VazeyatMorakhasi> getVazeyatMorakhasiList() {
        return vazeyatMorakhasiList;
    }

    public void setVazeyatMorakhasiList(List<VazeyatMorakhasi> vazeyatMorakhasiList) {
        this.vazeyatMorakhasiList = vazeyatMorakhasiList;
    }

    public Long getNoeMorakhasiId() {
        return noeMorakhasiId;
    }

    public void setNoeMorakhasiId(Long noeMorakhasiId) {
        this.noeMorakhasiId = noeMorakhasiId;
    }

    public Long getVazMorakhasiId() {
        return vazMorakhasiId;
    }

    public void setVazMorakhasiId(Long vazMorakhasiId) {
        this.vazMorakhasiId = vazMorakhasiId;
    }

    public String getShpFilter() {
        return shpFilter;
    }

    public void setShpFilter(String shpFilter) {
        this.shpFilter = shpFilter;
    }

    public String getShKartFilter() {
        return shKartFilter;
    }

    public void setShKartFilter(String shKartFilter) {
        this.shKartFilter = shKartFilter;
    }

    public String getMeliCodeFilter() {
        return MeliCodeFilter;
    }

    public void setMeliCodeFilter(String meliCodeFilter) {
        MeliCodeFilter = meliCodeFilter;
    }

    public String getTarikhShoroAzFilter() {
        return tarikhShoroAzFilter;
    }

    public void setTarikhShoroAzFilter(String tarikhShoroAzFilter) {
        this.tarikhShoroAzFilter = tarikhShoroAzFilter;
    }

    public String getTarikhShoroTaFilter() {
        return tarikhShoroTaFilter;
    }

    public void setTarikhShoroTaFilter(String tarikhShoroTaFilter) {
        this.tarikhShoroTaFilter = tarikhShoroTaFilter;
    }

    public String getTarikhPayanAzFilter() {
        return tarikhPayanAzFilter;
    }

    public void setTarikhPayanAzFilter(String tarikhPayanAzFilter) {
        this.tarikhPayanAzFilter = tarikhPayanAzFilter;
    }

    public String getTarikhPayanTaFilter() {
        return tarikhPayanTaFilter;
    }

    public void setTarikhPayanTaFilter(String tarikhPayanTaFilter) {
        this.tarikhPayanTaFilter = tarikhPayanTaFilter;
    }

    public Long getNoeMorakhasiFilter() {
        return noeMorakhasiFilter;
    }

    public void setNoeMorakhasiFilter(Long noeMorakhasiFilter) {
        this.noeMorakhasiFilter = noeMorakhasiFilter;
    }

    public Long getVazeyatMorakhasiFilter() {
        return VazeyatMorakhasiFilter;
    }

    public void setVazeyatMorakhasiFilter(Long vazeyatMorakhasiFilter) {
        VazeyatMorakhasiFilter = vazeyatMorakhasiFilter;
    }

    public Long getDayereFilter() {
        return dayereFilter;
    }

    public void setDayereFilter(Long dayereFilter) {
        this.dayereFilter = dayereFilter;
    }

    public Long getBakhshFilter() {
        return bakhshFilter;
    }

    public void setBakhshFilter(Long bakhshFilter) {
        this.bakhshFilter = bakhshFilter;
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


    public boolean isCheckBakhsh() {
        return checkBakhsh;
    }

    public void setCheckBakhsh(boolean checkBakhsh) {
        this.checkBakhsh = checkBakhsh;
    }

    public boolean isRenderedBtnCrud() {
        return renderedBtnCrud;
    }

    public void setRenderedBtnCrud(boolean renderedBtnCrud) {
        this.renderedBtnCrud = renderedBtnCrud;
    }

    public boolean isDisableDispatch() {
        return disableDispatch;
    }

    public void setDisableDispatch(boolean disableDispatch) {
        this.disableDispatch = disableDispatch;
    }

    public String getMeliCode() {
        return meliCode;
    }

    public void setMeliCode(String meliCode) {
        this.meliCode = meliCode;
    }

    public long getBaghimandeMorakhasi() {
        return baghimandeMorakhasi;
    }

    public void setBaghimandeMorakhasi(long baghimandeMorakhasi) {
        this.baghimandeMorakhasi = baghimandeMorakhasi;
    }

    public String getFarmandeh() {
        return farmandeh;
    }

    public void setFarmandeh(String farmandeh) {
        this.farmandeh = farmandeh;
    }

    public String getGirande() {
        return girande;
    }

    public void setGirande(String girande) {
        this.girande = girande;
    }

    public String getElat() {
        return elat;
    }

    public void setElat(String elat) {
        this.elat = elat;
    }

    public boolean isReadSaghfeMorakhasi() {
        return readSaghfeMorakhasi;
    }

    public void setReadSaghfeMorakhasi(boolean readSaghfeMorakhasi) {
        this.readSaghfeMorakhasi = readSaghfeMorakhasi;
    }

    public int getSaghfeMorakhasi() {
        return saghfeMorakhasi;
    }

    public void setSaghfeMorakhasi(int saghfeMorakhasi) {
        this.saghfeMorakhasi = saghfeMorakhasi;
    }

    public String getShpReport() {
        return shpReport;
    }

    public void setShpReport(String shpReport) {
        this.shpReport = shpReport;
    }

    public String getShomareKartReport() {
        return shomareKartReport;
    }

    public void setShomareKartReport(String shomareKartReport) {
        this.shomareKartReport = shomareKartReport;
    }

    public String getCodeMeliReport() {
        return codeMeliReport;
    }

    public void setCodeMeliReport(String codeMeliReport) {
        this.codeMeliReport = codeMeliReport;
    }

    public String getNameReport() {
        return nameReport;
    }

    public void setNameReport(String nameReport) {
        this.nameReport = nameReport;
    }

    public String getNeshanReport() {
        return neshanReport;
    }

    public void setNeshanReport(String neshanReport) {
        this.neshanReport = neshanReport;
    }

    public String getStartDateAzReport() {
        return startDateAzReport;
    }

    public void setStartDateAzReport(String startDateAzReport) {
        this.startDateAzReport = startDateAzReport;
    }

    public String getStartDateTaReport() {
        return startDateTaReport;
    }

    public void setStartDateTaReport(String startDateTaReport) {
        this.startDateTaReport = startDateTaReport;
    }

    public String getEndDateAzReport() {
        return endDateAzReport;
    }

    public void setEndDateAzReport(String endDateAzReport) {
        this.endDateAzReport = endDateAzReport;
    }

    public String getEndDateTaReport() {
        return endDateTaReport;
    }

    public void setEndDateTaReport(String endDateTaReport) {
        this.endDateTaReport = endDateTaReport;
    }

    public Long getDayereIdReport() {
        return dayereIdReport;
    }

    public void setDayereIdReport(Long dayereIdReport) {
        this.dayereIdReport = dayereIdReport;
    }

    public Long getBakhshIdReport() {
        return bakhshIdReport;
    }

    public void setBakhshIdReport(Long bakhshIdReport) {
        this.bakhshIdReport = bakhshIdReport;
    }

    public int getVazPersonelReport() {
        return vazPersonelReport;
    }

    public void setVazPersonelReport(int vazPersonelReport) {
        this.vazPersonelReport = vazPersonelReport;
    }

    public List<SavabeghMorakhasi> getMorakhasiList() {
        return morakhasiList;
    }

    public void setMorakhasiList(List<SavabeghMorakhasi> morakhasiList) {
        this.morakhasiList = morakhasiList;
    }

    public StreamedContent getExelFile() {
        return exelFile;
    }

    public void setExelFile(StreamedContent exelFile) {
        this.exelFile = exelFile;
    }

    public String getTozihatKholaseVazeyat() {
        return tozihatKholaseVazeyat;
    }

    public void setTozihatKholaseVazeyat(String tozihatKholaseVazeyat) {
        this.tozihatKholaseVazeyat = tozihatKholaseVazeyat;
    }

    public String getSalKholaseVazeyat() {
        return salKholaseVazeyat;
    }

    public void setSalKholaseVazeyat(String salKholaseVazeyat) {
        this.salKholaseVazeyat = salKholaseVazeyat;
    }


    public String getShpKholaseVazeyat() {
        return shpKholaseVazeyat;
    }

    public void setShpKholaseVazeyat(String shpKholaseVazeyat) {
        this.shpKholaseVazeyat = shpKholaseVazeyat;
    }

    public String getMeliCodeKholaseVazeyat() {
        return meliCodeKholaseVazeyat;
    }

    public void setMeliCodeKholaseVazeyat(String meliCodeKholaseVazeyat) {
        this.meliCodeKholaseVazeyat = meliCodeKholaseVazeyat;
    }

    public String getAzKholaseVazeyat() {
        return azKholaseVazeyat;
    }

    public void setAzKholaseVazeyat(String azKholaseVazeyat) {
        this.azKholaseVazeyat = azKholaseVazeyat;
    }

    public String getBeKholaseVazeyat() {
        return beKholaseVazeyat;
    }

    public void setBeKholaseVazeyat(String beKholaseVazeyat) {
        this.beKholaseVazeyat = beKholaseVazeyat;
    }

    public String getMozoeKholaseVazeyat() {
        return MozoeKholaseVazeyat;
    }

    public void setMozoeKholaseVazeyat(String mozoeKholaseVazeyat) {
        MozoeKholaseVazeyat = mozoeKholaseVazeyat;
    }

    public String getPeyvastKholaseVazeyat() {
        return peyvastKholaseVazeyat;
    }

    public void setPeyvastKholaseVazeyat(String peyvastKholaseVazeyat) {
        this.peyvastKholaseVazeyat = peyvastKholaseVazeyat;
    }

    public List<Semat> getSemats() {
        return semats;
    }

    public void setSemats(List<Semat> semats) {
        this.semats = semats;
    }

    public List<Emza> getEmzaList() {
        return emzaList;
    }

    public void setEmzaList(List<Emza> emzaList) {
        this.emzaList = emzaList;
    }

    public Integer getEstehghagh() {
        return estehghagh;
    }

    public void setEstehghagh(Integer estehghagh) {
        this.estehghagh = estehghagh;
    }

    public Integer getTashvighi() {
        return tashvighi;
    }

    public void setTashvighi(Integer tashvighi) {
        this.tashvighi = tashvighi;
    }

    public Integer getTorahi() {
        return torahi;
    }

    public void setTorahi(Integer torahi) {
        this.torahi = torahi;
    }

    public Integer getEstelaji() {
        return estelaji;
    }

    public void setEstelaji(Integer estelaji) {
        this.estelaji = estelaji;
    }

    public Integer getTatil() {
        return tatil;
    }

    public void setTatil(Integer tatil) {
        this.tatil = tatil;
    }

    public Integer getAshae() {
        return ashae;
    }

    public void setAshae(Integer ashae) {
        this.ashae = ashae;
    }

    public Integer getDaryanavardi() {
        return daryanavardi;
    }

    public void setDaryanavardi(Integer daryanavardi) {
        this.daryanavardi = daryanavardi;
    }

    public Integer getEnteghal() {
        return enteghal;
    }

    public void setEnteghal(Integer enteghal) {
        this.enteghal = enteghal;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public SavabeghMorakhasi getSelected() {
        return selected;
    }

    public void setSelected(SavabeghMorakhasi selected) {
        this.selected = selected;
    }

    public KholaseVazeyatReportModelField getObjEmza() {
        return objEmza;
    }

    public void setObjEmza(KholaseVazeyatReportModelField objEmza) {
        this.objEmza = objEmza;
    }

    public long getEmza1() {
        return emza1;
    }

    public void setEmza1(long emza1) {
        this.emza1 = emza1;
    }

    public long getEmza2() {
        return emza2;
    }

    public void setEmza2(long emza2) {
        this.emza2 = emza2;
    }

    public String getMasolEdari() {
        return masolEdari;
    }

    public void setMasolEdari(String masolEdari) {
        this.masolEdari = masolEdari;
    }

    public String getRaeesDayere() {
        return raeesDayere;
    }

    public void setRaeesDayere(String raeesDayere) {
        this.raeesDayere = raeesDayere;
    }

    public String getShpSalR() {
        return shpSalR;
    }

    public void setShpSalR(String shpSalR) {
        this.shpSalR = shpSalR;
    }

    public String getMeliCodeSalR() {
        return meliCodeSalR;
    }

    public void setMeliCodeSalR(String meliCodeSalR) {
        this.meliCodeSalR = meliCodeSalR;
    }

    public String getShomareKartSalR() {
        return ShomareKartSalR;
    }

    public void setShomareKartSalR(String shomareKartSalR) {
        ShomareKartSalR = shomareKartSalR;
    }

    public String getSalSalR() {
        return salSalR;
    }

    public void setSalSalR(String salSalR) {
        this.salSalR = salSalR;
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
