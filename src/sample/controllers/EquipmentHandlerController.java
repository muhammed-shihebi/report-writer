package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.Main;
import sample.handlers.DatabaseHandler;
import sample.model.Equipment;

import java.sql.SQLException;


public class EquipmentHandlerController {
    private static final String ERORRTEXTFILESTYLE = "-fx-border-color: red;";
    private static final int ADDMODE = 1;
    private static final int EDITMODE = 2;
    private int mode = ADDMODE;

    private Equipment selectedEquipment;

    @FXML
    private AnchorPane addEquipmentPane;

    @FXML
    private TextField equipmentField;

    @FXML
    private TextField poleDistanceField;

    @FXML
    private TextField MPCarrierMediumField;

    @FXML
    private TextField magTechField;

    @FXML
    private Text magTechMesg;

    @FXML
    private Button addButton;

    @FXML
    private TextField UVLightIntensityField;

    @FXML
    private TextField distanceOfLightField;

    @FXML
    private Text UVLightIntensityMseg;

    @FXML
    private Text distanceOfLightMesg;

    @FXML
    private Text MPCarrierMediumMesg;

    @FXML
    private Text equipmentMesg;

    @FXML
    private Text poleDistanceMesg;

    // ====== On Action ===========================

    @FXML
    void addButtonOnAction(ActionEvent event) throws SQLException {
        resetStyle();
        if(!areFieldsEmpty()){
            Equipment newEquipment = new Equipment(
                    Double.parseDouble(poleDistanceField.getText()),
                    equipmentField.getText(),
                    MPCarrierMediumField.getText(),
                    magTechField.getText(),
                    UVLightIntensityField.getText(),
                    distanceOfLightField.getText()
            );
            if(mode == ADDMODE){
                DatabaseHandler.addNewEquipment(newEquipment);
            }else {
                DatabaseHandler.editEquipment(newEquipment, selectedEquipment);
            }
            addEquipmentPane.getScene().getWindow().hide();
        }
    }

    @FXML
    void canselButtonOnAction(ActionEvent event) {
        addEquipmentPane.getScene().getWindow().hide();
    }

    // ====== On Key ==============================

    // these functions are made to sense any change in text fields and report errors immediately

    @FXML
    void MPCarrierMediumField(KeyEvent event) {
        MPCarrierMediumField.setStyle(null);
        MPCarrierMediumMesg.setVisible(false);
        if(MPCarrierMediumField.getText().equals("") || Main.isStringNotLegal(MPCarrierMediumField.getText())){
            MPCarrierMediumMesg.setVisible(true);
            MPCarrierMediumField.setStyle(ERORRTEXTFILESTYLE);
        }
    }

    @FXML
    void UVLightIntensityField(KeyEvent event) {
        UVLightIntensityField.setStyle(null);
        UVLightIntensityMseg.setVisible(false);
        if(UVLightIntensityField.getText().equals("") || Main.isStringNotLegal(UVLightIntensityField.getText())){
            UVLightIntensityMseg.setVisible(true);
            UVLightIntensityField.setStyle(ERORRTEXTFILESTYLE);
        }
    }

    @FXML
    void distanceOfLightField(KeyEvent event) {
        distanceOfLightField.setStyle(null);
        distanceOfLightMesg.setVisible(false);
        if(distanceOfLightField.getText().equals("") || Main.isStringNotLegal(distanceOfLightField.getText())){
            distanceOfLightMesg.setVisible(true);
            distanceOfLightField.setStyle(ERORRTEXTFILESTYLE);
        }
    }

    @FXML
    void equipmentField(KeyEvent event) {
        equipmentField.setStyle(null);
        equipmentMesg.setVisible(false);
        if(equipmentField.getText().equals("") || Main.isStringNotLegal(equipmentField.getText())){
            equipmentMesg.setVisible(true);
            equipmentField.setStyle(ERORRTEXTFILESTYLE);
        }
    }

