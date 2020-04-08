package dataBaseModel.dao;

import dataBaseModel.baseTable.Raste;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class RasteDao implements BaseQueryDaoI {
    private static RasteDao instance;
    private BaseQuery<Raste> baseQuery = new BaseQuery<>();


    public static RasteDao getInstance() {
        if (instance == null)
            instance = new RasteDao();
        return instance;
    }

    @Override
    public BaseQuery<Raste> getBaseQuery() {
        return baseQuery;
    }
}
