package org.main;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
 //todo: button to add new list, stylize menu
public class MenuController {

    public void closeMenu(MouseEvent mouseEvent) throws IOException {
        AppInstance.getApp().getChildren().remove(MenuSingleton.getMenu());
        MainViewInstance.getView().setEffect(null);
    }
}
