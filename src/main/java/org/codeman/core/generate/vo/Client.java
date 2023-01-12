package org.codeman.core.generate.vo;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.AddressUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * created by hdgaadd on 2021/12/06
 *
 * description: 识图取字 -> 翻译 -> 创建VO
 */
@Slf4j
public class Client {
    /**
     * MESSAGE里的VO变量注释
     */
    private static final String MESSAGE = AddressUtil.getFileString(Client.class, "MESSAGE").replace(" ", "\n");
    /**
     * 单词间间隔符号
     */
    private static String INTERVAL;
    /**
     * 缓存集合
     */
    private static String[] MESSAGE_ARR, TRANSLATE_ARR;

    static {
        try {
            log.info("是否变量名的单词组成，单词与单词之间之间无空格: 0 无 1 有");
            INTERVAL = new Scanner(System.in).nextInt() == 0 ? "" : " ";

            MESSAGE_ARR = MESSAGE.split("\n");
            // translate
            TRANSLATE_ARR = handleTranslateArr(GoogleTranslator.getInstance().translateText(MESSAGE, "auto", "en").split("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        boolean createResult = createFile(new File(AddressUtil.getFileAddress("VO", Client.class)), createVO());
        log.info(createResult ? "======创建VO成功======" : "======创建VO失败======");
    }

    /**
     * 创建VO
     */
    private static String createVO() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MESSAGE_ARR.length; i++) {
            builder.append("@ApiModelProperty(value = \"" + MESSAGE_ARR[i] + "\")\r\n" +
                      "private " + "String " + TRANSLATE_ARR[i] + ";\r\n\n"
            );
        }
        return builder.toString();
    }

    /**
     * @param file 创建的文件
     * @param context 文件里面的内容
     */
    private static boolean createFile(File file, String context) throws IOException{
        FileWriter writer = new FileWriter(file);
        writer.write(context);
        writer.flush();
        writer.close();
        return true;
    }

    /**
     * @param translateArr
     * @return 驼峰式写法
     */
    private static String[] handleTranslateArr(String[] translateArr) {
        for (int i = 0; i < translateArr.length; i++) {
            String[] curArr = translateArr[i].split(" ");

            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < curArr.length; j++) {
                String curStr = curArr[j];
                if (j == 0) {
                    builder.append(curStr.toLowerCase());
                } else {
                    char[] curStrArr = curStr.toCharArray();
                    curStrArr[0] = Character.toUpperCase(curStrArr[0]);
                    builder.append(INTERVAL + String.valueOf(curStrArr));
                }
            }
            translateArr[i] = String.valueOf(builder);
        }
        return translateArr;
    }
}