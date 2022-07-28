package org.codeman.core.mine.file_move;

/**
 * @author hdgaadd
 * Created on 2022/07/25
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// 移动文件，而不是移动整个文件夹，不会遍历排序；而rnameTo也是移动文件
public class CutFiles {

    public static void main(String[] args) {
        // 源地址
        String sourcePath = "D:\\move";
        // 目标地址
        String destPath = "D:\\new_move";
        // 源地址
        File srcFile = new File(sourcePath);
        // 目标地址
        File targetFile = new File(destPath);
        copyDir(srcFile, targetFile);
//        deleteFile(srcFile);
    }

    private static void deleteFile(File srcFile) {
        // 获取列表下所有文件
        final File[] files = srcFile.listFiles();
        if (files.length != 0) {
            for (File file : files) {
                // 如果获取到文件直接删除
                if (file.isFile()) {
                    file.delete();
                } else {
                    // 如果获取到文件夹,进入文件夹中遍历
                    deleteFile(file);
                }
            }
        }
        srcFile.delete();

    }

    // 复制一个文件
    public static void copyFile(File srcFile, File targetFile) {
        try (FileInputStream fis = new FileInputStream(srcFile);
             FileOutputStream fos = new FileOutputStream(targetFile);) {

            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 复制目录下的所有内容
    public static void copyDir(File srcFile, File targetFile) {
        // 如果 目录 不存在 就创建
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        // 遍历所有 时传入File 下所有的文件
        File[] files = srcFile.listFiles();

/*        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }
            public boolean equals(Object obj) {
                return true;
            }

        });
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            System.out.println(new Date(files[i].lastModified()));
        }*/

        for (File file : files) {
            if (file.isFile()) {
                copyFile(new File(srcFile + "\\" + file.getName())
                        , new File(targetFile + "\\" + file.getName()));
            } else {
                // 如果该目录下是 目录,执行 赋值文件
                copyDir(new File(srcFile + "\\" + file.getName())
                        , new File(targetFile + "\\" + file.getName()));

            }
        }
    }

}

