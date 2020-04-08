package dataBaseModel.dao;

import dataBaseModel.baseTable.Hoviyat;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class HoviyatDao implements BaseQueryDaoI {
    private static HoviyatDao instance;
    private BaseQuery<Hoviyat> baseQuery = new BaseQuery<>();


    public static HoviyatDao getInstance() {
        if (instance == null)
            instance = new HoviyatDao();
        return instance;
    }

    @Override
    public BaseQuery<Hoviyat> getBaseQuery() {
        return baseQuery;
    }
}
