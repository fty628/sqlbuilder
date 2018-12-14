package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.QueryBuilder;
import com.fty.baymax.sqlbuilder.S;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LogicalExpression extends Expression {

	private final Collection<Expression> expressions;
	private final String op;

	protected LogicalExpression(Expression lhs, String op, Expression rhs) {
		this.expressions = Arrays.asList(lhs, rhs);
		this.op = op;
	}
	protected LogicalExpression(String op, Expression...expressions) {
		this.expressions = Arrays.asList(expressions);
		this.op = op;
	}

	@Override
	public String toSqlString(QueryBuilder builder) {
		return '(' + expressions.stream().map(e->e.toSql(builder)).collect(Collectors.joining(S.space(getOp()))) + ')';
	}

	public String getOp() {
		return op;
	}

	@Override
	public String toString() {
		return expressions.stream().map(Object::toString).collect(Collectors.joining(S.space(getOp())));
	}
}
