package com.sushantpaudel.test;

import com.sushantpaudel.utils.FeatureExtraction;
import com.sushantpaudel.utils.HarrisAlgorithm;
import com.sushantpaudel.utils.PreProcessing;
import com.sushantpaudel.utils.PredictClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.stage.Stage;
import org.opencv.core.Mat;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

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
    public Button btnFeatureList;

    //Prediction
    public Button btnPredict;
    public Label labelPredictionOutput;

    //PRE-PROCESSING BUTTONS
    private PreProcessing preProcessing;
    public Button btnGrayScaleConversion;
    public Button btnNoiseReduction;
    public Button btnBackgroundElimination;
    public Button btnCrop;
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
        btnPredict.setOnMouseClicked(event -> predictSign());
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void addImageToView() {
        preProcessing = new PreProcessing();
        imageViewInput.setImage(preProcessing.loadImageFromFileChooser());
        preProcessData(preProcessing);
        featureExtraction();
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
        btnCrop.setOnMouseClicked(event -> {
            preProcessing.cropImage();
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


    /*
     * ░████░████░░░░█░░░█████░░█░░█░░███░░░░████░░
     * ░█░░░░█░░░░░█░█░░░░█░░░░█░░█░░█░░█░░░█░░░░░
     * ░███░░███░░░████░░░█░░░░░█░░█░░███░░░░███░░░
     * ░█░░░░█░░░░░█░░█░░░█░░░░█░░█░░█░░█░░░█░░░░░
     * ░█░░░░████░░█░░█░░░█░░░░░██░░░█░░░█░░░████░░
     *
     */


    //List of Features
    private HashMap<String, ArrayList<ArrayList<Float>>> listOfFeaturesOfAllImage = new HashMap<>();

    private void featureExtraction() {
        harrisAlgorithm();
        listOfFeatures();
    }

    //HARRIS ALGORITHM
    private void harrisAlgorithm() {
        HarrisAlgorithm algorithm = new HarrisAlgorithm();
        algorithm.setImagePath(preProcessing.getImagePath());
        btnHarrisAlgorithm.setOnMouseClicked(event -> {
            Mat mat = algorithm.Harris(algorithm.convertImageToMat());
            algorithm.convertMatToImage(mat);
            imageViewOutput.setImage(algorithm.getImage());
        });
    }

    //Calculation of the centre of mass of the signature by looking for the black pixel
    private void listOfFeatures() {
        btnFeatureList.setOnMouseClicked(event -> {
            System.out.println("Button Clicked");
            FeatureExtraction featureExtraction = new FeatureExtraction();
            featureExtraction.setImage(preProcessing.getImage());
            ArrayList<Float> arrayList = featureExtraction.getListOfFeatures();
            System.out.println("---------------------");
            System.out.println("List of features: " + "(Count : " + arrayList.size() + ")");
            int count = 1;
            for (Float i : arrayList) {
                System.out.println("F" + count + " -> " + i);
                count++;
            }
            System.out.println("---------------------");
        });
    }

    private void traverseStorageFeature(String path) {
        File mainFile = new File(path);
        File[] listOfFiles = mainFile.listFiles();
        String directoryName = mainFile.getName();
        if (listOfFiles != null) {
            for (File f : listOfFiles) {
                if (f.isDirectory()) {
                    traverseStorageFeature(f.getPath());
                } else {
                    Image image = getImageFromFile(f);
                    FeatureExtraction featureExtraction = new FeatureExtraction();
                    featureExtraction.setImage(image);
                    if (listOfFeaturesOfAllImage.containsKey(directoryName)) {
                        ArrayList<ArrayList<Float>> listOfFeatures = listOfFeaturesOfAllImage.get(directoryName);
                        listOfFeatures.add(featureExtraction.getListOfFeatures());
                    } else {
                        ArrayList<ArrayList<Float>> listOfFeatures = new ArrayList<>();
                        listOfFeaturesOfAllImage.put(directoryName, listOfFeatures);
                    }
                }
            }
        }
    }

    private Image getImageFromFile(File file) {
        try {
            return new Image(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void testImage() {
        Image testImage = imageViewOutput.getImage();
        int WIDTH = (int) testImage.getWidth();
        int HEIGHT = (int) testImage.getHeight();
        WritableImage writableImage = new WritableImage(WIDTH, HEIGHT);
        PixelReader reader = testImage.getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

    }

    private void predictSign() {
        PredictClass predict = new PredictClass();
        preProcessing.preProcessingAllPart();
        imageViewOutput.setImage(preProcessing.getImage());
        preProcessing.saveImage();
        predict.setName(preProcessing.getImageName());
        predict.setPath(preProcessing.getSavedImagePath());
        String prediction = predict.startPrediction();
        labelPredictionOutput.setText(prediction);
    }

}