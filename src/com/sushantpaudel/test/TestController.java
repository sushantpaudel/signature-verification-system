package com.sushantpaudel.test;

import com.sushantpaudel.utils.HarrisAlgorithm;
import com.sushantpaudel.utils.PreProcessing;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.stage.Stage;
import org.opencv.core.Mat;

public class TestController {
    private Stage primaryStage;
    //Main  User Interface
    public ImageView imageViewInput;
    public ImageView imageViewOutput;
    public Button btnAddImage;

    //Extra Buttons
    public Button btnTest;
    public Button btnCopy;
    public Button btnReset;
    public Button btnSaveImage;

    //FEATURE EXTRACTION BUTTONS
    public Button btnHarrisAlgorithm;

    //PRE-PROCESSING BUTTONS
    private PreProcessing preProcessing;
    public Button btnGrayScaleConversion;
    public Button btnNoiseReduction;
    public Button btnBackgroundElimination;
    public Button btnResizeImage;
    public Button btnPreProcessAllData;


    public TestController() {
    }

    public void initialize() {
        btnAddImage.setOnMouseClicked(event -> addImageToView());
        btnReset.setOnMouseClicked(event -> preProcessing.setImage(imageViewInput.getImage()));
        btnSaveImage.setOnMouseClicked(event -> preProcessing.saveImage());
        btnTest.setOnMouseClicked(event -> testImage());
        btnCopy.setOnMouseClicked(event -> imageViewOutput.setImage(preProcessing.getImage()));
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
        HarrisAlgorithm algorithm = new HarrisAlgorithm();
        algorithm.setImagePath(preProcessing.getImagePath());
        btnHarrisAlgorithm.setOnMouseClicked(event -> {
            Mat mat = algorithm.Harris(algorithm.convertImageToMat());
            algorithm.convertMatToImage(mat);
            imageViewOutput.setImage(algorithm.getImage());
        });
    }

    private void traverseStorageHarrisAlgorithm(String path) {

    }

    private void testImage() {
        Image testImage = imageViewOutput.getImage();
        int WIDTH = (int) testImage.getWidth();
        int HEIGHT = (int) testImage.getHeight();
        WritableImage writableImage = new WritableImage(WIDTH, HEIGHT);
        PixelReader reader = testImage.getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

    }


}