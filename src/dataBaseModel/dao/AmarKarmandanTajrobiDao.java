package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarKarmandanElmi;
import amar.model.amarRozaneJireBegir.AmarKarmandanTajrobi;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarKarmandanTajrobiDao implements BaseQueryDaoI {
    private static AmarKarmandanTajrobiDao instance;
    private BaseQuery<AmarKarmandanTajrobi> baseQuery = new BaseQuery<>();


    public static AmarKarmandanTajrobiDao getInstance() {
        if (instance == null)
            instance = new AmarKarmandanTajrobiDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarKarmandanTajrobi> getBaseQuery() {
        return baseQuery;
    }
}
