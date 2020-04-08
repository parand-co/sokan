package amar.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import dataBaseModel.SessionGate;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.PersianCalUtil;
import dto.TaradodDto;
import ezafekari.EzafeKarDto;
import ezafekari.EzafeUtil;
import manage.bean.IndexBean;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.hibernate.Session;
import util.FillList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nedaja on 09/01/2020.
 */
@ManagedBean
@ViewScoped
public class TaraddodReportBean {

    private int selectedMount = 0;
    private String selectedDate = "";
    private Personel selectedPersonel = new Personel();
    private List<Personel> searchedPersonels = new ArrayList<>();
    private List<Personel> selectedPersonels = new ArrayList<>();
    private List<Taraddod> taraddodList = new ArrayList<>();
    private List<TaradodDto> taraddodDtoList = new ArrayList<>();
    private int selectedPersonelCount;
    private String neshanSearched, shomarePersoneliSearched,shomarePersoneliSearched2, shomareKartSearched;
    private SessionGate sessionGate = new SessionGate();
    private List<Dayere> dayereList = new ArrayList<>();
    private List<Bakhsh> bakhshList = new ArrayList<>();
    private long selectedDayere = 0, selectedBakhsh = 0;
    private String fromDate="",toDate="";
    private String t1,t2;
    private String sal="1399";

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    public TaraddodReportBean() {
        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        FillList fillList = new FillList();
        dayereList = fillList.dayeres(permissions.get(0));
        bakhshList = fillList.bakhsh(permissions.get(0));
//        Session session = HibernateUtil.getSession();
//        dayereList = session.createQuery("from Dayere").list();
//        bakhshList = session.createQuery("from Bakhsh").list();
//        session.close();
    }

    public void selectDonePersonel() {
        selectedPersonelCount = selectedPersonels.size();
    }

