package models;

import javafx.beans.property.SimpleStringProperty;

public class Producer {
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty address;

    public Producer() {
        name = new SimpleStringProperty("");
        phone = new SimpleStringProperty("");
        address = new SimpleStringProperty("");
    }

    public Producer(String name, String phone, String address) {
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public String toString() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }
}
