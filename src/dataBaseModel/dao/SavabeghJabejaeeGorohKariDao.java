package dataBaseModel.dao;


import amar.model.SavabeghJabejaeeGorohKari;
import amar.model.SavabeghTaradod;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SavabeghJabejaeeGorohKariDao implements BaseQueryDaoI {
    private static SavabeghJabejaeeGorohKariDao instance;
    private BaseQuery<SavabeghJabejaeeGorohKari> baseQuery = new BaseQuery<>();


    public static SavabeghJabejaeeGorohKariDao getInstance() {
        if (instance == null)
            instance = new SavabeghJabejaeeGorohKariDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghJabejaeeGorohKari> getBaseQuery() {
        return baseQuery;
    }
}
