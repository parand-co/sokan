package ezafekari.bean;

import amar.model.Personel;
import dataBaseModel.SessionGate;
import dataBaseModel.util.PersianCalUtil;
import ezafekari.EzafeKarDto;
import ezafekari.EzafeUtil;
import ezafekari.model.EzafeKari;
import ezafekari.model.Sanad;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nedaja on 09/03/2020.
 */
@ManagedBean
@ViewScoped
public class FinalReportBean {

    private long sanadId;
    private List<Sanad> sanadList;
    private SessionGate sessionGate = new SessionGate();
    private long totalMablagh;
    private long totalKasrJire;
    private long totalKasrYegan;
    private long totalPardakht;
    private Sanad sanad;
    private List<EzafeKarDto> ezafeKariListDto = new ArrayList<>();
    private List<EzafeKarDto> filteredEzfDto = new ArrayList<>();
    private Set<EzafeKari> ezafeKariSet = new HashSet<>();
    private List<Personel> personelList = new ArrayList<>();

    public FinalReportBean() {
        sanadList = sessionGate.fillSanads();
    }

    public void reportExcel() {

    }

    public void reportPDF() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\report\\finalOvetime.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            int i = 1;
            for (EzafeKarDto ezafeKarDto : ezafeKariListDto) {
                ezafeKarDto.setId(String.valueOf(i));
                ezafeKarDto.setModat(EzafeUtil.splitHour(ezafeKarDto.getModat()));
                ezafeKarDto.setYegan(ezafeKarDto.getPersonel().getYegan() == null ? "" : ezafeKarDto.getPersonel().getYegan().getTitle());
                ezafeKarDto.setMakhaz(EzafeUtil.threeSplite(ezafeKarDto.getMakhaz()));
                ezafeKarDto.setMablagh(EzafeUtil.threeSplite(ezafeKarDto.getMablagh()));
                ezafeKarDto.setSumHoghoogh(EzafeUtil.threeSplite(ezafeKarDto.getSumHoghoogh()));
                ezafeKarDto.setTotalMablagh(EzafeUtil.threeSplite(ezafeKarDto.getTotalMablagh()));
                ezafeKarDto.setAccNum(ezafeKarDto.getPersonel().getShomareHesabHekmat());
                i++;
            }

