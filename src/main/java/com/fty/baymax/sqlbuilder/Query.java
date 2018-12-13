package com.fty.baymax.sqlbuilder;

import com.fty.baymax.sqlbuilder.condition.Conditions;

import java.util.Arrays;

/**
 * 查询Qwert12345!@#$%
 * <p>
 * @developer Create by <a href="mailto:fty628@sina.com">dingxf</a> at 2018/12/12 0012
 */
public class Query {

    private Object[] parameters;

    private String selectSql;

    protected Query(String selectSql, Object[] parameters){
        this.parameters = parameters;
        this.selectSql = selectSql;
    }

    public static QueryBuilder builder(){
        return new QueryBuilder();
    }

    public Object[] parameters() {
        return parameters;
    }

    public String sql() {
        return selectSql;
    }


    public static void main(String[] args) {
        Query query = Query.builder().select(
                        new Table("t_driver", "d")
                                .columns("driver_key", "driver_name", "driver_phone", "driver_register_time")
                ).join(
                        new JoinTable(JoinType.LEFT, "t_driver_car", "dc")
                                .on("d.driver_key", "dc.driver_key")
                                .columns("car_type","car_length","car_load_value","car_license")
                ).
                where().and(Conditions.eq("d.driver_key", 1161)).or(Conditions.eq("dc.car_length", 5.6)).groupBy("t_driver.driver_key").orderBy("t_driver.driver_key", OrderByType.DESC)
                .build();
        System.out.println(query.sql());
        System.out.println(Arrays.toString(query.parameters()));
    }
}
