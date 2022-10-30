package org.codeman.core.personal.verify;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.Address;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author hdgaadd
 * created on 2022/06/28
 *
 * description: typing所需，打印出存在" "的行
 *
 * typing地址:  https://10fastfingers.com/widgets/typingtest
 */
@Slf4j
public class App {

    private static final String KEY_WORD = " ";

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = Address.getReader(App.class)) {
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) if (curLine.contains(KEY_WORD)) log.info("存在\" \"的行: " + curLine);
        }
    }
}
