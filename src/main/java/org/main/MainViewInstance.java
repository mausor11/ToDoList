package org.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainViewInstance {
    private static VBox mainView;
    private static MainViewInstance instance;
    private MainViewInstance() throws IOException {
        mainView = new FXMLLoader(getClass().getResource("main-app.fxml")).load();
    }
    public static VBox getView() throws IOException {
        if(instance == null) {
            instance = new MainViewInstance();
        }
        return mainView;
    }
}
