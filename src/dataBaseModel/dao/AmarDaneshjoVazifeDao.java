package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarDaneshjoVazife;
import amar.model.amarRozaneJireBegir.AmarDarajeDaran;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarDaneshjoVazifeDao implements BaseQueryDaoI {
    private static AmarDaneshjoVazifeDao instance;
    private BaseQuery<AmarDaneshjoVazife> baseQuery = new BaseQuery<>();


    public static AmarDaneshjoVazifeDao getInstance() {
        if (instance == null)
            instance = new AmarDaneshjoVazifeDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarDaneshjoVazife> getBaseQuery() {
        return baseQuery;
    }
}
