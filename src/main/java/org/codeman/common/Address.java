package org.codeman.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author hdgaadd
 * Created on 2022/07/02
 *
 * @Description： 传递执行文件Class对象 -> 获取源文件地址
 */
public final class Address {
    /**
     * 源文件名
     */
    private static String FILE_NAME = "file";
    /**
     * 项目地址
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 源文件地址 == 项目地址 + 源文件名
     */
    private static final String SOURCE_PATH = PROJECT_PATH + FILE_NAME;
    /**
     * 单例
     */
    private static Address _INSTANCE = null;

    public static Address getInstance() {
        if (null == _INSTANCE) {
            _INSTANCE = new Address();
        }
        return _INSTANCE;
    }

    public Address setFileName(String fileName) {
        this.FILE_NAME = fileName;
        return this;
    }

    /**
     * @param
     * @return 执行文件地址
     */
    public String getRunAddress(Class clazz) {
        String objPath = clazz.getName();
        // 主包名
        int firstIndex = objPath.lastIndexOf("core");
        int endIndex = objPath.lastIndexOf(".");

        return PROJECT_PATH + "\\src\\main\\java\\org\\codeman\\" + objPath.substring(firstIndex, endIndex).replace(".", "\\") + "\\" + FILE_NAME;
    }

    /**
     * @param clazz
     * @return 执行文件对象
     * @throws IOException
     */
    public static BufferedReader getReader(Class clazz) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(new Address().getRunAddress(clazz)), "utf-8"));
    }

    /**
     * @return 生成class文件地址
     */
    public static String getAddressOfClass() {
        return SOURCE_PATH;
    }

}
