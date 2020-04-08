package dataBaseModel.util;


import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.user.User;

import org.hibernate.Query;
import org.hibernate.Session;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import java.util.Map;


public class SessionUtil {
    public  User user = new User();

    private static Map<String, Object> getSesionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public HttpSession session() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static void createSession(User user) {
        getSesionMap().put("user", user.getId());
    }

    public static void setSessionToNull() {
        getSesionMap().put("user",null);
    }

    public Permission getPermission() {
        try {
            Long userId = (Long) getSesionMap().get("user");
            Session session = HibernateUtil.getSession();
            Query query = session.createQuery("FROM Permission where user.id = ?");
            query.setLong(0, userId);
            Permission per = (Permission) query.list().get(0);
            session.close();
            return per;
        } catch (Exception e) {
            return null;
        }
    }
}