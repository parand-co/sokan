package dataBaseModel.dao;

import dataBaseModel.baseTable.Takhasos;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class TakhasosDao implements BaseQueryDaoI {
    private static TakhasosDao instance;
    private BaseQuery<Takhasos> baseQuery = new BaseQuery<>();


    public static TakhasosDao getInstance() {
        if (instance == null)
            instance = new TakhasosDao();
        return instance;
    }

    @Override
    public BaseQuery<Takhasos> getBaseQuery() {
        return baseQuery;
    }
}
