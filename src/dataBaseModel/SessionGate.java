package dataBaseModel;

import amar.model.Personel;
import amar.model.SavabeghJabejaeeGorohKari;
import amar.model.SavabeghTaradod;
import amar.model.Taraddod;
import dataBaseModel.baseTable.*;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.PersianCalUtil;
import ezafekari.model.Emza;
import ezafekari.model.EzafatoKosoorat;
import ezafekari.model.EzafeKari;
import ezafekari.model.Sanad;
import omorkoliAndgharardadi.model.SavabeghEsterahatPezeshki;
import omorkoliAndgharardadi.model.SavabeghMamoriyatRozane;
import omorkoliAndgharardadi.model.SavabeghMorakhasi;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.test.cache.infinispan.stress.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class SessionGate {

    public Taghvim findTaghvimByTarikh(String tarikh) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taghvim where tarikh=:dd");
            query.setString("dd", tarikh);
            return (Taghvim) query.list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Personel findPersonelByCartNumber(String number) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where shomareKart=:dd");
            query.setString("dd", number);
            return (Personel) query.list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


    public List<SavabeghJabejaeeGorohKari> findJabejayeeGoroohKariByPersonelAndDate(String date, Personel personel) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from SavabeghJabejaeeGorohKari where personel=:pers and tarikhShoro < :date1 and tarikhPayan < :date2");
            query.setEntity("pers", personel);
            query.setString("date1", date);
            query.setString("date2", date);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Taraddod> findTaraddodByCardNumAndDate(String cardNumber, String date) {

        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taraddod where personel.shomareKart=:p1 and taghvim.tarikh=:p2");
            query.setEntity("p1", cardNumber);
            query.setString("p2", date);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Personel> findPersonelByActive(boolean active) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where active= :a ");
            query.setBoolean("a", active);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Taraddod> findTaraddodByPersonelAndDate(Personel personel, String date) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taraddod where personel=:p and taghvim.tarikh=:d order by saat asc ");
            query.setEntity("p", personel);
            query.setString("d", date);
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public SavabeghMorakhasi findMorakhasiByPersonelAndDate(Personel personel, String date) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from SavabeghMorakhasi where personel=:pers and tarikhShoro < :date1 and tarikhPayan < :date2");
            query.setEntity("pers", personel);
            query.setString("date1", date);
            query.setString("date2", date);
            if (query.list().size() > 0)
                return (SavabeghMorakhasi) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public SavabeghEsterahatPezeshki findEsterahatPezeshkiByPersonelAndDate(Personel personel, String date) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from SavabeghEsterahatPezeshki where personel=:pers and tarikhShoro < :date1 and tarikhPayan < :date2");
            query.setEntity("pers", personel);
            query.setString("date1", date);
            query.setString("date2", date);
            if (query.list().size() > 0)
                return (SavabeghEsterahatPezeshki) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public SavabeghMamoriyatRozane findMamoriatByPersonelAndDate(Personel personel, String date) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from SavabeghMamoriyatRozane where personel=:pers and tarikhShoro < :date1 and tarikhPayan < :date2");
            query.setEntity("pers", personel);
            query.setString("date1", date);
            query.setString("date2", date);
            if (query.list().size() > 0)
                return (SavabeghMamoriyatRozane) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public boolean checkExistTaradodBySaatAndDateAndPersonel(int time, String date, int cardNum) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taraddod where personel.shomareKart= :num and taghvim.tarikh= :date and saat = :t");
            query.setInteger("t", time);
            query.setString("date", date);
            query.setInteger("num", cardNum);
            if (query.list().size() > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public List<Personel> checkTarikhEnqezaForInactvie() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where tarikhEngheza != '-' and tarikhEngheza < :num and active=true");
            query.setString("num", PersianCalUtil.getCurrentDate());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public boolean checkExistPersonel(int shomareKart) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where shomareKart = :sh");
            query.setString("sh", String.valueOf(shomareKart));
            if (query.list().size() > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public List<Personel> findPersonelByBakhshId(long selectedBakhsh) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where bakhsh.id = :sh");
            query.setLong("sh", selectedBakhsh);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Bakhsh> findBakhshByDayereID(long selectedDayere) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Bakhsh where dayere.id= :id");
            query.setLong("id", selectedDayere);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Personel> findPersonelByDayereID(long selectedDayere) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where dayere.id = :id");
            query.setLong("id", selectedDayere);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Personel> findPersonelContainsCardNumber(String shomareKartSearched) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where shomareKart like concat('%',:sh,'%')");
            query.setString("sh", shomareKartSearched);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Personel> findPersonelContainsPersNum(String shomarePersoneliSearched) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where shomarePersoneli like concat('%',:sh,'%')");
            query.setString("sh", shomarePersoneliSearched);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();

    }

    public List<Personel> findPersonelByPersNum(String shomarePersoneliSearched2) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where shomarePersoneli = :sh");
            query.setString("sh", shomarePersoneliSearched2);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Personel> findPersonelByPersCodeMeli(String codeMeli) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where codeMeli = :cm");
            query.setString("cm", codeMeli);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Personel> findPersonelContainsNameAndNeshan(String neshanSearched) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where concat(name,neshan) like concat('%',:sh,'%')");
            query.setString("sh", neshanSearched);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Taraddod> findTaraddodByPersonelAndFromDateAndToDate(Personel personel, String fromDate, String toDate) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taraddod where personel = :pers and taghvim.tarikh >= :fromdate and taghvim.tarikh <= :todate order by saat asc");
            query.setEntity("pers", personel);
            query.setString("fromdate", fromDate);
            query.setString("todate", toDate);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();

    }

    public EzafeKari findOvertimeByPersonelAndDate(Personel personel, String date) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from EzafeKari where personel.id = :p and tarikh.tarikh = :t");
            query.setLong("p", personel.getId());
            query.setString("t", date);
            if (query.list().size() > 0)
                return (EzafeKari) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Sanad findSanadByTarikh(String date) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Sanad where sal = :sal and mah = :mah");
            query.setString("sal", PersianCalUtil.getYear(date));
            query.setString("mah", PersianCalUtil.getMount(date));
            if (query.list().size() > 0)
                return (Sanad) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Sanad> fillSanads() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Sanad");
            if (query.list().size() > 0)
                return query.list();
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public EzafatoKosoorat checkEzafatKosoratBySanadAndPersonel(long sanadId, Personel personel) {

        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from EzafatoKosoorat where sanad.id = :sid and personel.id = :id");
            query.setLong("sid", sanadId);
            query.setLong("id", personel.getId());
            if (query.list().size() > 0)
                return (EzafatoKosoorat) query.list().get(0);
            else
                return new EzafatoKosoorat();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new EzafatoKosoorat();
    }

    public List<Personel> fillPersonels() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel");
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Daraje> fillDaraje() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Daraje ");
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Madrak> fillMadrak() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Madrak ");
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Yegan> fillYegan() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Yegan ");
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }
    public List<Paygah> fillPaygah() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Paygah ");
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public Madrak findMadrakById(long id) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Madrak where id=:i");
            query.setLong("i", id);
            if (query.list().size() > 0)
                return (Madrak) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<NoeEstekhdam> fillNoeEstekhdam() {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from NoeEstekhdam ");
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Personel> findPersonelByNoeEstekhdam(String[] selectedNoeEstekhdams) {
        if (selectedNoeEstekhdams.length == 0)
            return fillPersonels();

        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Personel where noeEstekhdam.title in :se");
            query.setParameterList("se", selectedNoeEstekhdams);
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();

    }

    public Sanad findSanadById(long sanadId) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Sanad where id=:i");
            query.setLong("i", sanadId);
            if (query.list().size() > 0)
                return (Sanad) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Taghvim> findTaghvimByTarikhBetweenFromAndTo(String from , String to) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taghvim where tarikh >= :ff and tarikh <= :tt ");
            query.setString("ff",from);
            query.setString("tt",to);
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Taraddod> findTaraddodByTaghvim(Taghvim taghvim) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Taraddod where taghvim.id=:id");
            query.setLong("id",taghvim.getId());
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<EzafeKari> findEzafekariByTarikhBetweenFromAndTo(String fromDate, String toDate) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from EzafeKari where tarikh.tarikh >= :ff and tarikh.tarikh <= :tt");
            query.setString("ff",fromDate);
            query.setString("tt",toDate);
            if (query.list().size() > 0)
                return query.list();
            else
                return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public EzafatoKosoorat findEzafeKasrBySanadAndPers(Personel personel, long sanadId) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from EzafatoKosoorat where personel=:pers and sanad.id = :sid");
            query.setEntity("pers", personel);
            query.setLong("sid", sanadId);
            if (query.list().size() > 0)
                return (EzafatoKosoorat) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public String findCountJireByPersonelAndMahAndSal(Personel personel, String mah, String sal) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("select count(*)  from Jire where personel=:pers and mah= :mah and sal= :sal");
            query.setEntity("pers", personel);
            query.setString("sal",sal);
            query.setString("mah",mah);
            return String.valueOf(query.uniqueResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Emza findEmzaBySematTitle(String s) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Emza where semat.title=:tt ");
            query.setEntity("tt",s);
            if (query.list().size() > 0)
                return (Emza) query.list().get(0);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
