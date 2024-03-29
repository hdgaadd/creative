package org.codeman.core.generate.sql;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hdgaadd
 * created on 2022/07/30
 *
 * design: 生成中文和添加"_"的英文映射Map -> 创建表
 */
@Slf4j
public class GenerateCreateSQLV1 {
    /**
     * 表字段名
     */
    private static final String FIELD_NAME = "id\n" + "first Name";
    /**
     * 表字段注释
     */
    private static final String FIELD_COMMENT = "序号\n" + "名称";

    private static final String TABLE_NAME = "test";

    private static final String TABLE_COMMENT = "测试表";

    private static final StringBuilder BUILDER = new StringBuilder();

    public static void main(String[] args) {
        Map<String, String> mapping = generateMapping();
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
     * @return 中文和英文的映射Map
     */
    private static Map<String, String> generateMapping() {
        Map<String, String> mapping = new LinkedHashMap<>();
        String[] firstArr = splitStr(FIELD_COMMENT, "\n"); // 以", "结尾，而不是","
        String[] secondArr = splitStr(FIELD_NAME, "\n");

        log.info("原xsl的字段个数为: " + firstArr.length);
        for (int i = 0; i < firstArr.length; i++) {
            mapping.put(firstArr[i], transform(secondArr[i]));
        }
        log.info("mapping的个数为（必须与以上一致，否则是覆盖了）: " + mapping.size());
        return mapping;
    }

    /**
     * @param str
     * @param point 字符串以什么分割
     * @return
     */
    private static String[] splitStr(String str, String point) {
        return str.split(point);
    }

    /**
     * @param str
     * @return 第一个字符串转换为小写，并添加"_"
     */
    private static String transform(String str) {
        return Arrays.stream(str.split(" ")).map(String::toLowerCase).collect(Collectors.joining("_"));
    }
}
