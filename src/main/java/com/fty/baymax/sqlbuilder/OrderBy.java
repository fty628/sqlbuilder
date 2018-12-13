package com.fty.baymax.sqlbuilder;

/**
 * 排序
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class OrderBy {

    private String column;
    private OrderByType type;

    public OrderBy(String columnName, OrderByType type) {
        this.column = columnName;
        this.type = type;
    }

    public OrderBy(String columnName) {
        this(columnName, OrderByType.ASC);
    }

    public String sql(QueryBuilder builder){
        return  type.join(builder.column(column));
    }

}
