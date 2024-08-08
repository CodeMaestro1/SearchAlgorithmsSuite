package org.tuc.dataClass;

import java.io.Serializable;

public class DataPair implements Serializable {
    
	
	private static final long serialVersionUID = 1L; //Here we use the default serialVersion of JVM.
    private int key;
    private String value;

    
    /**This constuctor holds the pair of key-string.
     * It contains methods to access the key or the string.
     * 
     */
    public DataPair(int randomKey, String value) {
        this.key = randomKey;
        this.value = value;
    }

    /**
     * 
     * @return the key of the pair key-string
     */
    public int getKey() {
        return key;
    }

    /**
     * 
     * @return the string from the pair.
     */
    public String getString() {
        return value;
    }
}

