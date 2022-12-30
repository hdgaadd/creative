package org.codeman.core.personal.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codeman.common.AddressUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author hdgaadd
 * created on 2022/09/18
 *
 * description: excel转换为String，打印出包含关键字符串的行
 */
public class App {

    private final static String PATH = AddressUtil.getFileAddress("test.xlsx", App.class);

    private final static List<String> KEYWORD = new ArrayList<String>(){{
        add("A");
        add("B");
    }};

    public static void main(String[] args) throws IOException {
        Sheet sheet = new XSSFWorkbook(new FileInputStream(new File(PATH))).getSheetAt(0);
        List<String> needLine = new ArrayList<>();

        int index = 0;
        for (Row row : sheet) {
            StringBuilder builder = new StringBuilder();
            for (Cell cell : row) {
                builder.append(cell.toString()).append("  ");
            }
            // 是否包含关键字 || 打印表头
            if (index++ == 0 || isContains(builder.toString())) {
                needLine.add(builder.toString());
            }
        }
        needLine.forEach(System.out::println);
    }

    private static boolean isContains(String builderStr) {
       AtomicBoolean ret = new AtomicBoolean(true);
       KEYWORD.forEach(key -> {
           if (!builderStr.contains(key)) {
               ret.set(false);
           }
       });
       return ret.get();
    }
}
