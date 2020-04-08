package ezafekari.bean;

import amar.model.Personel;
import dataBaseModel.SessionGate;
import dataBaseModel.dao.EzafatoKosooratDao;
import ezafekari.EzafeUtil;
import ezafekari.model.EzafatoKosoorat;
import ezafekari.model.EzafeKari;
import ezafekari.model.Sanad;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nedaja on 08/02/2020.
 */
@ManagedBean
@ViewScoped
public class EzafatKosooratBean {

    private long sanadId;
    private List<Sanad> sanadList = new ArrayList<>();
    private SessionGate sessionGate = new SessionGate();
    private String personelCode;
    private Personel selectedPersonel = new Personel();
    private String selectedPersonelsCount = "0";
    private List<EzafatoKosoorat> ekList = new ArrayList<>();
    private int addHours = 0, minusHours = 0;
    private List<Personel> selectedPersonels = new ArrayList<>();
    private String sharhAll = "";

    public EzafatKosooratBean() {
        sanadList = sessionGate.fillSanads();
    }

    public void loadEzafatoKosoorat() {
        ekList.clear();
        if (selectedPersonel.getId() != 0)
            selectedPersonels.add(selectedPersonel);
        for (Personel personel : selectedPersonels) {
            EzafatoKosoorat ezafatoKosoorat = new EzafatoKosoorat();
            ezafatoKosoorat = sessionGate.checkEzafatKosoratBySanadAndPersonel(sanadId, personel);
            if (ezafatoKosoorat.getId() != 0) {
                ekList.add(ezafatoKosoorat);
            } else {
                ezafatoKosoorat.setPersonel(personel);
                ezafatoKosoorat.setSaatEK(0);
                ezafatoKosoorat.setEzafOrKasr(true);
                ezafatoKosoorat.setSanad(new Sanad(sanadId));
                ekList.add(ezafatoKosoorat);
            }
        }
    }

    public void applyHoursToTable() {
        for (EzafatoKosoorat ezafatoKosoorat : ekList) {
            ezafatoKosoorat.setSaatEK((ezafatoKosoorat.getSaatEK() + addHours) - minusHours);
            ezafatoKosoorat.setSharh(sharhAll);
            if (ezafatoKosoorat.getSaatEK()>0)
                ezafatoKosoorat.setEzafOrKasr(true);
            else
                ezafatoKosoorat.setEzafOrKasr(false);
        }
    }

//    public void changeHour2Originial2(EzafeKari ezafeKari) {
//        if (ezafeKari.getSaatStart() < 100 || ezafeKari.getSaatEnd() < 100) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "ساعت وارد شده صحیح نمی باشد"));
//        } else {
//            ezafeKari.setSaatStart(EzafeUtil.roundSaat(ezafeKari.getSaatStart()));
//            ezafeKari.setSaatEnd(EzafeUtil.roundSaat(ezafeKari.getSaatEnd()));
//        }
//    }

    public void applyChanges() {
        try {
            for (EzafatoKosoorat ezafatoKosoorat : ekList) {
                if (ezafatoKosoorat.getSaatEK() != 0) {
                    if (ezafatoKosoorat.getId() != 0)
                        EzafatoKosooratDao.getInstance().createOrUpdate(ezafatoKosoorat);
                    else
                        EzafatoKosooratDao.getInstance().create(ezafatoKosoorat);
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "با موفقیت انجام شد."));
            ekList.clear();
        }catch (Exception e){
            e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "خطا", "بروز مشکل در عملیات!"));
        }
    }

    public void selectDonePersonel() {
        selectedPersonelsCount = String.valueOf(selectedPersonels.size());
    }

    public void loadPersonelsByPersNumExactly() {
        if (personelCode.length() > 7) {
            if (sessionGate.findPersonelByPersNum(personelCode).size() > 0) {
                selectedPersonel = sessionGate.findPersonelByPersNum(personelCode).get(0);
                selectedPersonelsCount = "1";
            } else
                selectedPersonelsCount = "0";
        } else
            selectedPersonel = null;
    }

    public List<Sanad> getSanadList() {
        return sanadList;
    }

    public void setSanadList(List<Sanad> sanadList) {
        this.sanadList = sanadList;
    }

    public long getSanadId() {
        return sanadId;
    }

    public void setSanadId(long sanadId) {
        this.sanadId = sanadId;
    }

    public String getPersonelCode() {
        return personelCode;
    }

    public void setPersonelCode(String personelCode) {
        this.personelCode = personelCode;
    }

    public Personel getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(Personel selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }

    public String getSelectedPersonelsCount() {
        return selectedPersonelsCount;
    }

    public void setSelectedPersonelsCount(String selectedPersonelsCount) {
        this.selectedPersonelsCount = selectedPersonelsCount;
    }

    public List<EzafatoKosoorat> getEkList() {
        return ekList;
    }

    public void setEkList(List<EzafatoKosoorat> ekList) {
        this.ekList = ekList;
    }

    public int getAddHours() {
        return addHours;
    }

    public void setAddHours(int addHours) {
        this.addHours = addHours;
    }

    public int getMinusHours() {
        return minusHours;
    }

    public void setMinusHours(int minusHours) {
        this.minusHours = minusHours;
    }

    public List<Personel> getSelectedPersonels() {
        return selectedPersonels;
    }

    public void setSelectedPersonels(List<Personel> selectedPersonels) {
        this.selectedPersonels = selectedPersonels;
    }

    public String getSharhAll() {
        return sharhAll;
    }

    public void setSharhAll(String sharhAll) {
        this.sharhAll = sharhAll;
    }
}
