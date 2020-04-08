package net.hokmeKar.bean;

import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.*;
import dataBaseModel.util.HibernateUtil;
import net.hokmeKar.model.SabtHokmeKar;
import net.hokmeKar.model.SabtHokmeKarDao;
import manage.bean.IndexBean;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SabtHokmeKarBean implements Serializable {

    private String URL;
    private List<SabtHokmeKar> savabegh = new ArrayList<>();

    private List<TypeHokmeKar> noeHokmeKars = new ArrayList<>();
    private List<Bakhsh> yegans = new ArrayList<>();
    private List<ArjaiyatHokmeKar> arjaiyatHokmeKars = new ArrayList<>();
    private List<VaziyatAnjamHokmeKar> vaziyatAnjamHokmeKars = new ArrayList<>();
    private List<AnjamDahandeHokmeKar> anjamDahandeHokmeKars = new ArrayList<>();
    private List<TypeKarHokmeKar> noeKareHokmeKars = new ArrayList<>();

    private String shomareDarkhast;
    private String hozeKari;
    private String codeShenavar;
    private String tarikhSabt;
    private String tarikhDarkhast;
    private String sharh;
    private Boolean emamReza;
    private Long noeHokmeKar;
    private Long yegan;
    private Long arjaiyatHokmeKar;
    private Long vaziyatAnjamHokmeKar;
    private Long anjamDahandeHokmeKar;
    private Long noeKareHokmeKar;

    private String searchCodeShenavar;
    private String searchTarikhDarkhast;
    private Long searchArjaiyatHokmeKar;
    private Long searchYegan;
    private Long searchAnjamDahandeHokmeKar;
    private Long searchVaziyatAnjamHokmeKar;

    private Alert alert = new Alert();

    private SabtHokmeKar selectMode = null;
    private boolean edited = false;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    private ULocale locale = new ULocale("en", "IR");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
    private Calendar today = Calendar.getInstance();
    private String emroz = simpleDateFormat.format(today.getTime());

    public SabtHokmeKarBean() {
        URL = IndexBean.url;

        permissions = IndexBean.permissions;

        if(permissions.size() > 0){
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }

        fillList();
    }

    private void fillList(){
        tarikhSabt = emroz;
        Session session = HibernateUtil.getSession();
        noeHokmeKars = session.createQuery("FROM TypeHokmeKar").list();
        yegans = session.createQuery("FROM Bakhsh").list();
        arjaiyatHokmeKars = session.createQuery("FROM ArjaiyatHokmeKar").list();
        vaziyatAnjamHokmeKars = session.createQuery("FROM VaziyatAnjamHokmeKar").list();
        anjamDahandeHokmeKars = session.createQuery("FROM AnjamDahandeHokmeKar").list();
        noeKareHokmeKars = session.createQuery("FROM TypeKarHokmeKar").list();
        session.close();
    }

    public void search(){
        savabegh.clear();
        if(!searchCodeShenavar.equals("") || !searchTarikhDarkhast.equals("") || searchArjaiyatHokmeKar != null ||
                searchYegan != null || searchAnjamDahandeHokmeKar != null || searchVaziyatAnjamHokmeKar != null){
            StringBuilder query = new StringBuilder("FROM SabtHokmeKar WHERE 1 = 1 ");
            if(!searchCodeShenavar.equals("")){
                query.append("AND codeShenavar = '").append(searchCodeShenavar).append("' ");
            }
            if(!searchTarikhDarkhast.equals("")){
                query.append("AND tarikhDarkhast = '").append(searchTarikhDarkhast).append("' ");
            }
            if(searchArjaiyatHokmeKar != null){
                query.append("AND arjaiyatHokmeKar.id = ").append(searchArjaiyatHokmeKar).append(" ");
            }
            if(searchYegan != null){
                query.append("AND yegan.id = ").append(searchYegan).append(" ");
            }
            if(searchAnjamDahandeHokmeKar != null){
                query.append("AND anjamDahandeHokmeKar.id = ").append(searchAnjamDahandeHokmeKar).append(" ");
            }
            if(searchVaziyatAnjamHokmeKar != null){
                query.append("AND vaziyatAnjamHokmeKar.id = ").append(searchVaziyatAnjamHokmeKar).append(" ");
            }
            Session session = HibernateUtil.getSession();
            savabegh = session.createQuery(query.toString()).list();
            session.close();

            if (savabegh.size() == 0){
                alert.unSuccessSearch();
            }

        } else {
            alert.firstSelectItem();
        }
    }

    public void save(){
        SabtHokmeKar model = new SabtHokmeKar();

        model.setShomareDarkhast(shomareDarkhast);
        model.setHozeKari(hozeKari);
        model.setCodeShenavar(codeShenavar);
        model.setTarikhSabt(tarikhSabt);
        model.setTarikhDarkhast(tarikhDarkhast);
        model.setSharh(sharh);
        model.setEmamReza(emamReza);
        model.setNoeHokmeKar(noeHokmeKars.stream().filter(o -> o.getId() == noeHokmeKar).findFirst().orElse(null));
        model.setYegan(yegans.stream().filter(o -> o.getId() == yegan).findFirst().orElse(null));
        model.setArjaiyatHokmeKar(arjaiyatHokmeKars.stream().filter(o -> o.getId() == arjaiyatHokmeKar).findFirst().orElse(null));
        model.setVaziyatAnjamHokmeKar(vaziyatAnjamHokmeKars.stream().filter(o -> o.getId() == vaziyatAnjamHokmeKar).findFirst().orElse(null));
        model.setAnjamDahandeHokmeKar(anjamDahandeHokmeKars.stream().filter(o -> o.getId() == anjamDahandeHokmeKar).findFirst().orElse(null));
        model.setNoeKareHokmeKar(noeKareHokmeKars.stream().filter(o -> o.getId() == noeKareHokmeKar).findFirst().orElse(null));

        SabtHokmeKarDao.getInstance().getBaseQuery().create(model, URL);
        alert.successSave();

        savabegh.add(model);
        nuller();
    }

    public void nuller(){
        shomareDarkhast = null;
        hozeKari = null;
        codeShenavar = null;
        tarikhSabt = emroz;
        tarikhDarkhast = null;
        sharh = null;
        emamReza = null;
        noeHokmeKar = null;
        yegan = null;
        arjaiyatHokmeKar = null;
        vaziyatAnjamHokmeKar = null;
        anjamDahandeHokmeKar = null;
        noeKareHokmeKar = null;
        edited = false;
        selectMode = null;
    }

    public void dispach(SabtHokmeKar model){
        edited = true;

        shomareDarkhast = model.getShomareDarkhast();
        hozeKari = model.getHozeKari();
        codeShenavar = model.getCodeShenavar();
        tarikhSabt = model.getTarikhSabt();
        tarikhDarkhast = model.getTarikhDarkhast();
        sharh = model.getSharh();
        emamReza = model.getEmamReza();
        if(model.getNoeHokmeKar() != null) {
            noeHokmeKar = model.getNoeHokmeKar().getId();
        }
        if(model.getYegan() != null) {
            yegan = model.getYegan().getId();
        }
        if(model.getArjaiyatHokmeKar() != null) {
            arjaiyatHokmeKar = model.getArjaiyatHokmeKar().getId();
        }
        if(model.getVaziyatAnjamHokmeKar() != null) {
            vaziyatAnjamHokmeKar = model.getVaziyatAnjamHokmeKar().getId();
        }
        if(model.getAnjamDahandeHokmeKar() != null) {
            anjamDahandeHokmeKar = model.getAnjamDahandeHokmeKar().getId();
        }
        if(model.getNoeKareHokmeKar() != null) {
            noeKareHokmeKar = model.getNoeKareHokmeKar().getId();
        }

        selectMode = model;
    }

    public void edit(){
        SabtHokmeKar model = selectMode;

        model.setShomareDarkhast(shomareDarkhast);
        model.setHozeKari(hozeKari);
        model.setCodeShenavar(codeShenavar);
        model.setTarikhSabt(tarikhSabt);
        model.setTarikhDarkhast(tarikhDarkhast);
        model.setSharh(sharh);
        model.setEmamReza(emamReza);
        model.setNoeHokmeKar(noeHokmeKars.stream().filter(o -> o.getId() == noeHokmeKar).findFirst().orElse(null));
        model.setYegan(yegans.stream().filter(o -> o.getId() == yegan).findFirst().orElse(null));
        model.setArjaiyatHokmeKar(arjaiyatHokmeKars.stream().filter(o -> o.getId() == arjaiyatHokmeKar).findFirst().orElse(null));
        model.setVaziyatAnjamHokmeKar(vaziyatAnjamHokmeKars.stream().filter(o -> o.getId() == vaziyatAnjamHokmeKar).findFirst().orElse(null));
        model.setAnjamDahandeHokmeKar(anjamDahandeHokmeKars.stream().filter(o -> o.getId() == anjamDahandeHokmeKar).findFirst().orElse(null));
        model.setNoeKareHokmeKar(noeKareHokmeKars.stream().filter(o -> o.getId() == noeKareHokmeKar).findFirst().orElse(null));

        SabtHokmeKarDao.getInstance().getBaseQuery().createOrUpdate(model, URL);

        alert.successEdit();

        savabegh.remove(selectMode);
        savabegh.add(model);

        nuller();
    }

    public String textEmamReza(Boolean checked){
        if (checked == null){
            return "ندارد";
        }
        if(checked){
            return "دارد";
        } else {
            return "ندارد";
        }
    }

    public void startDelete(SabtHokmeKar model){
        selectMode = model;
    }

    public void cancelDelete(){
        selectMode = null;
    }

    public void delete(){
        SabtHokmeKarDao.getInstance().getBaseQuery().delete(selectMode);
        savabegh.remove(selectMode);
        alert.successDelete();
        selectMode = null;
        nuller();
    }


    // GETTER AND SETTER

    public List<SabtHokmeKar> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<SabtHokmeKar> savabegh) {
        this.savabegh = savabegh;
    }

    public List<TypeHokmeKar> getNoeHokmeKars() {
        return noeHokmeKars;
    }

    public void setNoeHokmeKars(List<TypeHokmeKar> noeHokmeKars) {
        this.noeHokmeKars = noeHokmeKars;
    }

    public List<Bakhsh> getYegans() {
        return yegans;
    }

    public void setYegans(List<Bakhsh> yegans) {
        this.yegans = yegans;
    }

    public List<ArjaiyatHokmeKar> getArjaiyatHokmeKars() {
        return arjaiyatHokmeKars;
    }

    public void setArjaiyatHokmeKars(List<ArjaiyatHokmeKar> arjaiyatHokmeKars) {
        this.arjaiyatHokmeKars = arjaiyatHokmeKars;
    }

    public List<VaziyatAnjamHokmeKar> getVaziyatAnjamHokmeKars() {
        return vaziyatAnjamHokmeKars;
    }

    public void setVaziyatAnjamHokmeKars(List<VaziyatAnjamHokmeKar> vaziyatAnjamHokmeKars) {
        this.vaziyatAnjamHokmeKars = vaziyatAnjamHokmeKars;
    }

    public List<AnjamDahandeHokmeKar> getAnjamDahandeHokmeKars() {
        return anjamDahandeHokmeKars;
    }

    public void setAnjamDahandeHokmeKars(List<AnjamDahandeHokmeKar> anjamDahandeHokmeKars) {
        this.anjamDahandeHokmeKars = anjamDahandeHokmeKars;
    }

    public List<TypeKarHokmeKar> getNoeKareHokmeKars() {
        return noeKareHokmeKars;
    }

    public void setNoeKareHokmeKars(List<TypeKarHokmeKar> noeKareHokmeKars) {
        this.noeKareHokmeKars = noeKareHokmeKars;
    }

    public String getShomareDarkhast() {
        return shomareDarkhast;
    }

    public void setShomareDarkhast(String shomareDarkhast) {
        this.shomareDarkhast = shomareDarkhast;
    }

    public String getHozeKari() {
        return hozeKari;
    }

    public void setHozeKari(String hozeKari) {
        this.hozeKari = hozeKari;
    }

    public String getCodeShenavar() {
        return codeShenavar;
    }

    public void setCodeShenavar(String codeShenavar) {
        this.codeShenavar = codeShenavar;
    }

    public String getTarikhSabt() {
        return tarikhSabt;
    }

    public void setTarikhSabt(String tarikhSabt) {
        this.tarikhSabt = tarikhSabt;
    }

    public String getTarikhDarkhast() {
        return tarikhDarkhast;
    }

    public void setTarikhDarkhast(String tarikhDarkhast) {
        this.tarikhDarkhast = tarikhDarkhast;
    }

    public String getSharh() {
        return sharh;
    }

    public void setSharh(String sharh) {
        this.sharh = sharh;
    }

    public Boolean getEmamReza() {
        return emamReza;
    }

    public void setEmamReza(Boolean emamReza) {
        this.emamReza = emamReza;
    }

    public Long getNoeHokmeKar() {
        return noeHokmeKar;
    }

    public void setNoeHokmeKar(Long noeHokmeKar) {
        this.noeHokmeKar = noeHokmeKar;
    }

    public Long getYegan() {
        return yegan;
    }

    public void setYegan(Long yegan) {
        this.yegan = yegan;
    }

    public Long getArjaiyatHokmeKar() {
        return arjaiyatHokmeKar;
    }

    public void setArjaiyatHokmeKar(Long arjaiyatHokmeKar) {
        this.arjaiyatHokmeKar = arjaiyatHokmeKar;
    }

    public Long getVaziyatAnjamHokmeKar() {
        return vaziyatAnjamHokmeKar;
    }

    public void setVaziyatAnjamHokmeKar(Long vaziyatAnjamHokmeKar) {
        this.vaziyatAnjamHokmeKar = vaziyatAnjamHokmeKar;
    }

    public Long getAnjamDahandeHokmeKar() {
        return anjamDahandeHokmeKar;
    }

    public void setAnjamDahandeHokmeKar(Long anjamDahandeHokmeKar) {
        this.anjamDahandeHokmeKar = anjamDahandeHokmeKar;
    }

    public Long getNoeKareHokmeKar() {
        return noeKareHokmeKar;
    }

    public void setNoeKareHokmeKar(Long noeKareHokmeKar) {
        this.noeKareHokmeKar = noeKareHokmeKar;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
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

    public String getSearchCodeShenavar() {
        return searchCodeShenavar;
    }

    public void setSearchCodeShenavar(String searchCodeShenavar) {
        this.searchCodeShenavar = searchCodeShenavar;
    }

    public String getSearchTarikhDarkhast() {
        return searchTarikhDarkhast;
    }

    public void setSearchTarikhDarkhast(String searchTarikhDarkhast) {
        this.searchTarikhDarkhast = searchTarikhDarkhast;
    }

    public Long getSearchArjaiyatHokmeKar() {
        return searchArjaiyatHokmeKar;
    }

    public void setSearchArjaiyatHokmeKar(Long searchArjaiyatHokmeKar) {
        this.searchArjaiyatHokmeKar = searchArjaiyatHokmeKar;
    }

    public Long getSearchYegan() {
        return searchYegan;
    }

    public void setSearchYegan(Long searchYegan) {
        this.searchYegan = searchYegan;
    }

    public Long getSearchAnjamDahandeHokmeKar() {
        return searchAnjamDahandeHokmeKar;
    }

    public void setSearchAnjamDahandeHokmeKar(Long searchAnjamDahandeHokmeKar) {
        this.searchAnjamDahandeHokmeKar = searchAnjamDahandeHokmeKar;
    }

    public Long getSearchVaziyatAnjamHokmeKar() {
        return searchVaziyatAnjamHokmeKar;
    }

    public void setSearchVaziyatAnjamHokmeKar(Long searchVaziyatAnjamHokmeKar) {
        this.searchVaziyatAnjamHokmeKar = searchVaziyatAnjamHokmeKar;
    }
}