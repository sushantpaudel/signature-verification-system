package com.sushantpaudel.train;

import com.sushantpaudel.neural_network.Connection;
import com.sushantpaudel.neural_network.NeuralNet;
import com.sushantpaudel.neural_network.NeuralNetLayer;
import com.sushantpaudel.neural_network.Neuron;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class TrainController {
    private Stage primaryStage;

    public Button btnCreateNeuralNetwork;
    public Button btnTrain;
    public Button btnMakeConnections;

    private NeuralNet neuralNet;

    public TrainController() {
    }

    public void initialize() {
        btnCreateNeuralNetwork.setOnMouseClicked(event -> createNeuralNetwork());
        btnMakeConnections.setOnMouseClicked(event -> makeConnections());
        btnTrain.setOnMouseClicked(event -> trainData());
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void createNeuralNetwork() {
        ArrayList<Neuron> neurons = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            neurons.add(new Neuron());
        }
        NeuralNetLayer inputLayer = new NeuralNetLayer("input", neurons);

        neurons.clear();
        neurons.add(new Neuron());
        NeuralNetLayer outputLayer = new NeuralNetLayer("output", neurons);

//        neurons.clear();
//        for (int i = 0; i < 8; i++) {
//            neurons.add(new Neuron());
//        }
//        ArrayList<NeuralNetLayer> hiddenLayers = new ArrayList<>();
//        NeuralNetLayer neuralNetLayerHiddenOne = new NeuralNetLayer("hiddenOne",neurons);
//        hiddenLayers.add(neuralNetLayerHiddenOne);

        neuralNet = new NeuralNet("neuralNet", inputLayer, outputLayer);
    }

    private void makeConnections() {
        NeuralNetLayer input = neuralNet.getInputLayer();
        NeuralNetLayer output = neuralNet.getOutputLayer();
        ArrayList<Neuron> inputNeurons = (ArrayList<Neuron>) input.getNeurons();
        ArrayList<Neuron> outputNeurons = (ArrayList<Neuron>) output.getNeurons();
        for (Neuron neuronPrevious : inputNeurons) {
            for (Neuron neuronNext : outputNeurons) {
                Connection connection = new Connection(neuronPrevious, neuronNext);
                connection.setWeight(new Random().nextDouble());
                neuronPrevious.getOutputConnections().add(connection);
                neuronNext.getInputConnections().add(connection);
            }
        }
    }

    float outputError = 1f;

    private void trainData() {
        float error = 0.001f;
        while (true) {
            forwardPass();
            if (outputError > error) {
                backwardPass();
            } else {
                break;
            }
        }
    }

    private void forwardPass() {
        NeuralNetLayer input = neuralNet.getInputLayer();
        NeuralNetLayer output = neuralNet.getOutputLayer();
        ArrayList<Neuron> inputNeurons = (ArrayList<Neuron>) input.getNeurons();
        ArrayList<Neuron> outputNeurons = (ArrayList<Neuron>) output.getNeurons();
        for (Neuron neuronPrevious : inputNeurons) {
            for (Neuron neuronNext : outputNeurons) {

            }
        }
    }

    private void backwardPass() {

    }

    private void testData() {

    }

}
