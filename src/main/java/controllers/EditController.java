package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    private Stage mainStage;
    private ResourceBundle resourceBundle;
    private Waybill waybill;
    private WaybillGood waybillGood;
    private ObservableList<WaybillGood> backupList;
    private boolean saveChanges;

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
        saveChanges = false;

        if (waybill == null)
            return;
        this.waybill = waybill;

        txtNumber.setText(String.valueOf(waybill.getNumber()));
        txtNumber.setEditable(waybill.getNumber() == 0);

        txtName.setText(waybill.getProducer().getName());
        txtPhone.setText(waybill.getProducer().getPhone());
        txtAddress.setText(waybill.getProducer().getAddress());

        fillWaybillGoods();
    }

    private void updateSumLabel() {
        lblWaybillsSum.setText(String.valueOf(getSum()));
    }

    public double getSum() {
        double sum = 0;
        for (WaybillGood good : backupList)
            sum += good.getTotal();
        return sum;
    }

    public Waybill getWaybill() {
        return waybill;
    }

    public boolean getSaveChanges() {
        return saveChanges;
    }

    private void fillWaybillGoods() {
        backupList = FXCollections.observableArrayList();
        backupList.addListener((ListChangeListener<WaybillGood>) c -> updateSumLabel());
        backupList.addAll(waybill.getWaybillGoods());
        tblWaybillGoods.setItems(backupList);
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
        backupList.add(new WaybillGood(txtTitle.getText(),
                Double.parseDouble(txtPrice.getText()),
                Double.parseDouble(txtAmount.getText())));
    }

    public void deleteGood(MouseEvent mouseEvent) {
        backupList.remove(tblWaybillGoods.getSelectionModel().getSelectedItem());
    }

    public void submitWaybill(ActionEvent actionEvent) {
        waybill.setNumber(Integer.valueOf(txtNumber.getText()));

        waybill.getProducer().setName(txtName.getText());
        waybill.getProducer().setPhone(txtPhone.getText());
        waybill.getProducer().setAddress(txtAddress.getText());

        waybill.setWaybillGoods(backupList);

        saveChanges = true;
        actionClose(actionEvent);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.hide();
    }
}
