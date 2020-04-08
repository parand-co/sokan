package omorkoliAndgharardadi.bean;


import amar.model.Personel;
import amar.model.SavabeghTaradod;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.MojavezRozane;
import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.dao.SavabeghMamoriyatRozaneDao;
import dataBaseModel.dao.SavabeghTaradodDao;
import dataBaseModel.dao.TaraddodDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import javassist.bytecode.stackmap.BasicBlock;
import manage.bean.IndexBean;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import omorkoliAndgharardadi.model.SavabeghMamoriyatRozane;
import omorkoliAndgharardadi.model.SavabeghMamoriyatRozaneModel;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import util.Excel;
import util.FillList;

import java.text.ParseException;
import java.util.*;

@ManagedBean
@ViewScoped
public class SavabeghMamoriyatRozaneBean implements Serializable {

    private String URL = "omorkoliAndGharardadi/SavabeghMamoriyatRozane.xhtml";
    private String READ_PATH = "/resources/uploads/excel/";
    private StreamedContent exelFile;

    private SavabeghMamoriyatRozane savabeghMamoriyat = new SavabeghMamoriyatRozane();
    private SavabeghMamoriyatRozane selected = null;
    private List<SavabeghMamoriyatRozane> dataTable = new ArrayList<>();
    private List<SavabeghMamoriyatRozane> morakhasiList = new ArrayList<>();
    private List<SavabeghMamoriyatRozaneModel> arrayList = new ArrayList<>();
    private List<MojavezRozane> mojavezRozaneList = new ArrayList<>();


    private Long noeMamoriyatId;


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
    private String tarikhShoroAzFilter = "";
    private String tarikhShoroTaFilter = "";
    private String tarikhPayanAzFilter = "";
    private String tarikhPayanTaFilter = "";
    private String saatShoroAzFilter = "";
    private String saatShoroTaFilter = "";
    private String saatPayanAzFilter = "";
    private String saatPayanTaFilter = "";
    private Long noeMamoriyatFilter;
    private Long dayereFilter;
    private Long bakhshFilter;


    private boolean checkBakhsh = false;
    private boolean renderedBtnCrud = false;

    private boolean readSaghfeMorakhasi = true;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa", "IR"));
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HHmm");
    private Calendar today = Calendar.getInstance();


    private Boolean selectNoeMamoriyat = true;
    private Boolean selectNoeMamoriyatFilter = true;
    private Boolean selectNoeMamoriyatReport = true;


    /////
    private Permission permission;
    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;
///////

    public SavabeghMamoriyatRozaneBean() {
        savabeghMamoriyat.setTarikhSabt(dateFormat.format(today.getTime()));
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
        if (bakhshList.size() > 0) {
            setCheckBakhsh(true);
        } else {
            setCheckBakhsh(false);
        }

        Session session = HibernateUtil.getSession();
        mojavezRozaneList = session.createQuery("from MojavezRozane where title='ماموریت'").list();
        session.close();

    }

    public void selectNoeMamoriyatt(long id) {
        if (id == 6) {
            setSelectNoeMamoriyat(true);
            setSelectNoeMamoriyatFilter(true);
            setSelectNoeMamoriyatReport(true);
        } else if (id == 11) {
            setSelectNoeMamoriyat(false);
            setSelectNoeMamoriyatFilter(false);
            setSelectNoeMamoriyatReport(false);
        }
    }

