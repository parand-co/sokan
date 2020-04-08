package dataBaseModel.dao;


import dataBaseModel.baseTable.VaziyatAnjamHokmeKar;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class VaziyatAnjamHokmeKarDao implements BaseQueryDaoI {
    private static VaziyatAnjamHokmeKarDao instance;
    private BaseQuery<VaziyatAnjamHokmeKar> baseQuery = new BaseQuery<>();


    public static VaziyatAnjamHokmeKarDao getInstance() {
        if (instance == null)
            instance = new VaziyatAnjamHokmeKarDao();
        return instance;
    }

    @Override
    public BaseQuery<VaziyatAnjamHokmeKar> getBaseQuery() {
        return baseQuery;
    }
}
