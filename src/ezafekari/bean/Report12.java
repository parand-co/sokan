package ezafekari.bean;

import ezafekari.EzafeKarDto;
import ezafekari.EzafeUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nedaja on 05/03/2020.
 */
@ManagedBean
@ViewScoped
public class Report12 {

    private String tarikh;
    private String shomarehMadrak;
    private String bankName;
    private String hesabBodjehe;
    private String mahVaSal;
    private String roozMozd;
    private String bazNeshasteh;
    private String karMand;
    private String darejehDar;
    private String afsar;

    public Report12() {

    }

    public void showReport(){
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\report\\made12.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap<>();
//            map.put("emzaBazresi", "سعید طاهری");
//            map.put("emzaFar", "سعید طاهری");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); // your output goes here
            exporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getShomarehMadrak() {
        return shomarehMadrak;
    }

    public void setShomarehMadrak(String shomarehMadrak) {
        this.shomarehMadrak = shomarehMadrak;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getHesabBodjehe() {
        return hesabBodjehe;
    }

    public void setHesabBodjehe(String hesabBodjehe) {
        this.hesabBodjehe = hesabBodjehe;
    }

    public String getMahVaSal() {
        return mahVaSal;
    }

    public void setMahVaSal(String mahVaSal) {
        this.mahVaSal = mahVaSal;
    }

    public String getRoozMozd() {
        return roozMozd;
    }

    public void setRoozMozd(String roozMozd) {
        this.roozMozd = roozMozd;
    }

    public String getBazNeshasteh() {
        return bazNeshasteh;
    }

    public void setBazNeshasteh(String bazNeshasteh) {
        this.bazNeshasteh = bazNeshasteh;
    }

    public String getKarMand() {
        return karMand;
    }

    public void setKarMand(String karMand) {
        this.karMand = karMand;
    }

    public String getDarejehDar() {
        return darejehDar;
    }

    public void setDarejehDar(String darejehDar) {
        this.darejehDar = darejehDar;
    }

    public String getAfsar() {
        return afsar;
    }

    public void setAfsar(String afsar) {
        this.afsar = afsar;
    }
}
