package org.tuc.searchtest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.tuc.sortedFileIndexSearch.SortedFileIndexSearch;

public class TestSortedFileIndexSearch extends Test {
    public TestSortedFileIndexSearch(int stringLength, List<Integer> keys, RandomAccessFile sortedFile, RandomAccessFile dataPairFile) throws IOException {
        super(stringLength, keys, dataPairFile, sortedFile);
    }

    @Override
    protected boolean performSearch(int key, int stringLength, RandomAccessFile dataPairFile, RandomAccessFile keyFile) throws IOException {
        int tempResult = SortedFileIndexSearch.binarySearch(key, keyFile);
        return SortedFileIndexSearch.searchDataPage(key, tempResult, stringLength, dataPairFile);
    }
}

