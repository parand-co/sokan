package dataBaseModel.dao;

import dataBaseModel.baseTable.Daraje;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class DarajeDao implements BaseQueryDaoI {
    private static DarajeDao instance;
    private BaseQuery<Daraje> baseQuery = new BaseQuery<>();


    public static DarajeDao getInstance() {
        if (instance == null)
            instance = new DarajeDao();
        return instance;
    }

    @Override
    public BaseQuery<Daraje> getBaseQuery() {
        return baseQuery;
    }
}
