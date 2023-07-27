package org.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
public class Main extends Application {
    private Scene scene;
    private boolean menuAdded = false;
    public static Label menuAddedBoolean = new Label("false");
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
        VBox vboxMenu = MenuSingleton.getMenu();
        pane.getChildren().add(vbox);
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() > 1000.0) {
                vbox.setPrefWidth(newValue.doubleValue() - 250.0);
                vbox.setLayoutX(250.0);
                if(!menuAdded) {
                    if(!pane.getChildren().contains(vboxMenu)) {
                        pane.getChildren().add(vboxMenu);
                        menuAdded = true;
                        menuAddedBoolean.setText("true");
                    }

                }
                vboxMenu.setLayoutX(0.0);
            } else {
                vbox.setPrefWidth(newValue.doubleValue());
                vbox.setLayoutX(0.0);
//                pane.getChildren().remove(vboxMenu);
                menuAdded = false;
                menuAddedBoolean.setText("false");
            }
        });
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            vbox.setPrefHeight(newValue.doubleValue());
        });
        scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}