package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsaranSe;
import amar.model.amarRozaneJireBegir.AmarDarajeDaran;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarDarajeDaranDao implements BaseQueryDaoI {
    private static AmarDarajeDaranDao instance;
    private BaseQuery<AmarDarajeDaran> baseQuery = new BaseQuery<>();


    public static AmarDarajeDaranDao getInstance() {
        if (instance == null)
            instance = new AmarDarajeDaranDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarDarajeDaran> getBaseQuery() {
        return baseQuery;
    }
}
