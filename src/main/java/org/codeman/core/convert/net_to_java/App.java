package org.codeman.core.convert.net_to_java;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hdgaadd
 * created on 2022/06/25
 *
 * @description: .NET -> Java
 *
 * 设计思路: 去除不期望的 -> 缓存字段注释 -> 内容替换、添加@JsonProperty、添加缓存字段注释、添加变量声明
 */
@Slf4j
public class App {
    /**
     * 不期望出现的
     */
    private static final List<String> UNEXPECTED = new ArrayList<String>() {{
        add("/// <summary>");
        add("///<summary>");
        add("/// </summary>");
        add("///</summary>");
        add("[Serializable]");
        add("[SqlTable(dbEnum.QLWL)]");
        add("[SqlField]");
        add("using");
        add("namespace");
    }};
    /**
     * 内容替换Map
     */
    private static final Map<String, String> REPLACE_MAP = new HashMap<String, String>(){{
        put("int", "Integer");
        put("string", "String");
        put("public", "    private");
        put("DateTime", "Date");
        put("bool", "Boolean");
        put("string[]", "String[]");
    }};
    /**
     * a bean called StringBuilder
     */
    private static final StringBuilder BASE_BUILDER = new StringBuilder();
    /**
     * 生成的变量个数
     */
    private static int generateKeyword = 0;
    /**
     * 实际的变量个数
     */
    private static int realKeyword = 0;

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = Address.getReader(App.class)) {
            String curLine;
            // 缓存字段注释，让@JsonProperty在注释前面
            StringBuilder curFieldCommentBuilder = new StringBuilder();
            while ((curLine = bufferedReader.readLine()) != null) {
                // 1.去除不期望的
                boolean isUnexpected = handleUnexpected(curLine);
                // 2.缓存字段注释
                boolean isFieldName = cacheFieldName(curLine, isUnexpected, curFieldCommentBuilder);
                // 3.内容替换、添加@JsonProperty、添加缓存字段注释、添加变量声明
                handleReplaceContent(curLine, isUnexpected, isFieldName, curFieldCommentBuilder);
            }
            System.out.println(BASE_BUILDER);
        }
        // 变量个数对比
        documentScanning();
        log.info("以';'计数，生成的变量个数{}；以'get和set'计数，实际的变量个数: {}", generateKeyword, realKeyword);
    }

    /**
     * 扫描，对比
     *
     * @throws IOException
     */
    private static void documentScanning() throws IOException{
        try (BufferedReader bufferedReader = Address.getReader(App.class)) {
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                if (curLine.contains("get") && curLine.contains("set")) realKeyword++;
            }
        }
    }

    /**
     * 1.去除不期望的
     *
     * @param curLine
     * @return 是否该curLine执行了该操作: 去除不期望的
     */
    private static boolean handleUnexpected(String curLine) {
        // 执行第1步
        for (String notLikeStr : UNEXPECTED) {
            if (curLine.contains(notLikeStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2.缓存字段注释
     *
     * @param curLine
     * @param isUnexpected
     * @param curFieldNameBuilder
     * @return 是否该curLine执行了该操作: 缓存缓存字段注释
     */
    private static boolean cacheFieldName(String curLine, boolean isUnexpected, StringBuilder curFieldNameBuilder) {
        // 第1步不执行，才执行2
        if (!isUnexpected) {
            if (curLine.contains("///") || curLine.contains("/// ")) {
                if (curFieldNameBuilder.length() != 0) curFieldNameBuilder.delete(0, curFieldNameBuilder.length()); // 去除第一行实体类注解
                curFieldNameBuilder.append("    @ApiModelProperty(value = \"").append(curLine.substring(curLine.indexOf("/") + 4)).append("\")");
                return true;
            }
        }
        return false;
    }

    /**
     * 3.内容替换，添加@JsonProperty，添加缓存字段注释，添加变量声明
     *
     * @param curLine
     * @param isUnexpected
     * @param isFieldName
     * @param curFieldNameBuilder
     */
    private static void handleReplaceContent(String curLine, boolean isUnexpected, boolean isFieldName, StringBuilder curFieldNameBuilder) {
        // 该curBuilder可以让@JsonProperty添加、缓存字段注释添加，在字段声明前
        StringBuilder curBuilder = new StringBuilder();
        // 第1、2步不执行，才执行3
        if (!isUnexpected && !isFieldName) {
            // 只需要替换两个
            int replaceCount = 0;

            for (String lineUnit : curLine.split(" ")) {
                if (replaceCount == 1 && lineUnit.equals("class")) {
                    curBuilder.delete(0, curBuilder.length());
                    break;
                }
                // 替换了2个，紧跟着即是变量名
                if (replaceCount == 2) {
                    // 添加@JsonProperty，添加缓存字段注释，添加变量声明
                    curBuilder.append(handleFieldComment(lineUnit, curFieldNameBuilder));
                    // 添加变量名后，添加';'
                    curBuilder.append(";");
                    generateKeyword++;
                    break;
                }
                // 内容替换
                for (String key : REPLACE_MAP.keySet()) {
                    if (lineUnit.equals(key)) {
                        curBuilder.append(REPLACE_MAP.get(key)).append(" ");
                        replaceCount++;
                        break;
                    }
                }
            }
            BASE_BUILDER.append(curBuilder).append("\r\n");
        }
    }

    /**
     * 1.若.NET的变量首字母为大写，添加@JsonProperty
     * 2.添加缓存字段注释
     * 3.返回驼峰式变量名，添加变量声明
     *
     * @param lineUnit
     * @param curFieldNameBuilder
     * @return
     */
    private static String handleFieldComment(String lineUnit, StringBuilder curFieldNameBuilder) {
        char[] lineArr = lineUnit.toCharArray();
        // 是否首字符大写
        boolean isUpperCase = lineArr[0] < 'a';
        String result;
        if (!isUpperCase) {
            result = lineUnit;
        } else {
            // 添加@JsonProperty
            BASE_BUILDER.append("    @JsonProperty(\"").append(lineUnit).append("\")").append("\r\n");
            // 返回驼峰式变量名
            lineArr[0] = Character.toLowerCase(lineArr[0]);
            result =  String.valueOf(lineArr);
        }

        // 添加缓存字段注释
        if (curFieldNameBuilder.length() != 0) BASE_BUILDER.append(curFieldNameBuilder).append("\r\n");
        // 清空缓存字段注释，不影响下一行
        curFieldNameBuilder.delete(0, curFieldNameBuilder.length());
        return result;
    }

}
