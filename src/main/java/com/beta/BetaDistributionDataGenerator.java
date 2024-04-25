package com.beta;

import org.apache.commons.math3.distribution.BetaDistribution;

public class BetaDistributionDataGenerator {
    private int rows;
    private int columns;
    private Comparable[][][] matrix;

    public BetaDistributionDataGenerator(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new Comparable[rows][columns][];
    }



    // Method to initialize the arrays in each cell of the matrix
    public void initializeArrays(boolean alpha_ , boolean beta_ , boolean nk_) {
        for (int mi = 0; mi < rows; mi++) {
            double alpha = determineAlpha(alpha_ , mi);
            int nk = determineSize(nk_ , mi);
            double beta = determineBeta(beta_ , mi);
            for (int vj = 0; vj < columns; vj++) {

                matrix[mi][vj] = new Comparable[nk];
                BetaDistribution distribution = new BetaDistribution(alpha, beta);
                for (int cell = 0; cell < nk ; cell++) {
                    double sample = distribution.sample();
                    matrix[mi][vj][cell]= sample;
                }

            }
        }
    }

    public double determineAlpha(boolean alpha_ , int row) {
        if (alpha_) {
            return 1 ;
        }
        return 0.2 * (row + 1);
    }

    public double determineBeta(boolean beta_ , int row ) {
        if(beta_){
            return 1;
        }
        return 0.2 * (row + 1);
    }

    public int determineSize(boolean nk_ , int row) {
        int baseSize = 100000;
        if (nk_) {
            return baseSize;
        }
        return baseSize * (row + 1);
    }

    public void setElement(int row, int column, int index, int value) {
        matrix[row][column][index] = value;
    }

    public Comparable getElement(int row, int column, int index) {
        return matrix[row][column][index];
    }

    public Comparable[] getCell(int row, int column) {
        return matrix[row][column];
    }

    public Comparable[][][] getMatrix(){
        return matrix;
    }
}
