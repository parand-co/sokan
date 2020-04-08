package dataBaseModel.authentication.user;


import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class UserDao implements BaseQueryDaoI {
    private static UserDao instance;

    private BaseQuery<User> baseQuery = new BaseQuery<>();

    public static UserDao getInstance() {
        if (instance == null)
            instance = new UserDao();
        return instance;
    }
    @Override
    public BaseQuery<User> getBaseQuery() {
        return baseQuery;
    }
}
