package com.beta;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Generating Beta Distribution Data...");
        Comparable[] data = BetaDistributionDataGenerator.generateData(10, 2, 5);
        
        for (Comparable d : data) {
            System.out.println(d);
        }

        // Sort the data
        System.out.println("Sortings Beta Distribution Data...");
        ShellSort.sort(data);
        // Print sorted data
        for (Comparable d : data) {
            System.out.println(d);
        }
    }
}
