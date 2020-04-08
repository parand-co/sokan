package util;

import baseCode.alert.Alert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PdfReport {

    private Alert alert = new Alert();

    public void pdfReportSingleTable(List list, Map map, String url){

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + url);

        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


            print(jasperReport, list, map);

        } catch (JRException e) {
            alert.error();
        }
    }

    public void withoutTable(Map map, String url){
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + url);
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            List arrayList = new ArrayList<>();
            arrayList.add("a");

            print(jasperReport, arrayList, map);

        } catch (JRException e) {
            alert.error();
        }
    }

    private void print(JasperReport jasperReport, List list, Map map){
        try {
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanCollectionDataSource(list));
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        exporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | JRException e) {
            alert.error();
        }
    }

    public void multiPage(List<JasperPrint> jasperPrints){
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); // your output goes here
            exporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            alert.error();
        }
    }
}