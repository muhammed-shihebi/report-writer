package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.handlers.DatabaseHandler;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        DatabaseHandler.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
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
