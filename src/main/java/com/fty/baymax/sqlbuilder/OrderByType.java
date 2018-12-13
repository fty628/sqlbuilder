package com.fty.baymax.sqlbuilder;

/**
 * 排序类型
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public enum OrderByType {
    DESC {
        @Override
        public String join(Column column) {
            return String.join(S.SPACE, column.sql(), S.DESC);
        }
    },

    ASC {
        @Override
        public String join(Column column) {
            return String.join(S.SPACE, column.sql(), S.ASC);
        }
    };
    public abstract String join(Column column);
}
