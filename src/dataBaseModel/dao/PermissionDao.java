package dataBaseModel.dao;


import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class PermissionDao implements BaseQueryDaoI {
    private static PermissionDao instance;
    private BaseQuery<Permission> baseQuery = new BaseQuery<>();


    public static PermissionDao getInstance() {
        if (instance == null)
            instance = new PermissionDao();
        return instance;
    }

    @Override
    public BaseQuery<Permission> getBaseQuery() {
        return baseQuery;
    }
}
