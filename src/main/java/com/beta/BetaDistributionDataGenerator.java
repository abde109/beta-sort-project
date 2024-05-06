package com.beta;

import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.Arrays;
import java.util.Random;

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
    public void initializeArrays(int case_) {
        for (int mi = 0; mi < rows; mi++) {
            double alpha = determineAlpha(mi, case_);
            int nk = determineSize(mi, case_);
            double beta = determineBeta(mi, case_);
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

    // New method to get a copy of the matrix without modifying the original
    public Comparable[][][] getMatrixCopy() {
        int rows = matrix.length;
        Comparable[][][] copy = new Comparable[rows][][];
        for (int i = 0; i < rows; i++) {
            int columns = matrix[i].length;
            copy[i] = new Comparable[columns][];
            for (int j = 0; j < columns; j++) {
                copy[i][j] = Arrays.copyOf(matrix[i][j], matrix[i][j].length);
            }
        }
        return copy;
    }

    // Determines Alpha based on the row index to ensure varied distribution characteristics
    // Determine the value of Alpha based on the specified row
    public static double determineAlpha(int row , int case_) {

        return switch (case_) {
            case 0 -> {
                // Opposite phase: Generate a random value in the range [0.5, 5.0)
                double[] list = {0.5,1,1.5,2,2.5,3,3.5,4,4.5,5};
                yield list[ (row)%10 ];
            }
            case 1 -> {
                // Synchronous variations: Generate another random value in the range [0.5, 5.0)
                double[] list = {0.5,1,1.5,2,2.5,3,3.5,4,4.5,5};
                yield list[ (row)%10 ];
            }
            default -> 1; // Default case (not expected to be used)
        };
    }

    // Determine the value of Beta based on the specified row
    public static double determineBeta(int row, int case_) {
        return switch (case_) {
            case 0 -> {
                // Opposite phase: Generate a contrasting random value in the range [0.5, 5.0)
                double[] list = {0.5,1,1.5,2,2.5,3,3.5,4,4.5,5};
                yield 5.0 - list[ (row)%10 ];

            }
            case 1 -> {
                // Synchronous variations: Generate another random value in the range [0.5, 5.0)
                double[] list = {0.5,1,1.5,2,2.5,3,3.5,4,4.5,5};
                yield list[ (row)%10 ];
            }

            default -> 1; // Default case
        };
    }

    public int determineSize(int row, int case_) {
        int baseSize = 10000;

        switch (case_) {
            case 2->{
                return baseSize * (row +1);
            }
            default -> {
                return baseSize;
            }
        }
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
