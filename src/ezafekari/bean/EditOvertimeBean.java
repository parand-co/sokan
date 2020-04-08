package ezafekari.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.dao.EzafekariDao;
import ezafekari.EzafeUtil;
import ezafekari.model.EzafeKari;
import org.primefaces.event.RowEditEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nedaja on 25/01/2020.
 */
@ManagedBean
@ViewScoped
public class EditOvertimeBean {

    private String dateEzf = "";
    private List<Personel> selectedPersonels = new ArrayList<>();
    private List<EzafeKari> ezafeKariList = new ArrayList<>();
    private String selectedPersonelsCount = "0";
    private String personelCode = "";
    private SessionGate sessionGate = new SessionGate();
    private Personel selectedPersonel;
    private int taradodNaghesSize = 0;
    private EzafeKari selectEzfEdit = new EzafeKari();
    private Integer startHour, endHour;


    public EditOvertimeBean() {

    }

    public void inlineEdit(EzafeKari ezafeKari) {
        if (ezafeKari.getSaatStart() < 100 || ezafeKari.getSaatEnd() < 100) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "ساعت وارد شده صحیح نمی باشد"));
        } else {
            if (ezafeKari.getSaatStart() > ezafeKari.getSaatEnd())
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "هشدار", "ساعت شروع بزرگتر از ساعت پایان است!"));
            else {
                ezafeKari.setSaatStart(EzafeUtil.roundSaat(ezafeKari.getSaatStart()));
                ezafeKari.setSaatEnd(EzafeUtil.roundSaat(ezafeKari.getSaatEnd()));
                ezafeKari.setModat(EzafeUtil.calcModat(ezafeKari));
                EzafekariDao.getInstance().createOrUpdate(ezafeKari);
            }
        }
    }

    public void edit() {
//        System.out.println(selectEzfEdit);
        if (selectEzfEdit.getSaatStart() < selectEzfEdit.getSaatEnd())
            EzafekariDao.getInstance().createOrUpdate(selectEzfEdit);
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "ساعت شروع بزرگتر از ساعت پایان است!"));
        }
    }

    public void changeHour2Originial() {
        if (selectEzfEdit.getSaatStart() < 100 || selectEzfEdit.getSaatEnd() < 100) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "ساعت وارد شده صحیح نمی باشد"));
        } else {
            selectEzfEdit.setSaatStart(EzafeUtil.roundSaat(selectEzfEdit.getSaatStart()));
            selectEzfEdit.setSaatEnd(EzafeUtil.roundSaat(selectEzfEdit.getSaatEnd()));
            selectEzfEdit.setModat(EzafeUtil.calcModat(selectEzfEdit));
        }
    }



    public void loadOvertimes() {
        taradodNaghesSize = 0;
        ezafeKariList.clear();
        if (dateEzf.matches("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "تاریخ وارد نشده است!"));
        } else if (Integer.parseInt(selectedPersonelsCount) < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "هیچ پرسنلی انتخاب نشده است!"));
        } else {
            Taghvim taghvim = sessionGate.findTaghvimByTarikh(dateEzf);
            if (selectedPersonel != null)
                selectedPersonels.add(selectedPersonel);
            for (Personel personel : selectedPersonels) {
                EzafeKari ezafeKari = EzafeUtil.checkExistOvertime(personel, dateEzf);
                if (ezafeKari != null) {
                    ezafeKariList.add(ezafeKari);
                } else {
                    EzafeKari ezafeKari1 = EzafeUtil.createEzafekariByTaraddod(personel, dateEzf, taghvim);
                    if (ezafeKari1 != null) {
//                        ezafeKari1.setPersonel(personel);
//                        ezafeKari1.setTarikh(taghvim);
//                        ezafeKari1.setSanad(sessionGate.findSanadByTarikh(dateEzf));
                        EzafekariDao.getInstance().create(ezafeKari1);
                        ezafeKariList.add(ezafeKari1);
                    }
                }
            }
            if (ezafeKariList.size() < 1)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "اضافه کاری در این تاریخ ثبت نشده است!"));
        }
    }


    public void editOvertime(EzafeKari ezafeKari) {
        selectEzfEdit = ezafeKari;
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


    public void  selectDonePersonel() {
        selectedPersonelsCount = String.valueOf(selectedPersonels.size());
    }

    public String getDateEzf() {
        return dateEzf;
    }

    public void setDateEzf(String dateEzf) {
        this.dateEzf = dateEzf;
    }

    public List<Personel> getSelectedPersonels() {
        return selectedPersonels;
    }

    public void setSelectedPersonels(List<Personel> selectedPersonels) {
        this.selectedPersonels = selectedPersonels;
    }

    public List<EzafeKari> getEzafeKariList() {
        return ezafeKariList;
    }

    public void setEzafeKariList(List<EzafeKari> ezafeKariList) {
        this.ezafeKariList = ezafeKariList;
    }

    public String getSelectedPersonelsCount() {
        return selectedPersonelsCount;
    }

    public void setSelectedPersonelsCount(String selectedPersonelsCount) {
        this.selectedPersonelsCount = selectedPersonelsCount;
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

    public int getTaradodNaghesSize() {
        return taradodNaghesSize;
    }

    public void setTaradodNaghesSize(int taradodNaghesSize) {
        this.taradodNaghesSize = taradodNaghesSize;
    }

    public EzafeKari getSelectEzfEdit() {
        return selectEzfEdit;
    }

    public void setSelectEzfEdit(EzafeKari selectEzfEdit) {
        this.selectEzfEdit = selectEzfEdit;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }
}
