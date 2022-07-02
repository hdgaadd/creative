package org.codeman.core.vo_generate;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hdgaadd on 2021/12/06/18:25
 *
 * @Description： 识图取字 -> 翻译 -> 创建VO
 */
@Slf4j
public class App {

    private static final String MESSAGE =
            "用户数\n" +
            "男性用户数\n" +
            "女性用户数\n";

    private static String[] MESSAGE_ARR, TRANSLATE_ARR;

    private static String INTERVAL;

    static {
        try {
            log.info("是否变量名的单词组成，单词与单词之间之间无空格：0 无 1 有");
            INTERVAL = new Scanner(System.in).nextInt() == 0 ? "" : " ";

            MESSAGE_ARR = MESSAGE.split("\n");
            // translate
            TRANSLATE_ARR = handleTranslateArr(Translator.getInstance().translateText(MESSAGE, "auto", "en").split("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        boolean createResult = createFile(new File(System.getProperty("user.dir") + "\\VO.java"), createVo());
        log.info(createResult ? "======创建VO成功======" : "======创建VO失败======")     ;
    }

    /**
     * 创建Vo
     */
    private static String createVo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MESSAGE_ARR.length; i++) {
            sb.append("@ApiModelProperty(value = \"" + MESSAGE_ARR[i] + "\")\r\n"
                    + "private " + "String " + TRANSLATE_ARR[i] + ";\r\n\n"
            );
        }
        return  sb.toString();
    }

    /**
     * @param file 创建的文件
     * @param context 文件里面的内容
     */
    private static boolean createFile(File file, String context) {
        //获取文件
        File parent = file.getParentFile();
        //如果是目录
        if (parent != null) {
            // 创建目录
            parent.mkdirs();
        }
        try {
            // 创建文件
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(context);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("创建文件失败:" + e.getMessage());
        }
        return true;
    }

    /**
     * 转换为驼峰式写法
     *
     * @param translateArr
     * @return
     */
    private static String[] handleTranslateArr(String[] translateArr) {
        for (int i = 0; i < translateArr.length; i++) {
            String[] curArr = translateArr[i].split(" ");

            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < curArr.length; j++) {
                String curStr = curArr[j];
                if (j == 0) {
                    stringBuilder.append(curStr.toLowerCase());
                } else {
                    char[] curStrArr = curStr.toCharArray();
                    curStrArr[0] = Character.toUpperCase(curStrArr[0]);
                    stringBuilder.append(INTERVAL + String.valueOf(curStrArr));
                }
            }
            translateArr[i] = String.valueOf(stringBuilder);
        }
        return translateArr;
    }
}