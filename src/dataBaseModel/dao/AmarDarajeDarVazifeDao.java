package dataBaseModel.dao;

import amar.model.amarRozaneJireBegir.AmarAfsaranSe;
import amar.model.amarRozaneJireBegir.AmarDaneshjoVazife;
import amar.model.amarRozaneJireBegir.AmarDarajeDarVazife;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class AmarDarajeDarVazifeDao implements BaseQueryDaoI {
    private static AmarDarajeDarVazifeDao instance;
    private BaseQuery<AmarDarajeDarVazife> baseQuery = new BaseQuery<>();


    public static AmarDarajeDarVazifeDao getInstance() {
        if (instance == null)
            instance = new AmarDarajeDarVazifeDao();
        return instance;
    }

    @Override
    public BaseQuery<AmarDarajeDarVazife> getBaseQuery() {
        return baseQuery;
    }
}
