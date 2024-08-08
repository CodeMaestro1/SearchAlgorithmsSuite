package org.tuc.dataClass;

import java.io.Serializable;


/**
 * This class holds the pair key-index(DataPage) and implements Comparable
 * so we can sort the pairs based on the key.
 * It also contains various methos to access the key or the string.
 *
 */
public class DataPagePair implements Serializable, Comparable<DataPagePair> {
    

    private static final long serialVersionUID = 1L;
    private int key;
    private int dataPage;
    

    /**
     * This constructor creates a the pairs key-index of the file.
     * 
     * @param key
     * @param dataPage
     */
    public DataPagePair(int key, int dataPage) {
        this.key = key;
        this.dataPage = dataPage;
    }

    /**
     * 
     * @return the key from the pair key-DataPage
     */
    public int getKey() {
        return key;
    }

    /**
     * 
     * @return the DataPage.
     */
    public int getDataPage() {
        return dataPage;
    }
    
    
    /**
     * A simple method to compare two integers.
     */
    @Override
    public int compareTo(DataPagePair o) {
        return Integer.compare(this.key, o.key);
    }
}



