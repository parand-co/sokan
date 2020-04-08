package dashboard.model;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class NewsDao implements BaseQueryDaoI {
    private static NewsDao instance;

    private BaseQuery<News> baseQuery = new BaseQuery<>();

    public static NewsDao getInstance() {
        if (instance == null)
            instance = new NewsDao();
        return instance;
    }
    @Override
    public BaseQuery<News> getBaseQuery() {
        return baseQuery;
    }
}
