package org.codeman.core.personal.merge;

import java.io.*;

/**
 * @author hdgaadd
 * created on 2022/04/12
 *
 * description: 合并大量文件，使用Notepad++进行快速查找
 */
public class App {

    public static void main(String[] args) throws IOException {
        String projectPath = System.getProperty("user.dir");

        // 输出文件
        String FileOut = projectPath + "\\output.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(FileOut));

        // 输入源
        String folderName = projectPath + "\\folder\\";
        File file = new File(folderName);
        File[] files = file.listFiles();

        for (File curFile: files) {
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
