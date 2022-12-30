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
        Collections.shuffle(CODE_LINE_LIST);
        CODE_LIST_SIZE = CODE_LINE_LIST.size();
    }

    private static void checkTyping() {
        int typingCount = 0;
        while (true) {
            String codeLine = CODE_LINE_LIST.get(typingCount++);
            System.out.println(codeLine);

            String typingLine = scanner.nextLine();
            WORD_SIZE += typingLine.length();

            // other check
            if (typingLine.equals("break") || typingCount == CODE_LIST_SIZE) {
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
        }
    }

    private static void statistics() {
        double useMin = (double) (Duration.between(START_TIME, Instant.now()).toMillis()) / (60 * 1000);
        System.out.println(String.format(
                "the correct number you entered is %d\r\n" +
                "the error number you entered is %d\r\n" +
                "your typing time is %.2fMIN\r\n" +
                "your typing speed is %.0fWPM\r\n"
                , RIGHT_SIZE, ERROR_SIZE, useMin, ((double)WORD_SIZE / 3 / useMin)));
    }
}
