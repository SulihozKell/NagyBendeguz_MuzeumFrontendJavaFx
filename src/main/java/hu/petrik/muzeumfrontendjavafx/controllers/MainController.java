package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController extends Controller {

    public void onPaintingsButtonClick(ActionEvent actionEvent) {
        try {
            Controller paintingsList = newWindow("paintings-view.fxml","Paintings", 540, 500);
            paintingsList.getStage().show();
        }
        catch (IOException e) {
            ErrorWindow(e);
        }
    }

    public void onStatuesButtonClick(ActionEvent actionEvent) {
        try {
            Controller statuesList = newWindow("statues-view.fxml","Statues", 540, 500);
            statuesList.getStage().show();
        }
        catch (IOException e) {
            ErrorWindow(e);
        }
    }
}