    @FXML
    void magTechField(KeyEvent event) {
        magTechField.setStyle(null);
        magTechMesg.setVisible(false);
        if(magTechField.getText().equals("") || Main.isStringNotLegal(magTechField.getText())){
            magTechMesg.setVisible(true);
            magTechField.setStyle(ERORRTEXTFILESTYLE);
        }
    }

    @FXML
    void poleDistanceField(KeyEvent event) {
        poleDistanceField.setStyle(null);
        poleDistanceMesg.setVisible(false);
        if(poleDistanceField.getText().equals("") || Main.isNotDouble(poleDistanceField.getText())){
            poleDistanceMesg.setVisible(true);
            poleDistanceField.setStyle(ERORRTEXTFILESTYLE);
        }
    }

    // ====== Helper Functions =======================

    public void setEditMode(Equipment equipment){
        setMode(EDITMODE);
        setSelectedEquipment(equipment);
        addButton.setText("Kaydet");
        fillFields();
    }

    private void fillFields(){
        poleDistanceField.setText(String.valueOf(selectedEquipment.getPoleDistance()));
        equipmentField.setText(selectedEquipment.getEquipment());
        MPCarrierMediumField.setText(selectedEquipment.getMPCarrierMedium());
        magTechField.setText(selectedEquipment.getMagTech());
        UVLightIntensityField.setText(selectedEquipment.getUVLightIntensity());
        distanceOfLightField.setText(selectedEquipment.getDistanceOfLight());
    }

    // ====== Checking functions =====================

    private boolean areFieldsEmpty(){
        boolean correctness = false;

        if(poleDistanceField.getText().equals("") || Main.isNotDouble(poleDistanceField.getText())){
            poleDistanceMesg.setVisible(true);
            poleDistanceField.setStyle(ERORRTEXTFILESTYLE);
            correctness = true;
        }
        if(equipmentField.getText().equals("") || Main.isStringNotLegal(equipmentField.getText())){
            equipmentMesg.setVisible(true);
            equipmentField.setStyle(ERORRTEXTFILESTYLE);
            correctness = true;
        }
        if(MPCarrierMediumField.getText().equals("") || Main.isStringNotLegal(MPCarrierMediumField.getText())){
            MPCarrierMediumMesg.setVisible(true);
            MPCarrierMediumField.setStyle(ERORRTEXTFILESTYLE);
            correctness = true;
        }
        if(magTechField.getText().equals("") || Main.isStringNotLegal(magTechField.getText())){
            magTechMesg.setVisible(true);
            magTechField.setStyle(ERORRTEXTFILESTYLE);
            correctness = true;
        }
        if(UVLightIntensityField.getText().equals("") || Main.isStringNotLegal(UVLightIntensityField.getText())){
            UVLightIntensityMseg.setVisible(true);
            UVLightIntensityField.setStyle(ERORRTEXTFILESTYLE);
            correctness = true;
        }
        if(distanceOfLightField.getText().equals("") || Main.isStringNotLegal(distanceOfLightField.getText())){
            distanceOfLightMesg.setVisible(true);
            distanceOfLightField.setStyle(ERORRTEXTFILESTYLE);
            correctness = true;
        }
        return correctness;
    }

    // ====== Setters and Getters ====================

    private void resetStyle(){
        poleDistanceMesg.setVisible(false);
        poleDistanceField.setStyle(null);
        equipmentMesg.setVisible(false);
        equipmentField.setStyle(null);
        MPCarrierMediumMesg.setVisible(false);
        MPCarrierMediumField.setStyle(null);
        magTechMesg.setVisible(false);
        magTechField.setStyle(null);
        UVLightIntensityMseg.setVisible(false);
        UVLightIntensityField.setStyle(null);
        distanceOfLightMesg.setVisible(false);
        distanceOfLightField.setStyle(null);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Equipment getSelectedEquipment() {
        return selectedEquipment;
    }

    public void setSelectedEquipment(Equipment selectedEquipment) {
        this.selectedEquipment = selectedEquipment;
    }

}
