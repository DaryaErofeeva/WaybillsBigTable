package interfaces.impl;

import db.WaybillsDAO;
import interfaces.AccountingBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Waybill;

public class CollectionAccountingBook implements AccountingBook {
    private WaybillsDAO waybillsDAO = new WaybillsDAO();
    private ObservableList<Waybill> waybillObservableList;

    public CollectionAccountingBook() {
        waybillObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void add(Waybill waybill) {
        waybillsDAO.set(waybill);
        waybillObservableList.add(waybill);
    }

    @Override
    public void edit(Waybill waybill) {
        waybillsDAO.set(waybill);
    }

    @Override
    public void delete(Waybill waybill) {
        waybillsDAO.deleteRow(String.valueOf(waybill.getNumber()));
        waybillObservableList.remove(waybill);
    }

    public ObservableList<Waybill> getWaybillObservableList() {
        return waybillObservableList;
    }

    public void fillWaybillList() {
        waybillObservableList.addAll(waybillsDAO.readAll());
    }
}
