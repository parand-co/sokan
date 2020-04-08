package dataBaseModel.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.EncryptDecryptString;


public class HibernateUtil {

    private static final SessionFactory session_factory;

    static {
        //old configuration
        //session_factory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();

        //new configuration
        Configuration cfg = new Configuration().configure("/hibernate.cfg.xml");

        String urlToDecrypt = cfg.getProperty("hibernate.connection.url");
        String userToDecrypt = cfg.getProperty("hibernate.connection.username");
        String passwordToDecrypt = cfg.getProperty("hibernate.connection.password");

        if(urlToDecrypt.startsWith("ENC(")&&urlToDecrypt.endsWith(")")){
            urlToDecrypt = urlToDecrypt.substring(4, urlToDecrypt.length()-1);

            String decrypted = EncryptDecryptString.decrypt(urlToDecrypt);

            cfg.setProperty("hibernate.connection.url", decrypted);
        }

        if(userToDecrypt.startsWith("ENC(")&&userToDecrypt.endsWith(")")){
            userToDecrypt = userToDecrypt.substring(4, userToDecrypt.length()-1);

            String decrypted = EncryptDecryptString.decrypt(userToDecrypt);

            cfg.setProperty("hibernate.connection.username", decrypted);
        }

        if(passwordToDecrypt.startsWith("ENC(")&&passwordToDecrypt.endsWith(")")){
            passwordToDecrypt = passwordToDecrypt.substring(4, passwordToDecrypt.length()-1);

            String decrypted = EncryptDecryptString.decrypt(passwordToDecrypt);

            cfg.setProperty("hibernate.connection.password", decrypted);
        }

        session_factory = cfg.buildSessionFactory();
    }

    public static Session getSession() {
        ThreadLocal thread_session = new ThreadLocal();
        Session session = (Session) thread_session.get();
        if (session == null || session.equals(null)) {
            session = session_factory.openSession();
            thread_session.set(session);
        }

        return session;

    }
}

