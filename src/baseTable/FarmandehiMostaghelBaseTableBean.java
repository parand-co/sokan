package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.FarmandehiMostaghel;
import dataBaseModel.dao.FarmandehiMostaghelDao;
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
public class FarmandehiMostaghelBaseTableBean implements Serializable {
    private String URL;
    private List<FarmandehiMostaghel> savabegh = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String codeSearch;
    private String titleSearch;

    private String code;
    private String title;

    private FarmandehiMostaghel selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public FarmandehiMostaghelBaseTableBean() {
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
        savabegh = session.createQuery("FROM FarmandehiMostaghel").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM FarmandehiMostaghel WHERE 1 = 1 ");

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
                Long count = (Long) session.createQuery("FROM FarmandehiMostaghel WHERE code = ?").setString(0, code).uniqueResult();
                session.close();

                if(count == null || count == 0){
                    FarmandehiMostaghel farmandehiMostaghel = new FarmandehiMostaghel();

                    farmandehiMostaghel.setCode(code);
                    farmandehiMostaghel.setTitle(title);

                    FarmandehiMostaghelDao.getInstance().getBaseQuery().create(farmandehiMostaghel, URL);
                    savabegh.add(farmandehiMostaghel);

                    alert.successSave();
                    nuller();
                } else {
                    alert.codeDuplicate();
                }
            } else {
                alert.fieldIsEmpty("عنوان فرماندهی مستقل");
            }
        } else {
            alert.fieldIsEmpty("کد فرماندهی مستقل");
        }
    }

    public void nuller() {
        title = "";
        code = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(FarmandehiMostaghel model){
        edited = true;

        title = model.getTitle();
        code = model.getCode();

        selectMode = model;
    }

    public void edit(){

        if(code != null && !code.equals("")){
            if(title != null && !title.equals("")){
                Session session = HibernateUtil.getSession();
                Long count = (Long) session.createQuery("FROM FarmandehiMostaghel WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, code).uniqueResult();
                session.close();

                if(count == null || count == 0){
                    savabegh.remove(selectMode);
                    FarmandehiMostaghel farmandehiMostaghel = selectMode;

                    farmandehiMostaghel.setCode(code);
                    farmandehiMostaghel.setTitle(title);

                    FarmandehiMostaghelDao.getInstance().getBaseQuery().createOrUpdate(farmandehiMostaghel, URL);
                    savabegh.add(farmandehiMostaghel);

                    alert.successEdit();
                    nuller();
                } else {
                    alert.codeDuplicate();
                }
            } else {
                alert.fieldIsEmpty("عنوان فرماندهی مستقل");
            }
        } else {
            alert.fieldIsEmpty("کد فرماندهی مستقل");
        }

    }

    public void startDelete(FarmandehiMostaghel model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        FarmandehiMostaghelDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<FarmandehiMostaghel> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<FarmandehiMostaghel> savabegh) {
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

    public FarmandehiMostaghel getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(FarmandehiMostaghel selectMode) {
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