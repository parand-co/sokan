package dataBaseModel.dao;

import dataBaseModel.baseTable.VazeyatMorakhasi;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class VazeyatMorakhasiDao implements BaseQueryDaoI {
    private static VazeyatMorakhasiDao instance;
    private BaseQuery<VazeyatMorakhasi> baseQuery = new BaseQuery<>();


    public static VazeyatMorakhasiDao getInstance() {
        if (instance == null)
            instance = new VazeyatMorakhasiDao();
        return instance;
    }

    @Override
    public BaseQuery<VazeyatMorakhasi> getBaseQuery() {
        return baseQuery;
    }
}
