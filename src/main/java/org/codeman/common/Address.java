package org.codeman.common;

/**
 * @author hdgaadd
 * Created on 2022/07/02
 *
 * @Description： 传递执行文件Class对象 -> 获取源文件地址
 */
public class Address {
    /**
     * 源文件名
     */
    private static final String FILE_NAME = "file";
    /**
     * 项目地址
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 源文件地址 == 项目地址 + 源文件名
     */
    private static final String SOURCE_PATH = PROJECT_PATH + FILE_NAME;

    /**
     * @param
     * @return 执行文件地址
     */
    public static String getAddressRun(Class foldClass) {
        String[] split = foldClass.getName().split("\\.");
        return PROJECT_PATH + "\\src\\main\\java\\org\\codeman\\core\\" + split[split.length - 2] + "\\" + FILE_NAME;
    }

    /**
     * @return 生成class文件地址
     */
    public static String getAddressOfClass() {
        return SOURCE_PATH;
    }
}
