package org.codeman.core.convert.sql;

import org.codeman.common.AddressUtil;
import org.codeman.core.convert.sql.util.GenerateEntity;
import org.codeman.core.convert.sql.util.SQLFormat;

public class HandleYOURSQL {

    private static String SQL = AddressUtil.getFileStringUseReplace(HandleYOURSQL.class, "YOUR_SQL");

    public static void main(String[] args) {
        // delete alia
        if (SQL.contains("`")) {
            SQL = SQLFormat.removeAlias(SQL);
        }

        String aliasSQL = SQLFormat.addCamelCaseAlias(SQL);

        System.out.println(SQLFormat.formatSQL(aliasSQL)+"\n");
        System.out.println(GenerateEntity.generateEntity(aliasSQL));
    }

}
