package dataBaseModel.dao;


import dataBaseModel.baseTable.AnjamDahandeHokmeKar;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AnjamDahandeHokmeKarDao implements BaseQueryDaoI {
    private static AnjamDahandeHokmeKarDao instance;
    private BaseQuery<AnjamDahandeHokmeKar> baseQuery = new BaseQuery<>();


    public static AnjamDahandeHokmeKarDao getInstance() {
        if (instance == null)
            instance = new AnjamDahandeHokmeKarDao();
        return instance;
    }

    @Override
    public BaseQuery<AnjamDahandeHokmeKar> getBaseQuery() {
        return baseQuery;
    }
}
