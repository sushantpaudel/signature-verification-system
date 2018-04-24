package com.sushantpaudel.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

import static com.sushantpaudel.utils.ValuesClass.*;

public class PreProcessing {
    private Stage primaryStage;

    private Image image;

    public PreProcessing() {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Image loadImageFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        fileChooser.setTitle("Open Image");
        fileChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY_PATH));
        FileChooser.ExtensionFilter extFilterImage = new FileChooser.ExtensionFilter("Image files (*.png,*.jpg)", "*.JPG", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterImage);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage);
        try {
            this.image = new Image(file.toURI().toURL().toExternalForm());
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    //GRAY-SCALE CONVERSION - by taking average
    public void convertToGrayScale() {
        Image processImage = image;
        int width = (int) processImage.getWidth();
        int height = (int) processImage.getHeight();
        WritableImage setImage = new WritableImage(width, height);
        PixelReader reader = processImage.getPixelReader();
        PixelWriter writer = setImage.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = reader.getArgb(i, j);
                int alpha = ((pixel >> 24) & 0xff);
                int red = ((pixel >> 16) & 0xff);
                int green = ((pixel >> 8) & 0xff);
                int blue = (pixel & 0xff);
                int grayLevel = (red + green + blue) / 3;
                int gray = (alpha << 24) | (grayLevel << 16) | (grayLevel << 8) | grayLevel;
                writer.setArgb(i, j, gray);
            }
        }
        image = setImage;
    }

    //NOISE REDUCTION - using median filter on each pixel
    public void noiseReduction() {
        Image processImage = image;
        int width = (int) processImage.getWidth();
        int height = (int) processImage.getHeight();
        WritableImage setImage = new WritableImage(width, height);
        PixelReader reader = processImage.getPixelReader();
        PixelWriter writer = setImage.getPixelWriter();
        int box = BOX_MEDIAN_FILTER;
        for (int i = box; i < width - box; i += box + box) {
            for (int j = box; j < height - box; j += box + box) {
                int[] values = new int[(int) Math.pow(2 * box + 1, 2)];
                int k = 0;
                for (int ii = i - box; ii <= i + box; ii++) {
                    for (int jj = j - box; jj <= j + box; jj++) {
                        values[k] = reader.getArgb(ii, jj);
                        k++;
                    }
                }
                int median = getMedian(values);
                for (int ii = i - box; ii <= i + box; ii++) {
                    for (int jj = j - box; jj <= j + box; jj++) {
                        writer.setArgb(ii, jj, median);
                    }
                }
            }
        }
        image = setImage;
    }

    //BACKGROUND ELIMINATION - by providing limit to the intensity
    public void backgroundElimination() {
        Image processImage = image;
        int width = (int) processImage.getWidth();
        int height = (int) processImage.getHeight();
        WritableImage setImage = new WritableImage(width, height);
        PixelReader reader = processImage.getPixelReader();
        PixelWriter writer = setImage.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int argb = reader.getArgb(i, j);
                int grayLevel = (argb >> 16) & 0xff;
                Color pixel;
                if (grayLevel > INTENSITY) {
                    pixel = Color.WHITE;
                } else {
                    pixel = Color.BLACK;
                }
                writer.setColor(i, j, pixel);
            }
        }
        image = setImage;
    }

    private int getMedian(int[] values) {
        for (int i = 0; i < values.length; i++) {
            for (int j = i; j < values.length; j++) {
                if (values[i] <= values[j]) {
                    int temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                }
            }
        }
        return values[values.length / 2];
    }
}