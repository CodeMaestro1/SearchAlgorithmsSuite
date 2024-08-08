package org.tuc.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.tuc.searchtest.SearchTests;
import org.tuc.searchtest.TestRandomSearch;
import org.tuc.searchtest.TestSortedFileIndexSearch;
import org.tuc.searchtest.TestUnsortedFileIndexSearch;

/**
 * The PrintScreen class provides methods for displaying and storing test-related information.
 * It formats and presents information about access to the disk and time taken by different methods.
 * Information can be displayed on the console and/or stored for later retrieval.
 */
public class PrintScreen {
    private static final String SEPARATOR = "===============================================================================================";
    private static final String STAR_SEPARATOR = "**********************************************************************************************";
    private static final DecimalFormat df = new DecimalFormat("#.#");

    private static List<String> storedInformation = new ArrayList<>();

    private PrintScreen() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Displays formatted test-related information on the console.
     *
     * @param random         The TestRandomSearch instance containing test data.
     * @param unsorted       The TestUnsortedFileIndexSearch instance containing test data.
     * @param sorted         The TestSortedFileIndexSearch instance containing test data.
     * @param numberOfRecords The number of records for the test.
     * @param stringLength   The length of strings for the test.
     */
    public static void print(TestRandomSearch random, TestUnsortedFileIndexSearch unsorted, TestSortedFileIndexSearch sorted, int numberOfRecords, int stringLength) {
        // Create and format information
        String accessInfo = formatAccessInfo(numberOfRecords, stringLength);
        String timeInfo = formatTimeInfo(random, unsorted, sorted);
        String newLines = "\n\n";

        // Display the information
        display(STAR_SEPARATOR);
        display("The following data is the number of access on the disk per KEYS_FOR_TEST");
        display(accessInfo);
        display(SEPARATOR);
        display("The following data is the time taken by each method");
        display(timeInfo);
        display(STAR_SEPARATOR);
        display(newLines);
    }

    /**
     * Stores formatted test-related information for later retrieval.
     *
     * @param random         The TestRandomSearch instance containing test data.
     * @param unsorted       The TestUnsortedFileIndexSearch instance containing test data.
     * @param sorted         The TestSortedFileIndexSearch instance containing test data.
     * @param numberOfRecords The number of records for the test.
     * @param stringLength   The length of strings for the test.
     */
    public static void storeData(TestRandomSearch random, TestUnsortedFileIndexSearch unsorted, TestSortedFileIndexSearch sorted, int numberOfRecords, int stringLength) {
        // Create and format information
        String accessInfo = formatAccessInfo(numberOfRecords, stringLength);
        String timeInfo = formatTimeInfo(random, unsorted, sorted);

        // Store the information
        storedInformation.add(accessInfo);
        storedInformation.add(timeInfo);
    }

    /**
     * Retrieves the stored test-related information.
     *
     * @return A list containing the stored information.
     */
    public static List<String> getStoredInformation() {
        return storedInformation;
    }

    private static String formatAccessInfo(int numberOfRecords, int stringLength) {
        return String.format("For N: %d and stringLength: %d | Counter Index for Method A: %s | Counter Index for Method B: %s | Counter Index for Method C: %s",
            numberOfRecords,
            stringLength,
            df.format(((float) (MultiCounter.getCount(1))) / SearchTests.KEYS_FOR_TEST),
            df.format(((float) (MultiCounter.getCount(2))) / SearchTests.KEYS_FOR_TEST),
            df.format(((float) (MultiCounter.getCount(3))) / SearchTests.KEYS_FOR_TEST)
        );
    }

    private static String formatTimeInfo(TestRandomSearch random, TestUnsortedFileIndexSearch unsorted, TestSortedFileIndexSearch sorted) {
        return String.format("Mean time of method A: %d | Mean time of method B: %d | Mean time of method C: %d",
            random.getFinalTimeTest() / random.getCollectSuccessResults().size(),
            unsorted.getFinalTimeTest() / unsorted.getCollectSuccessResults().size(),
            sorted.getFinalTimeTest() / sorted.getCollectSuccessResults().size()
        );
    }

    private static void display(String message) {
        System.out.println(message);
    }
}
