package dataBaseModel.dao;

import dataBaseModel.baseTable.DayType;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class DayTypeDao implements BaseQueryDaoI {
    private static DayTypeDao instance;
    private BaseQuery<DayType> baseQuery = new BaseQuery<>();


    public static DayTypeDao getInstance() {
        if (instance == null)
            instance = new DayTypeDao();
        return instance;
    }

    @Override
    public BaseQuery<DayType> getBaseQuery() {
        return baseQuery;
    }
}
