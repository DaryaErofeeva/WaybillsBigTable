package start;

import controllers.MainController;
import db.WaybillsDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Producer;
import models.Waybill;
import models.WaybillGood;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(stage);

        stage.setTitle("Накладные");
        stage.setMinHeight(150);
        stage.setMinWidth(350);
        stage.setScene(new Scene(fxmlMain));
        stage.show();

//        WaybillsDAO waybillsDAO = new WaybillsDAO();
//        Waybill waybill = waybillsDAO.read("1");
//        waybill.getProducer().setName("Производитель");
//        waybill.setNumber(2);
//        waybillsDAO.set(waybill);
    }
}
