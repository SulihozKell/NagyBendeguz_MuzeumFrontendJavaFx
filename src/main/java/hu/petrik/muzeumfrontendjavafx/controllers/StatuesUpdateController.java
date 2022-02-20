package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.MuseumApi;
import hu.petrik.muzeumfrontendjavafx.Statue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StatuesUpdateController extends Controller {
    @FXML
    private TextField inputPerson;
    @FXML
    private Spinner<Integer> inputPrice;
    @FXML
    private Spinner<Integer> inputHeight;
    private Statue statue;

    public void setStatue(Statue statue) {
        this.statue = statue;
        setValue();
    }

    private void setValue() {
        inputPerson.setText(statue.getPerson());
        inputHeight.getValueFactory().setValue(statue.getHeight());
        inputPrice.getValueFactory().setValue(statue.getPrice());
    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        String person = inputPerson.getText().trim();
        int height = 0;
        int price = 0;
        if (person.isEmpty()) {
            alert("The person is required!");
            return;
        }
        if (person.length() < 5) {
            alert("The person must be at least 5 characters.");
            return;
        }
        try {
            height = inputHeight.getValue();
        }
        catch (NullPointerException e) {
            alert("Height is required!");
            return;
        }
        catch (Exception e) {
            alert("The height must be between 1 and 250!");
            return;
        }
        if (height < 1 || height > 250) {
            alert("The height must be between 1 and 250!");
            return;
        }
        try {
            price = inputPrice.getValue();
        }
        catch (NullPointerException e) {
            alert("Price is required!");
            return;
        }
        catch (Exception e) {
            alert("The price must be between 1000 and 99999!");
            return;
        }
        if (price < 1000 || price > 99999) {
            alert("The price must be between 1000 and 99999!");
            return;
        }
        statue.setPerson(person);
        statue.setHeight(height);
        statue.setPrice(price);
        try {
            Statue statue = MuseumApi.updateStatue(this.statue);
            if (statue != null) {
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
