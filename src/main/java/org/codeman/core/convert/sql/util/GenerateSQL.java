package org.codeman.core.convert.sql.util;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdgaadd
 * created on 2023/04/10
 *
 * description: 生成SQL语句
 */
public class GenerateSQL {

    public static void generateSelectAndInsertSQL(Connection conn, String table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSetMetaData rsmd = stmt.executeQuery("SELECT * FROM " + table).getMetaData();

        select(table, rsmd);
        insert(table, rsmd);
        stmt.close();
    }

    public static void generateCreateSQL(Connection conn, String table) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeQuery("SHOW CREATE TABLE " + table).getMetaData();

        // 获取表的创建语句
        String createTableSql = "";
        while (stmt.getResultSet().next()) {
            createTableSql = stmt.getResultSet().getString("Create Table");
        }
        System.out.println(createTableSql);
        stmt.close();
    }


    private static void select(String table, ResultSetMetaData rsmd) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//            if (rsmd.getColumnName(i).equals("id")) continue;
            columnNames.add(rsmd.getColumnName(i));
        }
        String SQL = "SELECT " + String.join(", ", columnNames) + " FROM " + table;
        System.out.println(SQLFormat.formatSQL(SQLFormat.addCamelCaseAlias(SQL)));
        System.out.println();
    }

    private static void insert(String table, ResultSetMetaData rsmd) throws SQLException {
        int columnCount = rsmd.getColumnCount();
        String sql = "insert into `" + table + "` (";
        for (int i = 1; i <= columnCount; i++) {
//            if (rsmd.getColumnName(i).equals("id")) continue;
            sql += "`" + rsmd.getColumnName(i) + "`";
            if (i < columnCount) {
                sql += ", ";
            }
        }
        sql += ") " + "\n" + "values (";
        for (int i = 1; i <= columnCount; i++) {
//            if (rsmd.getColumnName(i).equals("id")) continue;
            String camelCase = toCamelCase(rsmd.getColumnName(i));
            if (camelCase.equals("createTime") || camelCase.equals("updateTime")) {
                sql += "now()";
            } else if (camelCase.equals("id")) {
                sql += "NULL";
            }else {
                sql += "#{" + camelCase + "}";
            }
            if (i < columnCount) {
                sql += ", ";
            }
        }
        sql += ")";
        System.out.println(sql);
    }

    private static String toCamelCase(String column) {
        String[] words = column.split("_");
        String result = words[0].toLowerCase();
        for (int i = 1; i < words.length; i++) {
            result += words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }
        return result;
    }
}
