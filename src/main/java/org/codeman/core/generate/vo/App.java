package org.codeman.core.generate.vo;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.Address;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * created by hdgaadd on 2021/12/06/18:25
 *
 * description: 识图取字 -> 翻译 -> 创建VO
 *
 * todo: GoogleTranslate doesn't work
 */
@Slf4j
public class App {
    /**
     * VO变量注释
     */
    private static final String MESSAGE = Address.readFileToString(App.class, "MESSAGE").replace(" ", "\n");
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
            TRANSLATE_ARR = handleTranslateArr(GoogleTranslate.getInstance().translateText(MESSAGE, "auto", "en").split("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        boolean createResult = createFile(new File(Address.nameAndAddress("VO", App.class)), createVo());
        log.info(createResult ? "======创建VO成功======" : "======创建VO失败======");
    }

    /**
     * 创建Vo
     */
    private static String createVo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MESSAGE_ARR.length; i++) {
            sb.append("@ApiModelProperty(value = \"" + MESSAGE_ARR[i] + "\")\r\n" +
                      "private " + "String " + TRANSLATE_ARR[i] + ";\r\n\n"
            );
        }
        return sb.toString();
    }

    /**
     * @param file 创建的文件
     * @param context 文件里面的内容
     */
    private static boolean createFile(File file, String context) throws IOException{
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(context);
        fileWriter.flush();
        fileWriter.close();
        return true;
    }

    /**
     * @param translateArr
     * @return 驼峰式写法
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