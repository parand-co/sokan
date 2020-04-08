package manage.bean;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.util.HibernateUtil;
import manage.model.AmalKardModelChart;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ViewScoped
@ManagedBean
public class ChartBean implements Serializable {

    private String tarikhAz = "";
    private String tarikhTa = "";
    private String dayereCode = "";
    private String formCode = "";

    private Boolean isAllDayere = true;
    private Boolean isAllPage = true;
    private Boolean isDayere = false;
    private Boolean isBakhsh = false;
    private Boolean isForm = false;

    private String data;
    private String label;
    private Integer count;
    private String chartTitle;

    private List<Dayere> dayeres = new ArrayList<>();
    private List<Bakhsh> bakhshes = new ArrayList<>();
    private List<Form> forms = new ArrayList<>();

    private String dayereBakhshTitle = "دایره";

    public ChartBean() {
        fillList();
    }

    private void fillList(){
        Session session = HibernateUtil.getSession();
        List<Permission> permissions = session.createQuery("FROM Permission WHERE user.id = ?").setLong(0, IndexBean.user.getId()).list();

        List<SubPermission> subPermissions;
        subPermissions = session.createQuery("FROM SubPermission WHERE permission.user.id = ? AND bakhsh IS NOT NULL GROUP BY bakhsh.id").setLong(0, IndexBean.user.getId()).list();
        if(subPermissions.size() == 0) {
            subPermissions = session.createQuery("FROM SubPermission WHERE permission.user.id = ? GROUP BY dayere.id").setLong(0, IndexBean.user.getId()).list();
        } else if(subPermissions.size() == 1){
            isForm = true;
            dayereBakhshTitle = "فرم";
        } else {
            isBakhsh = true;
            dayereBakhshTitle = "بخش";
        }
        session.close();

        for (Permission permission : permissions) {
            forms.add(permission.getForm());
        }

        if(isForm || isBakhsh){
            for (SubPermission subPermission : subPermissions) {
                bakhshes.add(subPermission.getBakhsh());
            }
        } else {
            for (SubPermission subPermission : subPermissions) {
                dayeres.add(subPermission.getDayere());
            }
        }

        if(dayeres.size() == 1){
            if(dayeres.get(0).getCode().equals("0000")){
                Session session1 = HibernateUtil.getSession();
                dayeres = session1.createQuery("FROM Dayere WHERE code != ?").setString(0, "0000").list();
                session1.close();
            } else {
                Session session1 = HibernateUtil.getSession();
                bakhshes = session1.createQuery("FROM Bakhsh WHERE dayere.id = ?").setLong(0, dayeres.get(0).getId()).list();
                session1.close();
                dayereBakhshTitle = "بخش";
                isBakhsh = true;
            }
        }

        if(dayeres.size() > 1){
            isDayere = true;
        }

        makeChart();
    }

    public void makeChart(){
        if(Objects.equals(tarikhAz, "") && Objects.equals(tarikhTa, "")){
            tarikhTa = date();
            tarikhAz = tarikhTa.substring(0,8)+"01";
        }

        if(isForm){
            isAllDayere = false;
        } else if(isBakhsh){
            isAllDayere = Objects.equals(dayereCode, "");
        } else {
            if (dayeres.size() > 0) {
                isAllDayere = Objects.equals(dayereCode, "");
            } else {
                isAllDayere = false;
                dayereCode = "";
            }
        }

        isAllPage = Objects.equals(formCode, "");

        List<AmalKardModelChart> amalKardModelCharts = steeperWorks();

        setChartDetails(amalKardModelCharts);
    }

