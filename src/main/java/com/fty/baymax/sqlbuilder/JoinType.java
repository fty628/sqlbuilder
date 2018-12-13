package com.fty.baymax.sqlbuilder;

/**
 * 联接类型
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public enum JoinType {

    LEFT {
        @Override
        public String join(Table table) {
            return String.join(S.SPACE, S.JOIN_LEFT, table.sql());
        }
    },

    RIGHT {
        @Override
        public String join(Table table) {
            return String.join(S.SPACE, S.JOIN_RIGHT, table.sql());
        }
    };
    public abstract String join(Table table);
}
