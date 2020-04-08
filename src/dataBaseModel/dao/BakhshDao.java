package dataBaseModel.dao;

import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class BakhshDao implements BaseQueryDaoI {
    private static BakhshDao instance;
    private BaseQuery<Bakhsh> baseQuery = new BaseQuery<>();


    public static BakhshDao getInstance() {
        if (instance == null)
            instance = new BakhshDao();
        return instance;
    }

    @Override
    public BaseQuery<Bakhsh> getBaseQuery() {
        return baseQuery;
    }
}
