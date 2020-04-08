package dataBaseModel.dao;


import dataBaseModel.baseTable.TypeHokmeKar;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class TypeHokmeKarDao implements BaseQueryDaoI {
    private static TypeHokmeKarDao instance;
    private BaseQuery<TypeHokmeKar> baseQuery = new BaseQuery<>();


    public static TypeHokmeKarDao getInstance() {
        if (instance == null)
            instance = new TypeHokmeKarDao();
        return instance;
    }

    @Override
    public BaseQuery<TypeHokmeKar> getBaseQuery() {
        return baseQuery;
    }
}
