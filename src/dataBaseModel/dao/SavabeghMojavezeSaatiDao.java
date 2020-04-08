package dataBaseModel.dao;


import amar.model.SavabeghMojavezeSaati;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SavabeghMojavezeSaatiDao implements BaseQueryDaoI {
    private static SavabeghMojavezeSaatiDao instance;
    private BaseQuery<SavabeghMojavezeSaati> baseQuery = new BaseQuery<>();


    public static SavabeghMojavezeSaatiDao getInstance() {
        if (instance == null)
            instance = new SavabeghMojavezeSaatiDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghMojavezeSaati> getBaseQuery() {
        return baseQuery;
    }
}
