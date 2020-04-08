package omorkoliAndgharardadi.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.SavabeghEsterahatPezeshkiDao;
import dataBaseModel.dao.TaraddodDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import manage.bean.IndexBean;
import omorkoliAndgharardadi.model.EsterahatPezeshkiReportModel;
import omorkoliAndgharardadi.model.SavabeghEsterahatPezeshki;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Excel;
import util.FillList;
import util.PdfReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class EsterahatPezeshkiBean implements Serializable {
    private String URL;
    private List<SavabeghEsterahatPezeshki> savabegh = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdams = new ArrayList<>();
    private List<Dayere> dayeres = new ArrayList<>();
    private Alert alert = new Alert();
    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emroz = simpleDateFormat.format(today.getTime());

    // search field
    private String noeEstekhdamCodeSearch;
    private String shpSearch;
    private String codeMeliSearch;
    private String cartNumberSearch;
    private String nameSearch;
    private String familySearch;
    private String tarikhEsterahatAzSearch;
    private String tarikhEsterahatTaSearch;
    private Integer modatEsterahatAzSearch;
    private Integer modatEsterahatTaSearch;

    private String shp;
    private String cm;
    private Personel personel = null;
    private String moshakhasat;
    private String pezeshkName;
    private String pezeshkFamily;
    private String elat;
    private Integer modat;
    private String tarikhShoro;
    private String tarikhPayan;
    private String marhale;
    private Boolean taed = false;

    private SavabeghEsterahatPezeshki selectMode = null;
    private boolean edited = false;

    private List<Daraje> darajes = new ArrayList<>();

    private String rshp;
    private String rcodeMeli;
    private String rshomareKart;
    private String rname;
    private String rfamily;
    private String rdarajeCode;
    private String rshoroAz;
    private String rshoroTa;
    private String rpayanAz;
    private String rpayanTa;
    private Integer rtaeed = 3;

    private StreamedContent exelFile;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public EsterahatPezeshkiBean() {
        URL = IndexBean.url;

        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        fillSavabegh();
    }

    private void fillSavabegh(){
        FillList fillList = new FillList();
        noeEstekhdams = fillList.noeEstekhdams(permissions.get(0));
        dayeres = fillList.dayeres(permissions.get(0));

        Session session = HibernateUtil.getSession();
        darajes = session.createQuery("FROM Daraje").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM SavabeghEsterahatPezeshki WHERE 1 = 1 ");

        if(noeEstekhdamCodeSearch != null && !noeEstekhdamCodeSearch.equals("")){
            query.append("AND personel.noeEstekhdam.id = ").append(noeEstekhdamCodeSearch).append(" ");
        } else {
            String q = " AND (";
            for (NoeEstekhdam noeEstekhdam : noeEstekhdams) {
                if(q.equals(" AND (")){
                    q += "personel.noeEstekhdam.id = " + noeEstekhdam.getId();
                } else {
                    q += " OR personel.noeEstekhdam.id = " + noeEstekhdam.getId();
                }
            }
            if(!q.equals(" AND (")){
               q += ")";
               query.append(q);
            }
        }
        String d = " AND (";
        for (Dayere dayere : dayeres) {
            if(d.equals(" AND (")){
                d += "personel.dayere.id = " + dayere.getId();
            } else {
                d += " OR personel.dayere.id = " + dayere.getId();
            }
        }
        if(!d.equals(" AND (")){
            d += ")";
            query.append(d);
        }

        FillList fillList = new FillList();
        List<Bakhsh> bakhshFill = fillList.bakhsh(permissions.get(0));
        d = " AND (";
        for (Bakhsh bakhsh : bakhshFill) {
            if(d.equals(" AND (")){
                d += "personel.bakhsh.id = " + bakhsh.getId();
            } else {
                d += " OR personel.bakhsh.id = " + bakhsh.getId();
            }
        }
        if(!d.equals(" AND (")){
            d += ")";
            query.append(d);
        }

        if(cartNumberSearch != null && !cartNumberSearch.equals("")){
            query.append("AND personel.shomareKart = '").append(cartNumberSearch).append("' ");
        }

        if(shpSearch != null && !shpSearch.equals("")){
            query.append("AND personel.shomarePersoneli = '").append(shpSearch).append("' ");
        }

        if(codeMeliSearch != null && !codeMeliSearch.equals("")){
            query.append("AND personel.codeMeli = '").append(codeMeliSearch).append("' ");
        }

        if(nameSearch != null && !nameSearch.equals("")){
            query.append("AND personel.name LIKE '%").append(nameSearch).append("%' ");
        }

        if(familySearch != null && !familySearch.equals("")){
            query.append("AND personel.neshan LIKE '%").append(familySearch).append("%' ");
        }

        if(tarikhEsterahatAzSearch != null && !tarikhEsterahatAzSearch.equals("")){
            query.append("AND tarikhShoro >= '").append(tarikhEsterahatAzSearch).append("' ");
        }

        if(tarikhEsterahatTaSearch != null && !tarikhEsterahatTaSearch.equals("")){
            query.append("AND tarikhPayan <= '").append(tarikhEsterahatTaSearch).append("' ");
        }

        if(modatEsterahatAzSearch != null){
            query.append("AND modat >= ").append(modatEsterahatAzSearch).append(" ");
        }

        if(modatEsterahatTaSearch != null){
            query.append("AND modat <= ").append(modatEsterahatTaSearch).append(" ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void findPersonel(){
        FillList fillList = new FillList();
        List<Personel> personels = fillList.personels(permissions.get(0), shp, cm, "", "");

        if(personels.size() > 0){
            personel = personels.get(0);
            if(personel.getDaraje() != null) {
                moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan() + "     درجه :" + personel.getDaraje().getTitle();
            } else {
                moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan();
            }
            shp = personel.getShomarePersoneli();
            cm = personel.getCodeMeli();
        } else {
            personel = null;
            shp = null;
            cm = null;
            moshakhasat = null;
            alert.notFoundPersonel();
        }
    }

    public void save(){
        if(personel != null){
            if(tarikhShoro != null && !tarikhShoro.equals("") && tarikhPayan != null && !tarikhPayan.equals("") && modat != null && modat > 0){
                SavabeghEsterahatPezeshki model = new SavabeghEsterahatPezeshki();

                model.setPersonel(personel);
                model.setNamePezeshk(pezeshkName);
                model.setNeshanPezeshk(pezeshkFamily);
                model.setElat(elat);
                if(modat != null){
                    model.setModat(modat);
                }
                model.setTarikhShoro(tarikhShoro);
                model.setTarikhPayan(tarikhPayan);
                model.setMarhale(marhale);
                model.setTaeed(taed);
                model.setTarikhSabt(emroz);

                if(taed){
                    changeTaradodd(tarikhShoro, tarikhPayan);
                }

                SavabeghEsterahatPezeshkiDao.getInstance().getBaseQuery().create(model, URL);

                alert.successSave();

                savabegh.add(model);
                nuller();
            } else {
                alert.fieldIsEmpty("تاریخ شروع و تاریخ پایان و مدت استراحت");
            }
        } else {
            alert.fieldIsEmpty("شماره پرسنلی");
        }
    }

    public void nuller() {
        shp = "";
        personel = null;
        pezeshkName = "";
        pezeshkFamily = "";
        elat = "";
        modat = null;
        tarikhShoro = "";
        tarikhPayan = "";
        marhale = "";
        taed = false;
        moshakhasat = null;

        rshp = null;
        rcodeMeli = null;
        rshomareKart = null;
        rname = null;
        rfamily = null;
        rdarajeCode = null;
        rshoroAz = null;
        rshoroTa = null;
        rpayanAz = null;
        rpayanTa = null;
        rtaeed = 3;

        edited = false;
        selectMode = null;
    }

    private void changeTaradodd(String shoro, String payan){
        Session session = HibernateUtil.getSession();
        List<Taraddod> taraddods = session.createQuery("FROM Taraddod WHERE personel.shomarePersoneli = ? AND taghvim.tarikh >= ? AND taghvim.tarikh <= ?")
                .setString(0, personel.getShomarePersoneli())
                .setString(1, shoro)
                .setString(2,payan).list();
        session.close();

        Session session1 = HibernateUtil.getSession();
        MojavezRozane mojavezRozane = (MojavezRozane) session1.createQuery("FROM MojavezRozane WHERE id = 7").list().get(0);
        session1.close();

        for (Taraddod taraddod : taraddods) {
            taraddod.setVaziat(mojavezRozane);
            TaraddodDao.getInstance().getBaseQuery().createOrUpdate(taraddod, URL);
        }
    }

    public void dispach(SavabeghEsterahatPezeshki model){
        edited = true;
        shp = model.getPersonel().getShomarePersoneli();
        personel = model.getPersonel();
        if(personel.getDaraje() != null) {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan() + "     درجه :" + personel.getDaraje().getTitle();
        } else {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan();
        }

        pezeshkName = model.getNamePezeshk();
        pezeshkFamily = model.getNeshanPezeshk();
        elat = model.getElat();
        tarikhShoro = model.getTarikhShoro();
        tarikhPayan = model.getTarikhPayan();
        modat = model.getModat();
        marhale = model.getMarhale();
        taed = model.isTaeed();
        selectMode = model;
    }

    public void edit(){
        if(personel != null && selectMode != null){
            if(tarikhShoro != null && !tarikhShoro.equals("") && tarikhPayan != null && !tarikhPayan.equals("") && modat != null && modat > 0){
                SavabeghEsterahatPezeshki model = selectMode;

                model.setNamePezeshk(pezeshkName);
                model.setNeshanPezeshk(pezeshkFamily);
                model.setElat(elat);
                if(modat != null){
                    model.setModat(modat);
                }
                model.setTarikhShoro(tarikhShoro);
                model.setTarikhPayan(tarikhPayan);
                model.setMarhale(marhale);
                model.setTaeed(taed);

                if(taed){
                    changeTaradodd(tarikhShoro, tarikhPayan);
                }

                SavabeghEsterahatPezeshkiDao.getInstance().getBaseQuery().createOrUpdate(model, URL);

                alert.successEdit();

                savabegh.remove(selectMode);
                savabegh.add(model);

                nuller();
            } else {
                alert.fieldIsEmpty("تاریخ شروع و تاریخ پایان و مدت استراحت");
            }
        }
    }

    public void startDelete(SavabeghEsterahatPezeshki model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        SavabeghEsterahatPezeshkiDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }

    public String checkTaeed(Boolean taed){
        if(taed) return "تایید شده";
        else return "تایید نشده";
    }

    public void excelReport(){
        List<EsterahatPezeshkiReportModel> models = report();

        SessionUtil sessionUtil = new SessionUtil();
        String fileName;
        if(sessionUtil.getPermission() != null){
            fileName = sessionUtil.getPermission().getUser().getUserName() + System.currentTimeMillis() ;
        } else {
            fileName = "file" + System.currentTimeMillis() ;
        }
        String READ_PATH = "/resources/uploads/excel/";
        Excel excel = new Excel(READ_PATH, fileName);

        String[] columns = {"ردیف", "نام", "نشان", "درجه", "دایره", "قسمت", "مدت استراحت پزشکی", "تاریخ شروع استراحت پزشکی", "تاریخ پایان استراحت پزشکی"};
        if(excel.create("گزارش",columns, models)){
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(READ_PATH + "\\" + fileName + ".xlsx");
            exelFile = new DefaultStreamedContent(stream, "application/vnd.ms-exel", "excel.xlsx");
        } else {
            alert.error();
        }
    }

    public void pdfReport(){
        List models = report();

        Map map = new HashMap<>();
        map.put("logo", "../../resources/images/logo/artesh.png");
        map.put("tarikh", simpleDateFormat.format(com.ibm.icu.util.Calendar.getInstance().getTime()));

        PdfReport report = new PdfReport();

        report.pdfReportSingleTable(models, map, "\\omorkoliAndGharardadi\\jasperReport\\esterahatPezeshki.jrxml");

    }

    private List<EsterahatPezeshkiReportModel> report(){
        List<EsterahatPezeshkiReportModel> result = new ArrayList<>();

        StringBuilder query = new StringBuilder("FROM SavabeghEsterahatPezeshki WHERE 1=1");

        if(rshp != null && !rshp.equals("")){
            query.append(" AND personel.shomarePersoneli = '").append(rshp).append("'");
        }

        if(rcodeMeli != null && !rcodeMeli.equals("")){
            query.append(" AND personel.codeMeli = '").append(rcodeMeli).append("'");
        }

        if(rshomareKart != null && !rshomareKart.equals("")){
            query.append(" AND personel.shomareKart = '").append(rshomareKart).append("'");
        }

        if(rname != null && !rname.equals("")){
            query.append(" AND personel.name  LIKE'%").append(rname).append("%'");
        }

        if(rfamily != null && !rfamily.equals("")){
            query.append(" AND personel.neshan LIKE'%").append(rfamily).append("%'");
        }

        if(rdarajeCode != null && !rdarajeCode.equals("")){
            query.append(" AND personel.daraje.code = '").append(rdarajeCode).append("'");
        }

        if(rshoroAz != null && !rshoroAz.equals("")){
            query.append(" AND tarikhShoro >= '").append(rshoroAz).append("'");
        }

        if(rshoroTa != null && !rshoroTa.equals("")){
            query.append(" AND tarikhShoro <= '").append(rshoroTa).append("'");
        }

        if(rpayanAz != null && !rpayanAz.equals("")){
            query.append(" AND tarikhPayan >= '").append(rpayanAz).append("'");
        }

        if(rpayanTa != null && !rpayanTa.equals("")){
            query.append(" AND tarikhPayan <= '").append(rpayanTa).append("'");
        }

        String q = " AND (";
        for (NoeEstekhdam noeEstekhdam : noeEstekhdams) {
            if(q.equals(" AND (")){
                q += "personel.noeEstekhdam.id = " + noeEstekhdam.getId();
            } else {
                q += " OR personel.noeEstekhdam.id = " + noeEstekhdam.getId();
            }
        }
        if(!q.equals(" AND (")){
            q += ")";
            query.append(q);
        }

        String d = " AND (";
        for (Dayere dayere : dayeres) {
            if(d.equals(" AND (")){
                d += "personel.dayere.id = " + dayere.getId();
            } else {
                d += " OR personel.dayere.id = " + dayere.getId();
            }
        }
        if(!d.equals(" AND (")){
            d += ")";
            query.append(d);
        }

        if(rtaeed == 1){
            query.append(" AND taeed = true");
        }
        if(rtaeed == 2){
            query.append(" AND taeed = false");
        }

        Session session = HibernateUtil.getSession();
        List<SavabeghEsterahatPezeshki> models = session.createQuery(query.toString()).list();
        session.close();

        int i = 1;
        for (SavabeghEsterahatPezeshki model : models) {
            EsterahatPezeshkiReportModel data = new EsterahatPezeshkiReportModel();

            data.setId(i++);
            data.setName(model.getPersonel().getName());
            data.setNeshan(model.getPersonel().getNeshan());
            if(model.getPersonel().getDaraje() != null) {
                data.setDaraje(model.getPersonel().getDaraje().getTitle());
            }
            if(model.getPersonel().getDayere() != null) {
                data.setDayere(model.getPersonel().getDayere().getTitle());
            }
            if(model.getPersonel().getBakhsh() != null) {
                data.setGhesmat(model.getPersonel().getBakhsh().getTitle());
            }
            data.setModat(model.getModat());
            data.setShoro(model.getTarikhShoro());
            data.setPayan(model.getTarikhPayan());

            result.add(data);
        }

        return result;
    }




    // GETTER AND SETTER

    public List<SavabeghEsterahatPezeshki> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<SavabeghEsterahatPezeshki> savabegh) {
        this.savabegh = savabegh;
    }

    public List<NoeEstekhdam> getNoeEstekhdams() {
        return noeEstekhdams;
    }

    public void setNoeEstekhdams(List<NoeEstekhdam> noeEstekhdams) {
        this.noeEstekhdams = noeEstekhdams;
    }

    public String getNoeEstekhdamCodeSearch() {
        return noeEstekhdamCodeSearch;
    }

    public void setNoeEstekhdamCodeSearch(String noeEstekhdamCodeSearch) {
        this.noeEstekhdamCodeSearch = noeEstekhdamCodeSearch;
    }

    public String getShpSearch() {
        return shpSearch;
    }

    public void setShpSearch(String shpSearch) {
        this.shpSearch = shpSearch;
    }

    public String getCodeMeliSearch() {
        return codeMeliSearch;
    }

    public void setCodeMeliSearch(String codeMeliSearch) {
        this.codeMeliSearch = codeMeliSearch;
    }

    public String getCartNumberSearch() {
        return cartNumberSearch;
    }

    public void setCartNumberSearch(String cartNumberSearch) {
        this.cartNumberSearch = cartNumberSearch;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getFamilySearch() {
        return familySearch;
    }

    public void setFamilySearch(String familySearch) {
        this.familySearch = familySearch;
    }

    public String getTarikhEsterahatAzSearch() {
        return tarikhEsterahatAzSearch;
    }

    public void setTarikhEsterahatAzSearch(String tarikhEsterahatAzSearch) {
        this.tarikhEsterahatAzSearch = tarikhEsterahatAzSearch;
    }

    public String getTarikhEsterahatTaSearch() {
        return tarikhEsterahatTaSearch;
    }

    public void setTarikhEsterahatTaSearch(String tarikhEsterahatTaSearch) {
        this.tarikhEsterahatTaSearch = tarikhEsterahatTaSearch;
    }

    public Integer getModatEsterahatAzSearch() {
        return modatEsterahatAzSearch;
    }

    public void setModatEsterahatAzSearch(Integer modatEsterahatAzSearch) {
        this.modatEsterahatAzSearch = modatEsterahatAzSearch;
    }

    public Integer getModatEsterahatTaSearch() {
        return modatEsterahatTaSearch;
    }

    public void setModatEsterahatTaSearch(Integer modatEsterahatTaSearch) {
        this.modatEsterahatTaSearch = modatEsterahatTaSearch;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public String getPezeshkName() {
        return pezeshkName;
    }

    public void setPezeshkName(String pezeshkName) {
        this.pezeshkName = pezeshkName;
    }

    public String getPezeshkFamily() {
        return pezeshkFamily;
    }

    public void setPezeshkFamily(String pezeshkFamily) {
        this.pezeshkFamily = pezeshkFamily;
    }

    public String getElat() {
        return elat;
    }

    public void setElat(String elat) {
        this.elat = elat;
    }

    public Integer getModat() {
        return modat;
    }

    public void setModat(Integer modat) {
        this.modat = modat;
    }

    public String getTarikhShoro() {
        return tarikhShoro;
    }

    public void setTarikhShoro(String tarikhShoro) {
        this.tarikhShoro = tarikhShoro;
    }

    public String getTarikhPayan() {
        return tarikhPayan;
    }

    public void setTarikhPayan(String tarikhPayan) {
        this.tarikhPayan = tarikhPayan;
    }

    public String getMarhale() {
        return marhale;
    }

    public void setMarhale(String marhale) {
        this.marhale = marhale;
    }

    public Boolean getTaed() {
        return taed;
    }

    public void setTaed(Boolean taed) {
        this.taed = taed;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getMoshakhasat() {
        return moshakhasat;
    }

    public void setMoshakhasat(String moshakhasat) {
        this.moshakhasat = moshakhasat;
    }

    public String getRshp() {
        return rshp;
    }

    public void setRshp(String rshp) {
        this.rshp = rshp;
    }

    public String getRcodeMeli() {
        return rcodeMeli;
    }

    public void setRcodeMeli(String rcodeMeli) {
        this.rcodeMeli = rcodeMeli;
    }

    public String getRshomareKart() {
        return rshomareKart;
    }

    public void setRshomareKart(String rshomareKart) {
        this.rshomareKart = rshomareKart;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRfamily() {
        return rfamily;
    }

    public void setRfamily(String rfamily) {
        this.rfamily = rfamily;
    }

    public Integer getRtaeed() {
        return rtaeed;
    }

    public void setRtaeed(Integer rtaeed) {
        this.rtaeed = rtaeed;
    }

    public StreamedContent getExelFile() {
        return exelFile;
    }

    public void setExelFile(StreamedContent exelFile) {
        this.exelFile = exelFile;
    }

    public List<Daraje> getDarajes() {
        return darajes;
    }

    public void setDarajes(List<Daraje> darajes) {
        this.darajes = darajes;
    }

    public String getRdarajeCode() {
        return rdarajeCode;
    }

    public void setRdarajeCode(String rdarajeCode) {
        this.rdarajeCode = rdarajeCode;
    }

    public String getRshoroAz() {
        return rshoroAz;
    }

    public void setRshoroAz(String rshoroAz) {
        this.rshoroAz = rshoroAz;
    }

    public String getRshoroTa() {
        return rshoroTa;
    }

    public void setRshoroTa(String rshoroTa) {
        this.rshoroTa = rshoroTa;
    }

    public String getRpayanAz() {
        return rpayanAz;
    }

    public void setRpayanAz(String rpayanAz) {
        this.rpayanAz = rpayanAz;
    }

    public String getRpayanTa() {
        return rpayanTa;
    }

    public void setRpayanTa(String rpayanTa) {
        this.rpayanTa = rpayanTa;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
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