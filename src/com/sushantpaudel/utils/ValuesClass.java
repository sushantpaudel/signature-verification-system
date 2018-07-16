package com.sushantpaudel.utils;

public class ValuesClass {
    static final String CHECK_DATA_DIRECTORY_PATH = "C:/Users/susha/Projects/IdeaProjects/SignatureVerificationSystem/train/check-data";
    public static final String RAW_DATA_DIRECTORY_PATH = "C:/Users/susha/Projects/IdeaProjects/SignatureVerificationSystem/train/raw-data";
    public static final String PRE_PROCESSED_DATA_DIRECTORY_PATH = "C:/Users/susha/Projects/IdeaProjects/SignatureVerificationSystem/train/pre-processed-data";
    public static final String TRAIN_DATA_PATH = "C:/Users/susha/Projects/IdeaProjects/SignatureVerificationSystem/train/pre-processed-data-train";
    public static final String TEST_DATA_PATH = "C:/Users/susha/Projects/IdeaProjects/SignatureVerificationSystem/train/pre-processed-data-test";
    public static final String TEST_DATA_DIRECTORY_PATH = "";
    //PRE-PROCESSING ENTRIES
    static final int BOX_MEDIAN_FILTER = 1;
    static int INTENSITY = 130;
    static final int IMG_HEIGHT = 100;
    static int IMG_WIDTH = 100;
    //PREDICTIONS
    static final String GENUINE = "Genuine";
    static final String FORGED = "Forged";
    static final String IMAGE_ERROR = "Provide a valid Image!!";
    static final String SERVER_ERROR = "Try again! Server error!!";


}
