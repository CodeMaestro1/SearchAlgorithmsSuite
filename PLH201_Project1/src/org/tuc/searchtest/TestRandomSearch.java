package org.tuc.searchtest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.tuc.randomSearch.RandomSearch;

public class TestRandomSearch extends Test {
    public TestRandomSearch(int stringLength, List<Integer> keys, RandomAccessFile dataPairFile) throws IOException {
        super(stringLength, keys, dataPairFile, null); // Passing null for keyFile as it's not used in RandomSearch
    }

    @Override
    protected boolean performSearch(int key, int stringLength, RandomAccessFile dataPairFile, RandomAccessFile keyFile) throws IOException {
        return RandomSearch.randomSearch(key, stringLength, dataPairFile);
    }

}

