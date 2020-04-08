package amar.bean;

import amar.model.KarkardRozaneModel;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.hibernate.Session;
import util.Convert;
import util.FillList;
import util.PdfReport;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class KarkardRozaneBean implements Serializable {

    private String selectedMount = "00";
    private String selectedDateAz = "";
    private String selectedDateTa = "";
    private Boolean kholaseVaziyat = false;
    private Personel selectedPersonel = new Personel();
    private List<Personel> searchedPersonels = new ArrayList<>();
    private List<Personel> selectedPersonels = new ArrayList<>();
    private List<Goroh> gorohs;
    private List<KarkardRozaneModel> result = new ArrayList<>();
    private int selectedPersonelCount;
    private String nameSearched, neshanSearched, shomareMeliSearched, shomarePersoneliSearched2, shomareKartSearchedAz, shomareKartSearchedTa;
    private SessionGate sessionGate = new SessionGate();
    private List<Dayere> dayereList;
    private List<Bakhsh> bakhshList = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdams;
    private List<Daraje> darajes;
    private long selectedDayere = 0, selectedBakhsh = 0, selectedNoeEstekhdam = 0, selectedDaraje = 0, selectedSorted = 0;

    private String toDate;
    private String fromDate;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emsal = simpleDateFormat.format(today.getTime()).substring(0,4);
    private Convert convert = new Convert();
    private KarkardRozaneModel model = new KarkardRozaneModel();

    private Alert alert = new Alert();

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    public KarkardRozaneBean() {
        Session session = HibernateUtil.getSession();
        darajes = session.createQuery("FROM Daraje").list();
        gorohs = session.createQuery("FROM Goroh").list();
        session.close();

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
    }

    public void changeDayere() {
        bakhshList.clear();
        bakhshList = sessionGate.findBakhshByDayereID(selectedDayere);
    }

    public void loadPersonelsByPersNumExactly() {
        searchedPersonels.clear();
        if (shomarePersoneliSearched2.length() > 7) {
            FillList fillList = new FillList();
            List<Personel> personels = fillList.personels(permissions.get(0), shomarePersoneliSearched2, "", "", "");

            if(personels.size()>0) {
                selectedPersonel = personels.get(0);
                selectedPersonelCount = 1;
            }else
                selectedPersonelCount = 0;
        }else
            selectedPersonel = null;
    }

    public void showResults() {
        result.clear();
        if (selectedPersonelCount > 0){
            if (!selectedMount.equals("00") || (!selectedDateAz.matches("") && !selectedDateTa.matches(""))){
                if (selectedPersonel != null){
                    selectedPersonels.add(selectedPersonel);
                }

                if(!selectedDateAz.matches("") && !selectedDateTa.matches("")){
                    fromDate = selectedDateAz;
                    toDate = selectedDateTa;
                } else {
                    fromDate = emsal + "/" + selectedMount + "/01";
                    toDate = emsal + "/" + selectedMount + "/31";
                }

                for (Personel personel : selectedPersonels) {
                    List<Taraddod> taraddodList;
                    if(personel.getName() != null){
                        Session session = HibernateUtil.getSession();
                        taraddodList = session.createQuery("FROM Taraddod WHERE personel.shomarePersoneli = ? AND taghvim.tarikh >= ? AND taghvim.tarikh <= ?")
                                .setString(0, personel.getShomarePersoneli())
                                .setString(1, fromDate)
                                .setString(2, toDate)
                                .list();
                        session.close();
                        model = new KarkardRozaneModel();

                        for (Taraddod taraddod : taraddodList) {
                            if(taraddod.getTaghvim() != null){
                                if(model.getTaghvim() == null || model.getTaghvim().contains(taraddod.getTaghvim().getTarikh())){
                                    model.setPersonel(taraddod.getPersonel());
                                    model.setTarikh(taraddod.getTaghvim().getRoozHafte() + " " + taraddod.getTaghvim().getTarikh().substring(8,10) + "/" + taraddod.getTaghvim().getTarikh().substring(5,7) + "/" + taraddod.getTaghvim().getTarikh().substring(0,4));
                                    model.setTaghvim(taraddod.getTaghvim().getTarikh());

                                    if(model.getV1() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV1(convert.clockStr(taraddod.getSaat(), false));
                                            if(taraddod.getGoroh() != null){
                                                Goroh goroh = gorohs.stream().filter(o-> o.getCode().equals(taraddod.getGoroh().getCode())).findFirst().orElse(null);
                                                if(Integer.valueOf(goroh.getSaatShoroKarAddi()) < taraddod.getSaat()){
                                                    String saatShoro = goroh.getSaatShoroKarAddi().substring(0,2);
                                                    String daghigheShoro = goroh.getSaatShoroKarAddi().substring(2,4);

                                                    String vorod = String.valueOf(taraddod.getSaat());
                                                    if(vorod.length() == 3){
                                                        vorod = "0" + vorod;
                                                    }

                                                    String saatVorod = vorod.substring(0,2);
                                                    String daghigheVorod = vorod.substring(2,4);

                                                    int finalSaat = Integer.valueOf(saatVorod) - Integer.valueOf(saatShoro);
                                                    int finalDaghighe = Integer.valueOf(daghigheVorod) - Integer.valueOf(daghigheShoro);

                                                    if(finalDaghighe < 0){
                                                        finalSaat -= 1;
                                                        finalDaghighe += 60;
                                                    }
                                                    model.setTakhir(finalSaat + ":" + finalDaghighe);
                                                }
                                            }
                                        } else {
                                            model.setV1("");
                                        }

                                        if(taraddod.getVaziat() != null){
                                            model.setMojavez(taraddod.getVaziat().getTitle());
                                        }
                                    } else if(model.getV2() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV2(convert.clockStr(taraddod.getSaat(), false));
                                            if(taraddod.getGoroh() != null){
                                                Goroh goroh = gorohs.stream().filter(o-> o.getCode().equals(taraddod.getGoroh().getCode())).findFirst().orElse(null);
                                                if(Integer.valueOf(goroh.getSaatPayanKarAddi()) > taraddod.getSaat()){
                                                    String saatPayan = goroh.getSaatPayanKarAddi().substring(0,2);
                                                    String daghighePayan = goroh.getSaatPayanKarAddi().substring(2,4);

                                                    String khoroj = String.valueOf(taraddod.getSaat());
                                                    if(khoroj.length() == 3){
                                                        khoroj = "0" + khoroj;
                                                    }

                                                    String saatKhoroj = khoroj.substring(0,2);
                                                    String daghigheKhoroj = khoroj.substring(2,4);

                                                    int finalSaat = Integer.valueOf(saatPayan) - Integer.valueOf(saatKhoroj);
                                                    int finalDaghighe = Integer.valueOf(daghighePayan) - Integer.valueOf(daghigheKhoroj);

                                                    if(finalDaghighe < 0){
                                                        finalSaat -= 1;
                                                        finalDaghighe += 60;
                                                    }
                                                    model.setTajil(finalSaat + ":" + finalDaghighe);
                                                }
                                            }
                                        } else {
                                            model.setV2("");
                                        }
                                    } else if(model.getV3() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV3(convert.clockStr(taraddod.getSaat(), false));
                                        } else {
                                            model.setV3("");
                                        }
                                    } else if(model.getV4() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV4(convert.clockStr(taraddod.getSaat(), false));
                                        } else {
                                            model.setV4("");
                                        }
                                    } else if(model.getV5() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV5(convert.clockStr(taraddod.getSaat(), false));
                                        } else {
                                            model.setV5("");
                                        }
                                    } else if(model.getV6() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV6(convert.clockStr(taraddod.getSaat(), false));
                                        } else {
                                            model.setV6("");
                                        }
                                    } else if(model.getV7() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV7(convert.clockStr(taraddod.getSaat(), false));
                                        } else {
                                            model.setV7("");
                                        }
                                    } else if(model.getV8() == null){
                                        if(taraddod.getSaat() != null){
                                            model.setV8(convert.clockStr(taraddod.getSaat(), false));
                                        } else {
                                            model.setV8("");
                                        }
                                    } else if(model.getMojavez() == null){
                                        if(taraddod.getVaziat() != null){
                                            model.setMojavez(taraddod.getVaziat().getTitle());
                                        }
                                    }

                                } else {
                                    result.add(model);
                                }
                            }
                        }

                        if (model.getPersonel() != null) {
                            result.add(model);
                        }
                    }
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"","تاریخ یا ماه انتخاب نشده است!"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"","پرسنل انتخاب نشده است!"));
        }
    }

    public void pdfPrint(){
        showResults();
        PdfReport pdfReport = new PdfReport();
        List<JasperPrint> jasperPrints = new ArrayList<>();

        int count = 0;
        List<KarkardRozaneModel> karkardPersonel = new ArrayList<>();
        List<String> list = new ArrayList<>();
        boolean reload = true;
        while (reload){
            for (KarkardRozaneModel model  : result) {
                if(list.contains(model.getPersonel().getShomarePersoneli())){
                    count++;
                    if (count == result.size()){
                        reload = false;
                    }
                    continue;
                } else {
                    if(karkardPersonel.size() == 0 || model.getPersonel().getShomarePersoneli().equals(karkardPersonel.get(0).getPersonel().getShomarePersoneli())){
                        karkardPersonel.add(model);
                    }
                    count++;
                    if (count == result.size() && karkardPersonel.size() > 0){
                        jasperPrints.add(createPdfPage(karkardPersonel, kholaseVaziyat));
                        list.add(karkardPersonel.get(0).getPersonel().getShomarePersoneli());
                        karkardPersonel.clear();
                        count = 0;
                    }
                }
            }
        }

        pdfReport.multiPage(jasperPrints);
    }

    public JasperPrint createPdfPage(List<KarkardRozaneModel> list, Boolean vaziyat) {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file;
        if(vaziyat) {
            file = new File(path + "\\amar\\jasperReport\\karkardRozaneBaKholase.jrxml");
        } else {
            file = new File(path + "\\amar\\jasperReport\\karkardRozane.jrxml");
        }

        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap<>();
            map.put("tarikh", simpleDateFormat.format(today.getTime()));
            map.put("from", fromDate);
            map.put("to", toDate);
            map.put("footer", "تا تاریخ " + simpleDateFormat.format(today.getTime()).substring(8,10) + "/" + simpleDateFormat.format(today.getTime()).substring(5,7) + "/" + simpleDateFormat.format(today.getTime()).substring(0,4) + " در پشت همین صفحه گزارش نماید، در غیر اینصورت غیرموجه و برابر مقررات با نامبرده برخورد میگردد.");
            StringBuilder stringBuilder = new StringBuilder("درجه: ");
            if(list.get(0).getPersonel().getDaraje() != null){
                stringBuilder.append(list.get(0).getPersonel().getDaraje().getTitle().trim());
            } else {
                stringBuilder.append("      ");
            }
            stringBuilder.append("   شماره پرسنلی: ");
            if(list.get(0).getPersonel().getShomarePersoneli() != null){
                stringBuilder.append(list.get(0).getPersonel().getShomarePersoneli().trim());
            } else {
                stringBuilder.append("      ");
            }
            stringBuilder.append("   نام: ");
            if(list.get(0).getPersonel().getName() != null){
                stringBuilder.append(list.get(0).getPersonel().getName().trim());
            } else {
                stringBuilder.append("      ");
            }
            stringBuilder.append("   نشان: ");
            if(list.get(0).getPersonel().getNeshan() != null){
                stringBuilder.append(list.get(0).getPersonel().getNeshan().trim());
            } else {
                stringBuilder.append("      ");
            }
            stringBuilder.append("   سمت: ");
            if(list.get(0).getPersonel().getSemat() != null){
                stringBuilder.append(list.get(0).getPersonel().getSemat().getTitle().trim());
            } else {
                stringBuilder.append("      ");
            }
            stringBuilder.append("   بخش: ");
            if(list.get(0).getPersonel().getBakhsh() != null){
                stringBuilder.append(list.get(0).getPersonel().getBakhsh().getTitle().trim());
            } else {
                stringBuilder.append("      ");
            }
            stringBuilder.append("   نوع استخدام: ");
            if(list.get(0).getPersonel().getNoeEstekhdam() != null){
                stringBuilder.append(list.get(0).getPersonel().getNoeEstekhdam().getTitle().trim());
            } else {
                stringBuilder.append("      ");
            }
            map.put("moshakhasat", stringBuilder.toString());

            int sumSaatTakhir = 0;
            int sumDaghigheTakhir = 0;
            int sumSaatTajil = 0;
            int sumDaghigheTajil = 0;
            int sumRozane = 0;
            int sumSalane = 0;
            int sumNaghes = 0;
            int sumGheybat = 0;


            if(vaziyat) {
                for (KarkardRozaneModel model : list) {
                    if(model.getTakhir() != null && !model.getTarikh().equals("")){
                        sumSaatTakhir += Integer.valueOf(model.getTakhir().split(":")[0]);
                        sumDaghigheTakhir += Integer.valueOf(model.getTakhir().split(":")[1]);
                    }

                    if(model.getTajil() != null && !model.getTajil().equals("")){
                        sumSaatTajil += Integer.valueOf(model.getTajil().split(":")[0]);
                        sumDaghigheTajil += Integer.valueOf(model.getTajil().split(":")[1]);
                    }

                    if((model.getTakhir() != null && !model.getTarikh().equals("")) || (model.getTajil() != null && !model.getTajil().equals(""))){
                        sumNaghes++;
                    }

                    if(model.getMojavez().equals("مرخصی روزانه")){
                        sumRozane++;
                    }

                    if(model.getMojavez().equals("مرخصی سالیانه")){
                        sumSalane++;
                    }

                    if(model.getMojavez().equals("نهست") || model.getMojavez().equals("فرار")){
                        sumGheybat++;
                    }
                }

                map.put("sumTakhir", sumSaatTakhir + ":" + sumDaghigheTakhir);
                map.put("sumTajil", sumSaatTajil + ":" + sumDaghigheTajil);
                map.put("sumRozane", String.valueOf(sumRozane));
                map.put("sumSalane", String.valueOf(sumSalane));
                map.put("sumNughes", String.valueOf(sumNaghes));
                map.put("sumGheybat", String.valueOf(sumGheybat));
            }

            return JasperFillManager.fillReport(jasperReport, map,  new JRBeanCollectionDataSource(list));

        } catch (Exception e) {
            alert.error();
        }
        return null;
    }

    public void selectDonePersonel() {
        selectedPersonelCount = selectedPersonels.size();
    }

    public void searchPersonel(){
        selectedPersonels.clear();
        StringBuilder query = new StringBuilder();

        if(shomareKartSearchedAz != null && !shomareKartSearchedAz.equals("")){
            query.append(" AND shomareKart <= '").append(shomareKartSearchedAz).append("'");
        }
        if(shomareKartSearchedTa != null && !shomareKartSearchedTa.equals("")){
            query.append(" AND shomareKart >= '").append(shomareKartSearchedTa).append("'");
        }
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

        if(selectedBakhsh != 0){
            query.append(" AND bakhsh.id = ").append(selectedBakhsh);
        }

        if(selectedNoeEstekhdam != 0){
            query.append(" AND noeEstekhdam.id = ").append(selectedNoeEstekhdam);
        }

        if(selectedDaraje != 0){
            query.append(" AND daraje.id = ").append(selectedDaraje);
        }

        if(selectedSorted != 0){
            if(selectedSorted == 1){
                query.append(" ORDER BY shomareKart");
            } else if(selectedSorted == 2){
                query.append(" ORDER BY name");
            } else if(selectedSorted == 3){
                query.append(" ORDER BY neshan");
            } else if(selectedSorted == 4){
                query.append(" ORDER BY shomarePersoneli");
            } else if(selectedSorted == 5){
                query.append(" ORDER BY codeMeli");
            }
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

    public String getSelectedDateAz() {
        return selectedDateAz;
    }

    public void setSelectedDateAz(String selectedDateAZ) {
        this.selectedDateAz = selectedDateAZ;
    }

    public String getSelectedDateTa() {
        return selectedDateTa;
    }

    public void setSelectedDateTa(String selectedDateTa) {
        this.selectedDateTa = selectedDateTa;
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

    public List<Bakhsh> getBakhshList() {
        return bakhshList;
    }

    public void setBakhshList(List<Bakhsh> bakhshList) {
        this.bakhshList = bakhshList;
    }

    public long getSelectedBakhsh() {
        return selectedBakhsh;
    }

    public void setSelectedBakhsh(long selectedBakhsh) {
        this.selectedBakhsh = selectedBakhsh;
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

    public Boolean getKholaseVaziyat() {
        return kholaseVaziyat;
    }

    public void setKholaseVaziyat(Boolean kholaseVaziyat) {
        this.kholaseVaziyat = kholaseVaziyat;
    }

    public String getShomareMeliSearched() {
        return shomareMeliSearched;
    }

    public void setShomareMeliSearched(String shomareMeliSearched) {
        this.shomareMeliSearched = shomareMeliSearched;
    }

    public String getShomareKartSearchedAz() {
        return shomareKartSearchedAz;
    }

    public void setShomareKartSearchedAz(String shomareKartSearchedAz) {
        this.shomareKartSearchedAz = shomareKartSearchedAz;
    }

    public String getShomareKartSearchedTa() {
        return shomareKartSearchedTa;
    }

    public void setShomareKartSearchedTa(String shomareKartSearchedTa) {
        this.shomareKartSearchedTa = shomareKartSearchedTa;
    }

    public String getNameSearched() {
        return nameSearched;
    }

    public void setNameSearched(String nameSearched) {
        this.nameSearched = nameSearched;
    }

    public List<NoeEstekhdam> getNoeEstekhdams() {
        return noeEstekhdams;
    }

    public void setNoeEstekhdams(List<NoeEstekhdam> noeEstekhdams) {
        this.noeEstekhdams = noeEstekhdams;
    }

    public List<Daraje> getDarajes() {
        return darajes;
    }

    public void setDarajes(List<Daraje> darajes) {
        this.darajes = darajes;
    }

    public long getSelectedNoeEstekhdam() {
        return selectedNoeEstekhdam;
    }

    public void setSelectedNoeEstekhdam(long selectedNoeEstekhdam) {
        this.selectedNoeEstekhdam = selectedNoeEstekhdam;
    }

    public long getSelectedDaraje() {
        return selectedDaraje;
    }

    public void setSelectedDaraje(long selectedDaraje) {
        this.selectedDaraje = selectedDaraje;
    }

    public long getSelectedSorted() {
        return selectedSorted;
    }

    public void setSelectedSorted(long selectedSorted) {
        this.selectedSorted = selectedSorted;
    }

    public List<KarkardRozaneModel> getResult() {
        return result;
    }

    public void setResult(List<KarkardRozaneModel> result) {
        this.result = result;
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