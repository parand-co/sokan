package dataBaseModel.dao;


import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.query.BaseQuery;
import dataBaseModel.query.BaseQueryDaoI;

public class SubPermissionDao implements BaseQueryDaoI {
    private static SubPermissionDao instance;
    private BaseQuery<SubPermission> baseQuery = new BaseQuery<>();


    public static SubPermissionDao getInstance() {
        if (instance == null)
            instance = new SubPermissionDao();
        return instance;
    }

    @Override
    public BaseQuery<SubPermission> getBaseQuery() {
        return baseQuery;
    }
}
