package dataBaseModel.authentication.permission;

import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.role.Role;
import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.NoeEstekhdam;

public class Permission {
    private long id;
    private User user;
    private Form form;
    private Boolean creat;
    private Boolean reaad;
    private Boolean updat;
    private Boolean delet;
    private Boolean Active;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Boolean getCreat() {
        return creat;
    }

    public void setCreat(Boolean creat) {
        this.creat = creat;
    }

    public Boolean getReaad() {
        return reaad;
    }

    public void setReaad(Boolean reaad) {
        this.reaad = reaad;
    }

    public Boolean getUpdat() {
        return updat;
    }

    public void setUpdat(Boolean updat) {
        this.updat = updat;
    }

    public Boolean getDelet() {
        return delet;
    }

    public void setDelet(Boolean delet) {
        this.delet = delet;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }
}


