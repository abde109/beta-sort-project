package com.beta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TXTStorage {

    public static void storeResults(String filename, String content) {
        try (FileWriter fw = new FileWriter(filename, true); // Set append to true
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the data: " + e.getMessage());
        }
    }
}
