package org.codeman.core.db_table_create;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hdgaadd
 * Created on 2022/04/13
 *
 * @Description： 根据由字段名及字段类型的Map，快捷创建表语句
 */
public class App_TableCreate {
    /**
     * SQL参考模板
     */
    private static final String example = "CREATE TABLE `test`  (\n" +
            "  `id` int(11) NOT NULL COMMENT '序号',\n" +
            "  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试字段',\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试表' ROW_FORMAT = Dynamic;";

    private static final String tableName = "test";

    private static final String commentTable = "测试表";

    /**
     * 测试Map模板
     */
    private static final Map<String, String> exampleMap = new LinkedHashMap<String, String>(){{ put("序号", "id"); put("名称", "name");}};

    public static void main(String[] args) {
        example(exampleMap);
    }

    /**
     * 模板方法
     *
     * @param mapping
     */
    public static void example(Map<String, String> mapping) { // 要被其他类使用，不能使用static
        StringBuilder sb = new StringBuilder();
        // 1.头
        sb.append("CREATE TABLE `" + tableName + "`  (\n");

        // 2.中
        int index = 0;
        String idName = "id";
        for (Map.Entry<String, String> map : mapping.entrySet()) {
            String field = map.getValue();
            String comment = map.getKey();
            if (index == 0) {
                field = idName;
                sb.append("  `" + field + "` int(11) NOT NULL AUTO_INCREMENT COMMENT '" + comment + "',\n");
            } else {
                sb.append("  `" + field + "` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '" + comment + "',\n");
            }
            index++;
        }

        // 3.尾
        sb.append("  PRIMARY KEY (`" + idName + "`) USING BTREE\n) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '" + commentTable + "' ROW_FORMAT = Dynamic;");

        System.out.println(sb.toString());
    }
}
