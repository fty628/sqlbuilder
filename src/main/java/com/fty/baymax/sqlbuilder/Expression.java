package com.fty.baymax.sqlbuilder;

/**
 *  表达式
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public abstract class Expression {

    public String toSql(QueryBuilder builder){
        String sql = toSqlString(builder);
        builder.onAssemblyExpression(this);
        return sql;
    }

    public  boolean single(){
        return true;
    }

    public  Object value(){
        return null;
    }

    protected abstract String toSqlString(QueryBuilder builder);

}
