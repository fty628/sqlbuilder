package com.fty.baymax.sqlbuilder;

/**
 * 长量
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/13 0013
 */
public class S {

    public static final String PLACEHOLDER = "?";
    public static final String COMMA = ",";
    public static final String SPACE = " ";


    public static final String SELECT = "select";
    public static final String FROM = "from";
    public static final String WHERE = "where";
    public static final String ORDER_BY = "order by";
    public static final String GROUP_BY = "group by";

    public static final String AS = "as";

    public static final String AND = "and";
    public static final String OR = "or";

    public static final String DESC = "desc";
    public static final String ASC = "asc";

    public static final String JOIN_LEFT = "left join";
    public static final String JOIN_RIGHT= "right join";

    public static final String ON= "on";

    public static final String NULL = "is null";
    public static final String NOTNULL = "is not null";

    public static final String LIMIT = "limit";

    public static String space(Object src){
        return SPACE + src + SPACE;
    }
    public static String leftSpace(Object src){
        return SPACE + src;
    }
    public static String rightSpace(Object src){
        return src + SPACE;
    }

    public static String limit(int s, int e){
        return String.join(S.SPACE, LIMIT, String.valueOf(s), S.COMMA, String.valueOf(e));
    }
}
