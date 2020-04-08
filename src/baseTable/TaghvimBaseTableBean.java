package baseTable;

import baseCode.alert.Alert;
import baseTable.model.HafteModel;
import baseTable.model.RoozModel;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.TaghvimDao;
import dataBaseModel.util.HibernateUtil;
import manage.bean.IndexBean;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class TaghvimBaseTableBean implements Serializable {
    private String URL;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emroz = simpleDateFormat.format(today.getTime());
    private String emsal = emroz.substring(0,4);

    private List<String> sals = new ArrayList<>();
    private List<Month> months = new ArrayList<>();
    private List<Goroh> gorohs = new ArrayList<>();
    private List<Taghvim> taghvims = new ArrayList<>();
    private List<HafteModel> haftes = new ArrayList<>();
    private Alert alert = new Alert();

    // search field
    private String salCode;
    private String mahCode;
    private Long gorohId = 0L;

    private boolean edited = false;
    private int tedadHafte = 0;
    private int shomareRooz = 0;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public TaghvimBaseTableBean() {
        URL = IndexBean.url;

        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        fillSavabegh();
    }

    private void fillSavabegh(){
        for (int i = Integer.valueOf(emsal); i < 1450; i++) {
            sals.add(String.valueOf(i));
        }
        Session session = HibernateUtil.getSession();
        months = session.createQuery("FROM Month").list();
        gorohs = session.createQuery("FROM Goroh").list();
        session.close();
    }

    public void search(){
        taghvims.clear();
        haftes.clear();
        tedadHafte = 0;
        edited = false;

        if(salCode != null && !salCode.equals("") && mahCode != null && !mahCode.equals("") && gorohId != 0){
            Session session = HibernateUtil.getSession();
            taghvims = session.createQuery("FROM Taghvim WHERE SUBSTRING(tarikh,1,7) = ?").setString(0, salCode + "/" + mahCode).list();
            session.close();

            String roozAval = taghvims.get(0).getRoozHafte();
            tedadHafte = calcHafte(roozAval, taghvims.size());

            for (int i = 1; i <= tedadHafte; i++) {
                HafteModel hafteModel = new HafteModel();
                hafteModel.setHafte(i);
                hafteModel.setRoozModels(roozModels(i, taghvims));
                
                haftes.add(hafteModel);
            }
        } else {
            alert.allFeildNecessery();
        }

        if(taghvims.size() == 0){
            alert.notFoundList();
        } else{
            edited = true;
        }
    }

    private Integer calcHafte(String rooz, Integer tedadRooz){
        shomareRooz = 0;
        if(rooz.equals("شنبه")){
            shomareRooz = 7;
        }
        if(rooz.equals("یکشنبه")){
            shomareRooz = 6;
        }
        if(rooz.equals("دوشنبه")){
            shomareRooz = 5;
        }
        if(rooz.equals("سه شنبه")){
            shomareRooz = 4;
        }
        if(rooz.equals("چهارشنبه")){
            shomareRooz = 3;
        }
        if(rooz.equals("پنجشنبه")){
            shomareRooz = 2;
        }
        if(rooz.equals("جمعه")){
            shomareRooz = 1;
        }

        tedadRooz = tedadRooz - shomareRooz;

        int baghimande = tedadRooz % 7;

        if(baghimande > 0){
            return (tedadRooz / 7) + 2;
        }
        return (tedadRooz / 7) + 1;
    }
    
    private List<RoozModel> roozModels(Integer hafte, List<Taghvim> taghvims){
        List<RoozModel> result = new ArrayList<>();
        if(hafte == 1){
            int count = 0;
            for (Taghvim taghvim : taghvims) {
                RoozModel roozModel = new RoozModel();

                roozModel.setHafteTitle(taghvim.getRoozHafte());
                if(gorohId == 1){
                    roozModel.setDayType(taghvim.getDayTypeG1());
                }
                if(gorohId == 2){
                    roozModel.setDayType(taghvim.getDayTypeG2());
                }
                if(gorohId == 3){
                    roozModel.setDayType(taghvim.getDayTypeG3());
                }
                if(gorohId == 4){
                    roozModel.setDayType(taghvim.getDayTypeG4());
                }
                if(gorohId == 5){
                    roozModel.setDayType(taghvim.getDayTypeG5());
                }
                if(gorohId == 6){
                    roozModel.setDayType(taghvim.getDayTypeG6());
                }
                if(gorohId == 7){
                    roozModel.setDayType(taghvim.getDayTypeG7());
                }
                roozModel.setRooz(taghvim.getTarikh().replace(salCode + "/" + mahCode + "/", ""));

                result.add(roozModel);

                count++;

                if(count == shomareRooz){
                    break;
                }
            }
        } else {
            int count = 0;
            int rooz = 0;
            for (Taghvim taghvim : taghvims) {

                if (shomareRooz + ((hafte-2) * 7) > rooz){
                    rooz++;
                    continue;
                } else {
                    RoozModel roozModel = new RoozModel();

                    roozModel.setHafteTitle(taghvim.getRoozHafte());
                    if(gorohId == 1){
                        roozModel.setDayType(taghvim.getDayTypeG1());
                    }
                    if(gorohId == 2){
                        roozModel.setDayType(taghvim.getDayTypeG2());
                    }
                    if(gorohId == 3){
                        roozModel.setDayType(taghvim.getDayTypeG3());
                    }
                    if(gorohId == 4){
                        roozModel.setDayType(taghvim.getDayTypeG4());
                    }
                    if(gorohId == 5){
                        roozModel.setDayType(taghvim.getDayTypeG5());
                    }
                    if(gorohId == 6){
                        roozModel.setDayType(taghvim.getDayTypeG6());
                    }
                    if(gorohId == 7){
                        roozModel.setDayType(taghvim.getDayTypeG7());
                    }
                    roozModel.setRooz(taghvim.getTarikh().replace(salCode + "/" + mahCode + "/", ""));

                    result.add(roozModel);

                    count++;

                    if(count == 7){
                        break;
                    }
                }
            }
        }

        if(result.size() == 7){
            return result;
        }

        RoozModel rozAval = result.get(0);

        if(rozAval.getHafteTitle().equals("شنبه")){
            RoozModel rozAKhar = result.get(result.size() - 1);

            if(rozAKhar.getHafteTitle().equals("شنبه")){
                RoozModel roozModel1 = new RoozModel();
                roozModel1.setHafteTitle("یکشنبه");
                result.add(roozModel1);

                RoozModel roozModel2 = new RoozModel();
                roozModel2.setHafteTitle("دوشنبه");

                result.add(roozModel2);

                RoozModel roozModel3 = new RoozModel();
                roozModel3.setHafteTitle("سه شنبه");
                result.add(roozModel3);

                RoozModel roozModel4 = new RoozModel();
                roozModel4.setHafteTitle("چهارشنبه");
                result.add(roozModel4);

                RoozModel roozModel5 = new RoozModel();
                roozModel5.setHafteTitle("پنجشنبه");
                result.add(roozModel5);

                RoozModel roozModel6 = new RoozModel();
                roozModel6.setHafteTitle("جمعه");
                result.add( roozModel6);
            }

            if(rozAKhar.getHafteTitle().equals("یکشنبه")){
                RoozModel roozModel2 = new RoozModel();
                roozModel2.setHafteTitle("دوشنبه");

                result.add(roozModel2);

                RoozModel roozModel3 = new RoozModel();
                roozModel3.setHafteTitle("سه شنبه");
                result.add(roozModel3);

                RoozModel roozModel4 = new RoozModel();
                roozModel4.setHafteTitle("چهارشنبه");
                result.add(roozModel4);

                RoozModel roozModel5 = new RoozModel();
                roozModel5.setHafteTitle("پنجشنبه");
                result.add(roozModel5);

                RoozModel roozModel6 = new RoozModel();
                roozModel6.setHafteTitle("جمعه");
                result.add( roozModel6);
            }

            if(rozAKhar.getHafteTitle().equals("دوشنبه")){
                RoozModel roozModel3 = new RoozModel();
                roozModel3.setHafteTitle("سه شنبه");
                result.add(roozModel3);

                RoozModel roozModel4 = new RoozModel();
                roozModel4.setHafteTitle("چهارشنبه");
                result.add(roozModel4);

                RoozModel roozModel5 = new RoozModel();
                roozModel5.setHafteTitle("پنجشنبه");
                result.add(roozModel5);

                RoozModel roozModel6 = new RoozModel();
                roozModel6.setHafteTitle("جمعه");
                result.add( roozModel6);
            }

            if(rozAKhar.getHafteTitle().equals("سه شنبه")){
                RoozModel roozModel4 = new RoozModel();
                roozModel4.setHafteTitle("چهارشنبه");
                result.add(roozModel4);

                RoozModel roozModel5 = new RoozModel();
                roozModel5.setHafteTitle("پنجشنبه");
                result.add(roozModel5);

                RoozModel roozModel6 = new RoozModel();
                roozModel6.setHafteTitle("جمعه");
                result.add( roozModel6);
            }

            if(rozAKhar.getHafteTitle().equals("چهارشنبه")){
                RoozModel roozModel5 = new RoozModel();
                roozModel5.setHafteTitle("پنجشنبه");
                result.add(roozModel5);

                RoozModel roozModel6 = new RoozModel();
                roozModel6.setHafteTitle("جمعه");
                result.add( roozModel6);
            }

            if(rozAKhar.getHafteTitle().equals("پنجشنبه")){
                RoozModel roozModel6 = new RoozModel();
                roozModel6.setHafteTitle("جمعه");
                result.add( roozModel6);
            }
        }

        if(rozAval.getHafteTitle().equals("یکشنبه")){
            RoozModel roozModel = new RoozModel();
            roozModel.setHafteTitle("شنبه");

            result.add(0, roozModel);
        }

        if(rozAval.getHafteTitle().equals("دوشنبه")){
            RoozModel roozModel = new RoozModel();
            roozModel.setHafteTitle("شنبه");

            result.add(0, roozModel);

            RoozModel roozModel1 = new RoozModel();
            roozModel1.setHafteTitle("یکشنبه");

            result.add(1, roozModel1);

        }
        if(rozAval.getHafteTitle().equals("سه شنبه")){
            RoozModel roozModel = new RoozModel();
            roozModel.setHafteTitle("شنبه");

            result.add(0, roozModel);

            RoozModel roozModel1 = new RoozModel();
            roozModel.setHafteTitle("یکشنبه");

            result.add(1, roozModel1);

            RoozModel roozModel2 = new RoozModel();
            roozModel.setHafteTitle("دوشنبه");

            result.add(2, roozModel2);

        }
        if(rozAval.getHafteTitle().equals("چهارشنبه")){
            RoozModel roozModel = new RoozModel();
            roozModel.setHafteTitle("شنبه");

            result.add(0, roozModel);

            RoozModel roozModel1 = new RoozModel();
            roozModel1.setHafteTitle("یکشنبه");

            result.add(1, roozModel1);

            RoozModel roozModel2 = new RoozModel();
            roozModel2.setHafteTitle("دوشنبه");

            result.add(2, roozModel2);

            RoozModel roozModel3 = new RoozModel();
            roozModel3.setHafteTitle("سه شنبه");

            result.add(3, roozModel3);

        }
        if(rozAval.getHafteTitle().equals("پنجشنبه")){
            RoozModel roozModel = new RoozModel();
            roozModel.setHafteTitle("شنبه");

            result.add(0, roozModel);

            RoozModel roozModel1 = new RoozModel();
            roozModel1.setHafteTitle("یکشنبه");

            result.add(1, roozModel1);

            RoozModel roozModel2 = new RoozModel();
            roozModel2.setHafteTitle("دوشنبه");

            result.add(2, roozModel2);

            RoozModel roozModel3 = new RoozModel();
            roozModel3.setHafteTitle("سه شنبه");

            result.add(3, roozModel3);

            RoozModel roozModel4 = new RoozModel();
            roozModel4.setHafteTitle("چهارشنبه");

            result.add(4, roozModel4);
        }
        if(rozAval.getHafteTitle().equals("جمعه")){
            RoozModel roozModel = new RoozModel();
            roozModel.setHafteTitle("شنبه");

            result.add(0, roozModel);

            RoozModel roozModel1 = new RoozModel();
            roozModel1.setHafteTitle("یکشنبه");

            result.add(1, roozModel1);

            RoozModel roozModel2 = new RoozModel();
            roozModel2.setHafteTitle("دوشنبه");

            result.add(2, roozModel2);

            RoozModel roozModel3 = new RoozModel();
            roozModel3.setHafteTitle("سه شنبه");

            result.add(3, roozModel3);

            RoozModel roozModel4 = new RoozModel();
            roozModel4.setHafteTitle("چهارشنبه");

            result.add(4, roozModel4);

            RoozModel roozModel5 = new RoozModel();
            roozModel5.setHafteTitle("پنجشنبه");

            result.add(5, roozModel5);
        }

        return result;
    }

    public String colorDay(DayType dayType){
        if(dayType == null){
            return "";
        } else if(dayType.getId() == 1){
            return "";
        } else if(dayType.getId() == 2){
            return "background: #e8a62e;";
        } else if(dayType.getId() == 3){
            return "background: #f17d71;";
        }
        return "";
    }

    public void edit(String rooz){
        if(rooz != null && !rooz.equals("")){
            Session session = HibernateUtil.getSession();
            Taghvim taghvim = (Taghvim) session.createQuery("FROM Taghvim WHERE tarikh = ?").setString(0,salCode + "/" + mahCode + "/" + rooz).list().get(0);
            session.close();

            DayType dayType = null;

            if(gorohId == 1){
                dayType = taghvim.getDayTypeG1();
            }
            if(gorohId == 2){
                dayType = taghvim.getDayTypeG2();
            }
            if(gorohId == 3){
                dayType = taghvim.getDayTypeG3();
            }
            if(gorohId == 4){
                dayType = taghvim.getDayTypeG4();
            }
            if(gorohId == 5){
                dayType = taghvim.getDayTypeG5();
            }
            if(gorohId == 6){
                dayType = taghvim.getDayTypeG6();
            }
            if(gorohId == 7){
                dayType = taghvim.getDayTypeG7();
            }

            if(dayType == null){
                Session session1 = HibernateUtil.getSession();
                dayType = (DayType) session1.createQuery("FROM DayType WHERE id = ?").setLong(0, 1).list().get(0);
                session1.close();
            } else {
                if(dayType.getId() == 1){
                    Session session1 = HibernateUtil.getSession();
                    dayType = (DayType) session1.createQuery("FROM DayType WHERE id = ?").setLong(0, 2).list().get(0);
                    session1.close();
                } else if(dayType.getId() == 2){
                    Session session1 = HibernateUtil.getSession();
                    dayType = (DayType) session1.createQuery("FROM DayType WHERE id = ?").setLong(0, 3).list().get(0);
                    session1.close();
                } else{
                    Session session1 = HibernateUtil.getSession();
                    dayType = (DayType) session1.createQuery("FROM DayType WHERE id = ?").setLong(0, 1).list().get(0);
                    session1.close();
                }
            }

            if(gorohId == 1){
                taghvim.setDayTypeG1(dayType);
            }
            if(gorohId == 2){
                taghvim.setDayTypeG2(dayType);
            }
            if(gorohId == 3){
                taghvim.setDayTypeG3(dayType);
            }
            if(gorohId == 4){
                taghvim.setDayTypeG4(dayType);
            }
            if(gorohId == 5){
                taghvim.setDayTypeG5(dayType);
            }
            if(gorohId == 6){
                taghvim.setDayTypeG6(dayType);
            }
            if(gorohId == 7){
                taghvim.setDayTypeG7(dayType);
            }

            TaghvimDao.getInstance().createOrUpdate(taghvim, URL);

            search();
        }
    }




    // GETTER AND SETTER

    public List<String> getSals() {
        return sals;
    }

    public void setSals(List<String> sals) {
        this.sals = sals;
    }

    public List<Month> getMonths() {
        return months;
    }

    public void setMonths(List<Month> months) {
        this.months = months;
    }

    public List<Goroh> getGorohs() {
        return gorohs;
    }

    public void setGorohs(List<Goroh> gorohs) {
        this.gorohs = gorohs;
    }

    public String getSalCode() {
        return salCode;
    }

    public void setSalCode(String salCode) {
        this.salCode = salCode;
    }

    public String getMahCode() {
        return mahCode;
    }

    public void setMahCode(String mahCode) {
        this.mahCode = mahCode;
    }

    public Long getGorohId() {
        return gorohId;
    }

    public void setGorohId(Long gorohId) {
        this.gorohId = gorohId;
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

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public List<HafteModel> getHaftes() {
        return haftes;
    }

    public void setHaftes(List<HafteModel> haftes) {
        this.haftes = haftes;
    }

    public int getTedadHafte() {
        return tedadHafte;
    }

    public void setTedadHafte(int tedadHafte) {
        this.tedadHafte = tedadHafte;
    }
}