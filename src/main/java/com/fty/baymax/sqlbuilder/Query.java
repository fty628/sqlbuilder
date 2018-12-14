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
    private String selectCount;
    private String select;

    public static QueryBuilder builder(){
        return new QueryBuilder();
    }

    public Query(Object[] parameters, String select, String selectCount) {
        this.parameters = parameters;
        this.selectCount = selectCount;
        this.select = select;
    }

    public Object[] parameters() {
        return parameters;
    }

    public String selectCount() {
        return selectCount;
    }

    public String select() {
        return select;
    }

    public static void main(String[] args) {
        Query query = Query.builder().select(
                        new Table("t_driver", "d")
                                .columns("driver_key", "driver_name", "driver_phone", "driver_register_time")
                ).join(
                        new JoinTable(JoinType.LEFT, "t_driver_car", "dc")
                                .on("d.driver_key", "dc.driver_key")
                                .columns("car_type","car_length","car_load_value","car_license")
                )
                .and(Conditions.eq("d.driver_key", 1161)).or(Conditions.eq("dc.car_length", 5.6)).groupBy("t_driver.driver_key").orderBy("t_driver.driver_key", OrderByType.DESC)
                .page(1, 20).build();
        System.out.println(query.selectCount);
        System.out.println(query.select);
        System.out.println(Arrays.toString(query.parameters()));
    }
}
