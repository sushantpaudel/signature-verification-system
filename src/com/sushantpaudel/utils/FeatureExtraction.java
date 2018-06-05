package com.sushantpaudel.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.ArrayList;

public class FeatureExtraction {
    private Image image;

    public FeatureExtraction() {

    }


    /*
     *	F1  - Height to width Ratio
     *          -> Ratio of the number of rows in the image to the number of columns in the image
     *          -> F1 = image.height() / image.width()
     *
     *	F2  - Signature Occupancy Ratio
     *          -> Ratio of the no of pixel fall on signature to the total number of pixels
     *          -> F2 = no of black pixels / total no of pixels
     *
     *	F3  - Adjacency Ratio
     *          -> Find the position of the first black pixel in each column from the top and store in the 1D Array (LT).
     *             Then, find the position of the first black pixel in each column from the bottom and store in the 1D Array (LB).
     *          -> F3 = (sum of all elements of LT/ sum of all elements of LB) * signature occupancy ratio
     *
     *	F4,F5  - White Pixel Ratio
     *          -> The image in then divided virtually into two halves, left and right
     *          -> The ratio of the number of white pixels in one half to the total image is calculated
     *          -> F4 = (number of white pixels in the 1st half of negative image / number of white pixels in the whole negative image)
     *          -> F5 = (number of white pixels in the 2nd half of negative image / number of white pixels in the whole negative image)
     *
     *	F6,F7  - Corner ratio using Harris Corner Method
     *          -> The ratio of the Number of corners detected in one half of the image to the number of corners in the
     *             whole negative image is detected and extracted as a feature
     *          -> F6 = (number of corners in the 1st half of negative image / number of black pixels in the whole negative image)
     *          -> F7 = (number of corners in the 2nd half of negative image / number of black pixels in the whole negative image
     *
     *	F8,F9  - Coordinates of the Centre of mass of the image
     *          -> Centre of mass is the centre of the pixel where the signature falls
     *          -> i.e. The centroid of the black pixels where signature falls
     *          -> F8 = average x coordinate values of all signature pixels
     *          -> F9 = average y coordinate values of all signature pixels
     *
     *	F10 - Slope of the line joining the Centre of masses of the two halves of the signature image
     *          -> The slope of the line joining the centre of masses of the two halves of the signature image is calculated
     *             and extracted as a feature. The two centre of masses being (x1, y1) and (x2, y2)
     *          -> F10 = (y2 - y1) / (x2 - x1)
     *
     *	F11 - Distance of the line joining the centre of masses of the two halves of the signature
     *          -> The distance between the centre of masses of the two halves of the signature image is calculated
     *             and extracted as a feature. The two centre of masses being (x1, y1) and (x2, y2)
     *          -> F11 = sqrt((x2-x1)2 + (y2-y1)2)
     */

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Float> getListOfFeatures() {
        ArrayList<Float> list = new ArrayList<>();
        list.add(F1());
        list.add(F2());
        list.add(F3());
        list.add(F4());
        list.add(F5());
        list.add(F6());
        list.add(F7());
        list.add(F8());
        list.add(F9());
        list.add(F10());
        list.add(F11());
//        System.out.println("F1 -> "+F1());
//        System.out.println("F2 -> "+F2());
//        System.out.println("F3 -> "+F3());
//        System.out.println("F4 -> "+F4());
//        System.out.println("F5 -> "+F5());
//        System.out.println("F6 -> "+F6());
//        System.out.println("F7 -> "+F7());
//        System.out.println("F8 -> "+F8());
//        System.out.println("F9 -> "+F9());
//        System.out.println("F10 -> "+F10());
//        System.out.println("F11 -> "+F11());
        return list;
    }

    //F1  - Height to width Ratio
    private float F1() {
        return heightToWidthRatio();
    }

    private float heightToWidthRatio() {
        return (float) (image.getHeight() / image.getWidth());
    }

    //F2  - Signature Occupancy Ratio
    private float F2() {
        return signatureOccupancyRatio();
    }

