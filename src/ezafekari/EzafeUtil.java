package ezafekari;

import amar.model.Personel;
import amar.model.Taraddod;
import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.DayType;
import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.util.PwKaraDbUtil;
import ezafekari.model.EzafatoKosoorat;
import ezafekari.model.EzafeKari;
import ezafekari.model.Sanad;
import sun.applet.Main;
import util.PropertiesLoader;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nedaja on 02/02/2020.
 */
@ManagedBean
public class EzafeUtil {

    public static Integer roundSaat(Integer saat) {
        String s = String.valueOf(saat);
        String min;
        String hour;
        if (s.length() == 3) {
            hour = s.substring(0, 1);
            min = s.substring(1, 3);
        } else {
            hour = s.substring(0, 2);
            min = s.substring(2, 4);
        }
        if (Integer.parseInt(min) >= 0 && Integer.parseInt(min) < 15) {
            min = "00";
        } else if (Integer.parseInt(min) >= 15 && Integer.parseInt(min) < 45) {
            min = "30";
        } else if (Integer.parseInt(min) >= 45 && Integer.parseInt(min) <= 59) {
            min = "00";
            hour = String.valueOf(Integer.parseInt(hour) + 1);
        }
        return Integer.parseInt(hour + min);
    }

    public static String calcMakhaz(Personel personel){
        if (personel.getNoeEstekhdam() == null ||
                personel.getNoeEstekhdam().getId() == 1 ||
                personel.getNoeEstekhdam().getId() == 2 ||
                personel.getNoeEstekhdam().getId() == 6)
            if(PropertiesLoader.read("overtime_formool") != null){
                return String.valueOf((personel.getHaghShoghl()+
                        personel.getHagheShaghel()+
                        personel.getFogholadeModiriyat())/Integer.valueOf(PropertiesLoader.read("overtime_formool")));
            } else {
                return String.valueOf((personel.getHaghShoghl()+
                        personel.getHagheShaghel()+
                        personel.getFogholadeModiriyat())/176);
            }

        else
            return String.valueOf(personel.getMadrak().getMakhaz());
    }

    public static float convertModat(int modat) {
        String s = String.valueOf(modat);
        if (s.length() == 2)
            return 0.5f;
        else{
            float a = 0;
            if (modat % 100 != 0)
                a = 0.5f;
            return modat/100 + a;
        }
    }

    public static String threeSplite(String a){

        int end = a.length();
        int begin = end-3;
        if(begin<0){
            begin = 0;
        }
        int virs = end/3;
        if(end%3==0)
            virs--;

//        System.out.println(a.substring(size-6,size-3));

        List<String> strings = new ArrayList<String>();
        for(int i =0;i<=virs;i++){

            strings.add(a.substring(begin,end));
//            if(size-3 <= 0)
            end = end-3;
            begin = begin-3;
            if(begin < 0)
            {
                begin = 0;
            }
        }

        String str="";
        int size = strings.size();
        for(int i =size-1 ; i>=0 ;i--){
            str = str.concat(strings.get(i));
            if (i==0){}
            else{
                str =  str.concat(",");
            }
        }
        return str;
    }


    public static String commaRemover(String a){

        a = a.replaceAll(",","");
        return a;

    }

    public static int calcModat(EzafeKari ezafeKari) {

//        String startm="",endm="",starth="",endh="";
//        if (ezafeKari.getSaatStart() < 1000) {
//            startm = String.valueOf(ezafeKari.getSaatStart()).substring(1,3);
//            starth = String.valueOf(ezafeKari.getSaatStart()).substring(0,1);
//        }else {
//            startm = String.valueOf(ezafeKari.getSaatStart()).substring(2,4);
//            starth = String.valueOf(ezafeKari.getSaatStart()).substring(0,2);
//        }
//        if (ezafeKari.getSaatEnd() < 1000) {
//            endm = String.valueOf(ezafeKari.getSaatStart()).substring(1,3);
//            endh = String.valueOf(ezafeKari.getSaatStart()).substring(0,1);
//        }else {
//            endm = String.valueOf(ezafeKari.getSaatStart()).substring(2,4);
//            endh = String.valueOf(ezafeKari.getSaatStart()).substring(0,2);
//        }
        String hh = String.valueOf(ezafeKari.getSaatEnd() - ezafeKari.getSaatStart());
        if (hh.length() == 2){
            if (hh.matches("0"))
                hh = "0";
        }
        else if (hh.length() == 3){
            if (hh.substring(1,3).matches("70"))
                hh = hh.substring(0,1) + "30";
        }else {
            if (hh.substring(2,4).matches("70"))
                hh = hh.substring(0,2) + "30";
        }
        return Integer.parseInt(hh);
    }


