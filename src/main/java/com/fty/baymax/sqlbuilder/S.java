package com.fty.baymax.sqlbuilder;

/**
 * 长量
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/13 0013
 */
public class S {

    public static final String COMMA = ",";
    public static final String SPACE = " ";


    static final String SELECT = "select";
    static final String FROM = "from";
    static final String WHERE = "where";
    static final String ORDER_BY = "order by";
    static final String GROUP_BY = "group by";

    static final String AS = "as";

    static final String AND = "and";
    static final String OR = "or";

    static final String DESC = "desc";
    static final String ASC = "asc";

    static final String JOIN_LEFT = "left join";
    static final String JOIN_RIGHT= "right join";

    public static String space(String src){
        return SPACE + src + SPACE;
    }

}
