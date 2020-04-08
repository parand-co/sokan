package ezafekari.bean;

import amar.model.Personel;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.DayType;
import dataBaseModel.util.HibernateUtil;
import ezafekari.EzafeUtil;
import ezafekari.model.EzafeKari;
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
public class ReportEzafeKariBean implements Serializable {

    private String selectedMount = "00";
    private Personel selectedPersonel = new Personel();
    private List<Personel> searchedPersonels = new ArrayList<>();
    private List<EzafeKariReortModel> result = new ArrayList<>();
    private String moshakhasat;
    private String shomareMeliSearched, shomarePersoneliSearched2;

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

    public ReportEzafeKariBean() {
        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        for (int i = 1360; i < 1450; i++) {
            sal.add(String.valueOf(i));
        }
    }

    public void loadPersonelsByPersNumExactly() {
        searchedPersonels.clear();
        if (shomarePersoneliSearched2.length() == 9) {
            FillList fillList = new FillList();
            searchedPersonels = fillList.personels(permissions.get(0), shomarePersoneliSearched2, "", "", "");
            if(searchedPersonels.size() == 1) {
                selectedPersonel = searchedPersonels.get(0);
                fillMoshakhasat(selectedPersonel);
            }else{
                moshakhasat = "";
            }
        }else
            selectedPersonel = null;
    }

    public void loadPersonelsByPersMeliExactly() {
        searchedPersonels.clear();
        if (shomareMeliSearched.length() == 10) {
            FillList fillList = new FillList();
            searchedPersonels = fillList.personels(permissions.get(0), "", shomareMeliSearched, "", "");
            if(searchedPersonels.size() == 1) {
                selectedPersonel = searchedPersonels.get(0);
                fillMoshakhasat(selectedPersonel);
            }else{
                moshakhasat = "";
            }
        }else
            selectedPersonel = null;
    }

    private void fillMoshakhasat(Personel personel){
        moshakhasat = "نام: " + personel.getName().trim() + " نام خانوادگی: " + personel.getNeshan().trim();
        if(personel.getNoeEstekhdam() != null){
            moshakhasat += " نوع استخدام: " + personel.getNoeEstekhdam().getTitle();
        }
        if(personel.getDayere() != null){
            moshakhasat += " دایره: " + personel.getDayere().getTitle();
        }
    }

    public void showResults(){
        result.clear();
        if(!selectedMount.equals("00") && !emsal.equals("") && searchedPersonels.size() > 0){

            Session session = HibernateUtil.getSession();
            List<EzafeKari> ezafeKaris = session.createQuery("FROM EzafeKari WHERE SUBSTRING(tarikh.tarikh, 1, 7) = ? AND personel.shomarePersoneli = ?").setString(0, emsal + "/" + selectedMount).setString(1, selectedPersonel.getShomarePersoneli()).list();
            session.close();

            Convert convert = new Convert();
            int count = 1;
            for (EzafeKari ezafeKari : ezafeKaris) {
                EzafeKariReortModel model = new EzafeKariReortModel();

                model.setRadif(count++);
                model.setTarikh(ezafeKari.getTarikh().getTarikh());
                model.setRoz(ezafeKari.getTarikh().getRoozHafte());

                if(selectedPersonel.getGorohKari() != null) {
                    DayType dayType = EzafeUtil.findDayType(selectedPersonel.getGorohKari().getId(), ezafeKari.getTarikh());
                    model.setVaziyat(dayType.getType());
                } else {
                    model.setVaziyat(ezafeKari.getTarikh().getDayType().getType());
                }

                model.setVorod(convert.clockStr(ezafeKari.getSaatStart(), false));
                model.setKhoroj(convert.clockStr(ezafeKari.getSaatEnd(), false));
                if(ezafeKari.getSaatEnd() == null){
                    model.setTaradod("ناقص");
                } else {
                    model.setTaradod("کامل");
                }
                model.setEzafeKari(convert.clockStr(ezafeKari.getModat(), false));

                result.add(model);
            }

        } else {
            alert.fieldIsEmpty("سال، ماه و پرسنل");
        }
    }

    public void pdfPrint(){
        showResults();
        PdfReport pdfReport = new PdfReport();

        Map map = new HashMap<>();
        map.put("tarikh", simpleDateFormat.format(today.getTime()));
        map.put("moshakhasat", moshakhasat);

        pdfReport.pdfReportSingleTable(result, map,  "\\ezafekari\\jasperReport\\ezafeKari.jrxml");
    }


    public String getSelectedMount() {
        return selectedMount;
    }

    public void setSelectedMount(String selectedMount) {
        this.selectedMount = selectedMount;
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

    public List<EzafeKariReortModel> getResult() {
        return result;
    }

    public void setResult(List<EzafeKariReortModel> result) {
        this.result = result;
    }

    public String getMoshakhasat() {
        return moshakhasat;
    }

    public void setMoshakhasat(String moshakhasat) {
        this.moshakhasat = moshakhasat;
    }
}