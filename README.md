# B+ Tree Complexity Tester

## Overview

This repository contains a Java program designed to test the complexity of a B+ tree implementation using textual data from multiple files. The primary objective is to assess the efficiency of the B+ tree in indexing words within the files and managing the associated information.

## Features

- **B+ Tree Implementation:** The core of the project includes a B+ tree structure that efficiently organizes and manages word occurrences across multiple files.

- **File Reading:** The program reads input files, extracting words, and building a B+ tree to index their occurrences.

- **Linked List Storage:** Each word in the B+ tree is associated with a linked list containing information about the file(s) in which the word is found and its position(s) within the file.

## How to Use

1. **Clone the Repository:**
   ```bash
   https://github.com/CodeMaestro1/BPlusTree-LinkedList-Complexity.git
   cd bplus-tree-complexity-tester
   ```

2. **Compile the Java Program:**
   ```bash
   javac MainConsole.java
   ```

3. **Run the Program:**
   ```bash
   java MainConsole
   ```

4. **Input Files:**
   - Place the text files you want to test in the `input_files` directory.

5. **View Results:**
   - The program will generate output files in the `output_files` directory, showcasing the B+ tree structure and associated linked lists.

## Sample Output

The output will include visual representations of the B+ tree and linked lists for each word. Additionally, you can find statistical information about the efficiency of the B+ tree in handling the specified dataset.

## Contributing

Feel free to contribute to the development of this project by submitting bug reports, feature requests, or pull requests. Your feedback is highly appreciated.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
