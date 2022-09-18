package org.codeman.core.personal.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codeman.common.Address;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdgaadd
 * Created on 2022/09/18
 *
 * @Description: excel转换为String，打印出包含关键字符串的行
 */
public class App {
    private final static String PATH = Address.nameAndAddress("test.xlsx", App.class);

    private final static String KEYWORD1 = "A";

    private final static String KEYWORD2 = "B";

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
            if (index++ == 0 || builder.toString().contains(KEYWORD1) && builder.toString().contains(KEYWORD2)) {
                needLine.add(builder.toString());
            }
        }
        needLine.forEach(System.out::println);
    }
}
