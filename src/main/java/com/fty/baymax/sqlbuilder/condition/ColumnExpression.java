
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;

public class ColumnExpression extends Expression {

	private final String columnName;
	private final String otherColumnName;
	private final String op;

	protected ColumnExpression(String columnName, String op, String otherColumnName) {
		this.columnName = columnName;
		this.otherColumnName = otherColumnName;
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return builder.columnSql(columnName) +" "+ getOp() +" "+ builder.columnSql(otherColumnName);
	}

	@Override
	public String toString() {
		return columnName + getOp() + otherColumnName;
	}

}
