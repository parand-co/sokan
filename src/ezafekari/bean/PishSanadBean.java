package ezafekari.bean;

import amar.model.Personel;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.EzafatoKosooratDao;
import dataBaseModel.dao.EzafekariDao;
import dataBaseModel.dao.PersonelDao;
import dataBaseModel.dao.SanadDao;
import ezafekari.EzafeKarDto;
import ezafekari.EzafeUtil;
import ezafekari.model.EzafatoKosoorat;
import ezafekari.model.EzafeKari;
import ezafekari.model.Sanad;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.primefaces.context.RequestContext;
import util.PropertiesLoader;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by nedaja on 15/02/2020.
 */
@ManagedBean
@ViewScoped
public class PishSanadBean {

    private long sanadId;
    private Sanad sanad;
    private List<Sanad> sanadList = new ArrayList<>();
    private SessionGate sessionGate = new SessionGate();

    private List<EzafeKarDto> ezafeKariListDto = new ArrayList<>();
    private List<EzafeKarDto> filteredEzfDto = new ArrayList<>();
    private Personel personelDetailLoad = new Personel();
    private Personel selectedPersonel = new Personel();
    private List<EzafeKari> ezafeKariListLoad = new ArrayList<>();
    private Set<EzafeKari> ezafeKariSet = new HashSet<>();
    private EzafatoKosoorat ezafatoKosooratLoad = new EzafatoKosoorat();
    private EzafeKarDto ezafeKariLoaded = new EzafeKarDto();
    private List<Personel> selectedPersonels = new ArrayList<>();
    private List<Personel> personelList = new ArrayList<>();
    private List<EzafeKari> ezafeKariListTemp = new ArrayList<>();
    private int rows = 20;
    private long darajeShoghlID, madrakID;
    private List<Daraje> darajeList = new ArrayList<>();
    private List<Madrak> madrakList = new ArrayList<>();
    private List<Yegan> yeganList = new ArrayList<>();
    private List<Paygah> paygahList = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdamList = new ArrayList<>();
    private long newModat, newMablagh;
    private Personel newPersonel = new Personel(new Daraje(), new Yegan(), new NoeEstekhdam(), null);
    private long totalMablagh = 0;
    private int financeModalOpenFromLocation;
    private int ezafeKasrAmount;
    private String ezafeKasrLable, ezafeKasrSharh;
    private int hourFilter;

    public PishSanadBean() {
        sanad = new Sanad();
        sanadList = sessionGate.fillSanads();
    }

    public void loadOvertimes() {
        totalMablagh = 0;
        ezafeKariListDto.clear();
        filteredEzfDto.clear();
        sanad = sessionGate.findSanadById(sanadId);
        darajeList = sessionGate.fillDaraje();
        madrakList = sessionGate.fillMadrak();


        ezafeKariSet = sanad.getEzafeKariSet();
//        List<EzafeKari> ezafeKaris = sessionGate.findEzafekariByTarikhBetweenFromAndTo(fromDate,toDate);
        personelList = ezafeKariSet.stream().map(EzafeKari::getPersonel).collect(Collectors.toList());
        personelList = personelList.stream().distinct().collect(Collectors.toList());
        personelList.sort(Comparator.comparing(Personel::getShomarePersoneli));

        for (Personel personel : personelList) {
            List<EzafeKari> ezafeKariListForPers = ezafeKariSet.stream().filter(x -> x.getPersonel().equals(personel)).collect(Collectors.toList());
            EzafeKarDto ezafeKar = new EzafeKarDto();
            ezafeKar.setPersonel(personel);
            ezafeKar.setPersCode(personel.getShomarePersoneli());
            ezafeKar.setPersName(personel.getName() + " " + personel.getNeshan());
            ezafeKar.setBakhsh(personel.getBakhsh() == null ? "" : personel.getBakhsh().getTitle());
            ezafeKar.setDayere(personel.getDayere() == null ? "" : personel.getDayere().getTitle());
            ezafeKar.setMakhaz(EzafeUtil.calcMakhaz(personel));
            ezafeKar.setModat(String.valueOf(EzafeUtil.calcEzafekarModat(personel, ezafeKariListForPers)));
            ezafeKar.setMablagh(String.valueOf(EzafeUtil.calcMablaghByModatAndMakhaz(ezafeKar)));
            ezafeKar.setTotalMablagh(calcFinalPayment(ezafeKar));
            ezafeKar.setAccNum(personel.getShomareHesabHekmat());
            ezafeKariListDto.add(ezafeKar);
        }
        filteredEzfDto.addAll(ezafeKariListDto);
    }

