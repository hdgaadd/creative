package org.codeman.core.convert.sql_format;

import org.codeman.common.Address;

/**
 * @author hdgaadd
 * created on 2022/07/26
 *
 * @description: 自定义格式化SQL
 *
 * 设计思路: 确认select、from、where各自的范围下标 -> 确认select、from、where各自的字符串 -> 格式化
 */
public class App {

    private static final String SQL_SOURCE = Address.readFileToString(App.class, "YOUR_SQL");

    private static final StringBuilder BUILDER = new StringBuilder();

    /** 确认select、from、where各自的范围下标 **/
    // 1.select不存在，无影响
    private static final int FIRST_INDEX = 0;

    // 2.from或FROM不存在，则设置SECOND_INDEX的长度为: select内容的尾部，以使SELECT_STR剔除非select内容
    private static final int SECOND_INDEX = SQL_SOURCE.lastIndexOf("from") != -1 ? SQL_SOURCE.lastIndexOf("from") : (SQL_SOURCE.lastIndexOf("FROM") != -1 ? SQL_SOURCE.lastIndexOf("FROM") : SQL_SOURCE.substring(SQL_SOURCE.lastIndexOf(",") + 2).indexOf(" ") + SQL_SOURCE.lastIndexOf(",") + 2); // + 2: 预防','后有空格，导致index(" ")取错

    // 3.where或WHERE不存在，使WHERE_STR设置为""
    private static final int THIRD_INDEX = SQL_SOURCE.lastIndexOf("where") != -1 ? SQL_SOURCE.lastIndexOf("where") : (SQL_SOURCE.lastIndexOf("WHERE") != -1 ? SQL_SOURCE.lastIndexOf("WHERE") : SQL_SOURCE.length());

    /** 确认select、from、where各自的字符串 **/
    // 1.SELECT_STR
    private static final String SELECT_STR = SQL_SOURCE.substring(FIRST_INDEX, SECOND_INDEX);

    // 2.FROM_STR: 预防from不存在而where而存在，出现SECOND_INDEX > THIRD_INDEX，导致SQL_SOURCE.substring(SECOND_INDEX, THIRD_INDEX报错: 以上情况出现，WHERE_STR设置为""
    private static final String FROM_STR = SECOND_INDEX > THIRD_INDEX ? "" : SQL_SOURCE.substring(SECOND_INDEX, THIRD_INDEX);

    // 3.WHERE_STR
    private static final String WHERE_STR = SQL_SOURCE.substring(THIRD_INDEX);

    public static void main(String[] args) {
        handleSelect();
        handleFrom();
        handleWhere();
        System.out.println(BUILDER);
    }

    private static void handleSelect() {
        // add select
        int selectIndex = SELECT_STR.indexOf(" ") != -1 ? SELECT_STR.indexOf(" ") : 0;
        BUILDER.append(SELECT_STR, 0, selectIndex).append("\r\n");

        // add others
        String[] others = SELECT_STR.substring(selectIndex).split(",");
        for (int i = 0; i < others.length; i++) {
            BUILDER.append(others[i].replaceAll("\\s*", ""));
            if (i != others.length - 1) {
                BUILDER.append(",");
            }
            BUILDER.append("\r\n");
        }
    }

    private static void handleFrom() {
        // add from
        BUILDER.append(FROM_STR).append("\r\n");
    }

    private static void handleWhere() {
        // add where
        int whereIndex = WHERE_STR.indexOf(" ") != -1 ? SELECT_STR.indexOf(" ") : 0;
        BUILDER.append(WHERE_STR, 0, whereIndex);

        // add others
        String[] others = WHERE_STR.substring(whereIndex).split("and");
        for (int i = 0; i < others.length; i++) {
            if (i != 0) {
                BUILDER.append("and");
            }
            BUILDER.append(others[i]).append("\r\n");
        }
    }

}
