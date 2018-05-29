package com.sushantpaudel;

import com.sushantpaudel.main.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.opencv.core.Core;

import java.io.IOException;

public class BaseClass extends Application {
    private Stage primaryStage;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        startMain();
    }

    private void startMain(){
        FXMLLoader loader = new FXMLLoader(BaseClass.class.getResource("main/Main.fxml"));
        try {
            AnchorPane pane = loader.load();
            MainController controller = new MainController();
            controller.setStage(primaryStage);
            primaryStage.setTitle("Signature Verification System");
            primaryStage.setScene(new Scene(pane));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
