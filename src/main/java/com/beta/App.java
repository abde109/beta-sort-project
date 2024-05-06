package com.beta;

import java.io.FileOutputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {


        System.out.println("Generating Beta Distribution Data...");
        int rows = 9;
        int columns = 9;
        int case_ = 2;


        clearFile("GeneratingData.txt");
        clearFile("AnalysisResults.txt");

        BetaDistributionDataGenerator distribution = new BetaDistributionDataGenerator(rows, columns);
        distribution.initializeArrays(case_);

        // TXTStorage.storeResults("GeneratingData.txt", "Time taken to generate data: " + timer.getElapsedTime() + " milliseconds");
        System.out.println("\nSorting Each Row of Beta Distribution werm up...");


        // Initial Sorting Rounds for JVM Warm-Up
        for (int warmUpRound = 0; warmUpRound < 20; warmUpRound++) {
            for (int row = 0; row < distribution.getMatrix().length; row++) {
                for (int column = 0; column < distribution.getMatrix()[row].length; column++) {
                    ShellSort.sort(distribution.getMatrixCopy()[row][column]);
                }
            }
        }

        System.out.println("\nSorting Each Row of Beta Distribution Data...");
        for (int row = 0; row < distribution.getMatrix().length; row++) {
            StringBuilder analysisContent = new StringBuilder();
            for (int column = 0; column < distribution.getMatrix()[row].length; column++) {
                ExecutionTimer timeExecution = new App.ExecutionTimer();

                timeExecution.start();
                ShellSort.sort(distribution.getMatrix()[row][column]);
                timeExecution.stop();

                analysisContent.append("cell[").append(row).append(",").append(column).append("]");

                analysisContent.append("parameter[").append(distribution.determineAlpha(row,case_)).append(",").append(distribution.determineBeta(row,case_)).append("] - ").append("size[").append(distribution.determineSize(row, case_)).append("] - ").append("Execution time [").append(timeExecution.getElapsedTime()).append("]").append("\n");


            }
            TXTStorage.storeResults("AnalysisResults.txt", analysisContent.toString());
        }

        DataVisualizer.launch(DataVisualizer.class, args);

        // for (Comparable[][] row : distribution.getMatrix()) {
        //     StringBuilder analysisContent = new StringBuilder();

        //     for (Comparable[] column : row) {
        //         ShellSort.sort(column);
        //         analysisContent.append("cell["+ row +","+ column +"]");
        //         analysisContent.append(column);

        //         analysisContent.append("Variance = ").append("ddd").append("\n");
        //     }
        //     TXTStorage.storeResults("AnalysisResults.txt", analysisContent.toString());
        // }

    }


    public static class ExecutionTimer {
        private long startTime;
        private long endTime;
        private long elapsedTime;

        public void start() {
            startTime = System.currentTimeMillis();
        }

        public void stop() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
        }

        public long getElapsedTime() {
            return elapsedTime;
        }

    }

    private static void clearFile(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName, false)) {
            // Nothing to write, opening the stream with 'false' will truncate the file
        } catch (IOException e) {
            System.err.println("Error clearing the file: " + fileName);
            e.printStackTrace();
        }
    }
}


