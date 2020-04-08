package dataBaseModel.dao;


import amar.model.SavabeghTaradod;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SavabeghTaradodDao implements BaseQueryDaoI {
    private static SavabeghTaradodDao instance;
    private BaseQuery<SavabeghTaradod> baseQuery = new BaseQuery<>();


    public static SavabeghTaradodDao getInstance() {
        if (instance == null)
            instance = new SavabeghTaradodDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghTaradod> getBaseQuery() {
        return baseQuery;
    }
}
