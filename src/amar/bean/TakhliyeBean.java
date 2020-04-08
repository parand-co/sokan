package amar.bean;

import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.util.PwKaraDbUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;

@ManagedBean
@ViewScoped
public class TakhliyeBean {

    private String date = "";
    private SessionGate sessionGate = new SessionGate();
    private String rooz, noeRooz;

    public TakhliyeBean() {

    }

    public void loadRooz() {
        Taghvim taghvim = sessionGate.findTaghvimByTarikh(date);
        rooz = taghvim.getRoozHafte();
        noeRooz = taghvim.getDayType().getType();
    }

    public void load() throws SQLException {
        Taghvim taghvim = sessionGate.findTaghvimByTarikh(date);
        if (taghvim == null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, date, "تاریخ به درستی انتخاب نشده است!"));
        else {
            new PwKaraDbUtil().loadTaradodByCode(String.valueOf(taghvim.getCode()), date);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, date, "انجام شد."));
        }
    }

    public void checkFunction(){
//        new PwKaraDbUtil().loadFunction("","");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRooz() {
        return rooz;
    }

    public void setRooz(String rooz) {
        this.rooz = rooz;
    }

    public String getNoeRooz() {
        return noeRooz;
    }

    public void setNoeRooz(String noeRooz) {
        this.noeRooz = noeRooz;
    }
}
