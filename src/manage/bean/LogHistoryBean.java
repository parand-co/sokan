package manage.bean;

import baseCode.alert.Alert;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Daraje;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.dao.DarajeDao;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class LogHistoryBean implements Serializable {
    private String URL;
    private List<LogHistory> savabegh = new ArrayList<>();

    // search field
    private String activeSearch;
    private String tarikhAz;
    private String tarikhTa;
    private String shp;
    private String user;


    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public LogHistoryBean() {
        URL = IndexBean.url;

        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }
    }

    public void search(){
        StringBuilder query = new StringBuilder("FROM LogHistory WHERE 1 = 1 ");

        if(shp != null && !shp.equals("")){
            query.append("AND user.personel.shomarePersoneli = '").append(shp).append("' ");
        }

        if(user != null && !user.equals("")){
            query.append("AND user.userName = '").append(user).append("' ");
        }

        if(activeSearch != null && !activeSearch.equals("")){
            query.append("AND action = '").append(activeSearch).append("' ");
        }

        if(tarikhAz != null && !tarikhAz.equals("")){
            query.append("AND date >= '").append(tarikhAz).append("' ");
        }

        if(tarikhTa != null && !tarikhTa.equals("")){
            query.append("AND date <= '").append(tarikhTa).append("' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }




    // GETTER AND SETTER


    public List<LogHistory> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<LogHistory> savabegh) {
        this.savabegh = savabegh;
    }

    public String getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(String activeSearch) {
        this.activeSearch = activeSearch;
    }

    public String getTarikhAz() {
        return tarikhAz;
    }

    public void setTarikhAz(String tarikhAz) {
        this.tarikhAz = tarikhAz;
    }

    public String getTarikhTa() {
        return tarikhTa;
    }

    public void setTarikhTa(String tarikhTa) {
        this.tarikhTa = tarikhTa;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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