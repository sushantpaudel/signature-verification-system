package com.sushantpaudel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import static com.sushantpaudel.utils.ValuesClass.*;

public class PredictClass {
    private String name;
    private String path;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private byte[] getJSON() {
        String json = "{\"image\":\"" + path + "\"}".replace("\\", "/");
        System.out.println(json);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    public String startPrediction() {
        try {
            String prediction = getPrediction();
            System.out.println(prediction);
            if (prediction.contains(FORGED)) {
                return FORGED;
            } else if (prediction.contains(GENUINE)) {
                return GENUINE;
            } else {
                return IMAGE_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SERVER_ERROR;
        }
    }

    private String getPrediction() throws Exception {
        String POST_URL = "http://127.0.0.1:5000/predict";
        String response = doPostRequest(POST_URL);
        System.out.println(response);
        return response;
    }

    private String doPostRequest(String url) throws IOException {
        URL obj = new URL(url);
        URLConnection myURLConnection = obj.openConnection();
        HttpURLConnection http = (HttpURLConnection) myURLConnection;
        http.setRequestMethod("POST");
        byte[] out = getJSON();
        int length = out.length;
        myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        myURLConnection.setRequestProperty("Content-Length", "" + length);
        myURLConnection.setRequestProperty("Content-Language", "en-US");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoInput(true);
        myURLConnection.setDoOutput(true);
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }
}
