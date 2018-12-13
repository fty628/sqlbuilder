package com.fty.baymax.sqlbuilder;


import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询建造者
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class QueryBuilder {

    private static final String PREFIX_NAME = "NAME-";
    private static final String PREFIX_ALIAS = "ALIAS-";

    private Table table;
    private Map<String, Table> tables;
    private Collection<JoinTable> joinTables;
    private Collection<Column> columns;
    private Map<String, List<Expression>> expressions;

    private Collection<OrderBy> orderByCollection;

    private GroupBy groupBy;


    private List<Object> parameters;

    private StringBuilder sb;

    public QueryBuilder() {
        columns = new ArrayList<>();
        tables = new HashMap<>();
        sb= new StringBuilder();
    }

    private QueryBuilder append(Object o){
        if(sb.length() > 0){
            sb.append(S.SPACE);
        }
        sb.append(o.toString());
        return this;
    }

    public QueryBuilder select(String tableName){
        return select(new Table(tableName));
    }
    public QueryBuilder select(String tableName, String alias){
        return select(new Table(tableName, alias));
    }
    public QueryBuilder select(Table table){
        this.table = table;
        return registerTable(table).columns(table.columns);
    }

    public QueryBuilder columns(Column...clos){
        if(clos != null && clos.length > 0){
            columns.addAll(Arrays.asList(clos));
        }
        return this;
    }

    public QueryBuilder columns(String...clos){
        if(clos != null && clos.length > 0){
            for (String colName : clos) {
                columns.add(column(colName));
            }
        }
        return this;
    }

    public QueryBuilder join(JoinTable joinTable){
        if(joinTables == null){
            joinTables = new ArrayList<JoinTable>();
        }
        joinTables.add(joinTable);
        return registerTable(joinTable.table).columns(joinTable.table.columns);
    }

    public QueryBuilder where(){
        if(expressions == null){
            expressions = new HashMap<String, List<Expression>>();
        }
        return this;
    }
    protected QueryBuilder expression(String type, Expression ... es){
        List<Expression> list = expressions.getOrDefault(type, new ArrayList<Expression>());
        list.addAll(Arrays.asList(es));
        this.expressions.put(type,  list);
        return this;
    }

    protected QueryBuilder groupBy(String ... columns){
        this.groupBy = GroupBy.group(columns);
        return this;
    }

    protected QueryBuilder orderBy(String columnName, OrderByType orderByType){
        return orderBy(new OrderBy(columnName, orderByType));
    }

    protected QueryBuilder orderBy(OrderBy orderBy){
        if(orderByCollection == null){
            orderByCollection = new ArrayList<OrderBy>();
        }
        orderByCollection.add(orderBy);
        return this;
    }

    public QueryBuilder and(Expression ... es){
        return expression(S.AND, es);
    }

    public QueryBuilder or(Expression ... es){
        return expression(S.OR, es);
    }

    private QueryBuilder assembly(){
        append(S.SELECT);
        if(columns == null || columns.size() <= 0){
            append("*");
        }else{
            append(columns.stream().map(Column::sql).collect(Collectors.joining(S.space(S.COMMA))));
        }
        append(S.FROM).append(table.sql());
        if(joinTables != null && joinTables.size() > 0){
            append(joinTables.stream().map(e->e.sql(this)).collect(Collectors.joining(S.SPACE)));
        }
        if(expressions != null && expressions.size() > 0){
            append(S.WHERE);
            List<Expression> ands = expressions.get(S.AND),  ors = expressions.get(S.OR);
            if(ands != null && ands.size() > 0){
                append(ands.stream().map(e->e.toSql(this)).collect(Collectors.joining(S.space(S.AND))));
            }
            if(ors != null && ors.size() > 0){
                if(ands != null && ands.size() > 0){
                    append(S.OR);
                }
                append(ors.stream().map(e->e.toSql(this)).collect(Collectors.joining(S.space(S.OR))));
            }
        }
        if(groupBy != null){
            append(S.GROUP_BY).append(groupBy.sql(this));
        }
        if(orderByCollection != null && orderByCollection.size() > 0){
            append(S.ORDER_BY).append(orderByCollection.stream().map(o->o.sql(this)).collect(Collectors.joining(S.COMMA)));
        }
        return this;
    }

    protected void onAssemblyExpression(Expression expression){
        Object object = expression.value();
        if(object != null){
            if(parameters == null){
                parameters = new ArrayList<Object>();
            }
            if(!expression.single() && object.getClass().isArray()){
                parameters.addAll(Arrays.asList((Object[]) object));
            }else{
                parameters.add(object);
            }
        }
    }

    public Query build(){
        this.assembly();
        return new Query(sb.toString(), parameters.toArray());
    }

    private QueryBuilder registerTable(Table table){
        if(tables == null){
            tables = new HashMap<String, Table>();
        }
        tables.put(PREFIX_NAME + table.getName(), table);
        tables.put(PREFIX_ALIAS+ table.getAlias(), table);
        return this;
    }

    private boolean isEmpty(String src){
        return src == null || src.length() <= 0;
    }

    public Column column(String columnName){
        Column column = analyze(columnName);
        Table table = Optional.ofNullable(column.tableAlias).map(this::tableByName).orElse(this.table);
        if(!table.alias.equals(column.tableAlias)){
            return column.modifyTableAlias(table.alias);
        }
        return column;
    }

    public String columnSql(String columnName){
        return Optional.ofNullable(column(columnName)).map(Column::sql).orElseThrow(()-> new NullPointerException("没找到对应的列[" + columnName+ "]"));
    }

    public Table tableByColumnName(String columnName){
        return tableByName(columnName.substring(0, columnName.indexOf('.')));
    }

    private Table tableByName(String name){
        if(!isEmpty(name)){
            return Optional.ofNullable(tables.getOrDefault(PREFIX_ALIAS + name, tables.get(PREFIX_NAME + name))).orElse(this.table);
        }
        return this.table;
    }

    private Column analyze(String columnName){
        String[] array = columnName.split("\\.");
        if(array.length > 1){
            return new Column(array[0], array[1]);
        }
        return new Column(array[0]);
    }

}