    public void openSanadOption() {
        sanad = sessionGate.findSanadById(sanadId);
        RequestContext.getCurrentInstance().execute("$('#sanadOption').modal('show')");
    }

    public void saveOption() {
        SanadDao.getInstance().createOrUpdate(sanad);
        RequestContext.getCurrentInstance().execute("$('#sanadOption').modal('hide')");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "انجام شد.", "برای مشاهده تغییرات دوباره اطلاعات سند را بارگذاری نمایید."));
    }

    public void doFilter() {
        ezafeKariListDto = ezafeKariListDto.stream().filter(x -> Integer.parseInt(x.getModat()) > hourFilter).collect(Collectors.toList());
        filteredEzfDto.clear();
        filteredEzfDto.addAll(ezafeKariListDto);
    }

    public void saveEzfKsr() {
        ezafeKasrAmount = EzafeUtil.roundSaat(ezafeKasrAmount);
        EzafatoKosoorat ezafatoKosoorat = sessionGate.findEzafeKasrBySanadAndPers(personelDetailLoad, sanadId);
        if (ezafatoKosoorat == null) {
            if (ezafeKasrAmount != 0) {
                EzafatoKosoorat newEzfKsr = new EzafatoKosoorat();
                if (ezafeKasrLable.matches("اضافه"))
                    newEzfKsr.setEzafOrKasr(true);
                else
                    newEzfKsr.setEzafOrKasr(false);
                newEzfKsr.setSaatEK(ezafeKasrAmount);
                newEzfKsr.setSharh(ezafeKasrSharh);
                newEzfKsr.setSanad(sanad);
                newEzfKsr.setPersonel(personelDetailLoad);
                EzafatoKosooratDao.getInstance().create(newEzfKsr);
            }
        } else {
            if (ezafatoKosoorat.getSaatEK() * 100 != ezafeKasrAmount) {
                if (ezafeKasrAmount == 0) {
                    EzafatoKosooratDao.getInstance().delete(ezafatoKosoorat);
                } else {
                    ezafatoKosoorat.setSharh(ezafeKasrSharh);
                    if (ezafeKasrLable.matches("اضافه"))
                        ezafatoKosoorat.setEzafOrKasr(true);
                    else
                        ezafatoKosoorat.setEzafOrKasr(false);
                    ezafatoKosoorat.setSaatEK(ezafeKasrAmount / 100);
                    EzafatoKosooratDao.getInstance().createOrUpdate(ezafatoKosoorat);
                }
            }
        }
        RequestContext.getCurrentInstance().execute("$('#ezafeKasrSaat').modal('hide')");

    }

    public void reportPDF() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File file = new File(path + "\\report\\PishSanad.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap<>();
            map.put("month", findMounth(sanad));
            map.put("emzaEzafe", "سعید طاهری");
            map.put("emzaDayere", "سعید طاهری");
            map.put("emzaBazresi", "سعید طاهری");
            map.put("emzaFar", "سعید طاهری");
            int i = 1;
            for (EzafeKarDto ezafeKarDto : ezafeKariListDto) {
                ezafeKarDto.setId(String.valueOf(i));
                ezafeKarDto.setModat(EzafeUtil.splitHour(ezafeKarDto.getModat()));
                ezafeKarDto.setYegan(ezafeKarDto.getPersonel().getYegan() == null ? "" : ezafeKarDto.getPersonel().getYegan().getTitle());
                ezafeKarDto.setMakhaz(EzafeUtil.threeSplite(ezafeKarDto.getMakhaz()));
                ezafeKarDto.setMablagh(EzafeUtil.threeSplite(ezafeKarDto.getMablagh()));
                ezafeKarDto.setAccNum(ezafeKarDto.getPersonel().getShomareHesabHekmat());
                i++;
            }
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

    public void reportExcel() {

    }


    public void editSaatKasr() {
        ezafeKasrLable = "کسر";
        EzafatoKosoorat ezafatoKosoorat = sessionGate.findEzafeKasrBySanadAndPers(personelDetailLoad, sanadId);
        if (ezafatoKosoorat == null)
            ezafeKasrAmount = 0;
        else {
            ezafeKasrSharh = ezafatoKosoorat.getSharh();
            if (!ezafatoKosoorat.isEzafOrKasr())
                ezafeKasrAmount = ezafatoKosoorat.getSaatEK();
            else
                ezafeKasrAmount = 0;
        }
        RequestContext.getCurrentInstance().execute("$('#ezafeKasrSaat').modal('show')");
    }

    public void editSaatEzafe() {
        ezafeKasrLable = "اضافه";
        EzafatoKosoorat ezafatoKosoorat = sessionGate.findEzafeKasrBySanadAndPers(personelDetailLoad, sanadId);
        if (ezafatoKosoorat == null)
            ezafeKasrAmount = 0;
        else {
            ezafeKasrSharh = ezafatoKosoorat.getSharh();
            if (ezafatoKosoorat.isEzafOrKasr())
                ezafeKasrAmount = ezafatoKosoorat.getSaatEK();
            else
                ezafeKasrAmount = 0;
        }
        RequestContext.getCurrentInstance().execute("$('#ezafeKasrSaat').modal('show')");
    }

    public void changeMadrak() {
        if (selectedPersonel.getMadrak() == null) {
            selectedPersonel.setMadrak(findMadrakById(madrakID));
        } else {
            selectedPersonel.setMadrak(sessionGate.findMadrakById(selectedPersonel.getMadrak().getId()));
        }
    }


    public String calcSumHoghoogh(Personel personel) {
        return String.valueOf(personel.getHagheShaghel() + personel.getHaghShoghl() + personel.getFogholadeModiriyat());
    }


    public String calcFinalPayment(EzafeKarDto ezafeKar) {
        long a = (long) (Long.parseLong(ezafeKar.getMablagh())) - Long.parseLong(EzafeUtil.calcMablaghJire(ezafeKar.getPersonel(),sanad)) - Long.parseLong(EzafeUtil.calcKasrYegani(ezafeKar.getPersonel(),sanad));

        if (a < 0)  //manfi khord 0 mishe
            a = 0;
        if (a > ezafeKar.getPersonel().getSaghfeEzafeKar())  //saghf check mishe
            a = ezafeKar.getPersonel().getSaghfeEzafeKar();
        totalMablagh += a;
        return String.valueOf(a);
    }



    public String calcSaatEzafe(Personel personel) {
        if (personel.getId() != 0) {
            EzafatoKosoorat ezafatoKosoorat = sessionGate.findEzafeKasrBySanadAndPers(personelDetailLoad, sanadId);
            if (ezafatoKosoorat != null)
                if (ezafatoKosoorat.isEzafOrKasr())
                    return String.valueOf(ezafatoKosoorat.getSaatEK());
                else
                    return "00";
            else
                return "00";
        }
        return "00";
    }


    public String calcSaatKosoorat(Personel personel) {
        if (personel.getId() != 0) {
            EzafatoKosoorat ezafatoKosoorat = sessionGate.findEzafeKasrBySanadAndPers(personel, sanadId);
            if (ezafatoKosoorat != null)
                if (ezafatoKosoorat.isEzafOrKasr())
                    return "00";
                else
                    return String.valueOf(ezafatoKosoorat.getSaatEK());
            else
                return "00";
        }
        return "00";

    }

    public String calcSumHours(Personel personel) {
        if (personel.getId() != 0) {
            return String.valueOf(EzafeUtil.convertModat(Integer.parseInt(ezafeKariLoaded.getModat())));
        } else
            return "00";
    }

    public String calcSumHours(EzafeKari ezafeKari) {
        return String.valueOf(EzafeUtil.convertModat(ezafeKari.getModat()));
    }

    public String calcSumMablagh(Personel personel) {
        if (personel.getId() != 0) {
            return String.valueOf(Long.parseLong(EzafeUtil.calcMakhaz(personel)) * EzafeUtil.convertModat(Integer.parseInt(ezafeKariLoaded.getModat())));
        } else
            return "0";
    }

    public String calcSumMablagh(EzafeKari ezafeKari) {
//        if (ezafeKari.getId() != 0) {
        long a = (long) (Long.parseLong(EzafeUtil.calcMakhaz(ezafeKari.getPersonel())) * EzafeUtil.convertModat(ezafeKari.getModat()));
        return String.valueOf(a);
//        } else
//            return "0";
    }


    public void test() {
        System.out.println("test");
    }

    public void addNewPersoenl() {
        if (newPersonel.getShomarePersoneli().trim().length() > 0 && newPersonel.getName().trim().length() > 0 && newPersonel.getNeshan().trim().length() > 0) {
            newPersonel.setDakhelOrKharej(false);
            newPersonel.setActive(true);
            PersonelDao.getInstance().getBaseQuery().create(newPersonel);
            RequestContext.getCurrentInstance().execute("$('#addPers2Modal').modal('hide')");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "اطلاعات پرسنل با موفقیت ثبت شد."));
            newPersonel = new Personel(new Daraje(), new Yegan(), new NoeEstekhdam(), null);

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "اطلاعات پرسنل کامل وارد نشده است!"));
        }
    }

    public void openAddExPers() {
        yeganList = sessionGate.fillYegan();
        paygahList = sessionGate.fillPaygah();
        darajeList = sessionGate.fillDaraje();
        noeEstekhdamList = sessionGate.fillNoeEstekhdam();
        RequestContext.getCurrentInstance().execute("$('#addPers2Modal').modal('show')");
    }


    public void changePaygah() {
//        setYeganList(yeganList.stream().filter(x -> x.getPaygah().getId() == newPersonel.getYegan().getPaygah().getId()).collect(Collectors.toList()));
    }


    public void confirmFinanceChanges() {
        if (selectedPersonel.getDarajeShoghl() == null && darajeShoghlID != 0)
            selectedPersonel.setDarajeShoghl(findDarajeById(darajeShoghlID));
        if (madrakID != 0)
            selectedPersonel.setMadrak(new Madrak(madrakID));
        PersonelDao.getInstance().getBaseQuery().createOrUpdate(selectedPersonel);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "تغییرات ثبت شد."));
        if (financeModalOpenFromLocation == 1) {
            ezafeKariLoaded.setMakhaz(EzafeUtil.calcMakhaz(selectedPersonel));
            personelDetailLoad.setHaghShoghl(selectedPersonel.getHaghShoghl());
            personelDetailLoad.setHagheShaghel(selectedPersonel.getHagheShaghel());
            personelDetailLoad.setFogholadeModiriyat(selectedPersonel.getFogholadeModiriyat());
            personelDetailLoad.setMadrak(selectedPersonel.getMadrak());
            ezafeKariLoaded.setMablagh(String.valueOf(EzafeUtil.calcMablaghByModatAndMakhaz(ezafeKariLoaded)));
            ezafeKariLoaded.setTotalMablagh(calcFinalPayment(ezafeKariLoaded));
        } else
            showMakhaz(selectedPersonel);
        RequestContext.getCurrentInstance().execute("$('#financeModal').modal('hide')");
    }

    public String calcMakhaz(Personel personel){
        return EzafeUtil.calcMakhaz(personel);
    }


    private Madrak findMadrakById(long madrakID) {
        for (Madrak madrak : madrakList) {
            if (madrak.getId() == madrakID)
                return madrak;
        }
        return null;
    }


    private Daraje findDarajeById(long darajeShoghlID) {
        for (Daraje daraje : darajeList) {
            if (daraje.getId() == darajeShoghlID)
                return daraje;
        }
        return null;
    }

    public void selectDonePersonel() {
        ezafeKariListTemp.clear();
        boolean flag = true;
        if (selectedPersonels.size() == 0) {
            RequestContext.getCurrentInstance().execute("alert('هیج پرسنلی انتخاب نشده است!')");
        } else {
            for (Personel selectedPersonel : selectedPersonels) {
                if (personelList.stream().filter(x -> x.getId() == selectedPersonel.getId()).collect(Collectors.toList()).size() > 0) {
                    RequestContext.getCurrentInstance().execute("alert('" + selectedPersonel.getName() + selectedPersonel.getNeshan() + " در پیش سند موجود است!')");
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (Personel personel : selectedPersonels) {
                    EzafeKari ezafeKari = new EzafeKari();
                    ezafeKari.setSanad(new Sanad(sanadId));
                    ezafeKari.setPersonelBakhsh(personel.getBakhsh());
                    ezafeKari.setShomareDastgahEnd(0);
                    ezafeKari.setPersonel(personel);
                    ezafeKariListTemp.add(ezafeKari);
                }
                RequestContext.getCurrentInstance().execute("$('#addDetail').modal('show')");
            }
        }
    }


    public void cancelAdd() {
        selectedPersonels.clear();
        RequestContext.getCurrentInstance().execute("$('#df').modal('hide')");
    }


    public void confirmAdd() {
        boolean flag = true;
        for (EzafeKari ezafeKari : ezafeKariListTemp) {
            if (ezafeKari.getModat() < 30) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "مدت وارده معتبر نمی باشد!"));
                flag = false;
                break;
            }
        }
        if (flag) {
            for (EzafeKari ezafeKari : ezafeKariListTemp) {
//                ezafeKari.setModat(EzafeUtil.roundSaat(ezafeKari.getModat()));
                EzafekariDao.getInstance().create(ezafeKari);
                EzafeKarDto ezafeKarDto = new EzafeKarDto();
                ezafeKarDto.setPersonel(ezafeKari.getPersonel());
                ezafeKarDto.setPersCode(ezafeKari.getPersonel().getShomarePersoneli());
                ezafeKarDto.setPersName(ezafeKari.getPersonel().getName() + " " + ezafeKari.getPersonel().getNeshan());
                ezafeKarDto.setDegree(ezafeKari.getPersonel().getDaraje() == null ? "" : ezafeKari.getPersonel().getDaraje().getTitle());
                ezafeKarDto.setModat(String.valueOf(ezafeKari.getModat()));
                ezafeKarDto.setDayere(ezafeKari.getPersonel().getDayere() == null ? "" : ezafeKari.getPersonel().getDayere().getTitle());
                ezafeKarDto.setBakhsh(ezafeKari.getPersonel().getBakhsh() == null ? "" : ezafeKari.getPersonel().getBakhsh().getTitle());
                ezafeKarDto.setMakhaz(EzafeUtil.calcMakhaz(ezafeKari.getPersonel()));
                ezafeKarDto.setMablagh(String.valueOf(EzafeUtil.calcMablaghByModatAndMakhaz(ezafeKarDto)));
                ezafeKarDto.setTotalMablagh(calcFinalPayment(ezafeKarDto));
                ezafeKariListDto.add(ezafeKarDto);
                filteredEzfDto.add(ezafeKarDto);
            }
            RequestContext.getCurrentInstance().execute("$('#addDetail').modal('hide')");
        }
    }





    public void loadDetails(EzafeKarDto ezafeKari) {
        this.personelDetailLoad = ezafeKari.getPersonel();
        this.ezafeKariLoaded = ezafeKari;
        ezafeKariListLoad = ezafeKariSet.stream().filter(x -> x.getPersonel().equals(ezafeKari.getPersonel())).collect(Collectors.toList());
        ezafatoKosooratLoad = sessionGate.findEzafeKasrBySanadAndPers(ezafeKari.getPersonel(), sanadId);
        RequestContext.getCurrentInstance().execute("$('#dm').modal('show')");
    }





    public void modifyModat(EzafeKari ezafeKari) {
        ezafeKari.setModat(EzafeUtil.roundSaat(ezafeKari.getModat()));
        ezafeKari.setMablagh((long) (Long.parseLong(EzafeUtil.calcMakhaz(ezafeKari.getPersonel())) * EzafeUtil.convertModat(ezafeKari.getModat())));
    }


    public String showMakhaz(Personel personel) {
        return EzafeUtil.calcMakhaz(personel);
    }

    public void clickMakhaz(Personel personel, int loc) {
        this.financeModalOpenFromLocation = loc;
        this.selectedPersonel = personel;
        RequestContext.getCurrentInstance().execute("$('#financeModal').modal('show')");
    }

    public String calcKasrYegani(Personel personel){
        return EzafeUtil.calcKasrYegani(personel,sanad);
    }



    public String calcMablaghJire(Personel personel){
        return EzafeUtil.calcMablaghJire(personel,sanad);
    }

    public String calcCountJire(Personel personel){
        return EzafeUtil.calcCountJire(personel,sanad);
    }

    private String findMounth(Sanad sanad) {
        Integer mah = Integer.parseInt(sanad.getMah());
        String sal = sanad.getSal();
        switch (mah) {
            case 1: {
                return "فروردین";
            }
            case 2: {
                return "اردیبهشت";
            }
            case 3: {
                return "خرداد";
            }
            case 4: {
                return "تیر";
            }
            case 5: {
                return "مرداد";
            }
            case 6: {
                return "شهریور";
            }
            case 7: {
                return "مهر";
            }
            case 8: {
                return "آبان";
            }
            case 9: {
                return "آذر";
            }
            case 10: {
                return "دی";
            }
            case 11: {
                return "بهمن";
            }
            case 12: {
                return "اسفند";
            }
            default: {
                return "";
            }
        }
    }


    private String calcToDate(Sanad sanad) {
        Integer mah = Integer.parseInt(sanad.getMah());
        String sal = sanad.getSal();
        switch (mah) {
            case 1: {
                return sal + "/01/31";
            }
            case 2: {
                return sal + "/02/31";
            }
            case 3: {
                return sal + "/03/31";
            }
            case 4: {
                return sal + "/04/31";
            }
            case 5: {
                return sal + "/05/31";
            }
            case 6: {
                return sal + "/06/31";
            }
            case 7: {
                return sal + "/07/30";
            }
            case 8: {
                return sal + "/08/30";
            }
            case 9: {
                return sal + "/09/30";
            }
            case 10: {
                return sal + "/10/30";
            }
            case 11: {
                return sal + "/11/30";
            }
            case 12: {
                return sal + "/12/29";
            }
            default: {
                return sal + "/01/30";
            }
        }
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


    public Personel getPersonelDetailLoad() {
        return personelDetailLoad;
    }

    public void setPersonelDetailLoad(Personel personelDetailLoad) {
        this.personelDetailLoad = personelDetailLoad;
    }

    public List<EzafeKari> getEzafeKariListLoad() {
        return ezafeKariListLoad;
    }

    public void setEzafeKariListLoad(List<EzafeKari> ezafeKariListLoad) {
        this.ezafeKariListLoad = ezafeKariListLoad;
    }

    public EzafatoKosoorat getEzafatoKosooratLoad() {
        return ezafatoKosooratLoad;
    }

    public void setEzafatoKosooratLoad(EzafatoKosoorat ezafatoKosooratLoad) {
        this.ezafatoKosooratLoad = ezafatoKosooratLoad;
    }

    public EzafeKarDto getEzafeKariLoaded() {
        return ezafeKariLoaded;
    }

    public void setEzafeKariLoaded(EzafeKarDto ezafeKariLoaded) {
        this.ezafeKariLoaded = ezafeKariLoaded;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Personel> getSelectedPersonels() {
        return selectedPersonels;
    }

    public void setSelectedPersonels(List<Personel> selectedPersonels) {
        this.selectedPersonels = selectedPersonels;
    }

    public List<Personel> getPersonelList() {
        return personelList;
    }

    public void setPersonelList(List<Personel> personelList) {
        this.personelList = personelList;
    }

    public List<EzafeKari> getEzafeKariListTemp() {
        return ezafeKariListTemp;
    }

    public void setEzafeKariListTemp(List<EzafeKari> ezafeKariListTemp) {
        this.ezafeKariListTemp = ezafeKariListTemp;
    }

    public Personel getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(Personel selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }

    public long getDarajeShoghlID() {
        return darajeShoghlID;
    }

    public void setDarajeShoghlID(long darajeShoghlID) {
        this.darajeShoghlID = darajeShoghlID;
    }

    public long getMadrakID() {
        return madrakID;
    }

    public void setMadrakID(long madrakID) {
        this.madrakID = madrakID;
    }

    public List<Daraje> getDarajeList() {
        return darajeList;
    }

    public void setDarajeList(List<Daraje> darajeList) {
        this.darajeList = darajeList;
    }

    public List<Madrak> getMadrakList() {
        return madrakList;
    }

    public void setMadrakList(List<Madrak> madrakList) {
        this.madrakList = madrakList;
    }

    public Personel getNewPersonel() {
        return newPersonel;
    }

    public void setNewPersonel(Personel newPersonel) {
        this.newPersonel = newPersonel;
    }

    public List<Yegan> getYeganList() {
        return yeganList;
    }

    public void setYeganList(List<Yegan> yeganList) {
        this.yeganList = yeganList;
    }

    public List<NoeEstekhdam> getNoeEstekhdamList() {
        return noeEstekhdamList;
    }

    public void setNoeEstekhdamList(List<NoeEstekhdam> noeEstekhdamList) {
        this.noeEstekhdamList = noeEstekhdamList;
    }

    public List<Paygah> getPaygahList() {
        return paygahList;
    }

    public void setPaygahList(List<Paygah> paygahList) {
        this.paygahList = paygahList;
    }

    public long getNewModat() {
        return newModat;
    }

    public void setNewModat(long newModat) {
        this.newModat = newModat;
    }

    public long getNewMablagh() {
        return newMablagh;
    }

    public void setNewMablagh(long newMablagh) {
        this.newMablagh = newMablagh;
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

    public int getFinanceModalOpenFromLocation() {
        return financeModalOpenFromLocation;
    }

    public void setFinanceModalOpenFromLocation(int financeModalOpenFromLocation) {
        this.financeModalOpenFromLocation = financeModalOpenFromLocation;
    }

    public int getEzafeKasrAmount() {
        return ezafeKasrAmount;
    }

    public void setEzafeKasrAmount(int ezafeKasrAmount) {
        this.ezafeKasrAmount = ezafeKasrAmount;
    }

    public String getEzafeKasrLable() {
        return ezafeKasrLable;
    }

    public void setEzafeKasrLable(String ezafeKasrLable) {
        this.ezafeKasrLable = ezafeKasrLable;
    }

    public String getEzafeKasrSharh() {
        return ezafeKasrSharh;
    }

    public void setEzafeKasrSharh(String ezafeKasrSharh) {
        this.ezafeKasrSharh = ezafeKasrSharh;
    }

    public int getHourFilter() {
        return hourFilter;
    }

    public void setHourFilter(int hourFilter) {
        this.hourFilter = hourFilter;
    }
}
