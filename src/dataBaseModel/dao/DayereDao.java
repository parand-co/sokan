package dataBaseModel.dao;

import dataBaseModel.baseTable.Dayere;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class DayereDao implements BaseQueryDaoI {
    private static DayereDao instance;
    private BaseQuery<Dayere> baseQuery = new BaseQuery<>();


    public static DayereDao getInstance() {
        if (instance == null)
            instance = new DayereDao();
        return instance;
    }

    @Override
    public BaseQuery<Dayere> getBaseQuery() {
        return baseQuery;
    }
}
