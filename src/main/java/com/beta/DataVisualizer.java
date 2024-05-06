package com.beta;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataVisualizer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            List<TestResult> results = readDataFromFile("C:\\Users\\abdek\\IdeaProjects\\Project_algo\\AnalysisResults.txt");
            VBox vbox = new VBox(20); // 20 is the spacing between elements in the VBox
            vbox.setPadding(new Insets(20)); // Margin around the VBox

            BarChart<String, Number> sizeChart = createSizeChart(results);
            BarChart<String, Number> paramsChart = createParamsChart(results);
            BarChart<String, Number> cellChart = createCellChart(results);

            vbox.getChildren().addAll(sizeChart, paramsChart, cellChart);

            Scene scene = new Scene(vbox, 800, 1200); // Adjusted for three charts

            stage.setScene(scene);
            stage.setTitle("Algorithm Performance Visualization");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions properly in real-world applications
        }
    }

    private List<TestResult> readDataFromFile(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<TestResult> results = new ArrayList<>();

        Pattern pattern = Pattern.compile("cell\\[(\\d+),(\\d+)]parameter\\[(\\d+\\.\\d+),(\\d+\\.\\d+)] - size\\[(\\d+)] - Execution time \\[(\\d+)]");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int cellX = Integer.parseInt(matcher.group(1));
                int cellY = Integer.parseInt(matcher.group(2));
                double parameter1 = Double.parseDouble(matcher.group(3));
                double parameter2 = Double.parseDouble(matcher.group(4));
                int size = Integer.parseInt(matcher.group(5));
                int executionTime = Integer.parseInt(matcher.group(6));

                results.add(new TestResult(cellX, cellY, parameter1, parameter2, size, executionTime));
            }
        }

        return results;
    }

    private BarChart<String, Number> createSizeChart(List<TestResult> results) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Execution Time by Size");
        xAxis.setLabel("Size");
        yAxis.setLabel("Execution Time");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Execution Time");

        // Group by size and calculate the average execution time
        Map<Integer, Double> avgExecTimeBySize = results.stream()
                .collect(Collectors.groupingBy(TestResult::getSize,
                        Collectors.averagingInt(TestResult::getExecutionTime)));

        // Sort sizes
        List<Integer> sortedSizes = new ArrayList<>(avgExecTimeBySize.keySet());
        sortedSizes.sort(Comparator.naturalOrder());

        // Add sorted data to the series
        sortedSizes.forEach(size -> series.getData().add(new XYChart.Data<>(size.toString(), avgExecTimeBySize.get(size))));

        chart.getData().add(series);
        return chart;
    }


    private BarChart<String, Number> createParamsChart(List<TestResult> results) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Average Execution Time by Parameters (Grouped by Row)");
        xAxis.setLabel("Row (Alpha, Beta)");
        yAxis.setLabel("Average Execution Time");

        // Group by row (cellX) and collect results
        Map<Integer, List<TestResult>> resultsByRow = results.stream()
                .collect(Collectors.groupingBy(TestResult::getCellX));

        // Create and add series for each row
        resultsByRow.forEach((row, rowResults) -> {
            // Calculate the average execution time for this row
            double avgExecutionTime = rowResults.stream()
                    .mapToInt(TestResult::getExecutionTime)
                    .average()
                    .orElse(0);

            // Assume the first result in the group provides a representative value for alpha and beta
            TestResult firstResult = rowResults.get(0);
            String rowLabel = String.format("Row %d (Alpha %.2f, Beta %.2f)", row, firstResult.getParameter1(), firstResult.getParameter2());
            String rows = String.format("Row %d", row);

            // Create and add the data point
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(rowLabel);
            series.getData().add(new XYChart.Data<>(rows, avgExecutionTime));
            chart.getData().add(series);
        });

        return chart;
    }



    private BarChart<String, Number> createCellChart(List<TestResult> results) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Execution Time in Each Cell");
        xAxis.setLabel("Cell");
        yAxis.setLabel("Execution Time");

        // Assuming cells are unique and already sorted
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Execution Time");

        // If cells are not unique, replace this with appropriate aggregation logic
        results.stream()
                .sorted(Comparator.comparingInt(TestResult::getCellX)
                        .thenComparingInt(TestResult::getCellY))
                .forEach(result -> {
                    //String cellLabel = String.format("Cell[%d,%d] - size [%d] - Beta(%s,%s)", result.cellX, result.cellY, result.getSize(), String.format("%05.2f",result.parameter1),String.format("%05.2f",result.parameter2));
                    String cellLabel = String.format("Cell[%d,%d]", result.cellX, result.cellY);
                    series.getData().add(new XYChart.Data<>(cellLabel, result.executionTime));
                });

        chart.getData().add(series);
        return chart;
    }


    public static class TestResult {
        int cellX;
        int cellY;
        double parameter1;
        double parameter2;
        int size;
        int executionTime;

        public int getCellX() {
            return cellX;
        }

        public int getCellY() {
            return cellY;
        }

        public double getParameter1() {
            return parameter1;
        }

        public double getParameter2() {
            return parameter2;
        }


        public TestResult(int cellX, int cellY, double parameter1, double parameter2, int size, int executionTime) {
            this.cellX = cellX;
            this.cellY = cellY;
            this.parameter1 = parameter1;
            this.parameter2 = parameter2;
            this.size = size;
            this.executionTime = executionTime;
        }

        public int getSize() {
            return size;
        }

        public int getExecutionTime() {
            return executionTime;
        }
    }
}
