package org.codeman.core.convert.do_to_bo;

import org.codeman.common.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdgaadd
 * created on 2022/06/24d
 *
 * description: DO -> BO
 *
 * 设计思路: 读取每一行，剔除不希望的
 */
public class App {
    /**
     * 不期望出现的
     */
    private static final List<String> UNEXPECTED = new ArrayList<String>(){{
        add("/**");
        add("* ");
        add("*/");
        add("@TableName");
        add("@TableField");
        add("@TableId(type = IdType.AUTO)");
    }};
    /**
     * BUILDER
     */
    private static final StringBuilder BUILDER = new StringBuilder();

    public static void main(String[] args) throws IOException {
        handleEveryLine();
        System.out.println(BUILDER);
    }

    private static void handleEveryLine() throws IOException {
        try (BufferedReader bufferedReader = Address.getReader(App.class)) {
            String curLine;
            int lineIndex = 0; // 第一个注释不换行

            while ((curLine = bufferedReader.readLine()) != null) {
                // 若是注释，设置StringBuilder不进行添加
                boolean isUnexpected = false;
                for (String detail : UNEXPECTED) {
                    if (curLine.contains(detail)) {
                        // 若是注释末尾，换行
                        if (detail.equals("*/")) {
                            // 第一个注释不换行
                            if (lineIndex++ != 0) BUILDER.append("\r\n");
                        }
                        isUnexpected = true;
                        break;
                    }
                }
                // BUILDER不会抓获换行，须手动添加
                if (!isUnexpected) BUILDER.append(curLine).append("\r\n");
            }
        }
    }

}