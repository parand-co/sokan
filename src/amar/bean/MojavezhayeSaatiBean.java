package amar.bean;

import amar.model.Personel;
import amar.model.SavabeghMojavezeSaati;
import amar.model.Taraddod;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.SavabeghMojavezeSaatiDao;
import dataBaseModel.dao.TaraddodDao;
import dataBaseModel.util.HibernateUtil;
import manage.bean.IndexBean;
import org.hibernate.Session;
import util.FillList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class MojavezhayeSaatiBean implements Serializable {
    private String URL;
    private List<SavabeghMojavezeSaati> savabegh = new ArrayList<>();
    private List<NoeEstekhdam> noeEstekhdams = new ArrayList<>();
    private List<Dayere> dayeres = new ArrayList<>();
    private List<Bakhsh> bakhshes = new ArrayList<>();
    private List<MojavezSaati> mojavezSaatis = new ArrayList<>();
    private List<String> mahs = new ArrayList<>();
    private List<String> sals = new ArrayList<>();
    private Alert alert = new Alert();
    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emsal = simpleDateFormat.format(today.getTime()).substring(0,4);

    // search field
    private String shpSearch;
    private String codeMeliSearch;
    private String cartNumberSearch;
    private String nameSearch;
    private String familySearch;
    private String dayereCodeSearch;
    private String bakhshCodeSearch;
    private String salSearch;
    private String mahSearch;

    private String shp;
    private String cm;
    private Personel personel = null;
    private String mojavezCode;
    private String tarikh;
    private String zaman;

    private SavabeghMojavezeSaati selectMode = null;
    private boolean edited = false;

    private String moshakhasat;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;


    public MojavezhayeSaatiBean() {
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
        FillList fillList = new FillList();
        dayeres = fillList.dayeres(permissions.get(0));
        noeEstekhdams = fillList.noeEstekhdams(permissions.get(0));

        Session session = HibernateUtil.getSession();
        mojavezSaatis = session.createQuery("FROM MojavezSaati").list();
        session.close();

        for (int i = 1360; i < 1450; i++) {
            sals.add(String.valueOf(i));
        }

        salSearch = emsal;
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

    public void search(){
        if(salSearch != null && !salSearch.equals("") && mahSearch != null && !mahSearch.equals("")){
            StringBuilder query = new StringBuilder("FROM SavabeghMojavezeSaati WHERE 1 = 1 ");

            if(cartNumberSearch != null && !cartNumberSearch.equals("")){
                query.append("AND personel.shomareKart = '").append(cartNumberSearch).append("' ");
            }

            if(shpSearch != null && !shpSearch.equals("")){
                query.append("AND personel.shomarePersoneli = '").append(shpSearch).append("' ");
            }

            if(codeMeliSearch != null && !codeMeliSearch.equals("")){
                query.append("AND personel.codeMeli = '").append(codeMeliSearch).append("' ");
            }

            if(nameSearch != null && !nameSearch.equals("")){
                query.append("AND personel.name LIKE '%").append(nameSearch).append("%' ");
            }

            if(familySearch != null && !familySearch.equals("")){
                query.append("AND personel.neshan LIKE '%").append(familySearch).append("%' ");
            }

            if(dayereCodeSearch != null && !dayereCodeSearch.equals("")){
                query.append("AND personel.dayere.code = '").append(dayereCodeSearch).append("' ");
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

            if(bakhshCodeSearch != null && !bakhshCodeSearch.equals("")){
                query.append("AND personel.bakhsh.code = '").append(bakhshCodeSearch).append("' ");
            } else {
                FillList fillList = new FillList();
                List<Bakhsh> bakhshFill = fillList.bakhsh(permissions.get(0));
                String d = " AND (";
                for (Bakhsh bakhsh : bakhshFill) {
                    if(d.equals(" AND (")){
                        d += "personel.bakhsh.id = " + bakhsh.getId();
                    } else {
                        d += " OR personel.bakhsh.id = " + bakhsh.getId();
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

            query.append("AND SUBSTRING(tarikh, 1, 7) = '").append(salSearch).append("/").append(mahSearch).append("' ");

            Session session = HibernateUtil.getSession();
            savabegh = session.createQuery(query.toString()).list();
            session.close();
        } else {
            alert.fieldIsEmpty("سال و ماه");
        }
    }

    public void findPersonel(){
        FillList fillList = new FillList();
        List<Personel> personels = fillList.personels(permissions.get(0), shp, cm, "", "");

        if(personels.size() > 0){
            personel = personels.get(0);
            if(personel.getDaraje() != null) {
                moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan() + "     درجه :" + personel.getDaraje().getTitle();
            } else {
                moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan();
            }
            shp = personel.getShomarePersoneli();
            cm = personel.getCodeMeli();
        } else {
            personel = null;
            shp = null;
            cm = null;
            moshakhasat = null;
            alert.notFoundPersonel();
        }
    }

    public void save(){
        if(personel != null){
            if(tarikh != null && !tarikh.equals("") && zaman != null && !zaman.equals("") && mojavezCode != null && !mojavezCode.equals("")){
                SavabeghMojavezeSaati model = new SavabeghMojavezeSaati();

                model.setPersonel(personel);
                model.setMojavezSaati(mojavezSaatis.stream().filter(o -> o.getCode().equals(mojavezCode)).findFirst().orElse(null));
                model.setTarikh(tarikh);
                model.setZaman(zaman);

                changeTaradodd(tarikh, mojavezCode);

                SavabeghMojavezeSaatiDao.getInstance().getBaseQuery().create(model, URL);

                alert.successSave();

                savabegh.add(model);
                nuller();
            } else {
                alert.fieldIsEmpty("تاریخ، زمان و مجوز ساعتی");
            }
        } else {
            alert.fieldIsEmpty("شماره پرسنلی");
        }
    }

    public void nuller() {
        edited = false;
        selectMode = null;
        bakhshes.clear();

        shpSearch = "";
        codeMeliSearch = "";
        cartNumberSearch = "";
        nameSearch = "";
        familySearch = "";
        dayereCodeSearch = "";
        bakhshCodeSearch = "";
        salSearch = "";
        mahSearch = "";

        shp = "";
        personel = null;
        mojavezCode = "";
        tarikh = "";
        zaman = "";
        selectMode = null;
        edited = false;

        moshakhasat = "";
    }

    private void changeTaradodd(String tarikh, String mojavez){
        Session session = HibernateUtil.getSession();
        List<Taraddod> taraddods = session.createQuery("FROM Taraddod WHERE personel.shomarePersoneli = ? AND taghvim.tarikh = ? ")
                .setString(0, personel.getShomarePersoneli())
                .setString(1, tarikh)
                .list();
        session.close();

        int i = 1;
        for (Taraddod taraddod : taraddods) {
            Session session1 = HibernateUtil.getSession();
            MojavezRozane mojavezRozane = (MojavezRozane) session1.createQuery("FROM MojavezRozane WHERE code = ?").setString(0, mojavez).list().get(0);
            session1.close();
            if( i == 1 && !Objects.equals(mojavez, "13") ){
                taraddod.setVaziat(mojavezRozane);
                TaraddodDao.getInstance().getBaseQuery().createOrUpdate(taraddod, URL);
            } else if ( i == 3 ) {
                taraddod.setVaziat(mojavezRozane);
                TaraddodDao.getInstance().getBaseQuery().createOrUpdate(taraddod, URL);
            }
            i++;
        }
    }

    public void dispach(SavabeghMojavezeSaati model){
        edited = true;
        shp = model.getPersonel().getShomarePersoneli();
        personel = model.getPersonel();
        if(personel.getDaraje() != null) {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan() + "     درجه :" + personel.getDaraje().getTitle();
        } else {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan();
        }

        tarikh = model.getTarikh();
        if(model.getMojavezSaati() != null) {
            mojavezCode = model.getMojavezSaati().getCode();
        }
        zaman = model.getZaman();
        selectMode = model;
    }

    public void edit(){
        if(personel != null && selectMode != null){
            if(tarikh != null && !tarikh.equals("") && zaman != null && !zaman.equals("") && mojavezCode != null && !mojavezCode.equals("")){
                SavabeghMojavezeSaati model = selectMode;

                model.setMojavezSaati(mojavezSaatis.stream().filter(o -> o.getCode().equals(mojavezCode)).findFirst().orElse(null));
                model.setTarikh(tarikh);
                model.setZaman(zaman);

                changeTaradodd(tarikh, mojavezCode);

                SavabeghMojavezeSaatiDao.getInstance().getBaseQuery().createOrUpdate(model, URL);

                alert.successEdit();

                savabegh.remove(selectMode);
                savabegh.add(model);

                nuller();
            } else {
                alert.fieldIsEmpty("تاریخ، زمان و مجوز ساعتی");
            }
        }
    }

    public void startDelete(SavabeghMojavezeSaati model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        SavabeghMojavezeSaatiDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }



    // GETTER AND SETTER

    public List<SavabeghMojavezeSaati> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<SavabeghMojavezeSaati> savabegh) {
        this.savabegh = savabegh;
    }

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

    public List<MojavezSaati> getMojavezSaatis() {
        return mojavezSaatis;
    }

    public void setMojavezSaatis(List<MojavezSaati> mojavezSaatis) {
        this.mojavezSaatis = mojavezSaatis;
    }

    public List<String> getMahs() {
        return mahs;
    }

    public void setMahs(List<String> mahs) {
        this.mahs = mahs;
    }

    public List<String> getSals() {
        return sals;
    }

    public void setSals(List<String> sals) {
        this.sals = sals;
    }

    public String getShpSearch() {
        return shpSearch;
    }

    public void setShpSearch(String shpSearch) {
        this.shpSearch = shpSearch;
    }

    public String getCodeMeliSearch() {
        return codeMeliSearch;
    }

    public void setCodeMeliSearch(String codeMeliSearch) {
        this.codeMeliSearch = codeMeliSearch;
    }

    public String getCartNumberSearch() {
        return cartNumberSearch;
    }

    public void setCartNumberSearch(String cartNumberSearch) {
        this.cartNumberSearch = cartNumberSearch;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getFamilySearch() {
        return familySearch;
    }

    public void setFamilySearch(String familySearch) {
        this.familySearch = familySearch;
    }

    public String getDayereCodeSearch() {
        return dayereCodeSearch;
    }

    public void setDayereCodeSearch(String dayereCodeSearch) {
        this.dayereCodeSearch = dayereCodeSearch;
    }

    public String getBakhshCodeSearch() {
        return bakhshCodeSearch;
    }

    public void setBakhshCodeSearch(String bakhshCodeSearch) {
        this.bakhshCodeSearch = bakhshCodeSearch;
    }

    public String getSalSearch() {
        return salSearch;
    }

    public void setSalSearch(String salSearch) {
        this.salSearch = salSearch;
    }

    public String getMahSearch() {
        return mahSearch;
    }

    public void setMahSearch(String mahSearch) {
        this.mahSearch = mahSearch;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public Personel getPersonel() {
        return personel;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public String getMojavezCode() {
        return mojavezCode;
    }

    public void setMojavezCode(String mojavezCode) {
        this.mojavezCode = mojavezCode;
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public SavabeghMojavezeSaati getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(SavabeghMojavezeSaati selectMode) {
        this.selectMode = selectMode;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getMoshakhasat() {
        return moshakhasat;
    }

    public void setMoshakhasat(String moshakhasat) {
        this.moshakhasat = moshakhasat;
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