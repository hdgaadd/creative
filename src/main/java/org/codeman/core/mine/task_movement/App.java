package org.codeman.core.mine.task_movement;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.Address;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdgaadd
 * Created on 2022/06/12
 *
 * @Description： Z_task_ans中的非F行，移动到最前，实现任务提醒，个人使用
 *
 * 设计思路：如果上面有空行则进行匹配为一行，后续打印需打印空行
 *
 * 注意事项：
 * 1.确保最后一行空行俩行，原因为没处理最后一行的情况
 * 2.两个TaskMovement的区别，只是其中一个没有"package com.codeman.file.TaskMovement;"，且PROJECT_PATH不同
 *
 * bugs：
 * 1.F后面有空格会导致F不被识别
 * 2.退出PC登录个人账户 ，为什么在F下，原因为与前一行合为同一个行
 */
@Slf4j
public class App {
    /**
     * 待完成任务
     */
    private static final List<String> UNDONE = new ArrayList<>();
    /**
     * 已完成任务
     */
    private static final List<String> DONE = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Address.getInstance().getRunAddress(App.class)), "utf-8"))) {
            StringBuilder sb = new StringBuilder();
            String preLine = "";
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                sb.append(curLine);
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
            log.info("读取文件时，读取到的任务字符数为：" + sb.length());
        }
//        printTaskLine();
        generateFile();
    }

    /**
     * 存储任务行
     *
     * @param taskStr
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Address.getInstance().getRunAddress(App.class))),"utf-8"))) {
            StringBuilder sb = new StringBuilder();
            UNDONE.forEach(item -> {
                sb.append(item);
                try {
                    bufferedWriter.write(item);
                    bufferedWriter.write("\n\r");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.write("\n\r");
            DONE.forEach(item -> {
                sb.append(item);
                try {
                    bufferedWriter.write(item);
                    bufferedWriter.write("\n\r");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            log.info("生成文件时，读取到的任务字符数为：" + sb.length());
        }
    }

    /**
     * 打印
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
