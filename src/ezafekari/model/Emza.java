package ezafekari.model;

import dataBaseModel.baseTable.Daraje;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.Semat;

public class Emza {

    private long id;
    private String code;
    private String name;
    private String neshan;
    private Daraje daraje;
    private Dayere dayere;
    private Semat semat;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeshan() {
        return neshan;
    }

    public void setNeshan(String neshan) {
        this.neshan = neshan;
    }

    public Daraje getDaraje() {
        return daraje;
    }

    public void setDaraje(Daraje daraje) {
        this.daraje = daraje;
    }

    public Semat getSemat() {
        return semat;
    }

    public void setSemat(Semat semat) {
        this.semat = semat;
    }

    public Dayere getDayere() {
        return dayere;
    }

    public void setDayere(Dayere dayere) {
        this.dayere = dayere;
    }
}
