package dataBaseModel.dao;

import dataBaseModel.baseTable.Madrak;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class MadrakDao implements BaseQueryDaoI {
    private static MadrakDao instance;
    private BaseQuery<Madrak> baseQuery = new BaseQuery<>();


    public static MadrakDao getInstance() {
        if (instance == null)
            instance = new MadrakDao();
        return instance;
    }

    @Override
    public BaseQuery<Madrak> getBaseQuery() {
        return baseQuery;
    }
}
