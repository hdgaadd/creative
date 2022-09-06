import java.io.*;
import java.nio.charset.StandardCharsets;
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
 * 2.两个TaskMovement的区别，只是其中一个没有"package com.codeman.FILE.TaskMovement;"，且PROJECT_PATH不同
 *
 * bugs：
 * 1.F后面有空格会导致F不被识别
 * 2.退出PC登录个人账户 ，为什么在F下，原因为与前一行合为同一个行
 */

public class mv {
    /**
     * 待完成任务
     */
    private static final List<String> UNDONE = new ArrayList<>();
    /**
     * 已完成任务
     */
    private static final List<String> DONE = new ArrayList<>();

    private static final String PATH = "E:/Z_relax/offer" + "/src/z-task-ans.md";

    public static void main(String[] args) throws IOException {
        System.out.println(PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                // 存储任务行
                if (!curLine.equals("")) {
                    sb.append(curLine);
                    handleTaskSave(curLine);
                }
            }
            System.out.println("读取文件时，读取到的任务字符数为：" + sb.length());
        }
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(PATH)), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            UNDONE.forEach(item -> {
                sb.append(item);
                try {
                    bufferedWriter.write(item);
                    bufferedWriter.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            DONE.forEach(item -> {
                sb.append(item);
                try {
                    bufferedWriter.write(item);
                    bufferedWriter.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("生成文件时，读取到的任务字符数为：" + sb.length());
        }
    }
}
