package dataBaseModel.dao;


import amar.model.SavabeghAmarJireBegirRozane;
import amar.model.SavabeghJabejaeeGorohKari;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SavabeghAmarJireBegirRozaneDao implements BaseQueryDaoI {
    private static SavabeghAmarJireBegirRozaneDao instance;
    private BaseQuery<SavabeghAmarJireBegirRozane> baseQuery = new BaseQuery<>();


    public static SavabeghAmarJireBegirRozaneDao getInstance() {
        if (instance == null)
            instance = new SavabeghAmarJireBegirRozaneDao();
        return instance;
    }

    @Override
    public BaseQuery<SavabeghAmarJireBegirRozane> getBaseQuery() {
        return baseQuery;
    }
}
