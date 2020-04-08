package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Daraje;
import dataBaseModel.baseTable.DayType;
import dataBaseModel.dao.DarajeDao;
import dataBaseModel.dao.DayTypeDao;
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
public class DayTypeBaseTableBean implements Serializable {
    private String URL;
    private List<DayType> savabegh = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String titleSearch;

    private String title;

    private DayType selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public DayTypeBaseTableBean() {
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
        savabegh = session.createQuery("FROM DayType").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM DayType WHERE 1 = 1 ");

        if(titleSearch != null && !titleSearch.equals("")){
            query.append("AND type LIKE '%").append(titleSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(title != null && !title.equals("")){
            DayType dayType = new DayType();

            dayType.setType(title);

            DayTypeDao.getInstance().getBaseQuery().create(dayType, URL);
            savabegh.add(dayType);
            alert.successSave();
            nuller();
        } else {
            alert.fieldIsEmpty("عنوان");
        }
    }

    public void nuller() {
        title = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(DayType model){
        edited = true;

        title = model.getType();

        selectMode = model;
    }

    public void edit(){

        if(title != null && !title.equals("")){
            savabegh.remove(selectMode);

            DayType dayType = selectMode;

            dayType.setType(title);

            DayTypeDao.getInstance().getBaseQuery().createOrUpdate(dayType, URL);
            alert.successEdit();

            savabegh.add(dayType);

            nuller();
        } else {
            alert.fieldIsEmpty("عنوان");
        }

    }

    public void startDelete(DayType model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        DayTypeDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<DayType> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<DayType> savabegh) {
        this.savabegh = savabegh;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DayType getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(DayType selectMode) {
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