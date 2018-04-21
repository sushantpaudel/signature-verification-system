package com.sushantpaudel.main;

import com.sushantpaudel.utils.PreProcessing;
import com.sushantpaudel.utils.StringClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class MainController {

    //Main Variables
    public Label home;
    public Label train;
    public Label test;
    public Label preprocessing;
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
    public Button btnPreprocessingAllData;
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
        preprocessing.setOnMouseClicked(event -> preprocessingClicked());
        aboutUs.setOnMouseClicked(event -> aboutUsClicked());
    }

    private void clearAll() {
        home.setText(StringClass.home);
        train.setText(StringClass.train);
        test.setText(StringClass.test);
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

    private void preprocessingClicked() {
        System.out.println("__________________ Preprocessing Clicked! __________________");
        clearAll();
        preProcessingAnchorPane.setVisible(true);
        preprocessing.setText(preprocessing.getText()+" >");
        PreProcessing preProcessing = new PreProcessing();
        btnAddImage.setOnMouseClicked(event -> {

        });
    }

    private void aboutUsClicked() {
        System.out.println("__________________ About Us Clicked! __________________");
        clearAll();
        aboutUsAnchorPane.setVisible(true);
        aboutUs.setText(aboutUs.getText()+" >");
    }



}
