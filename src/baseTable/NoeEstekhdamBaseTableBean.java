package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.dao.NoeEstekhdamDao;
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
public class NoeEstekhdamBaseTableBean implements Serializable {
    private String URL;
    private List<NoeEstekhdam> savabegh = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String codeSearch;
    private String titleSearch;

    private String code;
    private String title;

    private NoeEstekhdam selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public NoeEstekhdamBaseTableBean() {
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
        savabegh = session.createQuery("FROM NoeEstekhdam").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM NoeEstekhdam WHERE 1 = 1 ");

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
                Long count = (Long) session.createQuery("FROM NoeEstekhdam WHERE code = ?").setString(0, code).uniqueResult();
                session.close();

                if(count == null || count == 0){
                    NoeEstekhdam noeEstekhdam = new NoeEstekhdam();

                    noeEstekhdam.setCode(code);
                    noeEstekhdam.setTitle(title);

                    NoeEstekhdamDao.getInstance().getBaseQuery().create(noeEstekhdam, URL);
                    savabegh.add(noeEstekhdam);

                    alert.successSave();
                    nuller();
                } else {
                    alert.codeDuplicate();
                }
            } else {
                alert.fieldIsEmpty("عنوان نوع استخدام");
            }
        } else {
            alert.fieldIsEmpty("کد نوع استخدام");
        }
    }

    public void nuller() {
        title = "";
        code = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(NoeEstekhdam model){
        edited = true;

        title = model.getTitle();
        code = model.getCode();

        selectMode = model;
    }

    public void edit(){

        if(code != null && !code.equals("")){
            if(title != null && !title.equals("")){
                Session session = HibernateUtil.getSession();
                Long count = (Long) session.createQuery("FROM NoeEstekhdam WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, code).uniqueResult();
                session.close();

                if(count == null || count == 0){
                    savabegh.remove(selectMode);
                    NoeEstekhdam noeEstekhdam = selectMode;

                    noeEstekhdam.setCode(code);
                    noeEstekhdam.setTitle(title);

                    NoeEstekhdamDao.getInstance().getBaseQuery().createOrUpdate(noeEstekhdam, URL);
                    savabegh.add(noeEstekhdam);

                    alert.successEdit();
                    nuller();
                } else {
                    alert.codeDuplicate();
                }
            } else {
                alert.fieldIsEmpty("عنوان نوع استخدام");
            }
        } else {
            alert.fieldIsEmpty("کد استخدام");
        }

    }

    public void startDelete(NoeEstekhdam model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        NoeEstekhdamDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<NoeEstekhdam> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<NoeEstekhdam> savabegh) {
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

    public NoeEstekhdam getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(NoeEstekhdam selectMode) {
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