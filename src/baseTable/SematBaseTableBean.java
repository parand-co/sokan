package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.Semat;
import dataBaseModel.dao.SematDao;
import dataBaseModel.util.HibernateUtil;
import manage.bean.IndexBean;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SematBaseTableBean implements Serializable {
    private String URL;
    private List<Semat> savabegh = new ArrayList<>();
    private List<Dayere> dayeres = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String dayereCodeSearch;
    private String titleSearch;

    private String dayereCode;
    private String sematCode;
    private String title;

    private Semat selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public SematBaseTableBean() {
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
        savabegh = session.createQuery("FROM Semat").list();
        dayeres = session.createQuery("FROM Dayere").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM Semat WHERE 1 = 1 ");

        if(dayereCodeSearch != null && !dayereCodeSearch.equals("")){
            query.append("AND dayere.code = '").append(dayereCodeSearch).append("' ");
        }

        if(titleSearch != null && !titleSearch.equals("")){
            query.append("AND title LIKE '%").append(titleSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(dayereCode != null && !dayereCode.equals("")){
            if(title != null && !title.equals("")){
                if (sematCode != null && !sematCode.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Semat WHERE code = ?").setString(0, sematCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        Semat semat = new Semat();

                        semat.setCode(sematCode);
                        semat.setTitle(title);
                        semat.setDayere(dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null));

                        SematDao.getInstance().getBaseQuery().create(semat, URL);
                        savabegh.add(semat);

                        alert.successSave();
                        nuller();
                    } else {
                       alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("کد سمت");
                }
            } else {
                alert.fieldIsEmpty("عنوان سمت");
            }
        } else {
            alert.fieldIsEmpty("دایره");
        }
    }

    public void nuller() {
        title = "";
        dayereCode = "";
        sematCode = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(Semat model){
        edited = true;

        title = model.getTitle();
        sematCode = model.getCode();
        dayereCode = model.getDayere().getCode();

        selectMode = model;
    }

    public void edit(){

        if(dayereCode != null && !dayereCode.equals("")){
            if(title != null && !title.equals("")){
                if (sematCode != null && !sematCode.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Semat WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, sematCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        savabegh.remove(selectMode);
                        Semat semat = selectMode;

                        semat.setCode(sematCode);
                        semat.setTitle(title);
                        semat.setDayere(dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null));

                        SematDao.getInstance().getBaseQuery().createOrUpdate(semat, URL);
                        savabegh.add(selectMode);

                        alert.successEdit();
                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("کد سمت");
                }
            } else {
                alert.fieldIsEmpty("عنوان سمت");
            }
        } else {
            alert.fieldIsEmpty("دایره");
        }

    }

    public void startDelete(Semat model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        SematDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<Semat> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Semat> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Dayere> getDayeres() {
        return dayeres;
    }

    public void setDayeres(List<Dayere> dayeres) {
        this.dayeres = dayeres;
    }

    public String getDayereCodeSearch() {
        return dayereCodeSearch;
    }

    public void setDayereCodeSearch(String dayereCodeSearch) {
        this.dayereCodeSearch = dayereCodeSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getDayereCode() {
        return dayereCode;
    }

    public void setDayereCode(String dayereCode) {
        this.dayereCode = dayereCode;
    }

    public String getSematCode() {
        return sematCode;
    }

    public void setSematCode(String sematCode) {
        this.sematCode = sematCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Semat getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Semat selectMode) {
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