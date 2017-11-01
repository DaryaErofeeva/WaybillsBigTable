package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Waybill;
import models.WaybillGood;

import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtAddress;

    @FXML
    private TableView tblWaybillGoods;

    @FXML
    private TableColumn<WaybillGood, String> clmnTitle;

    @FXML
    private TableColumn<WaybillGood, Double> clmnPrice;

    @FXML
    private TableColumn<WaybillGood, Double> clmnAmount;

    @FXML
    private TableColumn<WaybillGood, Double> clmnTotal;

    @FXML
    private Label lblWaybillsSum;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    private ObservableList<WaybillGood> backupList;
    private Stage mainStage;
    private ResourceBundle resourceBundle;
    private Waybill waybill;
    private WaybillGood waybillGood;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        clmnTitle.setCellValueFactory(new PropertyValueFactory("title"));
        clmnPrice.setCellValueFactory(new PropertyValueFactory("price"));
        clmnAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        clmnTotal.setCellValueFactory(new PropertyValueFactory("total"));
    }

    public void setWaybill(Waybill waybill) {
        if (waybill == null)
            return;
        this.waybill = waybill;

        txtNumber.setText(String.valueOf(waybill.getNumber()));

        txtName.setText(waybill.getProducer().getName());
        txtPhone.setText(waybill.getProducer().getPhone());
        txtAddress.setText(waybill.getProducer().getAddress());

        fillWaybillGoods();
    }

    private void updateSumLabel() {
        lblWaybillsSum.setText(String.valueOf(waybill.getSum()));
    }

    public Waybill getWaybill() {
        return waybill;
    }

    private void fillWaybillGoods() {
        backupList = FXCollections.observableArrayList();
        backupList.addAll(waybill.getWaybillGoods());
        tblWaybillGoods.setItems(waybill.getWaybillGoods());
        updateSumLabel();
    }

    public void setGood(MouseEvent mouseEvent) {
        if (tblWaybillGoods.getSelectionModel().getSelectedItem() == null) waybillGood = new WaybillGood();
        else waybillGood = (WaybillGood) tblWaybillGoods.getSelectionModel().getSelectedItem();

        txtTitle.setText(waybillGood.getTitle());
        txtPrice.setText(String.valueOf(waybillGood.getPrice()));
        txtAmount.setText(String.valueOf(waybillGood.getAmount()));
    }

    public void saveGood(MouseEvent mouseEvent) {
        waybillGood.setTitle(txtTitle.getText());
        waybillGood.setPrice(Double.parseDouble(txtPrice.getText()));
        waybillGood.setAmount(Double.parseDouble(txtAmount.getText()));
        updateSumLabel();
    }

    public void addGood(MouseEvent mouseEvent) {
        waybill.getWaybillGoods().add(new WaybillGood(txtTitle.getText(),
                Double.parseDouble(txtPrice.getText()),
                Double.parseDouble(txtAmount.getText())));
    }

    public void deleteGood(MouseEvent mouseEvent) {
    }

    public void submitWaybill(MouseEvent mouseEvent) {
    }

    public void cancelWaybill(MouseEvent mouseEvent) {
    }
}
