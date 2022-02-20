package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.MuseumApi;
import hu.petrik.muzeumfrontendjavafx.Statue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class StatuesAddController extends Controller {
    @FXML
    private TextField inputPerson;
    @FXML
    private Spinner<Integer> inputHeight;
    @FXML
    private Spinner<Integer> inputPrice;

    public void onAddButtonClick(ActionEvent actionEvent) {
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
        catch (NullPointerException ex) {
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
        catch (NullPointerException ex) {
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
        try {
            Statue newStatue = new Statue(0, person, height, price);
            Statue statue = MuseumApi.addStatue(newStatue);
            if (statue != null) {
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
