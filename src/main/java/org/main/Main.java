package org.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMinWidth(400);
        stage.setMinHeight(500);
        stage.setWidth(400);
        stage.setHeight(500);

        Scene scene = new Scene(new FXMLLoader(getClass().getResource("main-app.fxml")).load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
