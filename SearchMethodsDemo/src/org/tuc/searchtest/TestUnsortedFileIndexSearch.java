package org.tuc.searchtest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.tuc.unsortedFileIndexSearch.UnsortedFileIndexSearch;

public class TestUnsortedFileIndexSearch extends Test {
    public TestUnsortedFileIndexSearch(int stringLength, List<Integer> keys, RandomAccessFile unsortedFile, RandomAccessFile dataPairFile) throws IOException {
        super(stringLength, keys, dataPairFile, unsortedFile);
    }

    @Override
    protected boolean performSearch(int key, int stringLength, RandomAccessFile dataPairFile, RandomAccessFile keyFile) throws IOException {
        int tempResult = UnsortedFileIndexSearch.searchInUnsortedFile(key, keyFile);
        return UnsortedFileIndexSearch.searchGivenPage(key, tempResult, stringLength, dataPairFile);
    }
}

