package manage.bean;

import baseCode.alert.Alert;
import dataBaseModel.authentication.user.User;
import dataBaseModel.authentication.user.UserDao;
import dataBaseModel.authentication.user.UserQuestion;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;
import util.Password;
import util.Validate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ChangePasswordBean implements Serializable {
    private String URL;

    private List<UserQuestion> userQuestions1;
    private List<UserQuestion> userQuestions2;

    private String username = "";
    private String answer1 = "";
    private String answer2 = "";
    private String questionOne = "";
    private String questionTwo = "";

    private String oldPassWord;
    private String password1;
    private String password2;

    private Alert alert = new Alert();


    public ChangePasswordBean() {
        URL = IndexBean.url;

        fillList();
    }

    private void fillList(){
        Session session = HibernateUtil.getSession();
        userQuestions1 = session.createQuery("FROM UserQuestion where code % 2 = 0").list();
        userQuestions2 = session.createQuery("FROM UserQuestion where code % 2 != 0 ").list();
        session.close();

        if(IndexBean.user != null){
            if(IndexBean.user.getQuestionOne() != null) {
                questionOne = IndexBean.user.getQuestionOne().getCode();
                answer1 = IndexBean.user.getAnswerOne();
            }

            if(IndexBean.user.getQuestionTwo() != null) {
                questionTwo = IndexBean.user.getQuestionTwo().getCode();
                answer2 = IndexBean.user.getAnswerTwo();
            }
        }
    }

    public void save(){
        if(IndexBean.user != null){
            if(checkUserPass(oldPassWord)){
                if(password1 != null && !password1.equals("") && password2 != null && !password2.equals("") && questionOne != null &&
                        !questionOne.equals("") && answer1 != null && !answer1.equals("") && questionTwo != null &&
                        !questionTwo.equals("") && answer2 != null && !answer2.equals("")){
                    if(password1.equals(password2)){
                        if(new Validate().validatePassword(password1)){
                            Password password = new Password();
                            User user = IndexBean.user;
                            user.setPassWord(password.hashhPassword(password1));
                            user.setQuestionOne(userQuestions1.stream().filter(o -> o.getCode().equals(questionOne)).findFirst().orElse(null));
                            user.setAnswerOne(answer1);
                            user.setQuestionTwo(userQuestions2.stream().filter(o -> o.getCode().equals(questionTwo)).findFirst().orElse(null));
                            user.setAnswerTwo(answer2);
                            UserDao.getInstance().getBaseQuery().createOrUpdate(user, URL);

                            alert.successEdit();
                        } else {
                            alert.fieldValidPass();
                        }
                    } else {
                        alert.fieldEqualPass();
                    }
                } else {
                    alert.fieldIsEmpty("تمامی فیلدها");
                }
            } else {
                alert.fieldPass();
            }
        } else {
            alert.notFoundPersonel();
        }
    }

    private boolean checkUserPass(String pass){
        Password password = new Password();
        return password.hashhPassword(pass).equals(IndexBean.user.getPassWord());
    }

    // GETTER AND SETTER

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

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
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
}