            Map map = new HashMap<>();
            map.put("date", PersianCalUtil.getMountName(Integer.parseInt(sanad.getMah())) + " " + sanad.getSal());
            map.put("sumDastmozd", EzafeUtil.threeSplite(String.valueOf(totalMablagh)));
            map.put("sumKasrJire", EzafeUtil.threeSplite(String.valueOf(totalKasrJire)));
            map.put("sumKasrYegan", EzafeUtil.threeSplite(String.valueOf(totalKasrYegan)));
            map.put("sumPardakht", EzafeUtil.threeSplite(String.valueOf(totalPardakht)));
            map.put("emzaTanzim","کارمند سعید طاهری");
            map.put("emzaAmar","کارمند سعید طاهری");
            map.put("emzaPersoneli","کارمند سعید طاهری");
            map.put("emzaMali","کارمند سعید طاهری");
            map.put("emzaMomayezi","کارمند سعید طاهری");
            map.put("emzaPardakht","کارمند سعید طاهری");
            map.put("emzaDarayi","کارمند سعید طاهری");
            map.put("emzaFar","کارمند سعید طاهری");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanCollectionDataSource(ezafeKariListDto));
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
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

    public void loadOvertimes() {
        totalMablagh = 0;
        totalKasrJire = 0;
        totalKasrYegan = 0;
        totalPardakht = 0;
        ezafeKariListDto.clear();
        filteredEzfDto.clear();
        sanad = sessionGate.findSanadById(sanadId);

        ezafeKariSet = sanad.getEzafeKariSet();
//        List<EzafeKari> ezafeKaris = sessionGate.findEzafekariByTarikhBetweenFromAndTo(fromDate,toDate);
        personelList = ezafeKariSet.stream().map(EzafeKari::getPersonel).collect(Collectors.toList());
        personelList = personelList.stream().distinct().collect(Collectors.toList());
        personelList.sort(Comparator.comparing(Personel::getShomarePersoneli));

        for (Personel personel : personelList) {
            List<EzafeKari> ezafeKariListForPers = ezafeKariSet.stream().filter(x -> x.getPersonel().equals(personel)).collect(Collectors.toList());
            EzafeKarDto ezafeKar = new EzafeKarDto();
            ezafeKar.setPersonel(personel);
            ezafeKar.setName(personel.getName());
            ezafeKar.setNeshan(personel.getNeshan());
            ezafeKar.setPersCode(personel.getShomarePersoneli());
            ezafeKar.setBakhsh(personel.getBakhsh() == null ? "" : personel.getBakhsh().getTitle());
            ezafeKar.setDayere(personel.getDayere() == null ? "" : personel.getDayere().getTitle());
            ezafeKar.setMakhaz(EzafeUtil.calcMakhaz(personel));
            ezafeKar.setModat(String.valueOf(EzafeUtil.calcEzafekarModat(personel, ezafeKariListForPers)));
            ezafeKar.setMablagh(String.valueOf(EzafeUtil.calcMablaghByModatAndMakhaz(ezafeKar)));
            totalMablagh += Long.parseLong(ezafeKar.getMablagh());
            ezafeKar.setTotalMablagh(calcFinalPayment(ezafeKar));
            ezafeKar.setAccNum(personel.getShomareHesabHekmat());
            ezafeKar.setSumHoghoogh(String.valueOf(personel.getHagheShaghel()+personel.getHaghShoghl()+personel.getFogholadeModiriyat()));
            ezafeKar.setKasrJire(EzafeUtil.calcMablaghJire(personel,sanad));
            totalKasrJire += Long.parseLong(ezafeKar.getKasrJire());
            ezafeKar.setKasrYegan(EzafeUtil.calcKasrYegani(personel,sanad));
            totalKasrYegan += Long.parseLong(ezafeKar.getKasrYegan());
            ezafeKariListDto.add(ezafeKar);
        }
        filteredEzfDto.addAll(ezafeKariListDto);
    }

    public String calcFinalPayment(EzafeKarDto ezafeKar) {
        long a = (long) (Long.parseLong(ezafeKar.getMablagh())) - Long.parseLong(EzafeUtil.calcMablaghJire(ezafeKar.getPersonel(),sanad)) - Long.parseLong(EzafeUtil.calcKasrYegani(ezafeKar.getPersonel(),sanad));
        if (a > ezafeKar.getPersonel().getSaghfeEzafeKar())  //saghf check mishe
            a = ezafeKar.getPersonel().getSaghfeEzafeKar();
        if (a < 0)  //manfi khord 0 mishe
            a = 0;
        totalPardakht += a;
        return String.valueOf(a);
    }

    public Set<EzafeKari> getEzafeKariSet() {
        return ezafeKariSet;
    }

    public void setEzafeKariSet(Set<EzafeKari> ezafeKariSet) {
        this.ezafeKariSet = ezafeKariSet;
    }

    public List<Personel> getPersonelList() {
        return personelList;
    }

    public void setPersonelList(List<Personel> personelList) {
        this.personelList = personelList;
    }

    public Sanad getSanad() {
        return sanad;
    }

    public void setSanad(Sanad sanad) {
        this.sanad = sanad;
    }

    public long getSanadId() {
        return sanadId;
    }

    public void setSanadId(long sanadId) {
        this.sanadId = sanadId;
    }

    public List<Sanad> getSanadList() {
        return sanadList;
    }

    public void setSanadList(List<Sanad> sanadList) {
        this.sanadList = sanadList;
    }

    public long getTotalMablagh() {
        return totalMablagh;
    }

    public void setTotalMablagh(long totalMablagh) {
        this.totalMablagh = totalMablagh;
    }

    public List<EzafeKarDto> getEzafeKariListDto() {
        return ezafeKariListDto;
    }

    public void setEzafeKariListDto(List<EzafeKarDto> ezafeKariListDto) {
        this.ezafeKariListDto = ezafeKariListDto;
    }

    public List<EzafeKarDto> getFilteredEzfDto() {
        return filteredEzfDto;
    }

    public void setFilteredEzfDto(List<EzafeKarDto> filteredEzfDto) {
        this.filteredEzfDto = filteredEzfDto;
    }
}
