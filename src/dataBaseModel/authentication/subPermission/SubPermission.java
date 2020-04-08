package dataBaseModel.authentication.subPermission;

import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.NoeEstekhdam;

public class SubPermission {

    private long id;
    private Permission permission;
    private NoeEstekhdam noePersonel;
    private Dayere dayere;
    private Bakhsh bakhsh;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
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
}