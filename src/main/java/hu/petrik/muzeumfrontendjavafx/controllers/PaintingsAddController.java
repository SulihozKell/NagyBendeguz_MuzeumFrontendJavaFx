package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Painting;
import hu.petrik.muzeumfrontendjavafx.MuseumApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class PaintingsAddController extends Controller {
    @FXML
    private Spinner<Integer> inputYear;
    @FXML
    private CheckBox inputOnDisplay;
    @FXML
    private TextField inputTitle;

    public void onAddButtonClick(ActionEvent actionEvent) {
        String title = inputTitle.getText().trim();
        boolean onDisplay = inputOnDisplay.isSelected();
        int year = 0;
        if (title.isEmpty()) {
            alert("The title is required!");
            return;
        }
        if (title.length() < 3) {
            alert("The title must be at least 3 characters.");
            return;
        }
        try {
            year = inputYear.getValue();
        }
        catch (NullPointerException ex) {
            alert("Year is required!");
            return;
        }
        catch (Exception e) {
            alert("The year must be between 100 and 3000!");
            return;
        }
        if (year < 100 || year > 3000) {
            alert("The year must be between 100 and 3000!");
            return;
        }
        try {
            Painting newPainting = new Painting(0, title, year, onDisplay);
            Painting painting = MuseumApi.addPainting(newPainting);
            if (painting != null) {
                alert("Successful!");
            }
            else {
                alert("Unsuccessful!");
            }
        }
        catch (Exception e) {
            ErrorWindow(e);
        }
    }
}
