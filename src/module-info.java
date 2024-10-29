module app {
    requires javafx.controls;
    requires javafx.graphics;
    opens app to javafx.controls, javafx.base, javafx.graphics;
}
