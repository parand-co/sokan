package manage.bean;

import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dashboard.DashboardBean;
import dashboard.model.Banner;
import dashboard.model.BannerDao;
import dashboard.model.News;
import dashboard.model.NewsDao;
import dataBaseModel.authentication.module.Module;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;
import util.Pic;
import util.Tarikh;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class CreateBaannerBean implements Serializable {
    private String URL;

    private List<Banner> savabegh = new ArrayList<>();

    private Alert alert = new Alert();

    // search field
    private Part avatar;

    private Banner selectMode = null;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public CreateBaannerBean() {
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
        savabegh = session.createQuery("FROM Banner").list();
        session.close();
    }

    public void save(){
        if (avatar != null) {
            //گرفتن مسیر پروژه در سرور
            String projectPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat("/../pic/banner/");
            String bannerPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat("/resources/images/dashboard/banner/");
            //ساخت فایل مقصد ذخیره سازی
            String name = System.currentTimeMillis()+ ".jpg";
            File destionation = new File(projectPath + "/" + name);
            File destionationBanner = new File(bannerPath + "/" + name);
            try {
                //کپی کردن فایل به مقصد
                InputStream inputStream = avatar.getInputStream();
                InputStream inputStreamBanner = avatar.getInputStream();
                Files.copy(inputStream, destionation.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(inputStreamBanner, destionationBanner.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Banner banner = new Banner();
                banner.setUrl(name);
                BannerDao.getInstance().getBaseQuery().create(banner, URL);

                savabegh.add(banner);
                avatar = null;

                alert.successSave();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.fieldIsEmpty("تصویر");
        }
    }

    public void nuller() {
        avatar = null;

        selectMode = null;
    }

    public void startDelete(Banner banner){
        selectMode = banner;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(Banner banner){
        BannerDao.getInstance().getBaseQuery().delete(banner, URL);
        savabegh.remove(banner);
        alert.successDelete();
    }


    // GETTER AND SETTER

    public List<Banner> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<Banner> savabegh) {
        this.savabegh = savabegh;
    }

    public Part getAvatar() {
        return avatar;
    }

    public void setAvatar(Part avatar) {
        this.avatar = avatar;
    }

    public Banner getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(Banner selectMode) {
        this.selectMode = selectMode;
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