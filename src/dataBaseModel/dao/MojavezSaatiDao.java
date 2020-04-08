package dataBaseModel.dao;

import dataBaseModel.baseTable.MojavezSaati;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class MojavezSaatiDao implements BaseQueryDaoI {
    private static MojavezSaatiDao instance;
    private BaseQuery<MojavezSaati> baseQuery = new BaseQuery<>();


    public static MojavezSaatiDao getInstance() {
        if (instance == null)
            instance = new MojavezSaatiDao();
        return instance;
    }

    @Override
    public BaseQuery<MojavezSaati> getBaseQuery() {
        return baseQuery;
    }
}
