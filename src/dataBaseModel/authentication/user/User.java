package dataBaseModel.authentication.user;

import amar.model.Personel;
import dataBaseModel.authentication.role.Role;

public class User {
    private long id;
    private Personel personel;
    private String userName;
    private String passWord;
    private Role role;
    private UserQuestion questionOne;
    private String answerOne;
    private UserQuestion questionTwo;
    private String answerTwo;
    private String lastLoginDate;
    private String parentUser;
    private Boolean active;
    private Boolean online;
    private Boolean expirePassStatus=false;
    private String IntervalDatePass;
    private Integer faild;
    private String faildTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserQuestion getQuestionOne() {
        return questionOne;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setQuestionOne(UserQuestion questionOne) {
        this.questionOne = questionOne;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public UserQuestion getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(UserQuestion questionTwo) {
        this.questionTwo = questionTwo;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getParentUser() {
        return parentUser;
    }

    public void setParentUser(String parentUser) {
        this.parentUser = parentUser;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getExpirePassStatus() {
        return expirePassStatus;
    }

    public void setExpirePassStatus(Boolean expirePassStatus) {
        this.expirePassStatus = expirePassStatus;
    }

    public String getIntervalDatePass() {
        return IntervalDatePass;
    }

    public void setIntervalDatePass(String intervalDatePass) {
        IntervalDatePass = intervalDatePass;
    }

    public Integer getFaild() {
        return faild;
    }

    public void setFaild(Integer faild) {
        this.faild = faild;
    }

    public String getFaildTime() {
        return faildTime;
    }

    public void setFaildTime(String faildTime) {
        this.faildTime = faildTime;
    }
}

