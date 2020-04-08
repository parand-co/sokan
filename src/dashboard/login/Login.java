package dashboard.login;

import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.ULocale;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import dashboard.DashboardBean;
import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.module.Module;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.user.User;
import dataBaseModel.authentication.user.UserDao;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.manager.LogController;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import manage.bean.IndexBean;
import manage.model.PartSide;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.context.RequestContext;
import org.primefaces.model.ByteArrayContent;
import util.Password;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.*;

@ManagedBean
@ViewScoped
public class Login implements Serializable {

    private Alert alert = new Alert();

    private String username;
    private String password;

    private User user;
    private Integer wrongPassword;
    private Boolean captchaFrame;
    private String captchaInput = "";
    private LogController logController;
    private String zirSamaneCode;

    private ByteArrayContent captcha;
    private String captchaWord = "";

    private ULocale locale = new ULocale("en", "IR");
    private String timestamp;

    private String alarm = "";
    private Boolean changePass = false;
    private Boolean alarmShow = true;

    public Login() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss", locale);
        Calendar calendar = Calendar.getInstance();
        timestamp = simpleDateFormat.format(calendar.getTime());

        createCaptcha();
        captchaFrame = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("captchaFrame");
        if(captchaFrame == null){
            captchaFrame = false;
        }
        wrongPassword = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("wrongPassword");
        if(wrongPassword == null){
            wrongPassword = 0;
        }
    }

    public void createCaptcha() {
        try {
            int[] a = {22, 25};
            int[] b = {50, 100};
            int[] c = {50, 220};
            RandomRangeColorGenerator x = new RandomRangeColorGenerator(a, b, c);
            TextPaster textPaster = new RandomTextPaster(6, 6, x, Boolean.TRUE);
            Font[] fonts = {new Font("Arial", Font.BOLD, 12), new Font("Verdena", Font.PLAIN, 10), new Font("TimesNewRoman", Font.BOLD, 9)};
            FontGenerator fontGenerator = new RandomFontGenerator(20, 35, fonts);
            BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(250, 50, Color.WHITE, Color.GRAY);
            WordToImage wordToImage = new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
            RandomWordGenerator randomWordGenerator = new RandomWordGenerator("abcdefghjkLmnopqrstuvwxyz0123456789ABCDEFGHJKMNOPQRSTUVWXYZ");
            captchaWord = randomWordGenerator.getWord(6);
            BufferedImage image = wordToImage.getImage(captchaWord);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            captcha = new ByteArrayContent(bytes, "image/jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login() {
        if (wrongPassword >= 3) {
            if (captchaWord.equalsIgnoreCase(captchaInput)) {
                loginaction();
            } else {
                alert.wrongCaptcha();
            }
        } else {
            loginaction();
            captchaFrame = wrongPassword >= 3;
            if(alarmShow){
                try {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("alert", alarm);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("captchaFrame", captchaFrame);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("wrongPassword", wrongPassword);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("changePass", changePass);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./dashboard.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loginaction() {

        List<Permission> permissionList = new ArrayList<>();

        if (username != null && !Objects.equals(username, "") && password != null && !Objects.equals(password, "")) {
            if (UsernameChecker(username)) {
                if (PasswordChecker(username, password)) {
                    if (checkExpireUser(user)) {
                        if (user.getFaild() != null && user.getFaild() > 4 && !chekFieldTime(user)) {
                            alarm = "بعلت وارد نمودن رمز عبور نامعتبر به دفعات، نام کاربری شما بمدت 30 دقیقه غیرفعال شده است";
                            logController = new LogController("login.xhtml");
                            logController.logFail();
                        } else {
                            boolean singin = true;
                            if (user.getOnline() != null) {
                                if (user.getOnline()) {
                                    String[] strings = timestamp.split(";");
                                    String date = strings[0];
                                    String time = strings[1];

                                    Session session = HibernateUtil.getSession();
                                    List<LogHistory> logHistories = session.createQuery
                                            ("FROM LogHistory WHERE user.id = ? AND date = ? ORDER BY id DESC")
                                            .setLong(0, user.getId())
                                            .setString(1, date)
                                            .setMaxResults(1)
                                            .list();
                                    session.close();

                                    for (LogHistory history : logHistories) {
                                        String saat = time.substring(0, 2);
                                        String daghighe = time.substring(3, 5);

                                        String saateGhabli = history.getTime().substring(0, 2);
                                        String daghigheGhabli = history.getTime().substring(3, 5);

                                        if ((Integer.valueOf(saat) - Integer.valueOf(saateGhabli)) < 2) {
                                            if ((Integer.valueOf(saat) - Integer.valueOf(saateGhabli)) == 0) {
                                                if ((Integer.valueOf(daghighe) - Integer.valueOf(daghigheGhabli)) < 20) {
                                                    singin = false;
                                                }
                                            } else {
                                                if ((Integer.valueOf(daghighe) - Integer.valueOf(daghigheGhabli)) < 0) {
                                                    if (((Integer.valueOf(daghighe) + 60) - Integer.valueOf(daghigheGhabli)) < 20) {
                                                        singin = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (singin) {
                                boolean isAdmin = false;

                                if (user != null) {
                                    Session session1 = HibernateUtil.getSession();
                                    permissionList = session1.createQuery("FROM Permission where user.id = ? ORDER By form.module.id, form.id")
                                            .setLong(0, user.getId())
                                            .list();
                                    session1.close();
                                    setList(permissionList);

                                    if(user.getRole() != null) {
                                        if (user.getRole().getCode().equals("100")) {
                                            isAdmin = true;
                                        }
                                    }
                                }
                                for (Permission p : permissionList) {
                                    if (p.getForm().getModule() != null) {
                                        zirSamaneCode = p.getForm().getModule().getCode();
                                    }
                                }

                                if (!isAdmin) {
                                    if ((user != null ? user.getActive() : null) != null) {
                                        if (user.getActive()) {
                                            if (checkExpireUser(user)) {
                                                successLogin(user, timestamp);

                                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("captchaFrame", false);
                                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("wrongPassword", 0);
                                                logController = new LogController("login.xhtml");
                                                logController.logLogin();
                                                redirectToZirSamane();
                                            } else {
                                                wrongPassword++;
                                                logController = new LogController("login.xhtml");
                                                logController.logFail();
                                            }
                                        } else {
                                            alarm = "نام کاربری غیرفعال می باشد، با راهبر سامانه تماس بگیرید";
                                            wrongPassword++;
                                            logController = new LogController("login.xhtml");
                                            logController.logFail();
                                        }
                                    }
                                } else {
                                    successLogin(user, timestamp);
                                    logController = new LogController("login.xhtml");
                                    logController.logLogin(zirSamaneCode);
                                    redirectToZirSamane();
                                }
                            } else {
                                wrongPassword++;
                                alarm = "کاربر درحال حاضر فعال می باشد";
                                logController = new LogController("login.xhtml");
                                logController.logFail();
                            }
                        }
                    } else {
                        wrongPassword++;
                        logController = new LogController("login.xhtml");
                        logController.logFail();
                    }
                } else {
                    plussFaild(user);
                    wrongPassword++;
                    alarm = "نام کاربری یا کلمه عبور اشتباه است";
                    logController = new LogController("login.xhtml");
                    logController.logFail();
                }
            } else {
                wrongPassword++;
                alarm = "نام کاربری یا کلمه عبور اشتباه است";
                logController = new LogController("login.xhtml");
                logController.logFail();
            }
        } else {
            wrongPassword++;
            alarm = "نام کاربری و کلمه عبور را وراد نمایید";
            logController = new LogController("login.xhtml");
            logController.logFail();
        }
    }

    private void setList(List<Permission> permissions){
        IndexBean.sideMenu.clear();
        List<Form> forms = new ArrayList<>();
        String code = null;
        if(permissions.size() > 0) {
            code = permissions.get(0).getForm().getModule().getCode();
        }

        PartSide partSide = new PartSide();
        for (Permission permission : permissions) {

            if(!code.equals(permission.getForm().getModule().getCode())){
                partSide.setForms(forms);
                IndexBean.sideMenu.add(partSide);
                forms = new ArrayList<>();
                partSide = new PartSide();
                code = permission.getForm().getModule().getCode();
            }

            partSide.setCode(permission.getForm().getModule().getCode());
            partSide.setTitle(permission.getForm().getModule().getTitle());
            partSide.setIcon(permission.getForm().getModule().getIcon());
            forms.add(permission.getForm());
        }

        if(forms.size() > 0){
            Session session = HibernateUtil.getSession();
            List<Module> modules = session.createQuery("FROM Module WHERE id = 10").list();
            session.close();

            if(partSide.getCode().equals("10")){
                Form form = new Form();
                form.setId(35);
                form.setCode("35");
                form.setModule(modules.get(0));
                form.setTitle("تغییر کلمه عبور");
                form.setActive(true);
                form.setPath("../manage/changePassword.xhtml");
                forms.add(form);

                partSide.setForms(forms);
                IndexBean.sideMenu.add(partSide);
            } else {
                partSide.setForms(forms);
                IndexBean.sideMenu.add(partSide);

                forms = new ArrayList<>();
                partSide = new PartSide();

                partSide.setCode(modules.get(0).getCode());
                partSide.setTitle(modules.get(0).getTitle());
                partSide.setIcon(modules.get(0).getIcon());

                Form form = new Form();
                form.setId(35);
                form.setCode("35");
                form.setModule(modules.get(0));
                form.setTitle("تغییر کلمه عبور");
                form.setActive(true);
                form.setPath("../manage/changePassword.xhtml");
                forms.add(form);

                partSide.setForms(forms);
                IndexBean.sideMenu.add(partSide);
            }
        }
    }

    private static void successLogin(User user, String timestamp) {
        Alert alert = new Alert();
        alert.loginSuccess(user.getPersonel().getNeshan());
        SessionUtil.createSession(user);
        user.setLastLoginDate(timestamp);
        user.setOnline(true);
        user.setFaild(0);
        UserDao.getInstance().getBaseQuery().createOrUpdate(user);
    }

    private Boolean UsernameChecker(String username) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User u WHERE u.userName = ?");
        query.setString(0, username);
        List list = query.list();
        session.close();
        if (list.size() == 1) {
            user = (User) list.get(0);
        }
        return list.size() == 1;
    }

    private Boolean PasswordChecker(String username, String password) {
        return Objects.equals(user.getUserName(), username) && Objects.equals(user.getPassWord(), BCrypt.hashpw(password, Password.saltBcrypt));
    }

    private String dateAndTime() {
        ULocale locale = new ULocale("en", "IR");
        SimpleDateFormat simpleDateFormatLastLogin = new SimpleDateFormat("HH:mm", locale);
        com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance();
        return simpleDateFormatLastLogin.format(calendar.getTime());
    }

    private void plussFaild(User uusername) {
        String timestamp = dateAndTime();
        if (uusername.getFaild() != null) {
            uusername.setFaild(uusername.getFaild() + 1);
        } else {
            uusername.setFaild(1);
        }
        uusername.setFaildTime(timestamp);
        UserDao.getInstance().getBaseQuery().createOrUpdate(uusername);
    }

    private Boolean chekFieldTime(User user) {
        String timestamp = dateAndTime();

        String[] now = timestamp.split(":");
        Integer hourNow = Integer.valueOf(now[0]);
        Integer minuteNow = Integer.valueOf(now[1]);

        String[] last = user.getFaildTime().split(":");
        Integer hourLast = Integer.valueOf(last[0]);
        Integer minuteLast = Integer.valueOf(last[1]);

        if ((hourLast - hourNow) < 0) {
            return true;
        }
        if ((hourLast - hourNow) > 1) {
            return (minuteLast - minuteNow) >= 1;
        } else {
            return (minuteLast - minuteNow) >= 30;
        }
    }

    private boolean checkExpireUser(User user) {
//        SimpleDateFormat simpleDateFormatLastLogin = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss", locale);
//        Calendar calendar = Calendar.getInstance();
//        String timestamp = simpleDateFormatLastLogin.format(calendar.getTime());

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
            Date today = com.ibm.icu.util.Calendar.getInstance(locale).getTime();
            String LastLoginDate, IntervalDatePass;

            if (user.getLastLoginDate() != null && !user.getLastLoginDate().equals("")) {
                LastLoginDate = user.getLastLoginDate().split(";")[0];
                IntervalDatePass = user.getIntervalDatePass().split(";")[0];

                Date intervalDatePass = simpleDateFormat.parse(IntervalDatePass);

                long DateDiff;
                if ((today.getTime() - intervalDatePass.getTime()) > 0) {
                    DateDiff = (today.getTime() - intervalDatePass.getTime()) / (1000 * 60 * 60 * 24);

                    if (Math.toIntExact(DateDiff) > 23 && Math.toIntExact(DateDiff) < 60) {
                        alarm = "از زمان ورود قبلی شما 23 روز سپری گردیده لطفا مجددا نسبت به تغییر رمز خویش اقدام و مجددا وارد سامانه گردید";
                        changePass = true;
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("PF('MessageRedirectDialog').show();");
                        return false;
                    }
                    else if (Math.toIntExact(DateDiff) >= 60) {
                        return alertexpirePassword(user, "با توجه به سپری شدن 60 روز از تغییر رمز عبور، رمز فعلی منقضی گردیده است لطفا مجددا نسبت به تغییر رمز خویش اقدام و مجددا وارد سامانه گردید");
                    }
                }
            } else {//اولین ورود کاربر
                LastLoginDate = timestamp;
                user.setIntervalDatePass(timestamp);
            }

            Date lastLoginDate = simpleDateFormat.parse(LastLoginDate);
            long DateDiff;
            if ((today.getTime() - lastLoginDate.getTime()) > 0) {
                DateDiff = (today.getTime() - lastLoginDate.getTime()) / (1000 * 60 * 60 * 24);

                if (Math.toIntExact(DateDiff) > 23 && Math.toIntExact(DateDiff) < 60) {
                    return alertexpirePassword(user, "از زمان ورود قبلی شما 23 روز سپری گردیده لطفا مجددا نسبت به تغییر رمز خویش اقدام و مجددا وارد سامانه گردید");

                } else if (Math.toIntExact(DateDiff) >= 60) {
                    user.setActive(false);

                    UserDao.getInstance().getBaseQuery().createOrUpdate(user);
                    alarm = "با توجه به سپری شدن 60 از زمان ورود قبلی شما، لذا کاربر شما غیر فعال گردیده است با راهبر مربوطه تماس حاصل فرمایید";
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('MessageRedirectDialog').show();");

                    return false;
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean alertexpirePassword(User user, String s) {
        user.setExpirePassStatus(true);
        SessionUtil.createSession(user);
        UserDao.getInstance().getBaseQuery().createOrUpdate(user);
        alarm = s;
        changePass = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('MessageRedirectDialog').show();");

        return false;
    }

    private void redirectToZirSamane() {
        try {
            alarmShow = false;
            IndexBean.login = true;
            FacesContext.getCurrentInstance().getExternalContext().redirect(DashboardBean.url + "index/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToErrorPage(String code) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(DashboardBean.url + "error/404.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getCaptchaFrame() {
        return captchaFrame;
    }

    public void setCaptchaFrame(Boolean captchaFrame) {
        this.captchaFrame = captchaFrame;
    }

    public String getCaptchaInput() {
        return captchaInput;
    }

    public void setCaptchaInput(String captchaInput) {
        this.captchaInput = captchaInput;
    }

    public ByteArrayContent getCaptcha() {
        return captcha;
    }

    public void setCaptcha(ByteArrayContent captcha) {
        this.captcha = captcha;
    }
}