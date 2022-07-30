package org.codeman.core.generate.db;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hdgaadd
 * Created on 2022/04/13
 *
 * @Description： 根据由字段名及字段类型的Map，快捷创建表语句
 */
public class TableCreate {
    /**
     * SQL参考模板
     */
    private static final String SQL_EXAMPLE = "CREATE TABLE `test`  (\n" +
            "  `id` int(11) NOT NULL COMMENT '序号',\n" +
            "  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试字段',\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试表' ROW_FORMAT = Dynamic;";

    private static final String TABLE_NAME = "test";

    private static final String TABLE_COMMENT = "测试表";

    /**
     * 测试Map模板
     */
    private static final Map<String, String> EXAMPLE_MAP = new LinkedHashMap<String, String>(){{ put("序号", "id"); put("名称", "name");}};

    public static void main(String[] args) {
        create(EXAMPLE_MAP);
    }

    /**
     * 模板方法
     *
     * @param mapping
     */
    public static void create(Map<String, String> mapping) {
        StringBuilder sb = new StringBuilder();
        // 1.头
        sb.append("CREATE TABLE `" + TABLE_NAME + "`  (\n");

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
        sb.append("  PRIMARY KEY (`" + idName + "`) USING BTREE\n) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '" + TABLE_COMMENT + "' ROW_FORMAT = Dynamic;");

        System.out.println(sb.toString());
    }
}
