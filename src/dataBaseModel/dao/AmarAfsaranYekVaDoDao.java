package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsaranYekVaDo;
import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarAfsaranYekVaDoDao implements BaseQueryDaoI {
    private static AmarAfsaranYekVaDoDao instance;
    private BaseQuery<AmarAfsaranYekVaDo> baseQuery = new BaseQuery<>();


    public static AmarAfsaranYekVaDoDao getInstance() {
        if (instance == null)
            instance = new AmarAfsaranYekVaDoDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarAfsaranYekVaDo> getBaseQuery() {
        return baseQuery;
    }
}
