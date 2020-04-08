package dataBaseModel.dao;

import dataBaseModel.baseTable.NoeMorakhasi;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class NoeMorakhasiDao implements BaseQueryDaoI {
    private static NoeMorakhasiDao instance;
    private BaseQuery<NoeMorakhasi> baseQuery = new BaseQuery<>();


    public static NoeMorakhasiDao getInstance() {
        if (instance == null)
            instance = new NoeMorakhasiDao();
        return instance;
    }

    @Override
    public BaseQuery<NoeMorakhasi> getBaseQuery() {
        return baseQuery;
    }
}
