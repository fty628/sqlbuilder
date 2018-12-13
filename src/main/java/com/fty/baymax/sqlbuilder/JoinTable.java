package com.fty.baymax.sqlbuilder;

import com.fty.baymax.sqlbuilder.condition.JoinExpression;

import java.util.Optional;

/**
 * @TODO 类描述
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class JoinTable {

    protected JoinType joinType;
    protected Table table;

    protected Expression expression;

    public JoinTable(JoinType joinType, String name, String alias) {
        this(joinType, new Table(name, alias));
    }

    public JoinTable(JoinType joinType, Table table) {
        this.joinType = Optional.ofNullable(joinType).orElse(JoinType.LEFT);
        this.table = table;
    }

    public JoinTable on(String columnName, String otherColumnName){
        expression = new JoinExpression(columnName, otherColumnName);
        return this;
    }
    public JoinTable columns(String...columns){
        this.table.columns(columns);
        return this;
    }

    public String sql(QueryBuilder builder){
        return String.join(S.SPACE, joinType.join(table), expression.toSqlString(builder));
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public Table getTable() {
        return table;
    }
}
