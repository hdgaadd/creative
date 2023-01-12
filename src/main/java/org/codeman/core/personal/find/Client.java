package org.codeman.core.personal.find;

import org.codeman.common.AddressUtil;

import java.io.*;
import java.util.*;

/**
 * @author hdgaadd
 * created on 2023/01/12
 *
 * description: 查找所有API
 */
public class Client {

    private static final String PATH = "C:\\Java\\jdk1.8.0_311\\src";

    private static final String FILE_OUT = AddressUtil.getFileAddress("PRODUCT.txt", Client.class);

    private static final List<String> MUST_KEYWORD = new ArrayList<String>() {{
        add("(");
        add(")");
        add(" {");
    }};

    private static final List<String> OR_KEYWORD = new ArrayList<String>() {{
        add("public");
        add("private");
        add("protected");
        add("default");
        add("abstract");
        add("void");
    }};

    private static final Set<String> CONTAINER = new HashSet<>();

    public static void main(String[] args) throws IOException {
        dfsFile(new File(PATH));

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_OUT));
        CONTAINER.forEach(o -> {
            try {
                writer.write(o);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private static void dfsFile(File files) throws IOException {
        File[] fileArr = files.listFiles();

        for (File file : fileArr) {
            if (file.isDirectory()) dfsFile(file);

            String curName = file.getName();
            if (curName.lastIndexOf(".") == -1 || !curName.substring(curName.lastIndexOf(".")).equals(".java")) {
                continue;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                int mustCount = (int) MUST_KEYWORD.stream().filter(line::contains).count();
                int orCount = (int) OR_KEYWORD.stream().filter(line::contains).count();
                if (mustCount != MUST_KEYWORD.size() || orCount <= 0 || line.contains(curName.substring(0, curName.lastIndexOf(".")))) continue;

                handleLine(line);
            }
            reader.close();
        }
    }

    private static void handleLine(String line) {
        String[] arr = line.split(" ");
        Arrays.stream(arr).forEach(o -> {
            if (o.contains("(") && o.contains(")")) {
                String cur = o.split("\\(")[0];
                if (cur.matches("^[a-zA-Z]*") && (cur.length() > 0 && String.valueOf(cur.charAt(0)).matches("^[a-z]*"))) {
                    CONTAINER.add(cur);
                }
            }
        });
    }

}
