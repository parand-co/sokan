package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Daraje;
import dataBaseModel.dao.DarajeDao;
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
public class DarajeBaseTableBean implements Serializable {
    private String URL;
    private List<Daraje> savabegh = new ArrayList<>();
    private List<Daraje> darajes = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String darajeCodeSearch;
    private Long saghfSearch;

    private String darajeCode;
    private String title;
    private Long saghf;

    private Daraje selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public DarajeBaseTableBean() {
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
        savabegh = session.createQuery("FROM Daraje").list();
        darajes = session.createQuery("FROM Daraje").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM Daraje WHERE 1 = 1 ");

        if(darajeCodeSearch != null && !darajeCodeSearch.equals("")){
            query.append("AND code = '").append(darajeCodeSearch).append("' ");
        }

        if(saghfSearch != null){
            query.append("AND saghfEzfkar <= '").append(saghfSearch).append("' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(darajeCode != null && !darajeCode.equals("")){
            if(title != null && !title.equals("")){
                if (saghf != null && saghf > 0){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Daraje WHERE code = ?").setString(0, darajeCode).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        Daraje daraje = new Daraje();

                        daraje.setCode(darajeCode);
                        daraje.setTitle(title);
                        daraje.setSaghfEzfkar(saghf);

                        DarajeDao.getInstance().getBaseQuery().create(daraje, URL);
                        savabegh.add(daraje);
                        darajes.add(daraje);
                        alert.successSave();
                        nuller();
                    } else {
                       alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("سقف اضافه کار");
                }
            } else {
                alert.fieldIsEmpty("عنوان درجه");
            }
        } else {
            alert.fieldIsEmpty("کد درجه");
        }
    }

    public void nuller() {
        title = "";
        darajeCode = "";
        saghf = 0L;

        edited = false;
        selectMode = null;
    }

    public void dispach(Daraje model){
        edited = true;

        title = model.getTitle();
        darajeCode = model.getCode();
        saghf = model.getSaghfEzfkar();

        selectMode = model;
    }

    public void edit(){

        if(darajeCode != null && !darajeCode.equals("")){
            if(title != null && !title.equals("")){
                if (saghf != null && saghf > 0){
                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Daraje WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, darajeCode).uniqueResult();
                    session.close();
                    if(count == null || count == 0){
                        savabegh.remove(selectMode);
                        darajes.remove(selectMode);
                        Daraje daraje = selectMode;

                        daraje.setCode(darajeCode);
                        daraje.setTitle(title);
                        daraje.setSaghfEzfkar(saghf);

                        DarajeDao.getInstance().getBaseQuery().createOrUpdate(daraje, URL);
                        alert.successEdit();

                        savabegh.add(daraje);
                        darajes.add(daraje);

                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("سقف اضافه کار");
                }
            } else {
                alert.fieldIsEmpty("عنوان درجه");
            }
        } else {
            alert.fieldIsEmpty("کد درجه");
        }

    }

    public void startDelete(Daraje model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        DarajeDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<Daraje> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Daraje> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Daraje> getDarajes() {
        return darajes;
    }

    public void setDarajes(List<Daraje> darajes) {
        this.darajes = darajes;
    }

    public String getDarajeCodeSearch() {
        return darajeCodeSearch;
    }

    public void setDarajeCodeSearch(String darajeCodeSearch) {
        this.darajeCodeSearch = darajeCodeSearch;
    }

    public Long getSaghfSearch() {
        return saghfSearch;
    }

    public void setSaghfSearch(Long saghfSearch) {
        this.saghfSearch = saghfSearch;
    }

    public String getDarajeCode() {
        return darajeCode;
    }

    public void setDarajeCode(String darajeCode) {
        this.darajeCode = darajeCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSaghf() {
        return saghf;
    }

    public void setSaghf(Long saghf) {
        this.saghf = saghf;
    }

    public Daraje getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Daraje selectMode) {
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