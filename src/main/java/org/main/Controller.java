package org.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.HashMap;
import java.util.Map;

public class Controller {
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
    int doneNum = 0;
    int waitNum = 0;
    public void initialize() {
        menuButton.setText("☰");


        header.widthProperty().addListener((observable, oldValue, newValue) -> {
            title.setPrefWidth(newValue.doubleValue() - 120);
        });
        LocalTime now = LocalTime.now();
        System.out.println(now.getSecond());
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
            doneNum++;
            waitNum--;
            yesNum.setText(doneNum + "");
            noNum.setText(waitNum + "");
        });
        return taskBox;
    }

    public void takeText(KeyEvent keyEvent) {
        if((keyEvent.getCode() == KeyCode.ENTER) && (!textField.getText().isEmpty())) {
            HBox task = task(textField.getText());
            taskList.getItems().add(task);
            textField.clear();
            waitNum++;
            noNum.setText(waitNum + "");

        }
    }
}

