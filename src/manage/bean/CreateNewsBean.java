package manage.bean;

import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dashboard.model.News;
import dashboard.model.NewsDao;
import dataBaseModel.authentication.module.Module;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;
import util.Tarikh;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class CreateNewsBean implements Serializable {
    private String URL;

    private List<News> savabegh = new ArrayList<>();

    private List<Module> modules = new ArrayList<>();

    private Alert alert = new Alert();

    // search field
    private String moduleCode;
    private String title;
    private String kholase;
    private String matn;

    private String moduleCodeSearch;
    private String titleSearch;
    private String matnSearch;

    private News selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public CreateNewsBean() {
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
        modules = session.createQuery("FROM Module WHERE id != 10").list();
        session.close();
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM News WHERE 1 = 1");

        if(moduleCodeSearch != null && !moduleCodeSearch.equals("")){
            query.append("AND module.code = '").append(moduleCodeSearch).append("' ");
        }

        if(titleSearch != null && !titleSearch.equals("")){
            query.append("AND title LIKE '%").append(titleSearch).append("%' ");
        }

        if(matnSearch != null && !matnSearch.equals("")){
            query.append("AND matn LIKE '%").append(matnSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void save(){
        if(moduleCode != null && !moduleCode.equals("")){
            if(title != null && !title.equals("")){
                if(kholase != null && !kholase.equals("")){
                    if(matn != null && !matn.equals("")){
                        News news = new News();

                        ULocale ir_locale = new ULocale("fa", "IR");
                        String tarikh = new SimpleDateFormat("yyyy/MM/dd", ir_locale).format(Calendar.getInstance());

                        news.setInsertDate(new Tarikh().getDayOfWeek() + " " + tarikh);
                        news.setModule(modules.stream().filter(o -> o.getCode().equals(moduleCode)).findFirst().orElse(null));
                        news.setTitle(title);
                        news.setKholase(kholase);
                        news.setMatn(matn);

                        NewsDao.getInstance().getBaseQuery().create(news, URL);
                        alert.successSave();
                        savabegh.add(news);

                        nuller();
                    } else {
                        alert.fieldIsEmpty("متن");
                    }
                } else {
                    alert.fieldIsEmpty("خلاصه");
                }
            } else {
                alert.fieldIsEmpty("عنوان");
            }
        } else {
            alert.fieldIsEmpty("دسته");
        }
    }

    public void dispach(News news){
        moduleCode = news.getModule().getCode();
        title = news.getTitle();
        kholase = news.getKholase();
        matn = news.getMatn();

        selectMode = news;
        edited = true;
    }

    public void edit(){
        if(moduleCode != null && !moduleCode.equals("")){
            if(title != null && !title.equals("")){
                if(kholase != null && !kholase.equals("")){
                    if(matn != null && !matn.equals("")){
                        savabegh.remove(selectMode);

                        News news = selectMode;

                        news.setModule(modules.stream().filter(o -> o.getCode().equals(moduleCode)).findFirst().orElse(null));
                        news.setTitle(title);
                        news.setKholase(kholase);
                        news.setMatn(matn);

                        NewsDao.getInstance().getBaseQuery().createOrUpdate(news, URL);
                        alert.successEdit();
                        savabegh.add(news);

                        nuller();
                    } else {
                        alert.fieldIsEmpty("متن");
                    }
                } else {
                    alert.fieldIsEmpty("خلاصه");
                }
            } else {
                alert.fieldIsEmpty("عنوان");
            }
        } else {
            alert.fieldIsEmpty("دسته");
        }
    }

    public void nuller() {
        moduleCode = "";
        title = null;
        kholase = "";
        matn = null;

        edited = false;
        selectMode = null;
    }

    public void startDelete(News news){
        selectMode = news;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(News news){
        NewsDao.getInstance().getBaseQuery().delete(news, URL);
        savabegh.remove(news);
        alert.successDelete();
    }


    // GETTER AND SETTER

    public List<News> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<News> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKholase() {
        return kholase;
    }

    public void setKholase(String kholase) {
        this.kholase = kholase;
    }

    public String getMatn() {
        return matn;
    }

    public void setMatn(String matn) {
        this.matn = matn;
    }

    public String getModuleCodeSearch() {
        return moduleCodeSearch;
    }

    public void setModuleCodeSearch(String moduleCodeSearch) {
        this.moduleCodeSearch = moduleCodeSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getMatnSearch() {
        return matnSearch;
    }

    public void setMatnSearch(String matnSearch) {
        this.matnSearch = matnSearch;
    }

    public News getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(News selectMode) {
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