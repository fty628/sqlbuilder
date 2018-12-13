package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.S;

/**
 * 简单的表达式
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class SimpleExpression extends Expression {

    protected final String columnName;
    protected final Object value;
    protected final String op;

    public SimpleExpression(String columnName, String op, Object value) {
        this.columnName = columnName;
        this.value = value;
        this.op = op;
    }

    @Override
    public  Object value(){
        return value;
    }

    @Override
    public String toSqlString(QueryBuilder builder) {
        return builder.columnSql(columnName) + S.space(op) + "?";
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }

    public String getOp() {
        return op;
    }
}
