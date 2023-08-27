package org.tuc.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tuc.dataClass.DataClass;

public class Clear {

    private static final String ERROR_MESSAGE = "%s: no such file or directory%n";

    private Clear() {
        throw new IllegalArgumentException("Utility class");
    }

    /**
     * Performs the clearing and deletion operations.
     *
     * @param dataPairFile The dataFile to close and delete.
     * @param unsortedFile The unsortedFile to close and delete.
     * @param sortedFile   The sorted file to close and delete.
     * @throws IOException if any I/O error occurs.
     */
    public static void clearAndDeleteFiles(RandomAccessFile dataPairFile, RandomAccessFile unsortedFile,
                                           RandomAccessFile sortedFile) throws IOException {

    	//reset the counters
    	MultiCounter.resetCounter(1);
    	MultiCounter.resetCounter(2);
    	MultiCounter.resetCounter(3);
    	
    	
        // Close the files before we delete them.
        dataPairFile.close();
        unsortedFile.close();
        sortedFile.close();

        DataClass.uniqueKeys.clear();
        DataClass.duplicateKeys.clear();
        DataClass.dataPairs.clear();
        ReadFile.keyPairList.clear();

        // Delete the files
        Path[] filePaths = {
            Paths.get("UnsortedKeys.ser"),
            Paths.get("SortedKeys.ser"),
            Paths.get("DataPair.ser")
        };

        Path defaultPath = Paths.get(System.getProperty("user.dir"));

        for (Path path : filePaths) {
            try {
                Files.delete(defaultPath.resolve(path));
            } catch (NoSuchFileException e) {
                System.err.format(ERROR_MESSAGE, path);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}