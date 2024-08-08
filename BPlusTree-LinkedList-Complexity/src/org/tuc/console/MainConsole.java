package org.tuc.console;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.tuc.btree.BTree;
import org.tuc.index.IndexFormat;
import org.tuc.testTrial.TestTrial;
import org.tuc.utils.ReadFile;
import org.tuc.utils.StandardInputRead;

/**
 * The MainConsole class represents the main console application.
 */
public class MainConsole {

    private static final int DEFAULT_ORDER = 10;
    
    private StandardInputRead inputReader;
    private BTree<String, LinkedList<IndexFormat>> tree;

    public MainConsole() {
        inputReader = new StandardInputRead();
        tree = new BTree<>(DEFAULT_ORDER);
    }

    /**
     * The main method of the console application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        MainConsole console = new MainConsole();
        console.run();
    }

    /**
     * Runs the main console application.
     */
    public void run() {
        int choice;
        do {
            System.out.println("Main Menu");
            System.out.println("1. Insert file name");
            System.out.println("2. Search in the dictionary");
            System.out.println("3. Perform a search trial");
            System.out.println("0. Exit");
            choice = inputReader.readPositiveInt("Enter your choice:");

            switch (choice) {
                case 1:
                    handleFileInsertion();
                    break;
                case 2:
                    searchOnDictionary();
                    break;
                case 3:
                    new TestTrial(tree);
                    break;
                case 0:
                    System.out.println("Exiting...Thanks for using us!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println(); // Add a blank line for readability

        } while (choice != 0);
    }

    /**
     * Handles the file insertion option.
     */
    private void handleFileInsertion() {
        String fileName = inputReader.readString("Insert the name of the file:");
        Path filePath = Paths.get(fileName);
        if (!isValidFile(filePath)) {
            System.err.println("Invalid file: " + fileName + ". Please try again.");
        } else {
            insertFileData(filePath);
        }
    }

    /**
     * Checks if the given file is valid.
     *
     * @param filePath The path of the file
     * @return true if the file is valid, false otherwise
     */
    private boolean isValidFile(Path filePath) {
        return Files.exists(filePath) && !Files.isDirectory(filePath);
    }

    /**
     * Inserts data from a file into the BTree.
     *
     * @param filePath The path of the file
     */
    private void insertFileData(Path filePath) {
        try {
            ReadFile.readWordsFromFile(filePath, tree);
        } catch (Exception e) {
            System.err.println("Error inserting file data: " + e.getMessage());
        }
    }

    /**
     * Performs a search on the dictionary.
     */
    private void searchOnDictionary() {
        String wordToSearch = inputReader.readString("Enter a word to search in the dictionary: ");
        LinkedList<IndexFormat> searchResult = tree.search(wordToSearch);
        if (searchResult != null) {
            // Print the contents of the LinkedList
            for (IndexFormat index : searchResult) {
                System.out.println("File's name: " + index.getFileName() + " Location: " + index.getLocationInFile());
            }
        } else {
            System.out.println("The word you are searching for does not exist.");
        }
    }
}