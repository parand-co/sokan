package dataBaseModel.dao;

import dataBaseModel.baseTable.Goroh;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class GorohDao implements BaseQueryDaoI {
    private static GorohDao instance;
    private BaseQuery<Goroh> baseQuery = new BaseQuery<>();


    public static GorohDao getInstance() {
        if (instance == null)
            instance = new GorohDao();
        return instance;
    }

    @Override
    public BaseQuery<Goroh> getBaseQuery() {
        return baseQuery;
    }
}
