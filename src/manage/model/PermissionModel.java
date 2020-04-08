package manage.model;

import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.NoeEstekhdam;

public class PermissionModel {

    private long id;
    private long permissionId;
    private User user;
    private Form form;
    private Boolean creat;
    private Boolean reaad;
    private Boolean updat;
    private Boolean delet;
    private NoeEstekhdam noePersonel;
    private Dayere dayere;
    private Bakhsh bakhsh;
    private Boolean Active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
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

    public NoeEstekhdam getNoePersonel() {
        return noePersonel;
    }

    public void setNoePersonel(NoeEstekhdam noePersonel) {
        this.noePersonel = noePersonel;
    }

    public Dayere getDayere() {
        return dayere;
    }

    public void setDayere(Dayere dayere) {
        this.dayere = dayere;
    }

    public Bakhsh getBakhsh() {
        return bakhsh;
    }

    public void setBakhsh(Bakhsh bakhsh) {
        this.bakhsh = bakhsh;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }
}