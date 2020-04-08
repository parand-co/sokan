package dataBaseModel.query;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


public interface BaseQueryI<T> {


    void createOrUpdate(T t, String url);
    void createOrUpdate(T t);


    void create(T t, String url);
    void create(T t);


    void delete(T t, String url);
    void delete(T t);

    T getObject(T t);

    List<T> getList(Session session, Query query);
}
