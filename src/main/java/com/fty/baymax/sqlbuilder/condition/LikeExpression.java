
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;


public class LikeExpression extends Expression {

	private final String columnName;
	private final Object value;
	private final Character escapeChar;

	protected LikeExpression( String columnName, String value, Character escapeChar) {
		this.columnName = columnName;
		this.value = value;
		this.escapeChar = escapeChar;
	}

	protected LikeExpression(String columnName, String value) {
		this.columnName = columnName;
		this.value = value;
		this.escapeChar = null;
	}

	protected LikeExpression(String columnName, String value, LikeMatch matchMode) {
		this( columnName, value, matchMode, null );
	}

	protected LikeExpression( String propertyName, String value, LikeMatch matchMode, Character escapeChar) {
		this( propertyName, matchMode.toMatchString( value ), escapeChar);
	}

	@Override
	public  Object value(){
		return value;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return builder.columnSql(columnName) + " like ?" + (escapeChar == null ? "" : " escape \'" + escapeChar + "\'");
	}

	@Override
	public String toString() {
		return columnName + " like " + value;
	}
}
