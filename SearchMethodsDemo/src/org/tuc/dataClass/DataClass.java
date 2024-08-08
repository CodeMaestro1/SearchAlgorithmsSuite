package org.tuc.dataClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class generates random data instances, which consist of a random key and a random string.
 * The class provides methods for generating random keys and strings, as well as for generating
 * a list of data instances.
 */
public class DataClass implements Serializable {
	
    //Define the serialVersion so we can avoid any problem during the enoding and decoding process.
    private static final long serialVersionUID = 1L;
    private int randomKey;
    private String randomString;
    private static final int MAX_INSTANCES = 200000;
    private static final int MAX_DUPLICATE_KEYS = 1000;
    public static List<Integer> uniqueKeys  = new ArrayList<>(MAX_INSTANCES);
    public static List<Integer> duplicateKeys = new ArrayList<>(MAX_DUPLICATE_KEYS);
    public static List<DataPair> dataPairs = new ArrayList<>(MAX_INSTANCES);
    private static final Random random = new Random();

    
    /**
     * Generates a random string of length stringLength
     * @param stringLength the length of the string to generate
     * @param random the Random object to use for generating the string
     * @return a random string of length stringLength
     * Adopted from <a href="https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/">geeksforgeeks</a> 
     */
    private static String generateRandomString(int stringLength) {
    	// choose a Character random from this String
    	  String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    	         + "0123456789"
    	         + "abcdefghijklmnopqrstuvxyz";
    	 StringBuilder sb = new StringBuilder(stringLength);
    	  for (int i = 0; i < stringLength; i++) {
    	 
    	   // generate a random number between
    	   // 0 to AlphaNumericString variable length
    	   int index
    	    = (int)(AlphaNumericString.length()
    	      * Math.random());
    	 
    	   // add Character one by one in end of sb
    	   sb.append(AlphaNumericString
    	      .charAt(index));
    	  }
        return sb.toString();
    }
    
    
    /**
     * Generates a list of unique random keys.
     * 
     * @param minValue :the minimum value of the random key
     * @param maxValue :the maximum value of the random key
     * @param numOfKeys : the number of keys we want to generate. 
     * @return a list of keys(unique).
     */
    public static List<Integer> generateRandomKeys(int minValue, int maxValue, int numOfKeys) {
    	int[] randomIntsDuplicate = random.ints(minValue,maxValue+1)
    			.distinct()
    			.limit(numOfKeys)
    			.toArray();
    	
    	uniqueKeys = Arrays.stream(randomIntsDuplicate)
    			.boxed()
    			.collect(Collectors.toList());

        return uniqueKeys;
    }
    

    /**
     * Generates a list of duplicate random keys.
     *
     * @param minValue The minimum value of the random key (inclusive).
     * @param maxValue The maximum value of the random key (inclusive).
     * @param numOfKeys The number of keys to generate.
     * @return A list of keys (not unique).
     *
     * @apiNote This method uses {@link java.util.stream.Collectors#toList()} instead of {@link java.util.stream.Stream#toList()}
     *          since the former returns a mutable collection, allowing modifications to the list contents.
     */
    public static List<Integer> generateDuplicateKeys(int minValue, int maxValue, int numOfKeys) {
    	int[] randomIntsDuplicate = random.ints(minValue,maxValue+1)
    			.limit(numOfKeys)
    			.toArray();
    	
    	duplicateKeys = Arrays.stream(randomIntsDuplicate)
    			.boxed()
    			.collect(Collectors.toList());
    	
        return duplicateKeys;
    }


    
    /**
     * Generates N DataPair instances with random keys and strings and adds them to a list.
     * 
     * 
     * @param N the number of instances to create
     * @param stringLength the length of each random string
     * @param minValue the minimum value of the random key(inclusive)
     * @param maxValue the maximum value of the random key(exclusive)
     * @return a List of N DataPair instances
     */
    public static List<DataPair> generateDataPairs(int N, int stringLength, int minValue, int maxValue) {
    	List<Integer> helpList = generateRandomKeys(minValue, maxValue, N);
    	for(Integer uniqueKey : helpList ) {
        String randomString = generateRandomString(stringLength);
        dataPairs.add(new DataPair(uniqueKey,randomString));
        }
        return dataPairs;
    }

    /**
     * @return the random key stored in the object
     */
    public int getRandomKey() {
        return randomKey;
    }

    /**
     * @return the random string stored in the object
     */
    public String getRandomString() {
        return randomString;
    }

}
