package org.main;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    @FXML
    HBox doneBox;
    @FXML
    HBox waitingBox;
    @FXML
    Label yesNum1;
    @FXML
    Label noNum1;
    @FXML
    Label menuButton;
    @FXML
    HBox header;
    @FXML
    Label title;
    @FXML
    Rectangle secondHand;
    @FXML
    Rectangle hourHand;
    @FXML
    ListView taskList;
    @FXML
    TextField textField;
    @FXML
    Label yesNum;
    @FXML
    Label noNum;
    @FXML
    Group checkCross;
    @FXML
    Circle checkCircle;

    DataBase dataBase = new DataBase();
    ArrayList<String> doneTasks = dataBase.getOnlyDone();
    ArrayList<String> waitingTasks = dataBase.getOnlyWaiting();
    ArrayList<String> allTasks = dataBase.getAll();
    int doneNum = doneTasks.size();
    int waitNum = waitingTasks.size();
    private boolean doneIsClicked = false;
    private boolean waitingIsClicked = true;
    public void initialize() {
        menuButton.setText("☰");
        header.widthProperty().addListener((observable, oldValue, newValue) -> {
            title.setPrefWidth(newValue.doubleValue() - 120);
        });
        LocalTime now = LocalTime.now();
        Rotate minuteRotate = new Rotate(0, 2, 20);
        Rotate hourRotate = new Rotate(0, 2, 14);
        secondHand.getTransforms().add(minuteRotate);
        hourHand.getTransforms().add(hourRotate);
        yesNum.setText(doneNum + "");
        noNum.setText(waitNum + "");
        Timeline minuteTime = createRotateTimeline(Duration.minutes(60), getMinuteNow(now), minuteRotate);
        Timeline hourTime = createRotateTimeline(Duration.hours(12), getHourNow(now), hourRotate);
        minuteTime.play();
        hourTime.play();
        checkCircle.setOpacity(0);
        checkCross.setOpacity(1);
        textField.setText("Add task");
        textField.setStyle("-fx-text-fill: #FFB600");

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                checkCircle.setOpacity(1);
                checkCross.setOpacity(0);
                textField.clear();
                textField.setStyle("-fx-text-fill: white");
            } else {
                checkCircle.setOpacity(0);
                checkCross.setOpacity(1);
                textField.setText("Add task");
                textField.setStyle("-fx-text-fill: #FFB600");
            }
        });

        for(String s : waitingTasks) {
            HBox task = task(s);
            taskList.getItems().add(task);
        }

    }
    private Timeline createRotateTimeline(Duration duration, int startAngle, Rotate rotate) {
        Timeline timeline = new Timeline(1);
        rotate.setAngle(startAngle);
        timeline.getKeyFrames().add(new KeyFrame(duration, new KeyValue(rotate.angleProperty(), startAngle + 360)));
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }
    private int getMinuteNow(LocalTime time) {
        return (int) ((time.getMinute() + time.getSecond() / 60d) * 360 / 60);
    }
    private int getHourNow(LocalTime now) {
        return (int) ((now.getHour() % 12 + now.getMinute() / 60d + now.getSecond() / 3600d) * 360 / 12);
    }
    private HBox task(String task) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(60);
        anchorPane.setPrefWidth(30);
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.getStyleClass().add("checkOut");
        circle.setLayoutX(16);
        circle.setLayoutY(20);

        Circle circleIn = new Circle();
        circleIn.getStyleClass().add("checkIn");
        circleIn.setRadius(6);
        circleIn.setLayoutX(16);
        circleIn.setLayoutY(20);
        circleIn.setVisible(false);

        Circle circleCheck = new Circle();
        circleCheck.setRadius(12);
        circleCheck.setStyle("-fx-opacity: 0");
        circleCheck.setLayoutX(16);
        circleCheck.setLayoutY(20);

        circleCheck.setOnMouseEntered(e -> {
            circleIn.setVisible(true);
        });
        circleCheck.setOnMouseExited(e -> {
            circleIn.setVisible(false);
        });
        anchorPane.getChildren().addAll(circle, circleIn, circleCheck);
        Label taskTable = new Label(task);
        taskTable.setWrapText(true);
        HBox.setHgrow(taskTable, Priority.ALWAYS);
        taskTable.getStyleClass().add("taskTable");
        HBox taskBox = new HBox();
        taskBox.getStyleClass().add("taskBox");
        taskBox.getChildren().addAll(anchorPane, taskTable);
        HBox.setMargin(anchorPane, new Insets(0,0,0,10));
        HBox.setMargin(taskTable, new Insets(0,10,0,10));
        taskBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            taskTable.setMaxWidth(newValue.doubleValue() - 40);
        });
        circleCheck.setOnMouseClicked(e -> {
            taskList.getItems().remove(taskBox);
            dataBase.doneTask(task);
            doneNum++;
            waitNum--;
            yesNum.setText(doneNum + "");
            noNum.setText(waitNum + "");
        });
        return taskBox;
    }
    private HBox taskDone(String task) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(60);
        anchorPane.setPrefWidth(30);
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.getStyleClass().add("checkOut");
        circle.setLayoutX(16);
        circle.setLayoutY(20);

        Circle circleIn = new Circle();
        circleIn.getStyleClass().add("checkIn");
        circleIn.setRadius(6);
        circleIn.setLayoutX(16);
        circleIn.setLayoutY(20);
        circleIn.setVisible(true);

        Circle circleCheck = new Circle();
        circleCheck.setRadius(12);
        circleCheck.setStyle("-fx-opacity: 0");
        circleCheck.setLayoutX(16);
        circleCheck.setLayoutY(20);

        anchorPane.getChildren().addAll(circle, circleIn, circleCheck);
        Label taskTable = new Label(task);
        taskTable.setWrapText(true);
        HBox.setHgrow(taskTable, Priority.ALWAYS);
        taskTable.getStyleClass().add("taskTable");
        HBox taskBox = new HBox();
        taskBox.getStyleClass().add("taskBox");
        taskBox.getChildren().addAll(anchorPane, taskTable);
        HBox.setMargin(anchorPane, new Insets(0,0,0,10));
        HBox.setMargin(taskTable, new Insets(0,10,0,10));
        taskBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            taskTable.setMaxWidth(newValue.doubleValue() - 40);
        });
        return taskBox;
    }
    public void takeText(KeyEvent keyEvent) {
        if((keyEvent.getCode() == KeyCode.ENTER) && (!textField.getText().isEmpty())) {
            HBox task = task(textField.getText());
            taskList.getItems().add(task);
            dataBase.insertTask(textField.getText());
            textField.clear();
            waitNum++;
            noNum.setText(waitNum + "");

        }
    }

    public void showDone(MouseEvent mouseEvent) {
        if(!doneIsClicked) {
            taskList.getItems().clear();
            doneTasks = dataBase.getOnlyDone();
            for(String s : doneTasks) {
                HBox task = taskDone(s);
                taskList.getItems().add(task);
            }
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(100), yesNum1);
            RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(100), noNum1);
            rotateTransition.setAutoReverse(false);
            rotateTransition1.setAutoReverse(false);
            rotateTransition.setByAngle(180);
            rotateTransition1.setByAngle(180);
            rotateTransition.play();
            rotateTransition1.play();
            doneIsClicked = true;
            waitingIsClicked = false;
            doneBox.setStyle("-fx-background-color: #459445");
            waitingBox.setStyle("-fx-background-color: #693232");
        }

    }
    public void showWaiting(MouseEvent mouseEvent) {
        if(!waitingIsClicked) {
            taskList.getItems().clear();
            waitingTasks = dataBase.getOnlyWaiting();
            for(String s : waitingTasks) {
                HBox task = task(s);
                taskList.getItems().add(task);
            }
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(100), yesNum1);
            RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(100), noNum1);
            rotateTransition.setAutoReverse(false);
            rotateTransition1.setAutoReverse(false);
            rotateTransition.setByAngle(180);
            rotateTransition1.setByAngle(180);
            rotateTransition.play();
            rotateTransition1.play();
            waitingIsClicked = true;
            doneIsClicked = false;
            waitingBox.setStyle("-fx-background-color: #8c4242");
            doneBox.setStyle("-fx-background-color: #326932");
        }

    }
}

