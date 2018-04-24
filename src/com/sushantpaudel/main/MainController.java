package com.sushantpaudel.main;

import com.sushantpaudel.utils.PreProcessing;
import com.sushantpaudel.utils.StringClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {
    private Stage primaryStage;

    //Main Variables
    public Label home;
    public Label train;
    public Label test;
    public Label preProcessing;
    public Label aboutUs;
    public Label textHome;
    public Label exitLabel;
    public AnchorPane homeAnchorPane;
    public AnchorPane trainAnchorPane;
    public AnchorPane testAnchorPane;
    public AnchorPane aboutUsAnchorPane;
    public AnchorPane preProcessingAnchorPane;


    //Preprocessing variables
    public Button btnAddImage;
    public Button btnPreProcessing;
    public Button btnPreProcessingAllData;
    public ImageView imageViewPreProcessing;

    public MainController() {
    }

    public void initialize() {
        //Main Controls
        exitLabel.setOnMouseClicked(event -> System.exit(0));
        textHome.setText(StringClass.homeDescription);
        home.setOnMouseClicked(event -> dashboardClicked());
        train.setOnMouseClicked(event -> trainClicked());
        test.setOnMouseClicked(event -> testClicked());
        preProcessing.setOnMouseClicked(event -> preProcessingClicked());
        aboutUs.setOnMouseClicked(event -> aboutUsClicked());
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void clearAll() {
        home.setText(StringClass.home);
        train.setText(StringClass.train);
        test.setText(StringClass.test);
        preProcessing.setText(StringClass.preProcessing);
        aboutUs.setText(StringClass.about_us);
        homeAnchorPane.setVisible(false);
        trainAnchorPane.setVisible(false);
        testAnchorPane.setVisible(false);
        preProcessingAnchorPane.setVisible(false);
        aboutUsAnchorPane.setVisible(false);
    }

    private void dashboardClicked() {
        System.out.println("__________________ Dashboard Clicked! __________________");
        clearAll();
        homeAnchorPane.setVisible(true);
        home.setText(home.getText()+" >");
    }

    private void trainClicked() {
        System.out.println("__________________ Train Clicked! __________________");
        clearAll();
        trainAnchorPane.setVisible(true);
        train.setText(train.getText()+" >");
    }

    private void testClicked() {
        System.out.println("__________________ Test Clicked! __________________");
        clearAll();
        testAnchorPane.setVisible(true);
        test.setText(test.getText()+" >");
    }

    private void preProcessingClicked() {
        System.out.println("__________________ Pre-processing Clicked! __________________");
        clearAll();
        preProcessingAnchorPane.setVisible(true);
        preProcessing.setText(preProcessing.getText() + " >");
        PreProcessing preProcessing = new PreProcessing();
        preProcessing.setPrimaryStage(primaryStage);
        btnAddImage.setOnMouseClicked(event -> {
            Image image = preProcessing.loadImageFromFileChooser();
            imageViewPreProcessing.setImage(image);
        });
        btnPreProcessing.setOnMouseClicked(event -> {
            preProcessing.setImage(imageViewPreProcessing.getImage());
            preProcessing.convertToGrayScale();
            preProcessing.noiseReduction();
            preProcessing.backgroundElimination();
            imageViewPreProcessing.setImage(preProcessing.getImage());
            System.err.println("Finished!");
        });
    }

    private void aboutUsClicked() {
        System.out.println("__________________ About Us Clicked! __________________");
        clearAll();
        aboutUsAnchorPane.setVisible(true);
        aboutUs.setText(aboutUs.getText()+" >");
    }
}