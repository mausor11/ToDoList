package org.main;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.ArrayList;

//todo: button to add new list, stylize menu
public class MenuController {
    @FXML
     TextField textField;
    @FXML
    ListView listList;
     DataBase dataBase = new DataBase();
     ArrayList<String> allTables = dataBase.getAllTables();

    public void initialize() {
        textField.setText("Add list");
        textField.setStyle("-fx-text-fill: #FFB600");

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                textField.clear();
                textField.setStyle("-fx-text-fill: white");
            } else {
                textField.setText("Add list");
                textField.setStyle("-fx-text-fill: #FFB600");
            }
        });

        for(String s : allTables) {
            HBox table = newList(s);
            listList.getItems().add(table);
        }
    }

    public void closeMenu(MouseEvent mouseEvent) throws IOException {
        AppInstance.getApp().getChildren().remove(MenuSingleton.getMenu());
        MainViewInstance.getView().setEffect(null);
    }

     public void takeText(KeyEvent keyEvent) {
         if((keyEvent.getCode() == KeyCode.ENTER) && (!textField.getText().isEmpty())) {
             Pane list = newList(textField.getText());
             listList.getItems().add(list);
             dataBase.createNewList(textField.getText());
            textField.clear();
         }
     }

     private HBox newList(String list) {
         AnchorPane anchorPane = new AnchorPane();
         anchorPane.setPrefHeight(60);
         anchorPane.setPrefWidth(30);
         Label icon = new Label();
         icon.setText("☰");
         icon.setStyle("-fx-font-size: 15px; -fx-text-fill: #FFB600");
         icon.setLayoutY(10);
         anchorPane.getChildren().add(icon);
         Label taskTable = new Label(list);
         taskTable.setWrapText(true);
         HBox.setHgrow(taskTable, Priority.ALWAYS);
         taskTable.getStyleClass().add("listTable");
         HBox taskBox = new HBox();
         taskBox.getStyleClass().add("listBox");
         taskBox.getChildren().addAll(anchorPane, taskTable);
         HBox.setMargin(anchorPane, new Insets(0,0,0,10));
         HBox.setMargin(taskTable, new Insets(0,10,0,10));
         taskBox.widthProperty().addListener((observable, oldValue, newValue) -> {
             taskTable.setMaxWidth(newValue.doubleValue() - 40);
         });
         taskBox.setOnMouseClicked(e -> {
         });
         return taskBox;
     }
 }
