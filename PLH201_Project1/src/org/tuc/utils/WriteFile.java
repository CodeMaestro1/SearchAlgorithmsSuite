package org.tuc.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.tuc.dataClass.DataPair;
import org.tuc.dataClass.DataPagePair;

/**
 * A utility class for writing key-value pairs to files, as well as key-data page pairs.
 */
public class WriteFile {

    /**
     * The name of the file where data pairs are written.
     */
    public static final String DATA_PAIR_FILE_NAME = "DataPair.ser";
    
    /**
     * The name of the file where unsorted data page keys are written.
     */
    public static final String UNSORTED_KEYS_FILE_NAME = "UnsortedKeys.ser";
    
    /**
     * The name of the file where sorted data page keys are written.
     */
	public static final String SORTED_KEYS_FILE_NAME = "SortedKeys.ser";
	
	 /**
     * The default size of a data page.
     */
    public static final int DATA_PAGE_SIZE = 256;
 
    //This class should not be instantiated
    private WriteFile() {
    	throw new IllegalStateException("Utility class");
    }
    
    /**
     * Writes a list of data pairs to the data pair file.
     * 
     * @param dataPairs a list of data pairs to be written to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void writeDataPairs(List<DataPair> dataPair2) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(DATA_PAIR_FILE_NAME, "rw")) {
            List<DataPair> currentDataPage = new ArrayList<>();
            int totalPairSize = 0;
            for (DataPair dataPair : dataPair2) {
                int stringSize = dataPair.getString().getBytes(StandardCharsets.US_ASCII).length;
                int pairSize = Integer.BYTES + stringSize;
                if ((totalPairSize + pairSize) < DATA_PAGE_SIZE) {
                    totalPairSize += pairSize;
                    currentDataPage.add(dataPair);
                } else {
                    ByteBuffer bb = ByteBuffer.allocate(DATA_PAGE_SIZE);
                    for (DataPair dataPair1 : currentDataPage) {
                        bb.putInt(dataPair1.getKey());
                        bb.put(dataPair1.getString().getBytes(StandardCharsets.US_ASCII));
                    }
                    byte[] byteArray = bb.array();
                    bb.clear();
                    file.write(byteArray);
                    currentDataPage.clear();
                    totalPairSize = pairSize;
                    currentDataPage.add(dataPair);
                }
            }
            if (!currentDataPage.isEmpty()) {
                ByteBuffer bb = ByteBuffer.allocate(DATA_PAGE_SIZE);
                for (DataPair lastPairs : currentDataPage) {
                    bb.putInt(lastPairs.getKey());
                    bb.put(lastPairs.getString().getBytes(StandardCharsets.US_ASCII));
                }
                byte[] byteArray = bb.array();
                //Fill the empty space to reach the fixed size of the dataPage.We do that so even the
                //last page is exactly 256 bytes.
                int remainingSpace = DATA_PAGE_SIZE - bb.position();
                if (remainingSpace > 0) {
                	byte[] emptySpace = new byte[remainingSpace];
                	Arrays.fill(emptySpace, (byte) ' ');
                	bb.put(emptySpace);

                }
                file.write(byteArray);
            }
        }
    }

    /**
     * This method writes in the file the pairs key-DataPage.
     * 
     * @param keyPairList : A list with the pairs key-DataPage to write in the file.
     * @param sorted : A flag indicating whether to write the pairs in sorted order.
     * @throws IOException
     */
    public static void writeKeys(List<DataPagePair> keyPairList, boolean sorted) throws IOException {
        String fileName = sorted ? SORTED_KEYS_FILE_NAME : UNSORTED_KEYS_FILE_NAME;
        if (sorted) {
            Collections.sort(keyPairList);
        }
        try (RandomAccessFile file = new RandomAccessFile(fileName, "rw")) {
            List<DataPagePair> copyList = new ArrayList<>();
            int totalPairSize = 0; 
            for(DataPagePair copyList2 : keyPairList) {
            	int pairSize = Integer.BYTES*2;
            	if(totalPairSize + pairSize < DATA_PAGE_SIZE) {
            		totalPairSize += pairSize;
            		copyList.add(copyList2);
            	}else {
                    ByteBuffer bb = ByteBuffer.allocate(DATA_PAGE_SIZE);
                    for(DataPagePair helpList : copyList) {
                    	bb.putInt(helpList.getKey());
                        bb.putInt(helpList.getDataPage());
            	}
                    byte[] byteArray = bb.array();
                    bb.clear();
                    file.write(byteArray);
                    copyList.clear();
                    totalPairSize = pairSize;
                    copyList.add(copyList2);    
            }
            	
            }
            if(!copyList.isEmpty()) {
                    ByteBuffer bb = ByteBuffer.allocate(DATA_PAGE_SIZE);
                    for(DataPagePair lastPairs : copyList ) {
                    	bb.putInt(lastPairs.getKey());
                        bb.putInt(lastPairs.getDataPage());
                    }
                    byte[] byteArray = bb.array();
                    //Fill the empty space to reach the fixed size of the dataPage.We do that so even the
                    //last page is exactly 256 bytes.
                    int remainingSpace = DATA_PAGE_SIZE - bb.position();
                    if (remainingSpace > 0) {
                    	byte[] emptySpace = new byte[remainingSpace];
                    	Arrays.fill(emptySpace, (byte) ' ');
                    	bb.put(emptySpace);

                    }
                    file.write(byteArray);
            		}
            	}
        }
    }
