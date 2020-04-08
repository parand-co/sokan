package dataBaseModel.query;

import dataBaseModel.manager.LogController;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.LocalStatus;

import java.util.List;

/**
 * Created by nedaja on 6/18/16.
 */
public class BaseQuery<T> implements BaseQueryI<T> {


    @Override
    public void create(T t, String url) {

            Session session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
            session.close();
        try {
            LogController logController = new LogController(url);
            logController.logCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void create(T t) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createOrUpdate(T t, String url) {

            Session session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            session.close();
        try {
            LogController logController = new LogController(url);
            logController.logUpdate();
        } catch (Exception e) {

        }

    }

    @Override
    public void createOrUpdate(T t) {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(T t, String url) {

            Session session = HibernateUtil
                    .getSession();
            Transaction transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            session.close();
        try {
            LogController logController = new LogController(url);
            logController.logDelete();
        } catch (Exception e) {

        }

    }

    @Override
    public void delete(T t) {

        Session session = HibernateUtil
                .getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();
        session.close();
    }

    @Override
    public T getObject(T t) {
        return null;
    }

    @Override
    public List<T> getList(Session session, Query query) {
        List<T> list;
        Transaction tx = session.beginTransaction();
        list = query.list();
        tx.commit();
        session.flush();
        session.clear();
        session.close();
        return list;
    }

//    public List getList(T t)
}
