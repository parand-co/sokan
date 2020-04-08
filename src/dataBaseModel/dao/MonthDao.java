package dataBaseModel.dao;

import dataBaseModel.baseTable.Month;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class MonthDao implements BaseQueryDaoI {
    private static MonthDao instance;
    private BaseQuery<Month> baseQuery = new BaseQuery<>();


    public static MonthDao getInstance() {
        if (instance == null)
            instance = new MonthDao();
        return instance;
    }

    @Override
    public BaseQuery<Month> getBaseQuery() {
        return baseQuery;
    }
}