    private List<AmalKardModelChart> steeperWorks(){
        StringBuilder queryRegion = new StringBuilder();

        if(isForm){
            if(isAllPage) {
                queryRegion.append("FROM LogHistory WHERE (action = 'ایجاد' OR action = 'ویرایش') AND date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("'");
            } else {
                queryRegion.append("FROM LogHistory WHERE date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa)
                        .append("' AND user.personel.bakhsh.code = '").append(dayereCode).append("' AND form.code ='")
                        .append(formCode).append("'");
            }
        } else if(isBakhsh){
            if (isAllDayere && isAllPage) {
                queryRegion.append("FROM LogHistory WHERE action = 'ورود به سامانه' AND date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("'");
            } else if (isAllDayere){
                queryRegion.append("FROM LogHistory WHERE (action = 'ایجاد' OR action = 'ویرایش') AND date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("' AND form.code = '").append(formCode).append("'");
            } else if (isAllPage) {
                queryRegion.append("FROM LogHistory WHERE date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("' AND user.personel.bakhsh.code = '").append(dayereCode).append("'");
            } else {
                queryRegion.append("FROM LogHistory WHERE date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa)
                        .append("' AND user.personel.bakhsh.code = '").append(dayereCode).append("' AND form.code ='")
                        .append(formCode).append("'");
            }
        } else {
            if (isAllDayere && isAllPage) {
                queryRegion.append("FROM LogHistory WHERE action = 'ورود به سامانه' AND date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("'");
            } else if (isAllDayere) {
                queryRegion.append("FROM LogHistory WHERE (action = 'ایجاد' OR action = 'ویرایش') AND date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("' AND form.code = '").append(formCode).append("'");
            } else if (isAllPage) {
                queryRegion.append("FROM LogHistory WHERE date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa).append("' AND user.personel.dayere.code = '").append(dayereCode).append("'");
            } else {
                queryRegion.append("FROM LogHistory WHERE date >= '").append(tarikhAz).append("' AND date <= '").append(tarikhTa)
                        .append("' AND user.personel.dayere.code = '").append(dayereCode).append("' AND form.code ='")
                        .append(formCode).append("'");
            }
        }

        if (!isAllDayere || !isAllPage) {
            if(isAllPage){
                String f = " AND (";
                for (Form form : forms) {
                    if(f.equals(" AND (")){
                        f += "form.id = " + form.getId();
                    } else {
                        f += " OR form.id = " + form.getId();
                    }
                }
                if(!f.equals(" AND (")){
                    f += ")";
                    queryRegion.append(f);
                }
            }
        }

        if(isAllDayere || isForm){
            if(isBakhsh){
                String b = " AND (";
                for (Bakhsh bakhsh : bakhshes) {
                    if(b.equals(" AND (")){
                        b += "user.personel.bakhsh.id = " + bakhsh.getId();
                    } else {
                        b += " OR user.personel.bakhsh.id = " + bakhsh.getId();
                    }
                }
                if(!b.equals(" AND (")){
                    b += ")";
                    queryRegion.append(b);
                }
            } else {
                String d = " AND (";
                for (Dayere dayere : dayeres) {
                    if(d.equals(" AND (")){
                        d += "user.personel.dayere.id = " + dayere.getId();
                    } else {
                        d += " OR user.personel.dayere.id = " + dayere.getId();
                    }
                }
                if(!d.equals(" AND (")){
                    d += ")";
                    queryRegion.append(d);
                }
            }
        }

        queryRegion.append(" AND (user.id = ").append(IndexBean.user.getId()).append(" OR user.parentUser = '").append(IndexBean.user.getUserName()).append("')");

        StringBuilder query = new StringBuilder(queryRegion.toString());
        if (isAllDayere && isAllPage) {
            if (isBakhsh) {
                query.append(" GROUP BY user.personel.bakhsh.id");
            } else {
                query.append(" GROUP BY user.personel.dayere.id");
            }
        } else {
            query.append(" GROUP BY form.id");
        }

        List<AmalKardModelChart> amalKardModelCharts = null;

        Session session = HibernateUtil.getSession();
        List<LogHistory> logHistories = session.createQuery(query.toString()).list();
        session.close();

        if(logHistories.size() > 0){
            amalKardModelCharts = new ArrayList<>();
        }

        for (LogHistory logHistory : logHistories) {
            AmalKardModelChart model = new AmalKardModelChart();

            model.setTarikh(logHistory.getDate());
            model.setAction(logHistory.getAction());
            if(logHistory.getForm() != null) {
                model.setForm(logHistory.getForm().getTitle().trim());
            }
            if(isBakhsh){
                if (logHistory.getUser() != null) {
                    if (logHistory.getUser().getPersonel() != null) {
                        if (logHistory.getUser().getPersonel().getBakhsh() != null) {
                            model.setDayere(logHistory.getUser().getPersonel().getBakhsh().getTitle().trim());
                        }
                    }
                }
            } else {
                if (logHistory.getUser() != null) {
                    if (logHistory.getUser().getPersonel() != null) {
                        if (logHistory.getUser().getPersonel().getDayere() != null) {
                            model.setDayere(logHistory.getUser().getPersonel().getDayere().getTitle().trim());
                        }
                    }
                }
            }

            Session session1 = HibernateUtil.getSession();
            Long count;

            if(isBakhsh){
                if(logHistory.getForm() != null){
                    count = (Long) session1.createQuery("SELECT count(*) " + queryRegion.toString() + " AND form.id = " + logHistory.getForm().getId() + " GROUP BY user.personel.bakhsh.id").setMaxResults(1).uniqueResult();
                } else {
                    count = (Long) session1.createQuery("SELECT count(*) " + queryRegion.toString() + " AND form IS NULL GROUP BY user.personel.bakhsh.id").setMaxResults(1).uniqueResult();
                }
            } else {
                if(logHistory.getForm() != null){
                    count = (Long) session1.createQuery("SELECT count(*) " + queryRegion.toString() + " AND form.id = " + logHistory.getForm().getId() + " GROUP BY user.personel.dayere.id").setMaxResults(1).uniqueResult();
                } else {
                    count = (Long) session1.createQuery("SELECT count(*) " + queryRegion.toString() + " AND form IS NULL GROUP BY user.personel.dayere.id").setMaxResults(1).uniqueResult();
                }
//                if(isAllDayere && isAllPage){
//                    count = (Long) session1.createQuery("SELECT count(*) FROM LogHistory WHERE action = 'ورود به سامانه' AND date >= ? AND date <= ? AND (user.id = ? OR user.parentUser = ?) AND form = ? GROUP BY user.personel.dayere.id")
//                            .setString(0, tarikhAz)
//                            .setString(1, tarikhTa)
//                            .setLong(2,IndexBean.user.getId())
//                            .setString(3,IndexBean.user.getUserName())
//                            .setParameter(4, logHistory.getForm())
//                            .uniqueResult();
//                } else if(isAllDayere){
//                    count = (Long) session1.createQuery("SELECT count(*) FROM LogHistory WHERE (action = 'ایجاد' OR action = 'ویرایش') AND date >= ? AND date <= ? AND form.code = ? AND (user.id = ? OR user.parentUser = ?) AND form = ? GROUP BY user.personel.dayere.id")
//                            .setString(0, tarikhAz)
//                            .setString(1, tarikhTa)
//                            .setString(2, formCode)
//                            .setLong(3,IndexBean.user.getId())
//                            .setString(4,IndexBean.user.getUserName())
//                            .setParameter(5, logHistory.getForm())
//                            .uniqueResult();
//                } else if(isAllPage){
//                    count = (Long) session1.createQuery("SELECT count(*) FROM LogHistory WHERE (action = 'ایجاد' OR action = 'ویرایش') AND date >= ? AND date <= ? AND user.personel.dayere.code = ? AND (user.id = ? OR user.parentUser = ?) AND form = ? GROUP BY user.personel.dayere.id")
//                            .setString(0, tarikhAz)
//                            .setString(1, tarikhTa)
//                            .setString(2, dayereCode)
//                            .setLong(3,IndexBean.user.getId())
//                            .setString(4,IndexBean.user.getUserName())
//                            .setParameter(5, logHistory.getForm())
//                            .uniqueResult();
//                } else {
//                    count = (Long) session1.createQuery("SELECT count(*) FROM LogHistory WHERE (action = 'ایجاد' OR action = 'ویرایش') AND date >= ? AND date <= ? AND user.personel.dayere.code = ? AND form.code = ? AND (user.id = ? OR user.parentUser = ?) AND form = ? GROUP BY user.personel.dayere.id")
//                            .setString(0, tarikhAz)
//                            .setString(1, tarikhTa)
//                            .setString(2, dayereCode)
//                            .setString(3, formCode)
//                            .setLong(4,IndexBean.user.getId())
//                            .setString(5,IndexBean.user.getUserName())
//                            .setParameter(6, logHistory.getForm())
//                            .uniqueResult();
//                }
            }
            session1.close();
            model.setCount(String.valueOf(count));

            amalKardModelCharts.add(model);
        }

        return amalKardModelCharts;
    }

    public String date() {
        ULocale locale = new ULocale("EN", "IR");
        SimpleDateFormat general = new SimpleDateFormat("yyyy/MM/dd", locale);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return general.format(calendar.getTime());
    }

    private void setChartDetails(List<AmalKardModelChart> details){
        count = 0;
        data = "";
        label = "";
        chartTitle = "";
        if(isBakhsh){
            if(isAllDayere && isAllPage){
                chartTitle = "نمودار عملکرد کلیه بخش ها در بازه " + tarikhAz + " تا " + tarikhTa;
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getDayere() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getDayere() + ",";
                            count++;
                        }
                    }
                }
            } else if(isAllDayere){
                Form frm = forms.stream().filter(o -> o.getCode().equals(formCode)).findFirst().get();
                chartTitle = "نمودار عملکرد کلیه بخش ها در فرم " + frm.getTitle() +" و در بازه " + tarikhAz + " تا " + tarikhTa;
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getDayere() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getDayere() + ",";
                            count++;
                        }
                    }
                }
            } else if(isAllPage){
                if(dayeres.size() > 0){
                    Bakhsh pyg = bakhshes.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                    chartTitle = "نمودار عملکرد بخش " + pyg.getTitle().trim() + " در بازه " + tarikhAz + " تا " + tarikhTa;
                }
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getForm() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getForm() + ",";
                            count++;
                        }
                    }
                }
            } else {
                if(dayeres.size() > 0){
                    Bakhsh pyg = bakhshes.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                    Form frm = forms.stream().filter(o -> o.getCode().equals(formCode)).findFirst().get();
                    chartTitle = "نمودار عملکرد بخش " + pyg.getTitle().trim() + " در فرم " + frm.getTitle().trim() + " و در بازه " + tarikhAz + " تا " + tarikhTa;
                }
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getAction() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getAction() + ",";
                            count++;
                        }
                    }
                }
            }
        } else {
            if(isAllDayere && isAllPage){
                chartTitle = "نمودار عملکرد کلیه دایره ها در بازه " + tarikhAz + " تا " + tarikhTa;
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getDayere() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getDayere() + ",";
                            count++;
                        }
                    }
                }
            } else if(isAllDayere){
                Form frm = forms.stream().filter(o -> o.getCode().equals(formCode)).findFirst().get();
                chartTitle = "نمودار عملکرد کلیه دایره ها در فرم " + frm.getTitle() +" و در بازه " + tarikhAz + " تا " + tarikhTa;
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getDayere() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getDayere() + ",";
                            count++;
                        }
                    }
                }
            } else if(isAllPage){
                if(dayeres.size() > 0){
                    Dayere pyg = dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                    chartTitle = "نمودار عملکرد دایره " + pyg.getTitle().trim() + " در بازه " + tarikhAz + " تا " + tarikhTa;
                }
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getForm() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getForm() + ",";
                            count++;
                        }
                    }
                }
            } else {
                if(dayeres.size() > 0){
                    Dayere pyg = dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                    Form frm = forms.stream().filter(o -> o.getCode().equals(formCode)).findFirst().get();
                    chartTitle = "نمودار عملکرد دایره " + pyg.getTitle().trim() + " در فرم " + frm.getTitle().trim() + " و در بازه " + tarikhAz + " تا " + tarikhTa;
                }
                if(details != null) {
                    for (AmalKardModelChart model : details) {
                        if (model.getAction() != null && model.getCount() != null) {
                            data += model.getCount() + ",";
                            label += model.getAction() + ",";
                            count++;
                        }
                    }
                }
            }
        }

        if(details != null) {
            if (details.size() > 0) {
                removeLastAnd();
            }
        }
    }

    private void removeLastAnd() {
        if(label != null && !label.equals("")){
            label = label.substring(0, label.lastIndexOf(","));
        }
        if(data != null && !data.equals("")){
            data = data.substring(0, data.lastIndexOf(","));
        }
    }

    public String getTarikhAz() {
        return tarikhAz;
    }

    public void setTarikhAz(String tarikhAz) {
        this.tarikhAz = tarikhAz;
    }

    public String getTarikhTa() {
        return tarikhTa;
    }

    public void setTarikhTa(String tarikhTa) {
        this.tarikhTa = tarikhTa;
    }

    public String getDayereCode() {
        return dayereCode;
    }

    public void setDayereCode(String dayereCode) {
        this.dayereCode = dayereCode;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public List<Dayere> getDayeres() {
        return dayeres;
    }

    public void setDayeres(List<Dayere> dayeres) {
        this.dayeres = dayeres;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public String getDayereBakhshTitle() {
        return dayereBakhshTitle;
    }

    public void setDayereBakhshTitle(String dayereBakhshTitle) {
        this.dayereBakhshTitle = dayereBakhshTitle;
    }

    public Boolean getBakhsh() {
        return isBakhsh;
    }

    public void setBakhsh(Boolean bakhsh) {
        isBakhsh = bakhsh;
    }

    public List<Bakhsh> getBakhshes() {
        return bakhshes;
    }

    public void setBakhshes(List<Bakhsh> bakhshes) {
        this.bakhshes = bakhshes;
    }

    public Boolean getForm() {
        return isForm;
    }

    public void setForm(Boolean form) {
        isForm = form;
    }

    public Boolean getDayere() {
        return isDayere;
    }

    public void setDayere(Boolean dayere) {
        isDayere = dayere;
    }
}