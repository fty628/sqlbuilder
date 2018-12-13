package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.S;

/**
 * 函数表达式
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class AggregateExperssion extends SimpleExpression  {

    protected String aggregateName;

    public AggregateExperssion(String aggregateName, String columnName, String op, Object value) {
        super(columnName, op, value);
        this.aggregateName = aggregateName;
    }

    @Override
    public String toSql(QueryBuilder builder) {
        return aggregateName + "("+ builder.columnSql(columnName) +")" + S.space(op) + "?";
    }

    @Override
    public boolean single() {
        return super.single();
    }
}
