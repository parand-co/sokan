package dashboard.model;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class BannerDao implements BaseQueryDaoI {
    private static BannerDao instance;

    private BaseQuery<Banner> baseQuery = new BaseQuery<>();

    public static BannerDao getInstance() {
        if (instance == null)
            instance = new BannerDao();
        return instance;
    }
    @Override
    public BaseQuery<Banner> getBaseQuery() {
        return baseQuery;
    }
}
