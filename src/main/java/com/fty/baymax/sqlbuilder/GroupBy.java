package com.fty.baymax.sqlbuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 分组
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class GroupBy {

    private String[] columns;

    public static GroupBy group(String ... columns){
        return new GroupBy(columns);
    }

    public GroupBy(String[] columns) {
        this.columns = columns;
    }

    public String sql(QueryBuilder builder){
        return Arrays.stream(columns).map(builder::columnSql).collect(Collectors.joining(S.COMMA));
    }
}
