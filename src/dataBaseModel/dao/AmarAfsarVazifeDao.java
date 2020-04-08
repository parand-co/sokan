package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsarVazife;
import amar.model.amarRozaneJireBegir.AmarAfsaranSe;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarAfsarVazifeDao implements BaseQueryDaoI {
    private static AmarAfsarVazifeDao instance;
    private BaseQuery<AmarAfsarVazife> baseQuery = new BaseQuery<>();


    public static AmarAfsarVazifeDao getInstance() {
        if (instance == null)
            instance = new AmarAfsarVazifeDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarAfsarVazife> getBaseQuery() {
        return baseQuery;
    }
}
