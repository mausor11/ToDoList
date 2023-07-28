package org.main;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuController {
    @FXML
    ImageView imageBin;
    @FXML
     TextField textField;
    @FXML
    ListView listList;
    @FXML
    AnchorPane backgroundImage;
    @FXML
    Label menuButtonOn;
    @FXML
    HBox textFieldBox;
    DataBase dataBase = new DataBase();
    ArrayList<String> allTables = dataBase.getAllTables();
    private ArrayList<String> tablesToDelete;
    public static Label newTitle = new Label();
    public static Label isContainerDisable = new Label("false");
    private boolean deleteMode = false;
    public static boolean isMenuOn = false;
    private long toDelete = 0;
    public void initialize() throws IOException {
        Main.menuAddedBoolean.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("true")) {
                menuButtonOn.setOpacity(0);
                menuButtonOn.setDisable(true);
            } else {
                menuButtonOn.setOpacity(1);
                menuButtonOn.setDisable(false);


            }
        });

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

    public void closeMenu() throws IOException {
        AppInstance.getApp().getChildren().remove(MenuSingleton.getMenu());
        MainViewInstance.getView().setEffect(null);
        isContainerDisable.setText("false");
        isMenuOn = false;
    }

     public void takeText(KeyEvent keyEvent) throws SQLException {
         if((keyEvent.getCode() == KeyCode.ENTER) && (!textField.getText().isEmpty())) {
             allTables = dataBase.getAllTables();
             boolean good = true;
             for(String s : allTables) {
                 if(s.equals(textField.getText())) {
                     MenuController.newTitle.setText(textField.getText());
                     good = false;
                 }
             }
             if(good) {
                 HBox list = newList(textField.getText());
                 listList.getItems().add(list);
                 dataBase.createNewList(textField.getText());
                 if(listList.getItems().size() == 1) {
                     MenuController.newTitle.setText(textField.getText());
                 }
                 textField.clear();
             }


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
             MenuController.newTitle.setText(list);
         });

         taskBox.setOnMouseEntered(e -> {
             anchorPane.setStyle("-fx-background-color: #494949");
             taskBox.setStyle("-fx-background-color: #494949");
             taskTable.setStyle("-fx-background-color: #494949");
         });
         taskBox.setOnMouseExited(e -> {
             anchorPane.setStyle("-fx-background-color: #383838");
             taskBox.setStyle("-fx-background-color: #383838");
             taskTable.setStyle("-fx-background-color: #383838");
         });
         return taskBox;
     }
     private HBox newListDeleteMode(String list) {
        AtomicBoolean detected = new AtomicBoolean(false);
         AnchorPane anchorPane = new AnchorPane();
         anchorPane.setPrefHeight(60);
         anchorPane.setPrefWidth(30);
         Label icon = new Label();
         icon.setText("☰");
         icon.setStyle("-fx-font-size: 15px; -fx-text-fill: #d33030; -fx-font-weight: bold");
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
             if(detected.get()) {
                 detected.set(false);
                 icon.setText("☰");
                 icon.setStyle("-fx-font-size: 15px; -fx-text-fill: #d33030; -fx-font-weight: bold");
                 taskTable.getStyleClass().remove("listTableDeleted");
                 taskBox.getStyleClass().remove("listBoxDeleted");
                 taskTable.getStyleClass().add("listTable");
                 taskBox.getStyleClass().add("listTable");
                 if(tablesToDelete.contains(list)) {
                     tablesToDelete.remove(list);
                     toDelete--;
                 }
             } else {
                 detected.set(true);
                 icon.setText("X");
                 icon.setStyle("-fx-font-size: 15px; -fx-text-fill: #d33030; -fx-font-weight: bold");
                 taskTable.getStyleClass().remove("listTable");
                 taskBox.getStyleClass().remove("listBox");
                 taskTable.getStyleClass().add("listTableDeleted");
                 taskBox.getStyleClass().add("listBoxDeleted");
                 tablesToDelete.add(list);
                 toDelete++;
             }


         });
         return taskBox;
     }

    public void deleteMode() {
        if(deleteMode) {
            backgroundImage.getStyleClass().remove("backgroundImageDetected");
            backgroundImage.getStyleClass().add("backgroundImage");
            listList.getItems().clear();
            for(String s : allTables) {
                HBox table = newList(s);
                listList.getItems().add(table);
            }
            Image image = new Image(Objects.requireNonNull(getClass().getResource("bin_icon.png")).toExternalForm());
            imageBin.setImage(image);
            deleteMode = false;
            if(!tablesToDelete.isEmpty()) {
                if(listList.getItems().size() != 1) {
                    System.out.println(toDelete + " " + listList.getItems().size());
                    if ((listList.getItems().size() - toDelete) != 0) {
                        dataBase.deleteTables(tablesToDelete);
                        toDelete = 0;
                        listList.getItems().clear();
                        allTables = dataBase.getAllTables();
                        for (String s : allTables) {
                            HBox table = newList(s);
                            listList.getItems().add(table);
                        }
                        for (String s : tablesToDelete) {
                            if (s.equals(MenuController.newTitle.getText())) {
                                if (!allTables.isEmpty()) {
                                    MenuController.newTitle.setText(allTables.get(0));
                                    break;
                                } else {
                                    MenuController.newTitle.setText("");
                                    break;
                                }

                            }
                        }
                    } else {
                        toDelete = 0;
                    }
                } else {
                    toDelete = 0;
                }

            } else {
                toDelete = 0;
            }

        } else {
            tablesToDelete = new ArrayList<>();
            backgroundImage.getStyleClass().remove("backgroundImage");
            backgroundImage.getStyleClass().add("backgroundImageDetected");
            listList.getItems().clear();
            allTables = dataBase.getAllTables();
            for(String s : allTables) {
                HBox table = newListDeleteMode(s);
                listList.getItems().add(table);
            }
            Image image = new Image(Objects.requireNonNull(getClass().getResource("bin_icon_white.png")).toExternalForm());
            imageBin.setImage(image);
            deleteMode = true;
        }
    }
}
