package org.codeman.core.db_table_create;

import lombok.Data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hdgaadd
 * Created on 2022/04/13
 *
 * @Description：  拿取Excel字段，映射成Map，方便进行DBTableCreate的创建表
 */
public class App_Mapping {
    @Data
    static abstract class Example {
        /**
         * 表字段注释
         */
        public String strOne;
        /**
         * 表字段名
         */
        public String strTwo;
    }
    /**
     * ExampleOne
     */
    static class ExampleOne extends Example {
        public ExampleOne() {
            super.strOne = "序号, 名称";
            super.strTwo = "id, name";
        }
    }
    /**
     * ExampleTwo
     */
    static class ExampleTwo extends Example {
        public ExampleTwo() {
            super.strOne = "序号, 名称";
            super.strTwo = "id, name";
        }
    }

    public static void main(String[] args) {
        // 模板方法，返回中文和添加"_"的英文的映射Map
        Map<String, String> example = example(new ExampleTwo());

        // 使用CreateTable的创建表方法
        createTable(example);
    }

    /**
     * 模板方法，返回中文和英文的映射Map
     */
    private static Map<String, String> example(Example example) {
        String first = example.getStrOne();
        String second = example.getStrTwo();
        Map<String, String> mapping = new LinkedHashMap<>();
        String[] firstArr = splitStr(first, ", "); // 以", "结尾，而不是","
        String[] secondArr = splitStr(second, ", ");

        System.out.println("原xsl的字段个数为：" + firstArr.length);
        for (int i = 0; i < firstArr.length; i++) {
            mapping.put(firstArr[i], transform(secondArr[i]));
        }
        System.out.println("mpping的个数为（必须与以上一致，否则是覆盖了）：" + mapping.size());
        return mapping;
    }

    /**
     * 使用CreateTable的创建表方法
     *
     * @param mapping
     */
    public static void createTable(Map<String, String> mapping) {
        App_TableCreate.example(mapping);
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
