package org.tuc.unsortedFileIndexSearch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.tuc.dataClass.DataPagePair;
import org.tuc.utils.MultiCounter;
import org.tuc.utils.WriteFile;


public class UnsortedFileIndexSearch {
	
	  //This class should not be instantiated
	  private UnsortedFileIndexSearch() {
		    throw new IllegalStateException("static methods only");
		  }
	
	//the size of each pair in the index file
	private static final int  RECORD_SIZE_KEY_PAIR = Integer.BYTES *2;
	
	// The buffer size used for reading data pages
	private static final  byte[] buffer = new byte[WriteFile.DATA_PAGE_SIZE];
	
	// The maximum number of data page pairs that can be stored in the unsorted List
	private static final int MAX_CAPACITY = 200000;
	
	// The list of data page pairs that will used to keep the readed pairs.
	private static final List<DataPagePair> unsorted = new ArrayList<>(MAX_CAPACITY);

    /**
     * It is the decimal representation of the 32-bit number you get when you put 4 ASCII space
     * characters in a 32-bit word.If we reach that,we have reach the end of the file.
     */
    private static final int END_CHARACTER = 538976288;
	
	
	/**
	 * Given a DataPage this method is searching the key to the main file.
	 * 
	 * @param targetKey : The key we are looking
	 * @param dataPage :The result of the previous method.
	 * @param stringLength :The string can be either 55 or 27.
	 * @param dataPairFile : The dataPair file needed for the tests.
	 * @return :true(success) or false(fail)
	 * @throws IOException
	 */
    public static boolean searchGivenPage(int targetKey, int dataPage, int stringLength, RandomAccessFile dataPairFile) throws IOException {
            int recordSize = Integer.BYTES + stringLength;
            int offset = (dataPage * WriteFile.DATA_PAGE_SIZE);
            if (offset >= dataPairFile.length() || offset<0) {
                // The requested data page is beyond the end of the file or we got -1, return false
                return false;
            } else {
            	dataPairFile.seek(offset);
                int bytesRead = dataPairFile.read(buffer);
                while (bytesRead != -1) {
                    ByteBuffer bb = ByteBuffer.wrap(buffer, 0, bytesRead);
                    int numPairs = bytesRead / recordSize;
                    for (int i = 0; i < numPairs; i++) {
                        int key = bb.getInt();
                        bb.position(bb.position()+stringLength); //skip the string.We are not intrested.
                        if (key == targetKey) {
                            return true;
                            
                        }
                    }bytesRead = dataPairFile.read(buffer);
                }
            }
        return false;
    }
    /**
     * This method performs a search on the file with the usorted pairs.
     * 
     * 
     * @param targetKey : The key tht we are intrested.
     * @param stringLength : The lenght of the string.It can be either 55 or 27.
     * @param unsortedFile :The file that we need to perform the search.
     * @return If it successful it returns the dataPage.If not and we reach the end of the file -1.
     * @throws IOException
     */
    public static int searchInUnsortedFile(int targetKey, RandomAccessFile unsortedFile) throws IOException {
    	    unsortedFile.seek(0); //Reset the FilePointer to the beggining.
            int bytesRead = unsortedFile.read(buffer);
            while (bytesRead != -1) {
            	MultiCounter.increaseCounter(2);
                ByteBuffer bb = ByteBuffer.wrap(buffer, 0, bytesRead);
                int numPairs = (WriteFile.DATA_PAGE_SIZE/ RECORD_SIZE_KEY_PAIR)-1;
                for (int i = 0; i < numPairs;  i++) {
                    int key = bb.getInt();
                    if(key == END_CHARACTER) {
                        break; // stop adding keys if the end character is encountered
                    }
                    int dataPage = bb.getInt();
                    unsorted.add(new DataPagePair(key,dataPage));
                    }
                    for(DataPagePair searchInUnsorted : unsorted ) {
                        if (searchInUnsorted.getKey() == targetKey) {
                        	unsorted.clear();
                            return searchInUnsorted.getDataPage();         
                        }
                    }
                    unsorted.clear();
                    bytesRead = unsortedFile.read(buffer);
                    }
            
            unsorted.clear();
            return -1;
        }
    }