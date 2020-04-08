package dataBaseModel.util;

import dataBaseModel.baseTable.DayType;
import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.dao.TaghvimDao;
import org.hibernate.Session;

import java.sql.SQLException;

public class TaghvimCreatorUtil {

    public static void main(String[] args) throws SQLException {

//        Session session = HibernateUtil.getSession();
//        System.out.println(session);

        String[] roozHafte = {"شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه"};

        boolean kabiseFlag = false;
        String date = "13940101";  // start date
        int code = (int) new PwKaraDbUtil().miladi2Pwkara(new PwKaraDbUtil().shamsi2Miladi(date));            // code rooz marboote
        int length = 3000;    // tedad baraye ezaf shodan

        for (int i=code; i <= code+length ; i++){
            Taghvim taghvim = new Taghvim();
            taghvim.setCode(i);
            taghvim.setRoozHafte(roozHafte[i%7]);

            if (i%7 == 2 || i%7 == 5) {
                taghvim.setDayType(new DayType(2));
                taghvim.setDayTypeG1(new DayType(2));
                taghvim.setDayTypeG2(new DayType(2));
                taghvim.setDayTypeG3(new DayType(2));
                taghvim.setDayTypeG4(new DayType(2));
                taghvim.setDayTypeG5(new DayType(2));
                taghvim.setDayTypeG6(new DayType(2));
                taghvim.setDayTypeG7(new DayType(2));
            }
            else if (i%7 == 6) {
                taghvim.setDayType(new DayType(3));
                taghvim.setDayTypeG1(new DayType(3));
                taghvim.setDayTypeG2(new DayType(3));
                taghvim.setDayTypeG3(new DayType(3));
                taghvim.setDayTypeG4(new DayType(3));
                taghvim.setDayTypeG5(new DayType(3));
                taghvim.setDayTypeG6(new DayType(3));
                taghvim.setDayTypeG7(new DayType(3));
            }
            else {
                taghvim.setDayType(new DayType(1));
                taghvim.setDayTypeG1(new DayType(1));
                taghvim.setDayTypeG2(new DayType(1));
                taghvim.setDayTypeG3(new DayType(1));
                taghvim.setDayTypeG4(new DayType(1));
                taghvim.setDayTypeG5(new DayType(1));
                taghvim.setDayTypeG6(new DayType(1));
                taghvim.setDayTypeG7(new DayType(1));
            }

            if (date.contains("/12/29") && Integer.parseInt(date.split("/")[0])%4==3 && !kabiseFlag){
//                taghvim.setTarihk(date.split("/")[0]+"/"+"12/"+"30");
                taghvim.setTarikh(date);
                kabiseFlag = true;

            }else if(date.contains("/12/29") && Integer.parseInt(date.split("/")[0])%4==3 && kabiseFlag){
                taghvim.setTarikh(date.split("/")[0]+"/"+"12/"+"30");
                kabiseFlag = false;
            }

            else{
                taghvim.setTarikh(date);
            }

            if (!kabiseFlag){
                date = PersianCalUtil.add(date,1);
            }
            System.out.println(taghvim.getTarikh() +"  ---  "+ taghvim.getCode());
            TaghvimDao.getInstance().create(taghvim);
        }


    }
}
