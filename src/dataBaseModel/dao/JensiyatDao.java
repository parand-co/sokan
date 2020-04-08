package dataBaseModel.dao;

import dataBaseModel.baseTable.Jensiyat;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class JensiyatDao implements BaseQueryDaoI {
    private static JensiyatDao instance;
    private BaseQuery<Jensiyat> baseQuery = new BaseQuery<>();


    public static JensiyatDao getInstance() {
        if (instance == null)
            instance = new JensiyatDao();
        return instance;
    }

    @Override
    public BaseQuery<Jensiyat> getBaseQuery() {
        return baseQuery;
    }
}
