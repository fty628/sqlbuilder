
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.S;

public class NullExpression extends Expression {

	private final String columnName;
	private final boolean isNull;

	protected NullExpression(String columnName) {
		this(columnName, false);
	}

	protected NullExpression(String columnName, boolean isNull) {
		this.columnName = columnName;
		this.isNull = isNull;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return builder.columnSql(columnName) + (isNull ? S.leftSpace(S.NULL) : S.leftSpace(S.NOTNULL));
	}

	@Override
	public String toString() {
		return columnName + (isNull ? S.leftSpace(S.NULL) : S.leftSpace(S.NOTNULL));
	}
}
