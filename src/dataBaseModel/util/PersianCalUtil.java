package dataBaseModel.util;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2/5/14
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersianCalUtil {

//    private static PersianCalendar persianCalendar = new PersianCalendar(TimeZone.getTimeZone("Asia/Tehran"));
//    //set US to show and save characters prepare ( IR=????/??/?? in DB)
//    private static DateFormat shortDateFormat = persianCalendar.getDateTimeFormat(DateFormat.SHORT, DateFormat.SHORT, new Locale("fa", "IR", ""));
//    private static DateFormat fullDateFormat = persianCalendar.getDateTimeFormat(DateFormat.FULL, DateFormat.DEFAULT, new ULocale("en", "US", ""));

    private static String currentTime;
    private static String currentDate;
    private static String currentDateTime;

    public static String getCurrentTime() {
//        currentTime = shortDateFormat.format(new Date()).substring(11).trim();
        return currentTime;
    }

    public static String addDate() {
        return currentDate;
    }

    public static String getCurrentDate() {
//        currentDate = shortDateFormat.format(new Date()).substring(0, 10).trim();
//        return currentDate;
        ULocale uLocale=new ULocale("en","IR");
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd",uLocale);
        return getCorrectDate(simpleDateFormat.format(calendar));
    }

    public static String getCurrentDateTime() {
//        currentDateTime = fullDateFormat.format(new Date());
        return currentDateTime;
    }

    public static int getDiffDays(String from,String to){

        String[] fromdate = from.split("/");
        int yearF = Integer.parseInt(fromdate[0]);
        int mounthF = Integer.parseInt(fromdate[1]);
        int dayF = Integer.parseInt(fromdate[2]);

        String[] toDate = to.split("/");
        int yearT = Integer.parseInt(toDate[0]);
        int mounthT = Integer.parseInt(toDate[1]);
        int dayT = Integer.parseInt(toDate[2]);

        int diff = (yearT-yearF)*365 + (mounthT-mounthF)*30 + (dayT-dayF);
        return diff;
    }

    public static String getMounts(String from,String to){

        List<String> mounts = new ArrayList<String>();
        String[] fromdate = from.split("/");
        int yearF = Integer.parseInt(fromdate[0]);
        int mounthF = Integer.parseInt(fromdate[1]);
        int dayF = Integer.parseInt(fromdate[2]);

        String[] toDate = to.split("/");
        int yearT = Integer.parseInt(toDate[0]);
        int mounthT = Integer.parseInt(toDate[1]);
        int dayT = Integer.parseInt(toDate[2]);

//        mounts.add(getMountName(mounthF));
//        if(mounts.contains(getMountName(mounthT))){
//        }
//        else {
//            mounts.add(getMountName(mounthT));
//        }
//        String res="";
//        for(int i =0 ;i<mounts.size();i++){
//
//            res = res.concat(mounts.get(i));
//            if(i == mounts.size()-1){}
//            else {
//                res = res.concat(",");
//            }
//        }
        String res = "";
        int diff = getDiffDays(from,to);
        int mountCount = (diff+5) / 30;
        if (mountCount >= 1){
            mounts.add(getMountName(mounthF));
            res = res.concat(getMountName(mounthF)+",");
            for (int i=mounthF+1 ; i<mounthF+mountCount ; i++){
                if(i == mounthF+mountCount-1)
                    res = res.concat(getMountName(i));
                else
                res = res.concat(getMountName(i)+",");
            }
        }


        return res;

    }

    public static String getMountName(int a){
        switch (a){
            case 1:
                return "فروردين";
            case 2:
                return "ارديبهشت";
            case 3:
                return "خرداد";
            case 4:
                return "تير";
            case 5:
                return "مرداد";
            case 6:
                return "شهريور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آذر";
            case 10:
                return "دي";
            case 11:
                return "بهمن";
            case 12:
                return "اسفند";
        }
        return "";
    }

    public static String add(String oldDate, int addDay) {
        String date[] = oldDate.split("/");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        day += addDay;
        if(addDay==-1 && day==0)
            if(month<=6) {
                day = 30;
                month--;
            }
            else if(month > 6 && month<12){
                day=30;
                month--;
            }
            else {
                day = 29;
                month--;
            }


        if (month <= 6) {
            if (day > 31) {
                day -= 31;
                month++;
            }
        } else if (month > 6 && month < 12) {
            if (day > 30) {
                day -= 30;
                month++;
            }
        } else if (month == 12) {
            if (day > 29) {
                day -= 29;
                month = 1;
                year++;
            }
        }

        String monthStr = "";


        return PersianCalUtil.getCorrectDate(year + "/" + month + "/" + day);

    }

    public static String getCorrectDate(String oldDate) {
        String date[] = oldDate.split("/");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        String monthStr = "" + month;
        String dayStr = "" + day;

        if (monthStr.length() == 1)
            monthStr = "0" + month;

        if (dayStr.length() == 1)
            dayStr = "0" + day;

        return year + "/" + monthStr + "/" + dayStr;
    }

    public  static String getWithoutSlashDate (String date){
        String dates[] = date.split("/");
        String year = dates[0];
        String month = dates[1];
        String day = dates[2];

       String ret = year.concat(month.concat(day));
       return ret;
    }

    public static String getYesterday(String date){
        int[] kabiseArray = {1399,1404,1408,1412};
        String dates[] = date.split("/");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);
        if (day>1)
            return getCorrectDate(year+"/"+month+"/"+(day-1));
        else{
            if (month > 1){
                if (month <= 7)
                    day = 31;
                else
                    day = 30;
                return getCorrectDate(year+"/"+(month - 1)+"/"+day);
            }else {
                if (IntStream.of(kabiseArray).anyMatch(x -> x == (year-1)))
                    return getCorrectDate((year - 1) + "/" + "12" + "/" + "30");
                else
                    return getCorrectDate((year - 1) + "/" + "12" + "/" + "29");
            }
        }
    }

    public static void subDays2Date(String date,int count){
//        int[] kabiseArray = {1399,1404,1408,1412};
//        String dates[] = date.split("/");
//        int year = Integer.parseInt(dates[0]);
//        int month = Integer.parseInt(dates[1]);
//        int day = Integer.parseInt(dates[2]);
//        if (day>count)
//            return getCorrectDate(year+"/"+month+"/"+(day-1));
//        else {
//            if
//        }
    }

    public static int calcDaysFromStart2Date(String date){
        int[] kabiseArray = {1399,1404,1408,1412};
        String dates[] = date.split("/");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);
        int sum = 0;
        for (int i = 1; i < month; i++) {
            if (i <= 6)
                sum += 31;
            else
                sum += 30;
        }
        sum += day;

        return sum;
    }


    public static String getCurrentYear(){
        return getCurrentDate().substring(0,4);
    }
    public static String getYear(String date){
        return date.substring(0,4);
    }
    public static String getMount(String date){
        return date.substring(5,7);
    }
    public static String getDay(String date){
        return date.substring(8,10);
    }



}
