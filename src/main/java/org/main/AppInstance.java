package org.main;

import javafx.scene.layout.Pane;
import java.io.IOException;

public class AppInstance {
    private static Pane app;
    private static AppInstance instance;
    private AppInstance() {
        app = new Pane();
    }
    public static Pane getApp() throws IOException {
        if(instance == null) {
            instance = new AppInstance();
        }
        return app;
    }
}
