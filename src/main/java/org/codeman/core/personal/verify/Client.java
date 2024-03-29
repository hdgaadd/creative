package org.codeman.core.personal.verify;

import lombok.extern.slf4j.Slf4j;
import org.codeman.common.AddressUtil;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author hdgaadd
 * created on 2022/06/28
 *
 * description: typing所需，打印出存在" "的行
 *
 * typing地址: https://10fastfingers.com/widgets/typingtest
 */
@Slf4j
public class Client {

    private static final String KEY_WORD = " ";

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = AddressUtil.getFileReader(Client.class)) {
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) if (curLine.contains(KEY_WORD)) log.info("存在\" \"的行: " + curLine);
        }
    }
}
