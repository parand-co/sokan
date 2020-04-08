package dataBaseModel.dao;

import dataBaseModel.baseTable.FarmandehiMostaghel;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class FarmandehiMostaghelDao implements BaseQueryDaoI {
    private static FarmandehiMostaghelDao instance;
    private BaseQuery<FarmandehiMostaghel> baseQuery = new BaseQuery<>();


    public static FarmandehiMostaghelDao getInstance() {
        if (instance == null)
            instance = new FarmandehiMostaghelDao();
        return instance;
    }

    @Override
    public BaseQuery<FarmandehiMostaghel> getBaseQuery() {
        return baseQuery;
    }
}
