package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.S;


public class BetweenExpression extends Expression {

	private final String columnName;
	private final Object low;
	private final Object high;

	protected BetweenExpression(String columnName, Object low, Object high) {
		this.columnName = columnName;
		this.low = low;
		this.high = high;
	}

	@Override
	public boolean single() {
		return false;
	}

	@Override
	public  Object value(){
		return new Object[]{low, high};
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return String.join(S.SPACE, builder.columnSql(columnName),  "between ? and ?" );
	}

	@Override
	public String toString() {
		return columnName + " between " + low + " and " + high;
	}

}
