package org.tuc.searchtest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class for performing search tests and collecting results.
 */
public abstract class Test {

    protected List<Boolean> collectSuccessResults;
    protected List<Boolean> collectFailResults;
    protected long finalTimeTest;
    private RandomAccessFile keyFile;
    private RandomAccessFile dataPairFile;

    /**
     * Constructs a new Test instance and performs search tests.
     *
     * @param stringLength The length of the string used for the data pairs.
     * @param keys         A list of keys to perform the tests.
     * @param dataPairFile The dataPair file used for the search.
     * @param keyFile      The key file used as a dictionary to locate random keys.
     * @throws IOException If there's an I/O error.
     */
    protected Test(int stringLength, List<Integer> keys, RandomAccessFile dataPairFile, RandomAccessFile keyFile) throws IOException {
        initializeLists();
        this.keyFile = keyFile;
        this.dataPairFile = dataPairFile;
        runTests(stringLength, keys);
    }

    /**
     * Initializes the lists and counters used for collecting test results.
     */
    private void initializeLists() {
        collectSuccessResults = new ArrayList<>();
        collectFailResults = new ArrayList<>();
        finalTimeTest = 0;
    }

    /**
     * Runs search tests using the provided keys, evaluating their success and failure.
     *
     * @param stringLength The length of the string used for the data pairs.
     * @param keys         A list of keys to perform the tests.
     * @throws IOException If there's an I/O error.
     */
    private void runTests(int stringLength, List<Integer> keys) throws IOException {
        long startTimeTest = System.nanoTime();
        for (Integer key : keys) {
            boolean result = performSearch(key, stringLength, dataPairFile, keyFile);
            (result ? collectSuccessResults : collectFailResults).add(result);
        }
        long endTimeTest = System.nanoTime();
        finalTimeTest = endTimeTest - startTimeTest;
    }

    /**
     * Performs the search operation for a given key and string length using provided data files.
     *
     * @param key          The key to search for.
     * @param stringLength The length of the string used for the data pairs.
     * @param dataPairFile The data pair file used for the search.
     * @param keyFile      The key file used as a dictionary to locate random keys.
     * @return True if the search was successful, false otherwise.
     * @throws IOException If there's an I/O error.
     */
    protected abstract boolean performSearch(int key, int stringLength, RandomAccessFile dataPairFile, RandomAccessFile keyFile) throws IOException;

    /**
     * Returns a list of successful search results.
     *
     * @return The list of successful search results.
     */
    public List<Boolean> getCollectSuccessResults() {
        return collectSuccessResults;
    }

    /**
     * Returns a list of failed search results.
     *
     * @return The list of failed search results.
     */
    public List<Boolean> getCollectFailResults() {
        return collectFailResults;
    }

    /**
     * Returns the total time taken for the test.
     *
     * @return The total time taken for the test.
     */
    public long getFinalTimeTest() {
        return finalTimeTest;
    }
}
