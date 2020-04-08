package dashboard;

import baseCode.alert.Alert;
import dashboard.model.Banner;
import dashboard.model.News;
import dataBaseModel.authentication.module.Module;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Tarikh;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class DashboardBean implements Serializable {

    private String news = "";
    private String time;
    private String date;
    private String banner = "";
    private Boolean changePass;
    //todo hengam Bargozari dar server adres avaz shavad
//    public static final String url = "http://localhost:8085/";
    public static final String url = "http://172.21.80.102:8080/sokan/";
    public static final String ENC_KEY = "i'm a programmer";
    public static final String READ_PATH = "/";
    private static Boolean copyImg = true;

    private StreamedContent file;


    public DashboardBean() {
        String alarm = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("alert");
        String successAlarm = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("successAlarm");
        this.changePass = (Boolean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("changePass");
        Alert alert;
        if (!Objects.equals(alarm, "") && alarm != null) {
            alert = new Alert();
            alert.error(alarm);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("alert", "");
        }

        if (!Objects.equals(successAlarm, "") && successAlarm != null) {
            alert = new Alert();
            alert.success(successAlarm);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("successAlarm", "");
        }

        if (this.changePass == null) {
            this.changePass = false;
        }

        Tarikh tarikh = new Tarikh();
        this.time = tarikh.porKardaneMainTime();
        this.date = tarikh.porKardaneMainDate();
        this.porKardaneNews();
        this.porKardaneBanner();
    }

    private void porKardaneNews(){
        List<Module> modules;
        List<News> newsList;

        Session session = HibernateUtil.getSession();
        modules = session.createQuery("FROM Module WHERE id != 10 ").list();
        session.close();
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;

        for (Module module : modules) {
            i++;
            stringBuilder.append("<div class=\"NEWS\">\n<span class=\"dynamic-tabs\">")
                    .append(module.getTitle()).append("</span>\n<div id='WebPart_")
                    .append(i)
                    .append("' style='width:100%'>\n");

            Session session1 = HibernateUtil.getSession();
            newsList = session1.createQuery("FROM News WHERE module.id = ? ORDER BY id DESC").setLong(0, module.getId()).setMaxResults(5).list();
            session1.close();

            if(newsList.size() > 0){
                int j = 0;
                stringBuilder.append("<div class=\"tab-list-box\">");
                for (News news : newsList) {
                    j++;
                    if(j < 3){
                        stringBuilder.append("<div class=\"tab-list-item\" style=\"border:none;\">\n<div class=\"txt-rec\">\n");
                    } else {
                        stringBuilder.append("<div class=\"tab-list-item dash\">\n<div class=\"txt-rec\">\n");
                    }
                    stringBuilder.append("<a href=\"#\" ")
                            .append("onclick=\"document.location='")
                            .append(url)
                            .append("news.xhtml?news=")
                            .append(news.getId())
                            .append("'\"")
                            .append("\">\n");
                    stringBuilder.append("<p class=\"tab-list-date\"><span dir=\"ltr\">")
                            .append(news.getInsertDate())
                            .append("</span></p>\n");
                    stringBuilder.append("<p class=\"tab-list-title\">")
                            .append(news.getTitle())
                            .append("</p>\n");
                    stringBuilder.append("<p class=\"tab-list-summary\"> ")
                            .append(news.getKholase())
                            .append(" </p>\n");
                    stringBuilder.append("</a>\n</div>\n</div>");
                }

                if(newsList.size() == 5){
                    stringBuilder.append("<div class=\"tab-list-item dash\">\n" +
                            "<div class=\"txt-rec\" style=\"float: left; margin-top: 15px;\">\n" +
                            "<a href=\"#\" onclick=\"document.location='")
                            .append(url)
                            .append("more.xhtml?id=").append(module.getId()).append("'\">\n" +
                            "<p class=\"tab-list-summary\"> بیشتر </p>\n" +
                            "</a>\n" +
                            "</div>\n" +
                            "</div>");
                }

                stringBuilder.append("</div>");
            }

            stringBuilder.append("</div>\n</div>");
        }
        news = stringBuilder.toString();
    }

    private void porKardaneBanner(){
        List<Banner> banners;

        Session session = HibernateUtil.getSession();
        banners = session.createQuery("FROM Banner ORDER BY id DESC").list();
        session.close();

        StringBuilder stringBuilder = new StringBuilder();
        if(banners.size() > 0){

            if(DashboardBean.copyImg){
                for (Banner img : banners) {
                    File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat("/../pic/banner/").concat(img.getUrl()));
                    BufferedImage image;
                    try {
                        image = ImageIO.read(file);
                        ImageIO.write(image, "jpg", new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(DashboardBean.READ_PATH).concat("/resources/images/dashboard/banner/").concat(img.getUrl())));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                DashboardBean.copyImg = false;
            }

            String root = "./resources/images/dashboard/banner/";

            stringBuilder.append("<div id=\"TopMidBar\">\n<div class=\"slider fl-left\">\n<div class=\"NEWS\">\n<div class=\"fluidHeight\" style=\"margin-top:10px;\">\n<div class=\"sliderContainer\" id=\"sliderContainer\">\n<div class=\"iosSlider\">\n<div class=\"slider\">");
            for (Banner img : banners) {
                stringBuilder.append("<div class=\"item\">\n<img width=\"920\" height=\"256\" src=\"")
                        .append(root)
                        .append(img.getUrl())
                        .append("\"/>\n</div>");
            }
            stringBuilder.append("</div>\n</div>\n\n<div class=\"slideSelectors\">\n");
            for (int i = 0; i < banners.size(); i++) {
                if(i == 0){
                    stringBuilder.append("<div class=\"&#xD;&#xA;  item selected\">&nbsp;</div>\n");
                } else {
                    stringBuilder.append("<div class=\"&#xD;&#xA;  item\">&nbsp;</div>\n");
                }
            }
            stringBuilder.append("</div>\n</div>\n</div>\n</div>\n</div>\n</div>");
        }

        banner = stringBuilder.toString();
    }


    public String getNews() {
        return news;
    }

    public void setNews(String news1) {
        this.news = news1;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Boolean getChangePass() {
        return changePass;
    }

    public void setChangePass(Boolean changePass) {
        this.changePass = changePass;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}