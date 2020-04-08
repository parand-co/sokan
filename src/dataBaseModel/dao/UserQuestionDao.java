package dataBaseModel.dao;

import dataBaseModel.authentication.user.UserQuestion;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class UserQuestionDao implements BaseQueryDaoI {
    private static UserQuestionDao instance;
    private BaseQuery<UserQuestion> baseQuery = new BaseQuery<>();


    public static UserQuestionDao getInstance() {
        if (instance == null)
            instance = new UserQuestionDao();
        return instance;
    }

    @Override
    public BaseQuery<UserQuestion> getBaseQuery() {
        return baseQuery;
    }
}
