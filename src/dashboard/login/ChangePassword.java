package dashboard.login;

import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dashboard.DashboardBean;
import dataBaseModel.authentication.user.User;
import dataBaseModel.authentication.user.UserDao;
import dataBaseModel.authentication.user.UserQuestion;
import dataBaseModel.util.HibernateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import util.Password;
import util.RegularExpression;
import util.Validate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class ChangePassword {

    private String username = "";
    private String answer1 = "";
    private String answer2 = "";
    private String questionOne = "";
    private String questionTwo = "";

    private String oldPassword;
    private String password1;
    private String password2;

    private List<UserQuestion> userQuestions1;
    private List<UserQuestion> userQuestions2;
    List<User> userList = new ArrayList<>();

    private Alert alert = new Alert();


    public ChangePassword() {
        Session session = HibernateUtil.getSession();
        userQuestions1 = session.createQuery("FROM UserQuestion where code % 2 = 0").list();
        userQuestions2 = session.createQuery("FROM UserQuestion where code % 2 != 0 ").list();
        session.close();
    }

    public void proccess(){
        Session session = HibernateUtil.getSession();
        List<User> userList = session.createQuery("FROM User WHERE username = '" + username + "'").list();
        session.close();

        if(userList.size() == 1){
            User user = userList.get(0);
            if(!Objects.equals(answer1, "") && !Objects.equals(answer2, "") &&
                    !Objects.equals(questionOne, "") && !Objects.equals(questionTwo, "")){
                if(user.getQuestionOne().getCode().equals(questionOne) &&
                        user.getQuestionTwo().getCode().equals(questionTwo) &&
                        user.getAnswerOne().equals(answer1) &&
                        user.getAnswerTwo().equals(answer2)){
                    if(Objects.equals(password1, password2)){
                        if(new Validate().validatePassword(password1)){
                            Password password = new Password();
                            user.setPassWord(password.hashhPassword(password1));
                            UserDao.getInstance().getBaseQuery().createOrUpdate(user);
                            alert.successRecoveryPassword(username);
                        } else {
                            alert.faildValidPassword();
                        }
                    } else {
                        alert.notSetPassword();
                    }
                } else {
                    alert.wrongAnswer();
                }
            } else {
                alert.selectQuestion();
            }
        } else {
            alert.wrongUser();
        }
    }

    public void changePassword(){
        Session session = HibernateUtil.getSession();
        userList = session.createQuery("FROM User WHERE userName = '" + username + "'").list();
        session.close();

        if(userList.size() == 1){
            User user = userList.get(0);
            if (passwordChecker(user, username, oldPassword)) {
                if(!password1.equals(oldPassword)){
                    if (password1.length() >= 8) {
                        if (new RegularExpression().validatePassword(password1)) {
                            if(password1.equals(password2)){
                                ULocale locale = new ULocale("en", "IR");
                                SimpleDateFormat simpleDateFormatLastLogin = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss", locale);
                                Calendar calendar = Calendar.getInstance();
                                String timestamp = simpleDateFormatLastLogin.format(calendar.getTime());

                                String pass = BCrypt.hashpw(password1, Password.saltBcrypt);

                                user.setPassWord(pass);
                                user.setExpirePassStatus(false);
                                user.setIntervalDatePass(timestamp);
                                user.setLastLoginDate(timestamp);

                                UserDao.getInstance().getBaseQuery().createOrUpdate(user);

                                try {
                                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("changePass", false);
                                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("successAlarm", "کلمه عبور با موفقیت تغییر یافت");
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(DashboardBean.url + "dashboard.xhtml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                alert.warning("کلمه های عبور تطابق ندارند");
                            }
                        } else {
                            alert.warning("لطفا از ترکیب حروف بزرگ، کوچک، عدد و علائم استفاده کنید");
                        }
                    } else {
                        alert.warning("کلمه عبور می‌بایست حداقل هشت رقمی باشد");
                    }
                } else {
                    alert.warning("کلمه عبور جدید می بایست نسبت به کلمه عبور قدیم متفاوت باشد.");
                }
            } else {
                alert.warning("کلمه عبور اشتباه است");
            }
        } else {
            alert.wrongUser();
        }
    }

    private Boolean passwordChecker(User user, String username, String password) {
        return Objects.equals(user.getUserName(), username) && Objects.equals(user.getPassWord(), BCrypt.hashpw(password, Password.saltBcrypt));
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(String questionOne) {
        this.questionOne = questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(String questionTwo) {
        this.questionTwo = questionTwo;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public List<UserQuestion> getUserQuestions1() {
        return userQuestions1;
    }

    public void setUserQuestions1(List<UserQuestion> userQuestions1) {
        this.userQuestions1 = userQuestions1;
    }

    public List<UserQuestion> getUserQuestions2() {
        return userQuestions2;
    }

    public void setUserQuestions2(List<UserQuestion> userQuestions2) {
        this.userQuestions2 = userQuestions2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}