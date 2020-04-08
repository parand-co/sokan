package dataBaseModel.manager;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.dao.LogHistoryDao;
import dataBaseModel.util.HibernateUtil;
import dataBaseModel.util.SessionUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class LogController {
    private static String ACTION_CREATE = "ایجاد";
    private static String ACTION_UPDATE = "ویرایش";
    private static String ACTION_PASSWORD = "تغییر پسورد";
    private static String ACTION_DELETE = "حذف";
    private static String ACTION_NAVIGATION = "پیمایش";
    private static String ACTION_LOGIN = "ورود به سامانه";
    private static String ACTION_LOGOUT = "خروج از سامانه";
    private static String ACTION_LOGFAIL = "ورود ناموفق";

    private LogHistory object;

    public LogController() {
        object = new LogHistory();

        //Specify which User is Logged In;
        SessionUtil util=new SessionUtil();
        User user = util.getPermission().getUser();
        //Specify Date and Time
        ULocale locale = new ULocale("en", "IR");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss", locale);
        Calendar calendar = Calendar.getInstance();
        String timestamp = simpleDateFormat.format(calendar.getTime());
        String[] strings = timestamp.split(";");
        String date = strings[0];
        String time = strings[1];

        object.setUser(user);
        object.setDate(date);
        object.setTime(time);

    }

    public LogController(String form_url) {

        object = new LogHistory();

        //Specify which User is Logged In;
        SessionUtil util = new SessionUtil();
        User user = null;
        if(util.getPermission() != null) {
            user = util.getPermission().getUser();
        }
        //Specify Date and Time
        ULocale locale = new ULocale("en", "IR");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss", locale);
        Calendar calendar = Calendar.getInstance();
        String timestamp = simpleDateFormat.format(calendar.getTime());
        String[] strings = timestamp.split(";");
        String date = strings[0];
        String time = strings[1];

        object.setUser(user);
        object.setDate(date);
        object.setTime(time);

        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Form WHERE path = ?");
        query.setString(0, form_url);

        List list = query.list();
        if (list.size() == 1) {
            Form form = (Form) list.get(0);
            object.setForm(form);
        }

        session.close();

    }

    public void logCreate() {
        object.setAction(LogController.ACTION_CREATE);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logUpdate() {
        object.setAction(LogController.ACTION_UPDATE);
        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logChangePassword() {
        object.setAction(LogController.ACTION_PASSWORD);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void log(String action,String old_data,String new_data) {
        object.setAction(action);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logDelete() {
        object.setAction(LogController.ACTION_DELETE);


        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logNavigation() {
        object.setAction(LogController.ACTION_NAVIGATION);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logLogin() {
        object.setAction(LogController.ACTION_LOGIN);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logLogin(String code) {
        object.setAction(LogController.ACTION_LOGIN);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logLogout() {
        object.setAction(LogController.ACTION_LOGOUT);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logFail() {
        object.setAction(LogController.ACTION_LOGFAIL);

        try {
            LogHistoryDao.getInstance().getBaseQuery().create(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
