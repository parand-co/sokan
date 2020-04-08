package dataBaseModel.dao;

import dataBaseModel.baseTable.MojavezRozane;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class MojavezRozaneDao implements BaseQueryDaoI {
    private static MojavezRozaneDao instance;
    private BaseQuery<MojavezRozane> baseQuery = new BaseQuery<>();


    public static MojavezRozaneDao getInstance() {
        if (instance == null)
            instance = new MojavezRozaneDao();
        return instance;
    }

    @Override
    public BaseQuery<MojavezRozane> getBaseQuery() {
        return baseQuery;
    }
}