    public void filterTable() {
        StringBuilder builder = new StringBuilder();
        builder.append(" from SavabeghMamoriyatRozane Where personel.shomarePersoneli IS NOT NULL  ");

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

        if (!saatShoroAzFilter.equals("") && saatShoroTaFilter.equals("")) {
            builder.append(" AND saatShoroc >= '" + saatShoroAzFilter + "'");
        } else if (saatShoroAzFilter.equals("") && !saatShoroTaFilter.equals("")) {
            builder.append(" AND saatShoro <= '" + saatShoroTaFilter + "'");
        } else if (!saatShoroAzFilter.equals("") && !saatShoroTaFilter.equals("")) {
            builder.append(" AND saatShoro >= '" + saatShoroAzFilter + "'" + " AND saatShoro <= '" + saatShoroTaFilter + "'");
        }

        if (!tarikhPayanAzFilter.equals("") && tarikhPayanTaFilter.equals("")) {
            builder.append(" AND tarikhPayan >= '" + tarikhPayanAzFilter + "'");
        } else if (tarikhPayanAzFilter.equals("") && !tarikhPayanTaFilter.equals("")) {
            builder.append(" AND tarikhPayan <= '" + tarikhPayanTaFilter + "'");
        } else if (!tarikhPayanAzFilter.equals("") && !tarikhPayanTaFilter.equals("")) {
            builder.append(" AND tarikhPayan >= '" + tarikhPayanAzFilter + "'" + " AND tarikhPayan <= '" + tarikhPayanTaFilter + "'");
        }
        if (!saatPayanAzFilter.equals("") && saatPayanTaFilter.equals("")) {
            builder.append(" AND saatEnd >= '" + saatPayanAzFilter + "'");
        } else if (saatPayanAzFilter.equals("") && !saatPayanTaFilter.equals("")) {
            builder.append(" AND saatEnd <= '" + saatPayanTaFilter + "'");
        } else if (!saatPayanAzFilter.equals("") && !saatPayanTaFilter.equals("")) {
            builder.append(" AND saatEnd >= '" + saatPayanAzFilter + "'" + " AND saatEnd <= '" + saatPayanTaFilter + "'");
        }

        if (noeMamoriyatFilter != null) {
            builder.append(" AND noeMamoriyat.id=" + noeMamoriyatFilter);
        }


        String dayereQuery = "";
        StringBuilder dayereBuilder = new StringBuilder();
        StringBuilder bakhshBuilder = new StringBuilder();
        if (dayereFilter != null) {
            dayereQuery = (" AND personel.dayere.id=" + dayereFilter);
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
        if (bakhshFilter != null) {
            bakhshQuery = (" AND personel.bakhsh.id=" + bakhshFilter);
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
//        builder.append(bakhshQuery);

        Session session = HibernateUtil.getSession();
        dataTable = session.createQuery(builder.toString()).list();
        session.close();

    }

    public void diffDate() {
        long diif = 0;
        try {
            Date date1 = dateFormat.parse(savabeghMamoriyat.getTarikhShoro());
            Date date2 = dateFormat.parse(savabeghMamoriyat.getTarikhPayan());
            diif = (date2.getTime() - date1.getTime()) / (24 * 60 * 59 * 1000);
            savabeghMamoriyat.setModat(Integer.parseInt(String.valueOf(diif)) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void diffTime() {
        long diifMinutes = 0;
        long diifHours = 0;
        try {
            Date date1 = dateFormat2.parse(savabeghMamoriyat.getSaatShoro());
            Date date2 = dateFormat2.parse(savabeghMamoriyat.getSaatEnd());
            diifHours = (date2.getHours() - date1.getHours());
            diifMinutes = (date2.getMinutes() - date1.getMinutes());
            if (diifMinutes < 0) {

                diifMinutes = 60 + diifMinutes;
                diifHours = diifHours - 1;
            }
            savabeghMamoriyat.setModatHours(String.valueOf(diifHours));
            savabeghMamoriyat.setModatMinutes(String.valueOf(diifMinutes));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void dispach(SavabeghMamoriyatRozane obj) {
        setSavabeghMamoriyat(obj);
        setShp(obj.getPersonel().getShomarePersoneli());
        setMeliCode(obj.getPersonel().getCodeMeli());
        setNoeMamoriyatId(obj.getNoeMamoriyat().getId());
        setRenderedBtnCrud(true);
        setDisableDispatch(true);
        if (obj.getNoeMamoriyat().getId() == 6) {
            setSelectNoeMamoriyat(true);
        } else if (obj.getNoeMamoriyat().getId() == 11) {
            setSelectNoeMamoriyat(false);
        }
    }

    public void findPersonel(String shp, String meliCode) {
        List<Personel> personelList = new ArrayList<>();

        FillList fillList=new FillList();
        personelList=fillList.personels(permissions.get(0),shp,meliCode,"","");
        if (personelList.size()>0) {
            savabeghMamoriyat.setPersonel(personelList.get(0));
            setShp(personelList.get(0).getShomarePersoneli());
            setMeliCode(personelList.get(0).getCodeMeli());
        }

    }

    public void save() {
        if (noeMamoriyatId != null) {
            for (MojavezRozane noeMamoriyat : mojavezRozaneList) {
                if (noeMamoriyat.getId() == noeMamoriyatId) {
                    savabeghMamoriyat.setNoeMamoriyat(noeMamoriyat);
                    break;
                }
            }
        }

        if (savabeghMamoriyat.getPersonel().getDayere() != null) {
            savabeghMamoriyat.setDayere(savabeghMamoriyat.getPersonel().getDayere());
        }
        if (savabeghMamoriyat.getPersonel().getBakhsh() != null) {
            savabeghMamoriyat.setBakhsh(savabeghMamoriyat.getPersonel().getBakhsh());
        }


        try {
            SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().create(savabeghMamoriyat, URL);
            SavabeghMamoriyatRozane obj = savabeghMamoriyat;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    checkSavabeghTaradod(obj);
                }
            });
            thread.start();
            dataTable.add(savabeghMamoriyat);
            alert.successSave();
            nuller();
        } catch (Exception e) {
            alert.unSuccessSave();
            nuller();
        }

    }

    private void checkSavabeghTaradod(SavabeghMamoriyatRozane savabeghMamoriyat) {
        List<Taraddod> savabeghTaradodList = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        if (savabeghMamoriyat.getTarikhShoro() != null) {
            savabeghTaradodList = session.createQuery("from  Taraddod   where personel.shomarePersoneli='" + savabeghMamoriyat.getPersonel().getShomarePersoneli() +
                    "' AND ( taghvim.tarikh >= '" + savabeghMamoriyat.getTarikhShoro() + "' AND taghvim.tarikh <='" + savabeghMamoriyat.getTarikhPayan() + "') ").list();
        } else if (savabeghMamoriyat.getSaatShoro() != null) {
            savabeghTaradodList = session.createQuery("from  Taraddod   where personel.shomarePersoneli='" + savabeghMamoriyat.getPersonel().getShomarePersoneli()
                    + "' AND  taghvim.tarikh='" + savabeghMamoriyat.getTarikhSabt() + "' AND ( saat >= "
                    + Integer.parseInt(savabeghMamoriyat.getSaatShoro()) + "  AND  saat <= " + Integer.parseInt(savabeghMamoriyat.getSaatEnd()) + ") ").list();
        }

        List<MojavezRozane> mojavezRozaneList = session.createQuery("from MojavezRozane where id=6").list();
        session.close();

        for (Taraddod model : savabeghTaradodList) {
            model.setVaziat(mojavezRozaneList.get(0));
            TaraddodDao.getInstance().getBaseQuery().createOrUpdate(model);
        }
    }


    public void update() {
        try {
            SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().createOrUpdate(savabeghMamoriyat, URL);
            alert = new Alert();
            alert.successEdit();
            SavabeghMamoriyatRozane obj = savabeghMamoriyat;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    checkSavabeghTaradod(obj);
                }
            });
            thread.start();
            nuller();
        } catch (Exception e) {
            alert.unSuccessEdit();
            nuller();
        }
    }


    public void nuller() {
        savabeghMamoriyat = new SavabeghMamoriyatRozane();
        setShp(null);
        setMeliCode(null);
        setShpFilter(null);
        setShKartFilter(null);
        setMeliCodeFilter(null);
        setTarikhShoroAzFilter(null);
        setTarikhShoroTaFilter(null);
        setTarikhPayanAzFilter(null);
        setTarikhPayanTaFilter(null);
        setDayereFilter(null);
        setBakhshFilter(null);
        setRenderedBtnCrud(false);
        setDisableDispatch(false);
        setNoeMamoriyatId(null);
        setNoeMamoriyatFilter(null);


    }

    public void selectDayare(long id) {
        Session session = HibernateUtil.getSession();
        bakhshList = session.createQuery("from Bakhsh where dayere.id=" + id).list();
        session.close();
    }

    public void delete() {
        try {
            SavabeghMamoriyatRozaneDao.getInstance().getBaseQuery().delete(savabeghMamoriyat, URL);
            dataTable.remove(savabeghMamoriyat);
            alert.successDelete();
        } catch (Exception e) {
            alert.unSuccessDelete();
        }
    }


    ////////////////////

    public SavabeghMamoriyatRozane getSavabeghMamoriyat() {
        return savabeghMamoriyat;
    }

    public void setSavabeghMamoriyat(SavabeghMamoriyatRozane savabeghMamoriyat) {
        this.savabeghMamoriyat = savabeghMamoriyat;
    }

    public SavabeghMamoriyatRozane getSelected() {
        return selected;
    }

    public void setSelected(SavabeghMamoriyatRozane selected) {
        this.selected = selected;
    }

    public List<SavabeghMamoriyatRozane> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<SavabeghMamoriyatRozane> dataTable) {
        this.dataTable = dataTable;
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

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public String getMeliCode() {
        return meliCode;
    }

    public void setMeliCode(String meliCode) {
        this.meliCode = meliCode;
    }

    public boolean isDisableDispatch() {
        return disableDispatch;
    }

    public void setDisableDispatch(boolean disableDispatch) {
        this.disableDispatch = disableDispatch;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
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


    public List<MojavezRozane> getMojavezRozaneList() {
        return mojavezRozaneList;
    }

    public void setMojavezRozaneList(List<MojavezRozane> mojavezRozaneList) {
        this.mojavezRozaneList = mojavezRozaneList;
    }

    public Long getNoeMamoriyatId() {
        return noeMamoriyatId;
    }

    public void setNoeMamoriyatId(Long noeMamoriyatId) {
        this.noeMamoriyatId = noeMamoriyatId;
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

    public StreamedContent getExelFile() {
        return exelFile;
    }

    public void setExelFile(StreamedContent exelFile) {
        this.exelFile = exelFile;
    }


    public Long getNoeMamoriyatFilter() {
        return noeMamoriyatFilter;
    }

    public void setNoeMamoriyatFilter(Long noeMamoriyatFilter) {
        this.noeMamoriyatFilter = noeMamoriyatFilter;
    }

    public Boolean getSelectNoeMamoriyat() {
        return selectNoeMamoriyat;
    }

    public void setSelectNoeMamoriyat(Boolean selectNoeMamoriyat) {
        this.selectNoeMamoriyat = selectNoeMamoriyat;
    }

    public String getSaatShoroAzFilter() {
        return saatShoroAzFilter;
    }

    public void setSaatShoroAzFilter(String saatShoroAzFilter) {
        this.saatShoroAzFilter = saatShoroAzFilter;
    }

    public String getSaatShoroTaFilter() {
        return saatShoroTaFilter;
    }

    public void setSaatShoroTaFilter(String saatShoroTaFilter) {
        this.saatShoroTaFilter = saatShoroTaFilter;
    }

    public String getSaatPayanAzFilter() {
        return saatPayanAzFilter;
    }

    public void setSaatPayanAzFilter(String saatPayanAzFilter) {
        this.saatPayanAzFilter = saatPayanAzFilter;
    }

    public String getSaatPayanTaFilter() {
        return saatPayanTaFilter;
    }

    public void setSaatPayanTaFilter(String saatPayanTaFilter) {
        this.saatPayanTaFilter = saatPayanTaFilter;
    }


    public Boolean getSelectNoeMamoriyatFilter() {
        return selectNoeMamoriyatFilter;
    }

    public void setSelectNoeMamoriyatFilter(Boolean selectNoeMamoriyatFilter) {
        this.selectNoeMamoriyatFilter = selectNoeMamoriyatFilter;
    }

    public Boolean getSelectNoeMamoriyatReport() {
        return selectNoeMamoriyatReport;
    }

    public void setSelectNoeMamoriyatReport(Boolean selectNoeMamoriyatReport) {
        this.selectNoeMamoriyatReport = selectNoeMamoriyatReport;
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
