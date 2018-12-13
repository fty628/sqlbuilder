
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;


public class EmptyExpression extends Expression {

	private final String columnName;
	private final boolean exclude;

	protected EmptyExpression(String columnName) {
		this(columnName, false);
	}

	protected EmptyExpression(String columnName, boolean exclude) {
		this.columnName = columnName;
		this.exclude = exclude;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		final String innerSelect = "(select 1 from " + builder.tableByColumnName(columnName) + " where "
				+ builder.columnSql(columnName)
				+ ")";
		return exclude ? "exists " + innerSelect : "not exists " + innerSelect;
	}
}
