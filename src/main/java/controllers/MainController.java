package controllers;

import interfaces.impl.CollectionAccountingBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Producer;
import models.Waybill;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CollectionAccountingBook collectionAccountingBook;

    @FXML
    private TableView tblWaybills;

    @FXML
    private TableColumn<Waybill, Integer> clmnNumber;

    @FXML
    private TableColumn<Waybill, Producer> clmnProducer;

    private Stage mainStage;
    private Parent fxmlEdit;
    private Stage editStage;
    private EditController editController;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private ResourceBundle resourceBundle;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        clmnNumber.setCellValueFactory(new PropertyValueFactory("number"));
        clmnProducer.setCellValueFactory(new PropertyValueFactory("producer"));

        collectionAccountingBook = new CollectionAccountingBook();

        initLoader();
        fillData();
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("/fxml/edit_waybill.fxml"));
            fxmlLoader.setResources(resourceBundle);
            fxmlEdit = fxmlLoader.load();
            editController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillData() {
        collectionAccountingBook.fillWaybillList();
        tblWaybills.setItems(collectionAccountingBook.getWaybillObservableList());
    }

    public void editWaybillForm(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();

        if (!(source instanceof Button))
            return;

        Button button = (Button) source;

        switch (button.getId()) {
            case "btnAdd":
                editController.setWaybill(new Waybill());
                showDialog();
                collectionAccountingBook.add(editController.getWaybill());
                break;
            case "btnEdit":
                if (isWaybillSelected((Waybill) tblWaybills.getSelectionModel().getSelectedItem())) {
                    editController.setWaybill((Waybill) tblWaybills.getSelectionModel().getSelectedItem());
                    showDialog();
                    collectionAccountingBook.edit(editController.getWaybill());
                }
                break;
        }
    }

    private void showDialog() {
        if (editStage == null) {
            editStage = new Stage();
            editStage.setTitle("Редактирование записи");
            editStage.setMinHeight(650);
            editStage.setMinWidth(650);
            editStage.setScene(new Scene(fxmlEdit));
            editStage.setResizable(false);
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(mainStage);
        }
        editStage.showAndWait();
    }

    private boolean isWaybillSelected(Waybill waybill) {
        if (waybill == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("");
            alert.setContentText("Выберите запись");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void deleteWaybill(MouseEvent mouseEvent) {
        if (!isWaybillSelected((Waybill) tblWaybills.getSelectionModel().getSelectedItem())) return;
        collectionAccountingBook.delete((Waybill) tblWaybills.getSelectionModel().getSelectedItem());
    }
}
