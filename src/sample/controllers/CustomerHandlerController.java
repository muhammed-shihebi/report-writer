package sample.controllers;

public class CustomerHandlerController {
    private static final String ERORRTEXTFILESTYLE = "-fx-border-color: red;";
    public static final int ADDMODE = 1;
    public static final int EDITMODE = 2;
    private int mode;

    public void setEditMode(){
        // TODO apply changes according to edit mode
    }

    // ====== Setters and Getters ====================

    public void setMode(int mode) {
        this.mode = mode;
    }
}
