package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Painting;
import hu.petrik.muzeumfrontendjavafx.MuseumApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class PaintingsController extends Controller {
    @FXML
    private TableView<Painting> paintingTable;
    @FXML
    private TableColumn<Painting, String> columnTitle;
    @FXML
    private TableColumn<Painting, Integer> columnYear;
    @FXML
    private TableColumn<Painting, String> columnOnDisplay;

    public void initialize() {
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnOnDisplay.setCellValueFactory(new PropertyValueFactory<>("on_display"));
        try {
            paintingsList();
        }
        catch (Exception e) {
            ErrorWindow(e);
        }
    }

    private void paintingsList() {
        try {
            List<Painting> paintings = MuseumApi.getPaintings();
            paintingTable.getItems().clear();
            for (Painting painting : paintings) {
                paintingTable.getItems().add(painting);
            }
        }
        catch (IOException e) {
            ErrorWindow(e);
       }
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        try {
            Controller add = newWindow("painting-add-view.fxml", "New painting", 500, 400);
            add.getStage().setOnCloseRequest(event -> paintingsList());
            add.getStage().show();
        }
        catch (Exception e) {
            ErrorWindow(e);
        }
    }

    @FXML
    public void onUpdateButtonClick(ActionEvent actionEvent) {
        int selectedIndex = paintingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("No selected item!");
        }
        else {
            Painting painting = paintingTable.getSelectionModel().getSelectedItem();
            try {
                PaintingsUpdateController paintingsUpdateController = (PaintingsUpdateController) newWindow("painting-update-view.fxml","Update painting", 500, 400);
                paintingsUpdateController.setPainting(painting);
                paintingsUpdateController.getStage().setOnHiding(event -> paintingTable.refresh());
                paintingsUpdateController.getStage().show();
            }
            catch (IOException e) {
                ErrorWindow(e);
            }
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        int selectedIndex = paintingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("No selected item!");
        }
        Painting deletePainting = paintingTable.getSelectionModel().getSelectedItem();
        if (!confirmWindow("Are you sure to delete this painting: " + deletePainting.getTitle())) {
            return;
        }
        try {
            boolean success = MuseumApi.deletePainting(deletePainting.getId());
            alert(success? "Successful!" : "Unsuccessful!");
            paintingsList();
        }
        catch (IOException e) {
            ErrorWindow(e);
        }
    }
}
