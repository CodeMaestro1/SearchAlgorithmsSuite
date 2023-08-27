package org.tuc.randomSearch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import org.tuc.utils.MultiCounter;
import org.tuc.utils.WriteFile;

/**
 * 
 * This class is performing the first method that is requested.
 * 
 * */
public class RandomSearch {
	
	private static final  byte[] buffer = new byte[WriteFile.DATA_PAGE_SIZE];
	
	  //This class should not be instantiated
	  private RandomSearch() {
		    throw new IllegalStateException("static methods only");
		  }
	
   /**
     *Based on a key and a given stringLenght it read the file 
     * unti it found the key.If the key exists,it returns true.If not
     * it returns false.
     * 
     *  
     * @param targetKey : the key we want to perform search. 
     * @param stringLength : the stringLenght can be either 55 or 27.
     * @param dataPairFile : The file that contains the pair key-string.
     * @return either true if we found the key either false
     * @throws IOException
     */
	
	public static boolean randomSearch(int targetKey, int stringLength, RandomAccessFile dataPairFile) throws IOException {
		    dataPairFile.seek(0);
	        int recordSize = Integer.BYTES + stringLength; //calculate the size of the pair
	        int bytesRead = dataPairFile.read(buffer); //read 256bytes each time
	        while (bytesRead != -1) { //it returns -1 when we reach the end of the file
	        	MultiCounter.increaseCounter(1);
	            ByteBuffer bb = ByteBuffer.wrap(buffer, 0, bytesRead);
	            int numPairs = bytesRead / recordSize;
	            for (int i = 0; i < numPairs;  i++) {
	                int key = bb.getInt();
	                bb.position(bb.position() + stringLength); //skip the string since we are not intrested on that
	                if (key == targetKey) {
	                    return true;
	                }
	            }
	            bytesRead = dataPairFile.read(buffer);
	        }
	        return false;
	}
	}