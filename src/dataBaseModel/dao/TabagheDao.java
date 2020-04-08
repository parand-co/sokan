package dataBaseModel.dao;

import dataBaseModel.baseTable.Tabaghe;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class TabagheDao implements BaseQueryDaoI {
    private static TabagheDao instance;
    private BaseQuery<Tabaghe> baseQuery = new BaseQuery<>();


    public static TabagheDao getInstance() {
        if (instance == null)
            instance = new TabagheDao();
        return instance;
    }

    @Override
    public BaseQuery<Tabaghe> getBaseQuery() {
        return baseQuery;
    }
}
