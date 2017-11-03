package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Waybill {
    private SimpleIntegerProperty number;
    private Producer producer;
    private ObservableList<WaybillGood> waybillGoods;

    public Waybill() {
        number = new SimpleIntegerProperty();
        producer = new Producer();
        waybillGoods = FXCollections.observableArrayList();
    }

    public Waybill(int number, Producer producer, List<WaybillGood> waybillGoods) {
        this.number = new SimpleIntegerProperty(number);
        this.producer = producer;
        this.waybillGoods = FXCollections.observableArrayList(waybillGoods);
    }

    public int getNumber() {
        return number.get();
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public ObservableList<WaybillGood> getWaybillGoods() {
        return waybillGoods;
    }

    public void setWaybillGoods(List<WaybillGood> waybillGoods) {
        this.waybillGoods = FXCollections.observableArrayList(waybillGoods);
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public double getSum() {
        double sum = 0;
        for (WaybillGood good : waybillGoods)
            sum += good.getTotal();
        return sum;
    }
}
