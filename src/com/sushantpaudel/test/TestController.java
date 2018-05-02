package com.sushantpaudel.test;

import com.sushantpaudel.utils.HarrisAlgorithm;
import com.sushantpaudel.utils.PreProcessing;
import com.sushantpaudel.utils.ValuesClass;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TestController {
    private Stage primaryStage;
    //Main  User Interface
    public ImageView imageViewInput;
    public ImageView imageViewOutput;
    public Button btnAddImage;

    //FEATURE EXTRACTION BUTTONS
    public Button btnHarrisAlgorithm;

    //PRE-PROCESSING BUTTONS
    private PreProcessing preProcessing;
    public Button btnGrayScaleConversion;
    public Button btnNoiseReduction;
    public Button btnBackgroundElimination;
    public Button btnResizeImage;
    public Button btnPreProcessAllData;
    public Button btnReset;
    public Button btnSaveImage;


    public TestController() {
    }

    public void initialize() {
        btnAddImage.setOnMouseClicked(event -> addImageToView());
        btnReset.setOnMouseClicked(event -> preProcessing.setImage(imageViewInput.getImage()));
        btnSaveImage.setOnMouseClicked(event -> preProcessing.saveImage());
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void addImageToView() {
        preProcessing = new PreProcessing();
        imageViewInput.setImage(preProcessing.loadImageFromFileChooser());
        preProcessData(preProcessing);
        harrisAlgorithm();
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


    //Implementation of Harris Algorithm
    private void harrisAlgorithm() {
        PreProcessing harrisPreProcessing = new PreProcessing();
        harrisPreProcessing.setImage(imageViewInput.getImage());
        harrisPreProcessing.preProcessingAllPart();
        Image image = preProcessing.getImage();
        HarrisAlgorithm algorithm = new HarrisAlgorithm();
        btnHarrisAlgorithm.setOnMouseClicked(event -> {
            traverseStorageHarrisAlgorithm(ValuesClass.PRE_PROCESSED_DATA_DIRECTORY_PATH);
        });
    }

    private void traverseStorageHarrisAlgorithm(String path) {

    }


}