    private float signatureOccupancyRatio() {
        int totalNoOfPixels = (int) (image.getHeight() * image.getWidth());
        int totalNoOfBlackPixels = 0;
        PixelReader reader = image.getPixelReader();
        for (int i = 0; i < (int) image.getHeight(); i++) {
            for (int j = 0; j < (int) image.getWidth(); j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    totalNoOfBlackPixels++;
                }
            }
        }
        return (float) totalNoOfBlackPixels / totalNoOfPixels;
    }

    //F3  - Adjacency Ratio
    private float F3() {
        return adjacencyRatio();
    }

    private float adjacencyRatio() {
        int sumLT = 0;
        int sumLB = 1;
        PixelReader reader = image.getPixelReader();
        for (int i = 0; i < (int) image.getWidth(); i++) {
            for (int j = 0; j < (int) image.getHeight(); j++) {
                int argb = reader.getArgb(i, j);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    sumLT += j + 1;
                    break;
                }
            }
        }
        for (int i = 0; i < (int) image.getWidth(); i++) {
            for (int j = (int) image.getHeight() - 1; j >= 0; j--) {
                int argb = reader.getArgb(i, j);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    sumLB += j + 1;
                    break;
                }
            }
        }
        return (float) sumLT / sumLB * signatureOccupancyRatio();
    }

    //F4,F5  - White Pixel Ratio (As this Ratio is for negative image so without converting our image we take black pixel)
    private float F4() {
        return whitePixelRatio()[0];
    }

    private float F5() {
        return whitePixelRatio()[1];
    }

    private float[] whitePixelRatio() {
        float[] ratio = new float[2];
        int firstHalf = 0;
        int secondHalf = 0;
        int width = (int) image.getWidth();
        PixelReader reader = image.getPixelReader();
        for (int i = 0; i < (int) image.getHeight(); i++) {
            for (int j = 0; j < width / 2; j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    firstHalf++;
                }
            }
        }
        for (int i = 0; i < (int) image.getHeight(); i++) {
            for (int j = width / 2; j < width; j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    secondHalf++;
                }
            }
        }
        ratio[0] = (float) firstHalf / (firstHalf + secondHalf);
        ratio[1] = (float) secondHalf / (firstHalf + secondHalf);
        return ratio;
    }

    //F6,F7  - Corner ratio using Harris Corner Method
    private float F6() {
        return harrisCornerMethod()[0];
    }

    private float F7() {
        return harrisCornerMethod()[1];
    }

    private float[] harrisCornerMethod() {
        float[] ratio = new float[2];
        ratio[0] = 0;
        ratio[1] = 0;
        return ratio;
    }

    //F8,F9  - Coordinates of the Centre of mass of the image
    private float F8() {
        return centreOfMass()[0];
    }

    private float F9() {
        return centreOfMass()[1];
    }

    private float[] centreOfMass() {
        float[] XY = new float[2];
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

    //F10 - Slope of the line joining the Centre of masses of the two halves of the signature image
    private float F10() {
        return slopeOfCentreOfMasses();
    }

    private float slopeOfCentreOfMasses() {
        float[] X = new float[2];
        X[0] = 0;
        X[1] = 0;
        int count = 0;
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width / 2; j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    X[0] += i;
                    X[1] += j;
                    count++;
                }
            }
        }
        X[0] = X[0] / count;
        X[1] = X[1] / count;
        float[] Y = new float[2];
        Y[0] = 0;
        Y[1] = 0;
        count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = width / 2; j < width; j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    Y[0] += i;
                    Y[1] += j;
                    count++;
                }
            }
        }
        Y[0] = Y[0] / count;
        Y[1] = Y[1] / count;
        return (Y[1] - X[1]) / (Y[0] - X[0]);
    }

    //F11 - Distance of the line joining the centre of masses of the two halves of the signature
    private float F11() {
        return distanceBetweenCentreOfMasses();
    }

    private float distanceBetweenCentreOfMasses() {
        int[] X = new int[2];
        X[0] = 0;
        X[1] = 0;
        int count = 0;
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width / 2; j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    X[0] += i;
                    X[1] += j;
                    count++;
                }
            }
        }
        X[0] = X[0] / count;
        X[1] = X[1] / count;
        int[] Y = new int[2];
        Y[0] = 0;
        Y[1] = 0;
        count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = width / 2; j < width; j++) {
                int argb = reader.getArgb(j, i);
                int red = (argb >> 16) & 0xff;
                if (red == 0) {
                    Y[0] += i;
                    Y[1] += j;
                    count++;
                }
            }
        }
        Y[0] = Y[0] / count;
        Y[1] = Y[1] / count;
        return getEucledianDistance(X[0], X[1], Y[0], Y[1]);
    }


    private float getEucledianDistance(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}
