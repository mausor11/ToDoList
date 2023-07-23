package org.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuSingleton {
    private static VBox menu;
    private static MenuSingleton instance;
    private MenuSingleton() throws IOException {
        menu = new FXMLLoader(getClass().getResource("menu-app.fxml")).load();
    }
    public static VBox getMenu() throws IOException {
        if(instance == null) {
            instance = new MenuSingleton();
        }
        return menu;
    }
}
