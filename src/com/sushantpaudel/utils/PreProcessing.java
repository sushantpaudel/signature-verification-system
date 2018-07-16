package com.sushantpaudel.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static com.sushantpaudel.utils.ValuesClass.*;

public class PreProcessing {
    private Stage primaryStage;
    private Image image;
    private String parentFilePath = "";
    private String imageName = "";
    private String wholePath = "";


    public PreProcessing() {

    }

    public String getImageName() {
        return imageName;
    }

    public String getImagePath() {
        return wholePath;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Image loadImageFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        fileChooser.setTitle("Open Image");
        fileChooser.setInitialDirectory(new File(CHECK_DATA_DIRECTORY_PATH));
        FileChooser.ExtensionFilter extFilterImage = new FileChooser.ExtensionFilter("Image files (*.png,*.jpg)", "*.JPG", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterImage);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage);
        wholePath = file.getPath();
        parentFilePath = file.getParentFile().getPath();
        imageName = file.getName();
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

    public void preProcessingAllPart() {
        convertToGrayScale();
        backgroundElimination();
        noiseReduction();
        cropImage();
        resizeImage();
    }

    //GRAY-SCALE CONVERSION - by taking average
    public void convertToGrayScale() {
        Image processImage = image;
        int width = (int) processImage.getWidth();
        int height = (int) processImage.getHeight();
        int totalPixel = width * height;
        int sumOfValues = 0;
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
                sumOfValues += grayLevel;
                int gray = (alpha << 24) | (grayLevel << 16) | (grayLevel << 8) | grayLevel;
                writer.setArgb(i, j, gray);
            }
        }
        INTENSITY = sumOfValues / totalPixel;
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

    //CROPPING IMAGE
    public void cropImage() {
        Image processImage = image;
        int width = (int) processImage.getWidth();
        int height = (int) processImage.getHeight();
        PixelReader reader = processImage.getPixelReader();
        int box = BOX_MEDIAN_FILTER;
        int leftLimit = 0;
        int rightLimit = 0;
        int topLimit = 0;
        int bottomLimit = 0;
        int totalLimit = 255 * 6;
        //Checking Left Limit
        outer:
        for (int i = box; i < width - box; i += box + box) {
            for (int j = box; j < height - box; j += box + box) {
                int pixel = reader.getArgb(i, j);
                int red = ((pixel >> 16) & 0xff);
                if (red == 0) {
                    leftLimit = i;
                    break outer;
                }
            }
        }
        //Checking Right Limit
        outer:
        for (int i = width - 1; i >= 0; i--) {
            for (int j = 0; j < height; j++) {
                int pixel = reader.getArgb(i, j);
                int red = ((pixel >> 16) & 0xff);
                if (red == 0) {
                    rightLimit = i;
                    break outer;
                }
            }
        }
        //Checking Top Limit
        outer:
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = reader.getArgb(j, i);
                int red = ((pixel >> 16) & 0xff);
                if (red == 0) {
                    topLimit = i;
                    break outer;
                }
            }
        }
        //Checking Bottom Limit
        outer:
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                int pixel = reader.getArgb(j, i);
                int red = ((pixel >> 16) & 0xff);
                if (red == 0) {
                    bottomLimit = i;
                    break outer;
                }
            }
        }
        int newWidth = rightLimit - leftLimit + 1;
        int newHeight = bottomLimit - topLimit + 1;
        WritableImage setImage = new WritableImage(newWidth, newHeight);
        PixelWriter writer = setImage.getPixelWriter();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                int argb = reader.getArgb(i + leftLimit, j + topLimit);
                writer.setArgb(i, j, argb);
            }
        }
        image = setImage;
    }

    //RESIZE IMAGE - resizing image to a standard size for FEATURE EXTRACTION
    public void resizeImage() {
        PixelReader reader = image.getPixelReader();
        int totalWidth = (int) image.getWidth();
        int totalHeight = (int) image.getHeight();
//        IMG_WIDTH = (int) (totalWidth * IMG_HEIGHT / (float) totalHeight);
        WritableImage newImage = new WritableImage(IMG_WIDTH, IMG_HEIGHT);
        PixelWriter writer = newImage.getPixelWriter();
        for (int i = 0; i < totalWidth; i++) {
            for (int j = 0; j < totalHeight; j++) {
                float width = IMG_WIDTH * i / totalWidth;
                float height = IMG_HEIGHT * j / totalHeight;
                writer.setArgb((int) width, (int) height, reader.getArgb(i, j));
            }
        }
        image = newImage;
    }

    public void saveImage() {
        File newFile = new File(parentFilePath + "\\saved");
        if (!newFile.exists()) {
            boolean dirCreated = newFile.mkdir();
            if (!dirCreated) {
                return;
            }
            saveImageTo(newFile.getPath(), imageName, image);
        } else {
            saveImageTo(newFile.getPath(), imageName, image);
        }
    }

    public String getSavedImagePath() {
        return parentFilePath + "\\saved\\" + imageName;
    }

    //SAVING IMAGE TO the file
    public void saveImageTo(String parent, String fileName, Image image) {
        File imageSaved = new File(parent + "/" + fileName);
        System.out.println(imageSaved.getPath() + " saved!");
        String format = "png";
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), format, imageSaved);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