    public static EzafeKari createEzafekariByTaraddod(Personel personel, String date, Taghvim taghvim) {
        SessionGate sessionGate = new SessionGate();
        List<Taraddod> taraddodList = sessionGate.findTaraddodByPersonelAndDate(personel, date);
        if (taraddodList.size() % 2 == 0 && checkEzafeOk(taraddodList, taghvim, personel)) {  //taradod naghes nabashe
            EzafeKari ezafeKari = new EzafeKari();
//            if (taraddodList.size() == 2) {
            Taraddod taraddod = taraddodList.get(taraddodList.size() - 1);
            int roundedTaradodSaat = EzafeUtil.roundSaat(taraddod.getSaat());
            long gorohID = personel.getGorohKari().getId();
            DayType dayType = findDayType(gorohID,taghvim);

            if (dayType.getId() == 1) {
                if (roundedTaradodSaat > Integer.parseInt(personel.getGorohKari().getSaatShoroEzfAddi())) {
                    ezafeKari.setRealSaatStart(Integer.parseInt(personel.getGorohKari().getSaatShoroEzfAddi()));
                    ezafeKari.setSaatStart(Integer.parseInt(personel.getGorohKari().getSaatShoroEzfAddi()));
                    ezafeKari.setRealSaatEnd(taraddod.getSaat());
                    ezafeKari.setSaatEnd(EzafeUtil.roundSaat(taraddod.getSaat()));
                    ezafeKari.setShomareDastgahEnd(taraddod.getShomareDastgah());
                }
            } else if (dayType.getId() == 2) {
                if (roundedTaradodSaat > Integer.parseInt(personel.getGorohKari().getSaatShoroEzfNimTatil())) {
                    ezafeKari.setRealSaatStart(Integer.parseInt(personel.getGorohKari().getSaatShoroEzfNimTatil()));
                    ezafeKari.setSaatStart(Integer.parseInt(personel.getGorohKari().getSaatShoroEzfNimTatil()));
                    ezafeKari.setRealSaatEnd(taraddod.getSaat());
                    ezafeKari.setSaatEnd(EzafeUtil.roundSaat(taraddod.getSaat()));
                    ezafeKari.setShomareDastgahEnd(taraddod.getShomareDastgah());
                }
            } else {
                if (roundedTaradodSaat > Integer.parseInt(personel.getGorohKari().getSaatShoroEzfTatil())) {
                    ezafeKari.setRealSaatStart(Integer.parseInt(personel.getGorohKari().getSaatShoroEzfTatil()));
                    ezafeKari.setSaatStart(Integer.parseInt(personel.getGorohKari().getSaatShoroEzfTatil()));
                    ezafeKari.setRealSaatEnd(taraddod.getSaat());
                    ezafeKari.setSaatEnd(EzafeUtil.roundSaat(taraddod.getSaat()));
                    ezafeKari.setShomareDastgahEnd(taraddod.getShomareDastgah());
                }
            }
            if (ezafeKari.getSaatEnd() != null) {
                ezafeKari.setModat(EzafeUtil.calcModat(ezafeKari));
                ezafeKari.setPersonel(personel);
                ezafeKari.setTarikh(taghvim);
                ezafeKari.setSanad(sessionGate.findSanadByTarikh(date));
                ezafeKari.setPersonelBakhsh(ezafeKari.getPersonel().getBakhsh());
                return ezafeKari;
            } else
                return null;
        }
        return null;
    }

    private static boolean checkEzafeOk(List<Taraddod> taraddodList, Taghvim taghvim, Personel personel) {
        for (Taraddod taraddod : taraddodList) {
            DayType dayType = findDayType(personel.getGorohKari().getId(),taghvim);
            if (dayType.getId() == 1) {
                if (taraddod.getSaat() > Integer.parseInt(personel.getGorohKari().getSaatShoroEzfAddi()))
                    return true;
            }
            else if (dayType.getId() == 2) {
                if (taraddod.getSaat() > Integer.parseInt(personel.getGorohKari().getSaatShoroEzfNimTatil()))
                    return true;
            }
            else {
                if (taraddod.getSaat() > Integer.parseInt(personel.getGorohKari().getSaatShoroEzfTatil()))
                    return true;
            }
        }
        return false;
    }


    public static EzafeKari checkExistOvertime(Personel personel, String date) {
        EzafeKari ezafeKari = new SessionGate().findOvertimeByPersonelAndDate(personel, date);
        if (ezafeKari != null)
            return ezafeKari;
        else
            return null;
    }


