package amar.bean;

import amar.model.Personel;
import amar.model.amarRozaneJireBegir.*;
import amar.model.SavabeghAmarJireBegirRozane;
import baseCode.alert.Alert;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.ULocale;
import com.microsoft.schemas.office.x2006.encryption.CTKeyEncryptor;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.*;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import ezafekari.model.Jire;
import manage.bean.IndexBean;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import omorkoliAndgharardadi.model.SavabeghMamoriyatRozaneModel;
import org.hibernate.Session;
import org.primefaces.component.export.ExcelExporter;
import util.FillList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.StreamFilter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class SavabeghAmarRozaneJireBegirBean implements Serializable {

    private String URL = "amar/SavabeghAmarRozaneJireBegir.xhtml";

    private List<SavabeghAmarJireBegirRozane> dataTable = new ArrayList();
    private List<Taghvim> taghvims = new ArrayList<>();
    private SavabeghAmarJireBegirRozane amar;
    private SessionUtil util = new SessionUtil();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new ULocale("fa", "IR"));
    private AmarAfsaranYekVaDo amarAfsaranYekVaDo;
    private AmarAfsaranSe amarAfsaranSe;
    private AmarDarajeDaran amarDarajeDaran;
    private AmarKarmandanTajrobi amarKarmandanTajrobi;
    private AmarKarmandanElmi amarKarmandanElmi;
    private AmarAfsarVazife amarAfsarVazife;
    private AmarDaneshjoVazife amarDaneshjoVazife;
    private AmarDarajeDarVazife amarDarajeDarVazife;
    private AmarNavi amarNavi;

    private String tarikhSabtFilter;
    private Long dayereIdFilter;
    private Long bakhshIdFilter;
    private boolean disableDayere = false;


    //afYekVaDo
    private int nEdeKolAfsarYekVaDo;
    private int nMorakhasiAfsarYekVaDo;
    private int nBastariAfsarYekVaDo;
    private int nNahastAfsarYekVaDo;
    private int nFararAfsarYekVaDo;
    private int nBazdashtAfsarYekVaDo;
    private int nMontazerBekhedmatAfsarYekVaDo;
    private int nMamorAzEdeAfsarYekVaDo;
    private int nJameMotafaregheAfsarYekVaDo;
    private int nMamorBeEdeAfsarYekVaDo;
    private int nJameHazerAfsarYekVaDo;
    private int nSobhaneAfsarYekVaDo;
    private int nNaharAfsarYekVaDo;
    private int nShamAfsarYekVaDo;

    //afSe
    private int nEdeKolAfsarSe;
    private int nMorakhasiAfsarSe;
    private int nBastariAfsarSe;
    private int nNahastAfsarSe;
    private int nFararAfsarSe;
    private int nBazdashtAfsarSe;
    private int nMontazerBekhedmatAfsarSe;
    private int nMamorAzEdeAfsarSe;
    private int nJameMotafaregheAfsarSe;
    private int nMamorBeEdeAfsarSe;
    private int nJameHazerAfsarSe;
    private int nSobhaneAfsarSe;
    private int nNaharAfsarSe;
    private int nShamAfsarSe;

    //Darajedar
    private int nEdeKolDarajedar;
    private int nMorakhasiDarajedar;
    private int nBastariDarajedar;
    private int nNahastDarajedar;
    private int nFararDarajedar;
    private int nBazdashtDarajedar;
    private int nMontazerBekhedmatDarajedar;
    private int nMamorAzEdeDarajedar;
    private int nJameMotafaregheDarajedar;
    private int nMamorBeEdeDarajedar;
    private int nJameHazerDarajedar;
    private int nSobhaneDarajedar;
    private int nNaharDarajedar;
    private int nShamDarajedar;


    //karElmi
    private int nEdeKolKarmandElmi;
    private int nMorakhasiKarmandElmi;
    private int nBastariKarmandElmi;
    private int nNahastKarmandElmi;
    private int nFararKarmandElmi;
    private int nBazdashtKarmandElmi;
    private int nMontazerBekhedmatKarmandElmi;
    private int nMamorAzEdeKarmandElmi;
    private int nJameMotafaregheKarmandElmi;
    private int nMamorBeEdeKarmandElmi;
    private int nJameHazerKarmandElmi;
    private int nSobhaneKarmandElmi;
    private int nNaharKarmandElmi;
    private int nShamKarmandElmi;

    //karTajrobi
    private int nEdeKolKarmandTajrobi;
    private int nMorakhasiKarmandTajrobi;
    private int nBastariKarmandTajrobi;
    private int nNahastKarmandTajrobi;
    private int nFararKarmandTajrobi;
    private int nBazdashtKarmandTajrobi;
    private int nMontazerBekhedmatKarmandTajrobi;
    private int nMamorAzEdeKarmandTajrobi;
    private int nJameMotafaregheKarmandTajrobi;
    private int nMamorBeEdeKarmandTajrobi;
    private int nJameHazerKarmandTajrobi;
    private int nSobhaneKarmandTajrobi;
    private int nNaharKarmandTajrobi;
    private int nShamKarmandTajrobi;

    //vazAfsar
    private int nEdeKolVazifeAfsar;
    private int nMorakhasiVazifeAfsar;
    private int nBastariVazifeAfsar;
    private int nNahastVazifeAfsar;
    private int nFararVazifeAfsar;
    private int nBazdashtVazifeAfsar;
    private int nMontazerBekhedmatVazifeAfsar;
    private int nMamorAzEdeVazifeAfsar;
    private int nJameMotafaregheVazifeAfsar;
    private int nMamorBeEdeVazifeAfsar;
    private int nJameHazerVazifeAfsar;
    private int nSobhaneVazifeAfsar;
    private int nNaharVazifeAfsar;
    private int nShamVazifeAfsar;

    //vazDaneshjo
    private int nEdeKolVazifeDaneshjo;
    private int nMorakhasiVazifeDaneshjo;
    private int nBastariVazifeDaneshjo;
    private int nNahastVazifeDaneshjo;
    private int nFararVazifeDaneshjo;
    private int nBazdashtVazifeDaneshjo;
    private int nMontazerBekhedmatVazifeDaneshjo;
    private int nMamorAzEdeVazifeDaneshjo;
    private int nJameMotafaregheVazifeDaneshjo;
    private int nMamorBeEdeVazifeDaneshjo;
    private int nJameHazerVazifeDaneshjo;
    private int nSobhaneVazifeDaneshjo;
    private int nNaharVazifeDaneshjo;
    private int nShamVazifeDaneshjo;

    //vazDarajeDar
    private int nEdeKolVazifeDarajedar;
    private int nMorakhasiVazifeDarajedar;
    private int nBastariVazifeDarajedar;
    private int nNahastVazifeDarajedar;
    private int nFararVazifeDarajedar;
    private int nBazdashtVazifeDarajedar;
    private int nMontazerBekhedmatVazifeDarajedar;
    private int nMamorAzEdeVazifeDarajedar;
    private int nJameMotafaregheVazifeDarajedar;
    private int nMamorBeEdeVazifeDarajedar;
    private int nJameHazerVazifeDarajedar;
    private int nSobhaneVazifeDarajedar;
    private int nNaharVazifeDarajedar;
    private int nShamVazifeDarajedar;

    //vazNavi
    private int nEdeKolNavi;
    private int nMorakhasiNavi;
    private int nBastariNavi;
    private int nNahastNavi;
    private int nFararNavi;
    private int nBazdashtNavi;
    private int nMontazerBekhedmatNavi;
    private int nMamorAzEdeNavi;
    private int nJameMotafaregheNavi;
    private int nMamorBeEdeNavi;
    private int nJameHazerNavi;
    private int nSobhaneNavi;
    private int nNaharNavi;
    private int nShamNavi;

    ///


    private int nModavematKari;
    private int nEzafekari;
    private String molahezat = "";
    private String molahezatNew = "";
    private int tedadJireNahar;
    private int nAzMorakhasiPayvar;
    private int nBeMorakhasiPayvar;
    private int nAzNahastPayvar;
    private int nBeNahastPayvar;
    private int nAzBastariPayvar;
    private int nBeBastariPayvar;
    private int nAzMamoriyatPayvar;
    private int nBeMamoriyatPayvar;
    private int nKasrAzAmarPayvar;
    private int nEzafBeAmarPayvar;
    private int nEzafBeMamorBeEdePayvar;
    private int nKasrAzMamorBeEdePayvar;
    private int nBeEntezarBeKhedmatPayvar;
    private int nAzEntezarKhedmatPayvar;
    private int nBeBazdashtPayvar;
    private int nAzBazdashtPayvar;
    private int nAzMorakhasiVazife;
    private int nBeMorakhasiVazife;
    private int nAzNahastVazife;
    private int nBeNahastVazife;
    private int nAzBastariVazife;
    private int nBeBastariVazife;
    private int nAzMamoriyatVazife;
    private int nBeMamoriyatVazife;
    private int nKasrAzAmarVazife;
    private int nEzafBeAmarVazife;
    private int nEzafBeMamorBeEdeVazife;
    private int nKasrAzMamorBeEdeVazife;
    private int nBeEntezarBeKhedmatVazife;
    private int nAzEntezarKhedmatVazife;
    private int nBeBazdashtVazife;
    private int nAzBazdashtVazife;

    private Dayere dayere;
    private List<Dayere> dayereList = new ArrayList<>();
    private Bakhsh bakhsh;
    private List<Bakhsh> bakhshList = new ArrayList<>();

    private List<NoeEstekhdam> noeEstekhdamList = new ArrayList<>();

    private List<Personel> personelList = new ArrayList<>();
    private List<Personel> selectedPersonelList = new ArrayList<>();
    private List<Jire> jireList = new ArrayList<>();


    private Taghvim taghvim;
    private boolean showErja = false;
    private boolean showCrud = false;

    private int tabIndex = 0;


    private String dateReport = "";
    private Long dayereReport;
    private Long bakhshReport;

    private int nEdeKolJame;
    private int nMorakhasiJame;
    private int nBastariJame;
    private int nNahastJame;
    private int nFararJame;
    private int nBazdashtJame;
    private int nMontazerBekhedmatJame;
    private int nMamorAzEdeJame;
    private int nJameMotafaregheJame;
    private int nMamorBeEdeJame;
    private int nJameHazerJame;
    private int nSobhaneJame;
    private int nNaharJame;
    private int nShamJame;
    private String sarparasteShobeAmar = "";
    private String farmandeYegan = "";
    private String sarparasteShobeAmarNiroEnsani = "";

    /////
    private Permission permission;
    private List<Permission> permissions;
    private boolean createPermission = false;
    private boolean readPermission = false;
    private boolean updatePermission = false;
    private boolean deletePermission = false;
