package org.tuc.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.tuc.dataClass.DataClass;
import org.tuc.dataClass.DataPair;
import org.tuc.dataClass.DataPagePair;

/**
 * This utility class is responsible for creating the necessary files for test scenarios.
 * It creates dataPairs, sortedKeys, and unsortedKeys files for testing purposes.
 */
public class CreateFiles {

    private static final boolean UNSORTED = false;
    private static final boolean SORTED = true;

    private CreateFiles() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates the necessary files for testing.
     * 
     * @param numOfRecords Number of records to generate.
     * @param stringLength Length of the string (can be 55 or 27).
     * @param minValue The lowest valid value that the key must be.
     * @param maxValue The highest (included) value that the key cannot exceed.
     * @param dataPairFile RandomAccessFile to write data pairs.
     * @throws IOException If an I/O error occurs during file operations.
     */
    public static void createTestFiles(int numOfRecords, int stringLength, int minValue, int maxValue, RandomAccessFile dataPairFile) throws IOException {
        List<DataPair> dataPair = DataClass.generateDataPairs(numOfRecords, stringLength, minValue, maxValue);
        WriteFile.writeDataPairs(dataPair);
        
        List<DataPagePair> keyPairList = ReadFile.readDataPairs(stringLength, dataPairFile);
        
        WriteFile.writeKeys(keyPairList, UNSORTED); // Write the keys as unsorted
        WriteFile.writeKeys(keyPairList, SORTED);   // Write the keys as sorted
        
        keyPairList.clear();
    }
}
