package dataBaseModel.dao;

import dataBaseModel.baseTable.VazeyatTaahol;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class VazeyatTaaholDao implements BaseQueryDaoI {
    private static VazeyatTaaholDao instance;
    private BaseQuery<VazeyatTaahol> baseQuery = new BaseQuery<>();


    public static VazeyatTaaholDao getInstance() {
        if (instance == null)
            instance = new VazeyatTaaholDao();
        return instance;
    }

    @Override
    public BaseQuery<VazeyatTaahol> getBaseQuery() {
        return baseQuery;
    }
}
