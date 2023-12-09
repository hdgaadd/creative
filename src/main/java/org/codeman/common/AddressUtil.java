package org.codeman.common;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author hdgaadd
 * created on 2022/07/02
 */
public final class AddressUtil {

    private static String SOURCE_NAME = "FILE";

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private static AddressUtil _INSTANCE = null;

    private AddressUtil() { }

    public static AddressUtil getInstance() {
        if (null == _INSTANCE) {
            _INSTANCE = new AddressUtil();
        }
        return _INSTANCE;
    }

    /**
     * @param clazz
     * @return 执行文件地址
     */
    public static String getFileAddress(Class<?> clazz) {
        String objPath = clazz.getName();
        // 主包名
        int firstIndex = objPath.lastIndexOf("core");
        int endIndex = objPath.lastIndexOf(".");
        return PROJECT_PATH + "\\src\\main\\java\\org\\codeman\\" + objPath.substring(firstIndex, endIndex).replace(".", "\\") + "\\" + SOURCE_NAME;
    }

    /**
     * @param newName 新的执行文件名
     * @param clazz
     * @return 执行文件地址
     */
    public static String getFileAddress(String newName, Class<?> clazz) {
        SOURCE_NAME = newName;
        return getFileAddress(clazz);
    }

    /**
     * @param clazz
     * @return BufferedReader执行对象
     * @throws IOException
     */
    public static BufferedReader getFileReader(Class<?> clazz) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(getFileAddress(clazz)), StandardCharsets.UTF_8));
    }

    /**
     * @param clazz
     * @return 文件String格式
     */
    public static String getFileStringUseReplace(Class<?> clazz, String fileName) {
        SOURCE_NAME = fileName;
        String fileStr = null;
        try {
            fileStr = FileUtils.readFileToString(new File(getFileAddress(clazz)), StandardCharsets.UTF_8).replace("\r\n", " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStr;
    }

    /**
     * @param clazz
     * @return 文件String格式
     */
    public static String getFileString(Class<?> clazz, String fileName) {
        SOURCE_NAME = fileName;
        String fileStr = null;
        try {
            fileStr = FileUtils.readFileToString(new File(getFileAddress(clazz)), StandardCharsets.UTF_8).replace("\r\n", " ");;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStr;
    }
}
