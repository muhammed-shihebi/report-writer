package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class Report2Controller {

    @FXML
    private AnchorPane generalPane;

    @FXML
    private Pane equipmentPane;

    @FXML
    private Pane personnelPane;

    @FXML
    private Pane inspectionPane;

    @FXML
    private Pane customerAndReportPane;

    //  ================ sidebar actions ====================
    @FXML
    void customerAndReportOnAction(ActionEvent event) {
        customerAndReportPane.toFront();
    }
    @FXML
    void equipmentOnAuction(ActionEvent event) {
        equipmentPane.toFront();
    }
    @FXML
    void inspectionOnAction(ActionEvent event) {
        inspectionPane.toFront();
    }
    @FXML
    void personnelOnAction(ActionEvent event) {
        personnelPane.toFront();
    }
    @FXML
    void cancelOnAction(ActionEvent event) throws IOException {
        if(cancelAlert()){
            generalPane.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/main.fxml"));
            Parent root = loader.load();
            MainController mainController = (MainController) loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Main"); // ToDo this should be the name of the report chosen by user
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    // ================ Alerts ==============================

    boolean cancelAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText("Girdiğiniz tüm bilgiler kaydedilmeyecek. İptal etmek istediğinden emin misin?");
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            if(result.get() == buttonYes){
                return true;
            }else{
                return false;
            }
        }
        return false; // if user exit without clicking anything or if user clicked cancel
    }

}