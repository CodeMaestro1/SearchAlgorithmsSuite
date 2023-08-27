/**
 * This class performs search performance tests on various data sets and record sizes.
 * It generates random keys, conducts tests, and stores results for further analysis.
 */
package org.tuc.searchtest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.tuc.dataClass.DataClass;
import org.tuc.utils.Clear;
import org.tuc.utils.CreateFiles;
import org.tuc.utils.ResultWriter;
import org.tuc.utils.PrintScreen;
import org.tuc.utils.WriteFile;

public class SearchTests {

    private static final int[] NUMBER_OF_INSTANCES_PER_TEST = {50, 100, 200, 500, 800, 1000, 2000, 5000, 10000, 50000, 100000, 200000};
    private static final int[] MAX_VALUE = {100, 200, 400, 1000, 1600, 2000, 4000, 10000, 20000, 100000, 200000, 400000};
    private static final int[] CAPACITY_OF_THE_RECORDS = {55, 27};
    private static final int MIN_VALUE_KEY = 1;
    public static final int KEYS_FOR_TEST = 1000;

    /**
     * Entry point for running search performance tests.
     * 
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        List<String> storedInformation;
        
        try {
            storedInformation = new ArrayList<>();
            List<Integer> keys = new ArrayList<>(KEYS_FOR_TEST);
            
            for (int len : CAPACITY_OF_THE_RECORDS) {
                runTestsForKeyLength(len, keys);
            }

            System.out.println("*********The end*********");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	
            storedInformation = PrintScreen.getStoredInformation();
            
            //Write the results
            ResultWriter.writeResultsToODS(storedInformation);
        }
    }

    /**
     * Run performance tests for a specific key length.
     * 
     * @param keyLength Length of the keys.
     * @param keys List to store generated keys.
     * @param storedInformation List to store test results.
     * @throws IOException If there is an I/O error during the test.
     */
    private static void runTestsForKeyLength(int keyLength, List<Integer> keys) throws IOException {
        for (int i = 0; i < MAX_VALUE.length; i++) {
            runTest(keyLength, NUMBER_OF_INSTANCES_PER_TEST[i], MAX_VALUE[i], keys);
        }
    }

    /**
     * Run a performance test for a specific data set configuration.
     * 
     * @param keyLength Length of the keys.
     * @param numOfRecords Number of records in the data set.
     * @param maxValue Maximum value for generating data.
     * @param keys List of keys for the test.
     * @param storedInformation List to store test results.
     * @throws IOException If there is an I/O error during the test.
     */
    private static void runTest(int keyLength, int numOfRecords, int maxValue, List<Integer> keys) throws IOException {
        try (
            RandomAccessFile dataPairFile = new RandomAccessFile(WriteFile.DATA_PAIR_FILE_NAME, "rw");
            RandomAccessFile unSortedFile = new RandomAccessFile(WriteFile.UNSORTED_KEYS_FILE_NAME, "rw");
            RandomAccessFile sortedFile = new RandomAccessFile(WriteFile.SORTED_KEYS_FILE_NAME, "rw")
        ) {
        	CreateFiles.createTestFiles(numOfRecords, keyLength, MIN_VALUE_KEY, maxValue, dataPairFile);
            prepareKeys(keys, numOfRecords, maxValue);

            TestRandomSearch testRandom = new TestRandomSearch(keyLength, keys, dataPairFile);
            TestUnsortedFileIndexSearch testUnsorted = new TestUnsortedFileIndexSearch(keyLength, keys, unSortedFile, dataPairFile);
            TestSortedFileIndexSearch testSorted = new TestSortedFileIndexSearch(keyLength, keys, sortedFile, dataPairFile);
            
            // Print and store test results
            PrintScreen.print(testRandom, testUnsorted, testSorted, numOfRecords, keyLength);
            PrintScreen.storeData(testRandom, testUnsorted, testSorted, numOfRecords, keyLength);
            
            //Delete the files
            Clear.clearAndDeleteFiles(dataPairFile, unSortedFile, sortedFile);
        }
    }

    /**
     * Prepare the list of keys based on the number of records.
     * 
     * @param keys List to store generated keys.
     * @param numOfRecords Number of records in the data set.
     * @param maxValue Maximum value for generating keys.
     */
    private static void prepareKeys(List<Integer> keys, int numOfRecords, int maxValue) {
        keys.clear();
        if (numOfRecords <= 1000) {
            keys.addAll(DataClass.generateDuplicateKeys(MIN_VALUE_KEY, maxValue, KEYS_FOR_TEST));
        } else {
            keys.addAll(DataClass.generateRandomKeys(MIN_VALUE_KEY, maxValue, KEYS_FOR_TEST));
        }
    }
}
