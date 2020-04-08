package dataBaseModel.dao;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import omorkoliAndgharardadi.model.SavabeghEsterahatPezeshki;

public class SavabeghEsterahatPezeshkiDao implements BaseQueryDaoI {
    private static SavabeghEsterahatPezeshkiDao instance;
    private BaseQuery<SavabeghEsterahatPezeshki> baseQuery = new BaseQuery<>();


    public static SavabeghEsterahatPezeshkiDao getInstance() {
        if (instance == null)
            instance = new SavabeghEsterahatPezeshkiDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghEsterahatPezeshki> getBaseQuery() {
        return baseQuery;
    }
}
