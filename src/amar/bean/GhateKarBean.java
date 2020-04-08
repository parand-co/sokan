package amar.bean;

import amar.model.Personel;
import dataBaseModel.SessionGate;
import dataBaseModel.dao.PersonelDao;
import dataBaseModel.util.PersianCalUtil;
import org.hibernate.Session;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nedaja on 04/01/2020.
 */
@ManagedBean
@ViewScoped
public class GhateKarBean {

    private List<Personel> qateKarPersonels = new ArrayList<>();
    private SessionGate sessionGate = new SessionGate();
    private String expireDate;
    private Personel selectedPersonel = new Personel();

    public GhateKarBean() {

        qateKarPersonels = sessionGate.findPersonelByActive(false);
    }

    public void updatePersonels(){
        List<Personel> personelsExpired = sessionGate.checkTarikhEnqezaForInactvie();
        for (Personel personel : personelsExpired) {
            personel.setTarikhEngheza("-");
            personel.setActive(false);
            PersonelDao.getInstance().getBaseQuery().createOrUpdate(personel,"amar/personel_ghate_kar.xhtml");
        }
        qateKarPersonels.clear();
        qateKarPersonels = sessionGate.findPersonelByActive(false);
    }

    public void activePersonel(Personel personel,int daemiOrNot){
        if (daemiOrNot == 1){
            personel.setActive(true);
            personel.setTarikhEngheza("-");
            PersonelDao.getInstance().getBaseQuery().createOrUpdate(personel,"amar/personel_ghate_kar.xhtml");
        }
        else {
            if (Long.parseLong(expireDate.replaceAll("/","")) > Long.parseLong(PersianCalUtil.getCurrentDate().replaceAll("/",""))){
                selectedPersonel.setActive(true);
                selectedPersonel.setTarikhEngheza(expireDate);
                PersonelDao.getInstance().getBaseQuery().createOrUpdate(selectedPersonel, "amar/personel_ghate_kar.xhtml");
            }else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"","تاریخ انقضای نامعتبر!"));
        }
    }

    public void copyPersonel(Personel personel){
        this.selectedPersonel = personel;
    }

    public List<Personel> getQateKarPersonels() {
        return qateKarPersonels;
    }

    public void setQateKarPersonels(List<Personel> qateKarPersonels) {
        this.qateKarPersonels = qateKarPersonels;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Personel getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(Personel selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }
}
