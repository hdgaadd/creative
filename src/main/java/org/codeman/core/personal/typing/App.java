package org.codeman.core.personal.typing;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @author hdgaadd
 * created on 2022/12/29
 *
 * description: code typing
 */
public class App {

    private static final String SOURCE_PATH = "D:\\Users\\win\\Desktop\\block\\note\\company\\z-recite.txt";

    private static final List<String> CODE_LINE_LIST = new ArrayList<>();

    private static int CODE_LIST_SIZE = 0;

    private static int RIGHT_SIZE = 0, ERROR_SIZE = 0;

    private static int WORD_SIZE = 0;

    private static final Scanner scanner = new Scanner(System.in);

    private static final Instant START_TIME = Instant.now();

    public static void main(String[] args) throws IOException {
        getFullText();
        checkTyping();
    }

    private static void getFullText() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(SOURCE_PATH)));
        String curLine;
        while ((curLine = reader.readLine()) != null) {
            if (curLine.length() != 0) CODE_LINE_LIST.add(curLine);
        }
        CODE_LIST_SIZE = CODE_LINE_LIST.size();
    }

    private static void checkTyping() {
        while (true) {
            int codeIndex = new Random().nextInt(CODE_LIST_SIZE);
            String codeLine = CODE_LINE_LIST.get(codeIndex);
            System.out.println(codeLine);

            String typingLine = scanner.nextLine();

            // other check
            if (typingLine.equals("break")) {
                statistics();
                break;
            }
            // typing check
            if (codeLine.equals(typingLine)) {
                RIGHT_SIZE++;
                System.out.println("true!");
            } else {
                ERROR_SIZE++;
                System.out.println("false!");
            }
            WORD_SIZE += typingLine.length();
        }
    }

    private static void statistics() {
        System.out.println(String.format(
                "the correct number you entered is %d\r\n" +
                "the error number you entered is %d\r\n" +
                "your typing time is %.2fmin\r\n"
                , RIGHT_SIZE, ERROR_SIZE, (double)(Duration.between(START_TIME, Instant.now()).toMillis()) / (60 * 1000)));
    }
}
