package org.codeman.core.generate.db;

import java.util.Map;

/**
 * @author hdgaadd
 * Created on 2022/07/30
 */
public class App {
    public static void main(String[] args) {
        // 返回中文和添加"_"的英文的映射Map
        Map<String, String> example = FieldMapping.getMapping(new FieldMapping.ExampleTwo());
        // 创建表
        TableCreate.create(example);
    }
}
