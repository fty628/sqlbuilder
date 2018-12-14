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

    private int num;
    private int size;
    private List<Object> parameters;


    public QueryBuilder() {
        columns = new ArrayList<>();
        tables = new HashMap<>();
    }

    private QueryBuilder append(StringBuilder sb, Object o){
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

    protected QueryBuilder expression(String type, Expression ... es){
        if(expressions == null){
            expressions = new HashMap<String, List<Expression>>();
        }

        List<Expression> list = expressions.getOrDefault(type, new ArrayList<Expression>());
        list.addAll(Arrays.asList(es));
        this.expressions.put(type,  list);
        return this;
    }

    public QueryBuilder groupBy(String ... columns){
        this.groupBy = GroupBy.group(columns);
        return this;
    }

    public QueryBuilder orderBy(String columnName, OrderByType orderByType){
        return orderBy(new OrderBy(columnName, orderByType));
    }

    public QueryBuilder orderBy(OrderBy orderBy){
        if(orderByCollection == null){
            orderByCollection = new ArrayList<OrderBy>();
        }
        orderByCollection.add(orderBy);
        return this;
    }

    public QueryBuilder page(int num, int size){
        this.num = num;
        this.size = size;
        return this;
    }

    public QueryBuilder and(Expression ... es){
        return expression(S.AND, es);
    }

    public QueryBuilder or(Expression ... es){
        return expression(S.OR, es);
    }

    private String fromString(){
        StringBuilder sb = new StringBuilder();
        append(sb, S.FROM).append(sb, table.sql());
        if(joinTables != null && joinTables.size() > 0){
            append(sb, joinTables.stream().map(e->e.sql(this)).collect(Collectors.joining(S.SPACE)));
        }
        return sb.toString();
    }

    private String whereString(){
        StringBuilder sb = new StringBuilder();
        if(expressions != null && expressions.size() > 0){
            append(sb, S.WHERE);
            List<Expression> ands = expressions.get(S.AND),  ors = expressions.get(S.OR);
            if(ands != null && ands.size() > 0){
                append(sb, ands.stream().map(e->e.toSql(this)).collect(Collectors.joining(S.space(S.AND))));
            }
            if(ors != null && ors.size() > 0){
                if(ands != null && ands.size() > 0){
                    append(sb, S.OR);
                }
                append(sb, ors.stream().map(e->e.toSql(this)).collect(Collectors.joining(S.space(S.OR))));
            }
        }
        return sb.toString();
    }

    private String columnString(){
        if(columns == null || columns.size() <= 0){
            return "*";
        }else{
            return columns.stream().map(Column::sql).collect(Collectors.joining(S.space(S.COMMA)));
        }
    }

    private String groupString(){
        StringBuilder sb = new StringBuilder();
        if(groupBy != null){
            append(sb, S.GROUP_BY).append(sb, groupBy.sql(this));
        }
        return sb.toString();
    }
    private String orderString(){
        StringBuilder sb = new StringBuilder();
        if(orderByCollection != null && orderByCollection.size() > 0){
            append(sb, S.ORDER_BY).append(sb, orderByCollection.stream().map(o->o.sql(this)).collect(Collectors.joining(S.COMMA)));
        }
        return sb.toString();
    }

    public Query build(){

        StringBuilder selectCount = new StringBuilder(),
                select = new StringBuilder();

        String column = columnString(),
                from = fromString(),
                where = whereString(),
                group = groupString(),
                order = orderString();

        append(selectCount, S.SELECT).append(selectCount, "count(*)").append(selectCount, from).append(selectCount, where);
        append(select, S.SELECT).append(select, column).append(select, from).append(select, where).append(select, group).append(select, order);
        if(num > 0 && size > 0){
            append(select, S.limit(num, size));
        }
        return new Query(parameters.toArray(), select.toString(), selectCount.toString());
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
