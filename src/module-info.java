module app {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    opens app to javafx.controls, javafx.base, javafx.graphics;
}
