package org.codeman.core.generate.db;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hdgaadd
 * Created on 2022/07/30
 *
 * 设计: 生成中文和添加"_"的英文映射Map -> 创建表
 */
@Slf4j
public class App {
    @Data
    static class Example {
        /**
         * 表字段注释
         */
        public String strOne = "序号, 名称";
        /**
         * 表字段名
         */
        public String strTwo = "id, name";
    }
    /**
     * SQL参考模板
     */
    private static final String SQL_EXAMPLE = "CREATE TABLE `test`  (\n" +
            "  `id` int(11) NOT NULL COMMENT '序号',\n" +
            "  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试字段',\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试表' ROW_FORMAT = Dynamic;";
    /**
     * 表名
     */
    private static final String TABLE_NAME = "test";
    /**
     * 表注释
     */
    private static final String TABLE_COMMENT = "测试表";
    /**
     * a bean called BUILDER
     */
    private static final StringBuilder BUILDER = new StringBuilder();

    public static void main(String[] args) {
        // 生成中文和添加"_"的英文映射Map
        Map<String, String> mapping = generateMapping(new Example());
        // 创建表
        tableCreate(mapping);
        System.out.println(BUILDER);
    }

    private static void tableCreate(Map<String, String> mapping) {
        // 1.头
        BUILDER.append("CREATE TABLE `" + TABLE_NAME + "`  (\n");

        // 2.中
        int index = 0;
        String idName = "id";
        for (Map.Entry<String, String> map : mapping.entrySet()) {
            String field = map.getValue();
            String comment = map.getKey();
            if (index == 0) {
                field = idName;
                BUILDER.append("  `" + field + "` int(11) NOT NULL AUTO_INCREMENT COMMENT '" + comment + "',\n");
            } else {
                BUILDER.append("  `" + field + "` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '" + comment + "',\n");
            }
            index++;
        }

        // 3.尾
        BUILDER.append("  PRIMARY KEY (`" + idName + "`) USING BTREE\n) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '" + TABLE_COMMENT + "' ROW_FORMAT = Dynamic;");
    }

    /**
     * @param example
     * @return 中文和英文的映射Map
     */
    private static Map<String, String> generateMapping(Example example) {
        String first = example.getStrOne();
        String second = example.getStrTwo();
        Map<String, String> mapping = new LinkedHashMap<>();
        String[] firstArr = splitStr(first, ", "); // 以", "结尾，而不是","
        String[] secondArr = splitStr(second, ", ");

        log.info("原xsl的字段个数为: " + firstArr.length);
        for (int i = 0; i < firstArr.length; i++) {
            mapping.put(firstArr[i], transform(secondArr[i]));
        }
        log.info("mapping的个数为（必须与以上一致，否则是覆盖了）: " + mapping.size());
        return mapping;
    }

    /**
     * 字符串以什么分割
     *
     * @param str
     * @param point
     * @return
     */
    private static String[] splitStr(String str, String point) {
        return str.split(point);
    }

    /**
     * 第一个字符串转换为小写，并添加"_"
     *
     * @param str
     * @return
     */
    private static String transform(String str) {
        StringBuilder sb = new StringBuilder();
        String[] arr = str.split(" ");
        AtomicInteger index = new AtomicInteger(0);
        Arrays.stream(arr).forEach(item -> {
            if (index.get() == 0) {
                sb.append(item.toLowerCase());
                index.getAndAdd(1);
            } else {
                sb.append("_");
                sb.append(item.toLowerCase());
            }
        });
        return sb.toString();
    }
}
