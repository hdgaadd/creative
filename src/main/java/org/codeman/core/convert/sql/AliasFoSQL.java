package org.codeman.core.convert.sql;

import org.codeman.common.AddressUtil;
import org.codeman.core.generate.sql.util.GenerateEntity;
import org.codeman.core.generate.sql.util.SQLFormat;

/**
 * @author hdgaadd
 * created on 2023/12/09
 *
 * description: 为SQL添加驼峰式别名
 */
public class AliasFoSQL {

    private static String SQL = AddressUtil.getFileStringUseReplace(AliasFoSQL.class, "YOUR_SQL");

    public static void main(String[] args) {
        if (SQL.contains("`")) {
            SQL = SQLFormat.removeAlias(SQL);
        }

        String aliasSQL = SQLFormat.addCamelCaseAlias(SQL);

        System.out.println(SQLFormat.formatSQL(aliasSQL)+"\n");
        // System.out.println(GenerateEntity.generateEntity(aliasSQL));
    }

}
