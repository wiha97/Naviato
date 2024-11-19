package JWutil;

import java.util.List;
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
    }

    public static void line(Object obj) {
        System.out.println(obj);
    }

    public static void line(int i) {
        System.out.println(i);
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

    public static String title(String title) {
        int length = title.length();
        return String.format("""
                ============%s============
                            %s
                ============%s============""", filler(length, "="), title, filler(length, "="));
    }

    public static String title(String title, List<LogModel> list) {
        int length = title.length();
        return String.format("""
                ============%s============ %s
                            %s             %s
                ============%s============ %s""", filler(length, "="), sizeCheck(list, 0),
                title, list.size() > 0 ? sizeCheck(list, 1):"    No logs :(", filler(length, "="), sizeCheck(list, 2));
    }

    private static String sizeCheck(List<LogModel> list, int idx){
        if(list.size() > idx)
            return list.get(idx).toString();
        return "";
    }

    public static String title(String title, String note) {
        int length = title.length();
        return String.format("""
                ============%s============
                            %s
                            %s
                ============%s============""", filler(length, "="), title, note, filler(length, "="));
    }

    private static String filler(int length) {
        String filler = "";
        for (int i = 0; i < length; i++) {
            filler += "=";
        }
        return filler;
    }
    private static String filler(int length, String c) {
        String filler = "";
        for (int i = 0; i < length; i++) {
            filler += c;
        }
        return filler;
    }

    public static String back() {

        return "[<] [B]ack";
    }

    public static String newScan() {
        return new Scanner(System.in).nextLine();
    }

    //  Does not work in IDE terminal
    public static void clear() {
        Print.same("\033[H\033[2J");
    }
}
