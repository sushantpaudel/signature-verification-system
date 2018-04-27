package com.sushantpaudel.test;

import com.sushantpaudel.utils.PreProcessing;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TestController {
    private Stage primaryStage;
    //Main  User Interface
    public ImageView imageViewInput;
    public ImageView imageViewOutput;
    public Button addImage;
    //FEATURE EXTRACTION BUTTONS
    public Button harrisAlgorithm;

    //PRE-PROCESSING BUTTONS
    public Button btnGrayScaleConversion;
    public Button btnNoiseReduction;
    public Button btnBackgroundElimination;
    public Button btnResizeImage;
    public Button btnPreProcessAllData;


    public TestController() {
    }

    public void initialize() {
        addImage.setOnMouseClicked(event -> addImageToView());
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void addImageToView() {
        PreProcessing preProcessing = new PreProcessing();
        imageViewInput.setImage(preProcessing.loadImageFromFileChooser());
        preProcessData(preProcessing);
    }

    private void preProcessData(PreProcessing preProcessing) {
        btnGrayScaleConversion.setOnMouseClicked(event -> {
            preProcessing.convertToGrayScale();
            imageViewOutput.setImage(preProcessing.getImage());
        });
        btnNoiseReduction.setOnMouseClicked(event -> {
            preProcessing.noiseReduction();
            imageViewOutput.setImage(preProcessing.getImage());
        });
        btnBackgroundElimination.setOnMouseClicked(event -> {
            preProcessing.backgroundElimination();
            imageViewOutput.setImage(preProcessing.getImage());
        });
        btnResizeImage.setOnMouseClicked(event -> {
            preProcessing.resizeImage();
            imageViewOutput.setImage(preProcessing.getImage());
        });
        btnPreProcessAllData.setOnMouseClicked(event -> {
            preProcessing.preProcessingAllPart();
            imageViewOutput.setImage(preProcessing.getImage());
        });

    }


}