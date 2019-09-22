package com.client.processingfeecalculator.util;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import com.client.processingfeecalculator.constant.FileType;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class Util {

    public static Optional<FileType> getFileType(String fileName) {
        return getFileExtension(fileName).filter(fileExt -> EnumUtils.isValidEnum(FileType.class, fileExt)).map(fileExt -> FileType.valueOf(fileExt));
    }

    public static Optional<String> getFileExtension(String fileName) {
        return Optional.ofNullable(fileName).filter(f -> f.contains(".")).map(f -> StringUtils.upperCase(f.substring(f.lastIndexOf(".") + 1)));
    }

    public static String getFileNameWithTimeStamp(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf(".")) + "_" + LocalDate.now() + fileName.substring(fileName.lastIndexOf("."));
    }

    public static Optional<Path> moveFile(String sourceFilePath, String targetFilePath) {
        try {
            if (Files.exists(Paths.get(targetFilePath))) {
                Files.delete(Paths.get(targetFilePath));
            }
            return Optional.ofNullable(Files.move(Paths.get(sourceFilePath), Paths.get(targetFilePath)));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to move the file", ex.getCause());
        }
    }

    public static <T> void writeDataToCSV(Class<T> type, List<T> list, HttpServletResponse response) {
        try (PrintWriter writer = response.getWriter()) {
            final CsvMapper mapper = new CsvMapper();
            final CsvSchema csvSchema = mapper.schemaFor(type).withHeader();
            mapper.writer(csvSchema).writeValues(writer).write(list);
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to write into csv file", ex.getCause());
        }
    }

    public static <T> void writeDataToCSV(Class<T> type, Stream<T> stream, HttpServletResponse response) {
        try (PrintWriter writer = response.getWriter()) {
            final CsvMapper mapper = new CsvMapper();
            final CsvSchema csvSchema = mapper.schemaFor(type).withHeader();
            mapper.writer(csvSchema).writeValues(writer).write(stream);
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to write into csv file", ex.getCause());
        }
    }
}
