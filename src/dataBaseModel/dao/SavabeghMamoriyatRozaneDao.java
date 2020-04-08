package dataBaseModel.dao;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import omorkoliAndgharardadi.model.SavabeghMamoriyatRozane;
import omorkoliAndgharardadi.model.SavabeghMorakhasi;

public class SavabeghMamoriyatRozaneDao implements BaseQueryDaoI {
    private static SavabeghMamoriyatRozaneDao instance;
    private BaseQuery<SavabeghMamoriyatRozane> baseQuery = new BaseQuery<>();


    public static SavabeghMamoriyatRozaneDao getInstance() {
        if (instance == null)
            instance = new SavabeghMamoriyatRozaneDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghMamoriyatRozane> getBaseQuery() {
        return baseQuery;
    }
}