///////


    public SavabeghAmarRozaneJireBegirBean() {
        URL = IndexBean.url;
        permissions = IndexBean.permissions;
        if (permissions.size() > 0) {
            createPermission = permissions.get(0).getCreat();
            readPermission = permissions.get(0).getReaad();
            updatePermission = permissions.get(0).getUpdat();
            deletePermission = permissions.get(0).getDelet();
        }
        addToList();
        addListNahar();
    }


    public void addToList() {
        String queryPermissionDayere = "";
        String queryPermissionBakhsh = "";
        String tarikh = dateFormat.format(new Date());
        FillList fillList = new FillList();
        dayereList = fillList.dayeres(permissions.get(0));
        bakhshList = fillList.bakhsh(permissions.get(0));
        noeEstekhdamList = fillList.noeEstekhdams(permissions.get(0));

        Session session = HibernateUtil.getSession();
        List<SubPermission> subPermissions = session.createQuery("FROM SubPermission WHERE permission.id = ?").setLong(0, permissions.get(0).getId()).list();
        if (subPermissions.size() > 0) {
            if (subPermissions.get(0).getDayere() != null) {
                dayere = subPermissions.get(0).getDayere();
            }
            if (subPermissions.get(0).getBakhsh() != null) {
                bakhsh = subPermissions.get(0).getBakhsh();
            }
        }
        if (dayere != null && bakhsh == null) {
            if (dayere.getCode().equals("0000")) {
                dataTable = session.createQuery("from SavabeghAmarJireBegirRozane where taghvim.tarikh ='" + tarikh + "'").list();
            } else {
                dataTable = session.createQuery("from SavabeghAmarJireBegirRozane where taghvim.tarikh ='" + tarikh + "' AND dayere.id=" + dayere.getId()).list();
            }
        } else if (bakhsh != null) {
            dataTable = session.createQuery("from SavabeghAmarJireBegirRozane where taghvim.tarikh ='" + tarikh + "' AND bakhsh.id=" + bakhsh.getId()).list();
        }
        if (bakhsh != null) {
            setDisableDayere(true);
        }
        taghvims = session.createQuery("from Taghvim where tarikh='" + dateFormat.format(new Date()) + "'").list();

        session.close();
        if (taghvims.size() > 0) {
            taghvim = taghvims.get(0);
        }


        // TODO: 1/11/2020 button bayad barasas role namayesh dahad edary farmande niroensani

    }

    public void addListNahar() {
        StringBuilder builder = new StringBuilder();
        builder.append("FROM Personel where active=1 ");

        if (noeEstekhdamList.size() > 0) {
            builder.append(" AND  ( ");
            int i = 0;
            for (NoeEstekhdam ne : noeEstekhdamList) {
                i = i + 1;
                if (noeEstekhdamList.size() == i) {
                    builder.append(" noeEstekhdam.id=" + ne.getId());
                } else {
                    builder.append(" noeEstekhdam.id=" + ne.getId() + " OR ");
                }
            }
            builder.append(" ) ");
        }


        if (bakhshList.size() > 0) {
            if (bakhshList.size() > 0) {
                builder.append(" AND  ( ");
                int i = 0;
                for (Bakhsh b : bakhshList) {
                    i = i + 1;
                    if (bakhshList.size() == i) {
                        builder.append(" bakhsh.id=" + b.getId());
                    } else {
                        builder.append(" bakhsh.id=" + b.getId() + " OR ");
                    }
                }
                builder.append(" ) ");
            }
        } else {
            if (dayereList.size() > 0) {
                builder.append(" AND  ( ");
                int i = 0;
                for (Dayere d : dayereList) {
                    i = i + 1;
                    if (dayereList.size() == i) {
                        builder.append(" dayere.id=" + d.getId());
                    } else {
                        builder.append(" dayere.id=" + d.getId() + " OR ");
                    }
                }
                builder.append(" ) ");
            }
        }

        Session session = HibernateUtil.getSession();
        personelList = session.createQuery(builder.toString()).list();
        session.close();


    }

    public void selectBakhsh(long id) {
        Session session = HibernateUtil.getSession();
        bakhshList = session.createQuery("from Bakhsh where dayere.id=" + id).list();
        session.close();
    }

    public void dispach(SavabeghAmarJireBegirRozane obj) {
        setShowCrud(true);
        amar = new SavabeghAmarJireBegirRozane();
        setAmar(obj);
        setnEdeKolAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnEdeKol());
        setnMorakhasiAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnMorakhasi());
        setnBastariAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnBastari());
        setnNahastAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnNahast());
        setnFararAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnFarar());
        setnBazdashtAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnBazdasht());
        setnMontazerBekhedmatAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnMontazerBekhedmat());
        setnMamorAzEdeAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnMamorAzEde());
        setnJameMotafaregheAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnJameMotafareghe());
        setnMamorBeEdeAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnMamorBeEde());
        setnJameHazerAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnJameHazer());
        setnSobhaneAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnSobhane());
        setnNaharAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnNahar());
        setnShamAfsarYekVaDo(obj.getAmarAfsaranYekVaDo().getnSham());

        setnEdeKolAfsarSe(obj.getAmarAfsaranSe().getnEdeKol());
        setnMorakhasiAfsarSe(obj.getAmarAfsaranSe().getnMorakhasi());
        setnBastariAfsarSe(obj.getAmarAfsaranSe().getnBastari());
        setnNahastAfsarSe(obj.getAmarAfsaranSe().getnNahast());
        setnFararAfsarSe(obj.getAmarAfsaranSe().getnFarar());
        setnBazdashtAfsarSe(obj.getAmarAfsaranSe().getnBazdasht());
        setnMontazerBekhedmatAfsarSe(obj.getAmarAfsaranSe().getnMontazerBekhedmat());
        setnMamorAzEdeAfsarSe(obj.getAmarAfsaranSe().getnMamorAzEde());
        setnJameMotafaregheAfsarSe(obj.getAmarAfsaranSe().getnJameMotafareghe());
        setnMamorBeEdeAfsarSe(obj.getAmarAfsaranSe().getnMamorBeEde());
        setnJameHazerAfsarSe(obj.getAmarAfsaranSe().getnJameHazer());
        setnSobhaneAfsarSe(obj.getAmarAfsaranSe().getnSobhane());
        setnNaharAfsarSe(obj.getAmarAfsaranSe().getnNahar());
        setnShamAfsarSe(obj.getAmarAfsaranSe().getnSham());

        setnEdeKolDarajedar(obj.getAmarDarajeDaran().getnEdeKol());
        setnMorakhasiDarajedar(obj.getAmarDarajeDaran().getnMorakhasi());
        setnBastariDarajedar(obj.getAmarDarajeDaran().getnBastari());
        setnNahastDarajedar(obj.getAmarDarajeDaran().getnNahast());
        setnFararDarajedar(obj.getAmarDarajeDaran().getnFarar());
        setnBazdashtDarajedar(obj.getAmarDarajeDaran().getnBazdasht());
        setnMontazerBekhedmatDarajedar(obj.getAmarDarajeDaran().getnMontazerBekhedmat());
        setnMamorAzEdeDarajedar(obj.getAmarDarajeDaran().getnMamorAzEde());
        setnJameMotafaregheDarajedar(obj.getAmarDarajeDaran().getnJameMotafareghe());
        setnMamorBeEdeDarajedar(obj.getAmarDarajeDaran().getnMamorBeEde());
        setnJameHazerDarajedar(obj.getAmarDarajeDaran().getnJameHazer());
        setnSobhaneDarajedar(obj.getAmarDarajeDaran().getnSobhane());
        setnNaharDarajedar(obj.getAmarDarajeDaran().getnNahar());
        setnShamDarajedar(obj.getAmarDarajeDaran().getnSham());

        setnEdeKolKarmandElmi(obj.getAmarKarmandanElmi().getnEdeKol());
        setnMorakhasiKarmandElmi(obj.getAmarKarmandanElmi().getnMorakhasi());
        setnBastariKarmandElmi(obj.getAmarKarmandanElmi().getnBastari());
        setnNahastKarmandElmi(obj.getAmarKarmandanElmi().getnNahast());
        setnFararKarmandElmi(obj.getAmarKarmandanElmi().getnFarar());
        setnBazdashtKarmandElmi(obj.getAmarKarmandanElmi().getnBazdasht());
        setnMontazerBekhedmatKarmandElmi(obj.getAmarKarmandanElmi().getnMontazerBekhedmat());
        setnMamorAzEdeKarmandElmi(obj.getAmarKarmandanElmi().getnMamorAzEde());
        setnJameMotafaregheKarmandElmi(obj.getAmarKarmandanElmi().getnJameMotafareghe());
        setnMamorBeEdeKarmandElmi(obj.getAmarKarmandanElmi().getnMamorBeEde());
        setnJameHazerKarmandElmi(obj.getAmarKarmandanElmi().getnJameHazer());
        setnSobhaneKarmandElmi(obj.getAmarKarmandanElmi().getnSobhane());
        setnNaharKarmandElmi(obj.getAmarKarmandanElmi().getnNahar());
        setnShamKarmandElmi(obj.getAmarKarmandanElmi().getnSham());

        setnEdeKolKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnEdeKol());
        setnMorakhasiKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnMorakhasi());
        setnBastariKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnBastari());
        setnNahastKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnNahast());
        setnFararKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnFarar());
        setnBazdashtKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnBazdasht());
        setnMontazerBekhedmatKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnMontazerBekhedmat());
        setnMamorAzEdeKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnMamorAzEde());
        setnJameMotafaregheKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnJameMotafareghe());
        setnMamorBeEdeKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnMamorBeEde());
        setnJameHazerKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnJameHazer());
        setnSobhaneKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnSobhane());
        setnNaharKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnNahar());
        setnShamKarmandTajrobi(obj.getAmarKarmandanTajrobi().getnSham());

        setnEdeKolVazifeAfsar(obj.getAmarAfsarVazife().getnEdeKol());
        setnMorakhasiVazifeAfsar(obj.getAmarAfsarVazife().getnMorakhasi());
        setnBastariVazifeAfsar(obj.getAmarAfsarVazife().getnBastari());
        setnNahastVazifeAfsar(obj.getAmarAfsarVazife().getnNahast());
        setnFararVazifeAfsar(obj.getAmarAfsarVazife().getnFarar());
        setnBazdashtVazifeAfsar(obj.getAmarAfsarVazife().getnBazdasht());
        setnMontazerBekhedmatVazifeAfsar(obj.getAmarAfsarVazife().getnMontazerBekhedmat());
        setnMamorAzEdeVazifeAfsar(obj.getAmarAfsarVazife().getnMamorAzEde());
        setnJameMotafaregheVazifeAfsar(obj.getAmarAfsarVazife().getnJameMotafareghe());
        setnMamorBeEdeVazifeAfsar(obj.getAmarAfsarVazife().getnMamorBeEde());
        setnJameHazerVazifeAfsar(obj.getAmarAfsarVazife().getnJameHazer());
        setnSobhaneVazifeAfsar(obj.getAmarAfsarVazife().getnSobhane());
        setnNaharVazifeAfsar(obj.getAmarAfsarVazife().getnNahar());
        setnShamVazifeAfsar(obj.getAmarAfsarVazife().getnSham());

        setnEdeKolVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnEdeKol());
        setnMorakhasiVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnMorakhasi());
        setnBastariVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnBastari());
        setnNahastVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnNahast());
        setnFararVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnFarar());
        setnBazdashtVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnBazdasht());
        setnMontazerBekhedmatVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnMontazerBekhedmat());
        setnMamorAzEdeVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnMamorAzEde());
        setnJameMotafaregheVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnJameMotafareghe());
        setnMamorBeEdeVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnMamorBeEde());
        setnJameHazerVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnJameHazer());
        setnSobhaneVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnSobhane());
        setnNaharVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnNahar());
        setnShamVazifeDaneshjo(obj.getAmarDaneshjoVazife().getnSham());

        setnEdeKolVazifeDarajedar(obj.getAmarDarajeDarVazife().getnEdeKol());
        setnMorakhasiVazifeDarajedar(obj.getAmarDarajeDarVazife().getnMorakhasi());
        setnBastariVazifeDarajedar(obj.getAmarDarajeDarVazife().getnBastari());
        setnNahastVazifeDarajedar(obj.getAmarDarajeDarVazife().getnNahast());
        setnFararVazifeDarajedar(obj.getAmarDarajeDarVazife().getnFarar());
        setnBazdashtVazifeDarajedar(obj.getAmarDarajeDarVazife().getnBazdasht());
        setnMontazerBekhedmatVazifeDarajedar(obj.getAmarDarajeDarVazife().getnMontazerBekhedmat());
        setnMamorAzEdeVazifeDarajedar(obj.getAmarDarajeDarVazife().getnMamorAzEde());
        setnJameMotafaregheVazifeDarajedar(obj.getAmarDarajeDarVazife().getnJameMotafareghe());
        setnMamorBeEdeVazifeDarajedar(obj.getAmarDarajeDarVazife().getnMamorBeEde());
        setnJameHazerVazifeDarajedar(obj.getAmarDarajeDarVazife().getnJameHazer());
        setnSobhaneVazifeDarajedar(obj.getAmarDarajeDarVazife().getnSobhane());
        setnNaharVazifeDarajedar(obj.getAmarDarajeDarVazife().getnNahar());
        setnShamVazifeDarajedar(obj.getAmarDarajeDarVazife().getnSham());

        setnEdeKolNavi(obj.getAmarNavi().getnEdeKol());
        setnMorakhasiNavi(obj.getAmarNavi().getnMorakhasi());
        setnBastariNavi(obj.getAmarNavi().getnBastari());
        setnNahastNavi(obj.getAmarNavi().getnNahast());
        setnFararNavi(obj.getAmarNavi().getnFarar());
        setnBazdashtNavi(obj.getAmarNavi().getnBazdasht());
        setnMontazerBekhedmatNavi(obj.getAmarNavi().getnMontazerBekhedmat());
        setnMamorAzEdeNavi(obj.getAmarNavi().getnMamorAzEde());
        setnJameMotafaregheNavi(obj.getAmarNavi().getnJameMotafareghe());
        setnMamorBeEdeNavi(obj.getAmarNavi().getnMamorBeEde());
        setnJameHazerNavi(obj.getAmarNavi().getnJameHazer());
        setnSobhaneNavi(obj.getAmarNavi().getnSobhane());
        setnNaharNavi(obj.getAmarNavi().getnNahar());
        setnShamNavi(obj.getAmarNavi().getnSham());


        setnModavematKari(obj.getnModavematKari());
        setnEzafekari(obj.getnEzafekari());
        setTedadJireNahar(obj.getTedadJireNahar());

        if (obj.getMolahezat() != null) {
            setMolahezat(obj.getMolahezat());
        }

        setSarparasteShobeAmar(obj.getSarparasteShobeAmar());
        setFarmandeYegan(obj.getFarmandeYegan());
        setSarparasteShobeAmarNiroEnsani(obj.getSarparasteShobeAmarNiroEnsani());
        setnAzMorakhasiPayvar(obj.getnAzMorakhasiPayvar());
        setnBeMorakhasiPayvar(obj.getnBeMorakhasiPayvar());
        setnAzNahastPayvar(obj.getnAzNahastPayvar());
        setnBeNahastPayvar(obj.getnBeNahastPayvar());
        setnAzBastariPayvar(obj.getnAzBastariPayvar());
        setnBeBastariPayvar(obj.getnBeBastariPayvar());
        setnAzMamoriyatPayvar(obj.getnAzMamoriyatPayvar());
        setnBeMamoriyatPayvar(obj.getnBeMamoriyatPayvar());
        setnKasrAzAmarPayvar(obj.getnKasrAzAmarPayvar());
        setnEzafBeAmarPayvar(obj.getnEzafBeAmarPayvar());
        setnEzafBeMamorBeEdePayvar(obj.getnEzafBeMamorBeEdePayvar());
        setnKasrAzMamorBeEdePayvar(obj.getnKasrAzMamorBeEdePayvar());
        setnBeEntezarBeKhedmatPayvar(obj.getnBeEntezarBeKhedmatPayvar());
        setnAzEntezarKhedmatPayvar(obj.getnAzEntezarKhedmatPayvar());
        setnBeBazdashtPayvar(obj.getnBeBazdashtPayvar());
        setnAzBazdashtPayvar(obj.getnAzBazdashtPayvar());
        setnAzMorakhasiVazife(obj.getnAzMorakhasiVazife());
        setnBeMorakhasiVazife(obj.getnBeMorakhasiVazife());
        setnAzNahastVazife(obj.getnAzNahastVazife());
        setnBeNahastVazife(obj.getnBeNahastVazife());
        setnAzBastariVazife(obj.getnAzBastariVazife());
        setnBeBastariVazife(obj.getnBeBastariVazife());
        setnAzMamoriyatVazife(obj.getnAzMamoriyatVazife());
        setnBeMamoriyatVazife(obj.getnBeMamoriyatVazife());
        setnKasrAzAmarVazife(obj.getnKasrAzAmarVazife());
        setnEzafBeAmarVazife(obj.getnEzafBeAmarVazife());
        setnEzafBeMamorBeEdeVazife(obj.getnEzafBeMamorBeEdeVazife());
        setnKasrAzMamorBeEdeVazife(obj.getnKasrAzMamorBeEdeVazife());
        setnBeEntezarBeKhedmatVazife(obj.getnBeEntezarBeKhedmatVazife());
        setnAzEntezarKhedmatVazife(obj.getnAzEntezarKhedmatVazife());
        setnBeBazdashtVazife(obj.getnBeBazdashtVazife());
        setnAzBazdashtVazife(obj.getnAzBazdashtVazife());


        StringBuilder builder = new StringBuilder();
        String fQuery = "from Jire where sal='" + obj.getTaghvim().getTarikh().substring(0, 4) +
                "' AND mah='" + obj.getTaghvim().getTarikh().substring(5, 7) +
                "' AND rooz='" + obj.getTaghvim().getTarikh().substring(8, 10) + "'";
        builder.append(fQuery);
        if (obj.getBakhsh() != null) {
            builder.append(" AND bakhsh.id=" + obj.getBakhsh().getId());
        }
        if (obj.getDayere() != null) {
            if (obj.getDayere().getCode().equals("0000")) {

            } else {
                builder.append(" AND dayere.id=" + obj.getDayere().getId());
            }
        }
        Session session = HibernateUtil.getSession();
        jireList = session.createQuery(builder.toString()).list();
        session.close();

        Personel personel = new Personel();
        selectedPersonelList.clear();
        for (Jire jire : jireList) {
            personel = jire.getPersonel();
            selectedPersonelList.add(personel);
        }

    }

    public void forward(SavabeghAmarJireBegirRozane amar) {
        //todo role move to user
//        if (permission.getRole().getId() == 3) {
//            amar.setErja("فرمانده");
//        } else if (permission.getRole().getId() == 2) {
//            amar.setErja("شعبه آمار");
//        }
    }

    public void back(SavabeghAmarJireBegirRozane amar) {
        //todo role move to user
//        if (permission.getRole().getId() == 2) {
//            amar.setErja("اداری");
//        } else if (permission.getRole().getId() == 7) {
//            amar.setErja("فرمانده");
//        }
    }

    public void save() {
        if (dataTable.size() == 0) {
            AmarAfsaranYekVaDo yekVaDo = new AmarAfsaranYekVaDo();
            yekVaDo.setnEdeKol(nEdeKolAfsarYekVaDo);
            yekVaDo.setnMorakhasi(nMorakhasiAfsarYekVaDo);
            yekVaDo.setnBastari(nBastariAfsarYekVaDo);
            yekVaDo.setnNahast(nNahastAfsarYekVaDo);
            yekVaDo.setnFarar(nFararAfsarYekVaDo);
            yekVaDo.setnBazdasht(nBazdashtAfsarYekVaDo);
            yekVaDo.setnMontazerBekhedmat(nMontazerBekhedmatAfsarYekVaDo);
            yekVaDo.setnMamorAzEde(nMamorAzEdeAfsarYekVaDo);
            yekVaDo.setnJameMotafareghe(nJameMotafaregheAfsarYekVaDo);
            yekVaDo.setnMamorBeEde(nMamorBeEdeAfsarYekVaDo);
            yekVaDo.setnJameHazer(nJameHazerAfsarYekVaDo);
            yekVaDo.setnSobhane(nSobhaneAfsarYekVaDo);
            yekVaDo.setnNahar(nNaharAfsarYekVaDo);
            yekVaDo.setnSham(nShamAfsarYekVaDo);


            AmarAfsaranSe se = new AmarAfsaranSe();
            se.setnEdeKol(nEdeKolAfsarSe);
            se.setnMorakhasi(nMorakhasiAfsarSe);
            se.setnBastari(nBastariAfsarSe);
            se.setnNahast(nNahastAfsarSe);
            se.setnFarar(nFararAfsarSe);
            se.setnBazdasht(nBazdashtAfsarSe);
            se.setnMontazerBekhedmat(nMontazerBekhedmatAfsarSe);
            se.setnMamorAzEde(nMamorAzEdeAfsarSe);
            se.setnJameMotafareghe(nJameMotafaregheAfsarSe);
            se.setnMamorBeEde(nMamorBeEdeAfsarSe);
            se.setnJameHazer(nJameHazerAfsarSe);
            se.setnSobhane(nSobhaneAfsarSe);
            se.setnNahar(nNaharAfsarSe);
            se.setnSham(nShamAfsarSe);


            AmarDarajeDaran drjDar = new AmarDarajeDaran();
            drjDar.setnEdeKol(nEdeKolDarajedar);
            drjDar.setnMorakhasi(nMorakhasiDarajedar);
            drjDar.setnBastari(nBastariDarajedar);
            drjDar.setnNahast(nNahastDarajedar);
            drjDar.setnFarar(nFararDarajedar);
            drjDar.setnBazdasht(nBazdashtDarajedar);
            drjDar.setnMontazerBekhedmat(nMontazerBekhedmatDarajedar);
            drjDar.setnMamorAzEde(nMamorAzEdeDarajedar);
            drjDar.setnJameMotafareghe(nJameMotafaregheDarajedar);
            drjDar.setnMamorBeEde(nMamorBeEdeDarajedar);
            drjDar.setnJameHazer(nJameHazerDarajedar);
            drjDar.setnSobhane(nSobhaneDarajedar);
            drjDar.setnNahar(nNaharDarajedar);
            drjDar.setnSham(nShamDarajedar);


            AmarKarmandanElmi elmi = new AmarKarmandanElmi();
            elmi.setnEdeKol(nEdeKolKarmandElmi);
            elmi.setnMorakhasi(nMorakhasiKarmandElmi);
            elmi.setnBastari(nBastariKarmandElmi);
            elmi.setnNahast(nNahastKarmandElmi);
            elmi.setnFarar(nFararKarmandElmi);
            elmi.setnBazdasht(nBazdashtKarmandElmi);
            elmi.setnMontazerBekhedmat(nMontazerBekhedmatKarmandElmi);
            elmi.setnMamorAzEde(nMamorAzEdeKarmandElmi);
            elmi.setnJameMotafareghe(nJameMotafaregheKarmandElmi);
            elmi.setnMamorBeEde(nMamorBeEdeKarmandElmi);
            elmi.setnJameHazer(nJameHazerKarmandElmi);
            elmi.setnSobhane(nSobhaneKarmandElmi);
            elmi.setnNahar(nNaharKarmandElmi);
            elmi.setnSham(nShamKarmandElmi);


            AmarKarmandanTajrobi taj = new AmarKarmandanTajrobi();
            taj.setnEdeKol(nEdeKolKarmandTajrobi);
            taj.setnMorakhasi(nMorakhasiKarmandTajrobi);
            taj.setnBastari(nBastariKarmandTajrobi);
            taj.setnNahast(nNahastKarmandTajrobi);
            taj.setnFarar(nFararKarmandTajrobi);
            taj.setnBazdasht(nBazdashtKarmandTajrobi);
            taj.setnMontazerBekhedmat(nMontazerBekhedmatKarmandTajrobi);
            taj.setnMamorAzEde(nMamorAzEdeKarmandTajrobi);
            taj.setnJameMotafareghe(nJameMotafaregheKarmandTajrobi);
            taj.setnMamorBeEde(nMamorBeEdeKarmandTajrobi);
            taj.setnJameHazer(nJameHazerKarmandTajrobi);
            taj.setnSobhane(nSobhaneKarmandTajrobi);
            taj.setnNahar(nNaharKarmandTajrobi);
            taj.setnSham(nShamKarmandTajrobi);


            AmarAfsarVazife aVaz = new AmarAfsarVazife();
            aVaz.setnEdeKol(nEdeKolVazifeAfsar);
            aVaz.setnMorakhasi(nMorakhasiVazifeAfsar);
            aVaz.setnBastari(nBastariVazifeAfsar);
            aVaz.setnNahast(nNahastVazifeAfsar);
            aVaz.setnFarar(nFararVazifeAfsar);
            aVaz.setnBazdasht(nBazdashtVazifeAfsar);
            aVaz.setnMontazerBekhedmat(nMontazerBekhedmatVazifeAfsar);
            aVaz.setnMamorAzEde(nMamorAzEdeVazifeAfsar);
            aVaz.setnJameMotafareghe(nJameMotafaregheVazifeAfsar);
            aVaz.setnMamorBeEde(nMamorBeEdeVazifeAfsar);
            aVaz.setnJameHazer(nJameHazerVazifeAfsar);
            aVaz.setnSobhane(nSobhaneVazifeAfsar);
            aVaz.setnNahar(nNaharVazifeAfsar);
            aVaz.setnSham(nShamVazifeAfsar);


            AmarDaneshjoVazife danVaz = new AmarDaneshjoVazife();
            danVaz.setnEdeKol(nEdeKolVazifeDaneshjo);
            danVaz.setnMorakhasi(nMorakhasiVazifeDaneshjo);
            danVaz.setnBastari(nBastariVazifeDaneshjo);
            danVaz.setnNahast(nNahastVazifeDaneshjo);
            danVaz.setnFarar(nFararVazifeDaneshjo);
            danVaz.setnBazdasht(nBazdashtVazifeDaneshjo);
            danVaz.setnMontazerBekhedmat(nMontazerBekhedmatVazifeDaneshjo);
            danVaz.setnMamorAzEde(nMamorAzEdeVazifeDaneshjo);
            danVaz.setnJameMotafareghe(nJameMotafaregheVazifeDaneshjo);
            danVaz.setnMamorBeEde(nMamorBeEdeVazifeDaneshjo);
            danVaz.setnJameHazer(nJameHazerVazifeDaneshjo);
            danVaz.setnSobhane(nSobhaneVazifeDaneshjo);
            danVaz.setnNahar(nNaharVazifeDaneshjo);
            danVaz.setnSham(nShamVazifeDaneshjo);

            AmarDarajeDarVazife drjVaz = new AmarDarajeDarVazife();
            drjVaz.setnEdeKol(nEdeKolVazifeDarajedar);
            drjVaz.setnMorakhasi(nMorakhasiVazifeDarajedar);
            drjVaz.setnBastari(nBastariVazifeDarajedar);
            drjVaz.setnNahast(nNahastVazifeDarajedar);
            drjVaz.setnFarar(nFararVazifeDarajedar);
            drjVaz.setnBazdasht(nBazdashtVazifeDarajedar);
            drjVaz.setnMontazerBekhedmat(nMontazerBekhedmatVazifeDarajedar);
            drjVaz.setnMamorAzEde(nMamorAzEdeVazifeDarajedar);
            drjVaz.setnJameMotafareghe(nJameMotafaregheVazifeDarajedar);
            drjVaz.setnMamorBeEde(nMamorBeEdeVazifeDarajedar);
            drjVaz.setnJameHazer(nJameHazerVazifeDarajedar);
            drjVaz.setnSobhane(nSobhaneVazifeDarajedar);
            drjVaz.setnNahar(nNaharVazifeDarajedar);
            drjVaz.setnSham(nShamVazifeDarajedar);


            AmarNavi nav = new AmarNavi();
            nav.setnEdeKol(nEdeKolNavi);
            nav.setnMorakhasi(nMorakhasiNavi);
            nav.setnBastari(nBastariNavi);
            nav.setnNahast(nNahastNavi);
            nav.setnFarar(nFararNavi);
            nav.setnBazdasht(nBazdashtNavi);
            nav.setnMontazerBekhedmat(nMontazerBekhedmatNavi);
            nav.setnMamorAzEde(nMamorAzEdeNavi);
            nav.setnJameMotafareghe(nJameMotafaregheNavi);
            nav.setnMamorBeEde(nMamorBeEdeNavi);
            nav.setnJameHazer(nJameHazerNavi);
            nav.setnSobhane(nSobhaneNavi);
            nav.setnNahar(nNaharNavi);
            nav.setnSham(nShamNavi);


            SavabeghAmarJireBegirRozane model = new SavabeghAmarJireBegirRozane();
            model.setnModavematKari(nModavematKari);
            model.setnEzafekari(nEzafekari);
            model.setTedadJireNahar(selectedPersonelList.size());
            if (!molahezat.equals("")) {
                model.setMolahezat(molahezat);
            }
            model.setTedadJireNahar(selectedPersonelList.size());
            model.setnAzMorakhasiPayvar(nAzMorakhasiPayvar);
            model.setnBeMorakhasiPayvar(nBeMorakhasiPayvar);
            model.setnAzNahastPayvar(nAzNahastPayvar);
            model.setnBeNahastPayvar(nBeNahastPayvar);
            model.setnAzBastariPayvar(nAzBastariPayvar);
            model.setnBeBastariPayvar(nBeBastariPayvar);
            model.setnAzMamoriyatPayvar(nAzMamoriyatPayvar);
            model.setnBeMamoriyatPayvar(nBeMamoriyatPayvar);
            model.setnKasrAzAmarPayvar(nKasrAzAmarPayvar);
            model.setnEzafBeAmarPayvar(nEzafBeAmarPayvar);
            model.setnEzafBeMamorBeEdePayvar(nEzafBeMamorBeEdePayvar);
            model.setnKasrAzMamorBeEdePayvar(nKasrAzMamorBeEdePayvar);
            model.setnBeEntezarBeKhedmatPayvar(nBeEntezarBeKhedmatPayvar);
            model.setnAzEntezarKhedmatPayvar(nAzEntezarKhedmatPayvar);
            model.setnBeBazdashtPayvar(nBeBazdashtPayvar);
            model.setnAzBazdashtPayvar(nAzBazdashtPayvar);
            model.setnAzMorakhasiVazife(nAzMorakhasiVazife);
            model.setnBeMorakhasiVazife(nBeMorakhasiVazife);
            model.setnAzNahastVazife(nAzNahastVazife);
            model.setnBeNahastVazife(nBeNahastVazife);
            model.setnAzBastariVazife(nAzBastariVazife);
            model.setnBeBastariVazife(nBeBastariVazife);
            model.setnAzMamoriyatVazife(nAzMamoriyatVazife);
            model.setnBeMamoriyatVazife(nBeMamoriyatVazife);
            model.setnKasrAzAmarVazife(nKasrAzAmarVazife);
            model.setnEzafBeAmarVazife(nEzafBeAmarVazife);
            model.setnEzafBeMamorBeEdeVazife(nEzafBeMamorBeEdeVazife);
            model.setnKasrAzMamorBeEdeVazife(nKasrAzMamorBeEdeVazife);
            model.setnBeEntezarBeKhedmatVazife(nBeEntezarBeKhedmatVazife);
            model.setnAzEntezarKhedmatVazife(nAzEntezarKhedmatVazife);
            model.setnBeBazdashtVazife(nBeBazdashtVazife);
            model.setnAzBazdashtVazife(nAzBazdashtVazife);

            model.setSarparasteShobeAmar(sarparasteShobeAmar);
            model.setFarmandeYegan(farmandeYegan);
            model.setSarparasteShobeAmarNiroEnsani(sarparasteShobeAmarNiroEnsani);

            model.setAmarAfsaranYekVaDo(yekVaDo);
            model.setAmarAfsaranSe(se);
            model.setAmarDarajeDaran(drjDar);
            model.setAmarKarmandanElmi(elmi);
            model.setAmarKarmandanTajrobi(taj);
            model.setAmarAfsarVazife(aVaz);
            model.setAmarDaneshjoVazife(danVaz);
            model.setAmarDarajeDarVazife(drjVaz);
            model.setAmarNavi(nav);

            if (bakhsh != null) {
                model.setBakhsh(getBakhsh());
            }
            if (dayere != null) {
                if (getDayere().getCode().equals("0000")) {
                    model.setDayere(null);
                    model.setBakhsh(null);
                } else {
                    model.setDayere(getDayere());
                }
            }


            if (taghvims.size() > 0) {

                try {

                    AmarAfsaranYekVaDoDao.getInstance().getBaseQuery().create(yekVaDo);
                    AmarAfsaranSeDao.getInstance().getBaseQuery().create(se);
                    AmarDarajeDaranDao.getInstance().getBaseQuery().create(drjDar);
                    AmarKarmandanTajrobiDao.getInstance().getBaseQuery().create(taj);
                    AmarKarmandanElmiDao.getInstance().getBaseQuery().create(elmi);
                    AmarAfsarVazifeDao.getInstance().getBaseQuery().create(aVaz);
                    AmarDaneshjoVazifeDao.getInstance().getBaseQuery().create(danVaz);
                    AmarDarajeDarVazifeDao.getInstance().getBaseQuery().create(drjVaz);
                    AmarNaviDao.getInstance().getBaseQuery().create(nav);

                    Jire jire = new Jire();
                    if (selectedPersonelList.size() > 0) {
                        for (Personel p : selectedPersonelList) {
                            jire.setPersonel(p);
                            jire.setTedadjire(1);
                            jire.setSal(taghvims.get(0).getTarikh().substring(0, 4));
                            jire.setMah(taghvims.get(0).getTarikh().substring(5, 7));
                            jire.setRooz(taghvims.get(0).getTarikh().substring(8, 10));
                            if (getBakhsh() != null) {
                                jire.setBakhsh(getBakhsh());
                            } else {
                                jire.setBakhsh(null);
                            }
                            if (getDayere() != null) {
                                if (getDayere().getCode().equals("0000")) {
                                    jire.setDayere(null);
                                } else {
                                    jire.setDayere(getDayere());
                                }
                            }
                            try {
                                JireDao.getInstance().getBaseQuery().create(jire, URL);

                            } catch (Exception e) {

                            }
                        }

                    }

                } catch (Exception e) {

                }
                model.setTaghvim(taghvims.get(0));

                try {

                    SavabeghAmarJireBegirRozaneDao.getInstance().getBaseQuery().create(model, URL);

                    dataTable.add(model);

                    new Alert().successSave();
                    nuller();
                } catch (Exception e) {
                    new Alert().unSuccessSave();
                }
            } else {
                new Alert("خطا", "اطلاعات نادرست");
            }
        } else {
            new Alert("خطا", "آمار امروز تنظیم شده است");
        }
    }

    public void update() {
        AmarAfsaranYekVaDo yekVaDo = new AmarAfsaranYekVaDo();
        yekVaDo = amar.getAmarAfsaranYekVaDo();
        yekVaDo.setnEdeKol(nEdeKolAfsarYekVaDo);
        yekVaDo.setnMorakhasi(nMorakhasiAfsarYekVaDo);
        yekVaDo.setnBastari(nBastariAfsarYekVaDo);
        yekVaDo.setnNahast(nNahastAfsarYekVaDo);
        yekVaDo.setnFarar(nFararAfsarYekVaDo);
        yekVaDo.setnBazdasht(nBazdashtAfsarYekVaDo);
        yekVaDo.setnMontazerBekhedmat(nMontazerBekhedmatAfsarYekVaDo);
        yekVaDo.setnMamorAzEde(nMamorAzEdeAfsarYekVaDo);
        yekVaDo.setnJameMotafareghe(nJameMotafaregheAfsarYekVaDo);
        yekVaDo.setnMamorBeEde(nMamorBeEdeAfsarYekVaDo);
        yekVaDo.setnJameHazer(nJameHazerAfsarYekVaDo);
        yekVaDo.setnSobhane(nSobhaneAfsarYekVaDo);
        yekVaDo.setnNahar(nNaharAfsarYekVaDo);
        yekVaDo.setnSham(nShamAfsarYekVaDo);

        AmarAfsaranSe se = new AmarAfsaranSe();
        se = amar.getAmarAfsaranSe();
        se.setnEdeKol(nEdeKolAfsarSe);
        se.setnMorakhasi(nMorakhasiAfsarSe);
        se.setnBastari(nBastariAfsarSe);
        se.setnNahast(nNahastAfsarSe);
        se.setnFarar(nFararAfsarSe);
        se.setnBazdasht(nBazdashtAfsarSe);
        se.setnMontazerBekhedmat(nMontazerBekhedmatAfsarSe);
        se.setnMamorAzEde(nMamorAzEdeAfsarSe);
        se.setnJameMotafareghe(nJameMotafaregheAfsarSe);
        se.setnMamorBeEde(nMamorBeEdeAfsarSe);
        se.setnJameHazer(nJameHazerAfsarSe);
        se.setnSobhane(nSobhaneAfsarSe);
        se.setnNahar(nNaharAfsarSe);
        se.setnSham(nShamAfsarSe);


        AmarDarajeDaran drjDar = new AmarDarajeDaran();
        drjDar = amar.getAmarDarajeDaran();
        drjDar.setnEdeKol(nEdeKolDarajedar);
        drjDar.setnMorakhasi(nMorakhasiDarajedar);
        drjDar.setnBastari(nBastariDarajedar);
        drjDar.setnNahast(nNahastDarajedar);
        drjDar.setnFarar(nFararDarajedar);
        drjDar.setnBazdasht(nBazdashtDarajedar);
        drjDar.setnMontazerBekhedmat(nMontazerBekhedmatDarajedar);
        drjDar.setnMamorAzEde(nMamorAzEdeDarajedar);
        drjDar.setnJameMotafareghe(nJameMotafaregheDarajedar);
        drjDar.setnMamorBeEde(nMamorBeEdeDarajedar);
        drjDar.setnJameHazer(nJameHazerDarajedar);
        drjDar.setnSobhane(nSobhaneDarajedar);
        drjDar.setnNahar(nNaharDarajedar);
        drjDar.setnSham(nShamDarajedar);

        AmarKarmandanElmi elmi = new AmarKarmandanElmi();
        elmi = amar.getAmarKarmandanElmi();
        elmi.setnEdeKol(nEdeKolKarmandElmi);
        elmi.setnMorakhasi(nMorakhasiKarmandElmi);
        elmi.setnBastari(nBastariKarmandElmi);
        elmi.setnNahast(nNahastKarmandElmi);
        elmi.setnFarar(nFararKarmandElmi);
        elmi.setnBazdasht(nBazdashtKarmandElmi);
        elmi.setnMontazerBekhedmat(nMontazerBekhedmatKarmandElmi);
        elmi.setnMamorAzEde(nMamorAzEdeKarmandElmi);
        elmi.setnJameMotafareghe(nJameMotafaregheKarmandElmi);
        elmi.setnMamorBeEde(nMamorBeEdeKarmandElmi);
        elmi.setnJameHazer(nJameHazerKarmandElmi);
        elmi.setnSobhane(nSobhaneKarmandElmi);
        elmi.setnNahar(nNaharKarmandElmi);
        elmi.setnSham(nShamKarmandElmi);

        AmarKarmandanTajrobi taj = new AmarKarmandanTajrobi();
        taj = amar.getAmarKarmandanTajrobi();
        taj.setnEdeKol(nEdeKolKarmandTajrobi);
        taj.setnMorakhasi(nMorakhasiKarmandTajrobi);
        taj.setnBastari(nBastariKarmandTajrobi);
        taj.setnNahast(nNahastKarmandTajrobi);
        taj.setnFarar(nFararKarmandTajrobi);
        taj.setnBazdasht(nBazdashtKarmandTajrobi);
        taj.setnMontazerBekhedmat(nMontazerBekhedmatKarmandTajrobi);
        taj.setnMamorAzEde(nMamorAzEdeKarmandTajrobi);
        taj.setnJameMotafareghe(nJameMotafaregheKarmandTajrobi);
        taj.setnMamorBeEde(nMamorBeEdeKarmandTajrobi);
        taj.setnJameHazer(nJameHazerKarmandTajrobi);
        taj.setnSobhane(nSobhaneKarmandTajrobi);
        taj.setnNahar(nNaharKarmandTajrobi);
        taj.setnSham(nShamKarmandTajrobi);


        AmarAfsarVazife aVaz = new AmarAfsarVazife();
        aVaz = amar.getAmarAfsarVazife();
        aVaz.setnEdeKol(nEdeKolVazifeAfsar);
        aVaz.setnMorakhasi(nMorakhasiVazifeAfsar);
        aVaz.setnBastari(nBastariVazifeAfsar);
        aVaz.setnNahast(nNahastVazifeAfsar);
        aVaz.setnFarar(nFararVazifeAfsar);
        aVaz.setnBazdasht(nBazdashtVazifeAfsar);
        aVaz.setnMontazerBekhedmat(nMontazerBekhedmatVazifeAfsar);
        aVaz.setnMamorAzEde(nMamorAzEdeVazifeAfsar);
        aVaz.setnJameMotafareghe(nJameMotafaregheVazifeAfsar);
        aVaz.setnMamorBeEde(nMamorBeEdeVazifeAfsar);
        aVaz.setnJameHazer(nJameHazerVazifeAfsar);
        aVaz.setnSobhane(nSobhaneVazifeAfsar);
        aVaz.setnNahar(nNaharVazifeAfsar);
        aVaz.setnSham(nShamVazifeAfsar);

        AmarDaneshjoVazife danVaz = new AmarDaneshjoVazife();
        danVaz = amar.getAmarDaneshjoVazife();
        danVaz.setnEdeKol(nEdeKolVazifeDaneshjo);
        danVaz.setnMorakhasi(nMorakhasiVazifeDaneshjo);
        danVaz.setnBastari(nBastariVazifeDaneshjo);
        danVaz.setnNahast(nNahastVazifeDaneshjo);
        danVaz.setnFarar(nFararVazifeDaneshjo);
        danVaz.setnBazdasht(nBazdashtVazifeDaneshjo);
        danVaz.setnMontazerBekhedmat(nMontazerBekhedmatVazifeDaneshjo);
        danVaz.setnMamorAzEde(nMamorAzEdeVazifeDaneshjo);
        danVaz.setnJameMotafareghe(nJameMotafaregheVazifeDaneshjo);
        danVaz.setnMamorBeEde(nMamorBeEdeVazifeDaneshjo);
        danVaz.setnJameHazer(nJameHazerVazifeDaneshjo);
        danVaz.setnSobhane(nSobhaneVazifeDaneshjo);
        danVaz.setnNahar(nNaharVazifeDaneshjo);
        danVaz.setnSham(nShamVazifeDaneshjo);

        AmarDarajeDarVazife drjVaz = new AmarDarajeDarVazife();
        drjVaz = amar.getAmarDarajeDarVazife();
        drjVaz.setnEdeKol(nEdeKolVazifeDarajedar);
        drjVaz.setnMorakhasi(nMorakhasiVazifeDarajedar);
        drjVaz.setnBastari(nBastariVazifeDarajedar);
        drjVaz.setnNahast(nNahastVazifeDarajedar);
        drjVaz.setnFarar(nFararVazifeDarajedar);
        drjVaz.setnBazdasht(nBazdashtVazifeDarajedar);
        drjVaz.setnMontazerBekhedmat(nMontazerBekhedmatVazifeDarajedar);
        drjVaz.setnMamorAzEde(nMamorAzEdeVazifeDarajedar);
        drjVaz.setnJameMotafareghe(nJameMotafaregheVazifeDarajedar);
        drjVaz.setnMamorBeEde(nMamorBeEdeVazifeDarajedar);
        drjVaz.setnJameHazer(nJameHazerVazifeDarajedar);
        drjVaz.setnSobhane(nSobhaneVazifeDarajedar);
        drjVaz.setnNahar(nNaharVazifeDarajedar);
        drjVaz.setnSham(nShamVazifeDarajedar);

        AmarNavi nav = new AmarNavi();
        nav = amar.getAmarNavi();
        nav.setnEdeKol(nEdeKolNavi);
        nav.setnMorakhasi(nMorakhasiNavi);
        nav.setnBastari(nBastariNavi);
        nav.setnNahast(nNahastNavi);
        nav.setnFarar(nFararNavi);
        nav.setnBazdasht(nBazdashtNavi);
        nav.setnMontazerBekhedmat(nMontazerBekhedmatNavi);
        nav.setnMamorAzEde(nMamorAzEdeNavi);
        nav.setnJameMotafareghe(nJameMotafaregheNavi);
        nav.setnMamorBeEde(nMamorBeEdeNavi);
        nav.setnJameHazer(nJameHazerNavi);
        nav.setnSobhane(nSobhaneNavi);
        nav.setnNahar(nNaharNavi);
        nav.setnSham(nShamNavi);


        SavabeghAmarJireBegirRozane model = new SavabeghAmarJireBegirRozane();
        model = amar;

        model.setnModavematKari(nModavematKari);
        model.setnEzafekari(nEzafekari);

        if (!molahezat.equals("")) {
            model.setMolahezat(molahezat);
        }
        model.setTedadJireNahar(selectedPersonelList.size());
        model.setnAzMorakhasiPayvar(nAzMorakhasiPayvar);
        model.setnBeMorakhasiPayvar(nBeMorakhasiPayvar);
        model.setnAzNahastPayvar(nAzNahastPayvar);
        model.setnBeNahastPayvar(nBeNahastPayvar);
        model.setnAzBastariPayvar(nAzBastariPayvar);
        model.setnBeBastariPayvar(nBeBastariPayvar);
        model.setnAzMamoriyatPayvar(nAzMamoriyatPayvar);
        model.setnBeMamoriyatPayvar(nBeMamoriyatPayvar);
        model.setnKasrAzAmarPayvar(nKasrAzAmarPayvar);
        model.setnEzafBeAmarPayvar(nEzafBeAmarPayvar);
        model.setnEzafBeMamorBeEdePayvar(nEzafBeMamorBeEdePayvar);
        model.setnKasrAzMamorBeEdePayvar(nKasrAzMamorBeEdePayvar);
        model.setnBeEntezarBeKhedmatPayvar(nBeEntezarBeKhedmatPayvar);
        model.setnAzEntezarKhedmatPayvar(nAzEntezarKhedmatPayvar);
        model.setnBeBazdashtPayvar(nBeBazdashtPayvar);
        model.setnAzBazdashtPayvar(nAzBazdashtPayvar);
        model.setnAzMorakhasiVazife(nAzMorakhasiVazife);
        model.setnBeMorakhasiVazife(nBeMorakhasiVazife);
        model.setnAzNahastVazife(nAzNahastVazife);
        model.setnBeNahastVazife(nBeNahastVazife);
        model.setnAzBastariVazife(nAzBastariVazife);
        model.setnBeBastariVazife(nBeBastariVazife);
        model.setnAzMamoriyatVazife(nAzMamoriyatVazife);
        model.setnBeMamoriyatVazife(nBeMamoriyatVazife);
        model.setnKasrAzAmarVazife(nKasrAzAmarVazife);
        model.setnEzafBeAmarVazife(nEzafBeAmarVazife);
        model.setnEzafBeMamorBeEdeVazife(nEzafBeMamorBeEdeVazife);
        model.setnKasrAzMamorBeEdeVazife(nKasrAzMamorBeEdeVazife);
        model.setnBeEntezarBeKhedmatVazife(nBeEntezarBeKhedmatVazife);
        model.setnAzEntezarKhedmatVazife(nAzEntezarKhedmatVazife);
        model.setnBeBazdashtVazife(nBeBazdashtVazife);
        model.setnAzBazdashtVazife(nAzBazdashtVazife);

        model.setSarparasteShobeAmar(sarparasteShobeAmar);
        model.setFarmandeYegan(farmandeYegan);
        model.setSarparasteShobeAmarNiroEnsani(sarparasteShobeAmarNiroEnsani);

        model.setAmarAfsaranYekVaDo(yekVaDo);
        model.setAmarAfsaranSe(se);
        model.setAmarDarajeDaran(drjDar);
        model.setAmarKarmandanElmi(elmi);
        model.setAmarKarmandanTajrobi(taj);
        model.setAmarAfsarVazife(aVaz);
        model.setAmarDaneshjoVazife(danVaz);
        model.setAmarDarajeDarVazife(drjVaz);
        model.setAmarNavi(nav);
        if (bakhsh != null) {
            model.setBakhsh(getBakhsh());
        }
        if (dayere != null) {
            if (getDayere().getCode().equals("0000")) {
                model.setDayere(null);
                model.setBakhsh(null);
            } else {
                model.setDayere(getDayere());
            }
        }

        if (jireList.size() > 0) {
            StringBuilder builder = new StringBuilder();
            String fQuery = "DELETE FROM Jire WHERE  sal='" + amar.getTaghvim().getTarikh().substring(0, 4) + "' AND mah='" + amar.getTaghvim().getTarikh().substring(5, 7) + "' And rooz='" + amar.getTaghvim().getTarikh().substring(8, 10) + "' ";

            builder.append(fQuery);

            if (getBakhsh() != null) {
                builder.append(" AND bakhsh.id=" + getBakhsh().getId());
            } else if (getDayere() != null) {
                if (getDayere().getCode().equals("0000")) {

                } else {
                    builder.append(" AND dayere.id=" + getDayere().getId());
                }
            }

            Session session = HibernateUtil.getSession();
            session.createQuery(builder.toString()).executeUpdate();
            session.close();
        }

        Jire jire = new Jire();
        if (selectedPersonelList.size() > 0) {
            for (Personel p : selectedPersonelList) {
                jire.setPersonel(p);
                jire.setTedadjire(1);
                jire.setSal(taghvims.get(0).getTarikh().substring(0, 4));
                jire.setMah(taghvims.get(0).getTarikh().substring(5, 7));
                jire.setRooz(taghvims.get(0).getTarikh().substring(8, 10));
                try {
                    JireDao.getInstance().getBaseQuery().create(jire, URL);

                } catch (Exception e) {

                }
            }

        }

        try {
            AmarAfsaranYekVaDoDao.getInstance().getBaseQuery().createOrUpdate(yekVaDo);
            AmarAfsaranSeDao.getInstance().getBaseQuery().createOrUpdate(se);
            AmarDarajeDaranDao.getInstance().getBaseQuery().createOrUpdate(drjDar);
            AmarKarmandanTajrobiDao.getInstance().getBaseQuery().createOrUpdate(taj);
            AmarKarmandanElmiDao.getInstance().getBaseQuery().createOrUpdate(elmi);
            AmarAfsarVazifeDao.getInstance().getBaseQuery().createOrUpdate(aVaz);
            AmarDaneshjoVazifeDao.getInstance().getBaseQuery().createOrUpdate(danVaz);
            AmarDarajeDarVazifeDao.getInstance().getBaseQuery().createOrUpdate(drjVaz);
            AmarNaviDao.getInstance().getBaseQuery().createOrUpdate(nav);
        } catch (Exception e) {
        }

        try {
            SavabeghAmarJireBegirRozaneDao.getInstance().getBaseQuery().createOrUpdate(model, URL);
            new Alert().successEdit();
            nuller();
        } catch (Exception e) {
            new Alert().unSuccessEdit();
        }
    }


    public void delete() {
        StringBuilder builder = new StringBuilder();
        String fQuery = "DELETE FROM Jire WHERE  sal='" + amar.getTaghvim().getTarikh().substring(0, 4) + "' AND mah='" + amar.getTaghvim().getTarikh().substring(5, 7) + "' And rooz='" + amar.getTaghvim().getTarikh().substring(8, 10) + "' ";

        builder.append(fQuery);

        if (amar.getBakhsh() != null) {
            builder.append(" AND bakhsh.id=" + amar.getBakhsh().getId());
        } else if (amar.getDayere() != null) {
            if (amar.getDayere().getCode().equals("0000")) {

            } else {
                builder.append(" AND dayere.id=" + amar.getDayere().getId());
            }
        }

        Session session = HibernateUtil.getSession();
        session.createQuery(builder.toString()).executeUpdate();
        session.close();

        try {
            SavabeghAmarJireBegirRozaneDao.getInstance().getBaseQuery().delete(amar, URL);
            dataTable.remove(amar);

        } catch (Exception e) {

        }
        AmarAfsaranYekVaDo yekVaDo = new AmarAfsaranYekVaDo();
        yekVaDo = amar.getAmarAfsaranYekVaDo();
        AmarAfsaranSe se = new AmarAfsaranSe();
        se = amar.getAmarAfsaranSe();
        AmarDarajeDaran drjDar = new AmarDarajeDaran();
        drjDar = amar.getAmarDarajeDaran();
        AmarKarmandanTajrobi taj = new AmarKarmandanTajrobi();
        taj = amar.getAmarKarmandanTajrobi();
        AmarKarmandanElmi elm = new AmarKarmandanElmi();
        elm = amar.getAmarKarmandanElmi();
        AmarAfsarVazife aVaz = new AmarAfsarVazife();
        aVaz = amar.getAmarAfsarVazife();
        AmarDaneshjoVazife danVaz = new AmarDaneshjoVazife();
        danVaz = amar.getAmarDaneshjoVazife();
        AmarDarajeDarVazife drjVaz = new AmarDarajeDarVazife();
        drjVaz = amar.getAmarDarajeDarVazife();
        AmarNavi nav = new AmarNavi();
        nav = amar.getAmarNavi();
        try {
            AmarAfsaranYekVaDoDao.getInstance().getBaseQuery().delete(yekVaDo);
            AmarAfsaranSeDao.getInstance().getBaseQuery().delete(se);
            AmarDarajeDaranDao.getInstance().getBaseQuery().delete(drjDar);
            AmarKarmandanTajrobiDao.getInstance().getBaseQuery().delete(taj);
            AmarKarmandanElmiDao.getInstance().getBaseQuery().delete(elm);
            AmarAfsarVazifeDao.getInstance().getBaseQuery().delete(aVaz);
            AmarDaneshjoVazifeDao.getInstance().getBaseQuery().delete(danVaz);
            AmarDarajeDarVazifeDao.getInstance().getBaseQuery().delete(drjVaz);
            AmarNaviDao.getInstance().getBaseQuery().delete(nav);

            nuller();
        } catch (Exception e) {
        }
    }


    public void nuller() {
        amar = new SavabeghAmarJireBegirRozane();
        setnEdeKolAfsarYekVaDo(0);
        setnMorakhasiAfsarYekVaDo(0);
        setnBastariAfsarYekVaDo(0);
        setnNahastAfsarYekVaDo(0);
        setnFararAfsarYekVaDo(0);
        setnBazdashtAfsarYekVaDo(0);
        setnMontazerBekhedmatAfsarYekVaDo(0);
        setnMamorAzEdeAfsarYekVaDo(0);
        setnJameMotafaregheAfsarYekVaDo(0);
        setnMamorBeEdeAfsarYekVaDo(0);
        setnJameHazerAfsarYekVaDo(0);
        setnSobhaneAfsarYekVaDo(0);
        setnNaharAfsarYekVaDo(0);
        setnShamAfsarYekVaDo(0);

        setnEdeKolAfsarSe(0);
        setnMorakhasiAfsarSe(0);
        setnBastariAfsarSe(0);
        setnNahastAfsarSe(0);
        setnFararAfsarSe(0);
        setnBazdashtAfsarSe(0);
        setnMontazerBekhedmatAfsarSe(0);
        setnMamorAzEdeAfsarSe(0);
        setnJameMotafaregheAfsarSe(0);
        setnMamorBeEdeAfsarSe(0);
        setnJameHazerAfsarSe(0);
        setnSobhaneAfsarSe(0);
        setnNaharAfsarSe(0);
        setnShamAfsarSe(0);

        setnEdeKolDarajedar(0);
        setnMorakhasiDarajedar(0);
        setnBastariDarajedar(0);
        setnNahastDarajedar(0);
        setnFararDarajedar(0);
        setnBazdashtDarajedar(0);
        setnMontazerBekhedmatDarajedar(0);
        setnMamorAzEdeDarajedar(0);
        setnJameMotafaregheDarajedar(0);
        setnMamorBeEdeDarajedar(0);
        setnJameHazerDarajedar(0);
        setnSobhaneDarajedar(0);
        setnNaharDarajedar(0);
        setnShamDarajedar(0);

        setnEdeKolKarmandElmi(0);
        setnMorakhasiKarmandElmi(0);
        setnBastariKarmandElmi(0);
        setnNahastKarmandElmi(0);
        setnFararKarmandElmi(0);
        setnBazdashtKarmandElmi(0);
        setnMontazerBekhedmatKarmandElmi(0);
        setnMamorAzEdeKarmandElmi(0);
        setnJameMotafaregheKarmandElmi(0);
        setnMamorBeEdeKarmandElmi(0);
        setnJameHazerKarmandElmi(0);
        setnSobhaneKarmandElmi(0);
        setnNaharKarmandElmi(0);
        setnShamKarmandElmi(0);

        setnEdeKolKarmandTajrobi(0);
        setnMorakhasiKarmandTajrobi(0);
        setnBastariKarmandTajrobi(0);
        setnNahastKarmandTajrobi(0);
        setnFararKarmandTajrobi(0);
        setnBazdashtKarmandTajrobi(0);
        setnMontazerBekhedmatKarmandTajrobi(0);
        setnMamorAzEdeKarmandTajrobi(0);
        setnJameMotafaregheKarmandTajrobi(0);
        setnMamorBeEdeKarmandTajrobi(0);
        setnJameHazerKarmandTajrobi(0);
        setnSobhaneKarmandTajrobi(0);
        setnNaharKarmandTajrobi(0);
        setnShamKarmandTajrobi(0);

        setnEdeKolVazifeAfsar(0);
        setnMorakhasiVazifeAfsar(0);
        setnBastariVazifeAfsar(0);
        setnNahastVazifeAfsar(0);
        setnFararVazifeAfsar(0);
        setnBazdashtVazifeAfsar(0);
        setnMontazerBekhedmatVazifeAfsar(0);
        setnMamorAzEdeVazifeAfsar(0);
        setnJameMotafaregheVazifeAfsar(0);
        setnMamorBeEdeVazifeAfsar(0);
        setnJameHazerVazifeAfsar(0);
        setnSobhaneVazifeAfsar(0);
        setnNaharVazifeAfsar(0);
        setnShamVazifeAfsar(0);

        setnEdeKolVazifeDaneshjo(0);
        setnMorakhasiVazifeDaneshjo(0);
        setnBastariVazifeDaneshjo(0);
        setnNahastVazifeDaneshjo(0);
        setnFararVazifeDaneshjo(0);
        setnBazdashtVazifeDaneshjo(0);
        setnMontazerBekhedmatVazifeDaneshjo(0);
        setnMamorAzEdeVazifeDaneshjo(0);
        setnJameMotafaregheVazifeDaneshjo(0);
        setnMamorBeEdeVazifeDaneshjo(0);
        setnJameHazerVazifeDaneshjo(0);
        setnSobhaneVazifeDaneshjo(0);
        setnNaharVazifeDaneshjo(0);
        setnShamVazifeDaneshjo(0);

        setnEdeKolVazifeDarajedar(0);
        setnMorakhasiVazifeDarajedar(0);
        setnBastariVazifeDarajedar(0);
        setnNahastVazifeDarajedar(0);
        setnFararVazifeDarajedar(0);
        setnBazdashtVazifeDarajedar(0);
        setnMontazerBekhedmatVazifeDarajedar(0);
        setnMamorAzEdeVazifeDarajedar(0);
        setnJameMotafaregheVazifeDarajedar(0);
        setnMamorBeEdeVazifeDarajedar(0);
        setnJameHazerVazifeDarajedar(0);
        setnSobhaneVazifeDarajedar(0);
        setnNaharVazifeDarajedar(0);
        setnShamVazifeDarajedar(0);
        setnEdeKolNavi(0);
        setnMorakhasiNavi(0);
        setnBastariNavi(0);
        setnNahastNavi(0);
        setnFararNavi(0);
        setnBazdashtNavi(0);
        setnMontazerBekhedmatNavi(0);
        setnMamorAzEdeNavi(0);
        setnJameMotafaregheNavi(0);
        setnMamorBeEdeNavi(0);
        setnJameHazerNavi(0);
        setnSobhaneNavi(0);
        setnNaharNavi(0);
        setnShamNavi(0);
        setnModavematKari(0);
        setnEzafekari(0);
        setTedadJireNahar(0);
        setSarparasteShobeAmar(null);
        setFarmandeYegan(null);
        setSarparasteShobeAmarNiroEnsani(null);
        setnAzMorakhasiPayvar(0);
        setnBeMorakhasiPayvar(0);
        setnAzNahastPayvar(0);
        setnBeNahastPayvar(0);
        setnAzBastariPayvar(0);
        setnBeBastariPayvar(0);
        setnAzMamoriyatPayvar(0);
        setnBeMamoriyatPayvar(0);
        setnKasrAzAmarPayvar(0);
        setnEzafBeAmarPayvar(0);
        setnEzafBeMamorBeEdePayvar(0);
        setnKasrAzMamorBeEdePayvar(0);
        setnBeEntezarBeKhedmatPayvar(0);
        setnAzEntezarKhedmatPayvar(0);
        setnBeBazdashtPayvar(0);
        setnAzBazdashtPayvar(0);
        setnAzMorakhasiVazife(0);
        setnBeMorakhasiVazife(0);
        setnAzNahastVazife(0);
        setnBeNahastVazife(0);
        setnAzBastariVazife(0);
        setnBeBastariVazife(0);
        setnAzMamoriyatVazife(0);
        setnBeMamoriyatVazife(0);
        setnKasrAzAmarVazife(0);
        setnEzafBeAmarVazife(0);
        setnEzafBeMamorBeEdeVazife(0);
        setnKasrAzMamorBeEdeVazife(0);
        setnBeEntezarBeKhedmatVazife(0);
        setnAzEntezarKhedmatVazife(0);
        setnBeBazdashtVazife(0);
        setnAzBazdashtVazife(0);

        setShowCrud(false);
        setDisableDayere(false);

    }


    public void report() {
        String query = "";

        List<AmarJireBegirReportModel> amjList = new ArrayList<>();
        List<Object> obj1List = new ArrayList<>();
        List<Object> obj2List = new ArrayList<>();
        List<Object> obj3List = new ArrayList<>();
        List<Object> obj4List = new ArrayList<>();
        List<Object> obj5List = new ArrayList<>();
        List<Object> obj6List = new ArrayList<>();
        List<Object> obj7List = new ArrayList<>();
        List<Object> obj8List = new ArrayList<>();
        List<Object> obj9List = new ArrayList<>();
        List<Object> objKolList = new ArrayList<>();

        String dayereQuery = "";

        StringBuilder dayereBuilder = new StringBuilder();
        StringBuilder bakhshBuilder = new StringBuilder();
        dayereBuilder.append("");
        bakhshBuilder.append("");

        String bakhshQuery = "";
        if (bakhshIdFilter != null) {
            bakhshQuery = (" AND bakhsh.id=" + bakhshIdFilter);

        } else if (dayereIdFilter != null) {
            if (dayereIdFilter == 0) {
                dayereQuery = (" ");
            } else {
                dayereQuery = (" AND dayere.id=" + dayereIdFilter);
            }
        } else if (bakhshList.size() > 0) {
            bakhshBuilder.append(" AND  ( ");
            int i = 0;
            for (Bakhsh b : bakhshList) {
                i = i + 1;
                if (bakhshList.size() == i) {
                    bakhshBuilder.append(" bakhsh.id=" + b.getId());
                } else {
                    bakhshBuilder.append(" bakhsh.id=" + b.getId() + " OR ");
                }
            }
            bakhshBuilder.append(" ) ");

        } else if (dayereList.size() == 1) {
            if (dayereList.get(0).getCode().equals("0000")) {
                dayereQuery = "";
            } else {
                dayereQuery = "AND dayere.id=" + dayereList.get(0).getId();
            }
        } else if (dayereList.size() > 0) {
            dayereBuilder.append(" AND  ( ");
            int i = 0;
            for (Dayere d : dayereList) {
                i = i + 1;
                if (dayereList.size() == i) {
                    dayereBuilder.append(" dayere.id=" + d.getId());
                } else {
                    dayereBuilder.append(" dayere.id=" + d.getId() + " OR ");
                }
            }
            dayereBuilder.append(" ) ");
        }
        dayereQuery=dayereBuilder.toString();
        bakhshQuery=bakhshBuilder.toString();


        String queryAfsarYekVaDo = "SELECT\n " +
                "SUM(amarAfsaranYekVaDo.nEdeKol) as edeKol ,\n" +
                "SUM(amarAfsaranYekVaDo.nMorakhasi) as morakhasi ,\n" +
                "SUM(amarAfsaranYekVaDo.nBastari) as bastari ,\n" +
                "SUM(amarAfsaranYekVaDo.nNahast) as nahast ,\n" +
                "SUM(amarAfsaranYekVaDo.nFarar) as farar ,\n" +
                "SUM(amarAfsaranYekVaDo.nBazdasht) as bazdasht ,\n" +
                "SUM(amarAfsaranYekVaDo.nMontazerBekhedmat) as montazerKhedmat ,\n" +
                "SUM(amarAfsaranYekVaDo.nMamorAzEde) as mamorAzEde ,\n" +
                "SUM(amarAfsaranYekVaDo.nJameMotafareghe) as jameMotafareghe ,\n" +
                "SUM(amarAfsaranYekVaDo.nMamorBeEde) as mamorBeEde ,\n" +
                "SUM(amarAfsaranYekVaDo.nJameHazer) as jameeHazer ,\n" +
                "SUM(amarAfsaranYekVaDo.nSobhane) as sobhane ,\n" +
                "SUM(amarAfsaranYekVaDo.nNahar) as nahar ,\n" +
                "SUM(amarAfsaranYekVaDo.nSham) as sham \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String afsarSe = "SELECT\n " +
                "SUM(amarAfsaranSe.nEdeKol) as edeKol  ,\n" +
                "SUM(amarAfsaranSe.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarAfsaranSe.nBastari) as bastari  ,\n" +
                "SUM(amarAfsaranSe.nNahast) as nahast  ,\n" +
                "SUM(amarAfsaranSe.nFarar) as farar  ,\n" +
                "SUM(amarAfsaranSe.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarAfsaranSe.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarAfsaranSe.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarAfsaranSe.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarAfsaranSe.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarAfsaranSe.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarAfsaranSe.nSobhane) as sobhane  ,\n" +
                "SUM(amarAfsaranSe.nNahar) as nahar  ,\n" +
                "SUM(amarAfsaranSe.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String darajeDar = "SELECT\n " +
                "SUM(amarDarajeDaran.nEdeKol) as edeKol  ,\n" +
                "SUM(amarDarajeDaran.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarDarajeDaran.nBastari) as bastari  ,\n" +
                "SUM(amarDarajeDaran.nNahast) as nahast  ,\n" +
                "SUM(amarDarajeDaran.nFarar) as farar  ,\n" +
                "SUM(amarDarajeDaran.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarDarajeDaran.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarDarajeDaran.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarDarajeDaran.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarDarajeDaran.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarDarajeDaran.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarDarajeDaran.nSobhane) as sobhane  ,\n" +
                "SUM(amarDarajeDaran.nNahar) as nahar  ,\n" +
                "SUM(amarDarajeDaran.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String karmandElmi = "SELECT\n " +
                "SUM(amarKarmandanElmi.nEdeKol) as edeKol  ,\n" +
                "SUM(amarKarmandanElmi.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarKarmandanElmi.nBastari) as bastari  ,\n" +
                "SUM(amarKarmandanElmi.nNahast) as nahast  ,\n" +
                "SUM(amarKarmandanElmi.nFarar) as farar  ,\n" +
                "SUM(amarKarmandanElmi.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarKarmandanElmi.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarKarmandanElmi.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarKarmandanElmi.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarKarmandanElmi.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarKarmandanElmi.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarKarmandanElmi.nSobhane) as sobhane  ,\n" +
                "SUM(amarKarmandanElmi.nNahar) as nahar  ,\n" +
                "SUM(amarKarmandanElmi.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String karmandTajrobi = "SELECT\n " +
                "SUM(amarKarmandanTajrobi.nEdeKol) as edeKol  ,\n" +
                "SUM(amarKarmandanTajrobi.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarKarmandanTajrobi.nBastari) as bastari  ,\n" +
                "SUM(amarKarmandanTajrobi.nNahast) as nahast  ,\n" +
                "SUM(amarKarmandanTajrobi.nFarar) as farar  ,\n" +
                "SUM(amarKarmandanTajrobi.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarKarmandanTajrobi.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarKarmandanTajrobi.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarKarmandanTajrobi.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarKarmandanTajrobi.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarKarmandanTajrobi.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarKarmandanTajrobi.nSobhane) as sobhane  ,\n" +
                "SUM(amarKarmandanTajrobi.nNahar) as nahar  ,\n" +
                "SUM(amarKarmandanTajrobi.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String afsarVazife = "SELECT\n " +
                "SUM(amarAfsarVazife.nEdeKol) as edeKol  ,\n" +
                "SUM(amarAfsarVazife.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarAfsarVazife.nBastari) as bastari  ,\n" +
                "SUM(amarAfsarVazife.nNahast) as nahast  ,\n" +
                "SUM(amarAfsarVazife.nFarar) as farar  ,\n" +
                "SUM(amarAfsarVazife.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarAfsarVazife.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarAfsarVazife.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarAfsarVazife.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarAfsarVazife.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarAfsarVazife.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarAfsarVazife.nSobhane) as sobhane  ,\n" +
                "SUM(amarAfsarVazife.nNahar) as nahar  ,\n" +
                "SUM(amarAfsarVazife.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;

        String daneshjoVazife = "SELECT\n " +
                "SUM(amarDaneshjoVazife.nEdeKol) as edeKol  ,\n" +
                "SUM(amarDaneshjoVazife.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarDaneshjoVazife.nBastari) as bastari  ,\n" +
                "SUM(amarDaneshjoVazife.nNahast) as nahast  ,\n" +
                "SUM(amarDaneshjoVazife.nFarar) as farar  ,\n" +
                "SUM(amarDaneshjoVazife.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarDaneshjoVazife.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarDaneshjoVazife.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarDaneshjoVazife.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarDaneshjoVazife.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarDaneshjoVazife.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarDaneshjoVazife.nSobhane) as sobhane  ,\n" +
                "SUM(amarDaneshjoVazife.nNahar) as nahar  ,\n" +
                "SUM(amarDaneshjoVazife.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String darajeDarVazife = "SELECT\n " +
                "SUM(amarDarajeDarVazife.nEdeKol) as edeKol  ,\n" +
                "SUM(amarDarajeDarVazife.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarDarajeDarVazife.nBastari) as bastari  ,\n" +
                "SUM(amarDarajeDarVazife.nNahast) as nahast  ,\n" +
                "SUM(amarDarajeDarVazife.nFarar) as farar  ,\n" +
                "SUM(amarDarajeDarVazife.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarDarajeDarVazife.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarDarajeDarVazife.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarDarajeDarVazife.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarDarajeDarVazife.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarDarajeDarVazife.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarDarajeDarVazife.nSobhane) as sobhane  ,\n" +
                "SUM(amarDarajeDarVazife.nNahar) as nahar  ,\n" +
                "SUM(amarDarajeDarVazife.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String navi = "SELECT\n " +
                "SUM(amarNavi.nEdeKol) as edeKol  ,\n" +
                "SUM(amarNavi.nMorakhasi) as morakhasi  ,\n" +
                "SUM(amarNavi.nBastari) as bastari  ,\n" +
                "SUM(amarNavi.nNahast) as nahast  ,\n" +
                "SUM(amarNavi.nFarar) as farar  ,\n" +
                "SUM(amarNavi.nBazdasht) as bazdasht  ,\n" +
                "SUM(amarNavi.nMontazerBekhedmat) as montazerKhedmat  ,\n" +
                "SUM(amarNavi.nMamorAzEde) as mamorAzEde  ,\n" +
                "SUM(amarNavi.nJameMotafareghe) as jameMotafareghe  ,\n" +
                "SUM(amarNavi.nMamorBeEde) as mamorBeEde  ,\n" +
                "SUM(amarNavi.nJameHazer) as jameeHazer  ,\n" +
                "SUM(amarNavi.nSobhane) as sobhane  ,\n" +
                "SUM(amarNavi.nNahar) as nahar  ,\n" +
                "SUM(amarNavi.nSham) as sham  \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "' " + dayereQuery + bakhshQuery;


        String amarKol = "SELECT\n " +
                "SUM(nAzMorakhasiPayvar) as nAzMorakhasiPayvar ,\n" +
                "SUM(nBeMorakhasiPayvar) as nBeMorakhasiPayvar ,\n" +
                "SUM(nAzNahastPayvar) as nAzNahastPayvar ,\n" +
                "SUM(nBeNahastPayvar) as nBeNahastPayvar ,\n" +
                "SUM(nAzBastariPayvar) as nAzBastariPayvar ,\n" +
                "SUM(nBeBastariPayvar) as nBeBastariPayvar ,\n" +
                "SUM(nAzMamoriyatPayvar) as nAzMamoriyatPayvar ,\n" +
                "SUM(nBeMamoriyatPayvar) as nBeMamoriyatPayvar ,\n" +
                "SUM(nKasrAzAmarPayvar) as nKasrAzAmarPayvar ,\n" +
                "SUM(nEzafBeAmarPayvar) as nEzafBeAmarPayvar ,\n" +
                "SUM(nEzafBeMamorBeEdePayvar) as nEzafBeMamorBeEdePayvar ,\n" +
                "SUM(nKasrAzMamorBeEdePayvar) as nKasrAzMamorBeEdePayvar ,\n" +
                "SUM(nBeEntezarBeKhedmatPayvar) as nBeEntezarBeKhedmatPayvar ,\n" +
                "SUM(nAzEntezarKhedmatPayvar) as nAzEntezarKhedmatPayvar ,\n" +
                "SUM(nBeBazdashtPayvar) as nBeBazdashtPayvar ,\n" +
                "SUM(nAzBazdashtPayvar) as nAzBazdashtPayvar ,\n" +
                "SUM(nAzMorakhasiVazife) as nAzMorakhasiVazife ,\n" +
                "SUM(nBeMorakhasiVazife) as nBeMorakhasiVazife ,\n" +
                "SUM(nAzNahastVazife) as nAzNahastVazife ,\n" +
                "SUM(nBeNahastVazife) as nBeNahastVazife ,\n" +
                "SUM(nAzBastariVazife) as nAzBastariVazife ,\n" +
                "SUM(nBeBastariVazife) as nBeBastariVazife ,\n" +
                "SUM(nAzMamoriyatVazife) as nAzMamoriyatVazife ,\n" +
                "SUM(nBeMamoriyatVazife) as nBeMamoriyatVazife ,\n" +
                "SUM(nKasrAzAmarVazife) as nKasrAzAmarVazife ,\n" +
                "SUM(nEzafBeAmarVazife) as nEzafBeAmarVazife ,\n" +
                "SUM(nEzafBeMamorBeEdeVazife) as nEzafBeMamorBeEdeVazife ,\n" +
                "SUM(nKasrAzMamorBeEdeVazife) as nKasrAzMamorBeEdeVazife ,\n" +
                "SUM(nBeEntezarBeKhedmatVazife) as nBeEntezarBeKhedmatVazife ,\n" +
                "SUM(nAzEntezarKhedmatVazife) as nAzEntezarKhedmatVazife ,\n" +
                "SUM(nAzBazdashtVazife) as nAzBazdashtVazife ,\n" +
                "SUM(nBeBazdashtVazife) as nBeBazdashtVazife ,\n" +
                "SUM(tedadJireNahar) as tedadJireNahar ,\n" +
                "molahezat as molahezat ,\n" +
                "SUM(nModavematKari) as nModavematKari ,\n" +
                "SUM(nEzafekari) as nEzafekari ,\n" +
                "dayere.id ,\n" +
                "bakhsh.id ,\n" +
                "taghvim.id \n" +
                "FROM\n" +
                "SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + "'  " + dayereQuery + bakhshQuery;

        List<SavabeghAmarJireBegirRozane> FSAList = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        obj1List = session.createQuery(queryAfsarYekVaDo).list();
        obj2List = session.createQuery(afsarSe).list();
        obj3List = session.createQuery(darajeDar).list();
        obj4List = session.createQuery(karmandElmi).list();
        obj5List = session.createQuery(karmandTajrobi).list();
        obj6List = session.createQuery(afsarVazife).list();
        obj7List = session.createQuery(darajeDarVazife).list();
        obj8List = session.createQuery(daneshjoVazife).list();
        obj9List = session.createQuery(navi).list();
        objKolList = session.createQuery(amarKol).list();
        FSAList = session.createQuery(" FROM SavabeghAmarJireBegirRozane where taghvim.tarikh='" + tarikhSabtFilter + " ' " + dayereQuery + bakhshQuery).list();


        session.close();

        for (Object o1 : obj1List) {
            Object[] objList = (Object[]) obj1List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("نظامیان1و2");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }


        for (Object o1 : obj2List) {
            Object[] objList = (Object[]) obj2List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("نظامیان3");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }


        for (Object o1 : obj3List) {
            Object[] objList = (Object[]) obj3List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("درجه دار");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }

        for (Object o1 : obj4List) {
            Object[] objList = (Object[]) obj4List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("کارمند علمی");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }
        for (Object o1 : obj5List) {
            Object[] objList = (Object[]) obj5List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("کارمند تجربی");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }
        for (Object o1 : obj6List) {
            Object[] objList = (Object[]) obj6List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("افسر وظیفه");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }
        for (Object o1 : obj7List) {
            Object[] objList = (Object[]) obj7List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("دانشجو وظیفه");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }
        for (Object o1 : obj8List) {
            Object[] objList = (Object[]) obj8List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("درجه دار وظیفه");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }
        for (Object o1 : obj9List) {
            Object[] objList = (Object[]) obj9List.get(0);
            AmarJireBegirReportModel model = new AmarJireBegirReportModel();
            model.setSharh("ناوی");
            if (objList[0] != null) {
                model.setnEdeKol(Integer.parseInt(String.valueOf(objList[0])));
            }
            if (objList[1] != null) {
                model.setnMorakhasi(Integer.parseInt(String.valueOf(objList[1])));
            }
            if (objList[2] != null) {
                model.setnBastari(Integer.parseInt(String.valueOf(objList[2])));
            }
            if (objList[3] != null) {
                model.setnNahast(Integer.parseInt(String.valueOf(objList[3])));
            }
            if (objList[4] != null) {
                model.setnFarar(Integer.parseInt(String.valueOf(objList[4])));
            }
            if (objList[5] != null) {
                model.setnBazdasht(Integer.parseInt(String.valueOf(objList[5])));
            }
            if (objList[6] != null) {
                model.setnMontazerKhedmat(Integer.parseInt(String.valueOf(objList[6])));
            }
            if (objList[7] != null) {
                model.setnMamorAzEde(Integer.parseInt(String.valueOf(objList[7])));
            }
            if (objList[8] != null) {
                model.setnJameMotafareghe(Integer.parseInt(String.valueOf(objList[8])));
            }
            if (objList[9] != null) {
                model.setnMamorBeEde(Integer.parseInt(String.valueOf(objList[9])));
            }
            if (objList[10] != null) {
                model.setnJameeHazer(Integer.parseInt(String.valueOf(objList[10])));
            }
            if (objList[11] != null) {
                model.setnSobhane(Integer.parseInt(String.valueOf(objList[11])));
            }
            if (objList[12] != null) {
                model.setnNahar(Integer.parseInt(String.valueOf(objList[12])));
            }
            if (objList[13] != null) {
                model.setnSham(Integer.parseInt(String.valueOf(objList[13])));
            }
            amjList.add(model);
        }


        SavabeghAmarJireBegirRozane model = new SavabeghAmarJireBegirRozane();
        for (Object o1 : objKolList) {
            Object[] obj = (Object[]) objKolList.get(0);
            model.setSarparasteShobeAmar(getSarparasteShobeAmar());
            model.setFarmandeYegan(getFarmandeYegan());
            model.setSarparasteShobeAmarNiroEnsani(getSarparasteShobeAmarNiroEnsani());
            if (obj[0] != null) {
                model.setnAzMorakhasiPayvar(Integer.parseInt(String.valueOf(obj[0])));
            }
            if (obj[1] != null) {
                model.setnBeMorakhasiPayvar(Integer.parseInt(String.valueOf(obj[1])));
            }
            if (obj[2] != null) {
                model.setnAzNahastPayvar(Integer.parseInt(String.valueOf(obj[2])));
            }
            if (obj[3] != null) {
                model.setnBeNahastPayvar(Integer.parseInt(String.valueOf(obj[3])));
            }
            if (obj[4] != null) {
                model.setnAzBastariPayvar(Integer.parseInt(String.valueOf(obj[4])));
            }
            if (obj[5] != null) {
                model.setnBeBastariPayvar(Integer.parseInt(String.valueOf(obj[5])));
            }
            if (obj[6] != null) {
                model.setnAzMamoriyatPayvar(Integer.parseInt(String.valueOf(obj[6])));
            }
            if (obj[7] != null) {
                model.setnBeMamoriyatPayvar(Integer.parseInt(String.valueOf(obj[7])));
            }
            if (obj[8] != null) {
                model.setnKasrAzAmarPayvar(Integer.parseInt(String.valueOf(obj[8])));
            }
            if (obj[9] != null) {
                model.setnEzafBeAmarPayvar(Integer.parseInt(String.valueOf(obj[9])));
            }
            if (obj[10] != null) {
                model.setnEzafBeMamorBeEdePayvar(Integer.parseInt(String.valueOf(obj[10])));
            }
            if (obj[11] != null) {
                model.setnKasrAzMamorBeEdePayvar(Integer.parseInt(String.valueOf(obj[11])));
            }
            if (obj[12] != null) {
                model.setnBeEntezarBeKhedmatPayvar(Integer.parseInt(String.valueOf(obj[12])));
            }
            if (obj[13] != null) {
                model.setnAzEntezarKhedmatPayvar(Integer.parseInt(String.valueOf(obj[13])));
            }
            if (obj[14] != null) {
                model.setnBeBazdashtPayvar(Integer.parseInt(String.valueOf(obj[14])));
            }
            if (obj[15] != null) {
                model.setnAzBazdashtPayvar(Integer.parseInt(String.valueOf(obj[15])));
            }
            if (obj[16] != null) {
                model.setnAzMorakhasiVazife(Integer.parseInt(String.valueOf(obj[16])));
            }
            if (obj[17] != null) {
                model.setnBeMorakhasiVazife(Integer.parseInt(String.valueOf(obj[17])));
            }
            if (obj[18] != null) {
                model.setnAzNahastVazife(Integer.parseInt(String.valueOf(obj[18])));
            }
            if (obj[19] != null) {
                model.setnBeNahastVazife(Integer.parseInt(String.valueOf(obj[19])));
            }
            if (obj[20] != null) {
                model.setnAzBastariVazife(Integer.parseInt(String.valueOf(obj[20])));
            }
            if (obj[21] != null) {
                model.setnBeBastariVazife(Integer.parseInt(String.valueOf(obj[21])));
            }
            if (obj[22] != null) {
                model.setnAzMamoriyatVazife(Integer.parseInt(String.valueOf(obj[22])));
            }
            if (obj[23] != null) {
                model.setnBeMamoriyatVazife(Integer.parseInt(String.valueOf(obj[23])));
            }
            if (obj[24] != null) {
                model.setnKasrAzAmarVazife(Integer.parseInt(String.valueOf(obj[24])));
            }
            if (obj[25] != null) {
                model.setnEzafBeAmarVazife(Integer.parseInt(String.valueOf(obj[25])));
            }
            if (obj[26] != null) {
                model.setnEzafBeMamorBeEdeVazife(Integer.parseInt(String.valueOf(obj[26])));
            }
            if (obj[27] != null) {
                model.setnKasrAzMamorBeEdeVazife(Integer.parseInt(String.valueOf(obj[27])));
            }
            if (obj[28] != null) {
                model.setnBeEntezarBeKhedmatVazife(Integer.parseInt(String.valueOf(obj[28])));
            }
            if (obj[29] != null) {
                model.setnAzEntezarKhedmatVazife(Integer.parseInt(String.valueOf(obj[29])));
            }
            if (obj[30] != null) {
                model.setnBeBazdashtVazife(Integer.parseInt(String.valueOf(obj[30])));
            }
            if (obj[31] != null) {
                model.setnAzBazdashtVazife(Integer.parseInt(String.valueOf(obj[31])));
            }
            if (obj[32] != null) {
                model.setTedadJireNahar(Integer.parseInt(String.valueOf(obj[32])));
            }
            if (obj[33] != null) {
                model.setMolahezat(String.valueOf(obj[33]));
            }
            if (obj[34] != null) {
                model.setnModavematKari(Integer.parseInt(String.valueOf(obj[34])));
            }
            if (obj[35] != null) {
                model.setnEzafekari(Integer.parseInt(String.valueOf(obj[35])));
            }

        }

        for (AmarJireBegirReportModel o : amjList) {
            setnEdeKolJame(getnEdeKolJame() + o.getnEdeKol());
            setnMorakhasiJame(getnMorakhasiJame() + o.getnMorakhasi());
            setnBastariJame(getnBastariJame() + o.getnBastari());
            setnNahastJame(getnNahastJame() + o.getnNahast());
            setnFararJame(getnFararJame() + o.getnFarar());
            setnBazdashtJame(getnBazdashtJame() + o.getnBazdasht());
            setnMontazerBekhedmatJame(getnMontazerBekhedmatJame() + o.getnMontazerKhedmat());
            setnMamorAzEdeJame(getnMamorAzEdeJame() + o.getnMamorAzEde());
            setnJameMotafaregheJame(getnJameMotafaregheJame() + o.getnJameMotafareghe());
            setnMamorBeEdeJame(getnMamorBeEdeJame() + o.getnMamorBeEde());
            setnJameHazerJame(getnJameHazerJame() + o.getnJameeHazer());
            setnSobhaneJame(getnSobhaneJame() + o.getnSobhane());
            setnNaharJame(getnNaharJame() + o.getnNahar());
            setnShamJame(getnShamJame() + o.getnSham());
        }

        if ((getnEdeKolJame() + getnMorakhasiJame() + getnBastariJame() + getnNahastJame()
                + getnFararJame() + getnBazdashtJame() + getnMontazerBekhedmatJame() + getnMamorAzEdeJame()
                + getnJameMotafaregheJame() + getnMamorBeEdeJame() + getnJameHazerJame() + getnSobhaneJame()
                + getnNaharJame() + getnShamJame()) != 0) {


            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File file = new File(path + "\\report\\AmarJireGhazaeeKarkonan.jrxml");
            try {
                JasperDesign jasperDesign = JRXmlLoader.load(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                Map map = new HashMap<>();
                map.put("tarikh", taghvim.getTarikh());
                map.put("amarJire", amjList);
                map.put("rooz", taghvim.getRoozHafte());
                map.put("jEdeKol", getnEdeKolJame());
                map.put("jMorakhasi", getnMorakhasiJame());
                map.put("jBastari", getnBastariJame());
                map.put("jNahast", getnNahastJame());
                map.put("jFarar", getnFararJame());
                map.put("jBazdasht", getnBazdashtJame());
                map.put("jMontazerKhedmat", getnMontazerBekhedmatJame());
                map.put("jMamorAzEde", getnMamorAzEdeJame());
                map.put("jJameMotafareghe", getnJameMotafaregheJame());
                map.put("jMamorBeEde", getnMamorBeEdeJame());
                map.put("jJameeHazer", getnJameHazerJame());
                map.put("jSobhane", getnSobhaneJame());
                map.put("jNahar", getnNaharJame());
                map.put("jSham", getnShamJame());
                if (!sarparasteShobeAmar.equals("")) {
                    map.put("edari", sarparasteShobeAmar);
                } else {
                    map.put("edari", FSAList.get(0).getSarparasteShobeAmar());
                }
                if (!farmandeYegan.equals("")) {
                    map.put("farmandeYegan", farmandeYegan);
                } else {
                    map.put("farmandeYegan", FSAList.get(0).getFarmandeYegan());
                }
                if (!sarparasteShobeAmarNiroEnsani.equals("")) {
                    map.put("sarparasteShobeAmar", sarparasteShobeAmarNiroEnsani);
                } else {
                    map.put("sarparasteShobeAmar", FSAList.get(0).getSarparasteShobeAmarNiroEnsani());
                }

                map.put("modavematKari", model.getnModavematKari());
                map.put("ezafeKari", model.getnEzafekari());
                if (!molahezatNew.equals("")) {
                    map.put("molahezat", molahezatNew);
                } else {
                    map.put("molahezat", model.getMolahezat());
                }
                map.put("jkolJire", model.getTedadJireNahar());
                map.put("jModavematKariVaEzafeKari", model.getnModavematKari() + model.getnEzafekari());
                map.put("azMorakhasiP", model.getnAzMorakhasiPayvar());
                map.put("beMorakhasiP", model.getnBeMorakhasiPayvar());
                map.put("azNahastP", model.getnAzNahastPayvar());
                map.put("beNahastP", model.getnBeNahastPayvar());
                map.put("azBastariP", model.getnAzBastariPayvar());
                map.put("beBastariP", model.getnBeBastariPayvar());
                map.put("azMamoriyatP", model.getnAzMamoriyatPayvar());
                map.put("beMamoriyatP", model.getnBeMamoriyatPayvar());
                map.put("kasrAzAmarP", model.getnKasrAzAmarPayvar());
                map.put("ezafBeAmarP", model.getnEzafBeAmarPayvar());
                map.put("EzafBeMamorBeEdeP", model.getnEzafBeMamorBeEdePayvar());
                map.put("kasrAzAmarMamorBeEdeP", model.getnKasrAzMamorBeEdePayvar());
                map.put("beEntezarKhedmatP", model.getnBeEntezarBeKhedmatPayvar());
                map.put("azEntezarKhedmatP", model.getnAzEntezarKhedmatPayvar());
                map.put("beBazdashtP", model.getnBeBazdashtPayvar());
                map.put("azBazdashtP", model.getnAzBazdashtPayvar());
                map.put("azMorakhasiV", model.getnAzMorakhasiVazife());
                map.put("beMorakhasiV", model.getnBeMorakhasiVazife());
                map.put("azNahastV", model.getnAzNahastVazife());
                map.put("beNahastV", model.getnBeNahastVazife());
                map.put("azBastariV", model.getnAzBastariVazife());
                map.put("beBastariV", model.getnBeBastariVazife());
                map.put("azMamoriyatV", model.getnAzMamoriyatVazife());
                map.put("beMamoriyatV", model.getnBeMamoriyatVazife());
                map.put("kasrAzAmarV", model.getnKasrAzAmarVazife());
                map.put("ezafBeAmarV", model.getnEzafBeAmarVazife());
                map.put("ezafBeMamorBeEdeV", model.getnEzafBeMamorBeEdeVazife());
                map.put("kasrAzMamorBeEdeV", model.getnKasrAzMamorBeEdeVazife());
                map.put("beEntezarKhedmatV", model.getnBeEntezarBeKhedmatVazife());
                map.put("azEntezarBeKhedmatV", model.getnAzEntezarKhedmatVazife());
                map.put("beBazdashtV", model.getnBeBazdashtVazife());
                map.put("azBazdashtV", model.getnAzBazdashtVazife());
                JasperPrint jasperPrint = JasperFillManager
                        .fillReport(jasperReport, map, new JREmptyDataSource());
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

            }
        } else {
            new Alert("توجه", "لوحه آمار تنظیم نگردیده است");
        }
    }

    public void nextTab(int i) {
        setTabIndex(i);
    }

    public void backTab(int i) {
        setTabIndex(i);
    }


    public List<SavabeghAmarJireBegirRozane> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<SavabeghAmarJireBegirRozane> dataTable) {
        this.dataTable = dataTable;
    }

    public SavabeghAmarJireBegirRozane getAmar() {
        return amar;
    }

    public void setAmar(SavabeghAmarJireBegirRozane amar) {
        this.amar = amar;
    }

    public AmarAfsaranYekVaDo getAmarAfsaranYekVaDo() {
        return amarAfsaranYekVaDo;
    }

    public void setAmarAfsaranYekVaDo(AmarAfsaranYekVaDo amarAfsaranYekVaDo) {
        this.amarAfsaranYekVaDo = amarAfsaranYekVaDo;
    }

    public AmarAfsaranSe getAmarAfsaranSe() {
        return amarAfsaranSe;
    }

    public void setAmarAfsaranSe(AmarAfsaranSe amarAfsaranSe) {
        this.amarAfsaranSe = amarAfsaranSe;
    }

    public AmarDarajeDaran getAmarDarajeDaran() {
        return amarDarajeDaran;
    }

    public void setAmarDarajeDaran(AmarDarajeDaran amarDarajeDaran) {
        this.amarDarajeDaran = amarDarajeDaran;
    }

    public AmarKarmandanTajrobi getAmarKarmandanTajrobi() {
        return amarKarmandanTajrobi;
    }

    public void setAmarKarmandanTajrobi(AmarKarmandanTajrobi amarKarmandanTajrobi) {
        this.amarKarmandanTajrobi = amarKarmandanTajrobi;
    }

    public AmarKarmandanElmi getAmarKarmandanElmi() {
        return amarKarmandanElmi;
    }

    public void setAmarKarmandanElmi(AmarKarmandanElmi amarKarmandanElmi) {
        this.amarKarmandanElmi = amarKarmandanElmi;
    }

    public AmarAfsarVazife getAmarAfsarVazife() {
        return amarAfsarVazife;
    }

    public void setAmarAfsarVazife(AmarAfsarVazife amarAfsarVazife) {
        this.amarAfsarVazife = amarAfsarVazife;
    }

    public AmarDaneshjoVazife getAmarDaneshjoVazife() {
        return amarDaneshjoVazife;
    }

    public void setAmarDaneshjoVazife(AmarDaneshjoVazife amarDaneshjoVazife) {
        this.amarDaneshjoVazife = amarDaneshjoVazife;
    }

    public AmarDarajeDarVazife getAmarDarajeDarVazife() {
        return amarDarajeDarVazife;
    }

    public void setAmarDarajeDarVazife(AmarDarajeDarVazife amarDarajeDarVazife) {
        this.amarDarajeDarVazife = amarDarajeDarVazife;
    }

    public AmarNavi getAmarNavi() {
        return amarNavi;
    }

    public void setAmarNavi(AmarNavi amarNavi) {
        this.amarNavi = amarNavi;
    }

    public int getnEdeKolAfsarYekVaDo() {
        return nEdeKolAfsarYekVaDo;
    }

    public void setnEdeKolAfsarYekVaDo(int nEdeKolAfsarYekVaDo) {
        this.nEdeKolAfsarYekVaDo = nEdeKolAfsarYekVaDo;
    }

    public int getnMorakhasiAfsarYekVaDo() {
        return nMorakhasiAfsarYekVaDo;
    }

    public void setnMorakhasiAfsarYekVaDo(int nMorakhasiAfsarYekVaDo) {
        this.nMorakhasiAfsarYekVaDo = nMorakhasiAfsarYekVaDo;
    }

    public int getnBastariAfsarYekVaDo() {
        return nBastariAfsarYekVaDo;
    }

    public void setnBastariAfsarYekVaDo(int nBastariAfsarYekVaDo) {
        this.nBastariAfsarYekVaDo = nBastariAfsarYekVaDo;
    }

    public int getnNahastAfsarYekVaDo() {
        return nNahastAfsarYekVaDo;
    }

    public void setnNahastAfsarYekVaDo(int nNahastAfsarYekVaDo) {
        this.nNahastAfsarYekVaDo = nNahastAfsarYekVaDo;
    }

    public int getnFararAfsarYekVaDo() {
        return nFararAfsarYekVaDo;
    }

    public void setnFararAfsarYekVaDo(int nFararAfsarYekVaDo) {
        this.nFararAfsarYekVaDo = nFararAfsarYekVaDo;
    }

    public int getnBazdashtAfsarYekVaDo() {
        return nBazdashtAfsarYekVaDo;
    }

    public void setnBazdashtAfsarYekVaDo(int nBazdashtAfsarYekVaDo) {
        this.nBazdashtAfsarYekVaDo = nBazdashtAfsarYekVaDo;
    }

    public int getnMontazerBekhedmatAfsarYekVaDo() {
        return nMontazerBekhedmatAfsarYekVaDo;
    }

    public void setnMontazerBekhedmatAfsarYekVaDo(int nMontazerBekhedmatAfsarYekVaDo) {
        this.nMontazerBekhedmatAfsarYekVaDo = nMontazerBekhedmatAfsarYekVaDo;
    }

    public int getnMamorAzEdeAfsarYekVaDo() {
        return nMamorAzEdeAfsarYekVaDo;
    }

    public void setnMamorAzEdeAfsarYekVaDo(int nMamorAzEdeAfsarYekVaDo) {
        this.nMamorAzEdeAfsarYekVaDo = nMamorAzEdeAfsarYekVaDo;
    }

    public int getnJameMotafaregheAfsarYekVaDo() {
        return nJameMotafaregheAfsarYekVaDo;
    }

    public void setnJameMotafaregheAfsarYekVaDo(int nJameMotafaregheAfsarYekVaDo) {
        this.nJameMotafaregheAfsarYekVaDo = nJameMotafaregheAfsarYekVaDo;
    }

    public int getnMamorBeEdeAfsarYekVaDo() {
        return nMamorBeEdeAfsarYekVaDo;
    }

    public void setnMamorBeEdeAfsarYekVaDo(int nMamorBeEdeAfsarYekVaDo) {
        this.nMamorBeEdeAfsarYekVaDo = nMamorBeEdeAfsarYekVaDo;
    }

    public int getnJameHazerAfsarYekVaDo() {
        return nJameHazerAfsarYekVaDo;
    }

    public void setnJameHazerAfsarYekVaDo(int nJameHazerAfsarYekVaDo) {
        this.nJameHazerAfsarYekVaDo = nJameHazerAfsarYekVaDo;
    }

    public int getnSobhaneAfsarYekVaDo() {
        return nSobhaneAfsarYekVaDo;
    }

    public void setnSobhaneAfsarYekVaDo(int nSobhaneAfsarYekVaDo) {
        this.nSobhaneAfsarYekVaDo = nSobhaneAfsarYekVaDo;
    }

    public int getnNaharAfsarYekVaDo() {
        return nNaharAfsarYekVaDo;
    }

    public void setnNaharAfsarYekVaDo(int nNaharAfsarYekVaDo) {
        this.nNaharAfsarYekVaDo = nNaharAfsarYekVaDo;
    }

    public int getnShamAfsarYekVaDo() {
        return nShamAfsarYekVaDo;
    }

    public void setnShamAfsarYekVaDo(int nShamAfsarYekVaDo) {
        this.nShamAfsarYekVaDo = nShamAfsarYekVaDo;
    }

    public int getnEdeKolAfsarSe() {
        return nEdeKolAfsarSe;
    }

    public void setnEdeKolAfsarSe(int nEdeKolAfsarSe) {
        this.nEdeKolAfsarSe = nEdeKolAfsarSe;
    }

    public int getnMorakhasiAfsarSe() {
        return nMorakhasiAfsarSe;
    }

    public void setnMorakhasiAfsarSe(int nMorakhasiAfsarSe) {
        this.nMorakhasiAfsarSe = nMorakhasiAfsarSe;
    }

    public int getnBastariAfsarSe() {
        return nBastariAfsarSe;
    }

    public void setnBastariAfsarSe(int nBastariAfsarSe) {
        this.nBastariAfsarSe = nBastariAfsarSe;
    }

    public int getnNahastAfsarSe() {
        return nNahastAfsarSe;
    }

    public void setnNahastAfsarSe(int nNahastAfsarSe) {
        this.nNahastAfsarSe = nNahastAfsarSe;
    }

    public int getnFararAfsarSe() {
        return nFararAfsarSe;
    }

    public void setnFararAfsarSe(int nFararAfsarSe) {
        this.nFararAfsarSe = nFararAfsarSe;
    }

    public int getnBazdashtAfsarSe() {
        return nBazdashtAfsarSe;
    }

    public void setnBazdashtAfsarSe(int nBazdashtAfsarSe) {
        this.nBazdashtAfsarSe = nBazdashtAfsarSe;
    }

    public int getnMontazerBekhedmatAfsarSe() {
        return nMontazerBekhedmatAfsarSe;
    }

    public void setnMontazerBekhedmatAfsarSe(int nMontazerBekhedmatAfsarSe) {
        this.nMontazerBekhedmatAfsarSe = nMontazerBekhedmatAfsarSe;
    }

    public int getnMamorAzEdeAfsarSe() {
        return nMamorAzEdeAfsarSe;
    }

    public void setnMamorAzEdeAfsarSe(int nMamorAzEdeAfsarSe) {
        this.nMamorAzEdeAfsarSe = nMamorAzEdeAfsarSe;
    }

    public int getnJameMotafaregheAfsarSe() {
        return nJameMotafaregheAfsarSe;
    }

    public void setnJameMotafaregheAfsarSe(int nJameMotafaregheAfsarSe) {
        this.nJameMotafaregheAfsarSe = nJameMotafaregheAfsarSe;
    }

    public int getnMamorBeEdeAfsarSe() {
        return nMamorBeEdeAfsarSe;
    }

    public void setnMamorBeEdeAfsarSe(int nMamorBeEdeAfsarSe) {
        this.nMamorBeEdeAfsarSe = nMamorBeEdeAfsarSe;
    }

    public int getnJameHazerAfsarSe() {
        return nJameHazerAfsarSe;
    }

    public void setnJameHazerAfsarSe(int nJameHazerAfsarSe) {
        this.nJameHazerAfsarSe = nJameHazerAfsarSe;
    }

    public int getnSobhaneAfsarSe() {
        return nSobhaneAfsarSe;
    }

    public void setnSobhaneAfsarSe(int nSobhaneAfsarSe) {
        this.nSobhaneAfsarSe = nSobhaneAfsarSe;
    }

    public int getnNaharAfsarSe() {
        return nNaharAfsarSe;
    }

    public void setnNaharAfsarSe(int nNaharAfsarSe) {
        this.nNaharAfsarSe = nNaharAfsarSe;
    }

    public int getnShamAfsarSe() {
        return nShamAfsarSe;
    }

    public void setnShamAfsarSe(int nShamAfsarSe) {
        this.nShamAfsarSe = nShamAfsarSe;
    }

    public int getnEdeKolKarmandElmi() {
        return nEdeKolKarmandElmi;
    }

    public void setnEdeKolKarmandElmi(int nEdeKolKarmandElmi) {
        this.nEdeKolKarmandElmi = nEdeKolKarmandElmi;
    }

    public int getnMorakhasiKarmandElmi() {
        return nMorakhasiKarmandElmi;
    }

    public void setnMorakhasiKarmandElmi(int nMorakhasiKarmandElmi) {
        this.nMorakhasiKarmandElmi = nMorakhasiKarmandElmi;
    }

    public int getnBastariKarmandElmi() {
        return nBastariKarmandElmi;
    }

    public void setnBastariKarmandElmi(int nBastariKarmandElmi) {
        this.nBastariKarmandElmi = nBastariKarmandElmi;
    }

    public int getnNahastKarmandElmi() {
        return nNahastKarmandElmi;
    }

    public void setnNahastKarmandElmi(int nNahastKarmandElmi) {
        this.nNahastKarmandElmi = nNahastKarmandElmi;
    }

    public int getnFararKarmandElmi() {
        return nFararKarmandElmi;
    }

    public void setnFararKarmandElmi(int nFararKarmandElmi) {
        this.nFararKarmandElmi = nFararKarmandElmi;
    }

    public int getnBazdashtKarmandElmi() {
        return nBazdashtKarmandElmi;
    }

    public void setnBazdashtKarmandElmi(int nBazdashtKarmandElmi) {
        this.nBazdashtKarmandElmi = nBazdashtKarmandElmi;
    }

    public int getnMontazerBekhedmatKarmandElmi() {
        return nMontazerBekhedmatKarmandElmi;
    }

    public void setnMontazerBekhedmatKarmandElmi(int nMontazerBekhedmatKarmandElmi) {
        this.nMontazerBekhedmatKarmandElmi = nMontazerBekhedmatKarmandElmi;
    }

    public int getnMamorAzEdeKarmandElmi() {
        return nMamorAzEdeKarmandElmi;
    }

    public void setnMamorAzEdeKarmandElmi(int nMamorAzEdeKarmandElmi) {
        this.nMamorAzEdeKarmandElmi = nMamorAzEdeKarmandElmi;
    }

    public int getnJameMotafaregheKarmandElmi() {
        return nJameMotafaregheKarmandElmi;
    }

    public void setnJameMotafaregheKarmandElmi(int nJameMotafaregheKarmandElmi) {
        this.nJameMotafaregheKarmandElmi = nJameMotafaregheKarmandElmi;
    }

    public int getnMamorBeEdeKarmandElmi() {
        return nMamorBeEdeKarmandElmi;
    }

    public void setnMamorBeEdeKarmandElmi(int nMamorBeEdeKarmandElmi) {
        this.nMamorBeEdeKarmandElmi = nMamorBeEdeKarmandElmi;
    }

    public int getnJameHazerKarmandElmi() {
        return nJameHazerKarmandElmi;
    }

    public void setnJameHazerKarmandElmi(int nJameHazerKarmandElmi) {
        this.nJameHazerKarmandElmi = nJameHazerKarmandElmi;
    }

    public int getnSobhaneKarmandElmi() {
        return nSobhaneKarmandElmi;
    }

    public void setnSobhaneKarmandElmi(int nSobhaneKarmandElmi) {
        this.nSobhaneKarmandElmi = nSobhaneKarmandElmi;
    }

    public int getnNaharKarmandElmi() {
        return nNaharKarmandElmi;
    }

    public void setnNaharKarmandElmi(int nNaharKarmandElmi) {
        this.nNaharKarmandElmi = nNaharKarmandElmi;
    }

    public int getnShamKarmandElmi() {
        return nShamKarmandElmi;
    }

    public void setnShamKarmandElmi(int nShamKarmandElmi) {
        this.nShamKarmandElmi = nShamKarmandElmi;
    }

    public int getnEdeKolKarmandTajrobi() {
        return nEdeKolKarmandTajrobi;
    }

    public void setnEdeKolKarmandTajrobi(int nEdeKolKarmandTajrobi) {
        this.nEdeKolKarmandTajrobi = nEdeKolKarmandTajrobi;
    }

    public int getnMorakhasiKarmandTajrobi() {
        return nMorakhasiKarmandTajrobi;
    }

    public void setnMorakhasiKarmandTajrobi(int nMorakhasiKarmandTajrobi) {
        this.nMorakhasiKarmandTajrobi = nMorakhasiKarmandTajrobi;
    }

    public int getnBastariKarmandTajrobi() {
        return nBastariKarmandTajrobi;
    }

    public void setnBastariKarmandTajrobi(int nBastariKarmandTajrobi) {
        this.nBastariKarmandTajrobi = nBastariKarmandTajrobi;
    }

    public int getnNahastKarmandTajrobi() {
        return nNahastKarmandTajrobi;
    }

    public void setnNahastKarmandTajrobi(int nNahastKarmandTajrobi) {
        this.nNahastKarmandTajrobi = nNahastKarmandTajrobi;
    }

    public int getnFararKarmandTajrobi() {
        return nFararKarmandTajrobi;
    }

    public void setnFararKarmandTajrobi(int nFararKarmandTajrobi) {
        this.nFararKarmandTajrobi = nFararKarmandTajrobi;
    }

    public int getnBazdashtKarmandTajrobi() {
        return nBazdashtKarmandTajrobi;
    }

    public void setnBazdashtKarmandTajrobi(int nBazdashtKarmandTajrobi) {
        this.nBazdashtKarmandTajrobi = nBazdashtKarmandTajrobi;
    }

    public int getnMontazerBekhedmatKarmandTajrobi() {
        return nMontazerBekhedmatKarmandTajrobi;
    }

    public void setnMontazerBekhedmatKarmandTajrobi(int nMontazerBekhedmatKarmandTajrobi) {
        this.nMontazerBekhedmatKarmandTajrobi = nMontazerBekhedmatKarmandTajrobi;
    }

    public int getnMamorAzEdeKarmandTajrobi() {
        return nMamorAzEdeKarmandTajrobi;
    }

    public void setnMamorAzEdeKarmandTajrobi(int nMamorAzEdeKarmandTajrobi) {
        this.nMamorAzEdeKarmandTajrobi = nMamorAzEdeKarmandTajrobi;
    }

    public int getnJameMotafaregheKarmandTajrobi() {
        return nJameMotafaregheKarmandTajrobi;
    }

    public void setnJameMotafaregheKarmandTajrobi(int nJameMotafaregheKarmandTajrobi) {
        this.nJameMotafaregheKarmandTajrobi = nJameMotafaregheKarmandTajrobi;
    }

    public int getnMamorBeEdeKarmandTajrobi() {
        return nMamorBeEdeKarmandTajrobi;
    }

    public void setnMamorBeEdeKarmandTajrobi(int nMamorBeEdeKarmandTajrobi) {
        this.nMamorBeEdeKarmandTajrobi = nMamorBeEdeKarmandTajrobi;
    }

    public int getnJameHazerKarmandTajrobi() {
        return nJameHazerKarmandTajrobi;
    }

    public void setnJameHazerKarmandTajrobi(int nJameHazerKarmandTajrobi) {
        this.nJameHazerKarmandTajrobi = nJameHazerKarmandTajrobi;
    }

    public int getnSobhaneKarmandTajrobi() {
        return nSobhaneKarmandTajrobi;
    }

    public void setnSobhaneKarmandTajrobi(int nSobhaneKarmandTajrobi) {
        this.nSobhaneKarmandTajrobi = nSobhaneKarmandTajrobi;
    }

    public int getnNaharKarmandTajrobi() {
        return nNaharKarmandTajrobi;
    }

    public void setnNaharKarmandTajrobi(int nNaharKarmandTajrobi) {
        this.nNaharKarmandTajrobi = nNaharKarmandTajrobi;
    }

    public int getnShamKarmandTajrobi() {
        return nShamKarmandTajrobi;
    }

    public void setnShamKarmandTajrobi(int nShamKarmandTajrobi) {
        this.nShamKarmandTajrobi = nShamKarmandTajrobi;
    }

    public int getnEdeKolVazifeAfsar() {
        return nEdeKolVazifeAfsar;
    }

    public void setnEdeKolVazifeAfsar(int nEdeKolVazifeAfsar) {
        this.nEdeKolVazifeAfsar = nEdeKolVazifeAfsar;
    }

    public int getnMorakhasiVazifeAfsar() {
        return nMorakhasiVazifeAfsar;
    }

    public void setnMorakhasiVazifeAfsar(int nMorakhasiVazifeAfsar) {
        this.nMorakhasiVazifeAfsar = nMorakhasiVazifeAfsar;
    }

    public int getnBastariVazifeAfsar() {
        return nBastariVazifeAfsar;
    }

    public void setnBastariVazifeAfsar(int nBastariVazifeAfsar) {
        this.nBastariVazifeAfsar = nBastariVazifeAfsar;
    }

    public int getnNahastVazifeAfsar() {
        return nNahastVazifeAfsar;
    }

    public void setnNahastVazifeAfsar(int nNahastVazifeAfsar) {
        this.nNahastVazifeAfsar = nNahastVazifeAfsar;
    }

    public int getnFararVazifeAfsar() {
        return nFararVazifeAfsar;
    }

    public void setnFararVazifeAfsar(int nFararVazifeAfsar) {
        this.nFararVazifeAfsar = nFararVazifeAfsar;
    }

    public int getnBazdashtVazifeAfsar() {
        return nBazdashtVazifeAfsar;
    }

    public void setnBazdashtVazifeAfsar(int nBazdashtVazifeAfsar) {
        this.nBazdashtVazifeAfsar = nBazdashtVazifeAfsar;
    }

    public int getnMontazerBekhedmatVazifeAfsar() {
        return nMontazerBekhedmatVazifeAfsar;
    }

    public void setnMontazerBekhedmatVazifeAfsar(int nMontazerBekhedmatVazifeAfsar) {
        this.nMontazerBekhedmatVazifeAfsar = nMontazerBekhedmatVazifeAfsar;
    }

    public int getnMamorAzEdeVazifeAfsar() {
        return nMamorAzEdeVazifeAfsar;
    }

    public void setnMamorAzEdeVazifeAfsar(int nMamorAzEdeVazifeAfsar) {
        this.nMamorAzEdeVazifeAfsar = nMamorAzEdeVazifeAfsar;
    }

    public int getnJameMotafaregheVazifeAfsar() {
        return nJameMotafaregheVazifeAfsar;
    }

    public void setnJameMotafaregheVazifeAfsar(int nJameMotafaregheVazifeAfsar) {
        this.nJameMotafaregheVazifeAfsar = nJameMotafaregheVazifeAfsar;
    }

    public int getnMamorBeEdeVazifeAfsar() {
        return nMamorBeEdeVazifeAfsar;
    }

    public void setnMamorBeEdeVazifeAfsar(int nMamorBeEdeVazifeAfsar) {
        this.nMamorBeEdeVazifeAfsar = nMamorBeEdeVazifeAfsar;
    }

    public int getnJameHazerVazifeAfsar() {
        return nJameHazerVazifeAfsar;
    }

    public void setnJameHazerVazifeAfsar(int nJameHazerVazifeAfsar) {
        this.nJameHazerVazifeAfsar = nJameHazerVazifeAfsar;
    }

    public int getnSobhaneVazifeAfsar() {
        return nSobhaneVazifeAfsar;
    }

    public void setnSobhaneVazifeAfsar(int nSobhaneVazifeAfsar) {
        this.nSobhaneVazifeAfsar = nSobhaneVazifeAfsar;
    }

    public int getnNaharVazifeAfsar() {
        return nNaharVazifeAfsar;
    }

    public void setnNaharVazifeAfsar(int nNaharVazifeAfsar) {
        this.nNaharVazifeAfsar = nNaharVazifeAfsar;
    }

    public int getnShamVazifeAfsar() {
        return nShamVazifeAfsar;
    }

    public void setnShamVazifeAfsar(int nShamVazifeAfsar) {
        this.nShamVazifeAfsar = nShamVazifeAfsar;
    }

    public int getnEdeKolVazifeDaneshjo() {
        return nEdeKolVazifeDaneshjo;
    }

    public void setnEdeKolVazifeDaneshjo(int nEdeKolVazifeDaneshjo) {
        this.nEdeKolVazifeDaneshjo = nEdeKolVazifeDaneshjo;
    }

    public int getnMorakhasiVazifeDaneshjo() {
        return nMorakhasiVazifeDaneshjo;
    }

    public void setnMorakhasiVazifeDaneshjo(int nMorakhasiVazifeDaneshjo) {
        this.nMorakhasiVazifeDaneshjo = nMorakhasiVazifeDaneshjo;
    }

    public int getnBastariVazifeDaneshjo() {
        return nBastariVazifeDaneshjo;
    }

    public void setnBastariVazifeDaneshjo(int nBastariVazifeDaneshjo) {
        this.nBastariVazifeDaneshjo = nBastariVazifeDaneshjo;
    }

    public int getnNahastVazifeDaneshjo() {
        return nNahastVazifeDaneshjo;
    }

    public void setnNahastVazifeDaneshjo(int nNahastVazifeDaneshjo) {
        this.nNahastVazifeDaneshjo = nNahastVazifeDaneshjo;
    }

    public int getnFararVazifeDaneshjo() {
        return nFararVazifeDaneshjo;
    }

    public void setnFararVazifeDaneshjo(int nFararVazifeDaneshjo) {
        this.nFararVazifeDaneshjo = nFararVazifeDaneshjo;
    }

    public int getnBazdashtVazifeDaneshjo() {
        return nBazdashtVazifeDaneshjo;
    }

    public void setnBazdashtVazifeDaneshjo(int nBazdashtVazifeDaneshjo) {
        this.nBazdashtVazifeDaneshjo = nBazdashtVazifeDaneshjo;
    }

    public int getnMontazerBekhedmatVazifeDaneshjo() {
        return nMontazerBekhedmatVazifeDaneshjo;
    }

    public void setnMontazerBekhedmatVazifeDaneshjo(int nMontazerBekhedmatVazifeDaneshjo) {
        this.nMontazerBekhedmatVazifeDaneshjo = nMontazerBekhedmatVazifeDaneshjo;
    }

    public int getnMamorAzEdeVazifeDaneshjo() {
        return nMamorAzEdeVazifeDaneshjo;
    }

    public void setnMamorAzEdeVazifeDaneshjo(int nMamorAzEdeVazifeDaneshjo) {
        this.nMamorAzEdeVazifeDaneshjo = nMamorAzEdeVazifeDaneshjo;
    }

    public int getnJameMotafaregheVazifeDaneshjo() {
        return nJameMotafaregheVazifeDaneshjo;
    }

    public void setnJameMotafaregheVazifeDaneshjo(int nJameMotafaregheVazifeDaneshjo) {
        this.nJameMotafaregheVazifeDaneshjo = nJameMotafaregheVazifeDaneshjo;
    }

    public int getnMamorBeEdeVazifeDaneshjo() {
        return nMamorBeEdeVazifeDaneshjo;
    }

    public void setnMamorBeEdeVazifeDaneshjo(int nMamorBeEdeVazifeDaneshjo) {
        this.nMamorBeEdeVazifeDaneshjo = nMamorBeEdeVazifeDaneshjo;
    }

    public int getnJameHazerVazifeDaneshjo() {
        return nJameHazerVazifeDaneshjo;
    }

    public void setnJameHazerVazifeDaneshjo(int nJameHazerVazifeDaneshjo) {
        this.nJameHazerVazifeDaneshjo = nJameHazerVazifeDaneshjo;
    }

    public int getnSobhaneVazifeDaneshjo() {
        return nSobhaneVazifeDaneshjo;
    }

    public void setnSobhaneVazifeDaneshjo(int nSobhaneVazifeDaneshjo) {
        this.nSobhaneVazifeDaneshjo = nSobhaneVazifeDaneshjo;
    }

    public int getnNaharVazifeDaneshjo() {
        return nNaharVazifeDaneshjo;
    }

    public void setnNaharVazifeDaneshjo(int nNaharVazifeDaneshjo) {
        this.nNaharVazifeDaneshjo = nNaharVazifeDaneshjo;
    }

    public int getnShamVazifeDaneshjo() {
        return nShamVazifeDaneshjo;
    }

    public void setnShamVazifeDaneshjo(int nShamVazifeDaneshjo) {
        this.nShamVazifeDaneshjo = nShamVazifeDaneshjo;
    }

    public int getnEdeKolVazifeDarajedar() {
        return nEdeKolVazifeDarajedar;
    }

    public void setnEdeKolVazifeDarajedar(int nEdeKolVazifeDarajedar) {
        this.nEdeKolVazifeDarajedar = nEdeKolVazifeDarajedar;
    }

    public int getnMorakhasiVazifeDarajedar() {
        return nMorakhasiVazifeDarajedar;
    }

    public void setnMorakhasiVazifeDarajedar(int nMorakhasiVazifeDarajedar) {
        this.nMorakhasiVazifeDarajedar = nMorakhasiVazifeDarajedar;
    }

    public int getnBastariVazifeDarajedar() {
        return nBastariVazifeDarajedar;
    }

    public void setnBastariVazifeDarajedar(int nBastariVazifeDarajedar) {
        this.nBastariVazifeDarajedar = nBastariVazifeDarajedar;
    }

    public int getnNahastVazifeDarajedar() {
        return nNahastVazifeDarajedar;
    }

    public void setnNahastVazifeDarajedar(int nNahastVazifeDarajedar) {
        this.nNahastVazifeDarajedar = nNahastVazifeDarajedar;
    }

    public int getnFararVazifeDarajedar() {
        return nFararVazifeDarajedar;
    }

    public void setnFararVazifeDarajedar(int nFararVazifeDarajedar) {
        this.nFararVazifeDarajedar = nFararVazifeDarajedar;
    }

    public int getnBazdashtVazifeDarajedar() {
        return nBazdashtVazifeDarajedar;
    }

    public void setnBazdashtVazifeDarajedar(int nBazdashtVazifeDarajedar) {
        this.nBazdashtVazifeDarajedar = nBazdashtVazifeDarajedar;
    }

    public int getnMontazerBekhedmatVazifeDarajedar() {
        return nMontazerBekhedmatVazifeDarajedar;
    }

    public void setnMontazerBekhedmatVazifeDarajedar(int nMontazerBekhedmatVazifeDarajedar) {
        this.nMontazerBekhedmatVazifeDarajedar = nMontazerBekhedmatVazifeDarajedar;
    }

    public int getnMamorAzEdeVazifeDarajedar() {
        return nMamorAzEdeVazifeDarajedar;
    }

    public void setnMamorAzEdeVazifeDarajedar(int nMamorAzEdeVazifeDarajedar) {
        this.nMamorAzEdeVazifeDarajedar = nMamorAzEdeVazifeDarajedar;
    }

    public int getnJameMotafaregheVazifeDarajedar() {
        return nJameMotafaregheVazifeDarajedar;
    }

    public void setnJameMotafaregheVazifeDarajedar(int nJameMotafaregheVazifeDarajedar) {
        this.nJameMotafaregheVazifeDarajedar = nJameMotafaregheVazifeDarajedar;
    }

    public int getnMamorBeEdeVazifeDarajedar() {
        return nMamorBeEdeVazifeDarajedar;
    }

    public void setnMamorBeEdeVazifeDarajedar(int nMamorBeEdeVazifeDarajedar) {
        this.nMamorBeEdeVazifeDarajedar = nMamorBeEdeVazifeDarajedar;
    }

    public int getnJameHazerVazifeDarajedar() {
        return nJameHazerVazifeDarajedar;
    }

    public void setnJameHazerVazifeDarajedar(int nJameHazerVazifeDarajedar) {
        this.nJameHazerVazifeDarajedar = nJameHazerVazifeDarajedar;
    }

    public int getnSobhaneVazifeDarajedar() {
        return nSobhaneVazifeDarajedar;
    }

    public void setnSobhaneVazifeDarajedar(int nSobhaneVazifeDarajedar) {
        this.nSobhaneVazifeDarajedar = nSobhaneVazifeDarajedar;
    }

    public int getnNaharVazifeDarajedar() {
        return nNaharVazifeDarajedar;
    }

    public void setnNaharVazifeDarajedar(int nNaharVazifeDarajedar) {
        this.nNaharVazifeDarajedar = nNaharVazifeDarajedar;
    }

    public int getnShamVazifeDarajedar() {
        return nShamVazifeDarajedar;
    }

    public void setnShamVazifeDarajedar(int nShamVazifeDarajedar) {
        this.nShamVazifeDarajedar = nShamVazifeDarajedar;
    }

    public int getnEdeKolNavi() {
        return nEdeKolNavi;
    }

    public void setnEdeKolNavi(int nEdeKolNavi) {
        this.nEdeKolNavi = nEdeKolNavi;
    }

    public int getnMorakhasiNavi() {
        return nMorakhasiNavi;
    }

    public void setnMorakhasiNavi(int nMorakhasiNavi) {
        this.nMorakhasiNavi = nMorakhasiNavi;
    }

    public int getnBastariNavi() {
        return nBastariNavi;
    }

    public void setnBastariNavi(int nBastariNavi) {
        this.nBastariNavi = nBastariNavi;
    }

    public int getnNahastNavi() {
        return nNahastNavi;
    }

    public void setnNahastNavi(int nNahastNavi) {
        this.nNahastNavi = nNahastNavi;
    }

    public int getnFararNavi() {
        return nFararNavi;
    }

    public void setnFararNavi(int nFararNavi) {
        this.nFararNavi = nFararNavi;
    }

    public int getnBazdashtNavi() {
        return nBazdashtNavi;
    }

    public void setnBazdashtNavi(int nBazdashtNavi) {
        this.nBazdashtNavi = nBazdashtNavi;
    }

    public int getnMontazerBekhedmatNavi() {
        return nMontazerBekhedmatNavi;
    }

    public void setnMontazerBekhedmatNavi(int nMontazerBekhedmatNavi) {
        this.nMontazerBekhedmatNavi = nMontazerBekhedmatNavi;
    }

    public int getnMamorAzEdeNavi() {
        return nMamorAzEdeNavi;
    }

    public void setnMamorAzEdeNavi(int nMamorAzEdeNavi) {
        this.nMamorAzEdeNavi = nMamorAzEdeNavi;
    }

    public int getnJameMotafaregheNavi() {
        return nJameMotafaregheNavi;
    }

    public void setnJameMotafaregheNavi(int nJameMotafaregheNavi) {
        this.nJameMotafaregheNavi = nJameMotafaregheNavi;
    }

    public int getnMamorBeEdeNavi() {
        return nMamorBeEdeNavi;
    }

    public void setnMamorBeEdeNavi(int nMamorBeEdeNavi) {
        this.nMamorBeEdeNavi = nMamorBeEdeNavi;
    }

    public int getnJameHazerNavi() {
        return nJameHazerNavi;
    }

    public void setnJameHazerNavi(int nJameHazerNavi) {
        this.nJameHazerNavi = nJameHazerNavi;
    }

    public int getnSobhaneNavi() {
        return nSobhaneNavi;
    }

    public void setnSobhaneNavi(int nSobhaneNavi) {
        this.nSobhaneNavi = nSobhaneNavi;
    }

    public int getnNaharNavi() {
        return nNaharNavi;
    }

    public void setnNaharNavi(int nNaharNavi) {
        this.nNaharNavi = nNaharNavi;
    }

    public int getnShamNavi() {
        return nShamNavi;
    }

    public void setnShamNavi(int nShamNavi) {
        this.nShamNavi = nShamNavi;
    }

    public int getnEdeKolDarajedar() {
        return nEdeKolDarajedar;
    }

    public void setnEdeKolDarajedar(int nEdeKolDarajedar) {
        this.nEdeKolDarajedar = nEdeKolDarajedar;
    }

    public int getnMorakhasiDarajedar() {
        return nMorakhasiDarajedar;
    }

    public void setnMorakhasiDarajedar(int nMorakhasiDarajedar) {
        this.nMorakhasiDarajedar = nMorakhasiDarajedar;
    }

    public int getnBastariDarajedar() {
        return nBastariDarajedar;
    }

    public void setnBastariDarajedar(int nBastariDarajedar) {
        this.nBastariDarajedar = nBastariDarajedar;
    }

    public int getnNahastDarajedar() {
        return nNahastDarajedar;
    }

    public void setnNahastDarajedar(int nNahastDarajedar) {
        this.nNahastDarajedar = nNahastDarajedar;
    }

    public int getnFararDarajedar() {
        return nFararDarajedar;
    }

    public void setnFararDarajedar(int nFararDarajedar) {
        this.nFararDarajedar = nFararDarajedar;
    }

    public int getnBazdashtDarajedar() {
        return nBazdashtDarajedar;
    }

    public void setnBazdashtDarajedar(int nBazdashtDarajedar) {
        this.nBazdashtDarajedar = nBazdashtDarajedar;
    }

    public int getnMontazerBekhedmatDarajedar() {
        return nMontazerBekhedmatDarajedar;
    }

    public void setnMontazerBekhedmatDarajedar(int nMontazerBekhedmatDarajedar) {
        this.nMontazerBekhedmatDarajedar = nMontazerBekhedmatDarajedar;
    }

    public int getnMamorAzEdeDarajedar() {
        return nMamorAzEdeDarajedar;
    }

    public void setnMamorAzEdeDarajedar(int nMamorAzEdeDarajedar) {
        this.nMamorAzEdeDarajedar = nMamorAzEdeDarajedar;
    }

    public int getnJameMotafaregheDarajedar() {
        return nJameMotafaregheDarajedar;
    }

    public void setnJameMotafaregheDarajedar(int nJameMotafaregheDarajedar) {
        this.nJameMotafaregheDarajedar = nJameMotafaregheDarajedar;
    }

    public int getnMamorBeEdeDarajedar() {
        return nMamorBeEdeDarajedar;
    }

    public void setnMamorBeEdeDarajedar(int nMamorBeEdeDarajedar) {
        this.nMamorBeEdeDarajedar = nMamorBeEdeDarajedar;
    }

    public int getnJameHazerDarajedar() {
        return nJameHazerDarajedar;
    }

    public void setnJameHazerDarajedar(int nJameHazerDarajedar) {
        this.nJameHazerDarajedar = nJameHazerDarajedar;
    }

    public int getnSobhaneDarajedar() {
        return nSobhaneDarajedar;
    }

    public void setnSobhaneDarajedar(int nSobhaneDarajedar) {
        this.nSobhaneDarajedar = nSobhaneDarajedar;
    }

    public int getnNaharDarajedar() {
        return nNaharDarajedar;
    }

    public void setnNaharDarajedar(int nNaharDarajedar) {
        this.nNaharDarajedar = nNaharDarajedar;
    }

    public int getnShamDarajedar() {
        return nShamDarajedar;
    }

    public void setnShamDarajedar(int nShamDarajedar) {
        this.nShamDarajedar = nShamDarajedar;
    }

    public String getSarparasteShobeAmar() {
        return sarparasteShobeAmar;
    }

    public void setSarparasteShobeAmar(String sarparasteShobeAmar) {
        this.sarparasteShobeAmar = sarparasteShobeAmar;
    }

    public String getFarmandeYegan() {
        return farmandeYegan;
    }

    public void setFarmandeYegan(String farmandeYegan) {
        this.farmandeYegan = farmandeYegan;
    }

    public String getSarparasteShobeAmarNiroEnsani() {
        return sarparasteShobeAmarNiroEnsani;
    }

    public void setSarparasteShobeAmarNiroEnsani(String sarparasteShobeAmarNiroEnsani) {
        this.sarparasteShobeAmarNiroEnsani = sarparasteShobeAmarNiroEnsani;
    }

    public int getnModavematKari() {
        return nModavematKari;
    }

    public void setnModavematKari(int nModavematKari) {
        this.nModavematKari = nModavematKari;
    }

    public int getnEzafekari() {
        return nEzafekari;
    }

    public void setnEzafekari(int nEzafekari) {
        this.nEzafekari = nEzafekari;
    }

    public String getMolahezat() {
        return molahezat;
    }

    public void setMolahezat(String molahezat) {
        this.molahezat = molahezat;
    }

    public int getnAzMorakhasiPayvar() {
        return nAzMorakhasiPayvar;
    }

    public void setnAzMorakhasiPayvar(int nAzMorakhasiPayvar) {
        this.nAzMorakhasiPayvar = nAzMorakhasiPayvar;
    }

    public int getnBeMorakhasiPayvar() {
        return nBeMorakhasiPayvar;
    }

    public void setnBeMorakhasiPayvar(int nBeMorakhasiPayvar) {
        this.nBeMorakhasiPayvar = nBeMorakhasiPayvar;
    }

    public int getnAzNahastPayvar() {
        return nAzNahastPayvar;
    }

    public void setnAzNahastPayvar(int nAzNahastPayvar) {
        this.nAzNahastPayvar = nAzNahastPayvar;
    }

    public int getnBeNahastPayvar() {
        return nBeNahastPayvar;
    }

    public void setnBeNahastPayvar(int nBeNahastPayvar) {
        this.nBeNahastPayvar = nBeNahastPayvar;
    }

    public int getnAzBastariPayvar() {
        return nAzBastariPayvar;
    }

    public void setnAzBastariPayvar(int nAzBastariPayvar) {
        this.nAzBastariPayvar = nAzBastariPayvar;
    }

    public int getnBeBastariPayvar() {
        return nBeBastariPayvar;
    }

    public void setnBeBastariPayvar(int nBeBastariPayvar) {
        this.nBeBastariPayvar = nBeBastariPayvar;
    }

    public int getnAzMamoriyatPayvar() {
        return nAzMamoriyatPayvar;
    }

    public void setnAzMamoriyatPayvar(int nAzMamoriyatPayvar) {
        this.nAzMamoriyatPayvar = nAzMamoriyatPayvar;
    }

    public int getnBeMamoriyatPayvar() {
        return nBeMamoriyatPayvar;
    }

    public void setnBeMamoriyatPayvar(int nBeMamoriyatPayvar) {
        this.nBeMamoriyatPayvar = nBeMamoriyatPayvar;
    }

    public int getnKasrAzAmarPayvar() {
        return nKasrAzAmarPayvar;
    }

    public void setnKasrAzAmarPayvar(int nKasrAzAmarPayvar) {
        this.nKasrAzAmarPayvar = nKasrAzAmarPayvar;
    }

    public int getnEzafBeAmarPayvar() {
        return nEzafBeAmarPayvar;
    }

    public void setnEzafBeAmarPayvar(int nEzafBeAmarPayvar) {
        this.nEzafBeAmarPayvar = nEzafBeAmarPayvar;
    }

    public int getnEzafBeMamorBeEdePayvar() {
        return nEzafBeMamorBeEdePayvar;
    }

    public void setnEzafBeMamorBeEdePayvar(int nEzafBeMamorBeEdePayvar) {
        this.nEzafBeMamorBeEdePayvar = nEzafBeMamorBeEdePayvar;
    }

    public int getnKasrAzMamorBeEdePayvar() {
        return nKasrAzMamorBeEdePayvar;
    }

    public void setnKasrAzMamorBeEdePayvar(int nKasrAzMamorBeEdePayvar) {
        this.nKasrAzMamorBeEdePayvar = nKasrAzMamorBeEdePayvar;
    }

    public int getnBeEntezarBeKhedmatPayvar() {
        return nBeEntezarBeKhedmatPayvar;
    }

    public void setnBeEntezarBeKhedmatPayvar(int nBeEntezarBeKhedmatPayvar) {
        this.nBeEntezarBeKhedmatPayvar = nBeEntezarBeKhedmatPayvar;
    }

    public int getnAzEntezarKhedmatPayvar() {
        return nAzEntezarKhedmatPayvar;
    }

    public void setnAzEntezarKhedmatPayvar(int nAzEntezarKhedmatPayvar) {
        this.nAzEntezarKhedmatPayvar = nAzEntezarKhedmatPayvar;
    }

    public int getnBeBazdashtPayvar() {
        return nBeBazdashtPayvar;
    }

    public void setnBeBazdashtPayvar(int nBeBazdashtPayvar) {
        this.nBeBazdashtPayvar = nBeBazdashtPayvar;
    }

    public int getnAzBazdashtPayvar() {
        return nAzBazdashtPayvar;
    }

    public void setnAzBazdashtPayvar(int nAzBazdashtPayvar) {
        this.nAzBazdashtPayvar = nAzBazdashtPayvar;
    }

    public int getnAzMorakhasiVazife() {
        return nAzMorakhasiVazife;
    }

    public void setnAzMorakhasiVazife(int nAzMorakhasiVazife) {
        this.nAzMorakhasiVazife = nAzMorakhasiVazife;
    }

    public int getnBeMorakhasiVazife() {
        return nBeMorakhasiVazife;
    }

    public void setnBeMorakhasiVazife(int nBeMorakhasiVazife) {
        this.nBeMorakhasiVazife = nBeMorakhasiVazife;
    }

    public int getnAzNahastVazife() {
        return nAzNahastVazife;
    }

    public void setnAzNahastVazife(int nAzNahastVazife) {
        this.nAzNahastVazife = nAzNahastVazife;
    }

    public int getnBeNahastVazife() {
        return nBeNahastVazife;
    }

    public void setnBeNahastVazife(int nBeNahastVazife) {
        this.nBeNahastVazife = nBeNahastVazife;
    }

    public int getnAzBastariVazife() {
        return nAzBastariVazife;
    }

    public void setnAzBastariVazife(int nAzBastariVazife) {
        this.nAzBastariVazife = nAzBastariVazife;
    }

    public int getnBeBastariVazife() {
        return nBeBastariVazife;
    }

    public void setnBeBastariVazife(int nBeBastariVazife) {
        this.nBeBastariVazife = nBeBastariVazife;
    }

    public int getnAzMamoriyatVazife() {
        return nAzMamoriyatVazife;
    }

    public void setnAzMamoriyatVazife(int nAzMamoriyatVazife) {
        this.nAzMamoriyatVazife = nAzMamoriyatVazife;
    }

    public int getnBeMamoriyatVazife() {
        return nBeMamoriyatVazife;
    }

    public void setnBeMamoriyatVazife(int nBeMamoriyatVazife) {
        this.nBeMamoriyatVazife = nBeMamoriyatVazife;
    }

    public int getnKasrAzAmarVazife() {
        return nKasrAzAmarVazife;
    }

    public void setnKasrAzAmarVazife(int nKasrAzAmarVazife) {
        this.nKasrAzAmarVazife = nKasrAzAmarVazife;
    }

    public int getnEzafBeAmarVazife() {
        return nEzafBeAmarVazife;
    }

    public void setnEzafBeAmarVazife(int nEzafBeAmarVazife) {
        this.nEzafBeAmarVazife = nEzafBeAmarVazife;
    }

    public int getnEzafBeMamorBeEdeVazife() {
        return nEzafBeMamorBeEdeVazife;
    }

    public void setnEzafBeMamorBeEdeVazife(int nEzafBeMamorBeEdeVazife) {
        this.nEzafBeMamorBeEdeVazife = nEzafBeMamorBeEdeVazife;
    }

    public int getnKasrAzMamorBeEdeVazife() {
        return nKasrAzMamorBeEdeVazife;
    }

    public void setnKasrAzMamorBeEdeVazife(int nKasrAzMamorBeEdeVazife) {
        this.nKasrAzMamorBeEdeVazife = nKasrAzMamorBeEdeVazife;
    }

    public int getnBeEntezarBeKhedmatVazife() {
        return nBeEntezarBeKhedmatVazife;
    }

    public void setnBeEntezarBeKhedmatVazife(int nBeEntezarBeKhedmatVazife) {
        this.nBeEntezarBeKhedmatVazife = nBeEntezarBeKhedmatVazife;
    }

    public int getnAzEntezarKhedmatVazife() {
        return nAzEntezarKhedmatVazife;
    }

    public void setnAzEntezarKhedmatVazife(int nAzEntezarKhedmatVazife) {
        this.nAzEntezarKhedmatVazife = nAzEntezarKhedmatVazife;
    }

    public int getnBeBazdashtVazife() {
        return nBeBazdashtVazife;
    }

    public void setnBeBazdashtVazife(int nBeBazdashtVazife) {
        this.nBeBazdashtVazife = nBeBazdashtVazife;
    }

    public int getnAzBazdashtVazife() {
        return nAzBazdashtVazife;
    }

    public void setnAzBazdashtVazife(int nAzBazdashtVazife) {
        this.nAzBazdashtVazife = nAzBazdashtVazife;
    }

    public Dayere getDayere() {
        return dayere;
    }

    public void setDayere(Dayere dayere) {
        this.dayere = dayere;
    }

    public Bakhsh getBakhsh() {
        return bakhsh;
    }

    public void setBakhsh(Bakhsh bakhsh) {
        this.bakhsh = bakhsh;
    }

    public List<Taghvim> getTaghvims() {
        return taghvims;
    }

    public void setTaghvims(List<Taghvim> taghvims) {
        this.taghvims = taghvims;
    }

    public Taghvim getTaghvim() {
        return taghvim;
    }

    public void setTaghvim(Taghvim taghvim) {
        this.taghvim = taghvim;
    }

    public boolean isShowErja() {
        return showErja;
    }

    public void setShowErja(boolean showErja) {
        this.showErja = showErja;
    }

    public boolean isShowCrud() {
        return showCrud;
    }

    public void setShowCrud(boolean showCrud) {
        this.showCrud = showCrud;
    }

    public String getTarikhSabtFilter() {
        return tarikhSabtFilter;
    }

    public void setTarikhSabtFilter(String tarikhSabtFilter) {
        this.tarikhSabtFilter = tarikhSabtFilter;
    }

    public Long getDayereIdFilter() {
        return dayereIdFilter;
    }

    public void setDayereIdFilter(Long dayereIdFilter) {
        this.dayereIdFilter = dayereIdFilter;
    }

    public Long getBakhshIdFilter() {
        return bakhshIdFilter;
    }

    public void setBakhshIdFilter(Long bakhshIdFilter) {
        this.bakhshIdFilter = bakhshIdFilter;
    }

    public List<Dayere> getDayereList() {
        return dayereList;
    }

    public void setDayereList(List<Dayere> dayereList) {
        this.dayereList = dayereList;
    }

    public List<Bakhsh> getBakhshList() {
        return bakhshList;
    }

    public void setBakhshList(List<Bakhsh> bakhshList) {
        this.bakhshList = bakhshList;
    }

    public boolean isDisableDayere() {
        return disableDayere;
    }

    public void setDisableDayere(boolean disableDayere) {
        this.disableDayere = disableDayere;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getDateReport() {
        return dateReport;
    }

    public void setDateReport(String dateReport) {
        this.dateReport = dateReport;
    }

    public Long getDayereReport() {
        return dayereReport;
    }

    public void setDayereReport(Long dayereReport) {
        this.dayereReport = dayereReport;
    }

    public Long getBakhshReport() {
        return bakhshReport;
    }

    public void setBakhshReport(Long bakhshReport) {
        this.bakhshReport = bakhshReport;
    }

    public int getnEdeKolJame() {
        return nEdeKolJame;
    }

    public void setnEdeKolJame(int nEdeKolJame) {
        this.nEdeKolJame = nEdeKolJame;
    }

    public int getnMorakhasiJame() {
        return nMorakhasiJame;
    }

    public void setnMorakhasiJame(int nMorakhasiJame) {
        this.nMorakhasiJame = nMorakhasiJame;
    }

    public int getnBastariJame() {
        return nBastariJame;
    }

    public void setnBastariJame(int nBastariJame) {
        this.nBastariJame = nBastariJame;
    }

    public int getnNahastJame() {
        return nNahastJame;
    }

    public void setnNahastJame(int nNahastJame) {
        this.nNahastJame = nNahastJame;
    }

    public int getnFararJame() {
        return nFararJame;
    }

    public void setnFararJame(int nFararJame) {
        this.nFararJame = nFararJame;
    }

    public int getnBazdashtJame() {
        return nBazdashtJame;
    }

    public void setnBazdashtJame(int nBazdashtJame) {
        this.nBazdashtJame = nBazdashtJame;
    }

    public int getnMontazerBekhedmatJame() {
        return nMontazerBekhedmatJame;
    }

    public void setnMontazerBekhedmatJame(int nMontazerBekhedmatJame) {
        this.nMontazerBekhedmatJame = nMontazerBekhedmatJame;
    }

    public int getnMamorAzEdeJame() {
        return nMamorAzEdeJame;
    }

    public void setnMamorAzEdeJame(int nMamorAzEdeJame) {
        this.nMamorAzEdeJame = nMamorAzEdeJame;
    }

    public int getnJameMotafaregheJame() {
        return nJameMotafaregheJame;
    }

    public void setnJameMotafaregheJame(int nJameMotafaregheJame) {
        this.nJameMotafaregheJame = nJameMotafaregheJame;
    }

    public int getnMamorBeEdeJame() {
        return nMamorBeEdeJame;
    }

    public void setnMamorBeEdeJame(int nMamorBeEdeJame) {
        this.nMamorBeEdeJame = nMamorBeEdeJame;
    }

    public int getnJameHazerJame() {
        return nJameHazerJame;
    }

    public void setnJameHazerJame(int nJameHazerJame) {
        this.nJameHazerJame = nJameHazerJame;
    }

    public int getnSobhaneJame() {
        return nSobhaneJame;
    }

    public void setnSobhaneJame(int nSobhaneJame) {
        this.nSobhaneJame = nSobhaneJame;
    }

    public int getnNaharJame() {
        return nNaharJame;
    }

    public void setnNaharJame(int nNaharJame) {
        this.nNaharJame = nNaharJame;
    }

    public int getnShamJame() {
        return nShamJame;
    }

    public void setnShamJame(int nShamJame) {
        this.nShamJame = nShamJame;
    }

    public int getTedadJireNahar() {
        return tedadJireNahar;
    }

    public void setTedadJireNahar(int tedadJireNahar) {
        this.tedadJireNahar = tedadJireNahar;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
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

    public String getMolahezatNew() {
        return molahezatNew;
    }

    public void setMolahezatNew(String molahezatNew) {
        this.molahezatNew = molahezatNew;
    }

    public List<Personel> getPersonelList() {
        return personelList;
    }

    public void setPersonelList(List<Personel> personelList) {
        this.personelList = personelList;
    }

    public List<Personel> getSelectedPersonelList() {
        return selectedPersonelList;
    }

    public void setSelectedPersonelList(List<Personel> selectedPersonelList) {
        this.selectedPersonelList = selectedPersonelList;
    }

    public List<NoeEstekhdam> getNoeEstekhdamList() {
        return noeEstekhdamList;
    }

    public void setNoeEstekhdamList(List<NoeEstekhdam> noeEstekhdamList) {
        this.noeEstekhdamList = noeEstekhdamList;
    }

    public List<Jire> getJireList() {
        return jireList;
    }

    public void setJireList(List<Jire> jireList) {
        this.jireList = jireList;
    }
}
