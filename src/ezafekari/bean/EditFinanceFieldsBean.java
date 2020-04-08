package ezafekari.bean;

import amar.model.Personel;
import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.Daraje;
import dataBaseModel.baseTable.Madrak;
import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.dao.PersonelDao;
import org.primefaces.context.RequestContext;
import util.PropertiesLoader;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by nedaja on 09/02/2020.
 */
@ManagedBean
@ViewScoped
public class EditFinanceFieldsBean {

    private List<Personel> personels = new ArrayList<>();
    private List<Personel> filteredPersonels = new ArrayList<>();
    private SessionGate sessionGate = new SessionGate();
    private Personel selectedPersonel = new Personel();
    private List<Daraje> darajeList= new ArrayList<>();
    private List<Madrak> madrakList= new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdams= new ArrayList<>();
    private int rows = 10;
    private long selectedPersonelMakhaz;
    private long darajeShoghlID,madrakID;
    private String[] selectedNoeEstekhdams;
    private int formool;

    public EditFinanceFieldsBean() {
        personels = sessionGate.findPersonelByActive(true);
        darajeList = sessionGate.fillDaraje();
        madrakList = sessionGate.fillMadrak();
        noeEstekhdams = sessionGate.fillNoeEstekhdam();
        formool = Integer.parseInt(PropertiesLoader.read("overtime_formool"));
    }

    private void loadEzafeFormool() {
        System.out.println(System.getProperty("usr/dir"));
    }

    public String calcMakhaz(){
        selectedPersonelMakhaz = (selectedPersonel.getHaghShoghl()+
                selectedPersonel.getHagheShaghel()+
                selectedPersonel.getFogholadeModiriyat())/formool;
        return String.valueOf(selectedPersonelMakhaz);
    }

    public void saveFormool(){
        PropertiesLoader.write("overtime_formool",String.valueOf(formool));
    }

    public void filterByNoeEstekhdam(){

        personels = sessionGate.findPersonelByNoeEstekhdam(selectedNoeEstekhdams);
    }

    public void confirmChanges(){
        if (selectedPersonel.getDarajeShoghl() == null && darajeShoghlID != 0)
            selectedPersonel.setDarajeShoghl(findDarajeById(darajeShoghlID));
        if (madrakID != 0)
            selectedPersonel.setMadrak(new Madrak(madrakID));
        PersonelDao.getInstance().getBaseQuery().createOrUpdate(selectedPersonel);
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"","تغییرات ثبت شد."));
    }

    private Daraje findDarajeById(long darajeShoghlID) {
        for (Daraje daraje : darajeList) {
            if (daraje.getId() == darajeShoghlID)
                return daraje;
        }
        return null;
    }

    public String checkMakhaz(){
        if (selectedPersonel.getNoeEstekhdam() == null)
            return calcMakhaz();
        else {
            switch ((int) selectedPersonel.getNoeEstekhdam().getId()) {
                case 1: {
                    return calcMakhaz();
                }
                case 2: {
                    return calcMakhaz();
                }
                case 3: {
                    return String.valueOf(selectedPersonel.getMadrak().getMakhaz());
                }
                case 4: {
                    return String.valueOf(selectedPersonel.getMadrak().getMakhaz());
                }
                case 5: {
                    return "وظیفه";
                }
                case 6: {
                    return calcMakhaz();
                }
                case 7: {
                    return String.valueOf(selectedPersonel.getMadrak().getMakhaz());
                }
                default:{
                    return calcMakhaz();
                }
            }
        }
    }


    public String calcSaghf(){
       return "";
    }
    public void changeJayga(){

    }

    public void changeMadrak(){
        if (selectedPersonel.getMadrak() == null){
            selectedPersonel.setMadrak(findMadrakById(madrakID));
        }else{
            selectedPersonel.setMadrak(sessionGate.findMadrakById(selectedPersonel.getMadrak().getId()));
        }
    }

    private Madrak findMadrakById(long madrakID) {
        for (Madrak madrak : madrakList) {
            if (madrak.getId() == madrakID)
                return madrak;
        }
        return null;
    }

    public void showEditModal(Personel personel){
        selectedPersonel = new Personel();
        setSelectedPersonel(personel);
        RequestContext.getCurrentInstance().execute("$('#df').modal('show')");
    }

    public List<Personel> getPersonels() {
        return personels;
    }

    public void setPersonels(List<Personel> personels) {
        this.personels = personels;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Personel> getFilteredPersonels() {
        return filteredPersonels;
    }

    public void setFilteredPersonels(List<Personel> filteredPersonels) {
        this.filteredPersonels = filteredPersonels;
    }


    public Personel getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(Personel selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }

    public long getSelectedPersonelMakhaz() {
        return selectedPersonelMakhaz;
    }

    public void setSelectedPersonelMakhaz(long selectedPersonelMakhaz) {
        this.selectedPersonelMakhaz = selectedPersonelMakhaz;
    }

    public List<Daraje> getDarajeList() {
        return darajeList;
    }

    public void setDarajeList(List<Daraje> darajeList) {
        this.darajeList = darajeList;
    }

    public List<Madrak> getMadrakList() {
        return madrakList;
    }

    public void setMadrakList(List<Madrak> madrakList) {
        this.madrakList = madrakList;
    }

    public long getDarajeShoghlID() {
        return darajeShoghlID;
    }

    public void setDarajeShoghlID(long darajeShoghlID) {
        this.darajeShoghlID = darajeShoghlID;
    }

    public long getMadrakID() {
        return madrakID;
    }

    public void setMadrakID(long madrakID) {
        this.madrakID = madrakID;
    }

    public List<NoeEstekhdam> getNoeEstekhdams() {
        return noeEstekhdams;
    }

    public void setNoeEstekhdams(List<NoeEstekhdam> noeEstekhdams) {
        this.noeEstekhdams = noeEstekhdams;
    }


    public String[] getSelectedNoeEstekhdams() {
        return selectedNoeEstekhdams;
    }

    public void setSelectedNoeEstekhdams(String[] selectedNoeEstekhdams) {
        this.selectedNoeEstekhdams = selectedNoeEstekhdams;
    }

    public int getFormool() {
        return formool;
    }

    public void setFormool(int formool) {
        this.formool = formool;
    }
}
