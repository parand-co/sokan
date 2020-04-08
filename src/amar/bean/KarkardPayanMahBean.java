package amar.bean;

import amar.model.KarkardeMahBMahModel;
import amar.model.KarkardePayanMahModel;
import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.SessionGate;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.util.HibernateUtil;
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
public class KarkardPayanMahBean implements Serializable {

    private String selectedMount = "00";
    private Personel selectedPersonel = new Personel();
    private List<Personel> searchedPersonels = new ArrayList<>();
    private List<Personel> selectedPersonels = new ArrayList<>();
    private List<KarkardePayanMahModel> result = new ArrayList<>();
    private List<KarkardeMahBMahModel> mahBMah = new ArrayList<>();
    private int selectedPersonelCount;
    private String nameSearched, neshanSearched, shomareMeliSearched, shomarePersoneliSearched2;
    private SessionGate sessionGate = new SessionGate();
    private List<Dayere> dayereList;
    private List<Yegan> yegans;
    private List<NoeEstekhdam> noeEstekhdams;
    private long selectedDayere = 0, selectedYegan = 0, selectedNoeEstekhdam = 0;

    private String toDate;
    private String fromDate;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emsal = simpleDateFormat.format(today.getTime()).substring(0,4);
    private Convert convert = new Convert();

    private Alert alert = new Alert();

    private List<String> sal = new ArrayList<>();

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    public KarkardPayanMahBean() {
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

        Session session = HibernateUtil.getSession();
        yegans = session.createQuery("FROM Yegan").list();
        session.close();

        for (int i = 1360; i < 1450; i++) {
            sal.add(String.valueOf(i));
        }
    }

    public void loadPersonelsByPersNumExactly() {
        searchedPersonels.clear();
        if (shomarePersoneliSearched2.length() > 7) {
            FillList fillList = new FillList();
            List<Personel> personels = fillList.personels(permissions.get(0), shomarePersoneliSearched2, "", "", "");
            if(personels.size() > 0){
                selectedPersonel = personels.get(0);
                selectedPersonelCount = 1;
            } else {
                selectedPersonelCount = 0;
            }
        } else {
            selectedPersonel = null;
        }
    }

