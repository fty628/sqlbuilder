package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;

/**
 * @TODO 类描述
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class JoinExpression extends Expression {

    private ColumnExpression expression;

    public JoinExpression(String columnName, String otherColumnName) {
        this.expression = new ColumnExpression(columnName, "=", otherColumnName);
    }

    @Override
    public String toSqlString(QueryBuilder builder) {
        return "on "+ expression.toSql(builder);
    }

    @Override
    public String toString() {
        return "on "+ expression.toString();
    }
}
