package org.codeman.core.personal.blog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MsgExtractor {
    public static void main(String[] args) {
        String filePath = ""; // 文件路径
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 检查行是否以特定前缀开头，并提取中间的文本
                if (line.startsWith("> ***")) {
                    // 提取`***`后面的内容直到行末或下一个`***`
                    int startIndex = line.indexOf("***") + 3; // 跳过前面的`***`
                    int endIndex = line.indexOf("***", startIndex); // 查找下一个`***`
                    if (endIndex == -1) {
                        endIndex = line.length();
                    }
                    String extracted = line.substring(startIndex, endIndex);
                    // 去除文本中的反引号
                    extracted = extracted.replace("`", "");
                    System.out.println(extracted);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}