package org.tuc.sortedFileIndexSearch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.tuc.dataClass.DataPagePair;
import org.tuc.utils.MultiCounter;
import org.tuc.utils.WriteFile;

/**
 * The {@code SortedFileIndexSearch} class provides utility methods for performing binary search and data page search
 * operations on a sorted file.
 */
public final class SortedFileIndexSearch {

    private SortedFileIndexSearch() {
        throw new UnsupportedOperationException("static methods only");
    }

    /**
     * It is the decimal representation of the 32-bit number you get when you put 4 ASCII space
     * characters in a 32-bit word.
     */
    private static final int END_CHARACTER = 538976288;
    private static final int RECORD_SIZE_KEY_PAIR = Integer.BYTES * 2;
    private static final byte[] buffer = new byte[WriteFile.DATA_PAGE_SIZE];
    private static final List<DataPagePair> binary = new ArrayList<>();

    /**
     * Performs a binary search on the sorted file to find the specified key.
     *
     * @param key         the key to search for
     * @param sortedFile  the file with sorted keys
     * @return the data page associated with the key, or -1 if the key is not found
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static int binarySearch(int key, RandomAccessFile sortedFile) throws IOException {
        long fileSize = sortedFile.length();
        int numRecords = (int) (fileSize / WriteFile.DATA_PAGE_SIZE);
        int leftIndex = 0;
        int rightIndex = numRecords - 1;

        while (leftIndex <= rightIndex) {
            binary.clear();
            MultiCounter.increaseCounter(3);
            int midIndex = (leftIndex + rightIndex) >>> 1;
            int offset = midIndex * WriteFile.DATA_PAGE_SIZE;

            readDataPagePairs(sortedFile, offset);

            int firstKey = binary.get(0).getKey();
            int lastKey = binary.get(binary.size() - 1).getKey();

            if (key >= firstKey && key <= lastKey) {
                return findKeyInDataPagePairs(key);
            } else if (key < firstKey) {
                rightIndex = midIndex - 1;
            } else {
                leftIndex = midIndex + 1;
            }
        }

        binary.clear();
        return -1;
    }

    private static void readDataPagePairs(RandomAccessFile sortedFile, int offset) throws IOException {
        sortedFile.seek(offset);
        int bytesRead = sortedFile.read(buffer);
        ByteBuffer bb = ByteBuffer.wrap(buffer, 0, bytesRead);

        for (int i = 0; i < ((WriteFile.DATA_PAGE_SIZE / RECORD_SIZE_KEY_PAIR) - 1); i++) {
            int keyBinary = bb.getInt();

            if (keyBinary == END_CHARACTER) {
                break;
            }

            int dataPage = bb.getInt();
            binary.add(new DataPagePair(keyBinary, dataPage));
        }
    }

    private static int findKeyInDataPagePairs(int key) {
        for (DataPagePair findKey : binary) {
            if (findKey.getKey() == key) {
                binary.clear();
                return findKey.getDataPage();
            }
        }
        binary.clear();
        return -1;
    }

    /**
     * Performs a search for the target key in a specific data page of the data pair file.
     *
     * @param targetKey      the key to search for
     * @param dataPage       the data page to search in
     * @param stringLength   the length of the string used for data pair records
     * @param dataPairFile   the data pair file to search in
     * @return {@code true} if the target key is found in the data page, {@code false} otherwise
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static boolean searchDataPage(int targetKey, int dataPage, int stringLength, RandomAccessFile dataPairFile)
            throws IOException {
        int recordSize = Integer.BYTES + stringLength;
        int offset = dataPage * WriteFile.DATA_PAGE_SIZE;

        if (offset >= dataPairFile.length() || offset < 0) {
            return false;
        } else {
            dataPairFile.seek(offset);
            int bytesRead = dataPairFile.read(buffer);

            while (bytesRead != -1) {
                ByteBuffer bb = ByteBuffer.wrap(buffer, 0, bytesRead);
                int numPairs = bytesRead / recordSize;

                for (int i = 0; i < numPairs; i++) {
                    int key = bb.getInt();
                    bb.position(bb.position() + stringLength);

                    if (key == targetKey) {
                        return true;
                    }
                }

                bytesRead = dataPairFile.read(buffer);
            }
        }

        return false;
    }
}