    public void reportPDF(){
        if (taraddodDtoList.size() < 1)
            new Alert().warningAlert("","هیچ ترددی ثبت نشده است!");
        else {
            Map map = new HashMap<>();
            File file = null;
            if (selectedPersonels.size() == 1){
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                file = new File(path + "\\report\\taradodReportPersonel.jrxml");
                map.put("cDate",PersianCalUtil.getCurrentDate());
                map.put("fromDate",fromDate);
                map.put("toDate",toDate);
                map.put("persName",selectedPersonels.get(0).getName().trim() + " " + selectedPersonels.get(0).getNeshan().trim());
                map.put("persCode",selectedPersonels.get(0).getShomarePersoneli());
                map.put("persBakhsh",selectedPersonels.get(0).getBakhsh().getTitle());
                map.put("persEstekhdam",selectedPersonels.get(0).getNoeEstekhdam().getTitle());
            }else {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                file = new File(path + "\\report\\taradodReport.jrxml");
                map.put("cDate",PersianCalUtil.getCurrentDate());
                map.put("fromDate",fromDate);
                map.put("toDate",toDate);
            }
            try {
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanCollectionDataSource(taraddodDtoList));
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                        .getExternalContext().getResponse();
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); // your output goes here
                exporter.exportReport();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeGhesmat() {
        searchedPersonels.clear();
        searchedPersonels = sessionGate.findPersonelByBakhshId(selectedBakhsh);
    }

    public void changeDayere() {
        bakhshList.clear();
        bakhshList = sessionGate.findBakhshByDayereID(selectedDayere);
        searchedPersonels.clear();
        searchedPersonels = sessionGate.findPersonelByDayereID(selectedDayere);
    }

    public void loadPersonelsByCardNum() {
        searchedPersonels.clear();
        if (shomareKartSearched.length() > 2) {
            searchedPersonels = sessionGate.findPersonelContainsCardNumber(shomareKartSearched);
        }
    }

    public void loadPersonelsByPersNum() {
        searchedPersonels.clear();
        if (shomarePersoneliSearched.length() > 7) {
            searchedPersonels = sessionGate.findPersonelContainsPersNum(shomarePersoneliSearched);
        }
    }

    public void loadPersonelsByPersNumExactly() {
        searchedPersonels.clear();
        if (shomarePersoneliSearched2.length() > 7) {
            if(sessionGate.findPersonelByPersNum(shomarePersoneliSearched2).size()>0) {
                selectedPersonel = sessionGate.findPersonelByPersNum(shomarePersoneliSearched2).get(0);
                selectedPersonelCount = 1;
            }else
                selectedPersonelCount = 0;
        }else
            selectedPersonel = null;
    }

    public void loadPersonelsByNeshan() {
        searchedPersonels.clear();
        if (neshanSearched.length() > 2) {
            searchedPersonels = sessionGate.findPersonelContainsNameAndNeshan(neshanSearched);
        }
    }

    public void showResults() {
        taraddodDtoList.clear();
        if (selectedPersonelCount > 0){
            if (selectedMount != 0 || !selectedDate.matches("")){
                bindFormDateAndToDate();
//                if (selectedPersonel.getId() !=  0){
//                    selectedPersonels.add(selectedPersonel);
//                }
                for (Personel personel : selectedPersonels) {
                    List<Taraddod> taraddodList = sessionGate.findTaraddodByPersonelAndFromDateAndToDate(personel,fromDate,toDate);
                    for (Taraddod taraddod : taraddodList) {
                        TaradodDto taradodDto = checkTaradodDtoList(taraddod);
                        if (taradodDto != null){
                            setSaatOnTaraddodDto(taraddod,taradodDto);
                        }else {
                            TaradodDto newTaradodDto = new TaradodDto();
                            newTaradodDto.setPersName(personel.getName().trim()+" "+personel.getNeshan().trim());
                            newTaradodDto.setPersCode(personel.getShomarePersoneli());
                            newTaradodDto.setPersonel(personel);
                            newTaradodDto.setSaat1(taraddod.getSaat());
                            newTaradodDto.setDeviceNum1(taraddod.getShomareDastgah());
                            newTaradodDto.setDate(taraddod.getTaghvim().getTarikh());
                            newTaradodDto.setRooz(taraddod.getTaghvim().getRoozHafte());
                            if (taraddod.getVaziat() != null)
                                newTaradodDto.setVaziat(taraddod.getVaziat().getTitle());
                            else
                                newTaradodDto.setVaziat("نامشخص");
                            taraddodDtoList.add(newTaradodDto);
                        }
                    }
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"","تاریخ یا ماه انتخاب نشده است!"));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"","پرسنل انتخاب نشده است!"));
        }
    }

    private void setSaatOnTaraddodDto(Taraddod taraddod, TaradodDto taradodDto) {
        if (taradodDto.getSaat2() == null){
            taradodDto.setSaat2(taraddod.getSaat());
            taradodDto.setDeviceNum2(taraddod.getShomareDastgah());
        }else if (taradodDto.getSaat3() == null){
            taradodDto.setSaat3(taraddod.getSaat());
            taradodDto.setDeviceNum3(taraddod.getShomareDastgah());
        }else if (taradodDto.getSaat4() == null){
            taradodDto.setSaat4(taraddod.getSaat());
            taradodDto.setDeviceNum4(taraddod.getShomareDastgah());
        }else if (taradodDto.getSaat5() == null){
            taradodDto.setSaat5(taraddod.getSaat());
            taradodDto.setDeviceNum5(taraddod.getShomareDastgah());
        }else if (taradodDto.getSaat6() == null){
            taradodDto.setSaat6(taraddod.getSaat());
            taradodDto.setDeviceNum6(taraddod.getShomareDastgah());
        }else if (taradodDto.getSaat7() == null){
            taradodDto.setSaat7(taraddod.getSaat());
            taradodDto.setDeviceNum7(taraddod.getShomareDastgah());
        }else{
            taradodDto.setSaat8(taraddod.getSaat());
            taradodDto.setDeviceNum8(taraddod.getShomareDastgah());
        }
    }

    private TaradodDto checkTaradodDtoList(Taraddod taraddod) {
        for (TaradodDto taradodDto : taraddodDtoList) {
            if (taradodDto.getDate().matches(taraddod.getTaghvim().getTarikh()) && taradodDto.getPersonel().getId()==taraddod.getPersonel().getId()){
                return taradodDto;
            }
        }
        return null;
    }

    private void bindFormDateAndToDate() {

        if (selectedMount != 0){
            switch (selectedMount){
                case 1:{
                    fromDate = sal + "/01/01";
                    toDate = sal + "/01/31";
                    break;
                }
                case 2:{
                    fromDate = sal + "/02/01";
                    toDate = sal + "/02/31";
                    break;
                }
                case 3:{
                    fromDate = sal + "/03/01";
                    toDate = sal + "/03/31";
                    break;
                }
                case 4:{
                    fromDate = sal + "/04/01";
                    toDate = sal + "/04/31";
                    break;
                }
                case 5:{
                    fromDate = sal + "/05/01";
                    toDate = sal + "/05/31";
                    break;
                }
                case 6:{
                    fromDate = sal + "/06/01";
                    toDate = sal + "/06/31";
                    break;
                }
                case 7:{
                    fromDate = sal + "/07/01";
                    toDate = sal + "/07/30";
                    break;
                }
                case 8:{
                    fromDate = sal + "/08/01";
                    toDate = sal + "/08/30";
                    break;
                }
                case 9:{
                    fromDate = sal + "/09/01";
                    toDate = sal + "/09/30";
                    break;
                }
                case 10:{
                    fromDate = sal + "/10/01";
                    toDate = sal + "/10/30";
                    break;
                }
                case 11:{
                    fromDate = sal + "/11/01";
                    toDate = sal + "/11/30";
                    break;
                }
                case 12:{
                    fromDate = sal + "/12/01";
                    toDate = sal + "/12/29";
                    break;
                }
            }
        }else {
            fromDate = selectedDate;
            toDate = selectedDate;
        }
    }

    public void submitPersonels() {

    }


    public int getSelectedMount() {
        return selectedMount;
    }

    public void setSelectedMount(int selectedMount) {
        this.selectedMount = selectedMount;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Personel getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(Personel selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }


    public List<Taraddod> getTaraddodList() {
        return taraddodList;
    }

    public void setTaraddodList(List<Taraddod> taraddodList) {
        this.taraddodList = taraddodList;
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

    public String getShomarePersoneliSearched() {
        return shomarePersoneliSearched;
    }

    public void setShomarePersoneliSearched(String shomarePersoneliSearched) {
        this.shomarePersoneliSearched = shomarePersoneliSearched;
    }

    public String getShomareKartSearched() {
        return shomareKartSearched;
    }

    public void setShomareKartSearched(String shomareKartSearched) {
        this.shomareKartSearched = shomareKartSearched;
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

    public List<TaradodDto> getTaraddodDtoList() {
        return taraddodDtoList;
    }

    public void setTaraddodDtoList(List<TaradodDto> taraddodDtoList) {
        this.taraddodDtoList = taraddodDtoList;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
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

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }
}