package com.fty.baymax.sqlbuilder;

import java.util.Optional;

/**
 * @TODO 类描述
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class Table {

    protected String name;
    protected String alias;
    protected Column[] columns;


    public Table(String name) {
        this.name = name;
        this.alias = "_"+name;
    }
    public Table(String name, String alias) {
        this.name = name;
        this.alias = Optional.ofNullable(alias).orElse(name);
    }
    public Table columns(String...columns){
        this.columns = new Column[columns.length];
        for (int i = 0; i < columns.length ; i++) {
            this.columns[i] = new Column(alias, columns[i]);
        }
        return this;
    }


    public String sql(){
        return String.join(S.SPACE, name, S.AS, alias);
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
}
