package org.codeman.core.convert.sql.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hdgaadd
 * created on 2023/04/10
 *
 * description: 为SQL添加别名
 */
public class SQLFormat {

    public static String formatSQL(String sql) {
//        String[] keywords = {"LEFT JOIN", "RIGHT JOIN", "SELECT", "FROM", "WHERE", "ON"};
//        for (String keyword : keywords) {
//            sql = sql.replaceAll("(?i)" + keyword, keyword.toUpperCase()); // replace all occurrences of the keyword with its uppercase version
//        }
        String formattedSQL = sql.replace("select ", "select\n    ")
                .replace(", ", ",\n    ")
                .replace(" from", "\nfrom")
                .replace(" where", "\nwhere");
        formattedSQL = formattedSQL.replace("SELECT ", "select\n    ")
                .replace(", ", ",\n    ")
                .replace(" FROM", "\nfrom")
                .replace(" WHERE", "\nwhere");
        return formattedSQL;
    }

    public static String addCamelCaseAlias(String sql) {
        String[] columns = sql.replaceAll("(?i)SELECT\\s+", "")
                .replaceAll("\\s+(?i)FROM.*", "")
                .split(", ");
        StringBuilder sb = new StringBuilder(sql);
        for (String column : columns) {
            Pattern pattern = Pattern.compile("\\b(" + column + ")\\b");
            Matcher matcher = pattern.matcher(sb);
            if (matcher.find()) {
                String[] words = column.split("_");
                StringBuilder alias = new StringBuilder(words[0]);
                for (int i = 1; i < words.length; i++) {
                    alias.append(words[i].substring(0, 1).toUpperCase())
                            .append(words[i].substring(1));
                }
                String noPontAlias = alias.toString().contains(".") ? alias.toString().split("\\.")[1] : alias.toString();
                sb.replace(matcher.start(), matcher.end(), column + " " + "`" + noPontAlias + "`");
            }
        }
        return sb.toString();
    }

    public static String removeAlias(String sql) {
        String regex = " `\\w+`";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String alias = matcher.group();
            sql = sql.replace(alias, "");
        }
        return sql;
    }
}

