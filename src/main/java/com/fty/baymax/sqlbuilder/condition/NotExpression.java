
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;

public class NotExpression extends Expression {
	private Expression expression;

	protected NotExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return "not " + expression.toSql(builder);
	}

	@Override
	public String toString() {
		return "not " + expression.toString();
	}

}
