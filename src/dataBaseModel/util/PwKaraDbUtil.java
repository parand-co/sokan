package dataBaseModel.util;

import amar.model.Personel;
import amar.model.SavabeghJabejaeeGorohKari;
import amar.model.SavabeghTaradod;
import amar.model.Taraddod;
import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.*;
import dataBaseModel.dao.LogHistoryDao;
import dataBaseModel.dao.SavabeghTaradodDao;
import dataBaseModel.dao.TaraddodDao;
import ezafekari.EzafeUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PwKaraDbUtil {

    private SessionGate sessionGate = new SessionGate();
    private String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
            "databaseName=pwkara;user=sokan;password=P@ssword_1234";
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement statement = null;

    public void loadTaradodByCode(String code, String date) throws SQLException {

//            code = PersianCalUtil.getWithoutSlashDate(date);
//            code = shamsi2Miladi(code);
            code = String.valueOf(miladi2Pwkara(shamsi2Miladi(PersianCalUtil.getWithoutSlashDate(date))));
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            // Create and execute an SQL statement that returns some data.
            statement = con.prepareStatement("SELECT * from DataFile where DATE_='" + code + "'");
            rs = statement.executeQuery();

            List<SavabeghTaradod> taradodList = new ArrayList<>();
            while (rs.next()) {

                int a = rs.getInt("EMP_NO");
                if (sessionGate.checkExistPersonel(a)) {
                    if (!sessionGate.checkExistTaradodBySaatAndDateAndPersonel(rs.getInt("TIME_"), date, a)) {
                        Taraddod taraddod = new Taraddod();
                        taraddod.setPersonel(sessionGate.findPersonelByCartNumber(String.valueOf(a)));
                        taraddod.setGoroh(checkSavabeghJabejayeeGorooh(date, taraddod.getPersonel()));
                        taraddod.setShomareDastgah(rs.getInt("Clock_No"));
                        taraddod.setSaat(rs.getInt("TIME_"));
                        taraddod.setTaghvim(sessionGate.findTaghvimByTarikh(date));
                        checkDayTypeForSaveSaat(taraddod, date, rs.getInt("TIME_"));
                        TaraddodDao.getInstance().getBaseQuery().create(taraddod);
                        System.out.println(taraddod.getId());
                    }
                }
            }
            checkPersonelIsHavntTaradod(date);

        } catch (Exception e) {

            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "0400", "خطا در برقراری ارتباط"));
        }
        finally {
            con.close();
        }
    }

    public float miladi2Pwkara(String miladi) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            CallableStatement callableStatement = con.prepareCall("{? = call Miladi2Pwkara(?)}");
            callableStatement.registerOutParameter(1,Types.FLOAT);
            callableStatement.setString(2,miladi);
            callableStatement.execute();
            return callableStatement.getFloat(1);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "0400", "خطا در برقراری ارتباط"));
        }
        finally {
            con.close();
        }
        return 0;
    }

    public String miladi2Shamsi(String miladi) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            CallableStatement callableStatement = con.prepareCall("{? = call Miladi2Shamsi(?)}");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            callableStatement.setString(2,miladi);
            callableStatement.execute();
            return callableStatement.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "0400", "خطا در برقراری ارتباط"));
        }
        finally {
            con.close();
        }
        return "";
    }

    public String shamsi2Miladi(String shamsi) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            CallableStatement callableStatement = con.prepareCall("{? = call Shamsi2Miladi(?)}");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            callableStatement.setString(2,shamsi);
            callableStatement.execute();
            return callableStatement.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "0400", "خطا در برقراری ارتباط"));
        }
        finally {
            con.close();
        }
        return "";
    }

    public String pwKara2Miladi(float pw) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            CallableStatement callableStatement = con.prepareCall("{? = call PwKara2Miladi(?)}");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            callableStatement.setFloat(2,pw);
            callableStatement.execute();
            callableStatement.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "0400", "خطا در برقراری ارتباط"));
        }
        finally {
            con.close();
        }
        return "";
    }


    private void checkPersonelIsHavntTaradod(String date) {
        List<Personel> personels = sessionGate.findPersonelByActive(true);
        for (Personel personel : personels) {
            if (sessionGate.findTaraddodByPersonelAndDate(personel, date).size() == 0) {
                Taraddod taradod = new Taraddod();
                taradod.setPersonel(personel);
                taradod.setGoroh(checkSavabeghJabejayeeGorooh(date, personel));
                taradod.setVaziat(checkVaziatTaradod(date,personel));
                taradod.setTaghvim(sessionGate.findTaghvimByTarikh(date));
                TaraddodDao.getInstance().getBaseQuery().create(taradod);
                System.out.println(taradod.getId());
            }
        }
    }


    private void checkDayTypeForSaveSaat(Taraddod taradod, String date, int time) {

        Taghvim taghvim = sessionGate.findTaghvimByTarikh(date);
        long gorohID = taradod.getPersonel().getGorohKari().getId();
        DayType dayType = EzafeUtil.findDayType(gorohID,taghvim);

        switch (dayType.getType()) {
            case "عادی": {
                if (time <= Integer.parseInt(taradod.getPersonel().getGorohKari().getSaatShoroKarAddi()))
                    taradod.setVaziat(new MojavezRozane(1)); //hazer
                else
                    taradod.setVaziat(new MojavezRozane(8));  //takhir
                break;
            }
            case "نیمه تعطیل": {
                if (time <= Integer.parseInt(taradod.getPersonel().getGorohKari().getSaatShoroKarNimTatil()))
                    taradod.setVaziat(new MojavezRozane(1)); //hazer
                else
                    taradod.setVaziat(new MojavezRozane(8));  //takhir
                break;
            }
            case "تعطیل": {
                if (time <= Integer.parseInt(taradod.getPersonel().getGorohKari().getSaatShoroKarTatil()))
                    taradod.setVaziat(new MojavezRozane(1)); //hazer
                else
                    taradod.setVaziat(new MojavezRozane(8));  //takhir
                break;
            }
        }
    }




    private Taraddod checkTaraddodIsExistByPersAndDate(String cardNumber, String date) {
        List<Taraddod> taradodList = new ArrayList<>();
        taradodList = sessionGate.findTaraddodByCardNumAndDate(cardNumber, date);
        if (taradodList.size() > 0)
            return taradodList.get(0);
        else
            return null;
    }

    private MojavezRozane checkVaziatTaradod(String date, Personel personel) {

        if (sessionGate.findMorakhasiByPersonelAndDate(personel,date) != null)
            return new MojavezRozane(4);
        else if (sessionGate.findEsterahatPezeshkiByPersonelAndDate(personel,date) != null)
            return new MojavezRozane(7);
        else if (sessionGate.findMamoriatByPersonelAndDate(personel,date) != null)
            return new MojavezRozane(6);
        else
            return new MojavezRozane(2); //Nahast
    }

    private Goroh checkSavabeghJabejayeeGorooh(String date, Personel personel) {

        List<SavabeghJabejaeeGorohKari> jabejaeeGorohKaris = new ArrayList<>();
        jabejaeeGorohKaris = sessionGate.findJabejayeeGoroohKariByPersonelAndDate(date, personel);
        if (jabejaeeGorohKaris.size() > 0)
            return jabejaeeGorohKaris.get(0).getGorohJadid();
        else
            return personel.getGorohKari();
    }


}
