/**
 * This package contains utility classes and methods for various purposes.
 * The classes in this package provide functionality for file reading, word extraction, and random element selection.
 * 
 * 
 */
package org.tuc.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.tuc.btree.BTree;
import org.tuc.index.IndexFormat;

/**
 * A class that reads a file and extracts its words using a regular expression pattern.
 * 
 */
public class ReadFile {

    /**
     * The set of words extracted from the file.
     * Duplicate words are avoided by using a Set.
     */
    private static Set<String> wordSet;
    
    private static Random random = new Random();

    /**
     * Constructs a new ReadFile object.
     * Private constructor to prevent instantiation.
     * This class provides static methods and should not be instantiated.
     *
     * @throws IllegalStateException if an attempt is made to instantiate this class
     */
    private ReadFile() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Reads the contents of the file specified by the file path and extracts its words using the
     * regular expression pattern specified by the exercise.
     *
     * @param filePath the path of the file to read
     * @param btree the BTree data structure for storing word positions
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static void readWordsFromFile(Path filePath, BTree<String, LinkedList<IndexFormat>> btree) throws IOException {
        wordSet = new HashSet<>(); //Creating a new instance every time to avoid creating a big wordSet.
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            int charCount = 0;
            line = br.readLine();
            while (line != null) {
                String[] words = line.split(" "); // Separate the words
                for (String wordFile : words) {
                    String word = removeNonAlphabeticChars(wordFile);
                    LinkedList<IndexFormat> positions = btree.search(word);
                    if (positions == null) {
                        positions = new LinkedList<>();
                        positions.add(new IndexFormat(filePath.getFileName().toString(), charCount));
                        btree.insert(word, positions);
                    } else {
                        positions.add(new IndexFormat(filePath.getFileName().toString(), charCount));
                    }
                    charCount += wordFile.length() + 1; // +1 for the space character
                    wordSet.add(word); // Add the word to the set
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the set of words extracted from the file.
     * @apiNote The returned set is unmodifiable to ensure data integrity.
     *
     * @return the set of words extracted from the file
     */
    public static Set<String> getWordSet() {
        return Collections.unmodifiableSet(wordSet);
    }

    /**
     * Selects a random subset of elements from a given set.
     * 
     *@apiNote The returned set is unmodifiable to ensure data integrity.
     *
     * @param set1 the first set of elements from which to select
     * @param set2 the second set of elements from which to select
     * @param count the number of elements to select randomly
     * @param <T> the type of elements in the sets
     * @return a set containing a random subset of elements from
     * the original sets
     * @throws IllegalArgumentException if the specified count is negative or greater than the size of the sets
     */
    public static <T> Set<T> getRandomElements(Set<T> set1, Set<T> set2, int count) {
        if (count > set1.size() || count < 0) {
            throw new IllegalArgumentException("Count cannot be greater than the size of the set or negative");
        }

        List<T> list1 = new ArrayList<>(set1);
        List<T> list2 = new ArrayList<>(set2);

        Set<T> randomSet = new HashSet<>();

        while (randomSet.size() < count) {
        	
            // When we select 50 elements from the first file we process with the second one
            List<T> currentList = (randomSet.size() < count / 2) ? list1 : list2;
            int randomIndex = random.nextInt(currentList.size());
            randomSet.add(currentList.get(randomIndex));
        }

        return Collections.unmodifiableSet(randomSet);
    }

    /**
     * Removes non-alphabetic characters from a word.
     * @apiNote If the word is a number it will be eliminated
     *
     * @param word the word from which to remove non-alphabetic characters
     * @return the word without non-alphabetic characters
     */
    private static String removeNonAlphabeticChars(String word) {
        return word.replaceAll("[^a-zA-Z]", "");
    }
}