package dataBaseModel.dao;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import omorkoliAndgharardadi.model.SavabeghMorakhasi;
import omorkoliAndgharardadi.model.SavabeghNahast;

public class SavabeghNahastDao implements BaseQueryDaoI {
    private static SavabeghNahastDao instance;
    private BaseQuery<SavabeghNahast> baseQuery = new BaseQuery<>();


    public static SavabeghNahastDao getInstance() {
        if (instance == null)
            instance = new SavabeghNahastDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghNahast> getBaseQuery() {
        return baseQuery;
    }
}
