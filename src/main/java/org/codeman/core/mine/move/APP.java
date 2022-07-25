package org.codeman.core.mine.move;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author hdgaadd
 * Created on 2022/07/25
 */
public class APP {
    public static void main(String[] args) {
        new APP().moveFile();
    }

    public void moveFile() {
        String fromPath = "D:\\move\\";
        String toPath = "D:\\new_move";


//        File dirFile = new File(fromPath);
//        dirFile.renameTo(new File(toPath));

        // 移动文件夹，但是move会删除，且new_move必须不存在
        try {
//            FileUtils.moveFile(new File("F:/file/4spaces.txt"), new File("F:/4spaces/4spaces.txt"));
            FileUtils.moveDirectory(new File(fromPath), new File(toPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

