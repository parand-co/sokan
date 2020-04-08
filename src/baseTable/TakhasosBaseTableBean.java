package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Takhasos;
import dataBaseModel.dao.TakhasosDao;
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
public class TakhasosBaseTableBean implements Serializable {
    private String URL;
    private List<Takhasos> savabegh = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String codeSearch;
    private String titleSearch;

    private String code;
    private String title;

    private Takhasos selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public TakhasosBaseTableBean() {
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
        savabegh = session.createQuery("FROM Takhasos").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM Takhasos WHERE 1 = 1 ");

        if(codeSearch != null && !codeSearch.equals("")){
            query.append("AND code = '").append(codeSearch).append("' ");
        }

        if(titleSearch != null && !titleSearch.equals("")){
            query.append("AND title LIKE '%").append(titleSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(code != null && !code.equals("")){
            if(title != null && !title.equals("")){
                Session session = HibernateUtil.getSession();
                Long count = (Long) session.createQuery("FROM Takhasos WHERE code = ?").setString(0, code).uniqueResult();
                session.close();

                if(count == null || count == 0){
                    Takhasos takhasos = new Takhasos();

                    takhasos.setCode(code);
                    takhasos.setTitle(title);

                    TakhasosDao.getInstance().getBaseQuery().create(takhasos, URL);
                    savabegh.add(takhasos);

                    alert.successSave();
                    nuller();
                } else {
                    alert.codeDuplicate();
                }
            } else {
                alert.fieldIsEmpty("عنوان تخصص");
            }
        } else {
            alert.fieldIsEmpty("کد تخصص");
        }
    }

    public void nuller() {
        title = "";
        code = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(Takhasos model){
        edited = true;

        title = model.getTitle();
        code = model.getCode();

        selectMode = model;
    }

    public void edit(){

        if(code != null && !code.equals("")){
            if(title != null && !title.equals("")){
                Session session = HibernateUtil.getSession();
                Long count = (Long) session.createQuery("FROM Takhasos WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, code).uniqueResult();
                session.close();

                if(count == null || count == 0){
                    savabegh.remove(selectMode);
                    Takhasos takhasos = selectMode;

                    takhasos.setCode(code);
                    takhasos.setTitle(title);

                    TakhasosDao.getInstance().getBaseQuery().createOrUpdate(takhasos, URL);
                    savabegh.add(takhasos);

                    alert.successEdit();
                    nuller();
                } else {
                    alert.codeDuplicate();
                }
            } else {
                alert.fieldIsEmpty("عنوان تخصص");
            }
        } else {
            alert.fieldIsEmpty("کد تخصص");
        }

    }

    public void startDelete(Takhasos model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        TakhasosDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<Takhasos> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Takhasos> savabegh) {
        this.savabegh = savabegh;
    }

    public String getCodeSearch() {
        return codeSearch;
    }

    public void setCodeSearch(String codeSearch) {
        this.codeSearch = codeSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Takhasos getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Takhasos selectMode) {
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