# B+ Tree Complexity Tester

## Overview

Welcome to the B+ Tree Complexity Tester! This Java program is designed to assess the efficiency of a B+ tree implementation in indexing words within textual data from multiple files. The program analyzes the complexity of the B+ tree structure and its ability to manage word occurrences and associated information.

## Features

- **B+ Tree Implementation:** The core of the project features a robust B+ tree structure that efficiently organizes and manages word occurrences across multiple files.

- **File Reading:** The program reads input files, extracts words, and builds a B+ tree to index their occurrences.

- **Linked List Storage:** Each word in the B+ tree is associated with a linked list containing information about the file(s) in which the word is found and its position(s) within the file.

## How to Use

1. **Download the JAR File:**
   - Download the JAR file from the [releases page](https://github.com/CodeMaestro1/BPlusTree-LinkedList-Complexity/releases).

2. **Place Input Files:**
   - Place the text files you want to test in the same directory as the downloaded JAR file.

3. **Default Test Files:**
   - The zip archive includes additional files in the current directory. These files are provided for performing a default test. You can use these files as a starting point for evaluating the B+ tree's performance.

4. **Run the Program:**
   - Open a terminal or command prompt.
   - Navigate to the directory containing the JAR file and input files.
   - Run the program using the following command:
     ```bash
     java -jar BplusTreeComplexityTester.jar
     ```

## Sample Output

The output includes visual representations of the B+ tree and linked lists for each word. Additionally, statistical information about the efficiency of the B+ tree in handling the specified dataset is provided.

## Contributing

Feel free to contribute to the development of this project by submitting bug reports, feature requests, or pull requests. Your feedback is highly appreciated.

## License

This project is licensed under the GNU General Public License (GNU GPL) - see the [LICENSE](LICENSE) file for details.
