package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.handlers.DatabaseHandler;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class Main extends Application {

    public static final int MAXSTRINGSIZE = 50;

    public static boolean isStringNotLegal(String str){
        return str.length() > MAXSTRINGSIZE;
    }

    public static boolean isNotDouble(String myString){
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
                        "(\\."+Digits+"("+Exp+")?)|"+

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        return !Pattern.matches(fpRegex, myString);
    }

    @Override
    public void init() throws Exception {
        DatabaseHandler.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/main.fxml"));
        primaryStage.setTitle("Oturum aç");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    @Override
    public void stop() throws SQLException {
        DatabaseHandler.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
