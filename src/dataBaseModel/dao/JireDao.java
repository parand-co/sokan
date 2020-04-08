package dataBaseModel.dao;

import dataBaseModel.baseTable.Month;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;
import ezafekari.model.Jire;

public class JireDao implements BaseQueryDaoI {
    private static JireDao instance;
    private BaseQuery<Jire> baseQuery = new BaseQuery<>();


    public static JireDao getInstance() {
        if (instance == null)
            instance = new JireDao();
        return instance;
    }

    @Override
    public BaseQuery<Jire> getBaseQuery() {
        return baseQuery;
    }
}
