# Search Algorithms Suite and B+ Tree Complexity Tester

Welcome to the **Search Algorithms Suite** and the **B+ Tree Complexity Tester**! This project includes tools and methods for key searching and B+ tree performance evaluation.

---

## 📚 SearchMethodsDemo

### Overview

`SearchMethodsDemo` demonstrates various key searching methods using file indexes. It includes implementations for:
- Random Search
- Unsorted File Index Search
- Sorted File Index Search

### Key Components

#### Random Search

- **Class:** `RandomSearch`
- **Package:** `org.tuc.randomSearch`
- **Description:** Implements a basic random search strategy to locate a key in a dataset.

#### Unsorted File Index Search

- **Class:** `UnsortedFileIndexSearch`
- **Package:** `org.tuc.unsortedFileIndexSearch`
- **Description:** Provides methods for searching using unsorted file indexes.
  
  - **`searchGivenPage(targetKey, dataPage, stringLength, dataPairFile)`**: Searches for a key within a specific data page.
  - **`searchInUnsortedFile(targetKey, unsortedFile)`**: Searches for a key in an unsorted file index.

#### Sorted File Index Search

- **Class:** `SortedFileIndexSearch`
- **Package:** `org.tuc.sortedFileIndexSearch`
- **Description:** Methods for binary and data page searches on a sorted file.
  
  - **`binarySearch(key, sortedFile)`**: Performs a binary search on a sorted file.
  - **`searchDataPage(targetKey, dataPage, stringLength, dataPairFile)`**: Searches for a key within a specific data page of a data pair file.

---

## 🌳 B+ Tree Complexity Tester

### Overview

The **B+ Tree Complexity Tester** assesses the efficiency of a B+ tree for indexing words from textual data across multiple files. It includes:

- A robust B+ tree implementation
- File reading capabilities
- Linked list storage for word occurrences

### Key Features

- **B+ Tree Implementation:** Efficiently manages word occurrences.
- **File Reading:** Extracts words and builds a B+ tree.
- **Linked List Storage:** Associates words with file and position information using linked lists.

---

## 📜 Common Sections

### Usage

To build and run the projects, follow these steps:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/YourUsername/YourRepository.git
   cd YourRepository
   ```

2. **Install Dependencies:**

   Ensure you have `make` and `javac` (Java compiler) installed on your system.

3. **Build the Projects:**

   - **Build Project 1 (`SearchMethodsDemo`):**

     ```bash
     make project1
     ```

     This command compiles the Java source files and packages them into `project1.jar` located in the `build` directory.

   - **Build Project 2 (`B+ Tree Complexity Tester`):**

     ```bash
     make project2
     ```

     This command compiles the Java source files, prepares the necessary files (`1.txt`, `2.txt`), and packages everything into `project2.jar` located in the `build` directory.

4. **Run the Program:**

   - For `SearchMethodsDemo`:

     ```bash
     java -jar project1.jar
     ```

   - For `B+ Tree Complexity Tester`:

     ```bash
     java -jar project2.jar
     ```

   Ensure that any required input files are in the same directory as the JAR file when running the program.
---
### Contributing

Contributions to both projects are encouraged! To contribute:

1. **Fork** the repository.
2. **Create a new branch** for your feature or fix.
3. **Implement** your changes and commit them.
4. **Push** your changes to your fork.
5. **Submit a pull request** with a description of your modifications.

Ensure your contributions adhere to the project's coding standards and include clear documentation.

### License

Both projects are licensed under the [GNU General Public License](LICENSE). For detailed licensing information, please refer to the LICENSE file in the repository.

### Contact

For any questions or support, please open an issue on the GitHub repository or contact the maintainers.

---

Thank you for using and contributing to these projects!
