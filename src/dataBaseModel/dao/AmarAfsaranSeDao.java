package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsaranSe;
import amar.model.amarRozaneJireBegir.AmarAfsaranYekVaDo;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarAfsaranSeDao implements BaseQueryDaoI {
    private static AmarAfsaranSeDao instance;
    private BaseQuery<AmarAfsaranSe> baseQuery = new BaseQuery<>();


    public static AmarAfsaranSeDao getInstance() {
        if (instance == null)
            instance = new AmarAfsaranSeDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarAfsaranSe> getBaseQuery() {
        return baseQuery;
    }
}
