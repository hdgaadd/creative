package org.codeman.common;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author hdgaadd
 * created on 2022/07/02
 *
 * description: 传递执行文件Class对象 -> 返回源文件地址
 */
public final class Address {
    /**
     * 源文件名
     */
    private static String FILE_NAME = "FILE";
    /**
     * 项目地址
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 源文件地址 == 项目地址 + 源文件名
     */
    private static final String SOURCE_PATH = PROJECT_PATH + FILE_NAME;
    /**
     * instance
     */
    private static Address _INSTANCE = null;

    private Address() { }

    public static Address getInstance() {
        if (null == _INSTANCE) {
            _INSTANCE = new Address();
        }
        return _INSTANCE;
    }

    /**
     * @param clazz
     * @return 执行文件地址
     */
    public static String returnRunAddress(Class<?> clazz) {
        String objPath = clazz.getName();
        // 主包名
        int firstIndex = objPath.lastIndexOf("core");
        int endIndex = objPath.lastIndexOf(".");
        return PROJECT_PATH + "\\src\\main\\java\\org\\codeman\\" + objPath.substring(firstIndex, endIndex).replace(".", "\\") + "\\" + FILE_NAME;
    }

    /**
     * @param newName 新的执行文件名
     * @param clazz
     * @return 执行文件地址
     */
    public static String nameAndAddress(String newName, Class<?> clazz) {
        FILE_NAME = newName;
        return returnRunAddress(clazz);
    }

    /**
     * @param clazz
     * @return BufferedReader执行对象
     * @throws IOException
     */
    public static BufferedReader getReader(Class<?> clazz) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(returnRunAddress(clazz)), StandardCharsets.UTF_8));
    }

    /**
     * @return 生成class文件地址
     */
    public static String getAddressOfClass() {
        return SOURCE_PATH;
    }

    /**
     * @param clazz
     * @return 文件String格式
     */
    public static String readFileToString(Class<?> clazz, String fileName) {
        FILE_NAME = fileName;
        String fileStr = null;
        try {
            fileStr = FileUtils.readFileToString(new File(returnRunAddress(clazz)), StandardCharsets.UTF_8).replace("\r\n", " ");;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStr;
    }
}