    public static DayType findDayType(long gorohID,Taghvim taghvim) {
        switch ((int) gorohID){
            case 1:{
                if (taghvim.getDayTypeG1() != null)
                    return taghvim.getDayTypeG1();
                else
                    return taghvim.getDayType();
            }case 2:{
                if (taghvim.getDayTypeG2() != null)
                    return taghvim.getDayTypeG2();
                else
                    return taghvim.getDayType();
            }case 3:{
                if (taghvim.getDayTypeG3() != null)
                    return taghvim.getDayTypeG3();
                else
                    return taghvim.getDayType();
            }case 4:{
                if (taghvim.getDayTypeG4() != null)
                    return taghvim.getDayTypeG4();
                else
                    return taghvim.getDayType();
            }case 5:{
                if (taghvim.getDayTypeG5() != null)
                    return taghvim.getDayTypeG5();
                else
                    return taghvim.getDayType();
            }case 6:{
                if (taghvim.getDayTypeG6() != null)
                    return taghvim.getDayTypeG6();
                else
                    return taghvim.getDayType();
            }case 7:{
                if (taghvim.getDayTypeG7() != null)
                    return taghvim.getDayTypeG7();
                else
                    return taghvim.getDayType();
            }default:{
                return taghvim.getDayType();
            }
        }
    }

    public static String splitHour(String modat) {
        if (modat.length() == 3)
            return modat.substring(0,1) + ":" + modat.substring(1,3);
        else if (modat.length() == 4)
            return modat.substring(0,2) + ":" + modat.substring(2,4);
        return modat;
    }

    public static int calcEzafekarModat(Personel personel, List<EzafeKari> ezafeKaris) {
        List<EzafeKari> ezafeKariList = ezafeKaris.stream().filter(x -> x.getPersonel().getId() == personel.getId()).collect(Collectors.toList());
        int sum = 0;
        for (EzafeKari ezafeKari : ezafeKariList) {
            String modat = String.valueOf(ezafeKari.getModat());
            if (!modat.contains("300"))
                modat = modat.replaceAll("30", "50");
            sum += Integer.parseInt(modat);
        }

        EzafatoKosoorat ezafat = new SessionGate().findEzafeKasrBySanadAndPers(personel, ezafeKariList.get(0).getSanad().getId());
        if (ezafat != null) {
            String modat = String.valueOf(ezafat.getSaatEK());
            if (ezafat.isEzafOrKasr()) {
                if (!modat.contains("300"))
                    modat = modat.replaceAll("30", "50");
                sum += Integer.parseInt(modat);
            } else {
                if (!modat.contains("300"))
                    modat = modat.replaceAll("30", "50");
                sum -= Integer.parseInt(modat);
                if (sum < 0)
                    sum = 0;
            }
        }
        if (String.valueOf(sum).contains("500"))
            return sum;
        else
            return Integer.parseInt(String.valueOf(sum).replaceAll("50", "30"));
    }

    public static long calcMablaghByModatAndMakhaz(EzafeKarDto ezafeKarDto) {
        long a =  (long) (EzafeUtil.convertModat(Integer.parseInt(ezafeKarDto.getModat())) * Long.parseLong(ezafeKarDto.getMakhaz()));
//        if (a < 0 )
//            a = 0;
//        if (a > ezafeKarDto.getPersonel().getSaghfeEzafeKar())
//            a = ezafeKarDto.getPersonel().getSaghfeEzafeKar();
        return a;
    }

    public static String calcMablaghJire(Personel personel,Sanad sanad) {
        if (personel.getId() != 0) {
            if (personel.getNoeEstekhdam() == null ||
                    personel.getNoeEstekhdam().getId() == 1 ||
                    personel.getNoeEstekhdam().getId() == 2 ||
                    personel.getNoeEstekhdam().getId() == 6)
                return String.valueOf(sanad.getMablaghJirePayvar() * Integer.parseInt(calcCountJire(personel,sanad)));
            else
                return String.valueOf(sanad.getMablaghJireGharardadi() * Integer.parseInt(calcCountJire(personel,sanad)));
        }
        return "00";
    }

    public static String calcCountJire(Personel personel,Sanad sanad) {
        if (personel.getId() != 0) {
            return new SessionGate().findCountJireByPersonelAndMahAndSal(personel, sanad.getMah(), sanad.getSal());
        }
        return "0";
    }


    public static String calcKasrYegani(Personel personel,Sanad sanad) {
        if (personel.getNoeEstekhdam() == null)
            return "00";
        else if (personel.getNoeEstekhdam().getId() == 6)
            return String.valueOf(sanad.getKasrYeganiPeymani());
        else if (personel.getNoeEstekhdam().getId() == 3 ||
                personel.getNoeEstekhdam().getId() == 4 ||
                personel.getNoeEstekhdam().getId() == 7)
            return String.valueOf(sanad.getKasrYeganiGharardadi());
        else if (personel.getNoeEstekhdam().getId() == 1 ||
                personel.getNoeEstekhdam().getId() == 2)
            return String.valueOf(sanad.getKasrYeganiPayvar());
        else
            return "00";
    }



}
