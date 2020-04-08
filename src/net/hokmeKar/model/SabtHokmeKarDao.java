package net.hokmeKar.model;

import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SabtHokmeKarDao implements BaseQueryDaoI {
    private static SabtHokmeKarDao instance;
    private BaseQuery<SabtHokmeKar> baseQuery = new BaseQuery<>();


    public static SabtHokmeKarDao getInstance() {
        if (instance == null)
            instance = new SabtHokmeKarDao();
        return instance;
    }

    @Override
    public BaseQuery<SabtHokmeKar> getBaseQuery() {
        return baseQuery;
    }
}
