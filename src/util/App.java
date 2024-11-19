package util;

public class App {

    public static String getVersion() {
        return "v0.1.0";
    }

    public static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception ignored) {
        }
    }
}
