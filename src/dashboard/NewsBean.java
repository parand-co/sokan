package dashboard;

import dashboard.model.News;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;
import util.Tarikh;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
public class NewsBean extends HttpServlet implements Serializable {

    private String paramName = "news";

    private String newsTitr = "";
    private String time = "";
    private String date = "";
    private String insertDate = "";
    private String title = "";
    private String kholase = "";
    private String matn = "";

    public NewsBean() {
        Tarikh tarikh = new Tarikh();
        time = tarikh.porKardaneMainTime();
        date = tarikh.porKardaneMainDate();

        if(FacesContext.getCurrentInstance().getExternalContext().getRequest() != null){
            handleRequest((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        } else {
            newsTitr = "....";
            title = "مطلبی یافت نشد...";
        }
    }

    private void handleRequest(HttpServletRequest req) {
        String paramValue = req.getParameter(paramName);
        fillData(Integer.valueOf(paramValue));
    }

    private void fillData(Integer id){
        Session session = HibernateUtil.getSession();
        List<News> newss = session.createQuery("FROM News WHERE id = ?").setInteger(0, id).list();
        session.close();

        if(newss.size() == 1){
            newsTitr = newss.get(0).getTitle();
            title = newss.get(0).getTitle() + " : ";
            insertDate = newss.get(0).getInsertDate();
            matn =newss.get(0).getMatn();
            kholase = newss.get(0).getKholase();
        } else {
            newsTitr = "....";
            title = "مطلبی یافت نشد...";
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMatn() {
        return matn;
    }

    public void setMatn(String matn) {
        this.matn = matn;
    }

    public String getNewsTitr() {
        return newsTitr;
    }

    public void setNewsTitr(String newsTitr) {
        this.newsTitr = newsTitr;
    }

    public String getKholase() {
        return kholase;
    }

    public void setKholase(String kholase) {
        this.kholase = kholase;
    }
}