package com.client.processingfeecalculator.reader;

import java.io.File;

import com.fasterxml.jackson.databind.MappingIterator;

public interface FileReader {

    <T> MappingIterator getFileReaderIterator(Class<T> type, File file);
}
