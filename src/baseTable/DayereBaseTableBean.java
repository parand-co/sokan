package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.Paygah;
import dataBaseModel.baseTable.Yegan;
import dataBaseModel.dao.DayereDao;
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
public class DayereBaseTableBean implements Serializable {
    private String URL;
    private List<Dayere> savabegh = new ArrayList<>();
    private List<Paygah> yegans = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String yeganCodeSearch;
    private String titleSearch;

    private String yeganCode;
    private String dayereCode;
    private String title;

    private Dayere selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public DayereBaseTableBean() {
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
        savabegh = session.createQuery("FROM Dayere").list();
        yegans = session.createQuery("FROM Paygah").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM Dayere WHERE 1 = 1 ");

        if(yeganCodeSearch != null && !yeganCodeSearch.equals("")){
            query.append("AND yegan.code = '").append(yeganCodeSearch).append("' ");
        }

        if(titleSearch != null && !titleSearch.equals("")){
            query.append("AND title LIKE '%").append(titleSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(yeganCode != null && !yeganCode.equals("")){
            if(title != null && !title.equals("")){
                if (dayereCode != null && !dayereCode.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Dayere WHERE code = ?").setString(0, dayereCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        Dayere dayere = new Dayere();

                        dayere.setCode(dayereCode);
                        dayere.setTitle(title);
                        dayere.setPaygah(yegans.stream().filter(o -> o.getCode().equals(yeganCode)).findFirst().orElse(null));

                        DayereDao.getInstance().getBaseQuery().create(dayere, URL);
                        savabegh.add(dayere);

                        alert.successSave();
                        nuller();
                    } else {
                       alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("کد دایره");
                }
            } else {
                alert.fieldIsEmpty("عنوان دایره");
            }
        } else {
            alert.fieldIsEmpty("یگان");
        }
    }

    public void nuller() {
        title = "";
        dayereCode = "";
        yeganCode = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(Dayere model){
        edited = true;

        title = model.getTitle();
        dayereCode = model.getCode();
        if(model.getPaygah() != null) {
            yeganCode = model.getPaygah().getCode();
        }

        selectMode = model;
    }

    public void edit(){
        if(yeganCode != null && !yeganCode.equals("")){
            if(title != null && !title.equals("")){
                if (dayereCode != null && !dayereCode.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Dayere WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, dayereCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        savabegh.remove(selectMode);
                        Dayere dayere = selectMode;

                        dayere.setCode(dayereCode);
                        dayere.setTitle(title);
                        dayere.setPaygah(yegans.stream().filter(o -> o.getCode().equals(yeganCode)).findFirst().orElse(null));

                        DayereDao.getInstance().getBaseQuery().createOrUpdate(dayere, URL);
                        savabegh.add(dayere);

                        alert.successEdit();
                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("کد دایره");
                }
            } else {
                alert.fieldIsEmpty("عنوان دایره");
            }
        } else {
            alert.fieldIsEmpty("یگان");
        }

    }

    public void startDelete(Dayere model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        DayereDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<Dayere> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Dayere> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Paygah> getYegans() {
        return yegans;
    }

    public void setYegans(List<Paygah> yegans) {
        this.yegans = yegans;
    }

    public String getYeganCodeSearch() {
        return yeganCodeSearch;
    }

    public void setYeganCodeSearch(String yeganCodeSearch) {
        this.yeganCodeSearch = yeganCodeSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getYeganCode() {
        return yeganCode;
    }

    public void setYeganCode(String yeganCode) {
        this.yeganCode = yeganCode;
    }

    public String getDayereCode() {
        return dayereCode;
    }

    public void setDayereCode(String dayereCode) {
        this.dayereCode = dayereCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dayere getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Dayere selectMode) {
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