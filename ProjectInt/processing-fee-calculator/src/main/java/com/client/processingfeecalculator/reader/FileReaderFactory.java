package com.client.processingfeecalculator.reader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.client.processingfeecalculator.constant.FileType;

@Component
public class FileReaderFactory {
    private static final Map<FileType, Supplier<FileReader>> supplierMap = new HashMap<>();
    static {
        supplierMap.put(FileType.CSV, CsvFileReader::new);
    }

    public FileReader getFileReader(FileType fileType) {
        Supplier<FileReader> fileReaderSupplier = supplierMap.get(fileType);
        if (fileReaderSupplier != null) {
            return fileReaderSupplier.get();
        }
        throw new IllegalArgumentException("File format " + fileType.name() + "not supported");
    }
}
