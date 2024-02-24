package org.codeman.core.personal.find;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author hdgaadd
 * created on 2024/02/35
 *
 * description: 遍历忘记，查找内容
 */
public class JavaClassClient {

    private static final String PATH = "E:\\z-relax\\dynamic-tp";

    private static final String TARGET_CONTENT = "Licensed to the Apache Software Foundation (ASF) under one or more";

    private static int javaClassCount = 0;

    private static int haveCount = 0;

    public static void main(String[] args) throws IOException {
        dfsFile(new File(PATH));

        System.out.println(javaClassCount);
        System.out.println(haveCount);
    }

    private static void dfsFile(File files) throws IOException {
        File[] fileArr = files.listFiles();
        for (File file : fileArr) {
            if (file.isDirectory()) dfsFile(file);
            String curName = file.getName();
            if (curName.lastIndexOf(".") == -1 || !curName.substring(curName.lastIndexOf(".")).equals(".java")) {
                continue;
            }
            javaClassCount++;

            boolean isHave = false;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(TARGET_CONTENT)) {
                    haveCount++;
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                System.out.println(curName);
            }
            reader.close();
        }
    }

}
