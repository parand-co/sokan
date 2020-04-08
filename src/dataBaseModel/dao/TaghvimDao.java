package dataBaseModel.dao;

import dataBaseModel.baseTable.Taghvim;
import dataBaseModel.query.BaseQuery;

public class TaghvimDao extends BaseQuery<Taghvim> {
    private static TaghvimDao ourInstance = new TaghvimDao();

    public static TaghvimDao getInstance() {
        return ourInstance;
    }

    private TaghvimDao() {
    }
}
