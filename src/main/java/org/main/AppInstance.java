package org.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AppInstance {
    private static Pane app;
    private static AppInstance instance;
    private AppInstance() throws IOException {
        app = new Pane();
    }
    public static Pane getApp() throws IOException {
        if(instance == null) {
            instance = new AppInstance();
        }
        return app;
    }
}
