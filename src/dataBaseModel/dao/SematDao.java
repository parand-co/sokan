package dataBaseModel.dao;

import dataBaseModel.baseTable.Semat;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SematDao implements BaseQueryDaoI {
    private static SematDao instance;
    private BaseQuery<Semat> baseQuery = new BaseQuery<>();


    public static SematDao getInstance() {
        if (instance == null)
            instance = new SematDao();
        return instance;
    }

    @Override
    public BaseQuery<Semat> getBaseQuery() {
        return baseQuery;
    }
}
