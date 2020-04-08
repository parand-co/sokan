import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class Main {

    public static void main(String[] args) {
//        SavabeghTaradod savabeghTaradod = new SavabeghTaradod();
//        if (savabeghTaradod.getSaat1()==null)
//            System.out.println("ok");
//        Session session = HibernateUtil.getSession();
//        List<Bakhsh>bakhshList= session.createQuery("from Bakhsh where dayere.id=1").list();
//        System.out.println(PersianCalUtil.getCurrentDate().substring(0,4));
//        System.out.println(JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(2020,1,22)));
//        String s = "910";
//        System.out.println(s.substring(1,2));

        String date="1398/01/02";
        System.out.println(date.substring(0,4));
        System.out.println(date.substring(5,7));
        System.out.println(date.substring(8,10));



    }
}
