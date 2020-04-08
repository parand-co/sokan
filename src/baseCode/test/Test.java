package baseCode.test;

import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.EncryptDecryptString;

import java.util.List;

public class Test {
    public static void main(String[] args) {
//        String en = EncryptDecryptString.encrypt("13870");
//        System.out.println(en);
//        String de = EncryptDecryptString.decrypt(en);
//        System.out.println(de);


        Session session=HibernateUtil.getSession();
        List<Bakhsh>bakhshList=session.createQuery("from Bakhsh").list();
        session.close();


    }

}
