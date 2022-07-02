package org.codeman.core.do_to_bo;

import org.codeman.common.Address;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdgaadd
 * Created on 2022/06/24
 *
 * @Description： DO -> BO
 *
 * 设计思路：读取每一行，剔除不希望的
 *
 * why not 正则表达式:
 * 1.不知道如何设置多行匹配，都是匹配一行
 * 2.特殊字符，正则表达式如何标明
 */
public class App {
    /**
     * 不期望出现的字符串
     */
    private static final List<String> UNEXPECTED = new ArrayList<String>(){{
        add("/**");
        add("* ");
        add("*/");
        add("@TableName");
        add("@TableField");
        add("@TableId(type = IdType.AUTO)");
    }};

    public static void main(String[] args) throws IOException {
        new App().handleEveryLine();
    }

    private void handleEveryLine() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Address.getAddressRun(this.getClass())), "utf-8"))) {
            StringBuilder builder = new StringBuilder();
            String curLine;
            int index = 0; // 第一个注释不换行

            while ((curLine = bufferedReader.readLine()) != null) {
                // 若是注释，设置StringBuilder不进行添加
                boolean isUnexpected = false;

                for (String detail : UNEXPECTED) {
                    if (curLine.contains(detail)) {
                        // 若是注释末尾，换行
                        if (detail.equals("*/")) {
                            // 第一个注释不换行
                            if (index++ != 0) builder.append("\r\n");
                        }
                        isUnexpected = true;
                        break;
                    }
                }

                // StringBuilder不会抓获换行，需要手动添加
                if (!isUnexpected) {
                    builder.append(curLine).append("\r\n");
                }
            }

            System.out.println(builder);
        }
    }

}