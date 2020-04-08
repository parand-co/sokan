package dataBaseModel.dao;


import dataBaseModel.baseTable.TypeKarHokmeKar;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class TypeKarHokmeKarDao implements BaseQueryDaoI {
    private static TypeKarHokmeKarDao instance;
    private BaseQuery<TypeKarHokmeKar> baseQuery = new BaseQuery<>();


    public static TypeKarHokmeKarDao getInstance() {
        if (instance == null)
            instance = new TypeKarHokmeKarDao();
        return instance;
    }

    @Override
    public BaseQuery<TypeKarHokmeKar> getBaseQuery() {
        return baseQuery;
    }
}
