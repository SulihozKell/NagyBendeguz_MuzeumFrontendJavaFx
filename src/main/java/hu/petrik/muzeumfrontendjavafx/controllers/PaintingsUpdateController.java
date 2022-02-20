package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Painting;
import hu.petrik.muzeumfrontendjavafx.MuseumApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PaintingsUpdateController extends Controller {
    @FXML
    private Spinner<Integer> inputYear;
    @FXML
    private CheckBox inputOnDisplay;
    @FXML
    private TextField inputTitle;
    private Painting painting;

    public void setPainting(Painting painting) {
        this.painting = painting;
        setValue();
    }

    private void setValue() {
        inputTitle.setText(painting.getTitle());
        inputYear.getValueFactory().setValue(painting.getYear());
        inputOnDisplay.setSelected(painting.isOn_display());
    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        String title = inputTitle.getText().trim();
        int year = 0;
        boolean onDisplay = inputOnDisplay.isSelected();
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
        catch (NullPointerException e) {
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
        painting.setTitle(title);
        painting.setYear(year);
        painting.setOn_display(onDisplay);
        try {
            Painting painting = MuseumApi.updatePainting(this.painting);
            if (painting != null) {
                alertWait();
                this.stage.close();
            }
            else {
                alert("Unsuccessful!");
            }
        }
        catch (IOException e) {
            ErrorWindow(e);
        }
    }
}
