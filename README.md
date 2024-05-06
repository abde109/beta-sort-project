### **Project Overview**

This project implements the Shell Sort algorithm to sort data generated based on a Beta distribution. It provides visualizations of the execution time analysis using JavaFX and is designed to compare different data configurations with varying Alpha and Beta parameters.

### **Project Structure**

1. **`src/` Directory:**
    - **`com.beta.App`**: Main application file that generates and sorts the Beta-distributed data.
    - **`com.beta.BetaDistributionDataGenerator`**: Generates data according to Beta distribution parameters and stores it in a 3D matrix.
    - **`com.beta.DataVisualizer`**: Visualizes execution time trends using JavaFX charts.
    - **`com.beta.ShellSort`**: Implementation of the Shell Sort algorithm.
    - **`com.beta.TXTStorage`**: Helper class to store results in a text file.
2. **`resources/` Directory:**
    - Contains configuration files (if any).
3. **`results/` Directory:**
    - Contains text files or CSV files with analysis results.

### **Environment and Dependencies**

- **Java SDK**: Version 11 or later is recommended for this project.
- **JavaFX**: Required for the visualization charts. Follow JavaFX Setup Guide to set it up.
- **Apache Commons Math**: Used for generating Beta-distributed data. Ensure you have **`commons-math3-3.6.1.jar`** (or newer) in the classpath.
- **Maven/Gradle**: Alternatively, use these build tools to handle dependencies.

### **Project Setup Instructions**

1. **Install Java SDK:** Ensure that the required Java SDK is installed on your system.
2. **Install JavaFX:** Download and install JavaFX as per the linked guide above.
3. **Compile the Project:**
    - If using an IDE (IntelliJ, Eclipse), import the project as a Maven/Gradle project.
    - If compiling via command line, ensure the appropriate dependencies (JavaFX and Apache Commons Math) are included in the classpath.
4. **Run the Application:**
    - Execute **`com.beta.App`** to generate, sort, and analyze the data.
    - Ensure that the analysis results are saved in the **`results/`** directory for visualizations.
5. **Visualize Results:**
    - Run **`com.beta.DataVisualizer`** to view graphical analysis of execution times.

### **Additional Notes**

- **Warm-Up Rounds:** The program executes a certain number of warm-up rounds to stabilize JVM performance before measuring sorting times.
- **Adjustable Parameters:** Alpha and Beta distributions can be adjusted in the **`App`** class for experimentation purposes.

### **Troubleshooting**

- **JavaFX Errors:** Verify that JavaFX libraries are in the classpath.
- **Performance Issues:** Increase heap space allocation or adjust JVM parameters if sorting times are excessive.