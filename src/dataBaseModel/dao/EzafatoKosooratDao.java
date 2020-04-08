package dataBaseModel.dao;

import dataBaseModel.query.BaseQuery;
import ezafekari.model.EzafatoKosoorat;
import ezafekari.model.EzafeKari;

public class EzafatoKosooratDao extends BaseQuery<EzafatoKosoorat> {
    private static EzafatoKosooratDao ourInstance = new EzafatoKosooratDao();

    public static EzafatoKosooratDao getInstance() {
        return ourInstance;
    }

    private EzafatoKosooratDao() {
    }
}
