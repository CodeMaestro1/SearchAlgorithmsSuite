package org.tuc.testTrial;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.tuc.btree.BTree;
import org.tuc.index.IndexFormat;
import org.tuc.utils.MultiCounter;
import org.tuc.utils.ReadFile;

/**
 * The TestTrial class performs a search trial using a B+ tree data structure.
 * It processes two files, constructs a B+ tree, and measures the performance of searches on a subset of elements from the files.
 */
public class TestTrial {
    /**
     * The order of the B+ Tree that we will run our tests
     * It is M-1 so when we reach M keys we split.
     */
    private static final int[] ORDERS = {2, 4, 9, 14, 19, 24, 29, 39, 99};
    
    private static final String FIRST_FILE = "1.txt";
    private static final String SECOND_FILE = "2.txt";
    private static final int SUBSET_SIZE = 100;
    private BTree<String, LinkedList<IndexFormat>> tree;
    
    /**
     * Constructs a TestTrial object with a specified B+ tree instance.
     * The test trial is automatically performed upon instantiation.
     *
     * @param tree the B+ tree instance
     */
    public TestTrial(BTree<String, LinkedList<IndexFormat>> tree) {
        this.tree = tree;
        testTrial();
    }

    /**
     * Performs a search trial by processing two files and measuring 
     * the performance of searches on a subset of elements.
     * 
     */
    private void testTrial() {
        System.out.println("\n******************************Test Started******************************");
        try {
            Set<String> combinedSet;
            
            // Process the first file
            Set<String> setOne = processFile(FIRST_FILE);

            // Process the second file
            Set<String> setTwo = processFile(SECOND_FILE);
            
            
            //Uncomment this if you want to get the total words in one set
            Set<String> setThree = new HashSet<>(setOne);
            setThree.addAll(setTwo);
            
            
            //Uncommnet this if you want to see the total number of keys existing in the B+ Tree.
            //System.err.println(setThree.size());
            
            // Get the 100 elements for the tests.
            combinedSet = ReadFile.getRandomElements(setOne, setTwo, SUBSET_SIZE);
            
            //Uncomment this if you want to see the total number of test keys.
            //System.err.println("Total Number of keys: " + combinedSet.size());

            resetCounters(); //the counters have been increased during the insertion process 

            for (int order : ORDERS) {
                BTree<String, LinkedList<IndexFormat>> treeTest = new BTree<>(order);

                // Process the first file
                ReadFile.readWordsFromFile(Paths.get(FIRST_FILE), treeTest);

                // Process the second file
                ReadFile.readWordsFromFile(Paths.get(SECOND_FILE), treeTest);

                resetCounters();

                for (String element : combinedSet) {
                    treeTest.search(element);
                }

                float sumCounters = (float) MultiCounter.getCount(2) + MultiCounter.getCount(3); 
                
                //M+1 to restore the "proper" order
                System.out.println("For order: " + (order + 1) + " Mean of inner nodes: "
                        + ((float) MultiCounter.getCount(1) / 100) + " | " + " Mean of keys comparison: "
                        + (sumCounters / 100));
                
                resetCounters(); // Reset the counters for the next order
            }

        } catch (Exception e) {
            System.err.println("Error during the test trial: " + e.getMessage());
        } finally {
            System.out.println("******************************Test Ended******************************");
        }
    }

    /**
     * Processes a file by reading words from it and adding them to the B+ tree.
     * 
     * @param fileName the name of the file to process
     * @return a Set of unique words extracted from the file
     * @throws IOException if an error occurs while reading the file
     */
    private Set<String> processFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        ReadFile.readWordsFromFile(filePath, tree);
        return ReadFile.getWordSet();
    }

    /**
     * Resets the performance counters used for measuring mean inner nodes and key comparisons.
     */
    private void resetCounters() {
        MultiCounter.resetCounter(1);
        MultiCounter.resetCounter(2);
        MultiCounter.resetCounter(3);
    }
    
}
