package dashboard.model;

import dataBaseModel.authentication.module.Module;

public class News {

    private Integer id;
    private String title;
    private String kholase;
    private String matn;
    private String insertDate;
    private Module module;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKholase() {
        return kholase;
    }

    public void setKholase(String kholase) {
        this.kholase = kholase;
    }

    public String getMatn() {
        return matn;
    }

    public void setMatn(String matn) {
        this.matn = matn;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
