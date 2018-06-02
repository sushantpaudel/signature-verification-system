package com.sushantpaudel.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.ArrayList;

public class FeatureExtraction {
    private Image image;

    public FeatureExtraction() {

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Integer> extractEucledianFeature() {
        ArrayList<Integer> listOfFeatures = new ArrayList<>();
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int centreX = width / 2;
        int centreY = height / 2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = reader.getArgb(i, j);
                int red = (pixel >> 16) & 0Xff;
                if (red == 0) {
                    listOfFeatures.add(getEucledianDistance(i, j, centreX, centreY));
                }
            }
        }
        return listOfFeatures;
    }

    public int[] centreOfMass() {
        int[] XY = new int[2];
        XY[0] = 0;
        XY[1] = 0;
        int count = 0;
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = reader.getArgb(i, j);
                int red = (pixel >> 16) & 0Xff;
                if (red == 0) {
                    XY[0] += i;
                    XY[1] += j;
                    count++;
                }
            }
        }
        XY[0] = XY[0] / count;
        XY[1] = XY[1] / count;
        return XY;
    }

    private Integer getEucledianDistance(int x1, int y1, int x2, int y2) {
        return (Integer) (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}
