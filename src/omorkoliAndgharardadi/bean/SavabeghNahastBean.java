package omorkoliAndgharardadi.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.SavabeghNahastDao;
import dataBaseModel.dao.TaraddodDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import ezafekari.model.Emza;
import manage.bean.IndexBean;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import omorkoliAndgharardadi.model.SavabeghNahast;
import omorkoliAndgharardadi.model.SavabeghNahastReportModel;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Excel;
import util.FillList;

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
public class SavabeghNahastBean implements Serializable {
    private String URL = "omorkoliAndGharardadi/SavabeghNahast.xhtml";
    private String READ_PATH = "/resources/uploads/excel/";
    private StreamedContent exelFile;

    private SavabeghNahast savabeghNahast = new SavabeghNahast();
    private SavabeghNahast selected = null;
    private List<SavabeghNahast> dataTable = new ArrayList<>();
    private List<SavabeghNahast> savabeghNahastList = new ArrayList<>();
    private List<SavabeghNahastReportModel> arrayList = new ArrayList<>();


    private List<Dayere> dayereList = new ArrayList<>();
    private List<Bakhsh> bakhshList = new ArrayList<>();
    private List<NoeEstekhdam>noeEstekhdamList=new ArrayList<>();

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
    private String tanbihatFilter = "";
    private Long dayereFilter;
    private Long bakhshFilter;


    private String shpReport = "";
    private String shomareKartReport = "";
    private String meliCodeReport = "";
    private String nameReport = "";
    private String neshanReport = "";
    private String tarikhShoroReportAz = "";
    private String tarikhShoroReportTa = "";
    private String tarikhPayanReportAz = "";
    private String tarikhPayanReportTa = "";


    private boolean checkBakhsh = false;
    private boolean renderedBtnCrud = false;



    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa", "IR"));
    private Calendar today = Calendar.getInstance();

    private List<Semat> semats = new ArrayList<>();
    private List<Emza> emzaList = new ArrayList<>();

    /////
    private Permission permission;
    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;
///////


