package ezafekari.model;

import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class EmzaDao implements BaseQueryDaoI {
    private static EmzaDao instance;
    private BaseQuery<Emza> baseQuery = new BaseQuery<>();


    public static EmzaDao getInstance() {
        if (instance == null)
            instance = new EmzaDao();
        return instance;
    }

    @Override
    public BaseQuery<Emza> getBaseQuery() {
        return baseQuery;
    }
}
