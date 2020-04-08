package ezafekari.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Daraje;
import dataBaseModel.baseTable.MojavezRozane;
import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.baseTable.Semat;
import dataBaseModel.dao.SavabeghEsterahatPezeshkiDao;
import dataBaseModel.dao.TaraddodDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import ezafekari.model.Emza;
import ezafekari.model.EmzaDao;
import manage.bean.IndexBean;
import omorkoliAndgharardadi.model.EsterahatPezeshkiReportModel;
import omorkoliAndgharardadi.model.SavabeghEsterahatPezeshki;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Excel;
import util.PdfReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class EmzaKonandeganBean implements Serializable {
    private String URL;
    private List<Emza> savabegh = new ArrayList<>();
    private List<Semat> semats = new ArrayList<>();
    private List<Daraje> darajes = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String name;
    private String neshan;
    private String darajeCode;
    private String sematCode;

    private Emza selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public EmzaKonandeganBean() {
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
        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery("FROM Emza").list();
        semats = session.createQuery("FROM Semat").list();
        darajes = session.createQuery("FROM Daraje").list();
        session.close();
    }

    public void save(){
        if(name != null && neshan != null && darajeCode != null && sematCode != null && !name.equals("") && !Objects.equals(neshan, "") && !Objects.equals(darajeCode, "") && !Objects.equals(sematCode, "")){
            if(savabegh.stream().filter(o-> o.getSemat().getCode().equals(sematCode)).findFirst().orElse(null) != null){
                Emza emza = savabegh.stream().filter(o-> o.getSemat().getCode().equals(sematCode)).findFirst().orElse(null);
                savabegh.remove(emza);
                assert emza != null;
                emza.setName(name);
                emza.setNeshan(neshan);
                emza.setDaraje(darajes.stream().filter(o-> o.getCode().equals(darajeCode)).findFirst().orElse(null));
                emza.setSemat(semats.stream().filter(o-> o.getCode().equals(sematCode)).findFirst().orElse(null));

                EmzaDao.getInstance().getBaseQuery().createOrUpdate(emza, URL);
                alert.successEdit();
                savabegh.add(emza);
            } else {
                Emza emza = new Emza();
                emza.setName(name);
                emza.setNeshan(neshan);
                emza.setDaraje(darajes.stream().filter(o-> o.getCode().equals(darajeCode)).findFirst().orElse(null));
                emza.setSemat(semats.stream().filter(o-> o.getCode().equals(sematCode)).findFirst().orElse(null));

                EmzaDao.getInstance().getBaseQuery().create(emza, URL);
                alert.successSave();
                savabegh.add(emza);
            }
            nuller();
        } else {
            alert.fieldIsEmpty("تمامی موارد");
        }
    }

    public void nuller() {
        name = "";
        neshan = "";
        darajeCode = "";
        sematCode = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(Emza model){
        edited = true;
        name = model.getName();
        neshan = model.getNeshan();
        sematCode = model.getSemat().getCode();
        darajeCode = model.getDaraje().getCode();

        selectMode = model;
    }

    public void startDelete(Emza model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        EmzaDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }



    // GETTER AND SETTER

    public List<Emza> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Emza> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Semat> getSemats() {
        return semats;
    }

    public void setSemats(List<Semat> semats) {
        this.semats = semats;
    }

    public List<Daraje> getDarajes() {
        return darajes;
    }

    public void setDarajes(List<Daraje> darajes) {
        this.darajes = darajes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeshan() {
        return neshan;
    }

    public void setNeshan(String neshan) {
        this.neshan = neshan;
    }

    public String getDarajeCode() {
        return darajeCode;
    }

    public void setDarajeCode(String darajeCode) {
        this.darajeCode = darajeCode;
    }

    public String getSematCode() {
        return sematCode;
    }

    public void setSematCode(String sematCode) {
        this.sematCode = sematCode;
    }

    public Emza getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Emza selectMode) {
        this.selectMode = selectMode;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
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