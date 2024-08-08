# SearchMethodsDemo

A collection of methods for efficient key searching using different file index techniques. Includes random search, unsorted file index search, and sorted file index search implementations.

## Table of Contents

- [SearchMethodsDemo](#searchmethodsdemo)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Methods](#methods)
    - [Random Search](#random-search)
    - [Unsorted File Index Search](#unsorted-file-index-search)
      - [`searchGivenPage`](#searchgivenpage)
      - [`searchInUnsortedFile`](#searchinunsortedfile)
    - [Sorted File Index Search](#sorted-file-index-search)
      - [`binarySearch`](#binarysearch)
      - [`searchDataPage`](#searchdatapage)
  - [Usage](#usage)
  - [Contributing](#contributing)
  - [License](#license)


## Introduction

The purpose of this project is to demonstrate different approaches to key searching using file indexes. The implemented methods offer insights into how key search algorithms work in different scenarios, helping you understand their efficiency and trade-offs.

## Methods

### Random Search

The `RandomSearch` class, located in the `org.tuc.randomSearch` package, provides a method for implementing a basic random search strategy to locate a specific key within a given dataset. This approach can be useful for smaller datasets, but it may not be the most efficient choice for larger datasets.

### Unsorted File Index Search

The `UnsortedFileIndexSearch` class, situated within the `org.tuc.unsortedFileIndexSearch` package, offers methods for efficient key searching using unsorted file index techniques. This approach leverages the order of keys in the index to expedite search operations.

#### `searchGivenPage`

The `searchGivenPage` method is used to search for a target key within a specified data page of the dataset. It takes parameters such as `targetKey`, `dataPage`, `stringLength`, and `dataPairFile`.

#### `searchInUnsortedFile`

The `searchInUnsortedFile` method performs a search for a target key in an unsorted file index. It takes parameters such as `targetKey` and `unsortedFile`.

### Sorted File Index Search

The `SortedFileIndexSearch` class, located in the `org.tuc.sortedFileIndexSearch` package, provides utility methods for performing binary search and data page search operations on a sorted file. This class supports binary search operations and data page searches on a sorted file.

#### `binarySearch`

The `binarySearch` method performs a binary search on a sorted file to find a specified key. It takes the following parameters:

- `key`: The key value you want to locate.
- `sortedFile`: The file containing sorted keys.

The method returns the data page associated with the key if found; otherwise, it returns `-1`.

#### `searchDataPage`

The `searchDataPage` method performs a search for a target key within a specific data page of a data pair file. It takes the following parameters:

- `targetKey`: The key value you want to search for.
- `dataPage`: The data page to search in.
- `stringLength`: The length of the associated string for each key, which can be either 55 or 27.
- `dataPairFile`: The file containing pairs of keys and associated strings.

The method returns `true` if the target key is found within the specified data page; otherwise, it returns `false`.

## Usage

   - Open a terminal or command prompt.
   - Navigate to the directory containing the JAR file and input files.
   - Run the program using the following command:
     ```bash
     java -jar SearchMethodsDemo.jar
     ```

Each method's source code is thoroughly documented to assist your understanding of the implementation details.

## Contributing

Contributions to this repository are encouraged! If you have improvements, bug fixes, or additional methods to propose, please follow these guidelines:

1. Fork this repository.
2. Create a new branch for your feature/fix.
3. Implement your changes and commit them.
4. Push your changes to your fork.
5. Submit a pull request explaining your modifications.

Please ensure that your contributions adhere to the project's coding standards and maintain clear documentation.

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE), a free software license that ensures user freedoms to use, share, and modify the software. For the full terms of the GPLv3 license, refer to the [LICENSE](LICENSE) file.
