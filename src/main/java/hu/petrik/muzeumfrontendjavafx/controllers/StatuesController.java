package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.MuseumApi;
import hu.petrik.muzeumfrontendjavafx.Statue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class StatuesController extends Controller {
    @FXML
    private TableView<Statue> statueTable;
    @FXML
    private TableColumn<Statue, String> columnPerson;
    @FXML
    private TableColumn<Statue, Integer> columnHeight;
    @FXML
    private TableColumn<Statue, Integer> columnPrice;

    public void initialize() {
        columnPerson.setCellValueFactory(new PropertyValueFactory<>("person"));
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
            statueList();
        }
        catch (Exception e) {
            ErrorWindow(e);
        }
    }

    private void statueList() {
        try {
            List<Statue> statues = MuseumApi.getStatues();
            statueTable.getItems().clear();
            for (Statue statue : statues) {
                statueTable.getItems().add(statue);
            }
        }
        catch (IOException e) {
            ErrorWindow(e);
        }
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        try {
            Controller add = newWindow("statue-add-view.fxml", "New statue", 500, 400);
            add.getStage().setOnCloseRequest(event -> statueList());
            add.getStage().show();
        }
        catch (Exception e) {
            ErrorWindow(e);
        }
    }

    @FXML
    public void onUpdateButtonClick(ActionEvent actionEvent) {
        int selectedIndex = statueTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("No selected item!");
        }
        else {
            Statue statue = statueTable.getSelectionModel().getSelectedItem();
            try {
                StatuesUpdateController statuesUpdateController = (StatuesUpdateController) newWindow("statue-update-view.fxml","Update painting", 500, 400);
                statuesUpdateController.setStatue(statue);
                statuesUpdateController.getStage().setOnHiding(event -> statueTable.refresh());
                statuesUpdateController.getStage().show();
            }
            catch (IOException e) {
                ErrorWindow(e);
            }
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        int selectedIndex = statueTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("No selected item!");
        }
        Statue statue = statueTable.getSelectionModel().getSelectedItem();
        if (!confirmWindow("Are you sure to delete this statue: " + statue.getPerson())) {
            return;
        }
        try {
            boolean success = MuseumApi.deleteStatue(statue.getId());
            alert(success? "Successful!" : "Unsuccessful!");
            statueList();
        }
        catch (IOException e) {
            ErrorWindow(e);
        }
    }
}
