package manage.bean;

import amar.model.Personel;
import baseCode.alert.Alert;
import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.role.Role;
import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.PermissionDao;
import dataBaseModel.dao.SubPermissionDao;
import dataBaseModel.dao.UserDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import manage.model.PermissionModel;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.context.RequestContext;
import util.FillList;
import util.Password;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class CreateUserBean implements Serializable {
    private String URL;

    private List<User> savabegh = new ArrayList<>();

    private List<Role> roles = new ArrayList<>();
    private List<Form> forms = new ArrayList<>();
    private List<NoeEstekhdam> noePersonels = new ArrayList<>();
    private List<Dayere> dayeres = new ArrayList<>();
    private List<Bakhsh> bakhshes = new ArrayList<>();

    private List<PermissionModel> permissionModels = new ArrayList<>();

    private Alert alert = new Alert();

    // search field
    private String roleCodeSearch;
    private String noePersonelCodeSearch;
    private String dayereCodeSearch;
    private String shpSearch;
    private String codeMeliSearch;
    private String usernameSearch;

    private String shp;
    private String cm;
    private Personel personel = null;
    private String moshakhasat;
    private String userName;
    private String roleCode;

    private String[] formCodes;
    private String[] noePersonelCodes;
    private String[] dayereCodes;
    private String[] bakhshCodes;
    private boolean create = false;
    private boolean read = false;
    private boolean virayesh = false;
    private boolean delete = false;

    private User selectMode = null;
    private boolean edited = false;

    private PermissionModel permissionModel;
    private User userModel;

    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;

    private String userNameHide = "";


    public CreateUserBean() {
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
        dayeres = fillList.dayeresForAdmin(permissions.get(0));
        noePersonels = fillList.noeEstekhdams(permissions.get(0));
        forms = fillList.forms(IndexBean.user);
        bakhshes = fillList.bakhsh(permissions.get(0));

        Session session = HibernateUtil.getSession();
        roles = session.createQuery("FROM Role").list();
//        dayeres = session.createQuery("FROM Dayere").list();
//        noePersonels = session.createQuery("FROM NoeEstekhdam").list();
//        forms = session.createQuery("FROM Form").list();
        session.close();
    }

    public void fillBakhsh(String[] codes){
        bakhshes.clear();
        if(codes != null){
            for (String code : codes) {
                Session session = HibernateUtil.getSession();
                bakhshes.addAll(session.createQuery("FROM Bakhsh WHERE dayere.code = ?").setString(0, code).list());
                session.close();
            }
        }
    }

    public void search(){
        SessionUtil sessionUtil = new SessionUtil();
        User user = sessionUtil.getPermission().getUser();
        StringBuilder query = new StringBuilder("FROM User WHERE parentUser = '").append(user.getUserName()).append("' ");

        if(roleCodeSearch != null && !roleCodeSearch.equals("")){
            query.append("AND role.code = '").append(roleCodeSearch).append("' ");
        }

        if(noePersonelCodeSearch != null && !noePersonelCodeSearch.equals("")){
            query.append("AND personel.noeEstekhdam.code = '").append(noePersonelCodeSearch).append("' ");
        }

        if(dayereCodeSearch != null && !dayereCodeSearch.equals("")){
            query.append("AND personel.dayere.code = '").append(dayereCodeSearch).append("' ");
        }

        if(shpSearch != null && !shpSearch.equals("")){
            query.append("AND personel.shomarePersoneli = '").append(codeMeliSearch).append("' ");
        }

        if(codeMeliSearch != null && !codeMeliSearch.equals("")){
            query.append("AND personel.codeMeli = '").append(codeMeliSearch).append("' ");
        }

        if(usernameSearch != null && !usernameSearch.equals("")){
            query.append("AND userName LIKE '%").append(usernameSearch).append("%' ");
        }

        Session session = HibernateUtil.getSession();
        savabegh = session.createQuery(query.toString()).list();
        session.close();
    }

    public void findPersonel(){
        FillList fillList = new FillList();
        List<Personel> personels = fillList.personels(permissions.get(0), shp, "", "", "");

        if(personels.size() > 0){
            fillMoshakhasat(personels.get(0));
            cm = personel.getCodeMeli();
        } else {
            personel = null;
            shp = null;
            cm = null;
            moshakhasat = null;
            alert.notFoundPersonel();
        }
    }

    public void findPersonelByCodeMeli(){
        FillList fillList = new FillList();
        List<Personel> personels = fillList.personels(permissions.get(0), "", cm, "", "");

        if(personels.size() > 0){
            fillMoshakhasat(personels.get(0));
            shp = personel.getShomarePersoneli();
        } else {
            personel = null;
            shp = null;
            cm = null;
            moshakhasat = null;
            alert.notFoundPersonel();
        }
    }

    private void fillMoshakhasat(Personel p){
        personel = p;
        if(personel.getDaraje() != null) {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan() + "     درجه :" + personel.getDaraje().getTitle();
        } else {
            moshakhasat = "نام و نشان: " + personel.getName() + " " + personel.getNeshan();
        }
    }

    public void save(){
        if(personel != null){
            if(userName != null && !userName.equals("")){
                if(!checkUserName(userName)){
                    if(roleCode != null && !roleCode.equals("")){
                        User user = new User();

                        user.setPersonel(personel);
                        user.setUserName(userName);
                        user.setParentUser(IndexBean.user.getUserName());
                        user.setPassWord(BCrypt.hashpw(Password.defaultPass, Password.saltBcrypt));
                        user.setRole(roles.stream().filter(o -> o.getCode().equals(roleCode)).findFirst().orElse(null));
                        user.setActive(true);
                        user.setOnline(false);
                        user.setFaild(0);

                        UserDao.getInstance().getBaseQuery().create(user, URL);

                        userNameHide = userName;

                        nuller();

                        alert.successSave();

                        RequestContext.getCurrentInstance().execute("PF('create_access_dlg').show()");
                    } else {
                        alert.fieldIsEmpty("نقش کاربری");
                    }

                } else {
                    alert.userDuplicate();
                }
            } else {
                alert.fieldIsEmpty("نام کاربری");
            }
        } else {
            alert.fieldIsEmpty("شماره پرسنلی");
        }
    }

    private boolean checkUserName(String name){
        Session session = HibernateUtil.getSession();
        List<User> users = session.createQuery("FROM User WHERE userName = ?").setString(0, name).list();
        session.close();

        return users.size() == 1;
    }

    public void dispach(User user){
        shp = user.getPersonel().getShomarePersoneli();
        cm = user.getPersonel().getCodeMeli();
        personel = user.getPersonel();
        userName = user.getUserName();
        roleCode = user.getRole().getCode();

        fillMoshakhasat(personel);

        selectMode = user;
        edited = true;
    }

    public void edit(){
        if(personel != null){
            if(roleCode != null && !roleCode.equals("")){
                User user = selectMode;

                user.setPersonel(personel);
                user.setRole(roles.stream().filter(o -> o.getCode().equals(roleCode)).findFirst().orElse(null));

                UserDao.getInstance().getBaseQuery().createOrUpdate(user, URL);

                nuller();
                alert.successEdit();
            } else {
                alert.fieldIsEmpty("نقش کاربری");
            }
        } else {
            alert.fieldIsEmpty("شماره پرسنلی");
        }
    }

    public void nuller() {
        shp = "";
        personel = null;
        cm = "";
        moshakhasat = null;

        userName = "";
        roleCode = "";

        edited = false;
        selectMode = null;

        formCodes = null;
        noePersonelCodes = null;
        dayereCodes = null;
        bakhshCodes = null;
        create = false;
        read = false;
        virayesh = false;
        delete = false;
        permissionModel = null;
    }

    public String status(boolean st){
        if(st){
            return "فعال";
        } else {
            return "غیرفعال";
        }
    }

    public void setUser(User user){
        selectMode = user;
    }

    public void clearUser(){
        selectMode = null;
    }

    public void changeActivity(User user){
        user.setActive(!user.getActive());
        UserDao.getInstance().getBaseQuery().createOrUpdate(user, URL);
        alert.successEdit();
        clearUser();
    }

    public void laghv(User user){
        user.setOnline(false);
        user.setFaild(0);
        user.setFaildTime(null);
        UserDao.getInstance().getBaseQuery().createOrUpdate(user, URL);
        alert.successEdit();
        clearUser();
    }
    
    public void createPermission(User user){
        if(user == null){
            Session session = HibernateUtil.getSession();
            user = (User) session.createQuery("FROM User WHERE userName = ?").setString(0, userNameHide).list().get(0);
            session.close();
        }
        userNameHide = "";

        if(formCodes != null){
            for (String formCode : formCodes) {
                Form form = forms.stream().filter(o -> o.getCode().equals(formCode)).findFirst().orElse(null);
                Permission pm = new Permission();

                Session session = HibernateUtil.getSession();
                List<Permission> permissionList = session.createQuery("FROM Permission WHERE user.id = ? AND form.id = ?")
                        .setLong(0, user.getId())
                        .setLong(1, form.getId())
                        .list();
                session.close();

                if(permissionList.size() == 1){
                    pm = permissionList.get(0);

                    pm.setCreat(create);
                    pm.setReaad(read);
                    pm.setUpdat(virayesh);
                    pm.setDelet(delete);

                    PermissionDao.getInstance().getBaseQuery().createOrUpdate(pm, URL);
                } else {
                    pm.setUser(user);
                    pm.setForm(form);

                    pm.setCreat(create);
                    pm.setReaad(read);
                    pm.setUpdat(virayesh);
                    pm.setDelet(delete);

                    pm.setActive(true);

                    PermissionDao.getInstance().getBaseQuery().create(pm, URL);
                }

                Session session1 = HibernateUtil.getSession();
                Permission permission = (Permission) session1.createQuery("FROM Permission WHERE user.id = ? AND form.id = ?")
                        .setLong(0, user.getId())
                        .setLong(1, form.getId())
                        .list().get(0);
                session1.close();

                if(noePersonelCodes != null){
                    for (String noePersonelCode : noePersonelCodes) {
                        NoeEstekhdam noPersonel = noePersonels.stream().filter(o -> o.getCode().equals(noePersonelCode)).findFirst().orElse(null);
                        if(bakhshCodes != null && bakhshCodes.length > 0){
                            for (String bakhshCode : bakhshCodes) {
                                Bakhsh bakhsh = bakhshes.stream().filter(o -> o.getCode().equals(bakhshCode)).findFirst().orElse(null);
                                SubPermission subPermission = new SubPermission();

                                Session session2 = HibernateUtil.getSession();
                                List<SubPermission> subPermissions = session2.createQuery("FROM SubPermission WHERE permission.id = ? AND noePersonel.id = ? AND bakhsh.id =?")
                                        .setLong(0, permission.getId())
                                        .setLong(1, noPersonel.getId())
                                        .setLong(2, bakhsh.getId())
                                        .list();
                                session2.close();

                                if(subPermissions.size() == 0){
                                    subPermission.setPermission(permission);
                                    subPermission.setNoePersonel(noPersonel);
                                    subPermission.setDayere(bakhsh.getDayere());
                                    subPermission.setBakhsh(bakhsh);

                                    SubPermissionDao.getInstance().getBaseQuery().create(subPermission, URL);
                                }

                            }
                        } else if(dayereCodes != null && dayereCodes.length > 0){
                            for (String dayereCode : dayereCodes) {
                                Dayere dayere = dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                                SubPermission subPermission = new SubPermission();

                                Session session2 = HibernateUtil.getSession();
                                List<SubPermission> subPermissions = session2.createQuery("FROM SubPermission WHERE permission.id = ? AND noePersonel.id = ? AND dayere.id =?")
                                        .setLong(0, permission.getId())
                                        .setLong(1, noPersonel.getId())
                                        .setLong(2, dayere.getId())
                                        .list();
                                session2.close();

                                if(subPermissions.size() == 0){
                                    subPermission.setPermission(permission);
                                    subPermission.setNoePersonel(noPersonel);
                                    subPermission.setDayere(dayere);
                                    subPermission.setBakhsh(null);

                                    SubPermissionDao.getInstance().getBaseQuery().create(subPermission, URL);
                                }

                            }
                        }
                    }  
                }
                if(bakhshCodes != null && bakhshCodes.length > 0){
                    for (String bakhshCode : bakhshCodes) {
                        Bakhsh bakhsh = bakhshes.stream().filter(o -> o.getCode().equals(bakhshCode)).findFirst().orElse(null);
                        SubPermission subPermission = new SubPermission();

                        Session session2 = HibernateUtil.getSession();
                        List<SubPermission> subPermissions = session2.createQuery("FROM SubPermission WHERE permission.id = ? AND bakhsh.id =?")
                                .setLong(0, permission.getId())
                                .setLong(1, bakhsh.getId())
                                .list();
                        session2.close();

                        if(subPermissions.size() == 0){
                            subPermission.setPermission(permission);
                            subPermission.setDayere(bakhsh.getDayere());
                            subPermission.setBakhsh(bakhsh);

                            SubPermissionDao.getInstance().getBaseQuery().create(subPermission, URL);
                        }

                    }
                } else if(dayereCodes != null && dayereCodes.length > 0){
                    for (String dayereCode : dayereCodes) {
                        Dayere dayere = dayeres.stream().filter(o -> o.getCode().equals(dayereCode)).findFirst().orElse(null);
                        SubPermission subPermission = new SubPermission();

                        Session session2 = HibernateUtil.getSession();
                        List<SubPermission> subPermissions = session2.createQuery("FROM SubPermission WHERE permission.id = ? AND dayere.id =?")
                                .setLong(0, permission.getId())
                                .setLong(1, dayere.getId())
                                .list();
                        session2.close();

                        if(subPermissions.size() == 0){
                            subPermission.setPermission(permission);
                            subPermission.setDayere(dayere);
                            subPermission.setBakhsh(null);

                            SubPermissionDao.getInstance().getBaseQuery().create(subPermission, URL);
                        }
                    }
                }
            }
        }
        nuller();
    }

    public void startDeletePermission(PermissionModel model){
        permissionModel = model;
    }

    public void startDeleteAllPermission(User model){
        userModel = model;
    }

    public void deleteAllPermission(User userModel){
        Session session = HibernateUtil.getSession();
        List<Permission> permissions = session.createQuery("FROM Permission WHERE user.id = ?").setLong(0, userModel.getId()).list();
        session.close();

        for (Permission permission : permissions) {
            Session session1 = HibernateUtil.getSession();
            List<SubPermission> subPermissionList = session1.createQuery("FROM SubPermission WHERE permission.id = ?").setLong(0, permission.getId()).list();
            session1.close();

            for (SubPermission subPermission : subPermissionList) {
                SubPermissionDao.getInstance().getBaseQuery().delete(subPermission, URL);
            }

            PermissionDao.getInstance().getBaseQuery().delete(permission, URL);
        }
    }

    public void deletePermission(PermissionModel permissionModel){
        Session session = HibernateUtil.getSession();
        List<SubPermission> subPermissionList = session.createQuery("FROM SubPermission WHERE permission.id = ?").setLong(0, permissionModel.getPermissionId()).list();
        SubPermission subPermission = (SubPermission) session.createQuery("FROM SubPermission WHERE id = ?").setLong(0, permissionModel.getId()).list().get(0);
        session.close();

        if(subPermissionList.size() == 1){
            SubPermissionDao.getInstance().getBaseQuery().delete(subPermission, URL);

            PermissionDao.getInstance().getBaseQuery().delete(subPermission.getPermission(), URL);
        } else {
            SubPermissionDao.getInstance().getBaseQuery().delete(subPermission, URL);
        }

        permissionModels.remove(permissionModel);
    }

    public void cancelDeletePermission(){
        permissionModel = null;
    }

    public void cancelDeleteAllPermission(){
        userModel = null;
    }

    public void fillAccessLevel(User user){
        Session session = HibernateUtil.getSession();
        List<Permission> permissions = session.createQuery("FROM Permission WHERE user.id = ?").setLong(0, user.getId()).list();
        session.close();

        for (Permission permission : permissions) {
            Session session1 = HibernateUtil.getSession();
            List<SubPermission> subPermissions = session1.createQuery("FROM SubPermission WHERE permission.id = ?").setLong(0, permission.getId()).list();
            session1.close();

            for (SubPermission subPermission : subPermissions) {
                PermissionModel permissionModel = new PermissionModel();

                permissionModel.setId(subPermission.getId());
                permissionModel.setPermissionId(permission.getId());
                permissionModel.setUser(permission.getUser());
                permissionModel.setForm(permission.getForm());
                permissionModel.setCreat(permission.getCreat());
                permissionModel.setReaad(permission.getReaad());
                permissionModel.setUpdat(permission.getUpdat());
                permissionModel.setDelet(permission.getDelet());
                permissionModel.setNoePersonel(subPermission.getNoePersonel());
                permissionModel.setDayere(subPermission.getDayere());
                permissionModel.setBakhsh(subPermission.getBakhsh());
                permissionModel.setActive(permission.getActive());

                permissionModels.add(permissionModel);
            }
        }
    }

    public String access(boolean st){
        if(st){
            return "دارد";
        } else {
            return "ندارد";
        }
    }



    // GETTER AND SETTER


    public List<User> getSavabegh() {
        return savabegh;
    }

    public void setSavabegh(List<User> savabegh) {
        this.savabegh = savabegh;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<NoeEstekhdam> getNoePersonels() {
        return noePersonels;
    }

    public void setNoePersonels(List<NoeEstekhdam> noePersonels) {
        this.noePersonels = noePersonels;
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

    public String getRoleCodeSearch() {
        return roleCodeSearch;
    }

    public void setRoleCodeSearch(String roleCodeSearch) {
        this.roleCodeSearch = roleCodeSearch;
    }

    public String getNoePersonelCodeSearch() {
        return noePersonelCodeSearch;
    }

    public void setNoePersonelCodeSearch(String noePersonelCodeSearch) {
        this.noePersonelCodeSearch = noePersonelCodeSearch;
    }

    public String getDayereCodeSearch() {
        return dayereCodeSearch;
    }

    public void setDayereCodeSearch(String dayereCodeSearch) {
        this.dayereCodeSearch = dayereCodeSearch;
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

    public String getUsernameSearch() {
        return usernameSearch;
    }

    public void setUsernameSearch(String usernameSearch) {
        this.usernameSearch = usernameSearch;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public String getMoshakhasat() {
        return moshakhasat;
    }

    public void setMoshakhasat(String moshakhasat) {
        this.moshakhasat = moshakhasat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public User getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(User selectMode) {
        this.selectMode = selectMode;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public List<PermissionModel> getPermissionModels() {
        return permissionModels;
    }

    public void setPermissionModels(List<PermissionModel> permissionModels) {
        this.permissionModels = permissionModels;
    }

    public String[] getFormCodes() {
        return formCodes;
    }

    public void setFormCodes(String[] formCodes) {
        this.formCodes = formCodes;
    }

    public String[] getNoePersonelCodes() {
        return noePersonelCodes;
    }

    public void setNoePersonelCodes(String[] noePersonelCodes) {
        this.noePersonelCodes = noePersonelCodes;
    }

    public String[] getDayereCodes() {
        return dayereCodes;
    }

    public void setDayereCodes(String[] dayereCodes) {
        this.dayereCodes = dayereCodes;
    }

    public String[] getBakhshCodes() {
        return bakhshCodes;
    }

    public void setBakhshCodes(String[] bakhshCodes) {
        this.bakhshCodes = bakhshCodes;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isVirayesh() {
        return virayesh;
    }

    public void setVirayesh(boolean virayesh) {
        this.virayesh = virayesh;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public PermissionModel getPermissionModel() {
        return permissionModel;
    }

    public void setPermissionModel(PermissionModel permissionModel) {
        this.permissionModel = permissionModel;
    }

    public User getUserModel() {
        return userModel;
    }

    public void setUserModel(User userModel) {
        this.userModel = userModel;
    }
}