package dataBaseModel.dao;


import dataBaseModel.baseTable.ArjaiyatHokmeKar;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class ArjaiyatHokmeKarDao implements BaseQueryDaoI {
    private static ArjaiyatHokmeKarDao instance;
    private BaseQuery<ArjaiyatHokmeKar> baseQuery = new BaseQuery<>();


    public static ArjaiyatHokmeKarDao getInstance() {
        if (instance == null)
            instance = new ArjaiyatHokmeKarDao();
        return instance;
    }

    @Override
    public BaseQuery<ArjaiyatHokmeKar> getBaseQuery() {
        return baseQuery;
    }
}
