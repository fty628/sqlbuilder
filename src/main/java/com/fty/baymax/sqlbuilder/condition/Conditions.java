
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.Expression;

import java.util.Collection;
import java.util.Optional;

public class Conditions {

	public static SimpleExpression eq(String columnName, Object value) {
		return new SimpleExpression( columnName, "=", value);
	}

	public static SimpleExpression ne(String columnName, Object value) {
		return new SimpleExpression( columnName, "<>" , value);
	}

	public static Expression eqOrIsNull(String columnName, Object value) {
		return value == null
				? isNull( columnName )
				: eq( columnName, value );
	}

	public static Expression neOrIsNotNull(String columnName, Object value) {
		return value == null
				? isNotNull( columnName )
				: ne( columnName, value );
	}

	public static Expression like(String columnName, Object value) {
		return like( columnName, value,  LikeMatch.EXACT );
	}

	public static Expression like(String columnName, Object value, LikeMatch matchMode) {
		return new LikeExpression( columnName, Optional.ofNullable(value).map(Object::toString).orElseThrow(()-> new IllegalArgumentException( "like value cannot be null")), matchMode);
	}

	public static Expression gt(String columnName, Object value) {
		return new SimpleExpression( columnName, ">", value );
	}

	public static Expression lt(String columnName, Object value) {
		return new SimpleExpression( columnName, "<" , value);
	}

	public static Expression le(String columnName, Object value) {
		return new SimpleExpression( columnName, "<=" , value);
	}

	public static Expression ge(String columnName, Object value) {
		return new SimpleExpression( columnName, ">=" , value);
	}

	public static Expression between(String columnName, Object low, Object high) {
		return new BetweenExpression( columnName, low, high );
	}

	public static Expression in(String columnName, Object... values) {
		return new InExpression( columnName, values );
	}

	public static Expression in(String columnName, Collection values) {
		return new InExpression( columnName, values.toArray() );
	}

	public static Expression isNull(String columnName) {
		return new SimpleExpression( columnName, "is", "null" );
	}

	public static Expression isNotNull(String columnName) {
		return new SimpleExpression( columnName, "is not", "null" );
	}

	public static Expression eqProperty(String columnName, String otherColumnName) {
		return new PropertyExpression( columnName, "=" , otherColumnName);
	}

	public static Expression neProperty(String columnName, String otherColumnName) {
		return new PropertyExpression( columnName, "<>" , otherColumnName);
	}

	public static Expression ltProperty(String columnName, String otherColumnName) {
		return new PropertyExpression( columnName, "<" , otherColumnName);
	}

	public static Expression leProperty(String columnName, String otherColumnName) {
		return new PropertyExpression( columnName, "<=" , otherColumnName);
	}

	public static Expression gtProperty(String columnName, String otherColumnName) {
		return new PropertyExpression( columnName, ">" , otherColumnName);
	}

	public static Expression geProperty(String columnName, String otherColumnName) {
		return new PropertyExpression( columnName, ">=" , otherColumnName);
	}

	public static Expression and(Expression lhs, Expression rhs) {
		return new LogicalExpression(lhs, "and", rhs);
	}

	public static Expression or(Expression lhs, Expression rhs) {
		return new LogicalExpression( lhs, "or" , rhs);
	}

	public static Expression not(Expression expression) {
		return new NotExpression( expression );
	}

	public static Expression isEmpty(String columnName) {
		return new EmptyExpression( columnName );
	}

	public static Expression isNotEmpty(String columnName) {
		return new EmptyExpression( columnName, false );
	}

}