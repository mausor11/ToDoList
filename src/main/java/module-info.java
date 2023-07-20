module ToDoList {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens org.main to javafx.fxml, javafx.graphics, javafx.controls;

    exports org.main to javafx.controls, javafx.fxml, javafx.graphics;
}