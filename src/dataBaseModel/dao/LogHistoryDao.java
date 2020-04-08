package dataBaseModel.dao;


import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.baseTable.LogHistory;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class LogHistoryDao implements BaseQueryDaoI {
    private static LogHistoryDao instance;
    private BaseQuery<LogHistory> baseQuery = new BaseQuery<>();


    public static LogHistoryDao getInstance() {
        if (instance == null)
            instance = new LogHistoryDao();
        return instance;
    }

    @Override
    public BaseQuery<LogHistory> getBaseQuery() {
        return baseQuery;
    }
}
