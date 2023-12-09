package org.codeman.core.convert.sql.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateEntity {

    private static String ENTITY = "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Data\n" +
            "@AllArgsConstructor\n" +
            "@NoArgsConstructor\n" +
            "public class DO {\n";

    public static String generateEntity(String sql) {
        Pattern pattern = Pattern.compile("`\\w+`"); // create a pattern to match the alias names
        Matcher matcher = pattern.matcher(sql);
        List<String> aliases = new ArrayList<>();
        while (matcher.find()) {
            String alias = matcher.group().replace("`", ""); // remove the backticks from the matched string
            aliases.add(alias);
        }
        String[] aliasArray = aliases.toArray(new String[0]);

        for (String alias : aliasArray) {
            ENTITY += "    private String " + alias + ";\n";
        }
        ENTITY += "}";

        return ENTITY;
    }
}
