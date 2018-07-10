package com.sushantpaudel.neural_network;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a neuron model comprised of: </br>
 * <ul>
 * <li>Summing part  - input summing function</li>
 * <li>Activation function</li>
 * <li>Input connections</li>
 * <li>Output connections</li>
 * </ul>
 */
public class Neuron {
    /**
     * Neuron's identifier
     */
    private String id;
    /**
     * Collection of neuron's input connections (connections to this neuron)
     */
    protected List<Connection> inputConnections;
    /**
     * Collection of neuron's output connections (connections from this to other
     * neurons)
     */
    protected List<Connection> outputConnections;
    /**
     * Input summing function for this neuron
     */
    protected WeightedSumFunction inputSummingFunction;
    /**
     * Activation function for this neuron
     */
    protected ActivationFunction activationFunction;

    /**
     * Default constructor
     */
    public Neuron() {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
    }


    public List<Connection> getInputConnections() {
        return inputConnections;
    }

    public void setInputConnections(List<Connection> inputConnections) {
        this.inputConnections = inputConnections;
    }

    public List<Connection> getOutputConnections() {
        return outputConnections;
    }

    public void setOutputConnections(List<Connection> outputConnections) {
        this.outputConnections = outputConnections;
    }

    /**
     * Calculates the neuron's output
     */
    public double calculateOutput() {
        double totalInput = inputSummingFunction.collectOutput(inputConnections);
        return activationFunction.calculateOutput(totalInput);
    }
}
