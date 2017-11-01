package models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class WaybillGood {
    private SimpleStringProperty title;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty amount;
    private SimpleDoubleProperty total;

    public WaybillGood() {
        title = new SimpleStringProperty("");
        price = new SimpleDoubleProperty();
        amount = new SimpleDoubleProperty();
        total = new SimpleDoubleProperty();
    }

    public WaybillGood(String title, double price, double amount) {
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleDoubleProperty(amount);
        this.total = new SimpleDoubleProperty(price * amount);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
        total.set(amount.get() * price);
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
        total.set(amount * price.get());
    }

    public double getTotal() {
        return total.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }
}
