package org.codeman.core.convert.sql;

import org.codeman.core.convert.sql.util.GenerateSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateSelectAndInsertSQL {

    public static void main(String[] args) throws SQLException {
        String url = "";
        String user = "";
        String password = "";
        List<String> list = new ArrayList<String>(){{
            add(""); // table
        }};

        Connection connection = DriverManager.getConnection(url, user, password);
        for (String table : list) {
            GenerateSQL.generateSelectAndInsertSQL(connection, table);
            System.out.println("\n");
        }
        connection.close();
    }

}
