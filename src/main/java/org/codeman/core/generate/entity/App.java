package org.codeman.core.generate.entity;

/**
 * @author hdgaadd
 * created on 2022/07/02
 *
 * @description: 根据建表语句，生成实体类
 *
 * 查询建表语句: SHOW CREATE TABLE 表名
 */
public class App {
    private static final String example = "CREATE TABLE test (\n" +
            "  test1 STRING COMMENT '字段1',\n" +
            "  test2 STRING COMMENT '字段2',\n" +
            "  test3 INT COMMENT '字段3',\n" +
            "  test4 BIGINT COMMENT '字段4',\n" +
            "  test5 BIGINT COMMENT '字段5',\n" +
            "  test6 BIGINT COMMENT '字段6',\n" +
            "  test7 BIGINT COMMENT '字段7',\n" +
            "  test8 DOUBLE COMMENT '字段8',\n" +
            "  test9 DOUBLE COMMENT '字段9',\n" +
            "  test10 DOUBLE COMMENT '字段10',\n" +
            "  test11 DOUBLE COMMENT '字段11',\n" +
            ")";

    public static void main(String[] args) {

    }
}
