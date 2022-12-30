package org.codeman.core.personal.merge;

import org.codeman.common.AddressUtil;

import java.io.*;

/**
 * @author hdgaadd
 * created on 2022/04/12
 *
 * description: 合并大量文件，使用Notepad++进行快速查找
 */
public class App {

    private static final String PATH = AddressUtil.getFileAddress("source\\", App.class);

    private static final String FILE_OUT = AddressUtil.getFileAddress("PRODUCT.txt", App.class);

    public static void main(String[] args) throws IOException {
        File file = new File(PATH);
        File[] files = file.listFiles();

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_OUT));

        for (File curFile : files) {
            BufferedReader reader = new BufferedReader(new FileReader(curFile));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            reader.close();
        }

        writer.close();
    }

}