    public SavabeghNahastBean() {

        savabeghNahast.setTarikhSabt(dateFormat.format(today.getTime()));
        URL = IndexBean.url;
        permissions = IndexBean.permissions;
        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }
        FillList fillList = new FillList();
        dayereList = fillList.dayeres(permissions.get(0));
        bakhshList=fillList.bakhsh(permissions.get(0));
        noeEstekhdamList=fillList.noeEstekhdams(permissions.get(0));
        if (bakhshList.size() > 0) {
            setCheckBakhsh(true);
        } else {
            setCheckBakhsh(false);
        }
    }


    public void filterTable() {
        StringBuilder builder = new StringBuilder();
        builder.append(" from SavabeghNahast Where personel.shomarePersoneli IS NOT NULL  ");

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
        builder.append(bakhshQuery);

        if (!tanbihatFilter.equals("")) {
            builder.append(" AND tanbih LIKE '%" + tanbihatFilter + "%'");
        }

        Session session = HibernateUtil.getSession();
        dataTable = session.createQuery(builder.toString()).list();
        session.close();

    }

    public void diffDate() {
        long diif = 0;
        try {
            Date date1 = dateFormat.parse(savabeghNahast.getTarikhShoro());
            Date date2 = dateFormat.parse(savabeghNahast.getTarikhPayan());
            diif = (date2.getTime() - date1.getTime()) / (24 * 60 * 59 * 1000);
            savabeghNahast.setModat(Integer.parseInt(String.valueOf(diif)) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void dispach(SavabeghNahast obj) {
        setShp(obj.getPersonel().getShomarePersoneli());
        setMeliCode(obj.getPersonel().getCodeMeli());
        setSavabeghNahast(obj);
        setRenderedBtnCrud(true);
        setDisableDispatch(true);
    }

    public void findPersonel(String shp, String meliCode) {
        List<Personel> personelList = new ArrayList<>();
        FillList fillList = new FillList();

        personelList = fillList.personels(permissions.get(0), shp, meliCode, "", "");
        if (personelList.size() > 0) {
            savabeghNahast.setPersonel(personelList.get(0));
            setShp(personelList.get(0).getShomarePersoneli());
            setMeliCode(personelList.get(0).getCodeMeli());
        } else {
            alert.unSuccessSearch();
        }

        String emroz = dateFormat.format(today.getTime());
        String sal = emroz.substring(0, 4);


    }

    public void save() {
        try {
            SavabeghNahastDao.getInstance().getBaseQuery().create(savabeghNahast, URL);

            dataTable.add(savabeghNahast);
            alert.successSave();
            nuller();
        } catch (Exception e) {
            alert.unSuccessSave();
            nuller();
        }

    }


    public void update() {


        try {
            SavabeghNahastDao.getInstance().getBaseQuery().createOrUpdate(savabeghNahast, URL);

            alert = new Alert();
            alert.successEdit();

            nuller();
        } catch (Exception e) {
            alert.unSuccessEdit();
            nuller();
        }
    }


    public void nuller() {
        setShp(null);
        setMeliCode(null);
        savabeghNahast = new SavabeghNahast();
        setRenderedBtnCrud(false);
        setDisableDispatch(false);
        shpFilter = "";
        shKartFilter = "";
        MeliCodeFilter = "";
        tarikhShoroAzFilter = "";
        tarikhShoroTaFilter = "";
        tarikhPayanAzFilter = "";
        tarikhPayanTaFilter = "";
        tanbihatFilter = "";
        dayereFilter = null;
        bakhshFilter = null;
        shpReport = "";
        shomareKartReport = "";
        meliCodeReport = "";
        nameReport = "";
        neshanReport = "";
        tarikhShoroReportAz = "";
        tarikhShoroReportTa = "";
        tarikhPayanReportAz = "";
        tarikhPayanReportTa = "";
    }

    public void selectDayare(long id) {
        Session session = HibernateUtil.getSession();
        bakhshList = session.createQuery("from Bakhsh where dayere.id=" + id).list();
        session.close();

    }

    public void delete() {
        try {
            SavabeghNahastDao.getInstance().getBaseQuery().delete(savabeghNahast, URL);
            dataTable.remove(savabeghNahast);
            alert.successEdit();
        } catch (Exception e) {
            alert.unSuccessEdit();
        }
    }


    public void reportSavabegh(int id) {
        if (!shpReport.equals("") || !shomareKartReport.equals("") || !meliCodeReport.equals("")
                || !nameReport.equals("") || !neshanReport.equals("") || !tarikhShoroReportAz.equals("") ||
                !tarikhShoroReportTa.equals("") || tarikhPayanReportAz != null || tarikhPayanReportTa != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("FROM SavabeghNahast where personel.shomarePersoneli is not null ");
            if (!shpReport.equals("")) {
                builder.append(" AND personel.shomarePersoneli='" + shpReport + "'");
            }
            if (!shomareKartReport.equals("")) {
                builder.append(" AND personel.shomareKart='" + shKartFilter + "'");
            }
            if (!meliCodeReport.equals("")) {
                builder.append(" AND personel.codeMeli='" + meliCodeReport + "'");
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
            if (!tarikhShoroReportAz.equals("") && tarikhShoroReportTa.equals("")) {
                builder.append(" AND tarikhShoro >= '" + tarikhShoroReportAz + "'");
            } else if (tarikhShoroReportAz.equals("") && !tarikhShoroReportTa.equals("")) {
                builder.append(" AND tarikhShoro <= '" + tarikhShoroReportTa + "'");
            } else if (!tarikhShoroReportAz.equals("") && !tarikhShoroReportTa.equals("")) {
                builder.append(" AND ( tarikhShoro >= '" + tarikhShoroReportAz + "' AND tarikhShoro <='" + tarikhShoroReportTa + "') ");
            }


            if (!tarikhPayanReportAz.equals("") && tarikhPayanReportTa.equals("")) {
                builder.append(" AND tarikhPayan >= '" + tarikhPayanReportAz + "'");
            } else if (tarikhPayanReportAz.equals("") && !tarikhPayanReportTa.equals("")) {
                builder.append(" AND tarikhPayan <= '" + tarikhPayanReportTa + "'");
            } else if (!tarikhPayanReportAz.equals("") && !tarikhPayanReportTa.equals("")) {
                builder.append(" AND ( tarikhPayan >= '" + tarikhPayanReportAz + "' AND tarikhPayan <='" + tarikhPayanReportTa + "') ");
            }

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
                    }
                    dayereQuery = dayereBuilder.toString();
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
            savabeghNahastList = session.createQuery(builder.toString()).list();
            session.close();

            for (int i = 0; i < savabeghNahastList.size(); i++) {
                SavabeghNahast bean = savabeghNahastList.get(i);

                SavabeghNahastReportModel model = new SavabeghNahastReportModel();

                model.setRadif(String.valueOf(i));
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
                    }else {
                        model.setDayere("");
                    }
                    if (bean.getBakhsh() != null) {
                        model.setBakhsh(bean.getBakhsh().getTitle());
                    }else {
                        model.setBakhsh("");
                    }

                    model.setStartDate(bean.getTarikhShoro());
                    model.setEndDate(bean.getTarikhPayan());
                    model.setModat(String.valueOf(bean.getModat()));
                    model.setTanbih(String.valueOf(bean.getTanbih()));
                    model.setMarhale(String.valueOf(bean.getMarhale()));
                    model.setMolahezat(String.valueOf(bean.getMolahezat()));
                }

                arrayList.add(model);
            }
            if (id == 1) {
                printSavabeghNahastReport();
            } else if (id == 2) {
                createExel();
            }
        } else {
            new Alert("خطا", "فیلتر مورد نظر را انتخاب کنید");
        }

    }

    public void printSavabeghNahastReport() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\omorkoliAndGharardadi\\jasperReport\\savabeghNahastReport.jrxml");


        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map map = new HashMap<>();
            map.put("date", dateFormat.format(com.ibm.icu.util.Calendar.getInstance().getTime()));



            map.put("savabeghNahastList", arrayList);

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


    public void createExel() {
        SessionUtil util = new SessionUtil();
        String filename = "";
        if (util.getPermission() != null) {
            filename = util.getPermission().getUser().getUserName() + dateFormat.format(today.getTime());
        } else {
            filename = "test";
        }


        Excel exel = new Excel(READ_PATH, filename);


        String[] columnsName = {"ردیف", "شماره پرسنلی", "نام", "نشان", "درجه", "دایره", "بخش", "تاریخ شروع","تاریخ پایان","مدت", "تنبیهات","مرحله","ملاحظات"};

        if (exel.create("لیست نهست ", columnsName, arrayList)) {
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(READ_PATH + "\\" + filename + ".xlsx");
            exelFile = new DefaultStreamedContent(stream, "application/vnd.ms-exel", "exel.xlsx");
        } else {
            new Alert("خطا", "اشکال وجود دارد");
        }
    }

    public SavabeghNahast getSavabeghNahast() {
        return savabeghNahast;
    }

    public void setSavabeghNahast(SavabeghNahast savabeghNahast) {
        this.savabeghNahast = savabeghNahast;
    }

    public SavabeghNahast getSelected() {
        return selected;
    }

    public void setSelected(SavabeghNahast selected) {
        this.selected = selected;
    }

    public List<SavabeghNahast> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<SavabeghNahast> dataTable) {
        this.dataTable = dataTable;
    }

    public List<SavabeghNahastReportModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<SavabeghNahastReportModel> arrayList) {
        this.arrayList = arrayList;
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

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Calendar getToday() {
        return today;
    }

    public void setToday(Calendar today) {
        this.today = today;
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

    public String getTanbihatFilter() {
        return tanbihatFilter;
    }

    public void setTanbihatFilter(String tanbihatFilter) {
        this.tanbihatFilter = tanbihatFilter;
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

    public String getMeliCodeReport() {
        return meliCodeReport;
    }

    public void setMeliCodeReport(String meliCodeReport) {
        this.meliCodeReport = meliCodeReport;
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

    public String getTarikhShoroReportAz() {
        return tarikhShoroReportAz;
    }

    public void setTarikhShoroReportAz(String tarikhShoroReportAz) {
        this.tarikhShoroReportAz = tarikhShoroReportAz;
    }

    public String getTarikhShoroReportTa() {
        return tarikhShoroReportTa;
    }

    public void setTarikhShoroReportTa(String tarikhShoroReportTa) {
        this.tarikhShoroReportTa = tarikhShoroReportTa;
    }

    public String getTarikhPayanReportAz() {
        return tarikhPayanReportAz;
    }

    public void setTarikhPayanReportAz(String tarikhPayanReportAz) {
        this.tarikhPayanReportAz = tarikhPayanReportAz;
    }

    public String getTarikhPayanReportTa() {
        return tarikhPayanReportTa;
    }

    public void setTarikhPayanReportTa(String tarikhPayanReportTa) {
        this.tarikhPayanReportTa = tarikhPayanReportTa;
    }

    public StreamedContent getExelFile() {
        return exelFile;
    }

    public void setExelFile(StreamedContent exelFile) {
        this.exelFile = exelFile;
    }

    public List<SavabeghNahast> getSavabeghNahastList() {
        return savabeghNahastList;
    }

    public void setSavabeghNahastList(List<SavabeghNahast> savabeghNahastList) {
        this.savabeghNahastList = savabeghNahastList;
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
