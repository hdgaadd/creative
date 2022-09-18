//package org.codeman.core.personal.mv.ans;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author hdgaadd
// * Created on 2022/06/12
// * @Description： z-task-ans.md中的非F行，移动到最前，实现任务提醒，个人使用
// * <p>
// * 设计思路：如果上面有空行则进行匹配为一行，后续打印需打印空行
// */
//
//public class mv {
//    /**
//     * 待完成任务
//     */
//    private static final List<String> UNDONE = new ArrayList<>();
//    /**
//     * 已完成任务
//     */
//    private static final List<String> DONE = new ArrayList<>();
//
//    private static final String PATH = System.getProperty("user.dir") + "/z-task-ans.md";
//
//    public static void main(String[] args) throws IOException {
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8))) {
//            StringBuilder builder = new StringBuilder();
//            String curLine;
//            while ((curLine = bufferedReader.readLine()) != null) {
//                // 存储任务行
//                if (!curLine.equals("")) {
//                    builder.append(curLine);
//                    handleTaskSave(curLine);
//                }
//            }
//            System.out.println("读取文件所读取的任务字符数: " + builder.length());
//        }
//        generateFile();
//    }
//
//    /**
//     * 存储任务行
//     *
//     * @param taskStr
//     */
//    private static void handleTaskSave(String taskStr) {
//        String[] taskLines = taskStr.split("\n");
//        for (String line : taskLines) {
//            if (line.equals(" ") || line.equals("  ") || line.equals("   ")) { // 预防空格，导致以下代码数组越界问题
//                continue;
//            }
//            if (line.toCharArray()[line.length() - 1] == 'F' || line.toCharArray()[line.length() - 2] == 'F' || line.toCharArray()[line.length() - 3] == 'F') { // 预防F没在最后位置
//                DONE.add(taskStr);
//                return;
//            }
//        }
//        UNDONE.add(taskStr);
//    }
//
//    /**
//     * 生成文件
//     */
//    private static void generateFile() throws IOException {
//        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(PATH)), StandardCharsets.UTF_8))) {
//            StringBuilder builder = new StringBuilder();
//            UNDONE.forEach(it -> {
//                builder.append(it);
//                try {
//                    bufferedWriter.write(it);
//                    bufferedWriter.write("\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            DONE.forEach(it -> {
//                builder.append(it);
//                try {
//                    bufferedWriter.write(it);
//                    bufferedWriter.write("\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            System.out.println("生成文件所读取的任务字符数: " + builder.length());
//        }
//    }
//}
