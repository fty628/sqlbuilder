
package com.fty.baymax.sqlbuilder.condition;


import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.S;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class InExpression extends Expression {
	private final String columnName;
	private final Object[] values;

	protected InExpression(String columnName, Object[] values) {
		this.columnName = columnName;
		this.values = values;
	}

    protected InExpression(String columnName, Collection<?> values) {
        this.columnName = columnName;
        this.values = values.toArray();
    }

	@Override
	public boolean single() {
		return false;
	}

	@Override
	public  Object value(){
		return values;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
        return builder.columnSql(columnName) + " in ( "+ Arrays.stream(values).map(v-> S.leftSpace(S.PLACEHOLDER)).collect(Collectors.joining(S.COMMA)) +" )";
	}

	@Override
	public String toString() {
		return columnName + " in (" + Arrays.stream(values).map(Object::toString).collect(Collectors.joining(S.COMMA)) + ')';
	}

	public static void main(String[] args) {
		System.out.println(Arrays.stream(new Long[]{1111L, 2222L, 3333L, 4444L}).map(v-> S.leftSpace(S.PLACEHOLDER)).collect(Collectors.joining(S.COMMA)));
	}
}
