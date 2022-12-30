package org.codeman.core.convert.bo;

import org.codeman.common.AddressUtil;

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
 * design: 读取每一行，剔除不希望的
 */
public class App {

    private static final List<String> UNEXPECTED = new ArrayList<String>(){{
        add("/**");
        add("* ");
        add("*/");
        add("@TableName");
        add("@TableField");
        add("@TableId(type = IdType.AUTO)");
    }};

    private static final StringBuilder BUILDER = new StringBuilder();

    public static void main(String[] args) throws IOException {
        handleEveryLine();
        System.out.println(BUILDER);
    }

    private static void handleEveryLine() throws IOException {
        try (BufferedReader bufferedReader = AddressUtil.getFileReader(App.class)) {
            String curLine;
            int lineIndex = 0;

            while ((curLine = bufferedReader.readLine()) != null) {
                // 若是注释，设置StringBuilder不进行添加
                boolean isUnexpected = false;

                for (String detail : UNEXPECTED) {
                    if (curLine.contains(detail)) {
                        if (detail.equals("*/")) {
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