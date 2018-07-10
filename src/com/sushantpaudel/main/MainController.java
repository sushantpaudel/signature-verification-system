package com.sushantpaudel.main;

import com.sushantpaudel.test.TestController;
import com.sushantpaudel.train.TrainController;
import com.sushantpaudel.utils.PreProcessing;
import com.sushantpaudel.utils.StringClass;
import com.sushantpaudel.utils.ValuesClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

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
        home.setText(home.getText() + " >");
    }

    private void trainClicked() {
        System.out.println("__________________ Train Clicked! __________________");
        clearAll();
        trainAnchorPane.setVisible(true);
        train.setText(train.getText() + " >");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../train/Train.fxml"));
        try {
            Parent nextWindow = fxmlLoader.load();
            Stage stage = new Stage();
            TrainController controller = new TrainController();
            controller.setStage(stage);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(nextWindow));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testClicked() {
        System.out.println("__________________ Test Clicked! __________________");
        clearAll();
        testAnchorPane.setVisible(true);
        test.setText(test.getText() + " >");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../test/Test.fxml"));
        try {
            Parent nextWindow = fxmlLoader.load();
            Stage stage = new Stage();
            TestController controller = new TestController();
            controller.setStage(stage);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(nextWindow));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            preProcessing.resizeImage();
            imageViewPreProcessing.setImage(preProcessing.getImage());
            System.err.println("Finished!");
        });
        btnPreProcessingAllData.setOnMouseClicked(event -> preProcessAllData());
    }

    private void aboutUsClicked() {
        System.out.println("__________________ About Us Clicked! __________________");
        clearAll();
        aboutUsAnchorPane.setVisible(true);
        aboutUs.setText(aboutUs.getText() + " >");
    }

    /*
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░███░░░███░░░░████░░███░░░███░░░░░███░░░░███░░░████░░░████░░░████░░
     * ░█░░█░░█░░█░░░█░░░░░█░░█░░█░░█░░░█░░█░░░█░░░░░█░░░░░░█░░░░░░█░░░░░
     * ░███░░░███░░░░███░░░███░░░███░░░░█░░░█░░█░░░░░░███░░░░████░░░████░░
     * ░█░░░░░█░░█░░░█░░░░░█░░░░░█░░█░░░█░░█░░░█░░░░░█░░░░░░░░░█░░░░░░█░░
     * ░█░░░░░█░░░█░░████░░█░░░░░█░░░█░░░███░░░░███░░░████░░░████░░░████░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     */

    private void preProcessAllData() {
//        traverseStorage(ValuesClass.RAW_DATA_DIRECTORY_PATH);
        traverseStorage(rawDataPath());
        System.out.println("Finished!!!");
    }

    private String rawDataPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(ValuesClass.RAW_DATA_DIRECTORY_PATH));
        directoryChooser.setTitle("Raw data directory path");
        File file = directoryChooser.showDialog(primaryStage);
        return file.getAbsolutePath();
    }

    private void traverseStorage(String path) {
        File mainFile = new File(path);
        File[] list = mainFile.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    traverseStorage(f.getPath());
                } else {
                    String parent = mainFile.getName();
                    String fileName = f.getName();
                    File parentSaveDirectory = new File(ValuesClass.PRE_PROCESSED_DATA_DIRECTORY_PATH + "/" + parent);
                    if (!parentSaveDirectory.exists()) {
                        boolean dirCreated = parentSaveDirectory.mkdir();
                        if (!dirCreated) {
                            System.err.println(parent + " directory is not created!");
                            return;
                        }
                    }
                    PreProcessing preProcessing = new PreProcessing();
                    preProcessing.setImage(getImageFromFile(f));
                    preProcessing.preProcessingAllPart();
                    preProcessing.saveImageTo(parentSaveDirectory.getPath(), fileName, preProcessing.getImage());
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
}
