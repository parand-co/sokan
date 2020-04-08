package ezafekari;

import amar.model.Personel;
import amar.model.Taraddod;
import dataBaseModel.SessionGate;
import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.dao.EzafekariDao;
import dataBaseModel.util.PersianCalUtil;
import ezafekari.model.EzafeKari;

import java.util.*;

/**
 * Created by nedaja on 25/01/2018.
 */
public class UpdateEzfTask extends TimerTask{

    private final static long ONCE_PER_DAY = 1000*60*60*24;

    //private final static int ONE_DAY = 1;
    private final static int TWO_AM = 10;
    private final static int ZERO_MINUTES = 40;
    private int i=0;
    SessionGate sessionGate = new SessionGate();

    @Override
    public void run() {
        try {
            System.out.println("job started ...");
            String yesterday = PersianCalUtil.getYesterday(PersianCalUtil.getCurrentDate());
            Taghvim taghvim = sessionGate.findTaghvimByTarikh(yesterday);
            List<Taraddod> taraddodList = sessionGate.findTaraddodByTaghvim(taghvim);
            List<Personel> personelList = readyListPersonelsHaveTaraddod(taraddodList);
            for (Personel personel : personelList) {
                EzafeKari ezafeKari = EzafeUtil.checkExistOvertime(personel,taghvim.getTarikh());
                if (ezafeKari != null) {
                    System.out.println(1);
                } else {
                    EzafeKari ezafeKari1 = EzafeUtil.createEzafekariByTaraddod(personel, taghvim.getTarikh(), taghvim);
                    if (ezafeKari1 != null) {
                        EzafekariDao.getInstance().create(ezafeKari1);
                        System.out.println(2);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //personel daraye taraddod dar yek rooz
    private List<Personel> readyListPersonelsHaveTaraddod(List<Taraddod> taraddodList) {
        List<Personel> personelList = new ArrayList<>();
        for (Taraddod taraddod : taraddodList) {
            if (!personelList.contains(taraddod.getPersonel()))
                personelList.add(taraddod.getPersonel());
        }
        return personelList;
    }

    private static Date getTomorrowMorning2AM(){
        Date date2am = new Date();
        date2am.setHours(TWO_AM);
        date2am.setMinutes(ZERO_MINUTES);
        return date2am;
    }

    //call this method from your servlet init method
    public void startTask(){
//        UpdateDbTask task = new UpdateDbTask();
        Timer timer = new Timer();
        timer.schedule(new UpdateEzfTask(),getTomorrowMorning2AM(),ONCE_PER_DAY);// for your case u need to give 1000*60*60*24
    }


}
