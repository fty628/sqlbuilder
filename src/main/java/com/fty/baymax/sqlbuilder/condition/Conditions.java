
package com.fty.baymax.sqlbuilder.condition;

import com.fty.baymax.sqlbuilder.Expression;
import com.fty.baymax.sqlbuilder.S;

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
		return new NullExpression( columnName, true);
	}

	public static Expression isNotNull(String columnName) {
		return new NullExpression( columnName, false);
	}

	public static Expression eqColumn(String columnName, String otherColumnName) {
		return new ColumnExpression( columnName, "=" , otherColumnName);
	}

	public static Expression neColumn(String columnName, String otherColumnName) {
		return new ColumnExpression( columnName, "<>" , otherColumnName);
	}

	public static Expression ltColumn(String columnName, String otherColumnName) {
		return new ColumnExpression( columnName, "<" , otherColumnName);
	}

	public static Expression leColumn(String columnName, String otherColumnName) {
		return new ColumnExpression( columnName, "<=" , otherColumnName);
	}

	public static Expression gtColumn(String columnName, String otherColumnName) {
		return new ColumnExpression( columnName, ">" , otherColumnName);
	}

	public static Expression geColumn(String columnName, String otherColumnName) {
		return new ColumnExpression( columnName, ">=" , otherColumnName);
	}

	public static Expression and(Expression...exps) {
		if(exps.length == 1){
			return exps[0];
		}
		return new LogicalExpression( S.AND, exps);
	}

	public static Expression or(Expression...exps) {
		if(exps.length == 1){
			return exps[0];
		}
		return new LogicalExpression(S.OR, exps);
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
