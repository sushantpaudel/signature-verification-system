package com.sushantpaudel.utils;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HarrisAlgorithm {

    public HarrisAlgorithm() {

    }

    private String imagePath;
    private Image image;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public Mat convertImageToMat() {
        return Imgcodecs.imread(imagePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
    }

    public void convertMatToImage(Mat scene) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", scene, mob);
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(mob.toArray()));
            image = Image.impl_fromPlatformImage(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mat Harris(Mat Scene) {
        Image testImage = image;
        Mat Destination = new Mat(Scene.rows(), Scene.cols(), CvType.CV_32FC1);
        Imgproc.cornerEigenValsAndVecs(Scene, Destination, 3, 1, Core.BORDER_DEFAULT);
        return Destination;
    }


    public void printMat() {
        Mat mat = convertImageToMat();
        System.out.println(mat.dump());
    }
}
