package manage.bean;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dashboard.DashboardBean;
import dashboard.login.Login;
import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.authentication.user.User;
import dataBaseModel.authentication.user.UserDao;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.manager.LogController;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import manage.model.AmalKardModelChart;
import manage.model.PartSide;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import util.Password;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class IndexBean implements Serializable {

    public static boolean login = false;
    public static List<PartSide> sideMenu = new ArrayList<>();
    public static List<Permission> permissions = new ArrayList<>();
    public static String url;
    public static User user = new User();
    private List<PartSide> menu = new ArrayList<>();

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emroz = simpleDateFormat.format(today.getTime());

    private Long tedadHazer = 0L;
    private Long tedadNahast = 0L;
    private Long tedadBazdid = 0L;
    private Long tedadOnline = 0L;

    private String ipAddress;

    public void checkLogin(){
        if(!login){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(DashboardBean.url + "error/404.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SessionUtil sessionUtil = new SessionUtil();
            user = sessionUtil.getPermission().getUser();
        }
    }

    public IndexBean() {
        if(FacesContext.getCurrentInstance().getExternalContext().getRequest() != null){
            doPost((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        }

        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "unknown";
        }
    }

    private void doPost(HttpServletRequest req) {
        doGet(req);
    }

    private void doGet(HttpServletRequest req) {
        SessionUtil sessionUtil = new SessionUtil();
        user = sessionUtil.getPermission().getUser();
//        System.out.println(req.getRequestURL());
//        System.out.println(req.getRequestURI());
//        System.out.println(req.getServletPath());
        url = ".." + req.getServletPath();

        if(user.getPassWord().equals(BCrypt.hashpw(Password.defaultPass, Password.saltBcrypt))){
            if(!url.equals("../manage/changePassword.xhtml")){
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(DashboardBean.url + "manage/changePassword.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(url.startsWith("../error/")){
            permissions = null;
        } else if(url.equals("../manage/changePassword.xhtml")){
            permissions = null;
        } else if(!url.equals("../index/index.xhtml")){
            Session session = HibernateUtil.getSession();
            permissions = session.createQuery("FROM Permission WHERE form.path = ? AND user.id = ?").setString(0, url).setLong(1, user.getId()).list();
            session.close();

            if(permissions.size() == 0){
                Login login = new Login();
                login.redirectToErrorPage("404");
            }
        } else {
            Session session = HibernateUtil.getSession();
            tedadHazer = (Long) session.createQuery("SELECT count(*) FROM Taraddod WHERE taghvim.tarikh = ? GROUP BY personel.shomarePersoneli").setString(0, emroz).uniqueResult();
            Long col = (Long) session.createQuery("SELECT count(*) FROM Personel WHERE active = ?").setBoolean(0, true).uniqueResult();
            if(tedadHazer == null){
                tedadHazer = 0L;
            }
            tedadNahast = col - tedadHazer;
            tedadBazdid = (Long) session.createQuery("SELECT count(*) FROM LogHistory WHERE date = ? AND action = ? AND user.id = ?")
                    .setString(0, emroz)
                    .setString(1, "ورود به سامانه")
                    .setLong(2, user.getId())
                    .uniqueResult();
            tedadOnline = (Long) session.createQuery("SELECT count(*) FROM User WHERE online = ?").setBoolean(0, true).uniqueResult();
            session.close();
        }
    }

    public void logout() {
        try {
            SessionUtil sessionUtil = new SessionUtil();

            User user = sessionUtil.getPermission().getUser();
            user.setOnline(false);
            UserDao.getInstance().getBaseQuery().createOrUpdate(user);

            LogController logController = new LogController();
            logController.logLogout();
            SessionUtil.setSessionToNull();

            HibernateUtil.getSession().close();

            menu.clear();
            sideMenu.clear();
            login = false;

            FacesContext.getCurrentInstance().getExternalContext().redirect(DashboardBean.url + "dashboard.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PartSide> getMenu() {
        if(menu.size() == 0){
            menu.clear();
            menu.addAll(sideMenu);
        }
        return menu;
    }

    public void setMenu(List<PartSide> menu) {
        this.menu = menu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        IndexBean.user = user;
    }

    public Long getTedadHazer() {
        return tedadHazer;
    }

    public void setTedadHazer(Long tedadHazer) {
        this.tedadHazer = tedadHazer;
    }

    public Long getTedadNahast() {
        return tedadNahast;
    }

    public void setTedadNahast(Long tedadNahast) {
        this.tedadNahast = tedadNahast;
    }

    public Long getTedadBazdid() {
        return tedadBazdid;
    }

    public void setTedadBazdid(Long tedadBazdid) {
        this.tedadBazdid = tedadBazdid;
    }

    public Long getTedadOnline() {
        return tedadOnline;
    }

    public void setTedadOnline(Long tedadOnline) {
        this.tedadOnline = tedadOnline;
    }

    public String getEmroz() {
        return emroz;
    }

    public void setEmroz(String emroz) {
        this.emroz = emroz;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}