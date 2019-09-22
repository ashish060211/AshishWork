package com.client.processingfeecalculator.reader;

import java.io.File;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class CsvFileReader implements FileReader {

    public <T> MappingIterator getFileReaderIterator(Class<T> type, File file) {
        try {
            final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
            final CsvMapper mapper = new CsvMapper();
            final MappingIterator<T> mappingIterator = mapper.readerFor(type).with(csvSchema).readValues(file);
            return mappingIterator;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
