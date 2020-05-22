package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.handlers.DatabaseHandler;
import sample.model.Equipment;

import java.sql.SQLException;
import java.util.regex.Pattern;


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


    // ====== On Action =======================

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

    public static boolean isDouble(String myString){
        final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =
                ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
                        "[+-]?(" +         // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from the Java Language Specification, 2nd
                        // edition, section 3.10.2.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.("+Digits+")("+Exp+")?)|"+

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        return Pattern.matches(fpRegex, myString);
    }

    // ====== Emptiness checking functions =======

    private boolean areFieldsEmpty(){
        boolean emptiness = false;

        if(poleDistanceField.getText().equals("") || !isDouble(poleDistanceField.getText())){
            poleDistanceMesg.setVisible(true);
            poleDistanceField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(equipmentField.getText().equals("")){
            equipmentMesg.setVisible(true);
            equipmentField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(MPCarrierMediumField.getText().equals("")){
            MPCarrierMediumMesg.setVisible(true);
            MPCarrierMediumField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(magTechField.getText().equals("")){
            magTechMesg.setVisible(true);
            magTechField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(UVLightIntensityField.getText().equals("")){
            UVLightIntensityMseg.setVisible(true);
            UVLightIntensityField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(distanceOfLightField.getText().equals("")){
            distanceOfLightMesg.setVisible(true);
            distanceOfLightField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        return emptiness;
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
