package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.MojavezRozane;
import dataBaseModel.dao.MojavezRozaneDao;
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
public class MojavezRozaneBaseTableBean implements Serializable {
    private String URL;
    private List<MojavezRozane> savabegh = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String codeSearch;
    private String typeSearch;
    private String titleSearch;

    private String code;
    private String type;
    private String title;

    private MojavezRozane selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public MojavezRozaneBaseTableBean() {
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
        savabegh = session.createQuery("FROM MojavezRozane").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM MojavezRozane WHERE 1 = 1 ");

        if(codeSearch != null && !codeSearch.equals("")){
            query.append("AND code = '").append(codeSearch).append("' ");
        }

        if(typeSearch != null && !typeSearch.equals("")){
            query.append("AND roozaneSaatiTitle = '").append(typeSearch).append("' ");
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
                if (type != null && !type.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM MojavezRozane WHERE code = ?").setString(0, code).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        MojavezRozane mojavezRozane = new MojavezRozane();

                        mojavezRozane.setCode(code);
                        mojavezRozane.setTitle(title);
                        if(type.equals("روزانه")){
                            mojavezRozane.setRoozaneSaatiTitle(type);
                            mojavezRozane.setRoozaneOrSaati(false);
                        } else {
                            mojavezRozane.setRoozaneSaatiTitle(type);
                            mojavezRozane.setRoozaneOrSaati(true);
                        }

                        MojavezRozaneDao.getInstance().getBaseQuery().create(mojavezRozane, URL);
                        savabegh.add(mojavezRozane);

                        alert.successSave();
                        nuller();
                    } else {
                       alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("نوع مجوز");
                }
            } else {
                alert.fieldIsEmpty("عنوان مجوز");
            }
        } else {
            alert.fieldIsEmpty("کد مجوز");
        }
    }

    public void nuller() {
        title = "";
        code = "";
        type = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(MojavezRozane model){
        edited = true;

        title = model.getTitle();
        code = model.getCode();
        type = model.getRoozaneSaatiTitle();

        selectMode = model;
    }

    public void edit(){

        if(code != null && !code.equals("")){
            if(title != null && !title.equals("")){
                if (type != null && !type.equals("")){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM MojavezRozane WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, code).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        savabegh.remove(selectMode);
                        MojavezRozane mojavezRozane = selectMode;

                        mojavezRozane.setCode(code);
                        mojavezRozane.setTitle(title);
                        if(type.equals("روزانه")){
                            mojavezRozane.setRoozaneSaatiTitle(type);
                            mojavezRozane.setRoozaneOrSaati(false);
                        } else {
                            mojavezRozane.setRoozaneSaatiTitle(type);
                            mojavezRozane.setRoozaneOrSaati(true);
                        }

                        MojavezRozaneDao.getInstance().getBaseQuery().createOrUpdate(mojavezRozane, URL);
                        savabegh.add(mojavezRozane);

                        alert.successEdit();
                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("نوع مجوز");
                }
            } else {
                alert.fieldIsEmpty("عنوان مجوز");
            }
        } else {
            alert.fieldIsEmpty("کد مجوز");
        }

    }

    public void startDelete(MojavezRozane model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        MojavezRozaneDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<MojavezRozane> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<MojavezRozane> savabegh) {
        this.savabegh = savabegh;
    }

    public String getCodeSearch() {
        return codeSearch;
    }

    public void setCodeSearch(String codeSearch) {
        this.codeSearch = codeSearch;
    }

    public String getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MojavezRozane getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(MojavezRozane selectMode) {
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