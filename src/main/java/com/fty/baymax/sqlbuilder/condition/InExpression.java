
package com.fty.baymax.sqlbuilder.condition;


import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class InExpression extends Expression {
	private final String columnName;
	private final Collection<?> values;

	protected InExpression(String columnName, Object[] values) {
		this.columnName = columnName;
		this.values = Arrays.stream(values).collect(Collectors.toList());
	}

    protected InExpression(String columnName, Collection<?> values) {
        this.columnName = columnName;
        this.values = values;
    }

	@Override
	public  Object value(){
		return values;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
        return builder.columnSql(columnName) + " in ( ? )";
	}

	@Override
	public String toString() {
		return columnName + " in (" + values.stream().map(Object::toString).collect(Collectors.joining(",")) + ')';
	}

}
