package ezafekari.bean;

import amar.model.Personel;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.DayType;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.util.HibernateUtil;
import ezafekari.EzafeUtil;
import ezafekari.model.EzafeKari;
import ezafekari.model.EzafeKariDyereGhesmatReportModel;
import ezafekari.model.EzafeKariReortModel;
import manage.bean.IndexBean;
import org.hibernate.Session;
import util.Convert;
import util.FillList;
import util.PdfReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class ReportEzafeKariDayereGhesmatBean implements Serializable {

    private String selectedMount = "00";
    private Personel selectedPersonel = new Personel();
    private List<Personel> searchedPersonels = new ArrayList<>();
    private List<EzafeKariDyereGhesmatReportModel> result = new ArrayList<>();
    private String moshakhasat;
    private String shomareMeliSearched, shomarePersoneliSearched2;
    private List<Dayere> dayereList;
    private List<Bakhsh> bakhshes;
    private List<NoeEstekhdam> noeEstekhdams;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emsal = simpleDateFormat.format(today.getTime()).substring(0,4);

    private Alert alert = new Alert();

    private List<String> sal = new ArrayList<>();

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    private String dayereCode;
    private String bakhshCode;

    public ReportEzafeKariDayereGhesmatBean() {
        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        FillList fillList = new FillList();
        dayereList = fillList.dayeres(permissions.get(0));
        noeEstekhdams = fillList.noeEstekhdams(permissions.get(0));

        for (int i = 1360; i < 1450; i++) {
            sal.add(String.valueOf(i));
        }
    }

    public void fillBakhsh(String code){
        Session session=HibernateUtil.getSession();
        bakhshes = session.createQuery("from Bakhsh where dayere.code = ?").setString(0, code).list();
        session.close();
    }

    public void showResults(){
        result.clear();
        if(!selectedMount.equals("00") && !emsal.equals("")){

            StringBuilder query = new StringBuilder("FROM EzafeKari WHERE SUBSTRING(tarikh.tarikh, 1, 7) = ? ");

            if(dayereCode != null && !dayereCode.equals("")){
                query.append("AND personel.dayere.code = '").append(dayereCode).append("' ");
            } else {
                String d = " AND (";
                for (Dayere dayere : dayereList) {
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

            if(bakhshCode != null && !bakhshCode.equals("")){
                query.append("AND personel.bakhsh.code = '").append(bakhshCode).append("' ");
            }
            query.append("ORDER BY personel.shomarePersoneli");

            Session session = HibernateUtil.getSession();
            List<EzafeKari> ezafeKaris = session.createQuery(query.toString()).setString(0, emsal + "/" + selectedMount).list();
            session.close();

            Convert convert = new Convert();

            int count = 1;
            int saat = 0;
            float mablagh = 0;
            String shp = "";

            EzafeKariDyereGhesmatReportModel model = new EzafeKariDyereGhesmatReportModel();

            for (EzafeKari ezafeKari : ezafeKaris) {

                if(shp.equals("")){
                    shp = ezafeKari.getPersonel().getShomarePersoneli();
                    model.setRadif(count++);
                    model.setShp(ezafeKari.getPersonel().getShomarePersoneli());
                    model.setNameoNeshan(ezafeKari.getPersonel().getName().trim() + " " + ezafeKari.getPersonel().getNeshan().trim());
                    if(ezafeKari.getPersonel().getDaraje() != null){
                        model.setDaraje(ezafeKari.getPersonel().getDaraje().getTitle());
                    }
                } else if(!shp.equals(ezafeKari.getPersonel().getShomarePersoneli())){
                    shp = ezafeKari.getPersonel().getShomarePersoneli();

                    model.setSumSaat(convert.clockStr(saat, false));
                    model.setSumMablagh(convert.separatorThousands(mablagh));

                    result.add(model);
                    saat = 0;
                    mablagh = 0;

                    model = new EzafeKariDyereGhesmatReportModel();
                    model.setRadif(count++);
                    model.setShp(ezafeKari.getPersonel().getShomarePersoneli());
                    model.setNameoNeshan(ezafeKari.getPersonel().getName().trim() + " " + ezafeKari.getPersonel().getNeshan().trim());
                    if(ezafeKari.getPersonel().getDaraje() != null){
                        model.setDaraje(ezafeKari.getPersonel().getDaraje().getTitle());
                    }
                }

                saat = convert.sumSaat(saat, ezafeKari.getModat());
                mablagh += EzafeUtil.convertModat(ezafeKari.getModat()) * Integer.parseInt(EzafeUtil.calcMakhaz(ezafeKari.getPersonel()));

            }

            if(model != null) {
                if (model.getRadif() > 0) {
                    model.setSumSaat(convert.clockStr(saat, false));
                    model.setSumMablagh(convert.separatorThousands(mablagh));

                    result.add(model);
                }
            }

        } else {
            alert.fieldIsEmpty("سال، ماه");
        }
    }

    public void pdfPrint(){
        showResults();
        PdfReport pdfReport = new PdfReport();

        Map map = new HashMap<>();
        map.put("tarikh", simpleDateFormat.format(today.getTime()));
        map.put("moshakhasat", moshakhasat);

        pdfReport.pdfReportSingleTable(result, map,  "\\ezafekari\\jasperReport\\ezafeKariDayereGhesmat.jrxml");
    }


    public String getSelectedMount() {
        return selectedMount;
    }

    public void setSelectedMount(String selectedMount) {
        this.selectedMount = selectedMount;
    }

    public List<Dayere> getDayereList() {
        return dayereList;
    }

    public void setDayereList(List<Dayere> dayereList) {
        this.dayereList = dayereList;
    }

    public String getShomarePersoneliSearched2() {
        return shomarePersoneliSearched2;
    }

    public void setShomarePersoneliSearched2(String shomarePersoneliSearched2) {
        this.shomarePersoneliSearched2 = shomarePersoneliSearched2;
    }

    public String getShomareMeliSearched() {
        return shomareMeliSearched;
    }

    public void setShomareMeliSearched(String shomareMeliSearched) {
        this.shomareMeliSearched = shomareMeliSearched;
    }

    public String getEmsal() {
        return emsal;
    }

    public void setEmsal(String emsal) {
        this.emsal = emsal;
    }

    public List<String> getSal() {
        return sal;
    }

    public void setSal(List<String> sal) {
        this.sal = sal;
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

    public List<EzafeKariDyereGhesmatReportModel> getResult() {
        return result;
    }

    public void setResult(List<EzafeKariDyereGhesmatReportModel> result) {
        this.result = result;
    }

    public String getMoshakhasat() {
        return moshakhasat;
    }

    public void setMoshakhasat(String moshakhasat) {
        this.moshakhasat = moshakhasat;
    }

    public List<Bakhsh> getBakhshes() {
        return bakhshes;
    }

    public void setBakhshes(List<Bakhsh> bakhshes) {
        this.bakhshes = bakhshes;
    }

    public String getDayereCode() {
        return dayereCode;
    }

    public void setDayereCode(String dayereCode) {
        this.dayereCode = dayereCode;
    }

    public String getBakhshCode() {
        return bakhshCode;
    }

    public void setBakhshCode(String bakhshCode) {
        this.bakhshCode = bakhshCode;
    }
}