package dataBaseModel.dao;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import omorkoliAndgharardadi.model.SavabeghTabaghePezeshki;

public class SavabeghTabaghePezeshkiDao implements BaseQueryDaoI {
    private static SavabeghTabaghePezeshkiDao instance;
    private BaseQuery<SavabeghTabaghePezeshki> baseQuery = new BaseQuery<>();


    public static SavabeghTabaghePezeshkiDao getInstance() {
        if (instance == null)
            instance = new SavabeghTabaghePezeshkiDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghTabaghePezeshki> getBaseQuery() {
        return baseQuery;
    }
}
