package dataBaseModel.dao;

import amar.model.Personel;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class PersonelDao implements BaseQueryDaoI {
    private static PersonelDao instance;
    private BaseQuery<Personel> baseQuery = new BaseQuery<>();


    public static PersonelDao getInstance() {
        if (instance == null)
            instance = new PersonelDao();
        return instance;
    }

    @Override
    public BaseQuery<Personel> getBaseQuery() {
        return baseQuery;
    }
}
