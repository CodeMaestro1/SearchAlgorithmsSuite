package org.tuc.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    private static final String FILE_PATH = "test_results.txt";

    private ResultWriter() {
        throw new IllegalStateException("Utility class");
    }

    public static void writeResultsToODS(List<String> storedInformation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String information : storedInformation) {
                writer.write(information);
                writer.newLine();
            }
            System.out.println("Results written to " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
