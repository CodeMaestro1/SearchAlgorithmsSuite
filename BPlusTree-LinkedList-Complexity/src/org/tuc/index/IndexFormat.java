package org.tuc.index;


/**
 * This class represents the format used for indexing words in a dictionary.
 * It contains information about the file name and the location of the word in the file.
 * 
 * @author Constantinos Pisimisis
 */
public class IndexFormat {

    /** The file name. */
    private String fileName;
    
    /** The location in file. */
    private int locationInFile;

    /**
     * Constructs an IndexFormat object with the specified file name and location in the file.
     * 
     * @param fileName       the name of the file where the word was found
     * @param locationInFile the location of the word in the file
     */
    public IndexFormat(String fileName, int locationInFile) {
        this.fileName = fileName;
        this.locationInFile = locationInFile;
    }

    /**
     * Returns the file name where the word was found.
     * 
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name where the word was found.
     * 
     * @param fileName the file name to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the location of the word in the file.
     * 
     * @return the location in the file
     */
    public int getLocationInFile() {
        return locationInFile;
    }

    /**
     * Sets the location of the word in the file.
     * 
     * @param locationInFile the location in the file to set
     */
    public void setLocationInFile(int locationInFile) {
        this.locationInFile = locationInFile;
    }
}
