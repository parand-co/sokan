package dataBaseModel.dao;

import dataBaseModel.baseTable.Yegan;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class YeganDao implements BaseQueryDaoI {
    private static YeganDao instance;
    private BaseQuery<Yegan> baseQuery = new BaseQuery<>();


    public static YeganDao getInstance() {
        if (instance == null)
            instance = new YeganDao();
        return instance;
    }

    @Override
    public BaseQuery<Yegan> getBaseQuery() {
        return baseQuery;
    }
}
