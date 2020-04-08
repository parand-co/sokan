package dataBaseModel.dao;


import amar.model.SavabeghTaradod;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import omorkoliAndgharardadi.model.SavabeghMorakhasi;

public class SavabeghMorakhasiDao implements BaseQueryDaoI {
    private static SavabeghMorakhasiDao instance;
    private BaseQuery<SavabeghMorakhasi> baseQuery = new BaseQuery<>();


    public static SavabeghMorakhasiDao getInstance() {
        if (instance == null)
            instance = new SavabeghMorakhasiDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghMorakhasi> getBaseQuery() {
        return baseQuery;
    }
}
