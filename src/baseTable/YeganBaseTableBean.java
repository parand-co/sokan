package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Paygah;
import dataBaseModel.baseTable.Yegan;
import dataBaseModel.dao.YeganDao;
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
public class YeganBaseTableBean implements Serializable {
    private String URL;
    private List<Yegan> savabegh = new ArrayList<>();
    private List<Paygah> paygahs = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String paygahCodeSearch;
    private String titleSearch;

    private String paygahCode;
    private String yeganCode;
    private String title;

    private Yegan selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public YeganBaseTableBean() {
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
        savabegh = session.createQuery("FROM Yegan").list();
        paygahs = session.createQuery("FROM Paygah").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM Yegan WHERE 1 = 1 ");

        if(paygahCodeSearch != null && !paygahCodeSearch.equals("")){
            query.append("AND paygah.code = '").append(paygahCodeSearch).append("' ");
        }

        if(titleSearch != null && !titleSearch.equals("")){
            query.append("AND title LIKE '%").append(titleSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(paygahCode != null && !paygahCode.equals("")){
            if(title != null && !title.equals("")){
                if (yeganCode != null && !yeganCode.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Yegan WHERE code = ?").setString(0, yeganCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        Yegan yegan = new Yegan();

                        yegan.setCode(yeganCode);
                        yegan.setTitle(title);
                        yegan.setPaygah(paygahs.stream().filter(o -> o.getCode().equals(paygahCode)).findFirst().orElse(null));

                        YeganDao.getInstance().getBaseQuery().create(yegan, URL);
                        savabegh.add(yegan);

                        alert.successSave();
                        nuller();
                    } else {
                       alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("کد یگان");
                }
            } else {
                alert.fieldIsEmpty("عنوان یگان");
            }
        } else {
            alert.fieldIsEmpty("پایگاه");
        }
    }

    public void nuller() {
        title = "";
        yeganCode = "";
        paygahCode = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(Yegan model){
        edited = true;

        title = model.getTitle();
        yeganCode = model.getCode();
        paygahCode = model.getPaygah().getCode();

        selectMode = model;
    }

    public void edit(){

        if(paygahCode != null && !paygahCode.equals("")){
            if(title != null && !title.equals("")){
                if (yeganCode != null && !yeganCode.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Yegan WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, yeganCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        savabegh.remove(selectMode);
                        Yegan yegan = selectMode;

                        yegan.setCode(yeganCode);
                        yegan.setTitle(title);
                        yegan.setPaygah(paygahs.stream().filter(o -> o.getCode().equals(paygahCode)).findFirst().orElse(null));

                        YeganDao.getInstance().getBaseQuery().createOrUpdate(yegan, URL);
                        savabegh.add(yegan);

                        alert.successEdit();
                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("کد یگان");
                }
            } else {
                alert.fieldIsEmpty("عنوان یگان");
            }
        } else {
            alert.fieldIsEmpty("پایگاه");
        }

    }

    public void startDelete(Yegan model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        YeganDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<Yegan> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Yegan> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Paygah> getPaygahs() {
        return paygahs;
    }

    public void setPaygahs(List<Paygah> paygahs) {
        this.paygahs = paygahs;
    }

    public String getPaygahCodeSearch() {
        return paygahCodeSearch;
    }

    public void setPaygahCodeSearch(String paygahCodeSearch) {
        this.paygahCodeSearch = paygahCodeSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getPaygahCode() {
        return paygahCode;
    }

    public void setPaygahCode(String paygahCode) {
        this.paygahCode = paygahCode;
    }

    public String getYeganCode() {
        return yeganCode;
    }

    public void setYeganCode(String yeganCode) {
        this.yeganCode = yeganCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Yegan getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Yegan selectMode) {
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