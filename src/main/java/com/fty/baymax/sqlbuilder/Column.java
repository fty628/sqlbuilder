package com.fty.baymax.sqlbuilder;

/**
 * 列
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class Column {

    protected String tableAlias;
    protected String columnName;

    public Column(String columnName) {
        this.columnName = columnName;
    }

    public Column(String tableAlias, String columnName) {
        this.tableAlias = tableAlias;
        this.columnName = columnName;
    }

    public Column modifyTableAlias(String tableAlias){
        return new Column(tableAlias, columnName);
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public String getColumnName() {
        return columnName;
    }

    protected String sql(){
        return tableAlias +"."+ columnName;
    }

}
