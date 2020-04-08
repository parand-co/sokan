package util;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Tarikh {

    private ULocale en_locale = new ULocale("en", "IR");
    private ULocale ir_locale = new ULocale("fa", "IR");

    public String porKardaneMainDate(){

        String tarikh = new SimpleDateFormat("yyyy/MM/dd", ir_locale).format(Calendar.getInstance());
        String[] rms = tarikh.split("/");
        return getDayOfWeek() + " " + rms[2] + " " + getMounth(Integer.parseInt(rms[1])) + " " + rms[0];
    }

    public String getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        String dayOfWeek = "";

        switch (day){
            case Calendar.SATURDAY:
                dayOfWeek = "شنبه";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "یکشنبه";
                break;
            case Calendar.MONDAY:
                dayOfWeek = "دوشنبه";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "سه شنبه";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "چهارشنبه";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "پنجشنبه";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "جمعه";
                break;
        }
        return dayOfWeek;
    }

    private String getMounth(int mah){
        if(mah == 1) return "فروردین";
        if(mah == 2) return "اردیبهشت";
        if(mah == 3) return "خرداد";
        if(mah == 4) return "تیر";
        if(mah == 5) return "مرداد";
        if(mah == 6) return "شهریور";
        if(mah == 7) return "مهر";
        if(mah == 8) return "آبان";
        if(mah == 9) return "آذر";
        if(mah == 10) return "دی";
        if(mah == 11) return "بهمن";
        return "اسفند";
    }

    public String porKardaneMainTime(){
        return new SimpleDateFormat("HH:mm:ss", en_locale).format(Calendar.getInstance());
    }
}