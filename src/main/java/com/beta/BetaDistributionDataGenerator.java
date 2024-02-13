package com.beta;
import org.apache.commons.math3.distribution.BetaDistribution;

public class BetaDistributionDataGenerator {
    public static Comparable[] generateData(int size , double alpha, double beta){

        BetaDistribution distribution = new BetaDistribution(alpha, beta);
        Comparable[] data = new Comparable[size];

        for (int i = 0; i < size; i++) {
            data[i] = distribution.sample();
        }

        return data;
    }
}
