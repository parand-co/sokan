package dataBaseModel.dao;

import dataBaseModel.query.BaseQuery;
import ezafekari.model.EzafeKari;

public class EzafekariDao extends BaseQuery<EzafeKari> {
    private static EzafekariDao ourInstance = new EzafekariDao();

    public static EzafekariDao getInstance() {
        return ourInstance;
    }

    private EzafekariDao() {
    }
}
