package org.codeman.core.personal.blog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileModifier {
    public static void main(String[] args) {
        String inputFilePath = "";
        String outputFilePath = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = processLine(line);
                writer.write(line);
                writer.newLine();  // 写入一个新行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processLine(String line) {
        // 处理带 "> " 前缀的行
        if (line.trim().startsWith("> ")) {
            line = "> ***面试官：" + line.trim().substring(2) + "***";
        }

        // 处理行内的英文单词，要求单词前面是中文字符，后面是非英文字母的字符
        line = line.replaceAll("(?<=\\p{IsHan})([a-zA-Z]+)(?=[^a-zA-Z*])", "`$1`");

        return line;
    }
}
