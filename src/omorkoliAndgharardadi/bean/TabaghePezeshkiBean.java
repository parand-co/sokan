package omorkoliAndgharardadi.bean;

import amar.model.Personel;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.SavabeghTabaghePezeshkiDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import manage.bean.IndexBean;
import omorkoliAndgharardadi.model.SavabeghTabaghePezeshki;
import omorkoliAndgharardadi.model.TabaghePezeshkiReportModel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class TabaghePezeshkiBean implements Serializable {
    private String URL;
    private List<SavabeghTabaghePezeshki> savabegh = new ArrayList<>();
    private List<Tabaghe> satheTabaghe = new ArrayList<>();
    private List<Daraje> darajes = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdams = new ArrayList<>();
    private List<Dayere> dayeres = new ArrayList<>();
    private Alert alert = new Alert();
    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emroz = simpleDateFormat.format(today.getTime());

    // search field
    private String satheTabagheCodeSearch;
    private String shpSearch;
    private String codeMeliSearch;
    private String cartNumberSearch;
    private String nameSearch;
    private String familySearch;
    private String tarikhShoroTabagheAzSearch;
    private String tarikhShoroTabagheTaSearch;
    private String tarikhPayanTabagheAzSearch;
    private String tarikhPayanTabagheTaSearch;
    private Integer modadEtebarSearch;

    private String shp;
    private String cm;
    private Personel personel = null;
    private String moshakhasat;
    private String sharheTabaghe;
    private String satheTabagheCode;
    private String tarikhShoro;
    private String tarikhPayan;
    private Integer modat;

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
    private String rsharhe;
    private String rsathECode;

    private SavabeghTabaghePezeshki selectMode = null;
    private boolean edited = false;

    private StreamedContent exelFile;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public TabaghePezeshkiBean() {
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
        satheTabaghe = session.createQuery("FROM Tabaghe").list();
        darajes = session.createQuery("FROM Daraje").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM SavabeghTabaghePezeshki WHERE 1 = 1 ");

        if(satheTabagheCodeSearch != null && !satheTabagheCodeSearch.equals("")){
            query.append("AND sath.code = '").append(satheTabagheCodeSearch).append("' ");
        }

        if(shpSearch != null && !shpSearch.equals("")){
            query.append("AND personel.shomarePersoneli = '").append(shpSearch).append("' ");
        }

        if(codeMeliSearch != null && !codeMeliSearch.equals("")){
            query.append("AND personel.codeMeli = '").append(codeMeliSearch).append("' ");
        }

        if(cartNumberSearch != null && !cartNumberSearch.equals("")){
            query.append("AND personel.shomareKart = '").append(cartNumberSearch).append("' ");
        }

        if(nameSearch != null && !nameSearch.equals("")){
            query.append("AND personel.name LIKE '%").append(nameSearch).append("%' ");
        }

        if(familySearch != null && !familySearch.equals("")){
            query.append("AND personel.neshan LIKE '%").append(familySearch).append("%' ");
        }

        if(tarikhShoroTabagheAzSearch != null && !tarikhShoroTabagheAzSearch.equals("")){
            query.append("AND tarikhShoro >= '").append(tarikhShoroTabagheAzSearch).append("' ");
        }

        if(tarikhShoroTabagheTaSearch != null && !tarikhShoroTabagheTaSearch.equals("")){
            query.append("AND tarikhShoro <= '").append(tarikhShoroTabagheTaSearch).append("' ");
        }

        if(tarikhPayanTabagheAzSearch != null && !tarikhPayanTabagheAzSearch.equals("")){
            query.append("AND tarikhPayan >= '").append(tarikhPayanTabagheAzSearch).append("' ");
        }

        if(tarikhPayanTabagheTaSearch != null && !tarikhPayanTabagheTaSearch.equals("")){
            query.append("AND tarikhPayan <= '").append(tarikhPayanTabagheTaSearch).append("' ");
        }

        if(modadEtebarSearch != null){
            query.append("AND modat = ").append(modadEtebarSearch).append(" ");
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
            cm = personel.getCodeMeli();
            shp = personel.getShomarePersoneli();
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
                SavabeghTabaghePezeshki model = new SavabeghTabaghePezeshki();

                model.setPersonel(personel);
                model.setSath(satheTabaghe.stream().filter(o -> o.getCode().equals(satheTabagheCode)).findFirst().orElse(null));
                model.setSharh(sharheTabaghe);
                model.setTarikhShoro(tarikhShoro);
                model.setTarikhShoro(tarikhPayan);
                if(modat != null){
                    model.setModat(modat);
                }
                model.setTarikhSabt(emroz);

                SavabeghTabaghePezeshkiDao.getInstance().getBaseQuery().create(model, URL);

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
        satheTabagheCodeSearch = null;
        shpSearch = null;
        codeMeliSearch = null;
        cartNumberSearch = null;
        nameSearch = null;
        familySearch = null;
        tarikhShoroTabagheAzSearch = null;
        tarikhShoroTabagheTaSearch = null;
        tarikhPayanTabagheAzSearch = null;
        tarikhPayanTabagheTaSearch = null;

        modadEtebarSearch = null;

        shp = null;
        personel = null;
        moshakhasat = null;
        sharheTabaghe = null;
        satheTabagheCode = null;
        tarikhShoro = null;
        tarikhPayan = null;
        modat = null;

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
        rsharhe = null;
        rsathECode = null;

        edited = false;
        selectMode = null;
    }

    public void dispach(SavabeghTabaghePezeshki model){
        edited = true;
        shp = model.getPersonel().getShomarePersoneli();
        personel = model.getPersonel();
        if(personel.getDaraje() != null) {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan() + "     درجه :" + personel.getDaraje().getTitle();
        } else {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan();
        }

        if(model.getSath() != null) {
            satheTabagheCode = model.getSath().getCode();
        }
        tarikhShoro = model.getTarikhShoro();
        tarikhPayan = model.getTarikhPayan();
        modat = model.getModat();
        sharheTabaghe = model.getSharh();
        selectMode = model;
    }

    public void edit(){
        if(personel != null && selectMode != null){
            if(tarikhShoro != null && !tarikhShoro.equals("") && tarikhPayan != null && !tarikhPayan.equals("") && modat != null && modat > 0){
                SavabeghTabaghePezeshki model = selectMode;

                model.setSath(satheTabaghe.stream().filter(O -> O.getCode().equals(satheTabagheCode)).findFirst().orElse(null));
                model.setSharh(sharheTabaghe);
                if(modat != null){
                    model.setModat(modat);
                }
                model.setTarikhShoro(tarikhShoro);
                model.setTarikhPayan(tarikhPayan);

                SavabeghTabaghePezeshkiDao.getInstance().getBaseQuery().createOrUpdate(model, URL);

                alert.successEdit();

                savabegh.remove(selectMode);
                savabegh.add(model);

                nuller();
            } else {
                alert.fieldIsEmpty("تاریخ شروع و تاریخ پایان و مدت استراحت");
            }
        }
    }

    public void startDelete(SavabeghTabaghePezeshki model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        SavabeghTabaghePezeshkiDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }

    public void excelReport(){
        List<TabaghePezeshkiReportModel> models = report();

        SessionUtil sessionUtil = new SessionUtil();
        String fileName;
        if(sessionUtil.getPermission() != null){
            fileName = sessionUtil.getPermission().getUser().getUserName() + System.currentTimeMillis() ;
        } else {
            fileName = "file" + System.currentTimeMillis() ;
        }
        String READ_PATH = "/resources/uploads/excel/";
        Excel excel = new Excel(READ_PATH, fileName);

        String[] columns = {"ردیف", "نام", "نشان", "درجه", "دایره", "قسمت", "مدت اعتبار طبقه پزشکی", "تاریخ شروع طبقه", "تاریخ پایان طبقه", "شرح طبقه پزشکی"};
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
        map.put("tarikh", simpleDateFormat.format(Calendar.getInstance().getTime()));

        PdfReport report = new PdfReport();

        report.pdfReportSingleTable(models, map, "\\omorkoliAndGharardadi\\jasperReport\\tabaghePezeshki.jrxml");

    }

    private List<TabaghePezeshkiReportModel> report(){
        List<TabaghePezeshkiReportModel> result = new ArrayList<>();

        StringBuilder query = new StringBuilder("FROM SavabeghTabaghePezeshki WHERE 1=1");

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

        if(rsharhe != null && !rsharhe.equals("")){
            query.append(" AND sharh = '").append(rsharhe).append("'");
        }

        if(rsathECode != null && !rsathECode.equals("")){
            query.append(" AND sath.code = '").append(rsathECode).append("'");
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

        Session session = HibernateUtil.getSession();
        List<SavabeghTabaghePezeshki> models = session.createQuery(query.toString()).list();
        session.close();

        int i = 1;
        for (SavabeghTabaghePezeshki model : models) {
            TabaghePezeshkiReportModel data = new TabaghePezeshkiReportModel();

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
            data.setSharh(model.getSharh());

            result.add(data);
        }

        return result;
    }




    // GETTER AND SETTER


    public List<SavabeghTabaghePezeshki> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<SavabeghTabaghePezeshki> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Tabaghe> getSatheTabaghe() {
        return satheTabaghe;
    }

    public void setSatheTabaghe(List<Tabaghe> satheTabaghe) {
        this.satheTabaghe = satheTabaghe;
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

    public String getSatheTabagheCodeSearch() {
        return satheTabagheCodeSearch;
    }

    public void setSatheTabagheCodeSearch(String satheTabagheCodeSearch) {
        this.satheTabagheCodeSearch = satheTabagheCodeSearch;
    }

    public String getTarikhShoroTabagheAzSearch() {
        return tarikhShoroTabagheAzSearch;
    }

    public void setTarikhShoroTabagheAzSearch(String tarikhShoroTabagheAzSearch) {
        this.tarikhShoroTabagheAzSearch = tarikhShoroTabagheAzSearch;
    }

    public String getTarikhShoroTabagheTaSearch() {
        return tarikhShoroTabagheTaSearch;
    }

    public void setTarikhShoroTabagheTaSearch(String tarikhShoroTabagheTaSearch) {
        this.tarikhShoroTabagheTaSearch = tarikhShoroTabagheTaSearch;
    }

    public String getTarikhPayanTabagheAzSearch() {
        return tarikhPayanTabagheAzSearch;
    }

    public void setTarikhPayanTabagheAzSearch(String tarikhPayanTabagheAzSearch) {
        this.tarikhPayanTabagheAzSearch = tarikhPayanTabagheAzSearch;
    }

    public String getTarikhPayanTabagheTaSearch() {
        return tarikhPayanTabagheTaSearch;
    }

    public void setTarikhPayanTabagheTaSearch(String tarikhPayanTabagheTaSearch) {
        this.tarikhPayanTabagheTaSearch = tarikhPayanTabagheTaSearch;
    }

    public Integer getModadEtebarSearch() {
        return modadEtebarSearch;
    }

    public void setModadEtebarSearch(Integer modadEtebarSearch) {
        this.modadEtebarSearch = modadEtebarSearch;
    }

    public String getSharheTabaghe() {
        return sharheTabaghe;
    }

    public void setSharheTabaghe(String sharheTabaghe) {
        this.sharheTabaghe = sharheTabaghe;
    }

    public String getSatheTabagheCode() {
        return satheTabagheCode;
    }

    public void setSatheTabagheCode(String satheTabagheCode) {
        this.satheTabagheCode = satheTabagheCode;
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

    public StreamedContent getExelFile() {
        return exelFile;
    }

    public void setExelFile(StreamedContent exelFile) {
        this.exelFile = exelFile;
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

    public String getRsharhe() {
        return rsharhe;
    }

    public void setRsharhe(String rsharhe) {
        this.rsharhe = rsharhe;
    }

    public String getRsathECode() {
        return rsathECode;
    }

    public void setRsathECode(String rsathECode) {
        this.rsathECode = rsathECode;
    }

    public List<Daraje> getDarajes() {
        return darajes;
    }

    public void setDarajes(List<Daraje> darajes) {
        this.darajes = darajes;
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