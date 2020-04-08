package ezafekari.bean;

import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import ezafekari.model.EzafeKari;
import ezafekari.model.HosnAnjamKarModel;
import manage.bean.IndexBean;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Convert;
import util.Excel;
import util.FillList;
import util.PdfReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class HosnAnjamKarBean implements Serializable {

    private List<Dayere> dayeres;
    private List<Bakhsh> bakhshes = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdams;
    private List<HosnAnjamKarModel> result = new ArrayList<>();

    private Alert alert = new Alert();

    private Convert convert = new Convert();

    private String tarikh;
    private String dayereCode;
    private String bakhshCode;
    private String shp;
    private String name;
    private String family;
    private String project;
    private String startTime;
    private String startDastgah;
    private String endTime;
    private String endDastgah;
    private String modat;

    private StreamedContent exelFile;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    public HosnAnjamKarBean() {
        permissions = IndexBean.permissions;

        System.out.println("test");
        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        FillList fillList = new FillList();
        dayeres = fillList.dayeres(permissions.get(0));
        noeEstekhdams = fillList.noeEstekhdams(permissions.get(0));
    }

    public void fillBakhsh(String code){
        FillList fillList = new FillList();
        List<Bakhsh> bakhshFill = fillList.bakhsh(permissions.get(0));
        Session session=HibernateUtil.getSession();
        List<Bakhsh> bakhshSearch = session.createQuery("from Bakhsh where dayere.code = ?").setString(0, code).list();
        bakhshes.clear();

        for (Bakhsh bakhsh : bakhshFill) {
            for (Bakhsh search : bakhshSearch) {
                if(search.getId() == bakhsh.getId()){
                    bakhshes.add(search);
                }
            }
        }
        session.close();
    }

    private void findEzafekari(){
        result.clear();

        StringBuilder query = new StringBuilder("FROM EzafeKari WHERE 1 = 1");

        if(tarikh != null && !tarikh.equals("")){
            query.append(" AND tarikh.tarikh = '").append(tarikh).append("'");
        }
        if(dayereCode != null && !dayereCode.equals("")){
            query.append(" AND personel.dayere.code = '").append(dayereCode).append("'");
        } else {
            String d = " AND (";
            for (Dayere dayere : dayeres) {
                if(d.equals(" AND (")){
                    d += "personel.dayere.id = " + dayere.getId();
                } else {
                    d += " OR personel.dayere.id = " + dayere.getId();
                }
            }
            if(!d.equals(" AND (")){
                d += ")";
                query.append(d);
            }
        }

        String q = " AND (";
        for (NoeEstekhdam noeEstekhdam : noeEstekhdams) {
            if(q.equals(" AND (")){
                q += "personel.noeEstekhdam.id = " + noeEstekhdam.getId();
            } else {
                q += " OR personel.noeEstekhdam.id = " + noeEstekhdam.getId();
            }
        }
        if(!q.equals(" AND (")){
            q += ")";
            query.append(q);
        }

        if(bakhshCode != null && !bakhshCode.equals("")){
            query.append(" AND personel.bakhsh.code = '").append(bakhshCode).append("'");
        }
        if(shp != null && !shp.equals("")){
            query.append(" AND personel.shomarePersoneli = '").append(shp).append("'");
        }
        if(name != null && !name.equals("")){
            query.append(" AND personel.name LIKE '%").append(name).append("%'");
        }
        if(family != null && !family.equals("")){
            query.append(" AND personel.neshan LIKE '%").append(family).append("%'");
        }
        if(project != null && !project.equals("")){
//            query.append(" AND personel.neshan = '").append(project).append("'");
        }
        if(startTime != null && !startTime.equals("")){
            query.append(" AND saatStart >= ").append(Integer.valueOf(startTime.replace(":", "")));
        }
        if(endTime != null && !endTime.equals("")){
            query.append(" AND saatEnd <= ").append(Integer.valueOf(endTime.replace(":", "")));
        }
        if(startDastgah != null && !startDastgah.equals("")){
            query.append(" AND shomareDastgahStart = ").append(Integer.valueOf(startDastgah.replace(":", "")));
        }
        if(endDastgah != null && !endDastgah.equals("")){
            query.append(" AND shomareDastgahEnd = ").append(Integer.valueOf(endDastgah.replace(":", "")));
        }
        if(modat != null && !modat.equals("")){
            query.append(" AND (saatEnd - saatStart) = ").append(Integer.valueOf(modat.replace(":", "")));
        }

        if((tarikh == null || tarikh.equals("")) && (dayereCode == null || dayereCode.equals("")) && (bakhshCode == null || bakhshCode.equals("")) && (shp == null || shp.equals(""))
                && (name == null || name.equals("")) && (family == null || family.equals("")) && (startTime == null || startTime.equals("")) && (endTime == null || endTime.equals(""))
                && (startDastgah == null || startDastgah.equals("")) && (endDastgah == null || endDastgah.equals(""))){
            alert.neseceryFillField();
        } else {
            if(tarikh != null && !tarikh.equals("")){
                Session session = HibernateUtil.getSession();
                List<EzafeKari> ezafeKaris = session.createQuery(query.toString()).list();
                session.close();

                int i = 1;
                for (EzafeKari karis : ezafeKaris) {
                    HosnAnjamKarModel model = new HosnAnjamKarModel();

                    model.setRadif(String.valueOf(i++));
                    if(karis.getPersonel() != null) {
                        model.setShp(karis.getPersonel().getShomarePersoneli());
                        model.setNameFamily(karis.getPersonel().getName().trim() + " " + karis.getPersonel().getNeshan().trim());
                    }
                    model.setProject(project);
                    model.setStartTime(convert.clockStr(karis.getSaatStart(), false));
                    if(karis.getShomareDastgahStart() != null){
                        model.setStartDastgah(String.valueOf(karis.getShomareDastgahStart()));
                    }
                    model.setEndTime(convert.clockStr(karis.getSaatEnd(), false));
                    if(karis.getShomareDastgahEnd() != null){
                        model.setEndDastgah(String.valueOf(karis.getShomareDastgahEnd()));
                    }
                    model.setKarkard(convert.clockStr(convert.minesSaat(karis.getSaatEnd(), String.valueOf(karis.getSaatStart()), true), false));

                    result.add(model);
                }
            } else {
                alert.fieldIsEmpty("تاریخ");
            }
        }
    }

    public void pdfPrint(){
        findEzafekari();

        if(result.size() > 0) {
            PdfReport pdfReport = new PdfReport();

            Session session = HibernateUtil.getSession();
            List<Taghvim> taghvims = session.createQuery("FROM Taghvim WHERE tarikh = ?").setString(0, tarikh).list();
            session.close();

            Map map = new HashMap<>();

            if(taghvims.size() > 0){
                map.put("roz", "روز " + taghvims.get(0).getRoozHafte() + " مورخه " + tarikh.substring(8,10) + "/" + tarikh.substring(5,7) + "/" + tarikh.substring(0,4) + " روز " + taghvims.get(0).getDayType().getType());
            }

            map.put("tarikh", "تاریخ چاپ: " + simpleDateFormat.format(today.getTime()).substring(8,10) + "/" + simpleDateFormat.format(today.getTime()).substring(5,7) + "/" +simpleDateFormat.format(today.getTime()).substring(0,4));
            String dayere = "پرسنل اضافه کار کارگاه ";
            if(dayereCode != null && !dayereCode.equals("")){
                Dayere d = dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                if(d != null) {
                    dayere += d.getTitle();
                }
            }
            dayere += " / ";
            if(bakhshCode != null && !bakhshCode.equals("")){
                Bakhsh b = bakhshes.stream().filter(o -> o.getCode().equals(bakhshCode)).findFirst().orElse(null);
                if(b != null) {
                    dayere += b.getTitle();
                }
            }
            map.put("dayere", dayere);

            pdfReport.pdfReportSingleTable(result, map, "\\ezafekari\\jasperReport\\hosnAnjamKar.jrxml");
        } else {
            alert.notFoundList();
        }
    }

    public void reportExcel(){
        findEzafekari();

        if(result.size() > 0) {
            SessionUtil sessionUtil = new SessionUtil();
            String fileName;
            if(sessionUtil.getPermission() != null){
                fileName = sessionUtil.getPermission().getUser().getUserName() + System.currentTimeMillis() ;
            } else {
                fileName = "file" + System.currentTimeMillis() ;
            }
            String READ_PATH = "/resources/uploads/excel/";
            Excel excel = new Excel(READ_PATH, fileName);

            String[] columns = {"ردیف", "شماره پرسنلی", "نام و نام خانوادگی", "عنوان پروژه", "ساعت شروع", "شماره دستگاه", "ساعت خاتمه", "شماره دستگاه", "کارکرد"};
            if(excel.create("گزارش",columns, result)){
                InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(READ_PATH + "\\" + fileName + ".xlsx");
                exelFile = new DefaultStreamedContent(stream, "application/vnd.ms-exel", "excel.xlsx");
            } else {
                alert.error();
            }
        } else {
            alert.notFoundList();
        }
    }


    /* SETTER && GETTER */

    public List<Dayere> getDayeres() {
        return dayeres;
    }

    public void setDayeres(List<Dayere> dayeres) {
        this.dayeres = dayeres;
    }

    public List<Bakhsh> getBakhshes() {
        return bakhshes;
    }

    public void setBakhshes(List<Bakhsh> bakhshes) {
        this.bakhshes = bakhshes;
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getDayereCode() {
        return dayereCode;
    }

    public void setDayereCode(String dayereCode) {
        this.dayereCode = dayereCode;
    }

    public String getBakhshCode() {
        return bakhshCode;
    }

    public void setBakhshCode(String bakhshCode) {
        this.bakhshCode = bakhshCode;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDastgah() {
        return startDastgah;
    }

    public void setStartDastgah(String startDastgah) {
        this.startDastgah = startDastgah;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDastgah() {
        return endDastgah;
    }

    public void setEndDastgah(String endDastgah) {
        this.endDastgah = endDastgah;
    }

    public String getModat() {
        return modat;
    }

    public void setModat(String modat) {
        this.modat = modat;
    }

    public StreamedContent getExelFile() {
        return exelFile;
    }

    public void setExelFile(StreamedContent exelFile) {
        this.exelFile = exelFile;
    }

    public boolean isCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(boolean createPermission) {
        this.createPermission = createPermission;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isUpdatePermission() {
        return updatePermission;
    }

    public void setUpdatePermission(boolean updatePermission) {
        this.updatePermission = updatePermission;
    }

    public boolean isDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(boolean deletePermission) {
        this.deletePermission = deletePermission;
    }
}