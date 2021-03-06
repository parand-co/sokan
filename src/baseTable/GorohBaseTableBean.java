package baseTable;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Goroh;
import dataBaseModel.baseTable.Tabaghe;
import dataBaseModel.dao.GorohDao;
import dataBaseModel.dao.TabagheDao;
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
public class GorohBaseTableBean implements Serializable {
    private String URL;
    private List<Goroh> savabegh = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String codeSearch;
    private String titleSearch;

    private String code;
    private String title;
    private String saatShoroKarAddi;
    private String saatPayanKarAddi;
    private String saatShoroEzfAddi;
    private String saatPayanEzfAddi;
    private String saatShoroKarTatil;
    private String saatPayanKarTatil;
    private String saatShoroEzfTatil;
    private String saatPayanEzfTatil;
    private String saatShoroKarNimTatil;
    private String saatPayanKarNimTatil;
    private String saatShoroEzfNimTatil;
    private String saatPayanEzfNimTatil;

    private Goroh selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public GorohBaseTableBean() {
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
        savabegh = session.createQuery("FROM Goroh").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM Goroh WHERE 1 = 1 ");

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
                if(saatShoroKarAddi != null && saatPayanKarAddi != null && saatShoroEzfAddi != null && saatPayanEzfAddi != null
                        && saatShoroKarTatil != null && saatPayanKarTatil != null && saatShoroEzfTatil != null
                        && saatPayanEzfTatil != null && saatShoroKarNimTatil != null && saatPayanKarNimTatil != null
                        && saatShoroEzfNimTatil != null && saatPayanEzfNimTatil != null && !saatShoroKarAddi.equals("")
                        && !saatPayanKarAddi.equals("") && !saatShoroEzfAddi.equals("") && !saatPayanEzfAddi.equals("")
                        && !saatShoroKarTatil.equals("") && !saatPayanKarTatil.equals("") && !saatShoroEzfTatil.equals("")
                        && !saatPayanEzfTatil.equals("") && !saatShoroKarNimTatil.equals("") && !saatPayanKarNimTatil.equals("")
                        && !saatShoroEzfNimTatil.equals("") && !saatPayanEzfNimTatil.equals("")){

                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Goroh WHERE code = ?").setString(0, code).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        Goroh goroh = new Goroh();

                        goroh.setCode(code);
                        goroh.setTitle(title);
                        goroh.setSaatShoroKarAddi(saatShoroKarAddi.replace(":", ""));
                        goroh.setSaatPayanKarAddi(saatPayanKarAddi.replace(":", ""));
                        goroh.setSaatShoroEzfAddi(saatShoroEzfAddi.replace(":", ""));
                        goroh.setSaatPayanEzfAddi(saatPayanEzfAddi.replace(":", ""));
                        goroh.setSaatShoroKarTatil(saatShoroKarTatil.replace(":", ""));
                        goroh.setSaatPayanKarTatil(saatPayanKarTatil.replace(":", ""));
                        goroh.setSaatShoroEzfTatil(saatShoroEzfTatil.replace(":", ""));
                        goroh.setSaatPayanEzfTatil(saatPayanEzfTatil.replace(":", ""));
                        goroh.setSaatShoroKarNimTatil(saatShoroKarNimTatil.replace(":", ""));
                        goroh.setSaatPayanKarNimTatil(saatPayanKarNimTatil.replace(":", ""));
                        goroh.setSaatShoroEzfNimTatil(saatShoroEzfNimTatil.replace(":", ""));
                        goroh.setSaatPayanEzfNimTatil(saatPayanEzfNimTatil.replace(":", ""));

                        GorohDao.getInstance().getBaseQuery().create(goroh, URL);
                        savabegh.add(goroh);

                        alert.successSave();
                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("تمامی ساعت ها");
                }
            } else {
                alert.fieldIsEmpty("عنوان گروه");
            }
        } else {
            alert.fieldIsEmpty("کد گروه");
        }
    }

    public void nuller() {
        title = "";
        code = "";
        saatShoroKarAddi = "";
        saatPayanKarAddi = "";
        saatShoroEzfAddi = "";
        saatPayanEzfAddi = "";
        saatShoroKarTatil = "";
        saatPayanKarTatil = "";
        saatShoroEzfTatil = "";
        saatPayanEzfTatil = "";
        saatShoroKarNimTatil = "";
        saatPayanKarNimTatil = "";
        saatShoroEzfNimTatil = "";
        saatPayanEzfNimTatil = "";

        edited = false;
        selectMode = null;
    }

    public void dispach(Goroh model){
        edited = true;

        title = model.getTitle();
        code = model.getCode();
        saatShoroKarAddi = model.getSaatShoroKarAddi();
        saatPayanKarAddi = model.getSaatPayanKarAddi();
        saatShoroEzfAddi = model.getSaatShoroEzfAddi();
        saatPayanEzfAddi = model.getSaatPayanEzfAddi();
        saatShoroKarTatil = model.getSaatShoroKarTatil();
        saatPayanKarTatil = model.getSaatPayanKarTatil();
        saatShoroEzfTatil = model.getSaatShoroEzfTatil();
        saatPayanEzfTatil = model.getSaatPayanEzfTatil();
        saatShoroKarNimTatil = model.getSaatShoroKarNimTatil();
        saatPayanKarNimTatil = model.getSaatPayanKarNimTatil();
        saatShoroEzfNimTatil = model.getSaatShoroEzfNimTatil();
        saatPayanEzfNimTatil = model.getSaatPayanEzfNimTatil();

        selectMode = model;
    }

    public void edit(){

        if(code != null && !code.equals("")){
            if(title != null && !title.equals("")){
                if(saatShoroKarAddi != null && saatPayanKarAddi != null && saatShoroEzfAddi != null && saatPayanEzfAddi != null
                        && saatPayanEzfTatil != null && saatShoroKarNimTatil != null && saatPayanKarNimTatil != null
                        && saatShoroKarTatil != null && saatPayanKarTatil != null && saatShoroEzfTatil != null
                        && saatShoroEzfNimTatil != null && saatPayanEzfNimTatil != null && !saatShoroKarAddi.equals("")
                        && !saatShoroKarTatil.equals("") && !saatPayanKarTatil.equals("") && !saatShoroEzfTatil.equals("")
                        && !saatPayanKarAddi.equals("") && !saatShoroEzfAddi.equals("") && !saatPayanEzfAddi.equals("")
                        && !saatPayanEzfTatil.equals("") && !saatShoroKarNimTatil.equals("") && !saatPayanKarNimTatil.equals("")
                        && !saatShoroEzfNimTatil.equals("") && !saatPayanEzfNimTatil.equals("")){

                    Session session = HibernateUtil.getSession();
                    Long count = (Long) session.createQuery("FROM Goroh WHERE id != ? AND code = ?").setLong(0, selectMode.getId()).setString(1, code).uniqueResult();
                    session.close();

                    if(count == null || count == 0){
                        savabegh.remove(selectMode);
                        Goroh goroh = selectMode;

                        goroh.setCode(code);
                        goroh.setTitle(title);
                        goroh.setSaatShoroKarAddi(saatShoroKarAddi.replace(":", ""));
                        goroh.setSaatShoroEzfAddi(saatShoroEzfAddi.replace(":", ""));
                        goroh.setSaatPayanKarAddi(saatPayanKarAddi.replace(":", ""));
                        goroh.setSaatPayanEzfAddi(saatPayanEzfAddi.replace(":", ""));
                        goroh.setSaatPayanKarTatil(saatPayanKarTatil.replace(":", ""));
                        goroh.setSaatShoroKarTatil(saatShoroKarTatil.replace(":", ""));
                        goroh.setSaatShoroEzfTatil(saatShoroEzfTatil.replace(":", ""));
                        goroh.setSaatShoroKarNimTatil(saatShoroKarNimTatil.replace(":", ""));
                        goroh.setSaatPayanEzfTatil(saatPayanEzfTatil.replace(":", ""));
                        goroh.setSaatPayanKarNimTatil(saatPayanKarNimTatil.replace(":", ""));
                        goroh.setSaatPayanEzfNimTatil(saatPayanEzfNimTatil.replace(":", ""));
                        goroh.setSaatShoroEzfNimTatil(saatShoroEzfNimTatil.replace(":", ""));

                        GorohDao.getInstance().getBaseQuery().createOrUpdate(goroh, URL);
                        savabegh.add(goroh);

                        alert.successSave();
                        nuller();
                    } else {
                        alert.codeDuplicate();
                    }
                } else {
                    alert.fieldIsEmpty("تمامی ساعت ها");
                }
            } else {
                alert.fieldIsEmpty("عنوان گروه");
            }
        } else {
            alert.fieldIsEmpty("کد گروه");
        }

    }

    public void startDelete(Goroh model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        GorohDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }




    // GETTER AND SETTER

    public List<Goroh> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Goroh> savabegh) {
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

    public Goroh getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Goroh selectMode) {
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

    public String getSaatShoroKarAddi() {
        return saatShoroKarAddi;
    }

    public void setSaatShoroKarAddi(String saatShoroKarAddi) {
        this.saatShoroKarAddi = saatShoroKarAddi;
    }

    public String getSaatPayanKarAddi() {
        return saatPayanKarAddi;
    }

    public void setSaatPayanKarAddi(String saatPayanKarAddi) {
        this.saatPayanKarAddi = saatPayanKarAddi;
    }

    public String getSaatShoroEzfAddi() {
        return saatShoroEzfAddi;
    }

    public void setSaatShoroEzfAddi(String saatShoroEzfAddi) {
        this.saatShoroEzfAddi = saatShoroEzfAddi;
    }

    public String getSaatPayanEzfAddi() {
        return saatPayanEzfAddi;
    }

    public void setSaatPayanEzfAddi(String saatPayanEzfAddi) {
        this.saatPayanEzfAddi = saatPayanEzfAddi;
    }

    public String getSaatShoroKarTatil() {
        return saatShoroKarTatil;
    }

    public void setSaatShoroKarTatil(String saatShoroKarTatil) {
        this.saatShoroKarTatil = saatShoroKarTatil;
    }

    public String getSaatPayanKarTatil() {
        return saatPayanKarTatil;
    }

    public void setSaatPayanKarTatil(String saatPayanKarTatil) {
        this.saatPayanKarTatil = saatPayanKarTatil;
    }

    public String getSaatShoroEzfTatil() {
        return saatShoroEzfTatil;
    }

    public void setSaatShoroEzfTatil(String saatShoroEzfTatil) {
        this.saatShoroEzfTatil = saatShoroEzfTatil;
    }

    public String getSaatPayanEzfTatil() {
        return saatPayanEzfTatil;
    }

    public void setSaatPayanEzfTatil(String saatPayanEzfTatil) {
        this.saatPayanEzfTatil = saatPayanEzfTatil;
    }

    public String getSaatShoroKarNimTatil() {
        return saatShoroKarNimTatil;
    }

    public void setSaatShoroKarNimTatil(String saatShoroKarNimTatil) {
        this.saatShoroKarNimTatil = saatShoroKarNimTatil;
    }

    public String getSaatPayanKarNimTatil() {
        return saatPayanKarNimTatil;
    }

    public void setSaatPayanKarNimTatil(String saatPayanKarNimTatil) {
        this.saatPayanKarNimTatil = saatPayanKarNimTatil;
    }

    public String getSaatShoroEzfNimTatil() {
        return saatShoroEzfNimTatil;
    }

    public void setSaatShoroEzfNimTatil(String saatShoroEzfNimTatil) {
        this.saatShoroEzfNimTatil = saatShoroEzfNimTatil;
    }

    public String getSaatPayanEzfNimTatil() {
        return saatPayanEzfNimTatil;
    }

    public void setSaatPayanEzfNimTatil(String saatPayanEzfNimTatil) {
        this.saatPayanEzfNimTatil = saatPayanEzfNimTatil;
    }
}