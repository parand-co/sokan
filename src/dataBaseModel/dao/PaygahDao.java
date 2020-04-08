package dataBaseModel.dao;

import dataBaseModel.baseTable.Paygah;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class PaygahDao implements BaseQueryDaoI {
    private static PaygahDao instance;
    private BaseQuery<Paygah> baseQuery = new BaseQuery<>();


    public static PaygahDao getInstance() {
        if (instance == null)
            instance = new PaygahDao();
        return instance;
    }

    @Override
    public BaseQuery<Paygah> getBaseQuery() {
        return baseQuery;
    }
}
