package com.sushantpaudel.test;

import com.sushantpaudel.utils.FeatureExtraction;
import com.sushantpaudel.utils.HarrisAlgorithm;
import com.sushantpaudel.utils.PreProcessing;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.stage.Stage;
import org.opencv.core.Mat;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

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
    public Button btnFeatureDistance;
    public Button btnFeatureCentreOfMass;

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

    private void featureExtraction() {
        harrisAlgorithm();
        eucledianDistance();
        centreOfMass();
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

    //CALCULATION OF EUCLEDIAN DISTANCE to the pixel with black value FROM THE CENTRE OF THE IMAGE
    private void eucledianDistance() {
        btnFeatureDistance.setOnMouseClicked(event -> {
            System.out.println("Button Clicked");
            FeatureExtraction featureExtraction = new FeatureExtraction();
            featureExtraction.setImage(preProcessing.getImage());
            ArrayList<Integer> list = featureExtraction.extractEucledianFeature();
            System.out.println(list.size());
            for (Integer i : list) {
                System.out.print(i + " , ");
            }
//            traverseStorageEucledianDistance(ValuesClass.PRE_PROCESSED_DATA_DIRECTORY_PATH);
        });
    }

    private ArrayList<ArrayList<Integer>> featureListEucledian = new ArrayList<>();

    private void traverseStorageEucledianDistance(String path) {
        File mainFile = new File(path);
        File[] list = mainFile.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    traverseStorageEucledianDistance(f.getPath());
                } else {
                    Image image = getImageFromFile(f);
                    FeatureExtraction featureExtraction = new FeatureExtraction();
                    featureExtraction.setImage(image);
                    featureListEucledian.add(featureExtraction.extractEucledianFeature());
                }
            }
        }
    }

    //Calculation of the centre of mass of the signature by looking for the black pixel
    private ArrayList<int[]> featureListCentreOfMass = new ArrayList<>();

    private void centreOfMass() {
        btnFeatureCentreOfMass.setOnMouseClicked(event -> {
            System.out.println("Button Clicked");
            FeatureExtraction featureExtraction = new FeatureExtraction();
            featureExtraction.setImage(preProcessing.getImage());
            int[] XY = featureExtraction.centreOfMass();
            System.out.println("(x,y) -> (" + XY[0] + "," + XY[1] + ")");
//            traverseStorageCentreOfMass(ValuesClass.PRE_PROCESSED_DATA_DIRECTORY_PATH);
        });
    }

    private void traverseStorageCentreOfMass(String path) {
        File mainFile = new File(path);
        File[] list = mainFile.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    traverseStorageEucledianDistance(f.getPath());
                } else {
                    Image image = getImageFromFile(f);
                    FeatureExtraction featureExtraction = new FeatureExtraction();
                    featureExtraction.setImage(image);
                    featureListCentreOfMass.add(featureExtraction.centreOfMass());
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

}