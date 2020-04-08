package dataBaseModel.dao;


import amar.model.SavabeghTaradod;
import amar.model.Taraddod;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class TaraddodDao implements BaseQueryDaoI {
    private static TaraddodDao instance;
    private BaseQuery<Taraddod> baseQuery = new BaseQuery<>();


    public static TaraddodDao getInstance() {
        if (instance == null)
            instance = new TaraddodDao();
        return instance;
    }

    @Override
    public BaseQuery<Taraddod> getBaseQuery() {
        return baseQuery;
    }
}
