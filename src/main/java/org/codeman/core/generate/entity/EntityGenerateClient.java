package org.codeman.core.generate.entity;

import org.codeman.common.AddressUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EntityGenerateClient {

    private static String ENTITY = AddressUtil.getFileString(EntityGenerateClient.class, "ENTITY");

    private static final String tr = "package org.codeman.core.generate.entity;"
            + "\r\n\r\n";

    private static final String tr1 = "import lombok.AllArgsConstructor;\n" +
            "import lombok.Builder;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\r\n\r\n";

    public static void main(String[] args) throws IOException {
        Map<String, String> map = parse(ENTITY);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            createFile(new File(AddressUtil.getFileAddress(entry.getKey()+ ".java", EntityGenerateClient.class)), tr + tr1 + entry.getValue());
        }
    }

    public static Map<String, String> parse(String input) {
        Map<String, String> map = new HashMap<>();
        String[] entities = input.split("@Data");
        for (String entity : entities) {
            if (!entity.trim().isEmpty()) {
                int index = entity.indexOf("class ");
                if (index != -1) {
                    String className = entity.substring(index + 6, entity.indexOf("{")).trim();
                    map.put(className, "@Data" + entity);
                }
            }
        }
        return map;
    }

    private static boolean createFile(File file, String context) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(context);
        writer.flush();
        writer.close();
        return true;
    }

}
