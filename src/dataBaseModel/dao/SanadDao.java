package dataBaseModel.dao;

import dataBaseModel.query.BaseQuery;
import ezafekari.model.EzafeKari;
import ezafekari.model.Sanad;

public class SanadDao extends BaseQuery<Sanad> {
    private static SanadDao ourInstance = new SanadDao();

    public static SanadDao getInstance() {
        return ourInstance;
    }

    private SanadDao() {
    }
}
