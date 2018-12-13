package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.S;

public class LogicalExpression extends Expression {

	private final Expression lhs;
	private final Expression rhs;
	private final String op;

	protected LogicalExpression(Expression lhs, String op, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return '(' + lhs.toSql(builder) + S.space(getOp()) + rhs.toSql(builder) + ')';
	}

	public String getOp() {
		return op;
	}

	@Override
	public String toString() {
		return lhs.toString() + ' ' + getOp() + ' ' + rhs.toString();
	}
}
