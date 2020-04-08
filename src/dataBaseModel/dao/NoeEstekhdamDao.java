package dataBaseModel.dao;

import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class NoeEstekhdamDao implements BaseQueryDaoI {
    private static NoeEstekhdamDao instance;
    private BaseQuery<NoeEstekhdam> baseQuery = new BaseQuery<>();


    public static NoeEstekhdamDao getInstance() {
        if (instance == null)
            instance = new NoeEstekhdamDao();
        return instance;
    }

    @Override
    public BaseQuery<NoeEstekhdam> getBaseQuery() {
        return baseQuery;
    }
}