    public void showResults(){
        result.clear();
        if(!selectedMount.equals("00") && !emsal.equals("") &&
                selectedPersonelCount > 0){
            fromDate = emsal + "/" + selectedMount + "/01";
            toDate = emsal + "/" + selectedMount + "/31";

            Session session = HibernateUtil.getSession();
            Long rozKari = (Long) session.createQuery("SELECT count(*) FROM Taghvim WHERE SUBSTRING(tarikh, 1, 7) = ? AND dayType.id != ?").setString(0, emsal + "/" + selectedMount).setInteger(1, 3).uniqueResult();
            session.close();

            for (Personel personel : selectedPersonels) {
                List<Taraddod> taraddodList;
                String shp = personel.getShomarePersoneli();
                String name = personel.getName();
                String family = personel.getNeshan();
                String yegan = null;
                if(personel.getYegan() != null) {
                    yegan = personel.getYegan().getTitle();
                }

                int faghedAmar = 0;
                int morakhasi = 0;
                int nahast = 0;
                int sumSaatKhedmati = 0;
                int sumSaatAdameHozor = 0;
                int sumSaatTakhir = 0;
                int sumTakhir = 0;
                int sumTaradodNaghes = 0;
                int sumSaatTajil = 0;
                int sumTajil = 0;

                if(personel.getName() != null){

//                    for (int i = 1; i < 32; i++) {
                        Session session1 = HibernateUtil.getSession();
                        taraddodList = session1.createQuery("FROM Taraddod WHERE personel.shomarePersoneli = ? AND SUBSTRING(taghvim.tarikh, 1, 7) = ?")
                                .setString(0, personel.getShomarePersoneli())
                                .setString(1, emsal + "/" + selectedMount)
                                .list();
                        session1.close();

                        int tedad = 0;
                        String start = fromDate;

                        faghedAmar = (int) (rozKari - taraddodList.size());

                        for (Taraddod taraddod : taraddodList) {
                            if(taraddod.getTaghvim().getTarikh().equals(start)){
                                tedad++;
                            } else {
                                if(tedad % 2 != 0){
                                    sumTaradodNaghes++;
                                }
                                tedad = 1;
                                start = taraddod.getTaghvim().getTarikh();
                            }

                            if(taraddod.getVaziat().getId() == 2){
                                nahast++;
                            }

                            if(taraddod.getVaziat().getId() == 4 || taraddod.getVaziat().getId() == 5){
                                morakhasi++;
                            }

                            sumSaatKhedmati = convert.sumSaat(sumSaatKhedmati, taraddod.getSaat());
                            int takhir = convert.minesSaat(taraddod.getSaat(), taraddod.getGoroh().getSaatShoroKarAddi(), true);
                            if(takhir > 0){
                                sumTakhir++;
                            }
                            sumSaatTakhir = convert.sumSaat(sumSaatTakhir, takhir);
                            int tajil = convert.minesSaat(taraddod.getSaat(), taraddod.getGoroh().getSaatPayanKarAddi(), false);
                            if(tajil > 0){
                                sumTajil++;
                            }
                            sumSaatTajil = convert.sumSaat(sumTajil, tajil);
                        }

                        sumSaatAdameHozor = convert.sumSaat(sumSaatTakhir, sumSaatTajil);

                        KarkardePayanMahModel model = new KarkardePayanMahModel();
                        model.setShp(shp);
                        model.setName(name);
                        model.setNeshan(family);
                        model.setGhesmat(yegan);
                        model.setRozKari(String.valueOf(rozKari));
                        model.setFaghedAmar(String.valueOf(faghedAmar));
                        model.setMorakhasi(String.valueOf(morakhasi));
                        model.setNahast(String.valueOf(nahast));
                        model.setShomareKart(personel.getShomareKart());
                        model.setSumSaatKhedmat(String.valueOf(sumSaatKhedmati));
                        model.setSumAdameHozor(String.valueOf(sumSaatAdameHozor));
                        model.setSumTakhir(String.valueOf(sumSaatTakhir));
                        model.setSumTedadTakhir(String.valueOf(sumTakhir));
                        model.setSumTedadTaradodNaghes(String.valueOf(sumTaradodNaghes));
                        model.setSumTedadTajil(String.valueOf(sumTajil));

                        result.add(model);
//                    }
                }
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

        pdfReport.pdfReportSingleTable(result, map,  "\\amar\\jasperReport\\karkardMahane.jrxml");
    }

    public void pdfMahBMah(){
        showResults();

        mahBMah.clear();
        int count = 1;

        for (KarkardePayanMahModel payanMahModel : result) {
            KarkardeMahBMahModel model = new KarkardeMahBMahModel();

            model.setRadif(count++);
            model.setShp(payanMahModel.getShp());
            model.setName(payanMahModel.getName());
            model.setNeshan(payanMahModel.getNeshan());
            model.setGhesmat(payanMahModel.getGhesmat());
            model.setRozKari(payanMahModel.getRozKari());
            model.setFaghedAmar(payanMahModel.getFaghedAmar());
            model.setMorakhasi(payanMahModel.getMorakhasi());
            model.setNahast(payanMahModel.getNahast());
            model.setKarkard(String.valueOf(Integer.valueOf(payanMahModel.getRozKari()) - (Integer.valueOf(payanMahModel.getFaghedAmar()) + Integer.valueOf(payanMahModel.getMorakhasi()) + Integer.valueOf(payanMahModel.getNahast()))));

            mahBMah.add(model);
        }

        PdfReport pdfReport = new PdfReport();

        Map map = new HashMap<>();
        map.put("tarikh", simpleDateFormat.format(today.getTime()));

        pdfReport.pdfReportSingleTable(mahBMah, map,  "\\amar\\jasperReport\\karkardMahanePersonelGharardadi.jrxml");
    }

    public void selectDonePersonel() {
        selectedPersonelCount = selectedPersonels.size();
    }

    public void searchPersonel(){
        selectedPersonels.clear();
//        StringBuilder query = new StringBuilder("FROM Personel WHERE 1 = 1");
        StringBuilder query = new StringBuilder();

        if(shomareMeliSearched != null && !shomareMeliSearched.equals("")){
            query.append(" AND codeMeli = '").append(shomareMeliSearched).append("'");
        }
        if(nameSearched != null && !nameSearched.equals("")){
            query.append(" AND name LIKE '%").append(nameSearched).append("%'");
        }
        if(neshanSearched != null && !neshanSearched.equals("")){
            query.append(" AND neshan LIKE '%").append(neshanSearched).append("%'");
        }
        if(selectedDayere != 0){
            query.append(" AND dayere.id = ").append(selectedDayere);
        }
        if(selectedYegan != 0){
            query.append(" AND yegan.id = ").append(selectedYegan);
        }
        if(selectedNoeEstekhdam != 0){
            query.append(" AND noeEstekhdam.id = ").append(selectedNoeEstekhdam);
        }

        FillList fillList = new FillList();
        searchedPersonels = fillList.personels(permissions.get(0), "", "", "", query.toString());
    }


    public String getSelectedMount() {
        return selectedMount;
    }

    public void setSelectedMount(String selectedMount) {
        this.selectedMount = selectedMount;
    }

    public int getSelectedPersonelCount() {
        return selectedPersonelCount;
    }

    public void setSelectedPersonelCount(int selectedPersonelCount) {
        this.selectedPersonelCount = selectedPersonelCount;
    }

    public List<Personel> getSearchedPersonels() {
        return searchedPersonels;
    }

    public void setSearchedPersonels(List<Personel> searchedPersonels) {
        this.searchedPersonels = searchedPersonels;
    }

    public String getNeshanSearched() {
        return neshanSearched;
    }

    public void setNeshanSearched(String neshanSearched) {
        this.neshanSearched = neshanSearched;
    }

    public List<Dayere> getDayereList() {
        return dayereList;
    }

    public void setDayereList(List<Dayere> dayereList) {
        this.dayereList = dayereList;
    }

    public long getSelectedDayere() {
        return selectedDayere;
    }

    public void setSelectedDayere(long selectedDayere) {
        this.selectedDayere = selectedDayere;
    }

    public List<Personel> getSelectedPersonels() {
        return selectedPersonels;
    }

    public void setSelectedPersonels(List<Personel> selectedPersonels) {
        this.selectedPersonels = selectedPersonels;
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

    public String getNameSearched() {
        return nameSearched;
    }

    public void setNameSearched(String nameSearched) {
        this.nameSearched = nameSearched;
    }

    public List<KarkardePayanMahModel> getResult() {
        return result;
    }

    public void setResult(List<KarkardePayanMahModel> result) {
        this.result = result;
    }

    public List<Yegan> getYegans() {
        return yegans;
    }

    public void setYegans(List<Yegan> yegans) {
        this.yegans = yegans;
    }

    public List<NoeEstekhdam> getNoeEstekhdams() {
        return noeEstekhdams;
    }

    public void setNoeEstekhdams(List<NoeEstekhdam> noeEstekhdams) {
        this.noeEstekhdams = noeEstekhdams;
    }

    public long getSelectedYegan() {
        return selectedYegan;
    }

    public void setSelectedYegan(long selectedYegan) {
        this.selectedYegan = selectedYegan;
    }

    public long getSelectedNoeEstekhdam() {
        return selectedNoeEstekhdam;
    }

    public void setSelectedNoeEstekhdam(long selectedNoeEstekhdam) {
        this.selectedNoeEstekhdam = selectedNoeEstekhdam;
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
}