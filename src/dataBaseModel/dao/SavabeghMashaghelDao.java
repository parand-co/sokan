package dataBaseModel.dao;

import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import omorkoliAndgharardadi.model.SavabeghMashaghel;

public class SavabeghMashaghelDao implements BaseQueryDaoI {
    private static SavabeghMashaghelDao instance;
    private BaseQuery<SavabeghMashaghel> baseQuery = new BaseQuery<>();


    public static SavabeghMashaghelDao getInstance() {
        if (instance == null)
            instance = new SavabeghMashaghelDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghMashaghel> getBaseQuery() {
        return baseQuery;
    }
}
