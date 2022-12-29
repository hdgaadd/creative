package org.codeman.core.personal.mv;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.Address;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdgaadd
 * created on 2022/06/12
 *
 * description: FILE中的非F行，移动到最前，实现任务提醒，个人使用
 *
 * design: 如果上面有空行则进行匹配为一行，后续打印需打印空行
 */
@Slf4j
public class App {

    private static final List<String> UNDONE = new ArrayList<>();

    private static final List<String> DONE = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = Address.getReader(App.class)) {
            StringBuilder builder = new StringBuilder();
            String preLine = "";
            String curLine;

            while ((curLine = bufferedReader.readLine()) != null) {
                builder.append(curLine);
                if (preLine.equals("") || preLine.equals(" ") || preLine.equals("  ")) { // 上层为空行，则开始
                    preLine = curLine;
                } else { // 否则则添加行，或者结束
                    if (curLine.equals("") || curLine.equals(" ") || curLine.equals("  ")) {
                        handleTaskSave(preLine); // 存储任务行
                        preLine = curLine;
                    } else {
                        preLine += "\n" + curLine;
                    }
                }
            }
            log.info("读取文件时，读取到的任务字符数为: " + builder.length());
        }

        generateFile();
    }

    /**
     * 存储任务行
     */
    private static void handleTaskSave(String taskStr){
        String[] taskLines = taskStr.split("\n");
        for (String taskLine : taskLines) {
            if (taskLine.equals(" ") || taskLine.equals("  ") || taskLine.equals("   ")) { // 预防空格，导致以下代码数组越界问题
                continue;
            }
            if (taskLine.toCharArray()[taskLine.length() - 1] == 'F' || taskLine.toCharArray()[taskLine.length() - 2] == 'F' || taskLine.toCharArray()[taskLine.length() - 3] == 'F') { // 预防F没在最后位置
                DONE.add(taskStr);
                return;
            }
        }
        UNDONE.add(taskStr);
    }

    /**
     * 生成文件
     */
    private static void generateFile() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Address.getRunAddress(App.class))), StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();
            UNDONE.forEach(item -> {
                builder.append(item);
                try {
                    bufferedWriter.write(item);
                    bufferedWriter.write("\n\r");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            DONE.forEach(item -> {
                builder.append(item);
                try {
                    bufferedWriter.write(item);
                    bufferedWriter.write("\n\r");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            log.info("生成文件时，读取到的任务字符数为: " + builder.length());
        }
    }

    /**
     * print
     */
    private static void printTaskLine() {
        UNDONE.forEach(item -> {
            System.out.println("\n");
            System.out.println(item);
            System.out.println("\n");
        });

        log.info("=============================");

        DONE.forEach(item -> {
            System.out.println("\n");
            System.out.println(item);
            System.out.println("\n");
        });
    }
}
