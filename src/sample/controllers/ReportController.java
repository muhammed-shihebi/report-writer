package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.util.Optional;

public class ReportController {

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

    // ====== On Action ==============================
    @FXML
    private void customerAndReportOnAction(ActionEvent event) {
        customerAndReportPane.toFront();
    }

    @FXML
    private void equipmentOnAuction(ActionEvent event) {
        equipmentPane.toFront();
    }

    @FXML
    private void inspectionOnAction(ActionEvent event) {
        inspectionPane.toFront();
    }

    @FXML
    private void personnelOnAction(ActionEvent event) {
        personnelPane.toFront();
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText("Girdiğiniz tüm bilgiler kaydedilmeyecek. İptal etmek istediğinden emin misiniz?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(generalPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == buttonYes){
            generalPane.getScene().getWindow().hide();
        }
        // if user exit without clicking anything or if user clicked cancel do nothing
    }
}
