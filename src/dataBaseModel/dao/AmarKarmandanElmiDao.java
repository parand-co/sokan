package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsaranSe;
import amar.model.amarRozaneJireBegir.AmarKarmandanElmi;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarKarmandanElmiDao implements BaseQueryDaoI {
    private static AmarKarmandanElmiDao instance;
    private BaseQuery<AmarKarmandanElmi> baseQuery = new BaseQuery<>();


    public static AmarKarmandanElmiDao getInstance() {
        if (instance == null)
            instance = new AmarKarmandanElmiDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarKarmandanElmi> getBaseQuery() {
        return baseQuery;
    }
}
