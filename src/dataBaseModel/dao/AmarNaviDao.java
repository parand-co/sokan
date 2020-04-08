package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsaranSe;
import amar.model.amarRozaneJireBegir.AmarNavi;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarNaviDao implements BaseQueryDaoI {
    private static AmarNaviDao instance;
    private BaseQuery<AmarNavi> baseQuery = new BaseQuery<>();


    public static AmarNaviDao getInstance() {
        if (instance == null)
            instance = new AmarNaviDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarNavi> getBaseQuery() {
        return baseQuery;
    }
}
