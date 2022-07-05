package org.codeman.core.mine.verify_typing_line;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.Address;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author hdgaadd
 * Created on 2022/06/28
 *
 * @Description： typing所需，打印出存在" "的行
 *
 * typing地址： https://10fastfingers.com/widgets/typingtest
 */
@Slf4j
public class App {

    private static final String KEYWORD = " ";

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Address.getInstance().getRunAddress(App.class)), "utf-8"))) {
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) if (curLine.contains(KEYWORD)) log.info("存在\" \"的行：" + curLine);
        }
    }
}
