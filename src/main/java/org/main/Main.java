package org.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        Pane pane = AppInstance.getApp();
        pane.setStyle("-fx-background-color: #232323");
        VBox vbox = MainViewInstance.getView();
        pane.getChildren().addAll(vbox);
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            vbox.setPrefWidth(newValue.doubleValue());
        });
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            vbox.setPrefHeight(newValue.doubleValue());
        });
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
