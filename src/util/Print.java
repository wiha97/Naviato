package util;

import javafx.application.Platform;
import managers.GameManager;

import java.util.Scanner;

public class Print {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String AMBER = "\u001B[33m";
    public static final String NAVY = "\033[94m";
    public static final String GREEN = "\u001B[32m";
    public static final String GRAY = "\u001B[1m";

    public static void line(String text) {
        System.out.println(text);
        Platform.runLater(() -> GameManager.getLogList().add(0, text));
    }

    public static void line(Object obj) {
        line(obj.toString());
    }

    public static void line(int i) {
        line(""+i);
    }

    public static void debug (int text){
        System.out.println(text);
    }

    public static void same(String text) {
        System.out.print(text);
    }

    public static void same(Object obj) {
        System.out.print(obj);
    }

    public static void same(int i) {
        System.out.print(i);
    }

    public static String alert(String text) {
        return RED + text + RESET;
    }

    public static String warning(String text) {
        return AMBER + text + RESET;
    }

    public static String good(String text) {
        return GREEN + text + RESET;
    }

    public static String info(String text){
        return GRAY + text + RESET;
    }

    public static String newScan() {
        return new Scanner(System.in).nextLine();
    }

    //  Does not work in IDE terminal
    public static void clear() {
        Print.same("\033[H\033[2J");
    }
}
